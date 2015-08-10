package com.bluczak.albumofbeers.ui.activities.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bluczak.albumofbeers.backend.utils.Logger;

/**
 * Created by BLuczak on 2015-07-03.
 */
public class LoggerActivity extends Activity {

    private static boolean mLogLifecycle = false;

    public static void setLoggerEnabled(final boolean active) {
        mLogLifecycle = active;
    }

    protected boolean shouldLogLifecycle() {
        return mLogLifecycle;
    }

    //region Logging utilities helper functions
    protected final String TAG = getClass().getSimpleName();
    protected final Logger log = new Logger()
            .setDefaultTag(TAG);

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

    protected Logger lifecycle(Object... args) {
        if (shouldLogLifecycle()) {
            d(LIFECYCLE_FORMAT, args);
        }
        return log;
    }

    protected Logger resetIndentations() {
        return log.resetIndentations();
    }

    //endregion

    //region Basic lifecycle methods logging functionality

    protected static final String LIFECYCLE_FORMAT = "Activity\'s lifecycle method: %s called";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lifecycle("onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        log.resetIndentations();
    }

    @Override
    protected void onStart() {
        lifecycle("onStart()");
        super.onStart();
        log.resetIndentations();
    }

    @Override
    protected void onResume() {
        lifecycle("onResume()");
        super.onResume();
        log.resetIndentations();
    }

    @Override
    protected void onPause() {
        lifecycle("onPause()");
        super.onPause();
        log.resetIndentations();
    }

    @Override
    protected void onStop() {
        lifecycle("onStop()");
        super.onStop();
        log.resetIndentations();
    }

    @Override
    protected void onRestart() {
        lifecycle("onRestart()");
        super.onRestart();
        log.resetIndentations();
    }

    @Override
    protected void onDestroy() {
        lifecycle("onDestroy()");
        super.onDestroy();
        log.resetIndentations();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        lifecycle("onSaveInstanceState(Bundle)");
        super.onSaveInstanceState(outState);
        log.resetIndentations();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        lifecycle("onRestoreInstanceState(Bundle)");
        super.onRestoreInstanceState(savedInstanceState);
        log.resetIndentations();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        lifecycle("onActivityResult(" + requestCode + ", s" + resultCode + ", Intent)");
        super.onActivityResult(requestCode, resultCode, data);
        log.resetIndentations();
    }
    //endregion

    //region Toast functionality
    protected void toast(final String format, final Object...args) {
        final String txt = String.format(format, args);
        final Toast toast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
        toast.show();
    }
    //endregion

}
