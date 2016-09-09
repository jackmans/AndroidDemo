package test.cyz.com.layouttest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    String[] btnLable = new String[]{
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "0", ".", "=", "/"
    };

    RefreshableView refreshableView;
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pull_refresh_layout);
//        GridLayout gridCal = (GridLayout) findViewById(R.id.grid_cal);

//        for (int i = 0; i < btnLable.length; i++) {    //添加16个网格，每个网格1个按钮
//            Button button = new Button(this);
//            button.setText(btnLable[i]);
//            button.setTextSize(30);
//
//            GridLayout.Spec rowSpec = GridLayout.spec(i / 4 + 2);   //行位置
//            GridLayout.Spec colSpec = GridLayout.spec(i % 4);       //列位置
//
//            GridLayout.LayoutParams gridLayoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);   //属性
////            gridLayoutParams.setGravity(Gravity.FILL_HORIZONTAL);   //横向排满容器
//
//            gridCal.addView(button, gridLayoutParams);              //将按钮添加到网格容器内
//        }


                refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
                listView = (ListView) findViewById(R.id.list_view);
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
                listView.setAdapter(adapter);
                refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
                    @Override
                    public void onRefresh() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        refreshableView.finishRefreshing();
                    }
                }, 0);
    }

}



