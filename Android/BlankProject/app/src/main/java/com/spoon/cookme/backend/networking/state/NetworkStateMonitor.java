package com.spoon.cookme.backend.networking.state;

import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.spoon.cookme.backend.broadcasts.receivers.NetworkStateBroadcastReceiver;
import com.spoon.cookme.backend.events.EventBuses;
import com.spoon.cookme.backend.events.NetworkStateEvent;
import com.spoon.cookme.backend.events.NetworkStateMonitorEvent;
import com.spoon.corelib.backend.utils.Constants;

/**
 * Created by Lycha on 9/7/2015.
 */
public class NetworkStateMonitor {

    private static Context mContext;
    private static boolean mIsEnabled = false;
    private static IntentFilter mNetworkStateChangedFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

    public static void init(final Context context) {
        mContext = context;
        EventBuses.NetworkState.postStickyEvent(new NetworkStateMonitorEvent(isEnabled()));
        checkAndUpdateNetworkState();
    }

    public static boolean isEnabled() {
        synchronized (NetworkStateMonitor.class) {
            return NetworkStateBroadcastReceiver.isActive();
        }
    }

    public static void updateActiveState() {
        final boolean enabled = NetworkStateBroadcastReceiver.isActive();
        synchronized (NetworkStateMonitor.class) {
            if (mIsEnabled != enabled) {
                mIsEnabled = enabled;
                EventBuses.NetworkState.postStickyEvent(new NetworkStateMonitorEvent(mIsEnabled));
            }
        }
    }

    public static void enable() {
        synchronized (NetworkStateMonitor.class) {
            if (!mIsEnabled) {
                ComponentName receiver = new ComponentName(mContext, NetworkStateBroadcastReceiver.class);
                PackageManager pm = mContext.getPackageManager();
                pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                mIsEnabled = true;
            }
        }
        EventBuses.NetworkState.postStickyEvent(new NetworkStateMonitorEvent(mIsEnabled));
        checkNetworkAndSendEvent();
    }

    public static void disable() {
        synchronized (NetworkStateMonitor.class) {
            ComponentName receiver = new ComponentName(mContext, NetworkStateBroadcastReceiver.class);
            PackageManager pm = mContext.getPackageManager();
            pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            NetworkStateBroadcastReceiver.clearLastInstance();
            mIsEnabled = false;
        }
        EventBuses.NetworkState.postStickyEvent(new NetworkStateMonitorEvent(mIsEnabled));
    }

    private static Boolean mLastConnectionState = null;
    private static NetworkState.Type mLastConnectionType = null;

    private static NetworkState checkNetworkAndSendEvent() {
        NetworkState state = getCurrentNetworkState();
        if (mLastConnectionState == null ||
                mLastConnectionState != state.isConnected() ||
                mLastConnectionType == null ||
                mLastConnectionType != state.getConnectionType()) {
            mLastConnectionState = state.isConnected();
            mLastConnectionType = state.getConnectionType();
            NetworkStateEvent event = new NetworkStateEvent(state);
            EventBuses.NetworkState.postStickyEvent(event);
        }
        return state;
    }

    public static NetworkState checkAndUpdateNetworkState() {
        return checkNetworkAndSendEvent();
    }

    public static NetworkState getCurrentNetworkState() {
        final ConnectivityManager conMan = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        final boolean connected = netInfo != null && netInfo.isConnected();
        final boolean disconnected = !connected;
        final String whyConnectionFailed = netInfo != null ? netInfo.getReason() : Constants.EMPTY_STRING;
        NetworkState.Type type = NetworkState.Type.NONE;
        if (connected && netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI && netInfo.isConnectedOrConnecting())
            type = NetworkState.Type.WIFI;
        else if (connected && netInfo != null && netInfo.isConnectedOrConnecting())
            type = NetworkState.Type.GSM;
        NetworkState state = new NetworkState(connected, type);
        if (disconnected) state.setFailReason(whyConnectionFailed);
        return state;
    }



}
