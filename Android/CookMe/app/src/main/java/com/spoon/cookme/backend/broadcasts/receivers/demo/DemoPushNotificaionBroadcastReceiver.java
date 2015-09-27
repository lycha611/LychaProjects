package com.spoon.cookme.backend.broadcasts.receivers.demo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import com.spoon.cookme.ui.activities.demo.DemoNotificationActivity;
import com.spoon.cookme.ui.notifications.demo.DemoNotification;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lycha on 9/19/2015.
 */
public class DemoPushNotificaionBroadcastReceiver extends ParsePushBroadcastReceiver {

    private final String TAG = DemoPushNotificaionBroadcastReceiver.class.getSimpleName();


    private DemoNotification mDemoNotification;

    private Intent mParseIntent;

    public DemoPushNotificaionBroadcastReceiver(){
        super();
    }


    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.e(TAG, "Push received: " + json);

            mParseIntent = intent;

            parsePushJson(context, json);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(Context context, JSONObject json) {
        try {
            boolean isBackground = json.getBoolean("is_background");
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");

            if (!isBackground) {
                Intent resultIntent = new Intent(context, DemoNotificationActivity.class);
                showNotificationMessage(context, title, message, resultIntent);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }


    /**
     * Shows the notification message in the notification bar
     * If the app is in background, launches the app
     *
     * @param context
     * @param title
     * @param message
     * @param intent
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {

        mDemoNotification = new DemoNotification(context);

        intent.putExtras(mParseIntent.getExtras());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        mDemoNotification.showNotificationMessage(title, message, intent);
    }

}
