package com.example.zk.activity.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zk.activity.R;
import com.example.zk.activity.fragment.my.BuyMusic;
import com.example.zk.activity.fragment.net.DianTai;
import com.example.zk.activity.fragment.net.FengLei;
import com.example.zk.activity.fragment.net.Geshou;
import com.example.zk.activity.fragment.net.MV;
import com.example.zk.activity.fragment.net.Paihang;
import com.example.zk.activity.util.MyClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetMusic extends Fragment {

    View inflate;
   public static Handler handler ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         inflate = inflater.inflate(R.layout.fragment_net_music, container, false);
        viewPagerInit(inflate);


        setItem( R.id.fragment_net_music_geshou, R.drawable.ic_menu_camera, "歌手", Geshou.class);
        setItem( R.id.fragment_net_music_paihang, R.drawable.ic_menu_gallery, "排行 \u3000", Paihang.class);
        setItem( R.id.fragment_net_music_diantai, R.drawable.ic_menu_manage, "电台\u3000\u3000", DianTai.class);
        setItem( R.id.fragment_net_music_fenlei, R.drawable.ic_menu_send, "分类歌单", FengLei.class);
        setItem(R.id.fragment_net_music_mv, R.drawable.ic_menu_share, "视频MV", MV.class);
        setItem( R.id.fragment_net_music_shuzi, R.drawable.ic_menu_slideshow, "数字专辑", BuyMusic.class);



        setHot1( R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot1,  Geshou.class);
        setHot1( R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot2, Geshou.class);
        setHot1( R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot3, Geshou.class);
        setHot1( R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot1, Geshou.class);
        setHot1( R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot2, Geshou.class);
        setHot1( R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot3, Geshou.class);

        setHot1( R.id.fragment_net_music_new1, R.id.music_tuijian2_iv1, Geshou.class);
        setHot1( R.id.fragment_net_music_new1, R.id.music_tuijian2_iv2,  Geshou.class);
        setHot1( R.id.fragment_net_music_new2, R.id.music_tuijian2_iv1,  Geshou.class);
        setHot1( R.id.fragment_net_music_new2, R.id.music_tuijian2_iv2,  Geshou.class);



        handler =new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                Map<String,Object> mymap= (Map<String, Object>) msg.obj;

                        List<Map<String, Object>> hot= (List<Map<String, Object>>) mymap.get("hot");
                        List<Map<String, Object>> new1= (List<Map<String, Object>>) mymap.get("new");



                int ids1[]={R.id.music_tuijian3_im1,R.id.music_tuijian3_src1,R.id.music_tuijian3_num};
                int ids2[]={R.id.music_tuijian2_item_title,R.id.music_tuijian2_item_name,R.id.music_tuijian2_item_auther};


                        setHot( R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot1, (Bitmap) hot.get(0).get("pic"), (String) hot.get(0).get("title"), (String) hot.get(0).get("num"), ids1);
                        setHot( R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot2, (Bitmap) hot.get(1).get("pic"), (String) hot.get(1).get("title"), (String) hot.get(1).get("num"), ids1);
                        setHot( R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot3, (Bitmap) hot.get(2).get("pic"), (String) hot.get(2).get("title"), (String) hot.get(2).get("num"), ids1);
                        setHot( R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot1, (Bitmap) hot.get(3).get("pic"), (String) hot.get(3).get("title"), (String) hot.get(3).get("num"), ids1);
                        setHot( R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot2, (Bitmap) hot.get(4).get("pic"), (String) hot.get(4).get("title"), (String) hot.get(4).get("num"), ids1);
                        setHot( R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot3, (Bitmap) hot.get(5).get("pic"), (String) hot.get(5).get("title"), (String) hot.get(5).get("num"), ids1);

                        setHot( R.id.fragment_net_music_new1, R.id.music_tuijian2_iv1, (Bitmap) new1.get(0).get("pic"), (String) new1.get(0).get("title"), (String) new1.get(0).get("author"), ids2);
                        setHot( R.id.fragment_net_music_new1, R.id.music_tuijian2_iv2, (Bitmap) new1.get(4).get("pic"), (String) new1.get(4).get("title"), (String) new1.get(4).get("author"), ids2);
                        setHot( R.id.fragment_net_music_new2, R.id.music_tuijian2_iv1, (Bitmap) new1.get(7).get("pic"), (String) new1.get(7).get("title"), (String) new1.get(7).get("author"), ids2);
                        setHot( R.id.fragment_net_music_new2, R.id.music_tuijian2_iv2, (Bitmap) new1.get(8).get("pic"), (String) new1.get(8).get("title"), (String) new1.get(8).get("author"), ids2);




                return false;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                 MyClass.wangyiyun();
            }
        }).start();;

        return inflate;

    }




    int[] resIds = {R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery, R.drawable.ic_menu_manage
            , R.drawable.ic_menu_send, R.drawable.ic_menu_share};

    private void viewPagerInit(View inflate) {

        final ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.fragment_net_music_viewpager);

        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < resIds.length; i++) {
            res.add(resIds[i]);
        }

        NetMusicViewPagerAdapter pagerAdapter = new NetMusicViewPagerAdapter( inflate,getActivity(), res, viewPager);


    }


    public void setHot( int itme_layout, int itme_layout2, Bitmap nIcon, String nName, String nNum,int ids[]) {
        LinearLayout layout1 = (LinearLayout) inflate.findViewById(itme_layout);
        RelativeLayout layout = (RelativeLayout) layout1.findViewById(itme_layout2);


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
