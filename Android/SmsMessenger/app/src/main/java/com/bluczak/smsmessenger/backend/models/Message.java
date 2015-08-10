package com.bluczak.smsmessenger.backend.models;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog.Calls;

import com.bluczak.smsmessenger.backend.utils.Logger;

import java.util.LinkedHashMap;

/**
 * Created by BLuczak on 2015-06-26.
 */
public final class Message {

    private static final String TAG = "Message";

    private static final int CAHCESIZE = 50;

    private static final LinkedHashMap<Integer, Message> CACHE
            = new LinkedHashMap<>(
            26, 0.9f, true);

    public static final int INDEX_ID = 0;
    public static final int INDEX_READ = 1;
    public static final int INDEX_DATE = 2;
    public static final int INDEX_THREADID = 3;
    public static final int INDEX_TYPE = 4;
    public static final int INDEX_ADDRESS = 5;
    public static final int INDEX_BODY = 6;
    public static final int INDEX_SUBJECT = 7;
    public static final int INDEX_MTYPE = 8;

    public static final String[] PROJECTION = { //
            "_id", // 0
            "read", // 1
            Calls.DATE, // 2
            "thread_id", // 3
            Calls.TYPE, // 4
            "address", // 5
            "body", // 6
    };

    public static final String[] PROJECTION_SMS = { //
            PROJECTION[INDEX_ID], // 0
            PROJECTION[INDEX_READ], // 1
            PROJECTION[INDEX_DATE], // 2
            PROJECTION[INDEX_THREADID], // 3
            PROJECTION[INDEX_TYPE], // 4
            PROJECTION[INDEX_ADDRESS], // 5
            PROJECTION[INDEX_BODY], // 6
    };

    public static final String[] PROJECTION_JOIN = { //
            PROJECTION[INDEX_ID], // 0
            PROJECTION[INDEX_READ], // 1
            PROJECTION[INDEX_DATE], // 2
            PROJECTION[INDEX_THREADID], // 3
            PROJECTION[INDEX_TYPE], // 4
            PROJECTION[INDEX_ADDRESS], // 5
            PROJECTION[INDEX_BODY], // 6
            "sub", // 7
            "m_type", // 8
    };

    public static final String SORT_USD = Calls.DATE + " ASC";

    public static final int SMS_IN = Calls.INCOMING_TYPE;

    public static final int SMS_OUT = Calls.OUTGOING_TYPE;

    public static final int SMS_DRAFT = 3;

    private final long mId;
    private final long mThreadId;
    private long mDate;
    private String mAddress;
    private CharSequence mBody;
    private int mType;
    private int mRead;
    private String mSubject = null;
    private Intent mContentIntent = null;

    private Message(final Cursor cursor) {
        mId = cursor.getLong(INDEX_ID);
        mThreadId = cursor.getLong(INDEX_THREADID);
        mDate = cursor.getLong(INDEX_DATE);
        if (cursor.getColumnIndex(PROJECTION_JOIN[INDEX_TYPE]) >= 0) {
            mAddress = cursor.getString(INDEX_ADDRESS);
            mBody = cursor.getString(INDEX_BODY);
        } else {
            mBody = null;
            mAddress = null;
        }
        mType = cursor.getInt(INDEX_TYPE);
        mRead = cursor.getInt(INDEX_READ);

        try {
            mSubject = cursor.getString(INDEX_SUBJECT);
        } catch (IllegalStateException e) {
            mSubject = null;
        }
        try {
            if (cursor.getColumnCount() > INDEX_MTYPE) {
                final int t = cursor.getInt(INDEX_MTYPE);
                if (t != 0) {
                    mType = t;
                }
            }
        } catch (IllegalStateException e) {
            mSubject = null;
        }

        Logger.d(TAG, "ThreadId: %d", mThreadId);
        Logger.d(TAG, "mAddress: %d", mAddress);
        Logger.d(TAG, "mSubject: ", mSubject);
        Logger.d(TAG, "mBody: ", mBody);
        Logger.d(TAG, "mType: ", mType);
    }

    public void update(final Cursor cursor) {
        mRead = cursor.getInt(INDEX_READ);
        mType = cursor.getInt(INDEX_TYPE);
        try {
            if (cursor.getColumnCount() > INDEX_MTYPE) {
                final int t = cursor.getInt(INDEX_MTYPE);
                if (t != 0) {
                    mType = t;
                }
            }
        } catch (IllegalStateException e) {
            Logger.e(TAG, "wrong projection? %s", e);
        }
    }

    public static Message getMessage(final Context context, final Cursor cursor) {
        synchronized (CACHE) {
            String body = cursor.getString(INDEX_BODY);
            int id = cursor.getInt(INDEX_ID);
            if (body == null) { // MMS
                id *= -1;
            }
            Message ret = CACHE.get(id);
            if (ret == null) {
                ret = new Message(cursor);
                CACHE.put(id, ret);
                Logger.d(TAG, "cachesize: ", CACHE.size());
                while (CACHE.size() > CAHCESIZE) {
                    Integer i = CACHE.keySet().iterator().next();
                    Logger.d(TAG, "rm msg. from cache: ", i);
                    Message cc = CACHE.remove(i);
                    if (cc == null) {
                        Logger.w(TAG, "CACHE might be inconsistent!");
                        break;
                    }
                }
            } else {
                ret.update(cursor);
            }
            return ret;
        }
    }

    public static void flushCache() {
        synchronized (CACHE) {
            CACHE.clear();
        }
    }

    public long getId() {
        return mId;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public long getThreadId() {
        return mThreadId;
    }

    public CharSequence getBody() {
        return mBody;
    }

    public void setBody(CharSequence body) {
        mBody = body;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getRead() {
        return mRead;
    }

    public void setRead(int read) {
        mRead = read;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public Intent getContentIntent() {
        return mContentIntent;
    }

    public void setContentIntent(Intent contentIntent) {
        mContentIntent = contentIntent;
    }
    
}
