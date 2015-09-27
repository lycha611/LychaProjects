package com.spoon.cookme.application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.spoon.cookme.backend.configuration.AppConfiguration;
import com.spoon.cookme.backend.events.EventBuses;
import com.spoon.cookme.backend.models.demo.DemoModel;
import com.spoon.cookme.backend.networking.state.NetworkStateMonitor;
import com.spoon.cookme.backend.utils.ParseUtils;
import com.spoon.corelib.CoreLib;
import com.spoon.corelib.application.BaseApplication;
import com.spoon.corelib.backend.developer.StethoIntegration;
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
                .setLoggerEnabled(AppConfiguration.shouldUseLogger())
                .setActivityLoggerEnabled(AppConfiguration.shouldLogActivityLifecycle())
                .setFragmentLoggerEnabled(AppConfiguration.shouldLogFragmentLifecycle())
                .init();

        if (AppConfiguration.shouldUseStetho()) {
            StethoIntegration.init(this);
        }

        JodaTimeAndroid.init(this);
        EventBuses.init();
        NetworkStateMonitor.init(this);

        BackgroundWorkerService.initialize(this, EventBuses.BackgroundWorker);

        //region parse subclasses register
        ParseObject.registerSubclass(DemoModel.class);
        //endregion

        Parse.enableLocalDatastore(getApplicationContext());

        ParseUtils.registerParse(this);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        ParseFacebookUtils.initialize(this);

    }

    public static synchronized ThisApplication get() {
        return (ThisApplication) BaseApplication.get();
    }

    private final static String CLIENT_ID = "android-native";

    public static String getClientId() {
        return CLIENT_ID;
    }


}
