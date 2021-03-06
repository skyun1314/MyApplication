package com.skyun.music.mode;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.example.zk.activity.R;
import com.skyun.music.activity.Main;
import com.skyun.music.activity.MyMusic;
import com.skyun.music.activity.QuickControlsFragment;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.skyun.music.activity.Main.mContext;
import static com.skyun.music.mode.Music.MusicUtil.theWholeMusic;

/**
 * Created by zk on 2017/5/21.
 */

public class Music implements Serializable {

    private String _display_name;
    private String mime_type;
    private Bitmap bitmap;
    private long songid;
    private long albumid;
    private String title;
    private String singer;
    private String album;//歌曲专辑
    private long size;
    private long duration;
    private String url;

    public long getSongid() {
        return songid;
    }

    public void setSongid(long songid) {
        this.songid = songid;
    }

    public long getAlbumid() {
        return albumid;
    }

    public void setAlbumid(long albumid) {
        this.albumid = albumid;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public String get_display_name() {
        return _display_name;
    }

    public void set_display_name(String _display_name) {
        this._display_name = _display_name;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", url='" + url + '\'' +
                ", _display_name='" + _display_name + '\'' +
                ", mime_type='" + mime_type + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }


    public static class MusicUtil  {


        //获取专辑封面的Uri
        private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");


        public static List<Music> getMuaicByRequir(Cursor cursor, String... filepath) {
            if (null == cursor) {
                return null;
            }
            Music music;
            List<Music> list = new ArrayList<Music>();
            while (cursor.moveToNext()) {
                //歌曲名id
                long id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

                long album_id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                //歌曲名
                String title = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.TITLE));

                //歌手
                String singer = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.ARTIST));

                //专辑
                String album = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.ALBUM));

                //长度
                long size = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Audio.Media.SIZE));

                //时长
                long duration = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DURATION));

                //路径
                String url = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DATA));

                if ((filepath != null) && (filepath.length == 1) && (!url.contains(filepath[0]))) {
                    continue;
                }


                //显示的文件名
                String _display_name = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

                //类型
                String mime_type = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                music = new Music();
                music.setAlbum(album);
                music.setDuration(duration);
                music.setSinger(singer);
                music.setSize(size);
                music.setTitle(title);
                music.setUrl(url);
                music.set_display_name(_display_name);
                music.setMime_type(mime_type);

                long songid = cursor.getLong(3);
                long albumid = cursor.getLong(7);
                music.setAlbumid(albumid);
                music.setSongid(songid);
                // music.setBitmap(getArtwork(context, songid, albumid, false));
                list.add(music);
            }
            return list;
        }


        public static List<Music> getAllMusic(Context context) {
            ContentResolver cr = context.getContentResolver();
            if (cr == null) {
                return null;
            }
            // 获取所有歌曲
            Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);


            return getMuaicByRequir(cursor);
        }


        public static List<Map<String, Object>> getAllArtists(Context context) {
            ContentResolver cr = context.getContentResolver();
            if (cr == null) {
                return null;
            }
            // 获取所有歌手
            Cursor cursor = cr.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Artists.ARTIST_KEY);
            if (null == cursor) {
                return null;
            }
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));

                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
//共有多少该歌手的专辑
                int numOfAlbum = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
