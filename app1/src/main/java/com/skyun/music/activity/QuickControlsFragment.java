package com.skyun.music.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zk.activity.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.skyun.music.mode.Music;

import java.util.ArrayList;
import java.util.List;


public class QuickControlsFragment extends Fragment {
   /*  TextView music_name;
     TextView music_auther;*/
    private static QuickControlsFragment fragment;

    public static QuickControlsFragment newInstance() {
        return new QuickControlsFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  rootView = inflater.inflate(R.layout.buttom_music, container, false);
       //  music_name = (TextView) rootView.findViewById(R.id.buttom_music_info_music_name);


        // music_auther = (TextView) rootView.findViewById(R.id.buttom_music_info_music_auther);


        RollPagerView mViewPager = (RollPagerView) rootView.findViewById(R.id.buttom_music_view_pager);
        QuickControlsFragmentAdapter pagerAdapter = new QuickControlsFragmentAdapter(mViewPager, getActivity());
        mViewPager.setAdapter(pagerAdapter);
       mViewPager.mHintView.setVisibility(View.GONE);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"haha",Toast.LENGTH_SHORT).show();;
            }
        });

        return rootView;
    }


    public  void update(){
       /* Data.showMyLog(music_name.toString());
        music_auther.setText(Music.MusicUtil.theWholeMusic.getSinger());
        music_name.setText(Music.MusicUtil.theWholeMusic.getTitle());*/
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Music.MusicUtil.theWholeMusic!=null){
           /* music_name.setText(Music.MusicUtil.theWholeMusic.getTitle());
            music_auther.setText(Music.MusicUtil.theWholeMusic.getSinger());*/
        }

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

    }

}