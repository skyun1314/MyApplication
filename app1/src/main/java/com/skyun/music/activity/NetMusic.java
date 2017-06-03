package com.skyun.music.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zk.activity.R;
import com.hehe.MyAES;
import com.hehe.WYYAPI;
import com.jude.rollviewpager.RollPagerView;
import com.skyun.music.activity.my.BuyMusic;
import com.skyun.music.activity.net.DianTai;
import com.skyun.music.activity.net.FengLei;
import com.skyun.music.activity.net.Geshou;
import com.skyun.music.activity.net.MV;
import com.skyun.music.activity.net.Paihang;
import com.skyun.music.adapter.MyMusicListAdapter;
import com.skyun.music.adapter.NetMusicViewPagerAdapter;
import com.skyun.music.mode.Data;
import com.skyun.music.mode.Music;
import com.skyun.music.util.wyyapi.NetWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetMusic extends Fragment {

    View inflate;
    public static Handler handler;
    NetMusicViewPagerAdapter pagerAdapter;
     QuickControlsFragment quickcontrolsfragment1;
    public NetMusic(QuickControlsFragment quickcontrolsfragment1) {
        this.quickcontrolsfragment1=quickcontrolsfragment1;
    }

    MyImgAdapter myImgAdapter1 = null;
    MyImgAdapter myImgAdapter2= null;
    MyImgAdapter myImgAdapter3= null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_net_music, container, false);
        viewPagerInit(inflate);


        setItem(R.id.fragment_net_music_geshou, R.drawable.ic_menu_camera, "歌手", Geshou.class);
        setItem(R.id.fragment_net_music_paihang, R.drawable.ic_menu_gallery, "排行 \u3000", Paihang.class);
        setItem(R.id.fragment_net_music_diantai, R.drawable.ic_menu_manage, "电台\u3000\u3000", DianTai.class);
        setItem(R.id.fragment_net_music_fenlei, R.drawable.ic_menu_send, "分类歌单", FengLei.class);
        setItem(R.id.fragment_net_music_mv, R.drawable.ic_menu_share, "视频MV", MV.class);
        setItem(R.id.fragment_net_music_shuzi, R.drawable.ic_menu_slideshow, "数字专辑", BuyMusic.class);


        setHot1(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot1, Geshou.class);
        setHot1(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot2, Geshou.class);
        setHot1(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot3, Geshou.class);
        setHot1(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot1, Geshou.class);
        setHot1(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot2, Geshou.class);
        setHot1(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot3, Geshou.class);

        setHot1(R.id.fragment_net_music_new1, R.id.music_tuijian2_iv1, Geshou.class);
        setHot1(R.id.fragment_net_music_new1, R.id.music_tuijian2_iv2, Geshou.class);
        setHot1(R.id.fragment_net_music_new2, R.id.music_tuijian2_iv1, Geshou.class);
        setHot1(R.id.fragment_net_music_new2, R.id.music_tuijian2_iv2, Geshou.class);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what){
                    case 1:
                        Map<String, Object> mymap = (Map<String, Object>) msg.obj;

                        List<Map<String, Object>> banner = (List<Map<String, Object>>) mymap.get("banner");
                        List<Map<String, Object>> hot = (List<Map<String, Object>>) mymap.get("hot");
                        List<Map<String, Object>> new1 = (List<Map<String, Object>>) mymap.get("new");
                        List<Map<String, Object>> bangDan = (List<Map<String, Object>>) mymap.get("bangDan");

                        pagerAdapter.updataBitMap(banner);


                        int ids11[] = {R.id.fragment_find_music_im1, R.id.fragment_find_music_lv1};
                        int ids22[] = {R.id.fragment_find_music_im2, R.id.fragment_find_music_lv2};
                        int ids33[] = {R.id.fragment_find_music_im3, R.id.fragment_find_music_lv3};



                        showbangDan1(bangDan.get(0), getActivity(), ids11);
                        showbangDan2(bangDan.get(1), getActivity(), ids22);
                        showbangDan3(bangDan.get(2), getActivity(), ids33);


                        int ids1[] = {R.id.music_tuijian3_im1, R.id.music_tuijian3_src1, R.id.music_tuijian3_num};
                        int ids2[] = {R.id.music_tuijian2_item_title, R.id.music_tuijian2_item_name, R.id.music_tuijian2_item_auther};


                        setHot(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot1, hot.get(0), ids1);
                        setHot(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot2, hot.get(1), ids1);
                        setHot(R.id.fragment_net_music_hot1, R.id.music_tuijian3_hot3, hot.get(2), ids1);
                        setHot(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot1, hot.get(3), ids1);
                        setHot(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot2, hot.get(4), ids1);
                        setHot(R.id.fragment_net_music_hot2, R.id.music_tuijian3_hot3, hot.get(5), ids1);


                        setHot(R.id.fragment_net_music_new1, R.id.music_tuijian2_iv1, new1.get(0), ids2);
                        setHot(R.id.fragment_net_music_new1, R.id.music_tuijian2_iv2, new1.get(1), ids2);
                        setHot(R.id.fragment_net_music_new2, R.id.music_tuijian2_iv1, new1.get(2), ids2);
                        setHot(R.id.fragment_net_music_new2, R.id.music_tuijian2_iv2, new1.get(3), ids2);


                        break;


                    case 2:
                        myImgAdapter1.notifyDataSetChanged();;
                        myImgAdapter2.notifyDataSetChanged();;
                        myImgAdapter3.notifyDataSetChanged();;
                        break;

                }



                return false;
            }
        });


        return inflate;

    }


    public void showbangDan1(Map<String, Object> bangDan, Activity activity1, int ids[]  ) {


        //String title = (String) stringObjectMap.get("title");
        Bitmap pic = (Bitmap) bangDan.get("pic");

        List<Map<String, Object>> music = (List<Map<String, Object>>) bangDan.get("music");


        ImageView imageView = (ImageView) inflate.findViewById(ids[0]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ListView listView = (ListView) inflate.findViewById(ids[1]);
         myImgAdapter1 = new MyImgAdapter(activity1, music, quickcontrolsfragment1);
        listView.setAdapter(myImgAdapter1);

    }

    public void showbangDan2(Map<String, Object> bangDan, Activity activity1, int ids[] ) {


        //String title = (String) stringObjectMap.get("title");
        Bitmap pic = (Bitmap) bangDan.get("pic");

        List<Map<String, Object>> music = (List<Map<String, Object>>) bangDan.get("music");


        ImageView imageView = (ImageView) inflate.findViewById(ids[0]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ListView listView = (ListView) inflate.findViewById(ids[1]);
        myImgAdapter2 = new MyImgAdapter(activity1, music, quickcontrolsfragment1);
        listView.setAdapter(myImgAdapter2);

    }

    public void showbangDan3(Map<String, Object> bangDan, Activity activity1, int ids[] ) {


        //String title = (String) stringObjectMap.get("title");
        Bitmap pic = (Bitmap) bangDan.get("pic");

        List<Map<String, Object>> music = (List<Map<String, Object>>) bangDan.get("music");


        ImageView imageView = (ImageView) inflate.findViewById(ids[0]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ListView listView = (ListView) inflate.findViewById(ids[1]);
        myImgAdapter3 = new MyImgAdapter(activity1, music, quickcontrolsfragment1);
        listView.setAdapter(myImgAdapter3);

    }


    private void viewPagerInit(View inflate) {

        RollPagerView mViewPager = (RollPagerView) inflate.findViewById(R.id.view_pager);
        pagerAdapter = new NetMusicViewPagerAdapter(mViewPager, getActivity());
        mViewPager.setAdapter(pagerAdapter);
    }


    public void setHot(int itme_layout, int itme_layout2, Map<String, Object> hot, int ids[]) {
        LinearLayout layout1 = (LinearLayout) inflate.findViewById(itme_layout);
        RelativeLayout layout = (RelativeLayout) layout1.findViewById(itme_layout2);


        String nName = (String) hot.get("title");
        Bitmap nIcon = (Bitmap) hot.get("pic");

        String nNum = (String) hot.get("num");
        if (nNum == null) {
            nNum = (String) hot.get("author");
        }


        ImageView icon = (ImageView) layout.findViewById(ids[0]);
        TextView name = (TextView) layout.findViewById(ids[1]);
        TextView num = (TextView) layout.findViewById(ids[2]);


        if (nName.length()>8){
            nName=nName.substring(0,8);
        }
        if (nNum.length()>8){
            nNum=nNum.substring(0,8);
        }

        icon.setImageBitmap(nIcon);
        name.setText(nName);
        num.setText(nNum);


    }

    public void setHot1(int itme_layout, int itme_layout2, final Class activity) {
        LinearLayout layout1 = (LinearLayout) inflate.findViewById(itme_layout);
        RelativeLayout layout = (RelativeLayout) layout1.findViewById(itme_layout2);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), activity);
                startActivity(intent);
            }
        });

    }


    public void setItem(int itme_layout, int nIcon, String nName, final Class activity) {
        LinearLayout layout = (LinearLayout) inflate.findViewById(itme_layout);
        ImageView icon = (ImageView) layout.findViewById(R.id.fragment_net_music_item_icon);
        TextView name = (TextView) layout.findViewById(R.id.fragment_net_music_item_name);
        icon.setImageResource(nIcon);
        name.setText(nName);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), activity);
                startActivity(intent);
            }
        });

    }

    public  class MyImgAdapter extends MyMusicListAdapter {


        QuickControlsFragment quickcontrolsfragment1;
        public MyImgAdapter(Activity context, List<Map<String, Object>> musics,QuickControlsFragment quickcontrolsfragment1) {
            super(context, musics);
            this.quickcontrolsfragment1=quickcontrolsfragment1;

        }


        public Music getMusic(int id) {
            Map<String, Object> music = (Map<String, Object>) musics.get(id);
            String musicurl = (String) music.get("musicurl");
            Music music1 = new Music();
            String a = "http://music.163.com/song?id=";
            final String substring = musicurl.substring(a.length());

            String musicByKeyWord = WYYAPI.discover.getMusicByKeyWord(MyAES.getWhat.getMusicByid, substring);
            try {
                JSONObject jsonObject = new JSONObject(musicByKeyWord);
                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                JSONObject o = (JSONObject) jsonArray.get(0);
                String url = o.getString("url");

                music1.setUrl(url);
                music1.setTitle((String) music.get("musicname"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return music1;
        }


        List<Music> musicList;

        public void getAllNetMusic(final int index) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    musicList = new ArrayList<Music>();
                    for (int i = 0; i < musics.size(); i++) {
                        musicList.add(getMusic(i));
                    }

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Music.MusicUtil.setMusic(musicList, index, quickcontrolsfragment1);
                        }
                    });


                }
            }).start();
        }


        @Override
        public void setView(final int position, ViewHolder holder, View convertView) {
            Map<String, Object> music = (Map<String, Object>) musics.get(position);
            //holder.img.setImageBitmap(music.getBitmap());
            String musicname = (String) music.get("musicname");
            if (musicname.length() > 8) {
                musicname = musicname.substring(0, 8);
            }
            holder.title.setText(musicname);

            File musicnameF = new File(Data.APP_FILE_PATH + (String) music.get("musicname") + ".mp3");
            if (!musicnameF.exists()) {
                holder.download.setVisibility(View.VISIBLE);
            } else {
                holder.download.setVisibility(View.GONE);
            }

            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = getMusic(position).getUrl();
                            String title = getMusic(position).getTitle();


                            if (!haha(Data.APP_FILE_PATH+title)){
                                NetWork.downSong(context, url, title);
                            }
                        }
                    }).start();


                }
            });

            holder.info.setText("");
            holder.info.setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAllNetMusic(position);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = getMusic(position).getUrl();
                            String title = getMusic(position).getTitle();

                            if (!haha(Data.APP_FILE_PATH+title)){
                                NetWork.downSong(context, url, title);
                            }


                        }
                    }).start();

                }
            });

        }



    }

    public static boolean haha(String string){
        File file=new File(string);
        if (file.exists()){
            return true;
        }
        return false;
    }



}
