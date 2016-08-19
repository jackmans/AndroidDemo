package test.cyz.com.cheeseweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.cheeseweather.model.City;
import test.cyz.com.cheeseweather.model.County;
import test.cyz.com.cheeseweather.model.Province;

/**
 * Created by M on 2016/8/15.
 */
public class WeatherDB {
    public static final int VERSION = 1;

    public static final String DB_NAME = "cool_weather";

    public static WeatherDB weatherDB;

    private SQLiteDatabase db;

//    将构造方法私有化
    private WeatherDB(Context context){
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        db  = dbHelper.getWritableDatabase();
    }

    public synchronized static WeatherDB getInstance(Context context){
        if(weatherDB == null){
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    public void savePerovince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

//    从数据库中读取所有省份的信息
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null , null, null, null);
        if( cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt((cursor.getColumnIndex("id"))));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while(cursor.moveToNext());
        }
            return list;
    }

//    保存city信息到数据库中
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("province_id", city.getProvinceId());
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            db.insert("City", null, values);
        }
    }

    //从数据库读取
    public List<City> loadCity(int provinceId){
        List<City> list = new ArrayList<City>();
//        我什么这里 要用String数组，每次都需要new一个数组
        Cursor cursor = db.query("City", null, "province_id = ?" ,new String[]{String.valueOf(provinceId) }, null, null, null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(provinceId);
                list.add(city);
            }
            while(cursor.moveToNext());
        }
        return list;
    }

//    保存County实例到数据库
    public void saveCounty(County county){
        if(county !=  null){
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    public List<County> loadCounty(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCityId(cityId);
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                list.add(county);
            }
            while(cursor.moveToNext());
        }
        return list;
    }

}
