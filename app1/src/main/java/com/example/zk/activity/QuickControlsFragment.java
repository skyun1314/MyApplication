package com.example.zk.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class QuickControlsFragment extends Fragment {

    private static QuickControlsFragment fragment;
    static View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.buttom_music_info, container, false);


        return rootView;
    }


    public static void update(){
        /*TextView music_name = (TextView) rootView.findViewById(R.id.buttom_music_info_music_name);
        music_name.setText();

        TextView music_name = (TextView) rootView.findViewById(R.id.buttom_music_info_music_auther);
        music_name.setText();*/
    }


    public static QuickControlsFragment newInstance() {

        if (fragment==null){
            fragment =new QuickControlsFragment();
        }

        return fragment;
    }

}