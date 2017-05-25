package com.example.zk.activity.fragment;


import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zk.activity.Data;
import com.example.zk.activity.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class NetMusicViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    //    当前页面
    private int currentPosition = 0;

    protected Activity mContext;
    protected ArrayList<View> views;
    protected ViewPager mViewPager;

    public NetMusicViewPagerAdapter(View view, Activity context, ArrayList<Integer> datas, final ViewPager viewPager) {
        mContext = context;
        views = new ArrayList<>();

        if (datas.size() > 1) {//如果数据大于一条
            datas.add(0, datas.get(datas.size() - 1));//添加最后一页到第一页
            datas.add(datas.get(1));//添加第一页(经过上行的添加已经是第二页了)到最后一页
        }
        for (int data : datas) {
            views.add(getItemView(data));
        }
        mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1, false);
        initPoint(view);

        final Timer timer = new Timer();


        final TimerTask task = new TimerTask() {
            @Override
            public void run() {

                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                });
                Data.showMyLog("run: ");
            }
        };


        timer.schedule(task, 0, 5000);

    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = views.get(position);
        container.addView(view);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Data.showMyLog("xl:arrive here." + position);
            }
        });

        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    protected View getItemView(int data) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(data);
        return imageView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {



        int newPosition = currentPosition;


        if (state != ViewPager.SCROLL_STATE_IDLE)
            return;//若viewpager滑动未停止，直接返回

        if (currentPosition == 0) {// 若当前为第一张，设置页面为倒数第二张
            int i = views.size() - 2;
            mViewPager.setCurrentItem(i, false);

            newPosition=i;

        } else if (currentPosition == views.size() - 1) {// 若当前为倒数第一张，设置页面为第二张

            mViewPager.setCurrentItem(1, false);
            newPosition=1;
        }



        newPosition-=1;
        //循环设置当前页的标记图
        int length = views.size() - 2;
        for (int i = 0; i < length; i++) {
            ivPointArray[newPosition].setBackgroundResource(R.drawable.ic_menu_camera);
            if (newPosition != i) {
                ivPointArray[i].setBackgroundResource(R.drawable.ic_menu_gallery);
            }
        }
    }


    //实例化原点View
    private ImageView iv_point;
    private ImageView[] ivPointArray;

    /**
     * 加载底部圆点
     */
    private void initPoint(View view) {
        ViewGroup vg = (ViewGroup) view.findViewById(R.id.guide_ll_point);//放置圆点
        int size = views.size() - 2;
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[size];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中

        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(mContext);
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            iv_point.setPadding(30, 0, 30, 0);//left,top,right,bottom
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.drawable.ic_menu_camera);
            } else {
                iv_point.setBackgroundResource(R.drawable.ic_menu_gallery);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }


    }


}
