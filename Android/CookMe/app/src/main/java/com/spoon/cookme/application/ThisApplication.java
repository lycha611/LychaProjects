package com.spoon.cookme.application;

import com.spoon.cookme.backend.configuration.Configuration;
import com.spoon.corelib.CoreLib;
import com.spoon.corelib.application.BaseApplication;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Lycha on 9/5/2015.
 */
public class ThisApplication extends BaseApplication {


    public static final String APPLICATION_ID = "4w7B07hbHeegyBd26vpGs6VfFsMZJMtVM2g20Ik1";
    public static final String CLIENT_KEY = "zr12UaoIqOPQKHRFiYJof2jXXYqwyhXdE4fsbmuA";

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
//        NetworkStateMonitor.init(this);
        JodaTimeAndroid.init(this);
        BackgroundWorkerService.initialize(this, EventBuses.BackgroundWorker);
//        UpdaterService.initialize(this, EventBuses.VersionsAndUpdates);
//        Authentication.initialize(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
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
