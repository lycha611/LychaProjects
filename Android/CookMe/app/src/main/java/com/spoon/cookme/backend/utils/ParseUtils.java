package com.spoon.cookme.backend.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.spoon.cookme.backend.configuration.AppConfiguration;
import com.spoon.corelib.backend.utils.Logger;

/**
 * Created by Lycha on 9/19/2015.
 */
public class ParseUtils {

    private static String TAG = ParseUtils.class.getSimpleName();
    private static Logger mLog = new Logger();

    public static void verifyParseConfiguration(Context context) {
        if (TextUtils.isEmpty(AppConfiguration.PARSE_APPLICATION_ID) || TextUtils.isEmpty(AppConfiguration.PARSE_CLIENT_KEY)) {
            Toast.makeText(context, "Please configure your Parse Application ID and Client Key in AppConfig.java", Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
        }
    }

    public static void registerParse(Context context) {
        // initializing parse library
        Parse.initialize(context, AppConfiguration.PARSE_APPLICATION_ID, AppConfiguration.PARSE_CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground(AppConfiguration.PARSE_CHANNEL, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                mLog.e(TAG, "Successfully subscribed to Parse!");
            }
        });
    }

    public static void subscribeWithEmail(String email) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("email", email);
        installation.saveInBackground();
    }

}
