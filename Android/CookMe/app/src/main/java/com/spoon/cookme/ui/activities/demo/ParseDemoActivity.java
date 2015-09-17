package com.spoon.cookme.ui.activities.demo;

import android.os.Bundle;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.spoon.cookme.R;
import com.spoon.cookme.backend.controllers.Controllers;
import com.spoon.cookme.backend.controllers.DemoController;
import com.spoon.cookme.backend.models.DemoModel;
import com.spoon.corelib.backend.annotations.ContentView;
import com.spoon.corelib.backend.utils.Logger;
import com.spoon.corelib.ui.activities.BaseActivity;

import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import butterknife.OnItemClick;


/**
 * Created by Lycha on 9/12/2015.
 */
@ContentView(R.layout.activity_parse_demo)
public class ParseDemoActivity extends BaseActivity {

    //region fields
    private int itemNumber = 0;

    private SaveCallback mSaveCallback = null;
    private GetCallback<DemoModel> mGetCallback = null;
    private FindCallback<DemoModel> mFindCallback = null;
    private DeleteCallback mDeleteCallback = null;
    //endregion

    //region UI interaction handlers

    //SAVE OPERATIONS
    @OnClick(R.id.btn_save_item_in_backgrond)
    public void saveItemInBackground() {
        Controllers.DemoController.saveItemInBackground(generateNewDemoModelObject());
    }

    @OnClick(R.id.btn_save_item_in_backgrond_c)
    public void saveItemInBackgroundC() {
        DemoModel demoModel = generateNewDemoModelObject();
        Controllers.DemoController.saveItemInBackground(demoModel, createNewSaveCallback(demoModel));
    }

    @OnClick(R.id.btn_save_all_items_in_background)
    public void saveAllItemsInBackground() {
        List<DemoModel> demoModels = new ArrayList<DemoModel>();
        demoModels.add(generateNewDemoModelObject());
        demoModels.add(generateNewDemoModelObject());
        demoModels.add(generateNewDemoModelObject());
        demoModels.add(generateNewDemoModelObject());
        demoModels.add(generateNewDemoModelObject());
        Controllers.DemoController.saveAllItemsInBackground(demoModels);
    }

    @OnClick(R.id.btn_save_all_items_in_background_c)
    public void saveAllItemsInBackgroundC() {

    }

    @OnClick(R.id.btn_save_item_eventually)
    public void saveItemEventually() {

    }

    @OnClick(R.id.btn_save_item_eventually_c)
    public void saveItemEventuallyC() {

    }

    //FETCH OPERATIONS
    @OnClick(R.id.btn_fetch_item_in_background)
    public void fetchItemInBackground() {

    }

    @OnClick(R.id.btn_fetch_item_in_background_c)
    public void fetchItemInBackgroundC() {

    }


    @OnClick(R.id.btn_fetch_all_items_in_background)
    public void fetchAllItemsInBackground() {

    }


    @OnClick(R.id.btn_fetch_all_items_in_background_c)
    public void fetchAllItemsInBackgroundC() {

    }

    @OnClick(R.id.btn_fetch_item_from_local_datastore_in_background)
    public void fetchItemFromLocalDatastoreInBackground() {

    }


    @OnClick(R.id.btn_fetch_item_from_local_datastore_in_background_c)
    public void fetchItemFromLocalDatastoreInBackgroundC() {

    }

    @OnClick(R.id.btn_fetch_item_if_need_in_background)
    public void fetchItemIfNeedInBackground() {

    }

    @OnClick(R.id.btn_fetch_item_if_need_in_background_c)
    public void fetchItemIfNeedInBackgroundC() {

    }

    @OnClick(R.id.btn_fetch_all_items_if_need_in_background)
    public void fetchAllItemsIfNeedInBackground() {

    }

    @OnClick(R.id.btn_fetch_all_items_if_need_in_background_c)
    public void fetchAllItemsIfNeedInBackgroundC() {

    }


    //PIN OPERATIONS
    @OnClick(R.id.btn_pin_item_in_background)
    public void pinItemInBackground() {

    }

    @OnClick(R.id.btn_pin_item_in_background_c)
    public void pinItemInBackgroundC() {

    }

