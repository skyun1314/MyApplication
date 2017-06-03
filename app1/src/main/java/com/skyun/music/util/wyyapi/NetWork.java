package com.skyun.music.util.wyyapi;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getStr(String path) {

        try {
            // 1. 构造URL对象
            URL url = new URL(path);

            // 2. 创建一个URL连接对象
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // 3. 设置URL的请求模式或者其他信息
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);

            // 4. 请求数据，获取返回码
            int code = urlConnection.getResponseCode();
            // 200 OK  302 found 404 not fount 500 内部错误
            if (code == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int iReadSize = 0;
                while(iReadSize != -1){
                    iReadSize = inputStream.read(bytes, 0, 1024);
                    if (iReadSize == -1) break;
                    byteArrayOutputStream.write(bytes,0,iReadSize);
                }
                inputStream.close();

                 String string = new String(byteArrayOutputStream.toByteArray());
                return string;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static InputStream getInputStream(String path) {

        try {
            // 1. 构造URL对象
            URL url = new URL(path);

            // 2. 创建一个URL连接对象
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // 3. 设置URL的请求模式或者其他信息
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);

            // 4. 请求数据，获取返回码
            int code = urlConnection.getResponseCode();
            // 200 OK  302 found 404 not fount 500 内部错误
            if (code == 200) {
                InputStream inputStream = urlConnection.getInputStream();

                return inputStream;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void downSong(Context context, String songURL, String songName) {

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        String apkUrl = songURL;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setTitle(songName);
        request.setDestinationInExternalPublicDir("zkmusic", songName + ".mp3");
        request.setMimeType("audio/mpeg");
        request.allowScanningByMediaScanner();
        long downloadId = downloadManager.enqueue(request);
    }
}