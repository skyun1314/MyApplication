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
import com.skyun.music.activity.net.DianTai;
import com.skyun.music.activity.net.FengLei;
import com.skyun.music.activity.net.Geshou;
import com.skyun.music.activity.net.MV;
import com.skyun.music.activity.net.Paihang;
import com.jude.rollviewpager.RollPagerView;
import com.skyun.music.adapter.NetMusicViewPagerAdapter;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetMusic extends Fragment {

    View inflate;
    public static Handler handler;
    NetMusicViewPagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_net_music, container, false);
        viewPagerInit(inflate);


        setItem(R.id.fragment_net_music_geshou, R.drawable.ic_menu_camera, "歌手", Geshou.class);
        setItem(R.id.fragment_net_music_paihang, R.drawable.ic_menu_gallery, "排行 \u3000", Paihang.class);
        setItem(R.id.fragment_net_music_diantai, R.drawable.ic_menu_manage, "电台\u3000\u3000", DianTai.class);
        setItem(R.id.fragment_net_music_fenlei, R.drawable.ic_menu_send, "分类歌单", FengLei.class);
        setItem(R.id.fragment_net_music_mv, R.drawable.ic_menu_share, "视频MV", MV.class);
        setItem(R.id.fragment_net_music_shuzi, R.drawable.ic_menu_slideshow, "数字专辑", BuyMusic.class);


        setHot1(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot1, Geshou.class);
        setHot1(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot2, Geshou.class);
        setHot1(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot3, Geshou.class);
        setHot1(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot1, Geshou.class);
        setHot1(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot2, Geshou.class);
        setHot1(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot3, Geshou.class);

        setHot1(R.id.fragment_net_music_new1, R.id.music_tuijian2_iv1, Geshou.class);
        setHot1(R.id.fragment_net_music_new1, R.id.music_tuijian2_iv2, Geshou.class);
        setHot1(R.id.fragment_net_music_new2, R.id.music_tuijian2_iv1, Geshou.class);
        setHot1(R.id.fragment_net_music_new2, R.id.music_tuijian2_iv2, Geshou.class);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                Map<String, Object> mymap = (Map<String, Object>) msg.obj;

                List<Map<String, Object>> banner = (List<Map<String, Object>>) mymap.get("banner");
                List<Map<String, Object>> hot = (List<Map<String, Object>>) mymap.get("hot");
                List<Map<String, Object>> new1 = (List<Map<String, Object>>) mymap.get("new");

                pagerAdapter.updataBitMap(banner);


                int ids1[] = {R.id.music_tuijian3_im1, R.id.music_tuijian3_src1, R.id.music_tuijian3_num};
                int ids2[] = {R.id.music_tuijian2_item_title, R.id.music_tuijian2_item_name, R.id.music_tuijian2_item_auther};


                setHot(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot1, hot.get(0), ids1);
                setHot(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot2, hot.get(1), ids1);
                setHot(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot3, hot.get(2), ids1);
                setHot(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot1, hot.get(3), ids1);
                setHot(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot2, hot.get(4), ids1);
                setHot(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot3, hot.get(5), ids1);


                setHot(R.id.fragment_net_music_new1, R.id.music_tuijian2_iv1, new1.get(0), ids2);
                setHot(R.id.fragment_net_music_new1, R.id.music_tuijian2_iv2, new1.get(1), ids2);
                setHot(R.id.fragment_net_music_new2, R.id.music_tuijian2_iv1, new1.get(2), ids2);
                setHot(R.id.fragment_net_music_new2, R.id.music_tuijian2_iv2, new1.get(3), ids2);


                return false;
            }
        });


        return inflate;

    }




    private void viewPagerInit(View inflate) {

        /*final ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.fragment_net_music_viewpager);

        pagerAdapter = new NetMusicViewPagerAdapter(inflate, getActivity(), viewPager);
*/
        RollPagerView  mViewPager = (RollPagerView) inflate.findViewById(R.id.view_pager);
        pagerAdapter = new NetMusicViewPagerAdapter(mViewPager, getActivity());
        mViewPager.setAdapter(pagerAdapter);
    }


    public void setHot(int itme_layout, int itme_layout2, Map<String, Object> hot, int ids[]) {
        LinearLayout layout1 = (LinearLayout) inflate.findViewById(itme_layout);
        RelativeLayout layout = (RelativeLayout) layout1.findViewById(itme_layout2);


        String nName = (String) hot.get("title");
        Bitmap nIcon= (Bitmap) hot.get("pic");

        String nNum = (String) hot.get("num");
        if (nNum == null) {
            nNum = (String) hot.get("author");
        }


        ImageView icon = (ImageView) layout.findViewById(ids[0]);
        TextView name = (TextView) layout.findViewById(ids[1]);
        TextView num = (TextView) layout.findViewById(ids[2]);

        icon.setImageBitmap(nIcon);
        name.setText(nName);
        num.setText(nNum);


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


    public void setItem(int itme_layout, int nIcon, String nName, final Class activity) {
        LinearLayout layout = (LinearLayout) inflate.findViewById(itme_layout);
        ImageView icon = (ImageView) layout.findViewById(R.id.fragment_net_music_item_icon);
        TextView name = (TextView) layout.findViewById(R.id.fragment_net_music_item_name);
        icon.setImageResource(nIcon);
        name.setText(nName);

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
