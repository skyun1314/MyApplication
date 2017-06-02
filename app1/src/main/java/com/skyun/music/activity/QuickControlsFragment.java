package com.skyun.music.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zk.activity.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.skyun.music.mode.Data;
import com.skyun.music.mode.Music;
import com.skyun.music.mode.MyCycleView;

import java.util.List;

import static com.skyun.music.mode.Music.MusicUtil.mPlayer;


public class QuickControlsFragment extends Fragment implements View.OnClickListener {

    private static QuickControlsFragment fragment;
    private ImageView play;
    private ImageView setting;
    private Context context;

    public static QuickControlsFragment newInstance() {
        return new QuickControlsFragment();
    }

    QuickControlsFragmentAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buttom_music, container, false);
        context = getContext();
        RollPagerView mViewPager = (RollPagerView) rootView.findViewById(R.id.buttom_music_view_pager);
        pagerAdapter = new QuickControlsFragmentAdapter(mViewPager, getActivity());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.mHintView.setVisibility(View.GONE);


        play = (ImageView) rootView.findViewById(R.id.buttom_music_info_play);
        setting = (ImageView) rootView.findViewById(R.id.buttom_music_info_setting);

        play.setOnClickListener(this);
        setting.setOnClickListener(this);

        return rootView;
    }


    public void update() {
        pagerAdapter.musics = Music.MusicUtil.theWholeMusic;
        pagerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttom_music_info_play:
                Drawable.ConstantState drawable = play.getDrawable().getConstantState();

                Drawable.ConstantState play1 = context.getResources().getDrawable(R.drawable.buttom_music_play).getConstantState();
                Drawable.ConstantState pause = context.getResources().getDrawable(R.drawable.buttom_music_pause).getConstantState();

                if (drawable.equals(play1)) {
                    play.setImageDrawable(context.getResources().getDrawable(R.drawable.buttom_music_pause));
                } else if (drawable.equals(pause)) {
                    play.setImageDrawable(context.getResources().getDrawable(R.drawable.buttom_music_play));

                }
                Music.MusicUtil.playOrPaus();
                pagerAdapter.notifyDataSetChanged();

                break;
            case R.id.buttom_music_info_setting:

                ButtomMusicSetting playQueueFragment = new ButtomMusicSetting();
                playQueueFragment.show(getFragmentManager(), "playqueueframent");


                break;
        }
    }


    public class QuickControlsFragmentAdapter extends LoopPagerAdapter implements ViewPager.OnPageChangeListener {

        List<Music> musics = Music.MusicUtil.theWholeMusic;

        Activity Myactivity;
        RollPagerView viewPager;

        public QuickControlsFragmentAdapter(RollPagerView viewPager, Activity activity) {
            super(viewPager);
            Myactivity = activity;
            this.viewPager = viewPager;
            viewPager.getViewPager().addOnPageChangeListener(this);
        }

        @Override
        public View getView(ViewGroup container, final int position) {

            Music music = musics.get(position);
            View inflate = LayoutInflater.from(Myactivity).inflate(R.layout.buttom_music_item, null);
            TextView music_name = (TextView) inflate.findViewById(R.id.buttom_music_info_music_name);
            TextView music_auther = (TextView) inflate.findViewById(R.id.buttom_music_info_music_auther);
            MyCycleView pic = (MyCycleView) inflate.findViewById(R.id.buttom_music_info_pic);
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Myactivity, MusicPlayActivity.class);
                    Myactivity.startActivity(intent);
                    // Myactivity.overridePendingTransition(R.anim.activity_open,R.anim.activity_stay);
                }
            });

            music_auther.setText(music.getSinger());
            music_name.setText(music.getTitle());

            if (mPlayer.isPlaying()) {
                pic.start();
            } else {
                pic.stop();
            }


            //
            //  Bitmap bitmap = StackBlur.blur(bitmaps.get(position), 20, false);

            // cycleView.reSetBitMap(bitmap);


            return inflate;
        }

        @Override
        public int getRealCount() {

            if (musics == null) {
                return 0;
            }

            return musics.size();
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

            Data.showMyLog("onPageSelected:" + position + ":" + (position % getRealCount()));

            Music.MusicUtil.PlayByNum(position % getRealCount());
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}