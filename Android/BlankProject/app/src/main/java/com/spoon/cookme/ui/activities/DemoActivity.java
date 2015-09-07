package com.spoon.cookme.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.spoon.cookme.R;
import com.spoon.cookme.backend.events.EventBuses;
import com.spoon.cookme.backend.events.NetworkStateEvent;
import com.spoon.cookme.backend.networking.state.NetworkState;
import com.spoon.cookme.backend.networking.state.NetworkStateMonitor;
import com.spoon.corelib.backend.annotations.ContentView;
import com.spoon.corelib.backend.annotations.ListenOnEventBus;
import com.spoon.corelib.backend.annotations.ListenOnEventBusSticky;
import com.spoon.corelib.backend.utils.Logger;
import com.spoon.corelib.ui.activities.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Lycha on 9/7/2015.
 */

/**
 * TODO: remember to remove this activity, as it is only a demo for project's beginning
 */
@ListenOnEventBus({EventBuses.EVENT_BUS_GENERAL, EventBuses.EVENT_BUS_SETTINGS, EventBuses.EVENT_BUS_VOLATILE_MEMORY})
@ListenOnEventBusSticky(EventBuses.EVENT_BUS_NETWORK_STATE)
@ContentView(R.layout.activity_demo)
public class DemoActivity extends BaseActivity {

    //region View references
    @Bind(R.id.text_view)
    protected TextView textView;
    @Bind(R.id.lbl_network_connected)
    protected TextView tvNetworkConnected;
    @Bind(R.id.lbl_network_monitor_active)
    protected TextView tvNetworkMonitorEnabled;
    @Bind(R.id.tv_click_counter)
    protected TextView tvClickCounter;
    @Bind(R.id.tv_last_update_time)
    protected TextView tvLastUpdateTime;
    @Bind(R.id.tv_latitude)
    protected TextView tvLatitude;
    @Bind(R.id.tv_longitude)
    protected TextView tvLongitude;
    @Bind(R.id.btn_disable_gps_location)
    protected Button btnDisableGpsLocation;
    @Bind(R.id.btn_enable_gps_location)
    protected Button btnEnableGpsLocation;
    //endregion
// UI Widgets.
//endregion
    private LocationGpsMonitor locationGpsMonitor;

    //region Activity lifecycle callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.

                onCreate(savedInstanceState);

        d("From top-level onCreate()");

        textView.setText("other text from inside of the code!");
        locationGpsMonitor = new

                LocationGpsMonitor();
    }

    @Override
    protected void onCreateBefore(Bundle savedInstanceState) {
        super.onCreateBefore(savedInstanceState);
        d("From top-level onCreateBefore()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onResumeBefore() {
        super.onResumeBefore();
        d("1. text");
        d("2. text");
        d("3. text").indent();
        d("3.1. subtext").unindent();
        d("Finito of onResumeBefore()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        e("From onResume() function");
    }

    @Override
    protected void onStop() {
        super.onStop();
        spiceManager.shouldStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.

                onOptionsItemSelected(item);
    }

//endregion

//region Event handlers

    public void onEvent(final NetworkStateMonitorEvent event) {
        if (event.getValue()) {
            d("%s: Network State Monitor is enabled!", event.getEventLogString());
            tvNetworkMonitorEnabled.setText("Network Monitor enabled");
        } else {
            d("%s: Network State Monitor is disabled!", event.getEventLogString());
            tvNetworkMonitorEnabled.setText("Network Monitor disabled");
        }
    }

    public void onEvent(final SettingChangedEvent event) {
        if (event.getSettingId() == Settings.General.ClickCounter.getSettingId()) {
            // it is something we are interested in
            updateClickCounter(Settings.General.ClickCounter.getValue());
            d("%s: Received change event for setting: ID=%d NAME=%s", event.getEventLogString(), event.getSettingId(), event.getSettingName());
        }
    }

    public void onEvent(final BaseEvent.RegisteredEvent event) {
        d("%s: Activity was %s bus %s (id=%d) Sticky?=>%b", event.getEventLogString(), event.didRegistered() ? "registered to" : "unregistered from", event.getEventBusName(), event.getEventBusId(), event.wasRegisteredForStickyEvents());
    }

    public void onEvent(final NetworkStateEvent event) {
        d("%s: Network is %s.", event.getEventLogString(), event.isConnected() ? "connected" : "disconnected");
        if (event.isConnected()) {
            tvNetworkConnected.setText("Network is active and connected");
        } else {
            tvNetworkConnected.setText("Network is not available!");
        }
    }

    public void onEvent(final VolatileMemoryChangedEvent event) {
        d("%s: volatile memory has changed. You can reload whatever you need. Counter: %d", event.getEventLogString(), VolatileMemory.getMemory().expires);
    }

    //endregion
//region UI interaction handlers
    @OnClick(R.id.text_view)
    public void textClicked() {
        int count = Settings.General.ClickCounter.getValue();
        d("Text was clicked %d number of times!", ++count);
        VolatileMemory.getMemory().expires++;
        VolatileMemory.commitAndSignalChanges();
        Settings.General.ClickCounter.setValue(count);
        log.indent().debug(TAG, "Something indented...").unindent();
        log.setAppendTimestampTo(true).warning(null, "come back from indentations").setAppendTimestampTo(false);
        Logger.e(TAG, "It should also work with global static %s", "logger");
        EventBuses.NetworkState.postStickyEvent(new NetworkStateEvent(new NetworkState(false, NetworkState.Type.NONE)));
    }

    @OnClick(R.id.tv_click_counter)
    public void clearClickCounter() {
        VolatileMemory.getMemory().expires = 666;
        VolatileMemory.commitAndSignalChanges();
        Settings.General.ClickCounter.clear();
    }

    @OnClick(R.id.btn_enable_network_state_monitor)
    public void enableNetworkStateMonitor() {
        NetworkStateMonitor.enable();
    }

    @OnClick(R.id.btn_disable_network_state_monitor)
    public void disabelNetworkStateMonitor() {
        NetworkStateMonitor.disable();
    }

    @OnClick(R.id.btn_enable_gps_location)
    public void enableGpsLocation() {
        btnEnableGpsLocation.setEnabled(false);
        btnDisableGpsLocation.setEnabled(true);
        Gps.enableGPS(getApplicationContext());
        locationGpsMonitor.enable(tvLastUpdateTime, tvLatitude, tvLongitude);
    }

    @OnClick(R.id.btn_disable_gps_location)
    public void disableGpsLocation() {
        btnEnableGpsLocation.setEnabled(true);
        btnDisableGpsLocation.setEnabled(false);
        Gps.disableGPS(getApplicationContext());
        locationGpsMonitor.disable();
    }

    //endregion
//region Private helper methods

//endregion


}
