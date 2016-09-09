package test.cyz.com.dailydemo;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.dailydemo.MyFragmentAdapter;
import test.cyz.com.dailydemo.R;
import test.cyz.com.dailydemo.fragment.MyFragment1;
import test.cyz.com.dailydemo.fragment.MyFragment2;
import test.cyz.com.dailydemo.fragment.MyFragment3;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private TextView tv_tab1;
    private TextView tv_tab2;
    private TextView tv_tab3;
    private ImageView imageView;
    float moveOne;
    private ViewPager viewPager;
    Boolean scrolling = false;
    Boolean scrollFinish = false;
    float offset;
    private long currentTime = 0;
    private long startTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.vp_myViePage);
        imageView = (ImageView) findViewById(R.id.tab_line);
        initLineImage();
        Fragment fragment1 = new MyFragment1();
        Fragment fragment2 = new MyFragment2();
        Fragment fragment3 = new MyFragment3();

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);

        FragmentPagerAdapter fragmentPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(0);
        LinearLayout tabVIew = (LinearLayout) findViewById(R.id.tab_view);
        tv_tab1 = (TextView) tabVIew.findViewById(R.id.tv_tab1);
        tv_tab2 = (TextView) tabVIew.findViewById(R.id.tv_tab2);
        tv_tab3 = (TextView) tabVIew.findViewById(R.id.tv_tab3);
        tv_tab1.setOnClickListener(this);
        tv_tab2.setOnClickListener(this);
        tv_tab3.setOnClickListener(this);
        Log.d("viewPage", "监听器完成！");
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentTime = System.currentTimeMillis();
        if ( scrolling && (currentTime - startTime > 50)) {
            Log.d("viewPage", "scrolling=" + "fragmentId:" + position + "time:" + currentTime);
            Log.d("viewPage", "移动了:" + positionOffset);
            movePositionX(position, moveOne * positionOffset);
            startTime = currentTime;
        }
        if(scrollFinish){
            movePositionX(position, 0);
            Log.d("viewPage", " 到位！");
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                tv_tab1.setTextColor(Color.BLUE);
                tv_tab2.setTextColor(Color.BLACK);
                tv_tab2.setTextColor(Color.BLACK);
                movePositionX(0, 0);
                break;
            case 1:
                tv_tab1.setTextColor(Color.BLACK);
                tv_tab2.setTextColor(Color.BLUE);
                tv_tab3.setTextColor(Color.BLACK);
                movePositionX(1, 0);
                break;
            case 2:
                tv_tab1.setTextColor(Color.BLACK);
                tv_tab2.setTextColor(Color.BLACK);
                tv_tab3.setTextColor(Color.BLUE);
                movePositionX(2, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch(state){
            case 1:
                scrolling = true;
                scrollFinish = false;
                Log.d("viewPage", "在滚动中..");
                break;
            case 2:
                scrolling = false;
                scrollFinish = true;
                Log.d("viewPage", "滚动停止！");
                break;
            default:
                scrolling = false;
                scrollFinish = false;
                break;
        }
    }

    public void initLineImage(){
        DisplayMetrics dm =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        lp.width = screenW / 3;
        imageView.setLayoutParams(lp);
        moveOne = lp.width;
        Log.d("viewPage", "initLineImage!");
    }

    private void movePositionX(int toPosition, float offset){
        float curTranslationX = imageView.getTranslationX();
        float toPositionX = moveOne * toPosition + offset;
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", curTranslationX, toPositionX);
        animator.setDuration(100);
        animator.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_tab2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_tab3:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
}
