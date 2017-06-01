package com.skyun.music.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.zk.activity.R;


/**
 * Created by wm on 2016/2/4.
 */
public class ButtomMusicSetting extends DialogFragment {


    @Override
    public void onAttach(Activity activity){
        this.activity=activity;
        super.onAttach(activity);
    }
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式
       setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setAttributes(params);


        View view = inflater.inflate(R.layout.buttom_music_fragment_setting, container);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.6);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);

    }



}
