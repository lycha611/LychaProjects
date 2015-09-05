package com.bluczak.albumofbeers.backend.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.bluczak.albumofbeers.backend.models.base.BaseModel;

/**
 * Created by BLuczak on 2015-07-04.
 */
@Table(name = "Images")
public class ImageModel extends BaseModel {

    public static final String TABLE_NAME = "Images";
    public static final String IMAGE_FIELDNAME = "Image";
    public static final String BEER_MODEL_FIELDNAME = "BeerModel";

    @Column(name = IMAGE_FIELDNAME)
    public byte[] mImage;

    @Column(name = BEER_MODEL_FIELDNAME)
    public BeerModel beerModel;

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

    public BeerModel getBeerModel() {
        return beerModel;
    }

    public void setBeerModel(BeerModel beerModel) {
        this.beerModel = beerModel;
    }
}
