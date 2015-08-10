package com.bluczak.smsmessenger.application;

import com.bluczak.smsmessenger.backend.utils.Logger;
import com.bluczak.smsmessenger.ui.activities.base.LoggerActivity;

/**
 * Created by BLuczak on 2015-07-05.
 */
public class CoreLib {

    private boolean mUseLogger = false;
    private boolean mLogActivity = false;

    public static CoreLib App = new CoreLib();

    public CoreLib setLoggerEnabled(final boolean active) {
        mUseLogger = active;
        return this;
    }

    public CoreLib setActivityLoggerEnabled(final boolean active) {
        mLogActivity = active;
        return this;
    }

    public void init() {
        Logger.setActiveState(mUseLogger);
        LoggerActivity.setLoggerEnabled(mLogActivity);
    }

}
