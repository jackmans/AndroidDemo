package test.cyz.com.cheeseweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import test.cyz.com.cheeseweather.util.Utility;

/**
 * Created by M on 2016/8/15.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {

    /*
    *  Province表建表语句
    * */
    public static final String CREATE_PROVINCE = "create table Province( " + " id integer primary key autoincrement ,"  + "province_name text, " + "province_code text)";

    public static final String CREATE_CITY = "create table City ( " + " id integer primary key autoincrement ," + "city_name text, " + "city_code text, " + "province_id integer)";

    public static final String CREATE_COUNTY = "create table County ( "+ "id integer primary key autoincrement ," + "county_name text," + "county_code text, " + "city_id integer)";

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
