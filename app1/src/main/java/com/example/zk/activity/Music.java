package com.example.zk.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zk on 2017/5/21.
 */

public class Music {

    private static String TAG="wodelog";

    private String title;
    private String singer;
    private String album;//歌曲专辑
    private long size;
    private long duration;
    private String url;
    private String _display_name;
    private String mime_type;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public static Uri getAlbumArtUri() {
        return albumArtUri;
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

    //获取专辑封面的Uri
    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");


    public static List<Music> getAllMusic(Context context) {
        ContentResolver cr = context.getContentResolver();
        if (cr == null) {
            return null;
        }
        // 获取所有歌曲
        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
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

            music.setBitmap(getArtwork(context,songid,albumid ,false));
            list.add(music);
        }
        return list;
    }




    public static List<Map<String, Object>> getAllArtists(Context context) {
        ContentResolver cr = context.getContentResolver();
        if (cr == null) {
            return null;
        }
        // 获取所有歌手
        Cursor cursor = cr.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,  null ,  null ,  null ,
                MediaStore.Audio.Artists.ARTIST_KEY);
        if (null == cursor) {
            return null;
        }
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));

            String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
//共有多少该歌手的专辑
            int numOfAlbum = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
//共有多少该歌手的歌曲
            int numOfSong = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));

            Map<String,Object> map=new HashMap<String,Object>();
            map.put("name",name);
            map.put("numOfSong",numOfSong);
            map.put("id",id);
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
        Cursor cursor = cr.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,  null ,  null , null ,
                MediaStore.Audio.Albums.ALBUM_KEY);
        if (null == cursor) {
            return null;
        }
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        while (cursor.moveToNext()) {

            //MediaStore.Audio.Albums._ID ：专辑id
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID));

            //MediaStore.Audio.Albums.ALBUM：专辑名称
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));

            //MediaStore.Audio.Albums.NUMBER_OF_SONGS：共用多少歌曲属于该专辑
            int numOfSong = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("name",name);
            map.put("numOfSong",numOfSong);
            map.put("id",id);
            list.add(map);
        }
        return list;
    }

    public static List<Map<String, Object>> getMusicInfo(Context context,String id,String aid){
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

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        while (query.moveToNext()) {
       // MediaStore.Audio.Media.TITLE：歌曲的名称
        String tilte = query.getString(query.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
        //MediaStore.Audio.Media.ARTIST：歌曲的歌手名
        String artist = query.getString(query.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("tilte",tilte);
            map.put("artist",artist);
            list.add(map);
        }
        return list;
    }


    public static Map<String,Object> getMusicInfoByFile(String fileName){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        Map<String,Object>map=new HashMap<>();

        try
        {
            mmr.setDataSource(fileName);
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            Log.d(TAG, "title:" + title);
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            Log.d(TAG, "album:" + album);
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            Log.d(TAG, "artist:" + artist);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
            Log.d(TAG, "duration:" + duration);
            byte[] pic = mmr.getEmbeddedPicture();  // 图片，可以通过BitmapFactory.decodeByteArray转换为bitmap图片

            map.put("tilte",title);
            map.put("artist",artist);
            map.put("pic",pic);
            return map;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
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

    public static Bitmap getArtwork(Context context,  long song_id, long album_id,
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

        Resources res=context.getResources();

        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.ic_menu_send);

        return bmp;
    }





}
