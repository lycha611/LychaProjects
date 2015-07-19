package com.bluczak.corelib.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class ExtendedLifecycleFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        onAttachBefore(activity);
        super.onAttach(activity);
    }

    public void onAttachBefore(Activity activity) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreateBefore(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    public void onCreateBefore(Bundle savedInstanceState) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onCreateViewBefore(inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onCreateViewBefore(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        onViewCreatedBefore(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
    }

    public void onViewCreatedBefore(View view, Bundle saveInstanceState) {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        onActivityCreatedBefore(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    public void onActivityCreatedBefore(Bundle savedInstanceState) {
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        onViewStateRestoredBefore(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    public void onViewStateRestoredBefore(Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
        onStartBefore();
        super.onStart();
    }

    public void onStartBefore() {
    }

    @Override
    public void onResume() {
        onResumeBefore();
        super.onResume();
    }

    public void onResumeBefore() {
    }

    @Override
    public void onPause() {
        onPauseBefore();
        super.onPause();
    }

    public void onPauseBefore() {
    }

    @Override
    public void onStop() {
        onStopBefore();
        super.onStop();
    }

    public void onStopBefore() {
    }

    @Override
    public void onDestroyView() {
        onDestroyViewBefore();
        super.onDestroyView();
    }

    public void onDestroyViewBefore() {
    }

    @Override
    public void onDestroy() {
        onDestroyBefore();
        super.onDestroy();
    }

    public void onDestroyBefore() {
    }

    @Override
    public void onDetach() {
        onDetachBefore();
        super.onDetach();
    }

    public void onDetachBefore() {
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResultBefore(intent, requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResultBefore(Intent intent, int requestCode) {}

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        startActivityForResultBefore(intent, requestCode, options);
        super.startActivityForResult(intent, requestCode, options);
    }

    public void startActivityForResultBefore(Intent intent, int requestCode, Bundle options) {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultBefore(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onActivityResultBefore(int requestCode, int resultCode, Intent data) {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        onSaveInstanceStateBefore(outState);
        super.onSaveInstanceState(outState);
    }

    public void onSaveInstanceStateBefore(Bundle outState) {
    }

}
