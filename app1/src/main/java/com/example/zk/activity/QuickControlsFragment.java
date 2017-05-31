package com.example.zk.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class QuickControlsFragment extends Fragment {
     TextView music_name;
     TextView music_auther;
    private static QuickControlsFragment fragment;

    public static QuickControlsFragment newInstance() {
        return new QuickControlsFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  rootView = inflater.inflate(R.layout.buttom_music_info, container, false);
         music_name = (TextView) rootView.findViewById(R.id.buttom_music_info_music_name);


         music_auther = (TextView) rootView.findViewById(R.id.buttom_music_info_music_auther);
        Data.showMyLog(music_name.toString());
        return rootView;
    }


    public  void update(){
        Data.showMyLog(music_name.toString());
        music_auther.setText(Music.MusicUtil.theWholeMusic.getSinger());
        music_name.setText(Music.MusicUtil.theWholeMusic.getTitle());
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Music.MusicUtil.theWholeMusic!=null){
            music_name.setText(Music.MusicUtil.theWholeMusic.getTitle());
            music_auther.setText(Music.MusicUtil.theWholeMusic.getSinger());
        }

    }

}