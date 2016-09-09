package test.cyz.com.fragmentdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.fragmentdemo.model.Category;

/**
 * Created by M on 2016/9/8.
 */
public class ListViewAdapter extends ArrayAdapter {

    private Context c;
    private List<String> listViewContent;
    private int mResource;

    public ListViewAdapter(Context context, int resource, List<String> list) {
        super(context, resource, list);
        this.c = context;
        this.mResource = resource;
        this.listViewContent = list;
    }

    @Override
    public int getCount() {
        return listViewContent.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        if(convertView != null){
            view = convertView;
        }else{
            view = layoutInflater.inflate(mResource, null);
            TextView textView = (TextView) view.findViewById(R.id.category_item);
            textView.setText(listViewContent.get(position));

        }
        Log.d("news", "listView数据传入完毕！");
        return view;
    }
}
