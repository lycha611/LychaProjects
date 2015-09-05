package com.bluczak.smsmessenger.backend.device;

import android.provider.Settings;

import com.bluczak.smsmessenger.application.base.BaseApplication;

/**
 * Created by BLuczak on 2015-07-05.
 */
public class DeviceId {

    private DeviceId() {

    }

    private static String mDeviceId = null;
    private static int mDeviceIdHash = -1;
    public static String get() {
        if (mDeviceId == null) {
            mDeviceId = Settings.Secure.getString(BaseApplication.getMainContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            mDeviceIdHash = mDeviceId.hashCode();
        }
        return mDeviceId;
    }

}