//共有多少该歌手的歌曲
                int numOfSong = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", name);
                map.put("numOfSong", numOfSong);
                map.put("id", id);
                list.add(map);
            }
            return list;
        }


        public static List<Map<String, Object>> getAllAlbums(Context context) {
            ContentResolver cr = context.getContentResolver();
            if (cr == null) {
                return null;
            }
            // 获取所有专辑
            Cursor cursor = cr.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Albums.ALBUM_KEY);
            if (null == cursor) {
                return null;
            }
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            while (cursor.moveToNext()) {

                //MediaStore.Audio.Albums._ID ：专辑id
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID));

                //MediaStore.Audio.Albums.ALBUM：专辑名称
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));

                //MediaStore.Audio.Albums.NUMBER_OF_SONGS：共用多少歌曲属于该专辑
                int numOfSong = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", name);
                map.put("numOfSong", numOfSong);
                map.put("id", id);
                list.add(map);
            }
            return list;
        }


        public static List<Map<String, Object>> getMusicDirs(Context context) {


            String num_of_videos = "num_of_videos";

            /** 要从MediaStore检索的列 */
            final String[] mProjection = new String[]{
                    MediaStore.Files.FileColumns.DATA,
                    "count(" + MediaStore.Files.FileColumns.PARENT + ") as "
                            + num_of_videos};
            /** where子句 */
            String mSelection = MediaStore.Files.FileColumns.MEDIA_TYPE + " = "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO + " ) "
                    + " group by ( " + MediaStore.Files.FileColumns.PARENT;


            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Files.getContentUri("external"), mProjection,
                    mSelection, null, null);


            int index_data = cursor
                    .getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int index_num_of_videos = cursor.getColumnIndex(num_of_videos);


            List<Map<String, Object>> list = new ArrayList<>();

            // 将数据库查询结果保存到一个List集合中(存放在RAM)
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    // 获取每个目录下的视频数量
                    int nums = cursor.getInt(index_num_of_videos);

                    // 获取文件的路径，如/storage/sdcard0/MIUI/music/Baby.mp3
                    String filepath = cursor.getString(index_data);
                    String folderpath = filepath.substring(0, filepath.lastIndexOf(File.separator));


                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", folderpath);
                    map.put("numOfSong", nums);
                    list.add(map);
                }
            }
            // 如果没有扫描到媒体文件，itemsList的size为0，因为上面new过了
            return list;
        }


        public static List<Music> getMusicInfo(Context context, String id, String aid) {
            ContentResolver cr = context.getContentResolver();
            if (cr == null) {
                return null;
            }


            //查询属于指定歌手（歌手id 为 aid）的歌曲：
            Cursor query = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                    id + "=" + aid, null,// MediaStore.Audio.Media.ARTIST_ID
                    MediaStore.Audio.Media.TITLE);

        /*//查询属于指定专辑（专辑id 为 aid）的歌曲：
          cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,  null ,
                MediaStore.Audio.Media.ALBUM_ID + "="  + aid,  null ,//MediaStore.Audio.Media.ALBUM_ID
                MediaStore.Audio.Media.TITLE);*/

            return getMuaicByRequir(query);
        }


        public static List<Music> getMusicInfo(String path, Context context) {
 /*
            File file = new File(path);
          final String where = MediaStore.Audio.Media.DATA + "IN'" + file.getAbsolutePath() + "'";
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, where, null, null);*/

            ContentResolver cr = context.getContentResolver();
            if (cr == null) {
                return null;
            }
            // 获取所有歌曲
            Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null);

            return getMuaicByRequir(cursor, path);

        }


    public static Music getMusicInfoByFile(String fileName){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        Music music=new Music();

        try {
            mmr.setDataSource(fileName);
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
            byte[] pic = mmr.getEmbeddedPicture();  // 图片，可以通过BitmapFactory.decodeByteArray转换为bitmap图片
            //Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);

           // long size = mmr.extractMetadata(MediaMetadataRetriever.);
            //路径
            music.setTitle(title);
            music.setAlbum(album);
            music.setUrl(fileName);
            music.setSinger(artist);
           // music.setSize(size);
            music.setDuration(Integer.parseInt(duration));
            return music;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Music> getAllMusicByFile(List<String>strings){
        List<Music>musicList=new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            Music musicInfoByFile = getMusicInfoByFile(strings.get(i));
            musicList.add(musicInfoByFile);
        }
        return musicList;
    }


        /**
         * 格式化时间，将毫秒转换为分:秒格式
         *
         * @param time
         * @return
         */
        public static String formatTime(long time) {
            String min = time / (1000 * 60) + "";
            String sec = time % (1000 * 60) + "";
            if (min.length() < 2) {
                min = "0" + time / (1000 * 60) + "";
            } else {
                min = time / (1000 * 60) + "";
            }
            if (sec.length() == 4) {
                sec = "0" + (time % (1000 * 60)) + "";
            } else if (sec.length() == 3) {
                sec = "00" + (time % (1000 * 60)) + "";
            } else if (sec.length() == 2) {
                sec = "000" + (time % (1000 * 60)) + "";
            } else if (sec.length() == 1) {
                sec = "0000" + (time % (1000 * 60)) + "";
            }
            return min + ":" + sec.trim().substring(0, 2);
        }


        private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

        public static Bitmap getArtwork(Context context, long song_id, long album_id,
                                        boolean allowdefault) {
            if (album_id < 0) {
                if (song_id >= 0) {
                    Bitmap bm = getArtworkFromFile(context, song_id, -1);
                    if (bm != null) {
                        return bm;
                    }
                }
                if (allowdefault) {
                    return getDefaultArtwork(context);
                }
                return null;
            }
            ContentResolver res = context.getContentResolver();
            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
            if (uri != null) {
                InputStream in = null;
                try {
                    in = res.openInputStream(uri);
                    Bitmap bmp = BitmapFactory.decodeStream(in, null, sBitmapOptions);
                    if (bmp == null) {
                        bmp = getDefaultArtwork(context);
                    }
                    return bmp;
                } catch (FileNotFoundException ex) {
                    Bitmap bm = getArtworkFromFile(context, song_id, album_id);
                    if (bm != null) {
                        if (bm.getConfig() == null) {
                            bm = bm.copy(Bitmap.Config.RGB_565, false);
                            if (bm == null && allowdefault) {
                                return getDefaultArtwork(context);
                            }
                        }
                    } else if (allowdefault) {
                        bm = getDefaultArtwork(context);
                    }
                    return bm;
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        private static Bitmap getArtworkFromFile(Context context, long songid, long albumid) {
            Bitmap bm = null;
            if (albumid < 0 && songid < 0) {
                throw new IllegalArgumentException("Must specify an album or a song id");
            }
            try {
                if (albumid < 0) {
                    Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
                    ParcelFileDescriptor pfd = context.getContentResolver()
                            .openFileDescriptor(uri, "r");
                    if (pfd != null) {
                        FileDescriptor fd = pfd.getFileDescriptor();
                        bm = BitmapFactory.decodeFileDescriptor(fd);
                    }
                } else {
                    Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
                    ParcelFileDescriptor pfd = context.getContentResolver()
                            .openFileDescriptor(uri, "r");
                    if (pfd != null) {
                        FileDescriptor fd = pfd.getFileDescriptor();
                        bm = BitmapFactory.decodeFileDescriptor(fd);
                    }
                }
            } catch (FileNotFoundException ex) {

            }
            return bm;
        }

        private static Bitmap getDefaultArtwork(Context context) {

            Resources res = context.getResources();

            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.ic_menu_send);

            return bmp;
        }


        public static MediaPlayer mPlayer;
        //  public static Music theWholeMusic;
        public static List<Music> theWholeMusic;
        public static int theWholeMusicIndex;
        static QuickControlsFragment quickcontrolsfragment;


        public static void playMusic() {
            try {
                theWholeMusicIndex = 0;
                mPlayer.reset();
                mPlayer.setDataSource(theWholeMusic.get(0).getUrl());
                mPlayer.prepare();
                mPlayer.start();
                quickcontrolsfragment.update();
                notifications();
                Main.helper.insert(theWholeMusic.get(theWholeMusicIndex).getUrl());
                MyMusic.handler.sendEmptyMessage(3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public static void setMusic(List<Music> music, int index, QuickControlsFragment quickcontrolsfragment1) {

            quickcontrolsfragment = quickcontrolsfragment1;
            theWholeMusic = getNewList(music, index);
            playMusic();
        }

        static {
            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    try {
                        int index;
                        if (theWholeMusicIndex + 1 >= theWholeMusic.size()) {
                            index = 0;
                        } else {
                            index = theWholeMusicIndex + 1;
                        }

                        theWholeMusic = getNewList(theWholeMusic, index);
                        playMusic();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public static void PlayByNum(int num) {

            try {
                theWholeMusicIndex = num;
                mPlayer.reset();
                mPlayer.setDataSource(theWholeMusic.get(theWholeMusicIndex).getUrl());
                mPlayer.prepare();
                mPlayer.start();
                notifications();
                MyMusic.handler.sendEmptyMessage(3);
                Main.helper.insert(theWholeMusic.get(theWholeMusicIndex).getUrl());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public static List getNewList(List list, int index) {
            List nNewList = new ArrayList<>();

            int xx;
            for (int i = 0; i < list.size(); i++) {

                if (index + i < list.size()) {
                    xx = index + i;
                } else {
                    xx = index + i - list.size();
                }

                nNewList.add(list.get(xx));
            }
            return nNewList;
        }


        public static void playOrPaus() {
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                } else {
                    mPlayer.start();
                }
            }
        }

        public static int getMusicDuration() {
            return mPlayer.getDuration();
        }

        public static void setMusicDuration(int duiation) {
            mPlayer.seekTo(duiation);
        }


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public  static void notifications(){
        //3.定义一个PendingIntent，点击Notification后启动一个Activity
        PendingIntent pi = PendingIntent.getActivity(
                mContext,
                100,
                new Intent(mContext, Main.class),
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        //2.通过Notification.Builder来创建通知
        Notification.Builder myBuilder = new Notification.Builder(mContext);
        myBuilder.setContentTitle(theWholeMusic.get(0).getTitle())
                .setContentText(theWholeMusic.get(0).getSinger())
               // .setSubText("这是补充小行内容")
               // .setTicker("您收到新的消息")
                //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示
                .setSmallIcon(R.drawable.hot1)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.hotmusic))
                //.setLargeIcon(R.mipmap.ic_launcher)
                //设置默认声音和震动
               // .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
              //  .setAutoCancel(true)//点击后取消
                .setWhen(System.currentTimeMillis())//设置通知时间
                .setPriority(Notification.PRIORITY_HIGH)//高优先级
            //    .setVisibility(Notification.VISIBILITY_PUBLIC)
                //android5.0加入了一种新的模式Notification的显示等级，共有三种：
                //VISIBILITY_PUBLIC  只有在没有锁屏时会显示通知
                //VISIBILITY_PRIVATE 任何情况都会显示通知
                //VISIBILITY_SECRET  在安全锁和没有锁屏的情况下显示通知
                .setContentIntent(pi);  //3.关联PendingIntent
         Notification myNotification   = myBuilder.build();
        //4.通过通知管理器来发起通知，ID区分通知
        NotificationManager myManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        myManager.notify(1, myNotification);
    }



}

