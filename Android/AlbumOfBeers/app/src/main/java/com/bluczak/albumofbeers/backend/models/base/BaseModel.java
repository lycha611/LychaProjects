package com.bluczak.albumofbeers.backend.models.base;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by BLuczak on 2015-07-03.
 */
public class BaseModel extends Model {

    //region Base field names specifications
    public static final String FIELD_NAME_IS_DIRTY = "__is_dirty";
    public static final String FIELD_NAME_IS_DELETED = "__is_deleted";
    public static final String ID_FIELDNAME = "Id";
    //endregion

    //region isDirty() characteristic

    @Column(name = FIELD_NAME_IS_DIRTY)
    public boolean isDirty = false;

    public boolean isDirty() {
        return isDirty;
    }

    public void setIsDirty(final boolean newValue) {
        if(isDirty() == newValue) return;

        isDirty = newValue;
    }

    public boolean isSynchronized() {
        return !isDirty();
    }

    //endregion

    //region isDeleted() characteristic
    @Column(name = FIELD_NAME_IS_DELETED)
    public boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void markAsDeleted() {
        if(isDeleted) return;

        isDeleted = true;
    }
    //endregion

}
