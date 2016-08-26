package test.cyz.com.mediaplayer.domain;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by M on 2016/8/24.
 */
public class VideoItem {
    private String name;
    private String path;
    private Bitmap thumb;
    private String createTime;

    public VideoItem(){}

    public VideoItem(String name, String path, String createTime){
        this.name = name;
        this.path = path;
        this.thumb = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
        SimpleDateFormat sf = new SimpleDateFormat("yy年MM月dd日HH时mm分");
        Date d = new Date(Long.valueOf(createTime)*1000);
        this.createTime = sf.format(d);
    }

    public void createThumb()
    {
        if(this.thumb == null)
        {
            this.thumb = ThumbnailUtils.createVideoThumbnail(this.path, MediaStore.Images.Thumbnails.MINI_KIND);
        }
    }

    //与createThumb()对应，需要一个释放bitmap资源的方法
    public void releaseThumb()
    {
        if(this.thumb != null){
            this.thumb.recycle();
            this.thumb = null;
        }
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Bitmap getThumb() {

        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        VideoItem another = (VideoItem) obj;
        return another.path.equals(this.path);
    }
}
