package com.skyun.music.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.zk.activity.R;
import com.hehe.MyAES;
import com.hehe.WYYAPI;
import com.skyun.music.adapter.MyMusicListAdapter;
import com.skyun.music.mode.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindMusic extends Fragment {

    View inflater1;
    Random random = new Random();
    public static Handler handler;
    QuickControlsFragment quickcontrolsfragment1;

    public FindMusic(QuickControlsFragment quickcontrolsfragment1) {
        this.quickcontrolsfragment1 = quickcontrolsfragment1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_find_music, container, false);
        this.inflater1 = inflate;


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                List<Map<String, Object>> new1 = (List<Map<String, Object>>) msg.obj;
                int i = random.nextInt(new1.size());
                Map<String, Object> stringObjectMap = new1.get(i);
                String title = (String) stringObjectMap.get("title");
                Bitmap pic = (Bitmap) stringObjectMap.get("pic");

                List<Map<String, Object>> music = (List<Map<String, Object>>) stringObjectMap.get("music");


                ImageView imageView = (ImageView) inflater1.findViewById(R.id.fragment_find_music_im1);

                imageView.setImageBitmap(pic);

                ListView listView = (ListView) inflater1.findViewById(R.id.fragment_find_music_lv1);
                BaseAdapter adapter = new MyImgAdapter(getActivity(), music);
                listView.setAdapter(adapter);


                return false;
            }
        });


        return inflate;
    }


    class MyImgAdapter extends MyMusicListAdapter {


        public MyImgAdapter(Activity context, List<Map<String, Object>> musics) {
            super(context, musics);
        }


        public void getAllNetMusic(final int index) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Music> musicList = new ArrayList<Music>();
                    for (int i = 0; i < musics.size(); i++) {
                        Map<String, Object> music = (Map<String, Object>) musics.get(i);
                        String musicurl = (String) music.get("musicurl");

                        String a = "http://music.163.com/song?id=";
                        final String substring = musicurl.substring(a.length());

                        String musicByKeyWord = WYYAPI.discover.getMusicByKeyWord(MyAES.getWhat.getMusicByid, substring);
                        try {
                            JSONObject jsonObject = new JSONObject(musicByKeyWord);
                            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                            JSONObject o = (JSONObject) jsonArray.get(0);
                            String url = o.getString("url");
                            Music music1 = new Music();
                            music1.setUrl(url);

                            musicList.add(music1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Music.MusicUtil.setMusic(musicList, index, quickcontrolsfragment1);
                }
            }).start();
        }


        @Override
        public void setView(final int position, MyMusicListAdapter.ViewHolder holder, View convertView) {
            final Map<String, Object> music = (Map<String, Object>) musics.get(position);
            //holder.img.setImageBitmap(music.getBitmap());
            holder.title.setText((String) music.get("musicname"));

            holder.info.setText("");

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAllNetMusic(position);

                }
            });

        }
    }

}
