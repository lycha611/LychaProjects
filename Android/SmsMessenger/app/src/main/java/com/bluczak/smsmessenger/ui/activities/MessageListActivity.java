package com.bluczak.smsmessenger.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bluczak.smsmessenger.R;
import com.bluczak.smsmessenger.backend.annotations.ContentView;
import com.bluczak.smsmessenger.backend.models.Conversation;
import com.bluczak.smsmessenger.backend.utils.Logger;
import com.bluczak.smsmessenger.ui.activities.base.InjectionActivity;
import com.bluczak.smsmessenger.ui.adapters.MessageAdapter;

import butterknife.InjectView;
import butterknife.OnClick;
import de.ub0r.android.lib.Utils;
import de.ub0r.android.lib.apis.Contact;

/**
 * Created by BLuczak on 2015-06-26.
 */
@ContentView(R.layout.activity_message_list)
public class MessageListActivity extends InjectionActivity {

    //region view references
    @InjectView(R.id.lvMessages)
    protected ListView mLvMessages;

    @InjectView(R.id.etMessage)
    protected EditText mEtMessage;
    //end region view references


    //region UI interaction handlers
    @OnClick(R.id.btnSend)
    public void onBtnSendClick() {
        try {
            final Intent i = buildIntent(true);
            startActivity(i);
            //noinspection ConstantConditions
            PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .edit()
                    .putString("backup_last_sms",
                            mEtMessage.getText().toString()).commit();
            mEtMessage.setText("");
        } catch (ActivityNotFoundException | NullPointerException e) {
            Logger.e(TAG, "unable to launch sender app %s", e);
            Toast.makeText(this, R.string.error_sending_failed, Toast.LENGTH_LONG).show();
        }
    }
    //end region UI interaction handlers


    //region fields
    private static final String TAG = "MessageListActivity";
    private Uri uri;
    private Conversation mConversation = null;
    private static final String URI = "content://mms-sms/conversations/";
    private boolean mEnableAutosend = true;
    //end region fields


    //region Activity lifecycle callbacks
    @Override
    protected final void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        parseIntent(intent);
    }

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLocale(this);
        parseIntent(getIntent());
    }

    @Override
    protected final void onResume() {
        super.onResume();
        final ListView lv = mLvMessages;
        lv.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        lv.setAdapter(new MessageAdapter(this, uri));
    }
    //end region Activity lifecycle callbacks


    //region public helper methods

    //end region public helper methods


    //region private helper methods
    private Intent buildIntent(final boolean autosend) {
        //noinspection ConstantConditions
        if (mConversation == null || mConversation.getContact() == null) {
            Logger.e(TAG, "buildIntent() without contact: %s", mConversation);
            throw new NullPointerException("mConversation and mConversation.getContact() must be not null");
        }
        final String text = mEtMessage.getText().toString().trim();
        final Intent i = ConversationListActivity.getComposeIntent(mConversation.getContact()
                .getNumber());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Intent.EXTRA_TEXT, text);
        i.putExtra("sms_body", text);
        if (autosend && mEnableAutosend && text.length() > 0) {
            i.putExtra("AUTOSEND", "1");
        }
        return i;

    }

    private void setListAdapter(final ListAdapter la) {
        mLvMessages.setAdapter(la);
    }
    //end region private helper methods


    private void parseIntent(final Intent intent) {
        Logger.d(TAG, "parseIntent(", intent, ")");
        if (intent == null) {
            return;
        }
        Logger.d(TAG, "got action: ", intent.getAction());
        Logger.d(TAG, "got uri: ", intent.getData());

        uri = intent.getData();
        if (uri != null) {
            if (!uri.toString().startsWith(URI)) {
                uri = Uri.parse(URI + uri.getLastPathSegment());
            }
        } else {
            final long tid = intent.getLongExtra("thread_id", -1L);
            uri = Uri.parse(URI + tid);
            if (tid < 0L) {
                try {
                    startActivity(ConversationListActivity.getComposeIntent(null));
                } catch (ActivityNotFoundException e) {
                    Log.e(TAG, "activity not found", e);
                    Toast.makeText(this, R.string.error_conv_null, Toast.LENGTH_LONG).show();
                }
                finish();
                return;
            }
        }

        int threadId;
        try {
            threadId = Integer.parseInt(uri.getLastPathSegment());
        } catch (NumberFormatException e) {
            Log.e(TAG, "unable to parse thread id: %s", e);
            Toast.makeText(this, R.string.error_conv_null, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        final Conversation c = Conversation.getConversation(this, threadId, true);
        mConversation = c;

        if (c == null) {
            Toast.makeText(this, R.string.error_conv_null, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        final Contact contact = c.getContact();
        contact.update(this, false, true);
        boolean showKeyboard = intent.getBooleanExtra("showKeyboard", false);

        Logger.d(TAG, "address: %s", contact.getNumber());
        Logger.d(TAG, "name: %s", contact.getName());
        Logger.d(TAG, "displayName: %s", contact.getDisplayName());
        Logger.d(TAG, "showKeyboard: %s", showKeyboard);

        final ListView lv = mLvMessages;
        lv.setStackFromBottom(true);

        MessageAdapter adapter = new MessageAdapter(this, uri);
        setListAdapter(adapter);

        String displayName = contact.getDisplayName();
        setTitle(displayName);
        String number = contact.getNumber();
        if (displayName.equals(number)) {
            getActionBar().setSubtitle(null);
        } else {
            getActionBar().setSubtitle(number);
        }

        final String body = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (!TextUtils.isEmpty(body)) {
            mEtMessage.setText(body);
            showKeyboard = true;
        }

        if (showKeyboard) {
            mEtMessage.requestFocus();
        }

    }


}
