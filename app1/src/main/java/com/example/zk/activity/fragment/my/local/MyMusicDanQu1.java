package com.example.zk.activity.fragment.my.local;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zk.activity.R;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusicDanQu1 extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_music_local_list);
        ListView listView= (ListView) findViewById(R.id.fragment_my_music_local_list_listview);

        Intent intent = getIntent();
        List<Map<String, Object>> list = (List<Map<String, Object>>) intent.getSerializableExtra("listMap");
        BaseAdapter adapter=new MyImgAdapter(this,list);
        listView.setAdapter(adapter);
    }

    class MyImgAdapter extends MyMusicListAdapter{


        public MyImgAdapter(Context context, List<Map<String,Object>> musics) {
            super(context, musics);
        }

        @Override
        public void setView(int position, ViewHolder holder, View convertView) {
             Map<String,Object> music = (Map<String,Object>) musics.get(position);
           // holder.img.setImageBitmap(music.get(""));
            holder.title.setText((CharSequence) music.get("tilte"));
            holder.info.setText((CharSequence) music.get("artist"));


        }
    }

}
