package com.example.zk.activity.fragment;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zk.activity.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NetMusicViewPagerAdapter extends LoopPagerAdapter {
    int[] imgs = new int[]{
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
    };

    List<Bitmap> bitmaps=new ArrayList<>();
    Activity Myactivity;
    public NetMusicViewPagerAdapter(RollPagerView viewPager,Activity activity) {
        super(viewPager);
        Myactivity=activity;
        for (int i = 0; i < imgs.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(Myactivity.getResources(), imgs[i]);

            bitmaps.add(bitmap);
        }
    }

    @Override
    public View getView(ViewGroup container, int position) {




        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setImageBitmap(bitmaps.get(position));
        return view;
    }

    @Override
    public int getRealCount() {
        return bitmaps.size();
    }

    public void updataBitMap(List<Map<String, Object>> banner) {

        List<Bitmap> bitmaps=new ArrayList<>();
        for (int i = 0; i < banner.size(); i++) {

            bitmaps.add((Bitmap) banner.get(i).get("pic"));
        }
        this.bitmaps=bitmaps;
        notifyDataSetChanged();

    }
}