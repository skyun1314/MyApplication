package com.skyun.music.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zk.activity.R;
import com.skyun.music.activity.my.BuyMusic;
import com.skyun.music.activity.my.DowloadMusic;
import com.skyun.music.activity.my.LikeMusic;
import com.skyun.music.activity.my.LocalMusic;
import com.skyun.music.activity.my.MVMusic;
import com.skyun.music.activity.my.local.MyMusicDanQu1;
import com.skyun.music.activity.net.Geshou;
import com.skyun.music.mode.Data;
import com.skyun.music.mode.Music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.skyun.music.activity.Main.helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusic extends Fragment {
    List<Music> download = new ArrayList<>();
    View inflate;
    public static Handler handler;
    ;;
    int ids1[] = {R.id.music_tuijian3_im1, R.id.music_tuijian3_src1, R.id.music_tuijian3_num};
    private List<String> getDataBase = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.fragment_my_music, container, false);


       updateRecent();
        updateDonload();


        setItem(R.id.fragment_my_music_itme_local, R.drawable.ic_menu_camera, "本地歌曲", Music.MusicUtil.getAllMusic(getContext()), LocalMusic.class);
        setItem(R.id.fragment_my_music_itme_dowload, R.drawable.ic_menu_gallery, "下载歌曲", download, DowloadMusic.class);
        setItem(R.id.fragment_my_music_itme_recent, R.drawable.ic_menu_manage, "最近播放",download , MyMusicDanQu1.class);
        setItem(R.id.fragment_my_music_itme_like, R.drawable.ic_menu_send, "我喜欢", Music.MusicUtil.getAllMusic(getContext()), LikeMusic.class);
        setItem(R.id.fragment_my_music_itme_mv, R.drawable.ic_menu_share, "下载MV", Music.MusicUtil.getAllMusic(getContext()), MVMusic.class);
        setItem(R.id.fragment_my_music_itme_buy, R.drawable.ic_menu_slideshow, "以购音乐", Music.MusicUtil.getAllMusic(getContext()), BuyMusic.class);


        setHot1(R.id.fragment_my_music_tuijian, R.id.music_tuijian3_hot1, Geshou.class);
        setHot1(R.id.fragment_my_music_tuijian, R.id.music_tuijian3_hot2, Geshou.class);
        setHot1(R.id.fragment_my_music_tuijian, R.id.music_tuijian3_hot3, Geshou.class);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what) {

                    case 1:
                        List<Map<String, Object>> new1 = (List<Map<String, Object>>) msg.obj;


                        setHot(R.id.fragment_my_music_tuijian, R.id.music_tuijian3_hot1, new1.get(0), ids1);
                        setHot(R.id.fragment_my_music_tuijian, R.id.music_tuijian3_hot2, new1.get(1), ids1);
                        setHot(R.id.fragment_my_music_tuijian, R.id.music_tuijian3_hot3, new1.get(2), ids1);

                        break;


                    case 2:
                        updateRecent();
                        break;

                    case 3:
                        updateDonload();
                        break;

                }
                return false;
            }
        });


        return inflate;
    }


    public void updateRecent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataBase = helper.getAll();
                final List<Music> allMusicByFile = Music.MusicUtil.getAllMusicByFile(getDataBase);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setItem(R.id.fragment_my_music_itme_recent, R.drawable.ic_menu_manage, "最近播放", allMusicByFile, MyMusicDanQu1.class);

                    }
                });
            }
        }).start();
        ;
    }

    public void updateDonload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                download = Music.MusicUtil.getMusicInfo(Data.APP_FILE_PATH, getContext());


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setItem(R.id.fragment_my_music_itme_dowload, R.drawable.ic_menu_gallery, "下载歌曲", download, DowloadMusic.class);

                    }
                });
            }
        }).start();
        ;
    }



    //动态设置本地mp3标题图片

    public void setItem(int itme_layout, int nIcon, String nName, final List<Music> music, final Class activity) {
        LinearLayout layout = (LinearLayout) inflate.findViewById(itme_layout);
        ImageView icon = (ImageView) layout.findViewById(R.id.fragment_my_music_item_icon);
        TextView name = (TextView) layout.findViewById(R.id.fragment_my_music_item_name);
        TextView num = (TextView) layout.findViewById(R.id.fragment_my_music_item_num);
        icon.setImageResource(nIcon);
        name.setText(nName);
        num.setText(music.size() + "");

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(activity.getName(), (Serializable) music);
                intent.setClass(getContext(), activity);
                startActivity(intent);
            }
        });

    }

    public void setHot(int itme_layout, int itme_layout2, Map<String, Object> new1, int ids[]) {
        LinearLayout layout1 = (LinearLayout) inflate.findViewById(itme_layout);
        RelativeLayout layout = (RelativeLayout) layout1.findViewById(itme_layout2);

        ImageView icon = (ImageView) layout.findViewById(ids[0]);
        TextView name = (TextView) layout.findViewById(ids[1]);
        TextView num = (TextView) layout.findViewById(ids[2]);
        icon.setImageBitmap((Bitmap) new1.get("pic"));
        name.setText((String) new1.get("title"));
        num.setText((String) new1.get("author"));

    }

    public void setHot1(int itme_layout, int itme_layout2, final Class activity) {
        LinearLayout layout1 = (LinearLayout) inflate.findViewById(itme_layout);
        RelativeLayout layout = (RelativeLayout) layout1.findViewById(itme_layout2);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), activity);
                startActivity(intent);
            }
        });

    }


}
