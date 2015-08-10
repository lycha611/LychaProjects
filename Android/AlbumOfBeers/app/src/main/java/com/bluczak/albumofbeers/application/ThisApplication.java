package com.bluczak.albumofbeers.application;

import com.activeandroid.ActiveAndroid;
import com.bluczak.albumofbeers.application.base.BaseApplication;
import com.bluczak.albumofbeers.backend.configuration.Configuration;
import com.bluczak.albumofbeers.backend.controllers.Controllers;
import com.bluczak.albumofbeers.backend.models.BeerModel;
import com.bluczak.albumofbeers.backend.models.ImageModel;
import com.bluczak.albumofbeers.backend.utils.Utils;
import com.bluczak.albumofbeers.developer.StethoIntegration;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by BLuczak on 2015-07-03.
 */
public class ThisApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        CoreLib.App.setLoggerEnabled(Configuration.shouldUseLogger())
                .setActivityLoggerEnabled(Configuration.shouldLogActivityLifecycle())
                .init();

        if (Configuration.shouldUseStetho()) {
            StethoIntegration.init(this);
        }
        JodaTimeAndroid.init(this);
        deleteDatabase("albumOfBeers.db");
        ActiveAndroid.initialize(this);
        inputExampleDate();
    }

    private void inputExampleDate() {

        BeerModel beerModel = new BeerModel();
        beerModel.setName("Tyskie");
        beerModel.setKind("Pilzner");
        beerModel.setDescription("Tyskie Gronie to lager, którym dostarcza zdecydowanej goryczki i słodowej pełni, jednocześnie zaspokajając pragnienie. Tyskie powstaje ze starannie wyselekcjonowanych składników: słodu jęczmiennego, chmielu i wody, z użyciem drożdży w procesie naturalnej piwnej fermentacji.");
        Controllers.BeerController.addNewBeer(beerModel);
        inputImagesToBeer("tyskie", beerModel);

        beerModel = new BeerModel();
        beerModel.setName("Żubr");
        beerModel.setKind("Lager");
        beerModel.setDescription("Piwo Żubr to znakomity lager o pełnym, orzeźwiającym smaku, w którym wyczujesz delikatne, słodowe nuty. Warzony przy użyciu najlepszych składników.");
        Controllers.BeerController.addNewBeer(beerModel);
        inputImagesToBeer("zubr", beerModel);

        beerModel = new BeerModel();
        beerModel.setName("Heineken");
        beerModel.setKind("Jasne lager");
        beerModel.setDescription("Heineken, w charakterystycznej zielonej butelce, jest jedną z najsilniejszych międzynarodowych marek piwa. Jest sprzedawany na każdym kontynencie w ponad 180 krajach świata. W Polsce Heineken jest największą marką w segmencie international premium, cenioną przez osoby szukające piwa najwyższej jakości.");
        Controllers.BeerController.addNewBeer(beerModel);
        inputImagesToBeer("heineken", beerModel);

    }


    private List<ImageModel> inputImagesToBeer(String folderName, BeerModel beerModel) {
        List<ImageModel> images = null;
        try {
            String[] fileNames = getAssets().list(folderName);

            InputStream inputStream = null;
            images = new ArrayList<ImageModel>();
            for (int i = 0; i < fileNames.length; i++) {
                inputStream = getAssets().open(folderName + "/" + fileNames[i]);
                byte[] image = Utils.getBytesFromResource(inputStream);
                images.add(inputImageToDatabase(image, beerModel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }

    private ImageModel inputImageToDatabase(byte[] image, BeerModel beerModel) {
        return Controllers.ImageController.addNewImage(image, beerModel);
    }


    public static ThisApplication get() {
        return (ThisApplication) BaseApplication.get();
    }

    private final static String CLIENT_ID = "android-native";

    public static String getClientId() {
        return CLIENT_ID;
    }

}
