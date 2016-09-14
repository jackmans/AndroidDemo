package test.cyz.com.dailydemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.cyz.com.dailydemo.R;

/**
 * Created by M on 2016/9/13.
 */
public class LayoutActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
        View view = LayoutInflater.from(this).inflate(R.layout.view_test, linearLayout, false);

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(view);
        LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.view_test);
        TextView textView = (TextView) view.findViewById(R.id.textView);
//        int textHeight = textView.getHeight();
//        int textWidth = textView.getWidth();
//        int height = layout2.getHeight();
//        int width = layout2.getWidth();

/*        Log.d("layouttest", "gao:" + String.valueOf(height) + "kuang:" + String.valueOf(width));
        Log.d("layouttest", "gao1:" + String.valueOf(textHeight) + "kuang1:" + String.valueOf(textWidth));*/


    }
}
