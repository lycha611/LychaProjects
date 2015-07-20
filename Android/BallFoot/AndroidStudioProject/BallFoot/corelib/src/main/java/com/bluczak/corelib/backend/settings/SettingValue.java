package com.bluczak.corelib.backend.settings;

import android.content.SharedPreferences;

import com.bluczak.corelib.backend.events.BaseEventBus;
import com.bluczak.corelib.backend.events.SettingChangedEvent;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by BLuczak on 2015-07-19.
 */
public abstract class SettingValue<T> {

    private static final AtomicLong mIdGenerator = new AtomicLong(1);

    protected final long mId;
    protected final T mDefaultValue;
    protected T mValue;
    protected final SettingGroup mParentGroup;
    protected final BaseEventBus mEventBus;
    protected final String mName;
    protected boolean mWasSet;
    protected boolean mShouldSendEventForNextChange = true;

    //region Public API for Setting Value
    public SettingValue(final String name, final SettingGroup parent, final BaseEventBus bus, final T defaultValue) {
        mId = getNextAvailableId();
        mName = name;
        mParentGroup = parent;
        mEventBus = bus;
        mDefaultValue = defaultValue;
        mValue = mDefaultValue;
        mWasSet = load();
        internalInitialization();

        if (mParentGroup != null) {
            mParentGroup.addSetting(this);
        }
    }

    public boolean isSet() {
        return mWasSet;
    }

    public T getValue() {
        if (!mWasSet && !load()) {
            return mDefaultValue;
        }
        else {
            return mValue;
        }
    }

    public T getDefaultValue() {
        return mDefaultValue;
    }

    public void setValue(final T newValue) {
        if (!mWasSet || differentThan(newValue)) {
            mValue = newValue;
            mWasSet = true;
            save();
        }
        // always reset this flag:
        mShouldSendEventForNextChange = true;
    }

    public void clear() {
        SharedPreferences.Editor editor = mParentGroup.getPreferences().edit();
        editor.remove(mName);
        editor.apply();
        mWasSet = false;
        mValue = mDefaultValue;
        sendChangedEvent();
        mShouldSendEventForNextChange = true;
    }

    public SettingValue<T> disableEventPropagationForNextChange() {
        mShouldSendEventForNextChange = false;
        return this;
    }

    public long getSettingId() {
        return mId;
    }

    public String getSettingName() {
        return mName;
    }

    public SettingGroup getParentSettingGroup() {
        return mParentGroup;
    }

    public void forceEventPropagation() {
        sendChangedEvent();
    }

    //endregion

    //region Private/Protected helper methods

    protected void internalInitialization() {

    }

    protected void save() {
        SharedPreferences.Editor editor = mParentGroup.getPreferences().edit();
        putValue(editor, mName, mValue);
        editor.apply();
        sendChangedEvent();
    }

    protected boolean load() {
        SharedPreferences prefs = mParentGroup.getPreferences();
        if (!prefs.contains(mName)) {
            return false;
        }

        mValue = getValue(prefs, mName);
        mWasSet = true;
        return true;
    }

    private void sendChangedEvent() {
        if (mEventBus == null || !mShouldSendEventForNextChange) return;

        SettingChangedEvent event = new SettingChangedEvent(this);
        mEventBus.postEvent(event);
    }

    private static long getNextAvailableId() {
        return mIdGenerator.getAndIncrement();
    }

    protected abstract void putValue(final SharedPreferences.Editor editor, final String name, final T value);
    protected abstract T getValue(final SharedPreferences prefs, final String name);
    protected abstract boolean differentThan(final Object otherValue);

    //endregion

}
