package com.spoon.corelib.backend.events;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Lycha on 9/5/2015.
 */
public class BaseEvent<T> {

    private static final AtomicInteger mIdGenerator = new AtomicInteger(1);
    private static int generateNextId() {
        return mIdGenerator.getAndIncrement();
    }

    protected final int mId;
    protected final T mValue;
    protected boolean mConsumed = false;

    public BaseEvent() {
        mId = generateNextId();
        mValue = null;
    }

    public BaseEvent(T value) {
        mId = generateNextId();
        mValue = value;
    }

    public int getEventId() {
        return mId;
    }

    public T getValue() {
        return mValue;
    }

    public String getEventClassName() {
        return getClass().getSimpleName();
    }

    public String getEventLogString() {
        return String.format("*** EVENT(%s.%d)", getEventClassName(), getEventId());
    }

    public boolean wasConsumed() {
        return mConsumed;
    }

    public void markAsConsumed() {
        mConsumed = true;
    }

    // really.. this function shouldn't be used in good software
    public void pukeIt() {
        mConsumed = false;
    }

    public static class RegisteredEvent extends BaseEvent<Boolean> {
        private final int mBusId;
        private final String mBusName;
        private final Object mTarget;
        private final boolean mSticky;

        public int getEventBusId() {
            return mBusId;
        }

        public String getEventBusName() {
            return mBusName;
        }

        public boolean didRegistered() {
            return mValue;
        }

        public boolean didUnregistered() {
            return !mValue;
        }

        public boolean wasRegisteredForStickyEvents() {
            return mSticky;
        }

        public static RegisteredEvent newRegisteredEvent(final BaseEventBus bus, final Object listener, final boolean stickyType) {
            return new RegisteredEvent(bus, listener, true, stickyType);
        }

        public static RegisteredEvent newUnregisteredEvent(final BaseEventBus bus, final Object listener) {
            return new RegisteredEvent(bus, listener, false, false);
        }

        private RegisteredEvent(final BaseEventBus bus, final Object listener, final boolean regUnreg, final boolean sticky) {
            super(regUnreg);
            mBusId = bus.getId();
            mBusName = bus.getName();
            mTarget = listener;
            mSticky = sticky;
        }
    }

}
