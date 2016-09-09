package test.cyz.com.fragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.fragmentdemo.R;

/**
 * Created by M on 2016/9/7.
 */
public class TopBarFragment extends Fragment {

    private ListView listView;
    private String newsCate;
    private List<String> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.topbarfragment, container, false);
        initList();
        listView = (ListView) linearLayout.findViewById(R.id.listView_content);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), R.layout.ly_category_item, list);
        listView.setAdapter(listViewAdapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("move", "action:DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("move", "action:MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("move", "action:UP");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        Log.d("news", "topBsrFragment:" + listView.toString());

        return linearLayout;
    }

    void initList(){
        String[] text = new String[]{"头条", "体育", "科技", "财经", "汽车", "时尚", "房产", "段子", "军事", "历史", "游戏", "两性"};
        for(int i = 0; i < text.length; i++){
            list.add(text[i]);
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        newsCate = args.getString("newsCategory");
        Log.d("news", "newsCate:" + newsCate.toString());

    }
}
