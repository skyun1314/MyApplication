package com.example.zk.activity.fragment.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.zk.activity.BaseActivity;
import com.example.zk.activity.Music;
import com.example.zk.activity.R;
import com.example.zk.activity.adapter.TabLayoutViewPagerAdapter;
import com.example.zk.activity.fragment.my.local.MyMusicDanQu;
import com.example.zk.activity.fragment.my.local.MyMusicGeShou;
import com.example.zk.activity.fragment.my.local.MyMusicWenJianJia;
import com.example.zk.activity.fragment.my.local.MyMusicZhuanJi;

import java.util.ArrayList;
import java.util.List;

public class LocalMusic extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_music_local);

        viewPagerInit();
    }

    public void viewPagerInit(){
        ViewPager viewPager= (ViewPager) findViewById(R.id.fragment_my_music_local_viewpager);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.fragment_my_music_local_tabLayout);

        //页面，数据源
        List<Fragment> list = new ArrayList<>();
        list.add(new MyMusicDanQu( quickcontrolsfragment1));
        list.add(new MyMusicGeShou());
        list.add(new MyMusicZhuanJi());
        list.add(new MyMusicWenJianJia());
        String[] mTitles = new String[]{"歌曲 "+ Music.MusicUtil.getAllMusic(this).size(),
                "歌手 "+Music.MusicUtil.getAllArtists(this).size(),
                "专辑 "+ Music.MusicUtil.getAllAlbums(this).size(),
                "文件夹 "+Music.MusicUtil.getMusicDirs(this).size()};


        //ViewPager的适配器
        TabLayoutViewPagerAdapter adapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(),list,mTitles);
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
    }



}
