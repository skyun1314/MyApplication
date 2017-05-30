package com.example.zk.activity.fragment.my.local;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zk.activity.BaseActivity;
import com.example.zk.activity.Music;
import com.example.zk.activity.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusicDanQu1 extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_music_local_list);
        ListView listView = (ListView) findViewById(R.id.fragment_my_music_local_list_listview);

        Intent intent = getIntent();
        List<Music> list = (List<Music>) intent.getSerializableExtra("listMap");
        BaseAdapter adapter = new MyImgAdapter(this, list);
        listView.setAdapter(adapter);
    }

    class MyImgAdapter extends MyMusicListAdapter {


        public MyImgAdapter(Context context, List<Music> musics) {
            super(context, musics);
        }

        @Override
        public void setView(int position, ViewHolder holder, View convertView) {
            final Music music = (Music) musics.get(position);
            // holder.img.setImageBitmap(music.get(""));
            holder.title.setText((CharSequence) music.getTitle());
            holder.info.setText((CharSequence) music.getSinger());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Music.MusicUtil.setMusic((String) music.getUrl(),quickcontrolsfragment1);
                }
            });
        }
    }
}
