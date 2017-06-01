package com.skyun.music.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zk.activity.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.skyun.music.mode.MyCycleView;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);


        final ImageView imageView= (ImageView) findViewById(R.id.albumArt);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.actionbar_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });


        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(MusicPlayActivity.this.getResources(), R.mipmap.hotmusic);

              //  final Bitmap newBitmap = StackBlur.blur(bitmap, 20, false);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     //   imageView.setImageBitmap(newBitmap);
                    }
                });

            }
        }).start();


        RollPagerView mViewPager = (RollPagerView) findViewById(R.id.view_pager);

        mViewPager.mHintView.setVisibility(View.GONE);
        QuickControlsFragmentAdapter pagerAdapter = new QuickControlsFragmentAdapter(mViewPager, this);
        mViewPager.setAdapter(pagerAdapter);

    }


    public class QuickControlsFragmentAdapter extends LoopPagerAdapter {
        int[] imgs = new int[]{
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5,
        };

        List<Bitmap> bitmaps = new ArrayList<>();
        Activity Myactivity;
        private List<String> urls;

        public QuickControlsFragmentAdapter(RollPagerView viewPager, Activity activity) {
            super(viewPager);
            Myactivity = activity;
            for (int i = 0; i < imgs.length; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(Myactivity.getResources(), imgs[i]);

                bitmaps.add(bitmap);
            }
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            MyCycleView cycleView=new MyCycleView(Myactivity);
            Bitmap bitmap = bitmaps.get(position);

            cycleView.reSetBitMap(bitmap);




            return cycleView;
        }

        @Override
        public int getRealCount() {
            return bitmaps.size();
        }

    }


}
