package com.bluczak.albumofbeers.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bluczak.albumofbeers.R;
import com.bluczak.albumofbeers.backend.models.ImageModel;

import java.util.List;

/**
 * Created by BLuczak on 2015-07-04.
 */
public class ImageAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ImageModel> mImages;

    public ImageAdapter(Context context, List<ImageModel> images) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mImages = images;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = mLayoutInflater.inflate(R.layout.item_pager_image_beer, view, false);
        assert imageLayout != null;
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.iv_beer);
        imageView.setImageBitmap(setDateOnItemView(position));
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    private Bitmap setDateOnItemView(int position) {
        byte[] image = mImages.get(position).getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bmp;
    }

}
