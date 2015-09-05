package com.spoon.corelib.ui.fragments;

import com.spoon.corelib.backend.annotations.ListenOnEventBus;
import com.spoon.corelib.backend.annotations.ListenOnEventBusSticky;
import com.spoon.corelib.backend.events.BaseEventBus;
import com.spoon.corelib.ui.activities.InjectionActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lycha on 9/5/2015.
 */
public class EventBusAwareFragment extends InjectionActivity {

    private final Set<BaseEventBus> mBusesToMonitor = new HashSet<>();
    private final Set<BaseEventBus> mBusesToStickyMonitor = new HashSet<>();

    @Override
    public void onResume() {
        super.onResume();
        registerListeners();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterListeners();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBusesToMonitor.clear();
        mBusesToStickyMonitor.clear();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadEventBusesAnnotations();
    }

    private void loadEventBusesAnnotations() {
        ListenOnEventBus annotation = getClass().getAnnotation(ListenOnEventBus.class);
        if (annotation != null) {
            int[] ids = annotation.value();
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; ++i) {
                    BaseEventBus search = BaseEventBus.findEventBus(ids[i]);
                    if (search != null) {
                        mBusesToMonitor.add(search);
                    }
                    else {
                        e("Couldn't find event bus with id = %d", ids[i]);
                    }
                }
            }
        }

        ListenOnEventBusSticky sticky = getClass().getAnnotation(ListenOnEventBusSticky.class);
        if (sticky != null) {
            int[] ids = sticky.value();
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; ++i) {
                    BaseEventBus search = BaseEventBus.findEventBus(ids[i]);
                    if (search != null) {
                        mBusesToStickyMonitor.add(search);
                    }
                }
            }
        }

        final int s1 = mBusesToMonitor.size();
        final int s2 = mBusesToStickyMonitor.size();
        final int s3 = s1 + s2;
        i("Loaded %d number of Event Buses to be listened to. Count(sticky)=%d, Count(nonsticky)=%d", s3, s2, s1);
    }
    private void registerListeners() {
        int count = 0;
        for (BaseEventBus bus : mBusesToMonitor) {
            bus.registerListener(this);
            ++count;
        }
        for (BaseEventBus bus : mBusesToStickyMonitor) {
            bus.registerListener(this, true);
            ++count;
        }
        i("Registered at %d event buses.", count);
    }
    private void unregisterListeners() {
        for (BaseEventBus bus : mBusesToMonitor) {
            bus.unregisterListener(this);
        }
        for (BaseEventBus bus : mBusesToStickyMonitor) {
            bus.unregisterListener(this);
        }
    }

}
