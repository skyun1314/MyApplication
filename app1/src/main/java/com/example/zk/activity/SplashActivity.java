package com.example.zk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zk.activity.util.wyyapi.MyClass;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyClass.wangyiyun();
            }
        }).start();
        ;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, Main.class));
                finish();
            }
        };
        timer.schedule(task, 0);


       /* AssetManager manager = getAssets();
        try {
            InputStream open = manager.open("AndroidManifest.xml");
            readManifest(open);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    public static void readManifest(InputStream stream) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(stream, "utf-8");
            int eventType = parser.getEventType();
           String activity = null;
            while (XmlPullParser.END_DOCUMENT != eventType) {


                if (eventType == XmlPullParser.START_TAG) {
                    String name = parser.getName();

                  // Log.e(TAG, "name: " + name);


                    if (name.equals("manifest")) {
                        String package1 = parser.getAttributeValue("", "package");
                        Data.showMyLog("package: " + package1);
                    }


                    else  if (name.equals("uses-permission")) {


                        String permission = parser.getAttributeValue("", "android:name");

                        Data.showMyLog("uses-permission:" + permission);
                    }

                    else  if (name.equals("activity")) {
                         activity = parser.getAttributeValue("", "android:name");
                        Data.showMyLog("activity: " + activity);

                    }

                    else  if (name.equals("category")) {
                        String category = parser.getAttributeValue("", "android:name");
                        if (category.equals("android.intent.category.LAUNCHER")){
                            Data.showMyLog("主activity是: "+ activity);
                        }
                    }

                }

                // 获取XML标签的属性个数
               // Log.e(TAG, "readManifest: " + parser.getAttributeCount());
                eventType = parser.next();

            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
