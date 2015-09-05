package com.spoon.corelib.backend.events;

/**
 * Created by Lycha on 9/5/2015.
 */
public class SettingChangedEvent extends BaseEvent<SettingValue>  {

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
