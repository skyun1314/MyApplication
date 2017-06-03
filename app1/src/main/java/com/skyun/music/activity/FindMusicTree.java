package com.skyun.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zk.activity.R;

import java.util.List;

public class FindMusicTree extends AppCompatActivity {
    List<String> serializableExtra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_music);

      serializableExtra = (List<String>) getIntent().getSerializableExtra(FindMusicTow.class.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, serializableExtra);
        ListView listView = (ListView) findViewById(R.id.fragment_find_music_tianqi_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(FindMusicTree.this, FindMusicFour.class);
                intent.putExtra("city",serializableExtra.get(position));
                startActivity(intent);
            }
        });

    }
}
