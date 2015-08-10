package com.bluczak.smsmessenger.ui.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.bluczak.smsmessenger.R;
import com.bluczak.smsmessenger.backend.models.Conversation;
import com.bluczak.smsmessenger.backend.models.Message;
import com.bluczak.smsmessenger.backend.utils.Logger;
import com.bluczak.smsmessenger.ui.activities.ConversationListActivity;
import com.bluczak.smsmessenger.ui.activities.MessageListActivity;

import de.ub0r.android.lib.apis.Contact;

/**
 * Created by BLuczak on 2015-06-28.
 */
public class MessageAdapter extends ResourceCursorAdapter {

    private static final String TAG = "MessageAdapter";
    private Context mContext;

    /**
     * General WHERE clause.
     */
    private static final String WHERE = "(" + Message.PROJECTION_JOIN[Message.INDEX_TYPE] + " != "
            + Message.SMS_DRAFT + " OR " + Message.PROJECTION_JOIN[Message.INDEX_TYPE]
            + " IS NULL)";

    /**
     * WHERE clause for drafts.
     */
    private static final String WHERE_DRAFT = "(" + Message.PROJECTION_SMS[Message.INDEX_THREADID]
            + " = ? AND " + Message.PROJECTION_SMS[Message.INDEX_TYPE] + " = " + Message.SMS_DRAFT
            + ")";
    // + " OR " + type + " = " + Message.SMS_PENDING;

    private String displayName = null;

    private static class MessageViewHolder {
        private TextView mTvBody;
        private TextView mTvPerson;
        private TextView mTvDate;
    }

    public MessageAdapter(final MessageListActivity c, final Uri u) {
        super(c, R.layout.item_message_list, getCursor(c.getContentResolver(), u), true);
        mContext = c.getApplicationContext();

        /*
      Thread id.
     */
        int threadId = -1;
        if (u == null || u.getLastPathSegment() == null) {
            threadId = -1;
        } else {
            threadId = Integer.parseInt(u.getLastPathSegment());
        }
        final Conversation conv = Conversation.getConversation(c, threadId, false);
        /*
      Address.
     */
        String address = null;
        /*
      Name.
     */
        String name = null;
        if (conv == null) {
            address = null;
            name = null;
            displayName = null;
        } else {
            final Contact contact = conv.getContact();
            address = contact.getNumber();
            name = contact.getName();
            displayName = contact.getDisplayName();
        }
        Logger.d(TAG, "address: %s", address);
        Logger.d(TAG, "name: %s", name);
        Logger.d(TAG, "displayName: %s", displayName);
    }

    private static Cursor getCursor(final ContentResolver cr, final Uri u) {
        Logger.d(TAG, "getCursor(", u, ")");
        final Cursor[] c = new Cursor[]{null, null};

        int tid = -1;
        try {
            tid = Integer.parseInt(u.getLastPathSegment());
        } catch (Exception e) {
            Logger.e(TAG, "error parsing uri: ", u, e);
        }

        try {
            Logger.d(TAG, "where: %s", WHERE);
            c[0] = cr.query(u, Message.PROJECTION_JOIN, WHERE, null, null);
        } catch (NullPointerException e) {
            Logger.e(TAG, "error query: ", u, " / ", WHERE, e);
            c[0] = null;
        } catch (SQLiteException e) {
            Logger.e(TAG, "error getting messages %s", e);
        }

        final String[] sel = new String[]{String.valueOf(tid)};
        try {
            Logger.d(TAG, "where: ", WHERE_DRAFT, " / sel: %s", sel);
            c[1] = cr.query(Uri.parse("content://sms/"), Message.PROJECTION_SMS, WHERE_DRAFT, sel,
                    Message.SORT_USD);
        } catch (NullPointerException e) {
            Logger.e(TAG, "error query: ", u, " / ", WHERE_DRAFT, " sel: ", sel, e);
            c[1] = null;
        } catch (SQLiteException e) {
            Logger.e(TAG, "error getting drafts %s", e);
        }

        if (c[1] == null || c[1].getCount() == 0) {
            return c[0];
        }
        if (c[0] == null || c[0].getCount() == 0) {
            return c[1];
        }

        return new MergeCursor(c);
    }

    @Override
    public final void bindView(final View view, final Context context, final Cursor cursor) {
        final Message message = Message.getMessage(context, cursor);

        MessageViewHolder messageViewHolder = (MessageViewHolder) view.getTag();
        if (messageViewHolder == null) {
            messageViewHolder = new MessageViewHolder();
            initViewHolder(view, messageViewHolder);
            view.setTag(messageViewHolder);
        }

        setDateOnVieHolder(message, context, messageViewHolder);

    }


    private void initViewHolder( View view, MessageViewHolder messageViewHolder) {
        messageViewHolder.mTvPerson = (TextView) view.findViewById(R.id.tvPerson);
        messageViewHolder.mTvBody = (TextView) view.findViewById(R.id.tvBody);
        messageViewHolder.mTvDate = (TextView) view.findViewById(R.id.tvDate);
    }

    private void setDateOnVieHolder(Message message, Context context, MessageViewHolder messageViewHolder) {
        int messageType = message.getType();

        String subject = message.getSubject();
        if (subject == null) {
            subject = "";
        } else {
            subject = ": " + subject;
        }

        switch (messageType) {
            case Message.SMS_DRAFT:
                //TODO action after SMS_DRAFT
            case Message.SMS_OUT:
                messageViewHolder.mTvPerson.setText((String) mContext.getResources().getText(R.string.lbl_me) + subject);
                break;
            case Message.SMS_IN:
                messageViewHolder.mTvPerson.setText(displayName + subject);
                break;
        }

        // date
        final long time = message.getDate();
        messageViewHolder.mTvDate.setText(ConversationListActivity.getDate(context, time));

        // body
        CharSequence text = message.getBody();

        if (text == null) {
            messageViewHolder.mTvBody.setVisibility(View.INVISIBLE);
        } else {
            messageViewHolder.mTvBody.setText(text);
            messageViewHolder.mTvBody.setVisibility(View.VISIBLE);
        }
    }

}
