package com.example.zk.activity.util;

import android.os.Message;

import com.example.zk.activity.fragment.NetMusic;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClass {

    public static void main(String[] args) {

        //String url="http://127.0.0.1:9000/signup";
       // String url="http://127.0.0.1:9000/signin";
        //zhuCe(url,"1111","1111");
        //wangyiyun();

        String url="http://music.163.com/weapi/song/enhance/player/url?csrf_token=";
       // sendPost(url);
    }


    public static String login(){
        try {
            URL url=new URL("http://127.0.0.1:9000/signup");
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            int code=connection.getResponseCode();
            if (code==200){
                Map<String, List<String>> headerFields = connection.getHeaderFields();

                String s = headerFields.get("Set-Cookie").get(1);
                s= s.substring(0,s.indexOf(";"));
                print(s);
                return s;

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void zhuCe(String url,String name,String password){

        String cookie = login();
        String pamr=cookie.replace("|","%7c")+"&username="+name+"&password="+password;

        try {
            URL url1=new URL(url);
            try {
                HttpURLConnection connection= (HttpURLConnection) url1.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(5000);
                connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                connection.setRequestProperty("Connection","keep-alive");
                connection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
                connection.setRequestProperty("Cookie", cookie);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                PrintWriter printWriter=new PrintWriter(connection.getOutputStream());
                printWriter.print(pamr);
                printWriter.flush();;
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str1="";
                while ((str1=bufferedReader.readLine())!=null){
                    stringBuffer.append(str1);
                }

                str1=stringBuffer.toString();

                if(str1 != null){
                    if (str1.contains("用户已存在")){
                        System.out.println("用户已存在");
                    }else if(str1.contains("账户创建成功")){
                        print("账户创建成功");
                    }else if(str1.contains("今日开幕")){
                        print("今日开幕");
                    }else{
                        System.out.println(str1);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private static void jsoupDemo4() {

        String url = "http://jandan.net/ooxx";
        print("抓取中 %s...", url);
        try {
            Document doc = Jsoup.connect(url).get();

            Element logo = doc.select("div.nav").first();

            print("div.nav:---%s\n",logo.html());




            // 选择 链接
            Elements links = doc.select("a[href]");
            // 选择 图片
            Elements media = doc.select("img[src]");
            // 选择 导入链接
            Elements imports = doc.select("link[href]");

            print("\nMedia: (%d)", media.size());
            for (Element src : media) {
                if (src.tagName().equals("img"))
                    // 输出标签名，属性
                    print(" * %s: <%s> ",  src.tagName(), src.attr("abs:src"));
                else
                    print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
            }

            print("\nImports: (%d)", imports.size());
            for (Element link : imports) {
                print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
            }

            print("\nLinks: (%d)", links.size());
            for (Element link : links) {
                print(" * a: <%s>  (%s)", link.attr("abs:href"),link.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 封装一个多参数的输出函数
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    public static List<Map<String, Object>> wangyiyun() {

        String url = "http://music.163.com/";
        print("抓取中 %s...", url);
        try {
           /* Document doc = Jsoup.connect(url).get();

            Element logo = doc.select("ul.nav").first();


            //print( logo.html());

            Elements select = logo.select("a");


            for (int i = 0; i < select.size(); i++) {
                String href = select.get(i).absUrl("href");
                String text = select.get(i).text();
               // print("----%s",text);
                //print("----%s",href);

            }*/

            Document  doc = Jsoup.connect("http://music.163.com/discover").get();
            Element elementsByClass = doc.getElementsByClass("g-wrap3").first();


            //print("----%s", elementsByClass.html());

            Elements childrens = elementsByClass.children();

            List<Elements>elementList=new ArrayList<>();

            for (int i=0;i<childrens.size();i++){
                Element children =childrens.get(i);
                if (children.id()==""){//获取推荐里面的三个列表（热门推荐，新碟上架，榜单）
                    Elements children1 = children.children();

                    elementList.add(children1);


                }

            }

            List<Map<String, Object>> hotTuiJian = getHotTuiJian(elementList.get(0));
            print("--------------------------------------------------------------------------------");
            List<Map<String, Object>> newDieSHangJia = getNewDieSHangJia(elementList.get(1));
            print("--------------------------------------------------------------------------------");
            List<Map<String, String>> bangDan = getBangDan(elementList.get(2));


            Map<String,Object> mymap=new HashMap<>();
            mymap.put("hot",hotTuiJian);
            mymap.put("new",newDieSHangJia);


            Message message = NetMusic.handler.obtainMessage();
            message.obj= mymap;
            message.sendToTarget();



            return hotTuiJian;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * Elements  children1 热门推荐
    *
    * */
    public static List<Map<String, Object>> getHotTuiJian(Elements  children1){

        // print(children1.get(0).text());//获取热门推荐 的标题，tab选项

        Element children = children1.parents().first();
        Elements lis = children1.get(1).select("li");//获取热门推荐里面的详细列表li
        // print("lis:"+lis);

        List<Map<String,Object>>mapList=new ArrayList<>();

        for (int j = 0; j < lis.size(); j++) {//获取热门推荐里面的详细列表li

            Map<String,Object> map=new HashMap<>();

            print("-------------------------获取%s里面的详细列表"+j,children.className());
            String src = lis.get(j).select("img").attr("src");//根据每一个li获取图片链接
            print("图片:"+src);




            map.put("pic",NetWork.getPicByGet(src));
            Element a = lis.get(j).select("a").first();

            print("标题："+a.attr("title"));//根据每一个li获取标题
            map.put("title",a.attr("title"));
            print("跳转链接："+a.attr("abs:href"));////根据每一个li获取跳转链接
            map.put("url",a.attr("abs:href"));
            print("播放次数："+lis.get(j).getElementsByClass("nb").text());//根据每一个li获取播放次数
            map.put("num",lis.get(j).getElementsByClass("nb").text());
            mapList.add(map);
        }
        return mapList;

    }

    /*
   * Elements  children1 新碟上架
   *
   * */
    public static List<Map<String, Object>> getNewDieSHangJia(Elements  children1){

        // print(children1.get(0).text());//获取热门推荐 的标题，tab选项

        Element children = children1.parents().first();
        Elements lis = children1.get(1).select("li");//获取热门推荐里面的详细列表li
        List<Map<String,Object>>mapList=new ArrayList<>();
        for (int j = 0; j < lis.size(); j++) {//获取热门推荐里面的详细列表li
            Map<String,Object> map=new HashMap<>();
            print("-------------------------获取%s里面的详细列表"+j,children.className());
            String src = lis.get(j).select("img").attr("data-src");//根据每一个li获取图片链接
            print("图片:"+src);
            map.put("pic",NetWork.getPicByGet(src));

            Element a = lis.get(j).select("a").first();
            print("标题："+a.attr("title"));//根据每一个li获取标题
            print("跳转链接："+a.attr("abs:href"));////根据每一个li获取跳转链接
            map.put("title",a.attr("title"));
            map.put("url",a.attr("abs:href"));

            Element auther = lis.get(j).getElementsByClass("s-fc3").first();
            print("歌手链接："+auther.attr("abs:href"));//根据每一个li获取播放次数
            print("歌手名字："+auther.text());//根据每一个li获取播放次数
            map.put("author_url",auther.attr("abs:href"));
            map.put("author",auther.text());
            mapList.add(map);
        }
        return mapList;
    }



    public static List<Map<String, String>> getBangDan(Elements  children1){

        // print(children1.get(0).text());//获取热门推荐 的标题，tab选项

        Element children = children1.parents().first();
        print("-------------------------获取%s里面的详细列表",children.className());
        List<Map<String,String>>mapList=new ArrayList<>();
        Element lis = children1.get(1).getElementsByClass("n-bilst").first();
        Elements children2 = lis.children();
        for (int j = 0; j < children2.size(); j++) {//获取榜单里面的分类
            print("\n");
            Map<String,String> map=new HashMap<>();
            Element child1 = children2.get(j).child(0);//名称图片
            Element child2 = children2.get(j).child(1);//具体歌曲

            Elements a = child1.select("a");

            print("title:"+a.attr("title"));
            print("更多链接:"+a.attr("abs:href"));
            map.put("title",a.attr("title"));
            map.put("url",a.attr("abs:href"));

            Elements picAndName = child1.getElementsByClass("j-img");
            print("图片:"+ picAndName.attr("data-src"));
            map.put("pic",picAndName.attr("data-src"));
            print("--------------%s列表：\n",a.attr("title"));
            Elements li = child2.select("li");
            for (int i = 0; i < li.size(); i++) {
                Element a1 = li.get(i).select("a").first();
                print("歌曲名称："+a1.attr("title"));
                print("歌曲链接："+a1.attr("abs:href"));

            }

        }
        return mapList;
    }


    public static String sendPost(String strUrl){
        try {
            // 1. 创建URL对象
            URL url = new URL(strUrl);
            // 2. 创建URL连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 3. 设置请求信息
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept","*/*");
            urlConnection.setRequestProperty("Connection","keep-alive");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");


           // String params="params=YtNE7gRZWD%2FWd1esZvP%2BLlkg93bxhZataBOQEDDCl4X31TJ6Gomn17Dsy8tKmgO5HPvXciH5EcvSILSoH6l0HNSlEXI759ptYshgobqGEcH7FS%2FtmN9wzWlZWjJMUKOM&encSecKey=4e0c0fbb3082b41d5ecede1374f4b4f586e1311fd65563cb75376117ba95ef9e2a3c7843c4411e747b8a77ea74ac247975f099d3f1d5c4fd7cb17ca257c9c78923c34d1e64ed6227e44c7fa4c72cbc84aec83d0d328193ce39c34137e9a701b9035cf5a21d6f2739f8a4ff80bf00f64b317890806ce8fc2c22197f049a1d2dc1";


            // 4. 设置输入输出可操作
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            // 5. 构造提交参数(输出流)并提交
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(MyAES.get_param("479979010"));
            // 提交
            out.flush();
            // 6. 获取返回信息(输入流)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) !=null){
                stringBuffer.append(str);
            }
            print("sendPost: "+stringBuffer.toString());


            JSONObject jsonObject=new JSONObject(stringBuffer.toString());

            JSONArray data = (JSONArray) jsonObject.get("data");

            JSONObject o = (JSONObject) data.get(0);

            String string = o.getString("url");
            print("----------"+string);

            return new String(stringBuffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
