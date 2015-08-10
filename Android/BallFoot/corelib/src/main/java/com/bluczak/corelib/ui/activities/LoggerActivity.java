package com.bluczak.corelib.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bluczak.corelib.backend.utils.Logger;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class LoggerActivity extends ExtendedLifecycleActivity {

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
    protected void onCreateBefore(Bundle savedInstanceState) {
        lifecycle("onCreateBefore(Bundle)").indent();
        super.onCreateBefore(savedInstanceState);
    }

    @Override
    protected void onStart() {
        lifecycle("onStart()");
        super.onStart();
        log.resetIndentations();
    }

    @Override
    protected void onStartBefore() {
        lifecycle("onStartBefore()").indent();
        super.onStartBefore();
    }

    @Override
    protected void onResume() {
        lifecycle("onResume()");
        super.onResume();
        log.resetIndentations();
    }

    @Override
    protected void onResumeBefore() {
        lifecycle("onResumeBefore()").indent();
        super.onResumeBefore();
    }

    @Override
    protected void onPause() {
        lifecycle("onPause()");
        super.onPause();
        log.resetIndentations();
    }

    @Override
    protected void onPauseBefore() {
        lifecycle("onPauseBefore()").indent();
        super.onPauseBefore();
    }

    @Override
    protected void onStop() {
        lifecycle("onStop()");
        super.onStop();
        log.resetIndentations();
    }

    @Override
    protected void onStopBefore() {
        lifecycle("onStopBefore()").indent();
        super.onStopBefore();
    }

    @Override
    protected void onRestart() {
        lifecycle("onRestart()");
        super.onRestart();
        log.resetIndentations();
    }

    @Override
    protected void onRestartBefore() {
        lifecycle("onRestartBefore()").indent();
        super.onRestartBefore();
    }

    @Override
    protected void onDestroy() {
        lifecycle("onDestroy()");
        super.onDestroy();
        log.resetIndentations();
    }

    @Override
    protected void onDestroyBefore() {
        lifecycle("onDestroyBefore()").indent();
        super.onDestroyBefore();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        lifecycle("onSaveInstanceState(Bundle)");
        super.onSaveInstanceState(outState);
        log.resetIndentations();
    }

    @Override
    protected void onSaveInstanceStateBefore(Bundle outState) {
        lifecycle("onSaveInstanceStateBefore(Bundle)").indent();
        super.onSaveInstanceStateBefore(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        lifecycle("onRestoreInstanceState(Bundle)");
        super.onRestoreInstanceState(savedInstanceState);
        log.resetIndentations();
    }

    @Override
    protected void onRestoreInstanceStateBefore(Bundle savedInstanceState) {
        lifecycle("onRestoreInstanceStateBefore(Bundle)").indent();
        super.onRestoreInstanceStateBefore(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        lifecycle("onActivityResult(" + requestCode + ", s" + resultCode + ", Intent)");
        super.onActivityResult(requestCode, resultCode, data);
        log.resetIndentations();
    }

    @Override
    protected void onActivityResultBefore(int requestCode, int resultCode, Intent data) {
        lifecycle("onActivityResultBefore(" + requestCode + ", s" + resultCode + ", Intent)").indent();
        super.onActivityResultBefore(requestCode, resultCode, data);
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
