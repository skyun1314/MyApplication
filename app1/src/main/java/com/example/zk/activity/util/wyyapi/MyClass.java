package com.example.zk.activity.util.wyyapi;

import android.graphics.Bitmap;
import android.os.Message;

import com.example.zk.activity.fragment.MyMusic;
import com.example.zk.activity.fragment.NetMusic;
import com.hehe.WYYAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zk on 2017/5/31.
 */

public class MyClass {
    public static void wangyiyun() {

        List<Object> instans = WYYAPI.discover.instans();
        List<Map<String, Object>> hotTuiJian = (List<Map<String, Object>>) instans.get(0);
        List<Map<String, Object>> newDieSHangJia = (List<Map<String, Object>>) instans.get(1);

        List<Map<String, Object>> bangDan = (List<Map<String, Object>>) instans.get(2);
        List<Map<String, Object>> banner = (List<Map<String, Object>>) instans.get(3);


        reSetMap(hotTuiJian);
        reSetMap(newDieSHangJia);
        reSetMap(bangDan);
        reSetMap(banner);


        Map<String,Object> mymap=new HashMap<>();
        mymap.put("hot",hotTuiJian);
        mymap.put("new",newDieSHangJia);
        mymap.put("banner",banner);


        Message message = NetMusic.handler.obtainMessage();
        message.obj= mymap;
        message.sendToTarget();


        Message message1 = MyMusic.handler.obtainMessage();
        message1.obj= newDieSHangJia;
        message1.sendToTarget();


    }

    private static void reSetMap(List<Map<String, Object>> hotTuiJian) {
        for (int i = 0; i <hotTuiJian.size() ; i++) {
            String pic = (String) hotTuiJian.get(i).get("pic");
            Bitmap picByGet = NetWork.getPicByGet(pic);
            hotTuiJian.get(i).put("pic",picByGet);
        }
    }
}
