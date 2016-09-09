package test.cyz.com.fragmentdemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import test.cyz.com.fragmentdemo.model.Category;

/**
 * Created by M on 2016/9/7.
 */
public class TopBarFragAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public TopBarFragAdapter(FragmentManager fm, List<Fragment> flist){
        super(fm);
        this.fragmentList = flist;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
