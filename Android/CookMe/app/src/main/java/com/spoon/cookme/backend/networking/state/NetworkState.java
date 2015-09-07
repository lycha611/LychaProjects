package com.spoon.cookme.backend.networking.state;

/**
 * Created by Lycha on 9/7/2015.
 */
public class NetworkState {

    public enum Type {
        WIFI, GSM, NONE
    }

    private final boolean mIsConnected;
    private final Type mConnectionType;
    private String mFailReason;

    public NetworkState(final boolean connected, final Type connectionType) {
        mIsConnected = connected;
        mConnectionType = connectionType;
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public Type getConnectionType() {
        return mConnectionType;
    }

    public boolean isWifiConnected() {
        return mConnectionType == Type.WIFI;
    }

    public String getFailReason() {
        return mFailReason;
    }

    public void setFailReason(final String reason) {
        mFailReason = reason;
    }

    public boolean isGsmConnected() {
        return mConnectionType == Type.GSM;
    }

}
