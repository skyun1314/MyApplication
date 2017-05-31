package com.example.zk.activity.fragment;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zk.activity.Data;
import com.example.zk.activity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

class NetMusicViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    int[] resIds = {R.mipmap.hotmusic, R.mipmap.hotmusic, R.mipmap.hotmusic
            , R.mipmap.hotmusic, R.mipmap.hotmusic};

    View inflate;
    //    当前页面
    private int currentPosition = 0;

    protected Activity mContext;
    protected List<Bitmap> views = new ArrayList<>();
    protected ViewPager mViewPager;

    public NetMusicViewPagerAdapter(View view, Activity context, final ViewPager viewPager) {
        mContext = context;

        inflate = view;

        ArrayList<Bitmap> res = new ArrayList<>();
        for (int i = 0; i < resIds.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resIds[i]);
            res.add(bitmap);
        }
        views = res;


        mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0, false);
        initPoint();

        final Timer timer = new Timer();


        final TimerTask task = new TimerTask() {
            @Override
            public void run() {

                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int Items = viewPager.getCurrentItem() + 1;
                        viewPager.setCurrentItem(Items);


                    }
                });
                Data.showMyLog("run: ");
            }
        };


        timer.schedule(task, 0, 5000);

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        position %= views.size();
        if (position < 0) {
            position = views.size() + position;
        }

        Bitmap bitmap = views.get(position);
        ImageView view = new ImageView(mContext);
        view.setImageBitmap(bitmap);
        view.setScaleType(ImageView.ScaleType.FIT_XY);

        container.addView(view);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Data.showMyLog("xl:arrive here.");
            }
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    protected View getItemView(Bitmap data) {
        ImageView imageView = new ImageView(mContext);
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(data);
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

        currentPosition %= views.size();
        if (currentPosition < 0) {
            currentPosition = views.size() + currentPosition;
        }

        int length = views.size();
        for (int i = 0; i < length; i++) {
            ivPointArray[currentPosition].setBackgroundResource(R.drawable.ic_menu_camera);
            if (currentPosition != i) {
                ivPointArray[i].setBackgroundResource(R.drawable.ic_menu_gallery);
            }
        }
    }


    //实例化原点View
    private ImageView[] ivPointArray;


    /**
     * 加载底部圆点
     */
    private void initPoint() {
        ViewGroup vg = (ViewGroup) inflate.findViewById(R.id.guide_ll_point);//放置圆点
        vg.removeAllViews();
        int size = views.size();
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[size];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中

        for (int i = 0; i < size; i++) {
            ImageView   iv_point = new ImageView(mContext);
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


    public void updataBitMap(List<Map<String, Object>> banner) {
        List<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0; i < banner.size(); i++) {
            Bitmap pic = (Bitmap) banner.get(i).get("pic");
            bitmaps.add(pic);
        }
        views = bitmaps;
        initPoint();
        notifyDataSetChanged();
    }
}