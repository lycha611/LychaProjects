package com.bluczak.albumofbeers.backend.controllers;

import com.activeandroid.query.Delete;
import com.bluczak.albumofbeers.backend.models.BeerModel;
import com.bluczak.albumofbeers.backend.models.ImageModel;

/**
 * Created by BLuczak on 2015-07-04.
 */
public class ImageController extends BaseController<ImageModel> {

    public ImageController() {
        super(ImageModel.class);
    }

    public ImageModel addNewImage(byte[] image, BeerModel beerModel) {
        ImageModel imageModel = new ImageModel();
        imageModel.setImage(image);
        imageModel.beerModel = beerModel;
        imageModel.save();
        return imageModel;
    }

    public void deleteAll(){
        new Delete().from(ImageModel.class).execute();
    }

}
