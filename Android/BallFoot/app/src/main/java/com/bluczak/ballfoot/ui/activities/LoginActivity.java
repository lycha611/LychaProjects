package com.bluczak.ballfoot.ui.activities;

import android.os.Bundle;
import android.widget.EditText;

import com.bluczak.ballfoot.R;
import com.bluczak.corelib.backend.annotations.ContentView;
import com.bluczak.corelib.ui.activities.BaseActivity;
import com.parse.ParseUser;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by BLuczak on 2015-07-19.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    //region View references
    @InjectView(R.id.et_email)
    EditText mEtEmail;
    @InjectView(R.id.et_password)
    EditText mEtPassword;
    //endregion


    //region UI interaction handlers
    @OnClick(R.id.btn_log_in)
    protected void logIn(){

    }

    @OnClick(R.id.btn_sign_up)
    protected void signUp(){
        ParseUser user = new ParseUser();
        user.set
    }
    //endregion


    //region Activity lifecycle callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
    //endregion


    //region Event handlers

    //endregion


    //region Private helper methods

    //endregion

}
