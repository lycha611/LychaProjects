package com.spoon.corelib.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spoon.corelib.backend.utils.Logger;

/**
 * Created by Lycha on 9/5/2015.
 */
public class LoggerFragment extends ExtendedLifecycleFragment {

    // TODO: 9/17/2015 Change intents logger. Should be look like LoggerActivity
    
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

    protected static final String LIFECYCLE_FORMAT = "Fragment\'s lifecycle method: %s called";

    @Override
    public void onAttach(Activity activity) {
        lifecycle("onAttach(Activity)");
        super.onAttach(activity);
        log.resetIndentations();
    }

    @Override
    public void onAttachBefore(Activity activity) {
        lifecycle("onAttachBefore(Activity)").indent();
        super.onAttachBefore(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        lifecycle("onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        log.resetIndentations();
    }

    @Override
    public void onCreateBefore(Bundle savedInstanceState) {
        lifecycle("onCreateBefore(Bundle)").indent();
        super.onCreateBefore(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lifecycle("onCreateView(LayoutInflater, ViewGroup, Bundle)");
        log.resetIndentations();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void  onCreateViewBefore(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        lifecycle("onCreateViewBefore(LayoutInflater, ViewGroup, Bundle)").indent();
        super.onCreateViewBefore(inflater, container, savedInstanceStat);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lifecycle("onViewCreated(View, Bundle)");
        super.onViewCreated(view, savedInstanceState);
        log.resetIndentations();
    }

    @Override
    public void onViewCreatedBefore(View view, Bundle saveInstanceState) {
        lifecycle("onViewCreatedBefore(View, Bundle)").indent();
        super.onViewCreatedBefore(view, saveInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        lifecycle("onActivityCreated(Bundle)");
        super.onActivityCreated(savedInstanceState);
        log.resetIndentations();
    }

    @Override
    public void onActivityCreatedBefore(Bundle savedInstanceState) {
        lifecycle("onActivityCreatedBefore(Bundle)").indent();
        super.onActivityCreatedBefore(savedInstanceState);
    }

    @Override
    public void onStart() {
        lifecycle("onStart()");
        super.onStart();
        log.resetIndentations();
    }

    @Override
    public void onStartBefore() {
        lifecycle("onStartBefore()").indent();
        super.onStartBefore();
    }

    @Override
    public void onResume() {
        lifecycle("onResume()");
        super.onResume();
        log.resetIndentations();
    }

    @Override
    public void onResumeBefore() {
        lifecycle("onResumeBefore()").indent();
        super.onResumeBefore();
    }

    @Override
    public void onPause() {
        lifecycle("onPause()");
        super.onPause();
        log.resetIndentations();
    }

    @Override
    public void onPauseBefore() {
        lifecycle("onPauseBefore()").indent();
        super.onPauseBefore();
    }

    @Override
    public void onStop() {
        lifecycle("onStop()");
        super.onStop();
        log.resetIndentations();
    }

    @Override
    public void onStopBefore() {
        lifecycle("onStopBefore()").indent();
        super.onStopBefore();
    }

    @Override
    public void onDestroyView() {
        lifecycle("onDestroyView()");
        super.onDestroyView();
        log.resetIndentations();
    }

    @Override
    public void onDestroyViewBefore() {
        lifecycle("onDestroyViewBefore()").indent();
        super.onDestroyViewBefore();
    }

    @Override
    public void onDestroy() {
        lifecycle("onDestroy()");
        super.onDestroy();
        log.resetIndentations();
    }

    @Override
    public void onDestroyBefore() {
        lifecycle("onDestroyBefore()").indent();
        super.onDestroyBefore();
    }

    @Override
    public void onDetach() {
        lifecycle("onDetach()");
        super.onDetach();
        log.resetIndentations();
    }

    @Override
    public void onDetachBefore() {
        lifecycle("onDetachBefore()").indent();
        super.onDetachBefore();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        lifecycle("startActivityForResult(Intent, int)").indent();
        startActivityForResultBefore(intent, requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResultBefore(Intent intent, int requestCode) {
        lifecycle("onDetachBefore()").indent();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        lifecycle("startActivityForResultBefore(Intent, " + requestCode + ", " + options +  ")");
        super.startActivityForResult(intent, requestCode, options);
        log.resetIndentations();
    }

    @Override
    public void startActivityForResultBefore(Intent intent, int requestCode, Bundle options) {
        lifecycle("startActivityForResultBefore(Intent, " + requestCode + ", " + options +  ")").indent();
        super.startActivityForResultBefore(intent, requestCode, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        lifecycle("onActivityResult(" + requestCode + ", s" + resultCode + ", Intent)");
        super.onActivityResult(requestCode, resultCode, data);
        log.resetIndentations();

    }

    @Override
    public void onActivityResultBefore(int requestCode, int resultCode, Intent data) {
        lifecycle("onActivityResultBefore(" + requestCode + ", s" + resultCode + ", Intent)").indent();
        super.onActivityResultBefore(requestCode, resultCode, data);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        lifecycle("onViewStateRestored(Bundle)");
        super.onViewStateRestored(savedInstanceState);
        log.resetIndentations();
    }

    @Override
    public void onViewStateRestoredBefore(Bundle savedInstanceState) {
        lifecycle("onViewStateRestoredBefore(Bundle)").indent();
        super.onViewStateRestoredBefore(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        lifecycle("onSaveInstanceState(Bundle)");
        super.onSaveInstanceState(outState);
        log.resetIndentations();
    }

    @Override
    public void onSaveInstanceStateBefore(Bundle outState) {
        lifecycle("onSaveInstanceStateBefore(Bundle)").indent();
        super.onSaveInstanceStateBefore(outState);
    }

    //endregion

}
