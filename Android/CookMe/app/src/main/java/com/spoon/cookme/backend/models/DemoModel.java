package com.spoon.cookme.backend.models;

import com.parse.ParseClassName;
import com.spoon.corelib.backend.models.BaseModel;

/**
 * Created by Lycha on 9/13/2015.
 */
@ParseClassName("DemoModel")
public class DemoModel extends BaseModel {

    //region Base field names specifications
    public static final String TABLE_NAME = "DemoModel";
    public static final String FIELD_NAME_SIMPLE_TEXT = "simpleText";
    //endregion

    public DemoModel() {
        super();
    }

    //region getter and setter
    public String getSimpleText(){
        return getString(FIELD_NAME_SIMPLE_TEXT);
    }

    public void setSimpleText(String simpleText){
        put(FIELD_NAME_SIMPLE_TEXT, simpleText);
    }
    //endregion
}
