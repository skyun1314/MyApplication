package com.example.zk.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zk.activity.R;
import com.example.zk.activity.fragment.my.BuyMusic;
import com.example.zk.activity.fragment.my.DowloadMusic;
import com.example.zk.activity.fragment.my.LikeMusic;
import com.example.zk.activity.fragment.my.LocalMusic;
import com.example.zk.activity.fragment.my.MVMusic;
import com.example.zk.activity.fragment.my.RecentMusic;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusic extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_my_music, container, false);


        setItem(inflate,R.id.fragment_my_music_itme_local,R.drawable.ic_menu_camera,"本地歌曲",1, LocalMusic.class);
        setItem(inflate,R.id.fragment_my_music_itme_dowload,R.drawable.ic_menu_gallery,"下载歌曲",1, DowloadMusic.class);
        setItem(inflate,R.id.fragment_my_music_itme_recent,R.drawable.ic_menu_manage,"最近播放",1, RecentMusic.class);
        setItem(inflate,R.id.fragment_my_music_itme_like,R.drawable.ic_menu_send,"我喜欢",1, LikeMusic.class);
        setItem(inflate,R.id.fragment_my_music_itme_mv,R.drawable.ic_menu_share,"下载MV",1, MVMusic.class);
        setItem(inflate,R.id.fragment_my_music_itme_buy,R.drawable.ic_menu_slideshow,"以购音乐",1, BuyMusic.class);



        return inflate;
    }

    public void setItem(View inflate, int itme_layout, int nIcon, String nName, int nNum, final Class activity){
        LinearLayout layout= (LinearLayout) inflate.findViewById(itme_layout);
        ImageView icon= (ImageView) layout.findViewById(R.id.fragment_my_music_item_icon);
        TextView name= (TextView) layout.findViewById(R.id.fragment_my_music_item_name);
        TextView num= (TextView)layout.findViewById(R.id.fragment_my_music_item_num);
        icon.setImageResource(nIcon);
        name.setText(nName);
        num.setText(nNum+"");

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(),activity);
                startActivity(intent);
            }
        });

    }

}
