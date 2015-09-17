package com.spoon.cookme.ui.activities.demo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.spoon.cookme.R;
import com.spoon.cookme.backend.events.EventBuses;
import com.spoon.cookme.backend.events.NetworkStateEvent;
import com.spoon.cookme.backend.events.NetworkStateMonitorEvent;
import com.spoon.cookme.backend.networking.state.NetworkStateMonitor;
import com.spoon.corelib.backend.annotations.ContentView;
import com.spoon.corelib.backend.annotations.ListenOnEventBus;
import com.spoon.corelib.backend.annotations.ListenOnEventBusSticky;
import com.spoon.corelib.backend.events.BaseEvent;
import com.spoon.corelib.ui.activities.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Lycha on 9/7/2015.
 */

/**
 * TODO: remember to remove this activity, as it is only a demo for project's beginning
 */
@ListenOnEventBus({EventBuses.EVENT_BUS_GENERAL, EventBuses.EVENT_BUS_SETTINGS})
@ListenOnEventBusSticky(EventBuses.EVENT_BUS_NETWORK_STATE)
@ContentView(R.layout.activity_demo)
public class DemoActivity extends BaseActivity {

    //region View references
    @Bind(R.id.lbl_network_connected)
    protected TextView tvNetworkConnected;
    @Bind(R.id.lbl_network_monitor_active)
    protected TextView tvNetworkMonitorEnabled;
    //endregion

    //region Activity lifecycle callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onCreateBefore(Bundle savedInstanceState) {
        super.onCreateBefore(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    //endregion

    //region UI interaction handlers
    @OnClick(R.id.btn_enable_network_state_monitor)
    public void enableNetworkStateMonitor() {
        NetworkStateMonitor.enable();
    }

    @OnClick(R.id.btn_disable_network_state_monitor)
    public void disabelNetworkStateMonitor() {
        NetworkStateMonitor.disable();
    }
    //endregion

    //region Private helper methods

    //endregion


}
