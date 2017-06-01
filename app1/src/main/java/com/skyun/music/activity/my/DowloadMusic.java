package com.skyun.music.activity.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zk.activity.R;
import com.skyun.music.activity.BaseActivity;
import com.skyun.music.adapter.MyMusicListAdapter;
import com.skyun.music.mode.Data;
import com.skyun.music.mode.Music;

import java.util.List;

public class DowloadMusic extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_music_local_list);
        ListView listView = (ListView) findViewById(R.id.fragment_my_music_local_list_listview);


        List<Music> musicInfo =
                Music.MusicUtil.getMusicInfo(Data.APP_FILE_PATH,this);

            BaseAdapter adapter = new MyImgAdapter(this, musicInfo);
            listView.setAdapter(adapter);



    }
    class MyImgAdapter extends MyMusicListAdapter {

        public MyImgAdapter(Activity context, List<Music> musics) {
            super(context, musics);
        }

        @Override
        public void setView(final int position, ViewHolder holder, View convertView) {
            final Music music = (Music) musics.get(position);
            // holder.img.setImageBitmap(music.get(""));
            holder.title.setText((CharSequence) music.getTitle());
            holder.info.setText((CharSequence) music.getSinger());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Music.MusicUtil.setMusic(musics,position,quickcontrolsfragment1);
                }
            });
        }
    }


}