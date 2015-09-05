package com.bluczak.smsmessenger.ui.adapters;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.provider.CallLog.Calls;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.bluczak.smsmessenger.R;
import com.bluczak.smsmessenger.backend.models.Conversation;
import com.bluczak.smsmessenger.backend.utils.Logger;
import com.bluczak.smsmessenger.ui.activities.ConversationListActivity;

import de.ub0r.android.lib.apis.Contact;

/**
 * Created by BLuczak on 2015-06-26.
 */
public class ConversationAdapter extends ResourceCursorAdapter {

    private static final String TAG = "ConversationAdapter";
    private static final String SORT = Calls.DATE + " DESC";
    private final BackgroundQueryHandler queryHandler;
    private static final int MESSAGE_LIST_QUERY_TOKEN = 0;
    private final Activity mActivity;

    private static class ConverstationViewHolder {
        private TextView mTvPerson;
        private TextView mTvDate;
        private TextView mTvCount;
        private TextView mTvBody;
    }

    private final class BackgroundQueryHandler extends AsyncQueryHandler {

        /**
         * A helper class to help make handling asynchronous {@link ContentResolver} queries
         * easier.
         *
         * @param contentResolver {@link ContentResolver}
         */
        public BackgroundQueryHandler(final ContentResolver contentResolver) {
            super(contentResolver);
        }


        @Override
        protected void onQueryComplete(final int token, final Object cookie, final Cursor cursor) {
            switch (token) {
                case MESSAGE_LIST_QUERY_TOKEN:
                    ConversationAdapter.this.changeCursor(cursor);
                    ConversationAdapter.this.mActivity
                            .setProgressBarIndeterminateVisibility(Boolean.FALSE);
                    return;
                default:
            }
        }
    }


    public ConversationAdapter(final Activity activity) {
        super(activity, R.layout.item_conversation_list, null, true);
        mActivity = activity;

        final ContentResolver contentResolver = activity.getContentResolver();
        queryHandler = new BackgroundQueryHandler(contentResolver);

        Cursor cursor = null;
        try {
            cursor = contentResolver.query(Conversation.URI_SIMPLE, Conversation.PROJECTION_SIMPLE,
                    Conversation.COUNT + ">0", null, null);
        } catch (SQLiteException e) {
            Logger.e(TAG, "error getting conversations %s", e);
        }

        if (cursor != null) {
            cursor.registerContentObserver(new ContentObserver(new Handler()) {
                @Override
                public void onChange(final boolean selfChange) {
                    super.onChange(selfChange);
                    if (!selfChange) {
                        Logger.d(TAG, "call startMsgListQuery();");
                        ConversationAdapter.this.startMsgListQuery();
                    }
                }
            });
        }
    }

    /**
     * Start ConversationList query.
     */
    public final void startMsgListQuery() {
        Logger.d(TAG, "startMsgListQuery()");
        // Cancel any pending queries
        queryHandler.cancelOperation(MESSAGE_LIST_QUERY_TOKEN);
        try {
            // Kick off the new query
            mActivity.setProgressBarIndeterminateVisibility(Boolean.TRUE);
            queryHandler.startQuery(MESSAGE_LIST_QUERY_TOKEN, null, Conversation.URI_SIMPLE,
                    Conversation.PROJECTION_SIMPLE, Conversation.COUNT + ">0", null, SORT);
        } catch (SQLiteException e) {
            Logger.e(TAG, "error starting query %s", e);
        }
    }

    @Override
    public final void bindView(final View view, final Context context, final Cursor cursor) {
        final Conversation conversation = Conversation.getConversation(context, cursor);

        ConverstationViewHolder converstationViewHolder = (ConverstationViewHolder) view.getTag();

        if (converstationViewHolder == null) {
            converstationViewHolder = new ConverstationViewHolder();
            initViewHolder(view, converstationViewHolder);
            view.setTag(converstationViewHolder);
        }

        setDateOnVieHolder(conversation, context, converstationViewHolder);
    }

    private void initViewHolder( View view, ConverstationViewHolder converstationViewHolder) {
        converstationViewHolder.mTvPerson = (TextView) view.findViewById(R.id.tvPerson);
        converstationViewHolder.mTvCount = (TextView) view.findViewById(R.id.tvCount);
        converstationViewHolder.mTvBody = (TextView) view.findViewById(R.id.tvBody);
        converstationViewHolder.mTvDate = (TextView) view.findViewById(R.id.tvDate);
    }

    private void setDateOnVieHolder(Conversation conversation, Context context, ConverstationViewHolder converstationViewHolder) {
        final int count = conversation.getCount();
        if (count < 0) {
            converstationViewHolder.mTvCount.setText("");
        } else {
            converstationViewHolder.mTvCount.setText("(" + conversation.getCount() + ")");
        }

        // person
        final Contact contact = conversation.getContact();
        contact.update(context, true, false);
        converstationViewHolder.mTvPerson.setText(contact.getDisplayName());

        // body
        CharSequence text = conversation.getBody();
        converstationViewHolder.mTvBody.setText(text);

        // date
        long time = conversation.getDate();
        converstationViewHolder.mTvDate.setText(ConversationListActivity.getDate(context, time));
    }

}
