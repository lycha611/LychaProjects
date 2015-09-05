package com.bluczak.albumofbeers.backend.controllers;

import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.bluczak.albumofbeers.backend.models.base.BaseModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by BLuczak on 2015-07-03.
 */
public abstract class BaseController<L extends BaseModel> {

    //region Base Queries for all resources
    public static final String QUERY_NOT_DIRTY = String.format("%s=0", BaseModel.FIELD_NAME_IS_DIRTY);
    //endregion

    protected final Class<L> mType;
    protected final boolean mUseMemoryCache;
    protected final List<L> mMemoryCache = new ArrayList<>();

    public BaseController(final Class<L> type, final boolean useCache) {
        mType = type;
        mUseMemoryCache = useCache;
    }

    public BaseController(final Class<L> type) {
        this(type, false);
    }

    public Class<L> getTableClass() {
        return mType;
    }

    public int getCount() {
        return new Select().from(getTableClass()).count();
    }

    public  List<L> getAll() {
        return new Select().from(getTableClass()).execute();
    }

    public void addNewItem(final L newItem) {
        newItem.save();
        if (mUseMemoryCache) mMemoryCache.add(newItem);
    }

    public void addNewItems(final Collection<L> newItems) {
        for (final L item : newItems) {
            addNewItem(item);
        }
    }

    public void clear(final boolean leaveDirty) {
        From delete = new Delete().from(getTableClass());
        if (leaveDirty) {
            delete = delete.where(QUERY_NOT_DIRTY);
        }
        delete.execute();

        if (mUseMemoryCache) {
            if (leaveDirty) {
                ListIterator<L> iter = mMemoryCache.listIterator();
                while (iter.hasNext()) {
                    final L item = iter.next();
                    if (item.isSynchronized()) iter.remove();
                }
            } else {
                mMemoryCache.clear();
            }
        }
    }

}
