package com.example.zk.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zk.activity.R;
import com.example.zk.activity.fragment.my.BuyMusic;
import com.example.zk.activity.fragment.net.DianTai;
import com.example.zk.activity.fragment.net.FengLei;
import com.example.zk.activity.fragment.net.Geshou;
import com.example.zk.activity.fragment.net.MV;
import com.example.zk.activity.fragment.net.Paihang;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetMusic extends Fragment {

    String TAG="wodelog";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_net_music, container, false);
        viewPagerInit(inflate);


        setItem(inflate,R.id.fragment_net_music_geshou,R.drawable.ic_menu_camera,"歌手", Geshou.class);
        setItem(inflate,R.id.fragment_net_music_paihang,R.drawable.ic_menu_gallery,"排行 \u3000", Paihang.class);
        setItem(inflate,R.id.fragment_net_music_diantai,R.drawable.ic_menu_manage,"电台\u3000\u3000", DianTai.class);
        setItem(inflate,R.id.fragment_net_music_fenlei,R.drawable.ic_menu_send,"分类歌单", FengLei.class);
        setItem(inflate,R.id.fragment_net_music_mv,R.drawable.ic_menu_share,"视频MV", MV.class);
        setItem(inflate,R.id.fragment_net_music_shuzi,R.drawable.ic_menu_slideshow,"数字专辑", BuyMusic.class);




        return inflate;

    }

    private void viewPagerInit(View inflate) {

        ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.fragment_net_music_viewpager);
        int[] resIds = {R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery, R.drawable.ic_menu_manage
                , R.drawable.ic_menu_send, R.drawable.ic_menu_share};
         ArrayList<Integer> res=new ArrayList<>();
        for (int i = 0; i < resIds.length; i++) {
            res.add(resIds[i]);
        }

        NetMusicViewPagerAdapter pagerAdapter = new NetMusicViewPagerAdapter(getContext(), res,viewPager);
    }

    public void setItem(View inflate, int itme_layout, int nIcon, String nName, final Class activity){
        LinearLayout layout= (LinearLayout) inflate.findViewById(itme_layout);
        ImageView icon= (ImageView) layout.findViewById(R.id.fragment_net_music_item_icon);
        TextView name= (TextView) layout.findViewById(R.id.fragment_net_music_item_name);
        icon.setImageResource(nIcon);
        name.setText(nName);

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
