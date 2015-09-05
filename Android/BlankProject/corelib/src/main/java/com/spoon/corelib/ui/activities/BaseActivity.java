package com.spoon.corelib.ui.activities;

import android.os.Handler;

import org.joda.time.DateTime;

/**
 * Created by Lycha on 9/5/2015.
 */
public class BaseActivity extends EventBusAwareActivity {


    private Handler mUiHandler = new Handler();
    private Handler mBackgroundThread = null;

    protected Handler getUiHandler() {
        return mUiHandler;
    }

    protected Handler getBackgroundHandler() {
        if (mBackgroundThread == null) {
            Thread tmp = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    mBackgroundThread = new Handler();
                }
            });
            tmp.start();

            // UI waits for the thread - should take too long
            try
            {
                tmp.join();
            } catch (final Exception e) {
                // in worst case we will run something in UI thread
                return mUiHandler;
            }
        }
        return mBackgroundThread;
    }

    protected void postRunnable(final Runnable run) {
        getUiHandler().post(run);
    }

    protected void postRunnableAfter(final long ms, final Runnable run) {
        getUiHandler().postDelayed(run, ms);
    }

    protected void postRunnableAt(final DateTime time, final Runnable run) {
        getUiHandler().postAtTime(run, time.toInstant().getMillis());
    }

    protected void runInBackgroundThread(final Runnable run) {
        getBackgroundHandler().post(run);
    }


}
