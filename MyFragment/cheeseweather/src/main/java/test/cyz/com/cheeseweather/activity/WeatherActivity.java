package test.cyz.com.cheeseweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import test.cyz.com.cheeseweather.R;
import test.cyz.com.cheeseweather.util.HttpCallbackListener;
import test.cyz.com.cheeseweather.util.HttpUtil;
import test.cyz.com.cheeseweather.util.Utility;

/**
 * Created by M on 2016/8/18.
 */
public class WeatherActivity extends Activity implements View.OnClickListener {

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;
    private Button switchCity;
    private Button refreshWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {

            publishText.setText("在查啦，急毛啊！");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            //直接查本地
            showWeather();
        }
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
    }
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.switch_city:
                    Intent intent = new Intent(this, chooseAreaActivity.class);
                    intent.putExtra("from_weather_activity", true);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.refresh_weather:
                    publishText.setText("等等等等..");
                    SharedPreferences prfs = PreferenceManager.getDefaultSharedPreferences(this);
                    String weatherCode = prfs.getString("weather_code", "");
                    if(!TextUtils.isEmpty(weatherCode)){
                        queryWeatherInfo(weatherCode);
                    }
                    break;
                default:
                    break;
            }
    }

    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/data/cityinfo" + weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }

    private void queryWeatherCode(String countyCode){
        String address = "htpp://www.weather.com.cm/data/list3/city" + countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }


    private void queryFromServer(final String address, final String type){
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if("countyCode".equals(type)){
                    if (!TextUtils.isEmpty(response)){
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2 ){
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                    else if ("weatherCode".equals(type)){
                        Utility.handleWeatherResponse(WeatherActivity.this, response);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showWeather();
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void onError(Exception e){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                publishText.setText("GG!!");
            }
        });
    }

    private void showWeather(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_namne", ""));
        temp1Text.setText(prefs.getString("temp1", ""));
        temp2Text.setText(prefs.getString("temp2", ""));
        weatherDespText.setText(prefs.getString("weather_desp", ""));
        publishText.setText("今天" + prefs.getString("publish_time", "") + "发布");
        currentDateText.setText(prefs.getString("current_date", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }

}
