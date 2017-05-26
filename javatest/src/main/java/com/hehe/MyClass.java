package com.hehe;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClass {

    public static void main(String[] args) {
       // discover.getbanner();
        discover.getMusicById("479979010");
    }


    // 封装一个多参数的输出函数
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    /*
      这里打印出来的是
      ----  推荐  ----http://music.163.com/discover
       ----排行榜 ----http://music.163.com/discover/toplist
       ----歌单 ----http://music.163.com/discover/playlist
       ----主播电台  ----http://music.163.com/discover/djradio
       ----歌手  ----http://music.163.com/discover/artist
       ----新碟上架  ----http://music.163.com/discover/album*/
    public static List<Map<String, String>> getAllType() {
        String url = "http://music.163.com/";
        print("抓取中 %s...", url);
        try {
            Document doc = Jsoup.connect(url).get();
            Element logo = doc.select("ul.nav").first();
            Elements select = logo.select("a");
            List<Map<String, String>> mapList = new ArrayList<>();
            for (int i = 0; i < select.size(); i++) {
                String href = select.get(i).absUrl("href");
                String text = select.get(i).text();
                print("----%s", text);
                print("----%s", href);
                Map<String, String> map = new HashMap<>();
                map.put("text", text);
                map.put("url", href);
                mapList.add(map);
            }
            return mapList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static class discover {
        public static List<Map<String, String>> instans() {

            try {

                Document doc = Jsoup.connect("http://music.163.com/discover").get();
                Element elementsByClass = doc.getElementsByClass("g-wrap3").first();
                Elements childrens = elementsByClass.children();
                List<Elements> elementList = new ArrayList<>();

                for (int i = 0; i < childrens.size(); i++) {
                    Element children = childrens.get(i);
                    if (children.id() == "") {//获取推荐里面的三个列表（热门推荐，新碟上架，榜单）
                        Elements children1 = children.children();
                        elementList.add(children1);
                    }
                }

                List<Map<String, String>> hotTuiJian = getHotTuiJian(elementList.get(0));
                print("--------------------------------------------------------------------------------");
                List<Map<String, String>> newDieSHangJia = getNewDieSHangJia(elementList.get(1));
                print("--------------------------------------------------------------------------------");
                List<Map<String, String>> bangDan = getBangDan(elementList.get(2));

                return hotTuiJian;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /*
        * Elements  children1 热门推荐
        * */
        public static List<Map<String, String>> getHotTuiJian(Elements children1) {

            // print(children1.get(0).text());//获取热门推荐 的标题，tab选项

            Element children = children1.parents().first();
            Elements lis = children1.get(1).select("li");//获取热门推荐里面的详细列表li
            // print("lis:"+lis);

            List<Map<String, String>> mapList = new ArrayList<>();

            for (int j = 0; j < lis.size(); j++) {//获取热门推荐里面的详细列表li

                Map<String, String> map = new HashMap<>();

                print("-------------------------获取%s里面的详细列表" + j, children.className());
                String src = lis.get(j).select("img").attr("src");//根据每一个li获取图片链接
                print("图片:" + src);
                map.put("pic", src);
                Element a = lis.get(j).select("a").first();

                print("标题：" + a.attr("title"));//根据每一个li获取标题
                map.put("title", a.attr("title"));
                print("跳转链接：" + a.attr("abs:href"));////根据每一个li获取跳转链接
                map.put("url", a.attr("abs:href"));
                print("播放次数：" + lis.get(j).getElementsByClass("nb").text());//根据每一个li获取播放次数
                map.put("num", lis.get(j).getElementsByClass("nb").text());
                mapList.add(map);
            }
            return mapList;

        }

        /*
       * Elements  children1 新碟上架
       * */
        public static List<Map<String, String>> getNewDieSHangJia(Elements children1) {

            // print(children1.get(0).text());//获取热门推荐 的标题，tab选项

            Element children = children1.parents().first();
            Elements lis = children1.get(1).select("li");//获取热门推荐里面的详细列表li
            List<Map<String, String>> mapList = new ArrayList<>();
            for (int j = 0; j < lis.size(); j++) {//获取热门推荐里面的详细列表li
                Map<String, String> map = new HashMap<>();
                print("-------------------------获取%s里面的详细列表" + j, children.className());
                String src = lis.get(j).select("img").attr("data-src");//根据每一个li获取图片链接
                print("图片:" + src);
                map.put("pic", src);


                Element a = lis.get(j).select("a").first();
                print("标题：" + a.attr("title"));//根据每一个li获取标题
                print("跳转链接：" + a.attr("abs:href"));////根据每一个li获取跳转链接
                map.put("title", a.attr("title"));
                map.put("url", a.attr("abs:href"));

                Element auther = lis.get(j).getElementsByClass("s-fc3").first();
                print("歌手链接：" + auther.attr("abs:href"));//根据每一个li获取播放次数
                print("歌手名字：" + auther.text());//根据每一个li获取播放次数
                map.put("author_url", auther.attr("abs:href"));
                map.put("author", auther.text());
            }
            return mapList;
        }

        /*
        *排行榜
         */
        public static List<Map<String, String>> getBangDan(Elements children1) {

            // print(children1.get(0).text());//获取热门推荐 的标题，tab选项

            Element children = children1.parents().first();
            print("-------------------------获取%s里面的详细列表", children.className());
            List<Map<String, String>> mapList = new ArrayList<>();
            Element lis = children1.get(1).getElementsByClass("n-bilst").first();
            Elements children2 = lis.children();
            for (int j = 0; j < children2.size(); j++) {//获取榜单里面的分类
                print("\n");
                Map<String, String> map = new HashMap<>();
                Element child1 = children2.get(j).child(0);//名称图片
                Element child2 = children2.get(j).child(1);//具体歌曲

                Elements a = child1.select("a");

                print("title:" + a.attr("title"));
                print("更多链接:" + a.attr("abs:href"));
                map.put("title", a.attr("title"));
                map.put("url", a.attr("abs:href"));

                Elements picAndName = child1.getElementsByClass("j-img");
                print("图片:" + picAndName.attr("data-src"));
                map.put("pic", picAndName.attr("data-src"));
                print("--------------%s列表：\n", a.attr("title"));
                Elements li = child2.select("li");
                for (int i = 0; i < li.size(); i++) {
                    Element a1 = li.get(i).select("a").first();
                    print("歌曲名称：" + a1.attr("title"));
                    print("歌曲链接：" + a1.attr("abs:href"));

                }

            }
            return mapList;
        }

        /*
        *根据id获取音乐
         */
        public static String getMusicById(String id) {//id ="479979010"
            try {
                // 1. 创建URL对象
                URL url = new URL("http://music.163.com/weapi/song/enhance/player/url?csrf_token=");
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
                String param = MyAES.get_param(id);
                print("-----%s",param);
                out.print(param);
                // 提交
                out.flush();
                // 6. 获取返回信息(输入流)
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                print("getMusicById: " + stringBuffer.toString());


                JSONObject jsonObject = new JSONObject(stringBuffer.toString());

                JSONArray data = (JSONArray) jsonObject.get("data");

                JSONObject o = (JSONObject) data.get(0);

                String string = o.getString("url");
                print("----------" + string);

                return new String(stringBuffer);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /*
                *获取轮播图
                 */
        public static void getbanner() {
            try {

                Document doc = Jsoup.connect("http://music.163.com/discover").get();

                Elements script = doc.select("script");

                int lenth = "window.Gbanners =".length();
                String substring = script.get(2).html().substring(lenth);

                JSONArray jsonArra = new JSONArray(substring);

                for (int i = 0; i < jsonArra.length(); i++) {
                    JSONObject o = (JSONObject) jsonArra.get(i);
                    String picUrl = o.getString("picUrl");
                    String url = o.getString("url");

                    String targetId = o.getString("targetId");
                    if (!targetId.equals("0")) {
                        url = "http://music.163.com" + url;
                    }
                    print("picurl:%s", picUrl);
                    print("url:%s", url);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
