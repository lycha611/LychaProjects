package com.bluczak.corelib.backend.events;

import android.util.SparseArray;

import com.bluczak.corelib.backend.utils.Constants;
import com.bluczak.corelib.backend.utils.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import de.greenrobot.event.EventBus;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class BaseEventBus {

    private static final AtomicInteger mIdGenerator = new AtomicInteger(1);
    private static final String EMPTY_NAME = Constants.EMPTY_STRING;
    private static final String DEFAULT_LOG_TAG = "EventBus";
    private static final SparseArray<BaseEventBus> mBusesPerId = new SparseArray<>();
    private static final HashMap<String, BaseEventBus> mBusesPerName = new HashMap<>();
    private static final Logger mLog = new Logger();
    public static final int INVALID_BUS_ID = -1;
    public static final int NOT_AN_ID = -1;

    private final EventBus mBus;
    private final int mId;
    private final String mName;
    private boolean mIsValid = true;
    private Set<Object> mListeners = new HashSet<>();
    private final String mTag;


    //region Public API for Event Buses wrapper

    public BaseEventBus() {
        this(null);
    }

    public BaseEventBus(final String name) {
        this(name, NOT_AN_ID);
    }

    public BaseEventBus(final String name, final int dedicatedId) {
        mBus = EventBus.builder()
                .logNoSubscriberMessages(false)
                .logSubscriberExceptions(false)
                .sendNoSubscriberEvent(false)
                .sendSubscriberExceptionEvent(false)
                .build();
        mName = (name != null) ? name : EMPTY_NAME;
        mId = dedicatedId != NOT_AN_ID ? dedicatedId : getNextId();
        mTag = String.format("%s(%d)", mName.isEmpty() ? DEFAULT_LOG_TAG : mName, mId);
        addMeToGlobalLists();
    }

    public void invalidate() {
        removeMeFromGlobalLists();
        clearAndUnregisterAllListeners();
        mIsValid = false;
    }

    public void postEvent(final BaseEvent event) {
        mBus.post(event);
    }

    public void postStickyEvent(final BaseEvent event) {
        mBus.postSticky(event);
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int registerListener(final Object listener) {
        return registerListener(listener, false);
    }

    public int registerListener(final Object listener, final boolean stickyType) {
        if(mIsValid) {
            if (!mBus.isRegistered(listener) && !mListeners.contains(listener)) {
                mListeners.add(listener);
                if(stickyType) {
                    mBus.registerSticky(listener);
                }
                else {
                    mBus.register(listener);
                }

                mBus.post(BaseEvent.RegisteredEvent.newRegisteredEvent(this, listener, stickyType));
            }
            return getId();
        }
        else {
            return reportAccessToInvalidEventBusAndReturn();
        }
    }

    public boolean isRegistered(final Object askedObject) {
        return mBus.isRegistered(askedObject) && mListeners.contains(askedObject);
    }

    public int unregisterListener(final Object listener) {
        if(mIsValid) {
            if(isRegistered(listener)) {
                mBus.post(BaseEvent.RegisteredEvent.newUnregisteredEvent(this, listener));

                mBus.unregister(listener);
                mListeners.remove(listener);
            }

            return getId();
        }
        else {
            return reportAccessToInvalidEventBusAndReturn();
        }
    }

    public static BaseEventBus findEventBus(final int id) {
        return mBusesPerId.get(id);
    }

    public static BaseEventBus findEventBus(final String name) {
        return mBusesPerName.get(name);
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof BaseEventBus) {
            BaseEventBus tmp = (BaseEventBus)o;
            return tmp.getId() == getId();
        }
        return false;
    }

    //endregion

    //region Private helper functions

    private int getNextId() {
        int id = mIdGenerator.getAndIncrement();
        while (mBusesPerId.indexOfKey(id) >= 0) {
            id = mIdGenerator.getAndIncrement();
        }
        return id;
    }

    private void addMeToGlobalLists() {
        mBusesPerId.append(this.getId(), this);
        mBusesPerName.put(this.getName(), this);
    }

    private void removeMeFromGlobalLists() {
        mBusesPerId.remove(this.getId());
        mBusesPerName.remove(this.getName());
    }

    private void clearAndUnregisterAllListeners() {
        mBus.removeAllStickyEvents();
        for(final Object listener : mListeners) {
            unregisterListener(listener);
        }
        mListeners = new HashSet<>();
    }

    private int reportAccessToInvalidEventBusAndReturn() {
        mLog.error(mTag, "Trying to access invalid Event Bus(name=\"%s\", id=%d)", getName(), getId());
        return INVALID_BUS_ID;
    }

    //endregion

}
