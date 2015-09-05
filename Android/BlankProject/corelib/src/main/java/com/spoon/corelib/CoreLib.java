package com.spoon.corelib;

import com.spoon.corelib.backend.utils.Logger;
import com.spoon.corelib.ui.activities.LoggerActivity;
import com.spoon.corelib.ui.fragments.LoggerFragment;

/**
 * Created by Lycha on 9/5/2015.
 */
public class CoreLib {

    private boolean mUseLogger = false;
    private boolean mLogActivity = false;
    private boolean mLogFragments = false;

    public static CoreLib App = new CoreLib();

    public CoreLib setLoggerEnabled(final boolean active) {
        mUseLogger = active;
        return this;
    }

    public CoreLib setActivityLoggerEnabled(final boolean active) {
        mLogActivity = active;
        return this;
    }

    public CoreLib setFragmentLoggerEnabled(final boolean active) {
        mLogFragments = active;
        return this;
    }

    public void init() {
        Logger.setActiveState(mUseLogger);
        LoggerActivity.setLoggerEnabled(mLogActivity);
        LoggerFragment.setLoggerEnabled(mLogFragments);
    }

}
