package com.example.zk.activity.fragment.my.local;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zk.activity.Music;
import com.example.zk.activity.R;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusicWenJianJia extends Fragment {

    String TAG = "wodelog";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_my_music_local_list, container, false);

        ListView listView = (ListView) inflate.findViewById(R.id.fragment_my_music_local_list_listview);

        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String files[]={sdcardPath + "/qqmusic/song/",sdcardPath + "/netease/cloudmusic/music/"};



        BaseAdapter adapter = new MyImgAdapter(getContext(), getMusicWenJianJia(files));
        listView.setAdapter(adapter);
        return inflate;
    }

    class MyImgAdapter extends MyMusicListAdapter {

        public MyImgAdapter(Context context, List<Map<String, Object>> musics) {
            super(context, musics);
        }

        @Override
        public void setView(final int position, ViewHolder holder, View convertView) {
            Map<String, Object> music = (Map<String, Object>) musics.get(position);
            //holder.img.setImageBitmap();
            final List<Map<String, Object>> strings = (List<Map<String, Object>>) music.get("list");
            holder.title.setText(strings.size() + " 首");
            holder.info.setText((String) music.get("fileName"));


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), MyMusicDanQu1.class);
                    intent.putExtra("listMap", (Serializable) strings);
                    startActivity(intent);

                }
            });
        }
    }

    public static List<Map<String, Object>> getMusicWenJianJia(String fileName[]) {
        List<Map<String, Object>> Maplists = new ArrayList<>();

        for (int j = 0; j < fileName.length; j++) {
            File file = new File(fileName[j]);
            File[] list = file.listFiles();
            if (list != null&&list.length>0) {
                List<Map<String, Object>> maplist = new ArrayList<>();

                for (int i = 0; i < list.length; i++) {
                    Map<String, Object> musicInfoByFile = Music.getMusicInfoByFile(list[i].getAbsolutePath());
                    maplist.add(musicInfoByFile);
                }

                Map map = new HashMap();
                map.put("list", maplist);
                map.put("fileName", fileName);
                Maplists.add(map);
            }
        }
        return Maplists;

    }

}