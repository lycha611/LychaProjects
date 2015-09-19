package com.spoon.corelib.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.spoon.corelib.backend.utils.Logger;

/**
 * Created by Lycha on 9/5/2015.
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
        log.resetIndentations();
        lifecycle("onCreate(Bundle)").indent();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onCreateBefore(Bundle savedInstanceState) {
        lifecycle("onCreateBefore(Bundle)");
        super.onCreateBefore(savedInstanceState);
    }

    @Override
    protected void onStart() {
        log.resetIndentations();
        lifecycle("onStart()").indent();
        super.onStart();


    }

    @Override
    protected void onStartBefore() {
        lifecycle("onStartBefore()");
        super.onStartBefore();
    }

    @Override
    protected void onResume() {
        log.resetIndentations();
        lifecycle("onResume()").indent();
        super.onResume();
    }

    @Override
    protected void onResumeBefore() {
        lifecycle("onResumeBefore()");
        super.onResumeBefore();
    }

    @Override
    protected void onPause() {
        log.resetIndentations();
        lifecycle("onPause()").indent();
        super.onPause();
    }

    @Override
    protected void onPauseBefore() {
        lifecycle("onPauseBefore()");
        super.onPauseBefore();
    }

    @Override
    protected void onStop() {
        log.resetIndentations();
        lifecycle("onStop()").indent();
        super.onStop();
    }

    @Override
    protected void onStopBefore() {
        lifecycle("onStopBefore()");
        super.onStopBefore();
    }

    @Override
    protected void onRestart() {
        log.resetIndentations();
        lifecycle("onRestart()").indent();
        super.onRestart();
    }

    @Override
    protected void onRestartBefore() {
        lifecycle("onRestartBefore()");
        super.onRestartBefore();
    }

    @Override
    protected void onDestroy() {
        log.resetIndentations();
        lifecycle("onDestroy()").indent();
        super.onDestroy();
    }

    @Override
    protected void onDestroyBefore() {
        lifecycle("onDestroyBefore()");
        super.onDestroyBefore();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        log.resetIndentations();
        lifecycle("onSaveInstanceState(Bundle)").indent();
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onSaveInstanceStateBefore(Bundle outState) {
        lifecycle("onSaveInstanceStateBefore(Bundle)");
        super.onSaveInstanceStateBefore(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        log.resetIndentations();
        lifecycle("onRestoreInstanceState(Bundle)").indent();
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceStateBefore(Bundle savedInstanceState) {
        lifecycle("onRestoreInstanceStateBefore(Bundle)");
        super.onRestoreInstanceStateBefore(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        log.resetIndentations();
        lifecycle("onActivityResult(" + requestCode + ", s" + resultCode + ", Intent)").indent();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onActivityResultBefore(int requestCode, int resultCode, Intent data) {
        lifecycle("onActivityResultBefore(" + requestCode + ", s" + resultCode + ", Intent)");
        super.onActivityResultBefore(requestCode, resultCode, data);
    }

    //endregion

    //region Toast functionality
    protected void toast(final String format, final Object... args) {
        final String txt = String.format(format, args);
        final Toast toast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
        toast.show();
    }
    //endregion

}
