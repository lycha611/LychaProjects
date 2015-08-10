package com.bluczak.smsmessenger.ui.activities.base;

import android.os.Bundle;
import android.view.View;

import com.bluczak.smsmessenger.backend.annotations.ContentView;

import butterknife.ButterKnife;

/**
 * Created by BLuczak on 2015-07-03.
 */
public class InjectionActivity extends LoggerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setProperContentView();
        ButterKnife.inject(this);

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
