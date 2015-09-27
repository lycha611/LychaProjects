package com.spoon.cookme.ui.activities.demo;

import android.os.Bundle;

import com.parse.ParseAnalytics;
import com.spoon.cookme.R;
import com.spoon.corelib.backend.annotations.ContentView;
import com.spoon.corelib.ui.activities.BaseActivity;

import butterknife.OnClick;

/**
 * Created by Lycha on 9/12/2015.
 */
@ContentView(R.layout.activity_demo_login)
public class DemoLoginActivity extends BaseActivity {

    //region fields

    //endregion

    //region UI interaction handlers
    @OnClick(R.id.btn_login_online)
    public void addDemoModelObject() {


    }

    @OnClick(R.id.btn_login_offline)
    public void loginOffline() {
    }

    //endregion

    //region Private helper method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}
