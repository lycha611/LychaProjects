package com.spoon.cookme.ui.activities.demo;

import android.os.Bundle;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.spoon.cookme.R;
import com.spoon.corelib.backend.annotations.ContentView;
import com.spoon.corelib.ui.activities.BaseActivity;

import java.text.ParseException;
import java.util.HashMap;

import butterknife.OnClick;

/**
 * Created by Lycha on 9/27/2015.
 */
@ContentView(R.layout.activity_cloud_code_demo)
public class DemoCloudCodeActivity extends BaseActivity {

    @OnClick(R.id.btn_test_cloud_code)
    public void testCloudCode() {

        HashMap<String, Object> params = new HashMap<String, Object>();
        ParseCloud.callFunctionInBackground("hello", params, new FunctionCallback() {

            @Override
            public void done(Object o, Throwable throwable) {

            }

            @Override
            public void done(Object object, com.parse.ParseException e) {
                if (e == null) {
                    System.out.println("Poszlo");
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
