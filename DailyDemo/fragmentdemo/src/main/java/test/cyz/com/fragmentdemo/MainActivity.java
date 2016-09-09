package test.cyz.com.fragmentdemo;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.fragmentdemo.model.Category;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private TextView msgText;
    private TextView newText;
    private TextView contactText;
    private TextView settingText;
    private android.support.v4.app.FragmentManager fragmentManager;

    private Fragment contactFrag;
    private Fragment msgFrag;
    private Fragment newsFrag;
    private Fragment settingFrag;


//    private ListView listView;
//    private TextView textView;
//    private LinearLayout categoryLinearLayout;
//    private LayoutInflater layoutInflater;
//    private GridView gridView;
//    private List<Category> categoryList = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        initNewsCategory();
        fragmentManager = getSupportFragmentManager();
        setSelection(0);

//        categoryLinearLayout =(LinearLayout) findViewById(R.id.title_category);
//        layoutInflater = LayoutInflater.from(this);
//
//        categoryLinearLayout.addView(layoutInflater.inflate(R.layout.layout_grid_category, null));
//        gridView = (GridView)findViewById(R.id.gridView);
//        initGridView();
//        gridViewAdapter = new GirdViewAdapter(this, R.layout.ly_category_item, categoryList);
//        gridView.setAdapter(gridViewAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("news", "选中了第" + i);
//                TextView t;
//                for(int a = 0; a < adapterView.getCount(); a++)
//                {
//                    t = (TextView) adapterView.getChildAt(a);
//                    t.setTextColor(Color.BLACK);
//                }
//                t = (TextView) view;
//                t.setTextColor(Color.RED);
//            }
//        });
    }

//    private void initGridView() {
//        int size = categoryList.size();
//        int length = 60;
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        int gridviewWidth = (int) (size * (length + 4) * density);
//        int itemWidth = (int)(length * density);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
//        gridView.setLayoutParams(params);
//        gridView.setColumnWidth(itemWidth);
//        gridView.setHorizontalSpacing(3);
//        gridView.setStretchMode(GridView.NO_STRETCH);
//        gridView.setNumColumns(size);
//    }

    private void initView(){
        msgText = (TextView)findViewById(R.id.tv_msg);
        newText = (TextView) findViewById(R.id.tv_newText);
        contactText = (TextView) findViewById(R.id.tv_contact);
        settingText = (TextView) findViewById(R.id.tv_setting);
        msgText.setOnClickListener(this);
        newText.setOnClickListener(this);
        contactText.setOnClickListener(this);
        settingText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_msg:
                setSelection(0);
                break;
            case R.id.tv_contact:
                setSelection(1);
                break;
            case R.id.tv_newText:
                setSelection(2);
                break;
            case R.id.tv_setting:
                setSelection(3);
                break;
            default:
                break;
        }
    }

    private void setSelection(int index){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        clearSelected();
         switch (index){
             case 0:
                 msgText.setTextColor(Color.WHITE);
                 if(msgFrag != null){
                     transaction.show(msgFrag);
                 }
                 else{
                     msgFrag = new msgFragment();
                     transaction.add(R.id.frag_content, msgFrag);
                 }
                 break;
             case 1:
                 contactText.setTextColor(Color.WHITE);
                 if(contactFrag != null){
                     transaction.show(contactFrag);
                 }
                 else{
                     contactFrag = new contactFragment();
                     transaction.add(R.id.frag_content,contactFrag);
                 }
                 break;
             case 2:
                 newText.setTextColor(Color.WHITE);
                 if(newsFrag != null){
                     transaction.show(newsFrag);
                 }
                 else{
                     newsFrag = new newsFragment();
                     transaction.add(R.id.frag_content,newsFrag);
                 }
                 break;
             case 3:
                 settingText.setTextColor(Color.WHITE);
                 if(settingFrag != null){
                     transaction.show(settingFrag);
                 }
                 else{
                     settingFrag = new settingFragment();
                     transaction.add(R.id.frag_content,settingFrag);
                 }
                 break;
             default:
                 break;
         }
        transaction.commit();
    }

    private void clearSelected(){
        msgText.setTextColor(Color.BLACK);
        contactText.setTextColor(Color.BLACK);
        newText.setTextColor(Color.BLACK);
        settingText.setTextColor(Color.BLACK);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (msgFrag != null) {
            transaction.hide(msgFrag);
        }
        if (contactFrag != null) {
            transaction.hide(contactFrag);
        }
        if (newsFrag != null) {
            transaction.hide(newsFrag);
        }
        if (settingFrag != null) {
            transaction.hide(settingFrag);
        }
    }
}
