package com.skyun.music.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zk.activity.R;
import com.skyun.music.mode.Data;
import com.skyun.music.util.wyyapi.NetWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindMusicFour extends AppCompatActivity implements View.OnClickListener {

    private static String ganmao;
    private static String wendu;
    private static ArrayList<Map<String,String>> mapList;
    // 城市切换按钮
    private Button citySwitch;
    // 刷新数据按钮
    private Button weatherRefresh;
    // 城市名
    private TextView cityName;
    // 白天夜晚天气描叙
    private TextView DayNightWeather;
    // 温度
    private TextView temp;
    // 日出时间
    private TextView sunriseTime;
    // 日落时间
    private TextView sunsetTime;
    // 风力
    private TextView wind;
    // 降水概率
    private TextView pop;
    // 发布时间
    private TextView updateTime;
    // 今日天气预测列表
    private ListView listview;

    String Thecity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_music_four);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

         Thecity = getIntent().getStringExtra("city");


        init();
    }

    private void init() {
        citySwitch = (Button) findViewById(R.id.citySwitch);
        weatherRefresh = (Button) findViewById(R.id.weatherRefresh);
        citySwitch.setOnClickListener(this);
        weatherRefresh.setOnClickListener(this);
        cityName = (TextView) findViewById(R.id.cityName);
        DayNightWeather = (TextView) findViewById(R.id.DayNightWeather);
        temp = (TextView) findViewById(R.id.temp);
        sunriseTime = (TextView) findViewById(R.id.sunriseTime);
        sunsetTime = (TextView) findViewById(R.id.sunsetTime);
        wind = (TextView) findViewById(R.id.wind);
        pop = (TextView) findViewById(R.id.pop);
        updateTime = (TextView) findViewById(R.id.updateTime);
        listview = (ListView) findViewById(R.id.hourlyForecast);

        weatherRefresh.setText("同步中……");
        queryFromServer(Thecity);
    }

    private void queryFromServer(final String countyName) {


        new Thread(new Runnable() {
            @Override
            public void run() {//            http://wthrcdn.etouch.cn/weather_mini?city=北京
                String str = NetWork.getStr("http://wthrcdn.etouch.cn/weather_mini?city=" + countyName);


                readManifest(str);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                    }
                });

            }
        }).start();;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.citySwitch:
                break;
            case R.id.weatherRefresh:
                weatherRefresh.setText("同步中……");
                    queryFromServer(Thecity);
                break;
        }
    }

    private void showWeather() {
        Map<String, String> map = mapList.get(0);
        cityName.setText(Thecity);
        sunriseTime.setText("日出：" + "5:35");
        sunsetTime.setText("日落：" + "7:38");
        DayNightWeather.setText("日：" +map.get("high") + "\n夜：" + map.get("low") );
        wind.setText(map.get("type") );
        pop.setText("风向：" + map.get("fengxiang") );
        updateTime.setText("温馨提示:" + ganmao);



      /*  WeatherAdapter adapter = new WeatherAdapter(this, R.layout.hourly_weather, weatherList);
        listview.setAdapter(adapter);*/
        Toast.makeText(FindMusicFour.this, "已经是最新数据了", Toast.LENGTH_SHORT).show();
        weatherRefresh.setText("更新数据");
    }


        public  void readManifest(String stream) {
            try {
                Data.showMyLog(stream);
                JSONObject jsonObject=new JSONObject(stream);
                String status = jsonObject.getString("status");
                //
                Data.showMyLog("status"+status);
                JSONObject data= (JSONObject) jsonObject.get("data");

                mapList=new ArrayList<>();
               JSONArray forecast= (JSONArray) data.get("forecast");
                ganmao = data.getString("ganmao");
                for (int i = 0; i < forecast.length(); i++) {
                    Map<String,String>map=new HashMap<>();
                    JSONObject jsonObject1= (JSONObject) forecast.get(i);
                    String date = jsonObject1.getString("date");
                    String high = jsonObject1.getString("high");
                    String fengli = jsonObject1.getString("fengli");
                    String low = jsonObject1.getString("low");
                    String fengxiang = jsonObject1.getString("fengxiang");
                    String type = jsonObject1.getString("type");
                    map.put("date",date);
                    map.put("high",high);
                    map.put("fengli",fengli);
                    map.put("low",low);
                    map.put("fengxiang",fengxiang);
                    map.put("type",type);
                    mapList.add(map);
                }


                Data.showMyLog("gaomao------"+ganmao);


                 wendu = jsonObject.getString("wendu");



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

}

