package com.bluczak.ballfoot.application;

import com.bluczak.ballfoot.backend.configuration.Configuration;
import com.bluczak.corelib.CoreLib;
import com.bluczak.corelib.application.BaseApplication;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by BLuczak on 2015-07-14.
 */
public class ThisApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // setup core lib, as some of the things we can get only in final application
        // libs are always in release mode
        CoreLib.App
                .setLoggerEnabled(Configuration.shouldUseLogger())
                .setActivityLoggerEnabled(Configuration.shouldLogActivityLifecycle())
                .setFragmentLoggerEnabled(Configuration.shouldLogFragmentLifecycle())
                .init();

        EventBuses.init();
        Settings.init(this);
        NetworkStateMonitor.init(this);
        JodaTimeAndroid.init(this);
        BackgroundWorkerService.initialize(this, EventBuses.BackgroundWorker);
        UpdaterService.initialize(this, EventBuses.VersionsAndUpdates);
        Authentication.initialize(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "4w7B07hbHeegyBd26vpGs6VfFsMZJMtVM2g20Ik1", "zr12UaoIqOPQKHRFiYJof2jXXYqwyhXdE4fsbmuA");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

    public static ThisApplication get() {
        return (ThisApplication) BaseApplication.get();
    }

    private final static String CLIENT_ID = "android-native";

    public static String getClientId() {
        return CLIENT_ID;
    }


}
