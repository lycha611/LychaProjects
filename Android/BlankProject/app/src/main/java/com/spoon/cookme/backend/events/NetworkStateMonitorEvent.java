package com.spoon.cookme.backend.events;

import com.spoon.corelib.backend.events.BaseEvent;

/**
 * Created by Lycha on 9/7/2015.
 */
public class NetworkStateMonitorEvent extends BaseEvent<Boolean> {

    public NetworkStateMonitorEvent(final boolean active) {
        super(active);}

}
