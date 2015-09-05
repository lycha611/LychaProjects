package com.spoon.corelib.backend.settings;

import android.content.SharedPreferences;

import com.spoon.corelib.backend.events.BaseEventBus;

/**
 * Created by Lycha on 9/6/2015.
 */
public class SettingStringValue extends SettingValue<String>  {

    public SettingStringValue(final String name, final SettingGroup parent, final BaseEventBus bus, final String defaultValue) {
        super(name, parent, bus, defaultValue);
    }

    @Override
    protected void putValue(SharedPreferences.Editor editor, String name, String value) {
        editor.putString(name, value);
    }

    @Override
    protected String getValue(SharedPreferences prefs, String name) {
        return prefs.getString(name, mDefaultValue);
    }

    @Override
    protected boolean differentThan(Object otherValue) {
        return !mValue.equals(otherValue);
    }

}
