package com.bluczak.smsmessenger.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bluczak.smsmessenger.R;
import com.bluczak.smsmessenger.backend.annotations.ContentView;
import com.bluczak.smsmessenger.backend.models.Conversation;
import com.bluczak.smsmessenger.backend.utils.Logger;
import com.bluczak.smsmessenger.ui.activities.base.InjectionActivity;
import com.bluczak.smsmessenger.ui.adapters.ConversationAdapter;

import java.util.Calendar;

import butterknife.InjectView;
import butterknife.OnItemClick;
import de.ub0r.android.lib.apis.Contact;


@ContentView(R.layout.activity_conversation_list)
public class ConversationListActivity extends InjectionActivity {

    //region view references
    @InjectView(R.id.lvConversations)
    protected ListView mLvConversations;
    //end region view references


    //region UI interaction handlers
    @OnItemClick(R.id.lvConversations)
    public void onItemLvConversationsClick(final AdapterView<?> parent, final int position) {
        final Conversation c = Conversation.getConversation(this, (Cursor) parent.getItemAtPosition(position));
        final Uri target = c.getUri();
        final Intent i = new Intent(this, MessageListActivity.class);
        i.setData(target);
        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Logger.e(TAG, "error launching intent: ", i.getAction(), ", ", i.getData());
            Toast.makeText(this,
                    "error launching messaging app!\n" + "Please contact the developer.",
                    Toast.LENGTH_LONG).show();
        }
    }
    //end region UI interaction handlers


    //region fields
    public static final String TAG = "HomeActivity";
    private ConversationAdapter mConversationAdapter;
    private Contact mContact;
    public static final Uri URI = Uri.parse("content://mms-sms/conversations/");
    private static final Calendar CAL_DAYAGO = Calendar.getInstance();
    //end region fields


    //region Activity lifecycle callbacks
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            Logger.d(TAG, "got intent: ", intent.getAction());
            Logger.d(TAG, "got uri: ", intent.getData());
            final Bundle b = intent.getExtras();
            if (b != null) {
                Logger.d(TAG, "user_query: ", b.get("user_query"));
                Logger.d(TAG, "got extra: ", b);
            }
            final String query = intent.getStringExtra("user_query");
            Logger.d(TAG, "user query: ", query);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConversationAdapter = new ConversationAdapter(this);
        setListAdapter(mConversationAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mConversationAdapter.startMsgListQuery();
    }
    //end region Activity lifecycle callbacks


    //region public helper methods
    public static String getDate(final Context context, final long time) {
        long t = time;
        if (t < CAL_DAYAGO.getTimeInMillis()) {
            return DateFormat.getDateFormat(context).format(t);
        } else {
            return DateFormat.getTimeFormat(context).format(t);
        }
    }
    //end region public helper methods


    //region private helper methods
    private void setListAdapter(final ListAdapter listAdapter) {
        mLvConversations.setAdapter(listAdapter);
    }

    public static Intent getComposeIntent(final String address) {
        Intent i = new Intent(Intent.ACTION_SENDTO);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (address == null) {
            i.setData(Uri.parse("sms:"));
        } else {
//            i.setData(Uri.parse("sms to:" + PreferencesActivity.fixNumber(context, address)));
        }
        return i;
    }
    //end region private helper methods

}

