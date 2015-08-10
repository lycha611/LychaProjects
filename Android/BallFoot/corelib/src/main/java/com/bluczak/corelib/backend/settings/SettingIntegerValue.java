package com.bluczak.corelib.backend.settings;

import android.content.SharedPreferences;

import com.bluczak.corelib.backend.events.BaseEventBus;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class SettingIntegerValue extends SettingValue<Integer> {

    public SettingIntegerValue(String name, SettingGroup parent, BaseEventBus bus, Integer defaultValue) {
        super(name, parent, bus, defaultValue);
    }

    @Override
    protected void putValue(SharedPreferences.Editor editor, String name, Integer value) {
        editor.putInt(name, value);
    }

    @Override
    protected Integer getValue(SharedPreferences prefs, String name) {
        return prefs.getInt(name, mDefaultValue);
    }

    @Override
    protected boolean differentThan(Object otherValue) {
        return !mValue.equals(otherValue);
    }

}
