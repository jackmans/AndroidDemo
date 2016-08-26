package test.cyz.com.mediaplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.mediaplayer.R;
import test.cyz.com.mediaplayer.adapter.ContentAdapter;
import test.cyz.com.mediaplayer.domain.VideoItem;
import test.cyz.com.mediaplayer.thread.VideoUpdateTask;

;

public class MainActivity extends Activity {

    List<VideoItem> listVideo = new ArrayList<>();
    public ListView lvVideo;
    private MenuItem refreshMenuItem;
    private VideoUpdateTask mVideoUpdateTask;
    private ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvVideo = (ListView)findViewById(R.id.lvVideo);

//        List<VideoItem> list = new ArrayList<>();
//        for(int i =0;i<10;i++) {
//            VideoItem videoItem = new VideoItem();
//            videoItem.setCreateTime(new Date().toString());
//            videoItem.setName("df");
//            videoItem.setPath("path");
//            videoItem.setThumb(null);
//            list.add(videoItem);
//        }

        adapter = new ContentAdapter(this, R.layout.video_item, listVideo);
        lvVideo.setAdapter(adapter);
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideoItem item = listVideo.get(i);
                Intent intent = new Intent(MainActivity.this, ShowVideo.class);
                intent.setData(Uri.parse(item.getPath()));
                startActivity(intent);
            }
        });

        mVideoUpdateTask = new VideoUpdateTask(this, listVideo,adapter, lvVideo, refreshMenuItem);
        mVideoUpdateTask.execute();
        Log.d("media", " mVideoUpdateTask execute!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if((mVideoUpdateTask != null) &&
                (mVideoUpdateTask.getStatus() == AsyncTask.Status.RUNNING))
        {
            mVideoUpdateTask.cancel(true);
        }
        mVideoUpdateTask = null;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("media", "初始化菜單按鈕！");
        getMenuInflater().inflate(R.menu.menu, menu);
        refreshMenuItem = menu.findItem(R.id.menu_refresh);
        refreshMenuItem.setTitle(R.string.refresh);
        if ((mVideoUpdateTask != null) && (mVideoUpdateTask.getStatus() == AsyncTask.Status.RUNNING)) {
            refreshMenuItem.setTitle(R.string.stop_refresh);
        } else {
            refreshMenuItem.setTitle(R.string.refresh);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_refresh:{
                if((mVideoUpdateTask != null) && (mVideoUpdateTask.getStatus() == AsyncTask.Status.RUNNING)){
                    mVideoUpdateTask.cancel(true);
                    mVideoUpdateTask = null;
                }
                else{
                    mVideoUpdateTask = new VideoUpdateTask(this, listVideo, adapter, lvVideo, refreshMenuItem);
                    mVideoUpdateTask.execute();
                }
            }
            break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }


}
