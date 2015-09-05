package com.spoon.corelib.backend.settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Lycha on 9/6/2015.
 */
public class SettingGroup {

    final static private AtomicLong mIdGenerator = new AtomicLong(0);
    protected ArrayList<SettingValue> mSettings = new ArrayList<>();
    private final String mName;
    private final Context mContext;
    private final long mId = mIdGenerator.getAndIncrement();

    public SettingGroup(final String name, final Context context) {
        mName = name;
        mContext = context;

        internalInitialization();
    }

    protected SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
    }

    public long getGroupId() {
        return mId;
    }

    public void addSetting(final SettingValue newSetting) {
        mSettings.add(newSetting);
    }

    public void clear() {
        for(final SettingValue setting : mSettings) {
            setting.clear();
        }
    }

    protected void internalInitialization() {

    }


}
