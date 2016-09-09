package test.cyz.com.fragmentdemo;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import test.cyz.com.fragmentdemo.model.Category;

/**
 * Created by M on 2016/9/5.
 */
public class GirdViewAdapter extends ArrayAdapter {

    private List<Category> categoryList;
    private Context context;
    private int mResource;

    public GirdViewAdapter(Context context, int resource, List<Category> categoryList) {
        super(context,resource);
        this.context = context;
        this.mResource = resource;
        this.categoryList = categoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view;
        Category item = (Category) getItem(position);
        if(convertView != null){
            view = convertView;
        }else{
            view = layoutInflater.inflate(mResource, null);
            TextView textView = (TextView) view.findViewById(R.id.category_item);
            textView.setText(item.getName());

        }
        Log.d("news", item.getName());
        Log.d("news", "数据传入完毕！");
        return view;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryList.get(position).getId();
    }
}
