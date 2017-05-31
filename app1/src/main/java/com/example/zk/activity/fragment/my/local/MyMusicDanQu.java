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
import com.example.zk.activity.QuickControlsFragment;
import com.example.zk.activity.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMusicDanQu extends Fragment {
    QuickControlsFragment quickcontrolsfragment1;
    public MyMusicDanQu(QuickControlsFragment quickcontrolsfragment1) {
        this.quickcontrolsfragment1=quickcontrolsfragment1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_my_music_local_list, container, false);

        ListView listView= (ListView) inflate.findViewById(R.id.fragment_my_music_local_list_listview);


        BaseAdapter adapter=new MyImgAdapter(getContext(), Music.MusicUtil.getAllMusic(getContext()));
        listView.setAdapter(adapter);
        return inflate;
    }




    class MyImgAdapter extends MyMusicListAdapter{


        public MyImgAdapter(Context context, List<Music> musics) {
            super(context, musics);
        }

        @Override
        public void setView(int position, ViewHolder holder, View convertView) {
            final Music music = (Music) musics.get(position);
            holder.img.setImageBitmap(music.getBitmap());
            holder.title.setText(music.getTitle());
            holder.info.setText(music.getSinger());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Music.MusicUtil.setMusic(music,quickcontrolsfragment1);
                }
            });

        }
    }

}
