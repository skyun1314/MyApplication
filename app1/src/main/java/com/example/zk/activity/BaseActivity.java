package com.example.zk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by zk on 2017/5/24.
 */

public class BaseActivity extends AppCompatActivity {





    public QuickControlsFragment quickcontrolsfragment1; //底部播放控制栏
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showQuickControl(true);
    }

    /**
     * @param show 显示或关闭底部播放控制栏
     */




    protected void showQuickControl(boolean show) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (show) {
                if (quickcontrolsfragment1 == null) {
                    Data.showMyLog("我是空的：showQuickControl，我要重新创建");
                    quickcontrolsfragment1 =new QuickControlsFragment();// QuickControlsFragment.newInstance();
                    ft.add(R.id.app_bar_main_bottom_container, quickcontrolsfragment1).commitAllowingStateLoss();
                } else {
                    Data.showMyLog("我不是空的：showQuickControl");
                    ft.show(quickcontrolsfragment1).commitAllowingStateLoss();
                }
            } else {
                if (quickcontrolsfragment1 != null)
                    ft.hide(quickcontrolsfragment1).commitAllowingStateLoss();
            }


       /* else {
            if (fragment != null)
                ft.hide(fragment).commitAllowingStateLoss();
        }*/
    }

}