    @OnClick(R.id.btn_pin_all_items_in_background)
    public void pinAllItemsInBackground() {

    }

    @OnClick(R.id.btn_pin_all_items_in_background_c)
    public void pinAllItemsInBackgroundC() {

    }

    //UNPIN OPERATIONS
    @OnClick(R.id.btn_unpin_item_in_background)
    public void unpinItemInBackground() {

    }

    @OnClick(R.id.btn_unpin_item_in_background_g)
    public void unpinItemInBackgroundG() {

    }

    @OnClick(R.id.btn_unpin_item_in_background_c)
    public void unpinItemInBackgroundC() {

    }

    @OnClick(R.id.btn_unpin_item_in_background_g_c)
    public void unpinItemInBackgroundGC() {

    }

    @OnClick(R.id.btn_unpin_all_items_in_background)
    public void unpinAllItemsInBackground() {

    }

    @OnClick(R.id.btn_unpin_all_items_in_background_g)
    public void unpinAllItemsInBackgroundG() {

    }

    @OnClick(R.id.btn_unpin_all_items_in_background_c)
    public void unpinAllItemsInBackgroundC() {

    }

    @OnClick(R.id.btn_unpin_all_items_in_background_g_c)
    public void unpinnAllItemsInBackgroundGC() {

    }

    //DELETE OPERATIONS
    @OnClick(R.id.btn_delete_item_in_background)
    public void deleteItemInBackground() {

    }

    @OnClick(R.id.btn_delete_item_in_background_c)
    public void deleteItemInBackgroundC() {

    }

    @OnClick(R.id.btn_delete_all_items_in_background)
    public void deleteAllItemsInBackground() {

    }

    @OnClick(R.id.btn_delete_all_items_in_background_c)
    public void deleteAllItemsInBackgroundC() {

    }


    @OnClick(R.id.btn_delete_item_eventually)
    public void deleteItemEventually() {

    }


    @OnClick(R.id.btn_delete_item_eventually_c)
    public void deleteItemEventuallyC() {

    }

    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    //region Callbacks methods
    private SaveCallback createNewSaveCallback(final DemoModel demoModel) {
        return mSaveCallback = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    operationSuccessfully(demoModel);
                } else {
                    operationUnSuccessfully(demoModel);
                }
            }
        };
    }

    private GetCallback<DemoModel> createNewGetCallback(final DemoModel demoModel) {
        return mGetCallback = new GetCallback<DemoModel>() {
            @Override
            public void done(DemoModel object, ParseException e) {
                if (e == null) {
                    operationSuccessfully(demoModel);
                } else {
                    operationUnSuccessfully(demoModel);
                }
            }
        };
    }

    private FindCallback<DemoModel> createNewFindCallback(final DemoModel demoModel) {
        return  mFindCallback = new FindCallback<DemoModel>() {
            @Override
            public void done(List<DemoModel> objects, ParseException e) {
                if (e == null) {
                    operationSuccessfully(demoModel);
                } else {
                    operationUnSuccessfully(demoModel);
                }
            }
        };
    }

    private DeleteCallback createNewFinCallBack(final DemoModel demoModel) {
        return mDeleteCallback = new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    operationSuccessfully(demoModel);
                } else {
                    operationUnSuccessfully(demoModel);
                }
            }
        };
    }

    private void operationSuccessfully(DemoModel demoModel) {
        d("Operation for %s is successfully for", demoModel.getSimpleText());
        Toast.makeText(getApplicationContext(), "Success for " + demoModel.getSimpleText(), Toast.LENGTH_SHORT).show();
    }

    private void operationUnSuccessfully(DemoModel demoModel) {
        e("Operation for %s is unsuccessfully for", demoModel.getSimpleText());
        Toast.makeText(getApplicationContext(), "Excetpion for " + demoModel.getSimpleText(), Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region Private helper method
    private DemoModel generateNewDemoModelObject() {
        DemoModel demoModel = new DemoModel();
        demoModel.setSimpleText("DemoModel nr: " + getItemNumber());
        generateNextItemNumber();
        return demoModel;
    }

    private void generateNextItemNumber() {
        setItemNumber(getItemNumber() + 1);
    }
    //endregion

    //region getter and setter
    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
    //endregion

}
