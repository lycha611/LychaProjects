package com.spoon.corelib.backend.controllers;

import com.facebook.stetho.common.Predicate;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.spoon.corelib.backend.models.BaseModel;
import com.spoon.corelib.backend.utils.Logger;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import bolts.Task;

/**
 * Created by Lycha on 9/10/2015.
 */
public class BaseController<L extends BaseModel> {

    protected final Class<L> mType;

    public BaseController(final Class<L> type) {
        mType = type;
    }

    public Class<L> getTableClass() {
        return mType;
    }

    public ParseQuery<L> getQuery() {
        return ParseQuery.getQuery(getTableClass());
    }

    //region save object methods
    public void saveItemInBackground(L object) {
        object.saveInBackground();
    }

    public void saveItemInBackground(L object, SaveCallback saveCallback) {
        object.saveInBackground(saveCallback);
    }

    public void saveAllItemsInBackground(List<L> objects) {
        ParseObject.saveAllInBackground(objects);
    }

    public void saveAllItemsInBackground(List<L> objects, SaveCallback saveCallback) {
        ParseObject.saveAllInBackground(objects, saveCallback);
    }

    public void saveItemEventually(L object) {
        object.saveEventually();
    }

    public void saveItemEventually(L object, SaveCallback saveCallback) {
        object.saveEventually(saveCallback);
    }
    //endregion

    //region fetch object methods
    public void fetchItemInBackground(L object) {
        object.fetchInBackground();
    }

    public void fetchItemInBackground(L object, GetCallback<L> getCallback) {
        object.fetchInBackground(getCallback);
    }

    public void fetchAllItemsInBackground(List<L> objects) {
        ParseObject.fetchAllInBackground(objects);
    }

    public void fetchAllItemsInBackground(List<L> object, FindCallback<L> findCallback) {
        ParseObject.fetchAllInBackground(object, findCallback);
    }

    public void fetchItemFromLocalDatastoreInBackground(L object) {
        object.fetchIfNeededInBackground();
    }

    public void fetchItemFromLocalDatastoreInBackground(L object, GetCallback<L> getCallback) {
        object.fetchFromLocalDatastoreInBackground(getCallback);
    }

    // TODO: 9/15/2015 add fetchAllItemsFromLocalDataStoreInBackground 

    public void fetchItemIfNeededInBackground(L object) {
        object.fetchIfNeededInBackground();
    }

    public void fetchItemIfNeededInBackground(L object, GetCallback<L> getCallback) {
        object.fetchIfNeededInBackground(getCallback);
    }

    public void fetchAllItemsIfNeededInBackgrond(List<L> objects) {
        ParseObject.fetchAllIfNeededInBackground(objects);
    }

    public void fetchAllItemsIfNeededInBackgrond(List<L> objects, FindCallback<L> findCallback) {
        ParseObject.fetchAllIfNeededInBackground(objects, findCallback);
    }
    //endregion

    //region delete object methods
    public void deleteItemInBackground(L object) {
        object.deleteInBackground();
    }

    public void deleteItemInBackground(L object, DeleteCallback deleteCallback) {
        object.deleteInBackground(deleteCallback);
    }

    public Task<Void> deleteAllItemsInBackground(List<L> objects) {
        return ParseObject.deleteAllInBackground(objects);
    }

    public void deleteAllItemsInBackground(List<L> objects, DeleteCallback deleteCallback) {
        ParseObject.deleteAllInBackground(objects, deleteCallback);
    }

    public void deleteItemEventually(L object) {
        object.deleteEventually();
    }

    public void deleteItemEventually(L object, DeleteCallback deleteCallback) {
        object.deleteEventually(deleteCallback);
    }
    //endregion

    //region pin object methods
    public void pinItemInBackground(L object, String pinGroupName) {
        object.pinInBackground(pinGroupName);
    }

    public void pinItemInBackground(L object, String pinGroupName, SaveCallback saveCallback) {
        object.pinInBackground(pinGroupName, saveCallback);
    }

    public void pinAllItemsInBackground(List<L> objects, String pinGroupName) {
        ParseObject.pinAllInBackground(pinGroupName, objects);
    }

    public void pinAllItemsInBackground(List<L> objects, String pinGroupName, SaveCallback saveCallback) {
        ParseObject.pinAllInBackground(pinGroupName, objects, saveCallback);
    }
    //endregion

    //region unpin object methods
    public void unpinItemInBackground(L object) {
        object.unpinInBackground();
    }

    public void unpinItemInBackground(L object, DeleteCallback deleteCallback) {
        object.unpinInBackground(deleteCallback);
    }


    public void unpinItemInBackground(L object, String pinGroupName) {
        object.unpinInBackground(pinGroupName);
    }

    public void unpinItemInBackground(L object, String pinGroupName, DeleteCallback deleteCallback) {
        object.unpinInBackground(pinGroupName, deleteCallback);
    }

    public void unpinAllItemsInBackground() {
        ParseObject.unpinAllInBackground();
    }

    public void unpinAllItemsInBackground(DeleteCallback deleteCallback) {
        ParseObject.unpinAllInBackground(deleteCallback);
    }

    public void unpinAllItemsInBackground(List<L> objects) {
        ParseObject.unpinAllInBackground(objects);
    }

    public void unpinAllItemsInBackground(List<L> objects, DeleteCallback deleteCallback) {
        ParseObject.unpinAllInBackground(objects, deleteCallback);
    }

    public void unpinAllItemsInBackground(List<L> objects, String pinGroupName) {
        ParseObject.unpinAllInBackground(pinGroupName, objects);
    }

    public void unpinAllItemsInBackground(List<L> objects, String pinGroupName, DeleteCallback deleteCallback) {
        ParseObject.unpinAllInBackground(pinGroupName, objects, deleteCallback);
    }
    //endregion

    //region queries
    //endregion

}
