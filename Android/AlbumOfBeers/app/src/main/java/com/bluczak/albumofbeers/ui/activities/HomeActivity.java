package com.bluczak.albumofbeers.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bluczak.albumofbeers.R;
import com.bluczak.albumofbeers.backend.annotations.ContentView;
import com.bluczak.albumofbeers.backend.controllers.Controllers;
import com.bluczak.albumofbeers.backend.models.BeerModel;
import com.bluczak.albumofbeers.ui.activities.base.InjectionActivity;
import com.bluczak.albumofbeers.ui.adapters.BeersAdapter;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnItemClick;


@ContentView(R.layout.activity_home)
public class HomeActivity extends InjectionActivity {

    //region UI view references
    @InjectView(R.id.lv_beers)
    ListView mLvBeers;
    //endregion

    //region UI interaction handlers
    @OnItemClick(R.id.lv_beers)
    public void onItemLvBeersClick(int position) {
        BeerModel beerModel = (BeerModel) mLvBeers.getAdapter().getItem(position);
        long id = beerModel.getId();
        Intent intent = new Intent(this, BeerActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    //endregion

    //region Fields
    private ArrayList<BeerModel> mBeers;
    private BeersAdapter mBeersAdapter;
    //endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
            case R.id.action_add_beer:
                startActivity(new Intent(this, NewBeerActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //region Activity lifecycle callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputDataToList();
        setAdapterList();
    }

    //endregion


    //region Private helper methods
    private void createList() {
        mBeers = new ArrayList<BeerModel>();
    }

    private void inputDataToList() {
        mBeers = (ArrayList<BeerModel>) Controllers.BeerController.getAll();
    }


    private void setAdapterList() {
        mBeersAdapter = new BeersAdapter(this, mBeers);
        mLvBeers.setAdapter(mBeersAdapter);
    }
    //endregion
}
