package com.spoon.cookme.backend.configuration;

import com.spoon.cookme.BuildConfig;

/**
 * Created by Lycha on 9/5/2015.
 */
public class Configuration {

    public static final boolean USE_LOGGING = BuildConfig.DEBUG;
    public static final boolean LOG_ACTIVITY_LIFECYCLE = BuildConfig.DEBUG;
    public static final boolean LOG_FRAGMENT_LIFECYCLE = BuildConfig.DEBUG;

    //Parse configuration
    public static final String PARSE_CHANNEL = "CookMe";
    public static final String PARSE_APPLICATION_ID = "NUHz1gEnmil3VtMWcW4hu7N1OcEdPsPOpuhBE1B9";
    public static final String PARSE_CLIENT_KEY = "Bz68dVJq6JD2OhpZhpi7LPj2HIkj2MtaOlzlN7Vn";

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
