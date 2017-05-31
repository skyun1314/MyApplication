package com.skyun.music.activity.my.local;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.skyun.music.mode.Music;
import com.example.zk.activity.R;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusicGeShou extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_my_music_local_list, container, false);

        ListView listView = (ListView) inflate.findViewById(R.id.fragment_my_music_local_list_listview);


        BaseAdapter adapter = new MyImgAdapter(getContext(), Music.MusicUtil.getAllArtists(getContext()));
        listView.setAdapter(adapter);
        return inflate;
    }
    class MyImgAdapter extends MyMusicListAdapter {

        public MyImgAdapter(Context context, List<Map<String,Object>> musics) {
            super(context, musics);
        }

        @Override
        public void setView(final int position, ViewHolder holder, View convertView) {
              final Map<String,Object> music = (Map<String, Object>) musics.get(position);
            //holder.img.setImageBitmap();
            holder.title.setText((String)music.get("name"));
            holder.info.setText((int)music.get("numOfSong")+" 首");

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Music> id =
                            Music.MusicUtil.getMusicInfo(context,
                                    MediaStore.Audio.Media.ARTIST_ID, (int) music.get("id") + "");
                    Intent intent = new Intent(getActivity(), MyMusicDanQu1.class);
                    intent.putExtra("listMap", (Serializable) id);
                    startActivity(intent);

                }
            });
        }
    }


}