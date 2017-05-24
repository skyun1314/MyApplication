package com.example.zk.activity.fragment.my.local;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zk.activity.Music;
import com.example.zk.activity.R;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusicGeShou extends Fragment {

    public static final String TAG = "wodelog";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_my_music_local_list, container, false);

        ListView listView = (ListView) inflate.findViewById(R.id.fragment_my_music_local_list_listview);


        BaseAdapter adapter = new MyImgAdapter(getContext(), Music.getAllArtists(getContext()));
        listView.setAdapter(adapter);
        return inflate;
    }
    class MyImgAdapter extends MyMusicListAdapter {

        public MyImgAdapter(Context context, List<Map<String,Object>> musics) {
            super(context, musics);
        }

        @Override
        public void setView(final int position, ViewHolder holder, View convertView) {
              Map<String,Object> music = (Map<String, Object>) musics.get(position);
            //holder.img.setImageBitmap();
            holder.title.setText((String)music.get("name"));
            holder.info.setText((int)music.get("numOfSong")+" 首");


        }
    }


}