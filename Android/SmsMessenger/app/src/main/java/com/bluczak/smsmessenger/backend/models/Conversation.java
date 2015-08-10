package com.bluczak.smsmessenger.backend.models;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;

import com.bluczak.smsmessenger.backend.utils.Logger;
import com.bluczak.smsmessenger.ui.activities.ConversationListActivity;

import java.util.LinkedHashMap;

import de.ub0r.android.lib.apis.Contact;

/**
 * Class holding a single conversation
 *
 * Created by BLuczak on 2015-06-24.
 */
public final class Conversation {

    private static final String TAG = "Conversation";

    private static final int CACHESIZE = 50;

    private static final LinkedHashMap<Integer, Conversation> CACHE
            = new LinkedHashMap<>(26, 0.9f, true);

    public static final Uri URI_SIMPLE = Uri.parse("content://mms-sms/conversations").buildUpon()
            .appendQueryParameter("simple", "true").build();

    private static final String ID = "_id";
    private static final String DATE = Calls.DATE;
    public static final String COUNT = "message_count";
    private static final String NID = "recipient_ids";
    private static final String BODY = "snippet";
    private static final String READ = "read";

    private static final int INDEX_SIMPLE_ID = 0;
    private static final int INDEX_SIMPLE_DATE = 1;
    private static final int INDEX_SIMPLE_COUNT = 2;
    private static final int INDEX_SIMPLE_NID = 3;
    private static final int INDEX_SIMPLE_BODY = 4;
    private static final int INDEX_SIMPLE_READ = 5;

    public static final String[] PROJECTION_SIMPLE = {
            ID, // 0
            DATE, // 1
            COUNT, // 2
            NID, // 3
            BODY, // 4
            READ, // 5
    };

    private int mId;
    private final int mThreadId;
    private Contact mContact;
    private long mDate;
    private String mBody;
    private int mRead;
    private int mCount = -1;

    private Conversation(final Context context, final Cursor cursor) {
        mThreadId = cursor.getInt(INDEX_SIMPLE_ID);
        mDate = cursor.getLong(INDEX_SIMPLE_DATE);
        mBody = cursor.getString(INDEX_SIMPLE_BODY);
        mRead = cursor.getInt(INDEX_SIMPLE_READ);
        mCount = cursor.getInt(INDEX_SIMPLE_COUNT);
        mContact = new Contact(cursor.getInt(INDEX_SIMPLE_NID));
    }

    private void update(final Context context, final Cursor cursor) {
        Logger.d(TAG, "update() %d", mThreadId);
        if (cursor == null || cursor.isClosed()) {
            Logger.e(TAG, "cursor null or closed");
            return;
        }
        long date = cursor.getLong(INDEX_SIMPLE_DATE);
        if (date != mDate) {
            mId = cursor.getInt(INDEX_SIMPLE_ID);
            mDate = date;
            mBody = cursor.getString(INDEX_SIMPLE_BODY);
        }
        mCount = cursor.getInt(INDEX_SIMPLE_COUNT);
        mRead = cursor.getInt(INDEX_SIMPLE_READ);
        final int nid = cursor.getInt(INDEX_SIMPLE_NID);
        if (nid != mContact.getRecipientId()) {
            mContact = new Contact(nid);
        }
    }

    public static Conversation getConversation(final Context context, final Cursor cursor) {
        Logger.d(TAG, "getConversation()");
        synchronized (CACHE) {
            Conversation ret = CACHE.get(cursor.getInt(INDEX_SIMPLE_ID));
            if (ret == null) {
                ret = new Conversation(context, cursor);
                CACHE.put(ret.getThreadId(), ret);
                Logger.d(TAG, "cachesize: %d", CACHE.size());
                while (CACHE.size() > CACHESIZE) {
                    Integer i = CACHE.keySet().iterator().next();
                    Logger.d(TAG, "rm con. from cache: %d", i);
                    Conversation cc = CACHE.remove(i);
                    if (cc == null) {
                        Logger.w(TAG, "CACHE might be inconsistent!");
                        break;
                    }
                }
            } else {
                ret.update(context, cursor);
            }
            return ret;
        }
    }

    public static Conversation getConversation(final Context context, final int threadId,
                                               final boolean forceUpdate) {
        Logger.d(TAG, "getConversation()");
        synchronized (CACHE) {
            Conversation ret = CACHE.get(threadId);
            if (ret == null || ret.getContact().getNumber() == null || forceUpdate) {
                Cursor cursor = context.getContentResolver().query(URI_SIMPLE, PROJECTION_SIMPLE,
                        ID + " = ?", new String[]{String.valueOf(threadId)}, null);
                if (cursor.moveToFirst()) {
                    ret = getConversation(context, cursor);
                } else {
                    Logger.e(TAG, "did not found conversation: %d", threadId);
                }
                cursor.close();
            }
            return ret;
        }
    }

    public Uri getUri() {
        return Uri.withAppendedPath(ConversationListActivity.URI, String.valueOf(mThreadId));
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getThreadId() {
        return mThreadId;
    }

    public Contact getContact() {
        return mContact;
    }

    public void setContact(Contact contact) {
        mContact = contact;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public int getRead() {
        return mRead;
    }

    public void setRead(int read) {
        mRead = read;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
