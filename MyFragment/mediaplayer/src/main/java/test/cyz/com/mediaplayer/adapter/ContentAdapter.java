package test.cyz.com.mediaplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.cyz.com.mediaplayer.R;
import test.cyz.com.mediaplayer.domain.VideoItem;

/**
 * Created by M on 2016/8/24.
 */
public class ContentAdapter extends ArrayAdapter<VideoItem> {

    private final int mResource;
    private final LayoutInflater mInflater;
    private List<VideoItem> list;

    public ContentAdapter(Context context, int Resource, List<VideoItem> objects) {
        super(context, Resource, objects);
        this.list = objects;
        this.mResource = Resource;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        VideoItem videoItem = getItem(position);
        View mview = null;
        if(convertView == null){
            Log.d("media", "convertView is null!");
            mview = mInflater.inflate(mResource, null);
            Log.d("media", "引入mResource!");
        }
        else{
            mview = convertView;
        }
        ImageView videoThumb = (ImageView) mview.findViewById(R.id.video_thumb);
        TextView videoText = (TextView)mview.findViewById(R.id.video_title);
        TextView videoData = (TextView)mview.findViewById(R.id.video_date);
        videoThumb.setImageBitmap(videoItem.getThumb());
        videoText.setText(videoItem.getName());
        videoData.setText(videoItem.getCreateTime());
        Log.d("media", "已获取view");
        return mview;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public int getPosition(VideoItem item) {
        return super.getPosition(item);
    }
}
