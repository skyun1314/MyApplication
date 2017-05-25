package com.example.zk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by zk on 2017/5/24.
 */

public class BaseActivity extends AppCompatActivity {
    private QuickControlsFragment fragment; //底部播放控制栏
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
                if (fragment == null) {
                    Data.showMyLog("我是空的：showQuickControl，我要重新创建");
                    fragment =new QuickControlsFragment();// QuickControlsFragment.newInstance();
                    ft.add(R.id.app_bar_main_bottom_container, fragment).commitAllowingStateLoss();
                } else {
                    Data.showMyLog("我不是空的：showQuickControl");
                    ft.show(fragment).commitAllowingStateLoss();
                }
            } else {
                if (fragment != null)
                    ft.hide(fragment).commitAllowingStateLoss();
            }


       /* else {
            if (fragment != null)
                ft.hide(fragment).commitAllowingStateLoss();
        }*/
    }

}
