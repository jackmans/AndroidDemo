package test.cyz.com.layouttest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by M on 2016/9/6.
 */
public class SimpleLayout extends ViewGroup {

    public SimpleLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(getChildCount() > 0){
            View childView =  getChildAt(0);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if(getChildCount() > 0){
            View childView = getChildAt(0);
            childView.layout(0, 0, 200, 200);
        }
    }


}
