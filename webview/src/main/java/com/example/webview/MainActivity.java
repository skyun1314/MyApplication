package com.example.webview;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    public void init() {
          webView = new WebView(this);

        webView.setVerticalScrollbarOverlay(true);
        //设置WebView支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/webview.html");
       /* //在js中调用本地java方法
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
*/
//        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
    }


    //在java中调用js代码
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void sendInfoToJs1111(String musicid) {

        String d = "{\"ids\":\"[" + musicid + "]\",\"br\":128000,\"csrf_token\":\"\"}";
        d="{\"hlpretag\":\"<span class=\\\\\"s-fc7\\\\\">\",\"hlposttag\":\"</span>\",\"s\":\"" + musicid+
                "\",\"type\":\"1\",\"offset\":\"0\",\"total\":\"true\",\"limit\":\"30\",\"csrf_token\":\"\"}";
        String e = "010001";
        String f = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
        String g = "0CoJUm6Qyw8W8jud";

        String script = "javascript:window.asrsea('" + d + "','" + e + "','" + f + "','" + g + "')";
        webView.evaluateJavascript(script, new ValueCallback<String>() {

            @Override
            public void onReceiveValue(String responseJson) {
                try {
                    JSONObject jsonObject = new JSONObject(responseJson);
                    String encSecKey = jsonObject.getString("encSecKey");
                    String encText = jsonObject.getString("encText");
                     final String key = "params=" + encText + "&encSecKey=" + encSecKey;
                    //final String url1 = "http://music.163.com/weapi/song/enhance/player/url?csrf_token=";
                    final String    url1 ="http://music.163.com/weapi/cloudsearch/get/web?csrf_token=";
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendPost(url1, key);
                        }
                    }).start();

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });


    }


    public static String sendPost(String strUrl, String key) {
        try {
            // 1. 创建URL对象
            URL url = new URL(strUrl);
            // 2. 创建URL连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 3. 设置请求信息
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setRequestProperty("Connection", "keep-alive");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            // 4. 设置输入输出可操作
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            // 5. 构造提交参数(输出流)并提交

            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());


            out.print(key);//479979010
            // 提交
            out.flush();
            // 6. 获取返回信息(输入流)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }

            Log.e(TAG, "stringBuffer: " + stringBuffer);


           /* JSONObject jsonObject = new JSONObject(stringBuffer.toString());

            JSONArray data = (JSONArray) jsonObject.get("data");

            JSONObject o = (JSONObject) data.get(0);

            String string = o.getString("url");
            Log.e(TAG, "sendPost: " + string);*/
            return new String(stringBuffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 封装一个多参数的输出函数
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    public static final String TAG = "wodelog";

    public void haha(View view) {
        sendInfoToJs1111("304957");


    }
}
