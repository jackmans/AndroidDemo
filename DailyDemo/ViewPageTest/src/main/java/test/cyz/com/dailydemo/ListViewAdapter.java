package test.cyz.com.dailydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by M on 2016/9/13.
 */
public class ListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private int mResource;
    private List<String> list;

    public ListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mResource = resource;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(convertView == null){
            view = layoutInflater.inflate(mResource, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.text_content);
            textView.setText(list.get(position));
        }
        else{
            view = convertView;
        }

        return view;
    }



}
