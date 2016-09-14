package test.cyz.com.dailydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 2016/9/13.
 */
public class ListViewActivity extends Activity implements View.OnTouchListener{

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private TextView textContent;
    private LinearLayout rootLayout;
    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_move);
        initArray();
        rootLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.lv_item, null);
        listView = (ListView) findViewById(R.id.lv_list);
        arrayAdapter = new ListViewAdapter(this, R.layout.lv_item, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnTouchListener(this);
    }

    private void initArray() {
        String[] text = new String[]{"头条", "体育", "科技", "财经", "汽车", "时尚", "房产", "段子", "军事", "历史", "游戏", "两性"};
        for(int a = 0; a < text.length; a++){
            list.add(text[a]);
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int)motionEvent.getX();
        int y = (int)motionEvent.getY();

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
               int position = listView.pointToPosition(x, y);
                View selected = listView.getChildAt(position);
                rootLayout = (LinearLayout) selected.findViewById(R.id.lt_item);
                break;
            case MotionEvent.ACTION_MOVE:
                int scrollX = rootLayout.getScrollX();
                int newScroll = scrollX +
        }

        return false;
    }
}
