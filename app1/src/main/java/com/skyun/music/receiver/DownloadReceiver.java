package com.skyun.music.receiver;

/**
 * Created by zk on 2017/6/2.
 */

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.skyun.music.activity.MyMusic;
import com.skyun.music.activity.NetMusic;

/**
 * 安装下载接收器
 * Created by maimingliang on 2016/8/11.
 */

public class DownloadReceiver extends BroadcastReceiver {


    // 安装下载接收器
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            System.out.println("歌曲："  + " 下载完成");
            NetMusic.handler.sendEmptyMessage(2);
            MyMusic.handler.sendEmptyMessage(2);
        }
    }


}