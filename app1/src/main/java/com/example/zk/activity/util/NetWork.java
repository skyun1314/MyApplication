package com.example.zk.activity.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zk on 2017/5/26.
 */

public class NetWork {

    public static Bitmap getPicByGet(String path) {

        try {
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);

            int code = urlConnection.getResponseCode();
            if (code == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                 Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}