package com.spoon.cookme.application;

import com.activeandroid.ActiveAndroid;
import com.spoon.cookme.backend.configuration.Configuration;
import com.spoon.cookme.backend.events.EventBuses;
import com.spoon.cookme.backend.networking.state.NetworkStateMonitor;
import com.spoon.corelib.CoreLib;
import com.spoon.corelib.application.BaseApplication;
import com.spoon.corelib.backend.services.BackgroundWorkerService;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Lycha on 9/5/2015.
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

        JodaTimeAndroid.init(this);
        EventBuses.init();
        NetworkStateMonitor.init(this);
        ActiveAndroid.initialize(this);

        BackgroundWorkerService.initialize(this, EventBuses.BackgroundWorker);
//        UpdaterService.initialize(this, EventBuses.VersionsAndUpdates);

    }

    //    private void initRestClient() {
//        mRestClient = new RestClient();//    }
//
//    public static RestClient getmRestClient() {
//        return mRestClient;
//    }
//
//    public static void setmRestClient(RestClient mRestClient) {
//        ThisApplication.mRestClient = mRestClient;
//    }

    public static ThisApplication get() {
        return (ThisApplication) BaseApplication.get();
    }

    private final static String CLIENT_ID = "android-native";

    public static String getClientId() {
        return CLIENT_ID;
    }


}
