package com.spoon.cookme.backend.configuration;

import com.spoon.cookme.BuildConfig;

/**
 * Created by Lycha on 9/5/2015.
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
