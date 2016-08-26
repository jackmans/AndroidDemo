package test.cyz.com.mediaplayer.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import test.cyz.com.mediaplayer.R;

/**
 * Created by M on 2016/8/26.
 */
public class ShowVideo extends Activity {

    public VideoView videoView;
    public int lastPlayTime;
    public String p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_video);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            videoView = (VideoView)findViewById(R.id.land_view);
            Log.d("media", "現在爲橫屏！");
        }
        else{
            videoView = (VideoView) findViewById(R.id.v_view);
            Log.d("media", "現在爲豎屏！");
        }

        Uri uri = getIntent().getData();
        p = uri.getPath();
        Log.d("media", "獲取到視頻路徑！");
        if(p == null || p.equals("")){
            Log.d("media", "path 爲空");
        }
        else{
            videoView.setVideoPath(p);
            Log.d("media", "創建播放控制器");
            MediaController controller = new MediaController(this);
            videoView.setMediaController(controller);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        lastPlayTime = videoView.getCurrentPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(lastPlayTime > 0){
            videoView.seekTo(lastPlayTime);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("lastTime", videoView.getCurrentPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastPlayTime = savedInstanceState.getInt("lastTime");
    }
}
