package com.bluczak.corelib.backend.settings;

import android.content.SharedPreferences;

import com.bluczak.corelib.backend.events.BaseEventBus;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class SettingBooleanValue extends SettingValue<Boolean> {

    public SettingBooleanValue(String name, SettingGroup parent, BaseEventBus bus, Boolean defaultValue) {
        super(name, parent, bus, defaultValue);
    }

    @Override
    protected void putValue(SharedPreferences.Editor editor, String name, Boolean value) {
        editor.putBoolean(name, value);
    }

    @Override
    protected Boolean getValue(SharedPreferences prefs, String name) {
        return prefs.getBoolean(name, mDefaultValue);
    }

    @Override
    protected boolean differentThan(Object otherValue) {
        return !mValue.equals(otherValue);
    }


}
