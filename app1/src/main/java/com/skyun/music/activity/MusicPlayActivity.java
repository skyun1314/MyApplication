package com.skyun.music.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zk.activity.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.skyun.music.mode.Music;
import com.skyun.music.mode.MyCycleView;
import com.skyun.music.util.LrcView;

import java.util.ArrayList;
import java.util.List;

import static com.skyun.music.mode.Music.MusicUtil.mPlayer;

public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener {
    Music music;
    private TextView music_duration_played;
    private TextView music_duration;
    private SeekBar seekBar;
    ActionBar ab;
    ImageView playing_play;
    ImageView playing_next;
    ImageView playing_pre;
    RelativeLayout elativeLayout;
    RelativeLayout relativeLayout;
    QuickControlsFragmentAdapter pagerAdapter;
    private LrcView mLrcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);

        relativeLayout = (RelativeLayout) findViewById(R.id.lrcviewContainer);
        elativeLayout = (RelativeLayout) findViewById(R.id.activity_music_play_RelativeLayout);

        final ImageView imageView = (ImageView) findViewById(R.id.albumArt);
        music_duration_played = (TextView) findViewById(R.id.music_duration_played);
        music_duration = (TextView) findViewById(R.id.music_duration);
        playing_play = (ImageView) findViewById(R.id.playing_play);
        playing_pre = (ImageView) findViewById(R.id.playing_pre);
        playing_next = (ImageView) findViewById(R.id.playing_next);
        seekBar = (SeekBar) findViewById(R.id.play_seek);

        playing_play.setOnClickListener(this);
        playing_next.setOnClickListener(this);
        playing_pre.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.actionbar_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mLrcView = (LrcView) findViewById(R.id.lrc);

        mLrcView.loadLrcByAsste("lrc.lrc");


        setSeekBarListener();
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
        pagerAdapter = new QuickControlsFragmentAdapter(mViewPager, this);
        mViewPager.setAdapter(pagerAdapter);
        elativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elativeLayout.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        music = Music.MusicUtil.theWholeMusic.get(Music.MusicUtil.theWholeMusicIndex);
        music_duration.setText(Music.MusicUtil.formatTime(music.getDuration()));
        seekBar.setMax((int) music.getDuration());
        ab.setTitle(music.getTitle());
        ab.setSubtitle(music.getSinger());
        handler.postDelayed(mUpdateProgress, 0);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });


    private Runnable mUpdateProgress = new Runnable() {

        @Override
        public void run() {

            if (seekBar != null) {
                long position = Music.MusicUtil.mPlayer.getCurrentPosition();
                long duration = Music.MusicUtil.mPlayer.getDuration();
                if (duration > 0 && duration < 627080716) {
                    seekBar.setProgress((int) (position));
                    mLrcView.updateTime(position);
                    music_duration_played.setText(Music.MusicUtil.formatTime(position));
                }

                if (Music.MusicUtil.mPlayer.isPlaying()) {
                    seekBar.postDelayed(mUpdateProgress, 200);
                } else {
                    seekBar.removeCallbacks(mUpdateProgress);
                }
            }
        }
    };

    int xx = 0;

    private void setSeekBarListener() {

        if (seekBar != null)
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress = 0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    //mLrcView.seekTo(i, true, b);
                    if (b) {
                        xx = i;
                        music_duration_played.setText(Music.MusicUtil.formatTime(i));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Music.MusicUtil.mPlayer.seekTo(xx);
                }
            });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        seekBar.removeCallbacks(mUpdateProgress);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        seekBar.removeCallbacks(mUpdateProgress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playing_play:
                Drawable.ConstantState drawable = playing_play.getDrawable().getConstantState();

                Drawable.ConstantState play1 = getResources().getDrawable(R.drawable.play_rdi_btn_play).getConstantState();
                Drawable.ConstantState pause = getResources().getDrawable(R.drawable.play_rdi_btn_pause).getConstantState();

                if (drawable.equals(play1)) {
                    playing_play.setImageDrawable(getResources().getDrawable(R.drawable.play_rdi_btn_pause));
                } else if (drawable.equals(pause)) {
                    playing_play.setImageDrawable(getResources().getDrawable(R.drawable.play_rdi_btn_play));

                }
                Music.MusicUtil.playOrPaus();
                pagerAdapter.notifyDataSetChanged();
                break;

            case R.id.playing_pre:
                break;
            case R.id.playing_next:
                break;
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
            MyCycleView cycleView = new MyCycleView(Myactivity);
            Bitmap bitmap = bitmaps.get(position);

            cycleView.reSetBitMap(bitmap);

            if (mPlayer.isPlaying()) {
                cycleView.start();
            } else {
                cycleView.stop();
            }


            return cycleView;
        }

        @Override
        public int getRealCount() {
            return bitmaps.size();
        }

    }


}
