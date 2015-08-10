package com.bluczak.albumofbeers.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluczak.albumofbeers.R;
import com.bluczak.albumofbeers.backend.models.BeerModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by BLuczak on 2015-07-03.
 */
public class BeersAdapter extends ArrayAdapter<BeerModel> {

    private ArrayList<BeerModel> mBeers;
    private Context mContext;


    public BeersAdapter(Context context, ArrayList<BeerModel> beers) {
        super(context, R.layout.list_view_item_beer, beers);
        mContext = context;
        mBeers = beers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        final BeerViewHolder beerViewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            beerViewHolder = new BeerViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_view_item_beer, parent, false);
            initUiViewHolder(convertView, beerViewHolder);
            convertView.setTag(beerViewHolder);
        } else {
            beerViewHolder = (BeerViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        setDateOnUiViewHolder(beerViewHolder, position);

        // Return the completed view to render on screen
        return convertView;
    }

    private static class BeerViewHolder {
        private TextView mTvName;
        private ImageView mIvBeer;
    }

    private void initUiViewHolder(View convertView, BeerViewHolder beerViewHolder) {
        beerViewHolder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
        beerViewHolder.mIvBeer = (ImageView) convertView.findViewById(R.id.iv_beer);
    }

    private void setDateOnUiViewHolder(BeerViewHolder beerViewHolder, int position) {
        BeerModel beerModel = mBeers.get(position);
        beerViewHolder.mTvName.setText(beerModel.getName());
        byte[] image = null;
        if (beerModel.images() == null || beerModel.images().isEmpty() ) {
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_empty);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image = stream.toByteArray();
        } else {
            image = beerModel.images().get(0).getImage();
        }
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        beerViewHolder.mIvBeer.setImageBitmap(bmp);
    }
}
