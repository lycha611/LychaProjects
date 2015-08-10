package com.bluczak.smsmessenger.backend.configuration;

import com.bluczak.smsmessenger.BuildConfig;

/**
 * Created by BLuczak on 2015-07-05.
 */
public class Configuration {

    public static final boolean USE_LOGGING = BuildConfig.DEBUG;
    public static final boolean LOG_ACTIVITY_LIFECYCLE = BuildConfig.DEBUG;
    public static final boolean LOG_FRAGMENT_LIFECYCLE = BuildConfig.DEBUG;

    public static boolean shouldUseLogger() {
        return USE_LOGGING;
    }

    public static boolean shouldLogActivityLifecycle() {
        return shouldUseLogger() && LOG_ACTIVITY_LIFECYCLE;
    }

    public static boolean shouldLogFragmentLifecycle() {
        return shouldUseLogger() && LOG_FRAGMENT_LIFECYCLE;
    }

    public static boolean shouldUseStetho() {
        return BuildConfig.DEBUG;
    }

}
