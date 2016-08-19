package test.cyz.com.cheeseweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.Locale;
import java.util.prefs.PreferenceChangeEvent;

import test.cyz.com.cheeseweather.db.WeatherDB;
import test.cyz.com.cheeseweather.model.City;
import test.cyz.com.cheeseweather.model.County;
import test.cyz.com.cheeseweather.model.Province;

/**
 * Created by M on 2016/8/16.
 */
public class Utility {
    //为什么这里要加个同步和static修饰
    public synchronized static boolean handleProvincesResponse(WeatherDB weatherDB, String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            //为什么这里要加这个判断，迟点回来看看
            if(allProvinces != null && allProvinces.length > 0){
                for(String province: allProvinces){
                    String[] array = province.split("\\|");
                    Province pro = new Province();
                    pro.setProvinceName(array[1]);
                    pro.setProvinceCode(array[0]);
                    weatherDB.savePerovince(pro);
                }
                return true;
            }

        }
        return false;
    }

    public synchronized static boolean handleCitiesResponse(WeatherDB weatherDB, String response, int provinceId){
        if(!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            //为什么这里要加这个判断，迟点回来看看
            if (allCities != null && allCities.length > 0) {
                for (String city : allCities) {
                    String[] array = city.split("\\|");
                    City c = new City();
                    c.setCityName(array[1]);
                    c.setCityCode(array[0]);
                    c.setProvinceId(provinceId);
                    weatherDB.saveCity(c);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCountiesResponse(WeatherDB weatherDB, String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allcounties = response.split(",");
            if(allcounties != null && allcounties.length > 0){
                for(String county : allcounties ){
                    String[] array = county.split("\\|");
                    County ci = new County();
                    ci.setCountyName(array[1]);
                    ci.setCountyCode(array[0]);
                    ci.setCityId(cityId);
                    weatherDB.saveCounty(ci);
                }
                return true;
            }
         }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response){
        try{
            JSONObject jsonObject =  new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityId");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);

         }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年m月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
    }
}
