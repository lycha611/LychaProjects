package com.bluczak.smsmessenger.application;

import com.bluczak.smsmessenger.application.base.BaseApplication;
import com.bluczak.smsmessenger.backend.configuration.Configuration;
import com.bluczak.smsmessenger.developer.StethoIntegration;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by BLuczak on 2015-07-05.
 */
public class ThisApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        CoreLib.App.setLoggerEnabled(Configuration.shouldUseLogger())
                .setActivityLoggerEnabled(Configuration.shouldLogActivityLifecycle())
                .init();

        if (Configuration.shouldUseStetho()) {
            StethoIntegration.init(this);
        }
        JodaTimeAndroid.init(this);
    }

    public static ThisApplication get() {
        return (ThisApplication) BaseApplication.get();
    }

    private final static String CLIENT_ID = "android-native";

    public static String getClientId() {
        return CLIENT_ID;
    }

}
