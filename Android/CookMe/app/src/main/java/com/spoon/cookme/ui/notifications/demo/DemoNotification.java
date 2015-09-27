package com.spoon.cookme.ui.notifications.demo;

import android.content.Context;

import com.spoon.cookme.R;
import com.spoon.cookme.ui.notifications.Notifications;
import com.spoon.corelib.ui.notifications.BaseNotification;

/**
 * Created by Lycha on 9/19/2015.
 */
public class DemoNotification extends BaseNotification {

    public static final int mNotificationIcon = R.mipmap.ic_launcher;

    public DemoNotification(Context mContext) {
        super(mContext, Notifications.DEMO_NOTIFICATION, mNotificationIcon);
    }


}
