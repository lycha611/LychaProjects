package com.spoon.cookme.ui.activities.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.spoon.cookme.R;
import com.spoon.cookme.backend.models.demo.MessageDemo;
import com.spoon.cookme.backend.utils.ParseUtils;
import com.spoon.corelib.backend.session.BaseSession;
import com.spoon.corelib.ui.activities.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lycha on 9/19/2015.
 */
public class DemoNotificationActivity extends Activity {

    private static String TAG = DemoNotificationActivity.class.getSimpleName();

    private android.support.v7.widget.Toolbar mToolbar;
    private ListView listView;
    private List<MessageDemo> listMessages = new ArrayList<>();
    private MessageAdapter adapter;
    private BaseSession pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
//        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adapter = new MessageAdapter(this);
        pref = new BaseSession(getApplicationContext());

        listView.setAdapter(adapter);

        Intent intent = getIntent();

        String email = pref.getEmail();

        if (email != null) {
            ParseUtils.subscribeWithEmail(pref.getEmail());
        }else{
            Log.e(TAG, "Email is null. Not subscribing to parse!");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("message");

        MessageDemo m = new MessageDemo(message, System.currentTimeMillis());
        listMessages.add(0, m);
        adapter.notifyDataSetChanged();
    }

    private class MessageAdapter extends BaseAdapter {

        LayoutInflater inflater;

        public MessageAdapter(Activity activity) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            System.out.println("LIST SIZE : " + listMessages.size());
            return listMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return listMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.list_row, null);
            }

            TextView txtMessage = (TextView) view.findViewById(R.id.message);
            TextView txtTimestamp = (TextView) view.findViewById(R.id.timestamp);

            MessageDemo message = listMessages.get(position);
            txtMessage.setText(message.getMessage());

            CharSequence ago = DateUtils.getRelativeTimeSpanString(message.getTimestamp(), System.currentTimeMillis(),
                    0L, DateUtils.FORMAT_ABBREV_ALL);

            txtTimestamp.setText(String.valueOf(ago));

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            pref.logout();
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }




}
