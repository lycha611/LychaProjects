package com.bluczak.albumofbeers.ui.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bluczak.albumofbeers.R;
import com.bluczak.albumofbeers.backend.annotations.ContentView;
import com.bluczak.albumofbeers.backend.controllers.Controllers;
import com.bluczak.albumofbeers.backend.models.BeerModel;
import com.bluczak.albumofbeers.backend.models.ImageModel;
import com.bluczak.albumofbeers.backend.utils.Utils;
import com.bluczak.albumofbeers.ui.activities.base.InjectionActivity;
import com.bluczak.albumofbeers.ui.adapters.ImageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by BLuczak on 2015-07-04.
 */
@ContentView(R.layout.activity_beer)
public class BeerActivity extends InjectionActivity {

    //region UI view references
    @InjectView(R.id.vp_images)
    ViewPager mVpImages;

    @InjectView(R.id.tv_beer_name)
    TextView mTvBeerName;

    @InjectView(R.id.tv_kind_of_beer)
    TextView mTvKindOfBeer;

    @InjectView(R.id.tv_description)
    TextView mTvDescription;

    //endregion

    //region Fields
    private List<ImageModel> mImages;
    private ImageAdapter mImageAdapter;
    private BeerModel mBeerModel;
    // Image loading result to pass to startActivityForResult method.
    private static int LOAD_IMAGE_RESULTS = 1;
    //endregion

    //region Activity lifecycle callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getIntent().getExtras().getLong("id");
        initImageLoader();
        mBeerModel = Controllers.BeerController.getBeer(id);
        setTitleOnActionBar();
        createList();
        setDateAboutBeer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataToList();
        setAdapterList();
    }

    //endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_beer_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_image:
                showImageGallery();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //region Private helper methods
    private void setTitleOnActionBar() {
        ActionBar ab = getActionBar();
        ab.setTitle(mBeerModel.getName());
    }

    private void initImageLoader() {
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void createList() {
        mImages = new ArrayList<ImageModel>();
    }

    private void getDataToList() {
        mImages = mBeerModel.images();
    }

    private void setAdapterList() {
        mImageAdapter = new ImageAdapter(this, mImages);
        mVpImages.setAdapter(mImageAdapter);
    }

    private void setDateAboutBeer() {
        mTvBeerName.setText(mBeerModel.getName());
        mTvKindOfBeer.setText(mBeerModel.getKind());
        mTvDescription.setText(mBeerModel.getDescription());
    }

    private void showImageGallery() {
        //Create the Intent for Image Gallery.
        Intent i = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);

        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            // image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            Bitmap bmp = BitmapFactory.decodeFile(imagePath);

            byte[] image = Utils.getBytesFromBitmap(bmp);

            Controllers.ImageController.addNewImage(image, mBeerModel);


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    //endregion
}
