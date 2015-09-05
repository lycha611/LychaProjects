package com.spoon.corelib.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.spoon.corelib.backend.annotations.ContentView;

import butterknife.ButterKnife;

/**
 * Created by Lycha on 9/5/2015.
 */
public class InjectionActivity extends LoggerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setProperContentView();
        ButterKnife.bind(this);
    }

    // override this method if needed
    protected View injectedActivityContentView() {
        return null;
    }

    private void setProperContentView() {
        final View view = injectedActivityContentView();
        if(view == null) {
            final int resContentId = getContentViewIdFromAnnotations();
            if (resContentId != -1) {
                setContentView(resContentId);
            }
            else {
                e("BaseActivity subclasses should use either @ContentView annotation or override " +
                        "[protected View injectedActivityContentView()] method!");
                finish();
            }
        }
        else {
            setContentView(view);
        }
    }

    private int getContentViewIdFromAnnotations() {
        ContentView annotation = getClass().getAnnotation(ContentView.class);
        if(annotation != null) {
            return annotation.value();
        }
        return -1;
    }

}
