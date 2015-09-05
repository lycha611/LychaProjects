package com.bluczak.albumofbeers.backend.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by BLuczak on 2015-07-04.
 */
public class Utils {

    public static byte[] getBytesFromResource(final InputStream input) {
        byte[] buffer = null;

        try {
            buffer = new byte[input.available()];
            if (input.read(buffer, 0, buffer.length) != buffer.length) {
                buffer = null;
            }
        } catch (IOException e) {
            buffer = null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }

        return buffer;
    }

    // convert from bitmap to byte array
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public static boolean isBlankOrNull(String pValue) {
        if (pValue == null || "".equals(pValue) || " ".equals(pValue) || pValue.trim().length() <= 0 || "null".equals(pValue)) {
            return true;
        } else {
            return false;
        }

    }

}
