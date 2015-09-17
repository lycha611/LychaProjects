package com.spoon.cookme.backend.events;

import com.spoon.corelib.backend.events.BaseEventBus;

/**
 * Created by Lycha on 9/6/2015.
 */
public class EventBuses {

    public static final int EVENT_BUS_GENERAL = 0;
    public static final int EVENT_BUS_SETTINGS = 1;
    public static final int EVENT_BUS_NETWORK_STATE = 2;
    public static final int EVENT_BUS_BACKGROUND_EXECUTION = 3;
    public static final int EVENT_BUS_LOCAL_DATA = 4;
    public static final int EVENT_BUS_VERSION_AND_UPDATES = 5;
    public static final int EVENT_BUS_SESSION = 6;

    public static final BaseEventBus General = new BaseEventBus("GeneralEventBus", EVENT_BUS_GENERAL);
    public static final BaseEventBus Settings = new BaseEventBus("SettingsEventBus", EVENT_BUS_SETTINGS);
    public static final BaseEventBus NetworkState = new BaseEventBus("NetworkStateEventBus", EVENT_BUS_NETWORK_STATE);
    public static final BaseEventBus BackgroundWorker = new BaseEventBus("BackgroundWorkerEventBus", EVENT_BUS_BACKGROUND_EXECUTION);
    public static final BaseEventBus LocalData = new BaseEventBus("LocalDataEventBus", EVENT_BUS_LOCAL_DATA);
    public static final BaseEventBus VersionsAndUpdates = new BaseEventBus("VersionsAndUpdatesEventBus", EVENT_BUS_VERSION_AND_UPDATES);
    public static final BaseEventBus Session = new BaseEventBus("SessionEventBus", EVENT_BUS_SESSION);

    public static void init() {
        // nothing to do here - it is enough that java loaded the class
        // java will take care of creating proper static objects
    }


}
