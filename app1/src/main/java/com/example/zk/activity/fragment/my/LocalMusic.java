package com.example.zk.activity.fragment.my;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zk.activity.Music;
import com.example.zk.activity.R;

import java.util.List;

public class LocalMusic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_music_local);

        ListView listView= (ListView) findViewById(R.id.fragment_my_music_local_listview);


        BaseAdapter adapter=new MyImgAdapter(this,Music.getAlldata(this));
        listView.setAdapter(adapter);
    }


    class MyImgAdapter extends BaseAdapter {
        List<Music> musics;
        private Context context;//用于接收传递过来的Context对象
        private LayoutInflater mInflater = null;
        public MyImgAdapter(Context context,List<Music> musics) {
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

         class ViewHolder
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

                convertView = mInflater.inflate(R.layout.fragment_my_music_local_item, null);;
                holder.img = (ImageView)convertView.findViewById(R.id.fragment_my_music_local_item_icon);
                holder.title = (TextView)convertView.findViewById(R.id.fragment_my_music_local_item_name);
                holder.info = (TextView)convertView.findViewById(R.id.fragment_my_music_local_item_author);
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            Music music = musics.get(position);
            holder.img.setImageBitmap(music.getBitmap());
            holder.title.setText(music.getTitle());
            holder.info.setText(music.getSinger());
            return convertView;
        }



    }



}
