package com.skyun.music.mode;

import android.os.Environment;
import android.util.Log;

/**
 * Created by zk on 2017/5/23.
 */

public class Data {

   public static String APP_FILE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/zkmusic/";
   //public static String APP_FILE_PATH= "/sdcard/zkmusic/";
   public static String TAG="wodelog";

   /**
    * 获取扩展SD卡存储目录
    *
    * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
    * 否则：返回内置SD卡目录
    *
    * @return
    */

   public static void showMyLog(String...log){

      if (log.length>1){
         Log.e(log[0],log[1] );
      }else{
         Log.e(TAG,log[0] );
      }


   }

}
