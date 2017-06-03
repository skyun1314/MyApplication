package com.skyun.music.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zk.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skyun.music.mode.besjon.City;
import com.skyun.music.mode.besjon.JsonRootBean;
import com.skyun.music.util.wyyapi.FileUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindMusic extends Fragment {
    List<JsonRootBean> retList;
    View inflater1;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_find_music, container, false);
        this.inflater1 = inflate;

        listView = (ListView) inflate.findViewById(R.id.fragment_find_music_tianqi_list);


        String json = FileUtil.getJson(getContext(), "city.json");

        Gson gson = new Gson();


        retList = gson.fromJson(json, new TypeToken<List<JsonRootBean>>() {
        }.getType());


        List list = new ArrayList();
        for (int i = 0; i < retList.size(); i++) {
            list.add(retList.get(i).getName());
        }


        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                List<City> city = retList.get(position).getCity();

                Intent intent = new Intent(FindMusic.this.getActivity(), FindMusicTow.class);
                intent.putExtra(FindMusicTow.class.getName(), (Serializable) city);
                startActivity(intent);


            }
        });

        return inflater1;

    }
}
