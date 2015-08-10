package com.bluczak.corelib.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class ExtendedLifecycleActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityBeforeCreation();
        onCreateBefore(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    protected void onCreateBefore(Bundle savedInstanceState) {
    }

    protected void setupActivityBeforeCreation() {

    }

    @Override
    protected void onStart() {
        onStartBefore();
        super.onStart();
    }

    protected void onStartBefore() {
    }

    @Override
    protected void onResume() {
        onResumeBefore();
        super.onResume();
    }

    protected void onResumeBefore() {
    }

    @Override
    protected void onPause() {
        onPauseBefore();
        super.onPause();
    }

    protected void onPauseBefore() {
    }

    @Override
    protected void onStop() {
        onStopBefore();
        super.onStop();
    }

    protected void onStopBefore() {
    }

    @Override
    protected void onRestart() {
        onRestartBefore();
        super.onRestart();
    }

    protected void onRestartBefore() {
    }

    @Override
    protected void onDestroy() {
        onDestroyBefore();
        super.onDestroy();
    }

    protected void onDestroyBefore() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultBefore(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onActivityResultBefore(int requestCode, int resultCode, Intent data) {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        onRestoreInstanceStateBefore(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onRestoreInstanceStateBefore(Bundle savedInstanceState) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        onSaveInstanceStateBefore(outState);
        super.onSaveInstanceState(outState);
    }

    protected void onSaveInstanceStateBefore(Bundle outState) {
    }

}
