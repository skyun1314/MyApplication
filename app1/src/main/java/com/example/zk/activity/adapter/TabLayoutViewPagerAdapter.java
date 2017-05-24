package com.example.zk.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zk on 2017/5/22.
 */

public class TabLayoutViewPagerAdapter extends FragmentPagerAdapter{


        private List<Fragment> list_fragment;                         //fragment列表
        private String[] list_Title;                              //tab名的列表



        public TabLayoutViewPagerAdapter(FragmentManager fm, List<Fragment> list_fragment, String[] list_Title) {
            super(fm);
            this.list_fragment = list_fragment;
            this.list_Title = list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_fragment.size();
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {

            return list_Title[position];
        }

}
