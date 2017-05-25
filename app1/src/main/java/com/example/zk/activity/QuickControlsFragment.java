package com.example.zk.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class QuickControlsFragment extends Fragment {

    private static QuickControlsFragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buttom_music_info, container, false);


        return rootView;
    }


    public static QuickControlsFragment newInstance() {

        if (fragment==null){
            fragment =new QuickControlsFragment();
        }

        return fragment;
    }

}