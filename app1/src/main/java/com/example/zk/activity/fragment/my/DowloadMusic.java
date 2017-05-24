package com.example.zk.activity.fragment.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zk.activity.Data;
import com.example.zk.activity.Music;
import com.example.zk.activity.R;
import com.example.zk.activity.fragment.my.local.MyMusicDanQu1;
import com.example.zk.activity.fragment.my.local.MyMusicListAdapter;
import com.example.zk.activity.fragment.my.local.MyMusicWenJianJia;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DowloadMusic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_music_local_list);
        ListView listView = (ListView) findViewById(R.id.fragment_my_music_local_list_listview);

        String files[]={Data.APP_FILE_PATH};

        BaseAdapter adapter = new MyImgAdapter(this, MyMusicWenJianJia.getMusicWenJianJia(files));
        listView.setAdapter(adapter);
    }
    class MyImgAdapter extends MyMusicListAdapter {

        public MyImgAdapter(Context context, List<Map<String, Object>> musics) {
            super(context, musics);
        }

        @Override
        public void setView(final int position, ViewHolder holder, View convertView) {
            Map<String,Object> music = (Map<String, Object>) musics.get("list");
            //holder.img.setImageBitmap();
            holder.title.setText((String)music.get("name"));
            holder.info.setText((int)music.get("numOfSong")+" 首");

            List<Map<String, Object>> id =
                    Music.getMusicInfo(context,
                            MediaStore.Audio.Media.ALBUM_ID, (int) music.get("id") + "");

            id.get()

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> music = (Map<String, Object>) musics.get(position);
                    List<Map<String, Object>> id =
                            Music.getMusicInfo(context,
                                    MediaStore.Audio.Media.ALBUM_ID, (int) music.get("id") + "");
                    Intent intent = new Intent(getActivity(), MyMusicDanQu1.class);
                    intent.putExtra("listMap", (Serializable) id);
                    startActivity(intent);

                }
            });
        }
    }


}