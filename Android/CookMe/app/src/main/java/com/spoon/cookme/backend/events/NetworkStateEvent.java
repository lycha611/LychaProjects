package com.spoon.cookme.backend.events;

import com.spoon.cookme.backend.networking.state.NetworkState;
import com.spoon.corelib.backend.events.BaseEvent;

/**
 * Created by Lycha on 9/7/2015.
 */
public class NetworkStateEvent extends BaseEvent<NetworkState> {

    public NetworkStateEvent(final NetworkState state) {
        super(state);}

    public boolean isConnected() {
        return mValue.isConnected();}

    public NetworkState.Type getConnectionType() {
        return mValue.getConnectionType();}

    public boolean isWifiConnected() {
        return mValue.isWifiConnected();}

    public boolean isGsmConnected() {
        return mValue.isGsmConnected();}


}
