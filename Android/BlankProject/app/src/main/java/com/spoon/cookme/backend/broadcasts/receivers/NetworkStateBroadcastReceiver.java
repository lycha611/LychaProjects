package com.spoon.cookme.backend.broadcasts.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.spoon.cookme.backend.networking.state.NetworkState;
import com.spoon.cookme.backend.networking.state.NetworkStateMonitor;
import com.spoon.corelib.backend.utils.Logger;

/**
 * Created by Lycha on 9/7/2015.
 */
public class NetworkStateBroadcastReceiver extends BroadcastReceiver {

    private static NetworkStateBroadcastReceiver mLastInstance = null;
    private static boolean mIsActive = false;
    private static final String TAG = "NetworkState BroadcastReceiver";

    public NetworkStateBroadcastReceiver() {
        super();
        mIsActive = true;
        mLastInstance = this;
        NetworkStateMonitor.updateActiveState();
    }

    public static NetworkStateBroadcastReceiver getLastInstance() {
        return mLastInstance;
    }

    public static void clearLastInstance() {
        mLastInstance = null;
        mIsActive = false;
    }

    public static boolean isActive() {
        return mIsActive;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkState state = NetworkStateMonitor.checkAndUpdateNetworkState();
        Logger.d(TAG, "Network state has changed. It is now: %s on network type: %s", state.isConnected() ? "connected" : "disconnected", state.getConnectionType().toString());
    }

}
