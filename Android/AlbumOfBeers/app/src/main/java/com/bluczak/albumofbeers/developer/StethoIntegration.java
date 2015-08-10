package com.bluczak.albumofbeers.developer;

import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by BLuczak on 2015-07-03.
 */
public class StethoIntegration {

    private static boolean mInitialized = false;

    public static void init(final Context context) {
        if(mInitialized) return;

        Stetho.InitializerBuilder initBuilder = Stetho.newInitializerBuilder(context);
        initBuilder
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context));

        Stetho.initialize(initBuilder.build());

        mInitialized = true;
    }

}
