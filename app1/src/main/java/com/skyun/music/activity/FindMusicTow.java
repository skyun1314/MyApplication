package com.skyun.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zk.activity.R;
import com.skyun.music.mode.besjon.City;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FindMusicTow extends AppCompatActivity {
    List<City> city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_music);

       city  = (List<City>) getIntent().getSerializableExtra(FindMusicTow.class.getName());

        List<String>strings=new ArrayList<>();

        for (int i = 0; i < city.size(); i++) {
            strings.add(city.get(i).getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strings);
        ListView listView = (ListView) findViewById(R.id.fragment_find_music_tianqi_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> area = city.get(position).getArea();

                Intent intent = new Intent(FindMusicTow.this, FindMusicTree.class);
                intent.putExtra(FindMusicTow.class.getName(), (Serializable) area);
                startActivity(intent);
            }
        });


    }
}
