package test.cyz.com.fragmentdemo;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.fragmentdemo.model.Category;

/**
 * Created by M on 2016/9/2.
 */
public class msgFragment extends Fragment {

    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private GridView gridView;
    private GirdViewAdapter gridViewAdapter;
    private List<Category> categoryList = new ArrayList<Category>();
    private TopBarFragAdapter topBarFragAdapter;
    private ViewPager viewPager;
    private int currentIndex;
    private HorizontalScrollView h;

//    private LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
//        layoutInflater = LayoutInflater.from(getActivity());
        LinearLayout msgFragment = (LinearLayout) inflater.inflate(R.layout.frag_msg, container, false);

        Log.d("news", "msgFrag:" + msgFragment.toString());
//        LinearLayout titleCate = (LinearLayout) msgFragment.findViewById(R.id.title_category);
//        Log.d("news", "titleCate:" + titleCate.toString());
//        LinearLayout layout_grid = (LinearLayout) inflater.inflate(R.layout.layout_grid_category, null);

//        titleCate.addView(layout_grid);
//        Log.d("news", "layout_grid:" + layout_grid.getParent().toString());
        h = (HorizontalScrollView) msgFragment.findViewById(R.id.scroll);
        gridView = (GridView) msgFragment.findViewById(R.id.gridView);
        initNewsCategory();
        initFragments();
        initGridView();

        gridViewAdapter = new GirdViewAdapter(getActivity(), R.layout.ly_category_item, categoryList);
        Log.d("news", gridViewAdapter.toString());
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("news", "选中了第" + i);
                TextView t;
//                for(int a = 0; a < adapterView.getCount(); a++)
//                {
//                    t = (TextView) adapterView.getChildAt(a);
//                    t.setTextColor(Color.BLACK);
//                }
                t = (TextView) adapterView.getChildAt(currentIndex);
                t.setTextColor(Color.BLACK);
                currentIndex = i;
                t = (TextView) adapterView.getChildAt(currentIndex);
                t.setTextColor(Color.RED);
                viewPager.setCurrentItem(i);

            }
        });
       Log.d("news", "初始化完成！");

        viewPager = (ViewPager) msgFragment.findViewById(R.id.viewpager);
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        TopBarFragAdapter adapter = new TopBarFragAdapter(fragmentManager, fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                TextView selectedBeforeView = (TextView) gridView.getChildAt(currentIndex);
                selectedBeforeView.setTextColor(Color.BLACK);
                currentIndex = position;
                TextView selectAfterView = (TextView) gridView.getChildAt(currentIndex);
                selectAfterView.setTextColor(Color.RED);
                selectAfterView.setTextColor(Color.RED);
                int scrollx = selectAfterView.getLeft();
                h.scrollTo(scrollx, 0);
//                gridView.setSelection(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return msgFragment;
    }

    private void initGridView() {
        int size = categoryList.size();
        int length = 60;
        DisplayMetrics dm;
        dm = getResources().getDisplayMetrics();
        float density = dm.density;
        int gridViewWidth = (int) (size * length * density);
        int itemWidth = (int)(length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(itemWidth);
        gridView.setHorizontalSpacing(0);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size);
    }

    private void initNewsCategory() {
        String[] text = new String[]{"头条", "体育", "科技", "财经", "汽车", "时尚", "房产", "段子", "军事", "历史", "游戏", "两性"};
        for(int i = 0; i < text.length; i++){
            Category category  = new Category();
            category.setName(text[i]);
            category.setId(i);
            categoryList.add(category);
            Log.d("news", category.toString() + category.getName());
        }
    }

    private void initFragments(){
        for(int i = 0; i < categoryList.size(); i++){
            Category category = categoryList.get(i);
            Fragment contentFragment = new TopBarFragment();
            Bundle bundle = new Bundle();
            bundle.putString("newsCategory", category.getName());
            Log.d("news"," initFragment:" + category.getName());
            contentFragment.setArguments(bundle);
            fragmentList.add(contentFragment);
        }

    }
}
