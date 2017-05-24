package com.example.zk.activity.fragment.my;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zk.activity.Data;
import com.example.zk.activity.R;
import com.example.zk.activity.fragment.my.local.MyMusicListAdapter;
import com.example.zk.activity.fragment.my.local.MyMusicWenJianJia;

import java.util.List;
import java.util.Map;

public class DowloadMusic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_music_local_list);
        ListView listView = (ListView) findViewById(R.id.fragment_my_music_local_list_listview);

        String files[]={Data.APP_FILE_PATH};
        List<Map<String, Object>> musicWenJianJia = MyMusicWenJianJia.getMusicWenJianJia(files);
        if (musicWenJianJia.size()!=0){
            Map<String, Object> map = musicWenJianJia.get(0);
            List<Map<String, Object>> maplist    = (List<Map<String, Object>>) map.get("list");
            BaseAdapter adapter = new MyImgAdapter(this, maplist);
            listView.setAdapter(adapter);
        }



    }
    class MyImgAdapter extends MyMusicListAdapter {

        public MyImgAdapter(Context context, List<Map<String, Object>> musics) {
            super(context, musics);
        }

        @Override
        public void setView(final int position, ViewHolder holder, View convertView) {
            Map<String,Object> music = (Map<String, Object>) musics.get(position);
            //holder.img.setImageBitmap();
            holder.title.setText((String)music.get("tilte"));
            holder.info.setText((int)music.get("artist"));


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


}