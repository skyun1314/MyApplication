package com.skyun.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zk.activity.R;

import java.util.List;

/**
 * Created by zk on 2017/5/23.
 */

public abstract class MyMusicListAdapter<T> extends BaseAdapter {

        public List<T> musics;
        public Context context;//用于接收传递过来的Context对象
        private LayoutInflater mInflater = null;
        public MyMusicListAdapter(Context context,List<T> musics) {
            super();
            this.context = context;
            this.musics=musics;
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return musics.size();
        }

        @Override
        public Object getItem(int position) {
            return musics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder
        {
            public ImageView img;
            public TextView title;
            public TextView info;
        }
        //然后重写getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null)
            {
                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.fragment_my_music_local_list_item, null);;
                holder.img = (ImageView)convertView.findViewById(R.id.fragment_my_music_local_list_item_icon);
                holder.title = (TextView)convertView.findViewById(R.id.fragment_my_music_local_list_item_name);
                holder.info = (TextView)convertView.findViewById(R.id.fragment_my_music_local_list_item_author);
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder)convertView.getTag();
            }
           /* T music = musics.get(position);
            holder.img.setImageBitmap(music.getBitmap());
            holder.title.setText(music.getTitle());
            holder.info.setText(music.getSinger());*/
            setView(position,holder,convertView);
            return convertView;
        }


        public abstract void setView(int position,ViewHolder holder, View convertView);

}
