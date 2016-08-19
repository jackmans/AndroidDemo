package test.cyz.com.cheeseweather.util;

/**
 * Created by M on 2016/8/16.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}