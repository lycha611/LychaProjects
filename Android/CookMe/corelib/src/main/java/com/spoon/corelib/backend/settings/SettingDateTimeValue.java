package com.spoon.corelib.backend.settings;

import android.content.SharedPreferences;

import com.spoon.corelib.backend.events.BaseEventBus;

import org.joda.time.DateTime;
import org.joda.time.Instant;

/**
 * Created by Lycha on 9/6/2015.
 */
public class SettingDateTimeValue extends SettingValue<DateTime> {

    private final long mDefault;

    public SettingDateTimeValue(String name, SettingGroup parent, BaseEventBus bus, DateTime defaultValue) {
        super(name, parent, bus, defaultValue);
        mDefault = mDefaultValue.toInstant().getMillis();
    }

    @Override
    protected void putValue(SharedPreferences.Editor editor, String name, DateTime value) {
        editor.putLong(name, value.toInstant().getMillis());
    }

    @Override
    protected DateTime getValue(SharedPreferences prefs, String name) {
        return new Instant(prefs.getLong(name, mDefault)).toDateTime();
    }

    @Override
    protected boolean differentThan(Object otherValue) {
        return !mValue.equals(otherValue);
    }


}
