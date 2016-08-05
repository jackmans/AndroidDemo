package test.cyz.com.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class NewsContentActivity extends Activity {
    public static void actionStart(Context context, String newsTitle,
                              String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }
}