package com.bluczak.albumofbeers.backend.controllers;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.bluczak.albumofbeers.backend.models.BeerModel;

import java.util.List;

/**
 * Created by BLuczak on 2015-07-03.
 */
public class BeerController extends BaseController<BeerModel> {

    public BeerController() {
        super(BeerModel.class);
    }

    public void addNewBeer(BeerModel beerModel) {
        beerModel.save();
    }

    public List<BeerModel> getAll() {
        return new Select()
                .from(BeerModel.class)
                .execute();
    }

    public void deleteAll(){
        new Delete().from(BeerModel.class).execute();
    }


    public BeerModel getBeer(long id) {
        return new Select()
                .from(BeerModel.class)
                .where("Id = ?", id)
                .executeSingle();
    }
}
