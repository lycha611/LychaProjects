package com.bluczak.corelib.backend.events;

import com.bluczak.corelib.backend.settings.SettingValue;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class SettingChangedEvent extends BaseEvent<SettingValue> {

    private final String settingName;
    private final long settingId;

    public SettingChangedEvent(final SettingValue value) {
        super(value);
        settingId = value.getSettingId();
        settingName = value.getSettingName();
    }

    public long getSettingId() {
        return settingId;
    }

    public String getSettingName() {
        return settingName;
    }

    public long getGroupId() {
        return getValue().getParentSettingGroup().getGroupId();

    }
}
