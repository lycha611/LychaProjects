package com.bluczak.albumofbeers.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bluczak.albumofbeers.R;
import com.bluczak.albumofbeers.backend.annotations.ContentView;
import com.bluczak.albumofbeers.backend.controllers.Controllers;
import com.bluczak.albumofbeers.backend.models.BeerModel;
import com.bluczak.albumofbeers.backend.utils.Utils;
import com.bluczak.albumofbeers.ui.activities.base.InjectionActivity;

import butterknife.InjectView;

/**
 * Created by BLuczak on 2015-07-04.
 */
@ContentView(R.layout.activity_add_beer)
public class NewBeerActivity extends InjectionActivity {

    //region UI view references
    @InjectView(R.id.iv_photo)
    ImageView mIvPhoto;

    @InjectView(R.id.et_beer_name)
    EditText mEtBeerName;

    @InjectView(R.id.et_kind_of_beer)
    EditText mEtKindOfBeer;

    @InjectView(R.id.et_description)
    EditText mEtDescription;
    //endregion

    //region Fields
    // Image loading result to pass to startActivityForResult method.
    private static int LOAD_IMAGE_RESULTS = 1;
    private byte[] mImage = null;
    //endregion

    //region Activity lifecycle callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_beer_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_image:
                showImageGallery();
                return true;
            case R.id.action_save_beer:
                saveBeer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showImageGallery() {
        //Create the Intent for Image Gallery.
        Intent i = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);

        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when mImage is picked from the Image Gallery.
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an mImage was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked mImage data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked mImage path using content resolver
            String[] filePath = {Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            // mImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            Bitmap bmp = BitmapFactory.decodeFile(imagePath);
            mIvPhoto.setImageBitmap(bmp);

            mImage = Utils.getBytesFromBitmap(bmp);



            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    private void saveBeer() {
        String name = mEtBeerName.getText().toString();
        String kindOfBeer = mEtKindOfBeer.getText().toString();
        String description = mEtDescription.getText().toString();
        if(Utils.isBlankOrNull(name)){
            Toast.makeText(getApplicationContext(), R.string.info_you_must_enter_name_for_beer, Toast.LENGTH_SHORT).show();
        } else {
            BeerModel beerModel = new BeerModel();
            beerModel.setName(name);
            beerModel.setKind(kindOfBeer);
            beerModel.setDescription(description);
            Controllers.BeerController.addNewBeer(beerModel);
            if (mImage != null){
                Controllers.ImageController.addNewImage(mImage, beerModel);
            }
            Toast.makeText(getApplicationContext(), R.string.info_beer_has_been_added, Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

}
