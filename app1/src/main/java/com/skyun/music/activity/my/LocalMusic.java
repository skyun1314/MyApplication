package com.skyun.music.activity.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.skyun.music.activity.BaseActivity;
import com.skyun.music.mode.Music;
import com.example.zk.activity.R;
import com.skyun.music.adapter.TabLayoutViewPagerAdapter;
import com.skyun.music.activity.my.local.MyMusicDanQu;
import com.skyun.music.activity.my.local.MyMusicGeShou;
import com.skyun.music.activity.my.local.MyMusicWenJianJia;
import com.skyun.music.activity.my.local.MyMusicZhuanJi;

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
