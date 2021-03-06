package com.bluczak.corelib.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluczak.corelib.backend.annotations.ContentView;

import butterknife.ButterKnife;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class InjectionFragment extends LoggerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = setProperContentView(inflater, container);
        ButterKnife.inject(this, view);
        return view;
    }

    // override this method if needed
    protected View injectedFragmentContentView() {
        return null;
    }

    private View setProperContentView(LayoutInflater inflater, ViewGroup container) {
        final View view = injectedFragmentContentView();
        if (view == null) {
            final int resContentId = getContentViewIdFromAnnotations();
            if (resContentId != -1) {
                return inflater.inflate(resContentId, container, false);
            } else {
                e("BaseFragment subclasses should use either @ContentView annotation or override " +
                        "[protected View injectedFragmentContentView()] method!");
            }
        }
        return view;
    }

    private int getContentViewIdFromAnnotations() {
        ContentView annotation = getClass().getAnnotation(ContentView.class);
        if (annotation != null) {
            return annotation.value();
        }
        return -1;
    }

}
