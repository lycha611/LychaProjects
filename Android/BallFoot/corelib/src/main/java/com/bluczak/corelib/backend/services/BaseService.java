package com.bluczak.corelib.backend.services;

import android.app.Service;
import android.os.Handler;
import android.os.Looper;

import com.bluczak.corelib.backend.utils.Logger;

/**
 * Created by BLuczak on 2015-07-19.
 */
public abstract class BaseService extends Service {

    //region UI Thread Handler
    protected final Handler UiHandler = new Handler();
    //endregion

    //region Background Thread Handler
    protected static Handler BackgroundHandler;

    @Override
    public void onCreate()
    {
        super.onCreate();
        if(BackgroundHandler == null)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    Looper.prepare();
                    BackgroundHandler = new Handler();
                    Looper.loop();
                }
            }).start();
        }
    }

    //endregion


    //region Logging utilities
    private static boolean mUseLogging = true;

    public static void setLoggingEnabled(final boolean state) {
        mUseLogging = state;
        log.setEnabled(mUseLogging);
    }

    private static boolean isLoggingEnabled() {
        return mUseLogging;
    }

    protected static final Logger log = new Logger().setDefaultTag("");

    protected Logger i(final String fmt, final Object... args) {
        return log.info(null, fmt, args);
    }

    protected Logger d(final String fmt, final Object... args) {
        return log.debug(null, fmt, args);
    }

    protected Logger w(final String fmt, final Object... args) {
        return log.warning(null, fmt, args);
    }

    protected Logger e(final String fmt, final Object... args) {
        return log.error(null, fmt, args);
    }

    protected Logger e(final Throwable ex, final String fmt, final Object... args) {
        return log.error(null, ex, fmt, args);
    }

    protected Logger wtf(final String fmt, final Object... args) {
        return log.wtf(null, fmt, args);
    }

    //endregion

}
