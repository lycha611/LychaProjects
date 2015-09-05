package com.bluczak.albumofbeers.backend.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.bluczak.albumofbeers.backend.models.base.BaseModel;

import java.util.List;

/**
 * Created by BLuczak on 2015-07-03.
 */
@Table(name = "Beers")
public class BeerModel extends BaseModel {

    public static final String TABLE_NAME = "Beers";
    public static final String NAME_FIELDNAME = "Name";
    public static final String KIND_FIELDNAME = "Kind";
    public static final String DESCRIPTION_FIELDNAME = "Description";


    @Column(name = NAME_FIELDNAME)
    public String mName;

    @Column(name = KIND_FIELDNAME)
    public String mKind;

    @Column(name = DESCRIPTION_FIELDNAME)
    public String mDescription;

    public List<ImageModel> images() {
        return getMany(ImageModel.class, "BeerModel");
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

}
