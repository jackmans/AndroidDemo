package test.cyz.com.mediaplayer.thread;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.mediaplayer.adapter.ContentAdapter;
import test.cyz.com.mediaplayer.domain.VideoItem;

/**
 * Created by M on 2016/8/24.
 */
public class VideoUpdateTask extends AsyncTask<Object, VideoItem, Void>{
    private List<VideoItem> listVideo;
    private Context context;
    private ContentAdapter adapter;
    public ListView lvVideo;
    private MenuItem refreshMenuItem;
    List<VideoItem> mDataList = new ArrayList<VideoItem>();;

    public VideoUpdateTask(Context c, List<VideoItem> list, ContentAdapter adapter, ListView lvVideo, MenuItem refreshMenuItem){
        context = c;
        listVideo = list;
        this.adapter = adapter;
        this.lvVideo = lvVideo;
        this.refreshMenuItem = refreshMenuItem;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Object[] objects) {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String [] searchKey = new String[] {
                MediaStore.Video.Media.TITLE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
        };

        String [] keywords = null;
        String where = MediaStore.Video.Media.DATA + " like \"%"+"/Video"+"%\"";
        String sortOder = MediaStore.Video.Media.DEFAULT_SORT_ORDER;

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, searchKey, where, keywords, sortOder);
        Log.d("media", cursor.toString());
        if(cursor != null){
            Log.d("media", "cursor is not null!");
            while(cursor.moveToNext()){
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String createdTime = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
                Log.d("media", "路径：" + path + ",名字："+ name + ",时间：" + createdTime);
                VideoItem videoItem = new VideoItem(name, path, createdTime);
                if(listVideo.contains(videoItem) == false){
                    videoItem.createThumb();
                    publishProgress(videoItem);
                    Log.d("media", "publishProgressing!!");
                }
                mDataList.add(videoItem);

            }
            cursor.close();
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(VideoItem[] videoItem) {
        Log.d("media", "onProgressUpdate!!");
        VideoItem data = videoItem[0];
        listVideo.add(data);
        Log.d("media", data.getName() + "|" + data.getPath() + "|" + data.getCreateTime());
        adapter = (ContentAdapter) lvVideo.getAdapter();
        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onCancelled() {
        updateResult();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        updateResult();
    }

    private void updateResult() {
        if(refreshMenuItem != null){
            for(int i = 0; i < listVideo.size(); i++){
                if (mDataList.contains(listVideo.get(i))){
                    VideoItem video = listVideo.get(i);
                    video.releaseThumb();
                    listVideo.remove(i);
                    i =  i - 1;
                }
            }
            adapter = (ContentAdapter) lvVideo.getAdapter();
            adapter.notifyDataSetChanged();
        }
        mDataList.clear();
    }


}
