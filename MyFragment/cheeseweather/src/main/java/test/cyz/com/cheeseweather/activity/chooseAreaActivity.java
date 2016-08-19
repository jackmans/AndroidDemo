package test.cyz.com.cheeseweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.cyz.com.cheeseweather.R;
import test.cyz.com.cheeseweather.db.WeatherDB;
import test.cyz.com.cheeseweather.model.City;
import test.cyz.com.cheeseweather.model.County;
import test.cyz.com.cheeseweather.model.Province;
import test.cyz.com.cheeseweather.util.HttpCallbackListener;
import test.cyz.com.cheeseweather.util.HttpUtil;
import test.cyz.com.cheeseweather.util.Utility;

/**
 * Created by M on 2016/8/16.
 */
public class chooseAreaActivity extends Activity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private WeatherDB weatherDB;
    private List<String> dataList = new ArrayList<String>();
    private int currentLevel;
    private Province selectProvince;
    private City selectCity;
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences prefs = PreferenceManager.
//                getDefaultSharedPreferences(this);
//        if (prefs.getBoolean("city_selected", false)) {
//            Intent intent = new Intent(this, WeatherActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView)findViewById(R.id.list_view);
        titleText = (TextView)findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        weatherDB = WeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentLevel == LEVEL_PROVINCE){
                    selectProvince = provinceList.get(i);
                    queryCities();
                    Log.d("main", "onItemClick :" + selectProvince.getProvinceName());
                }
                else if(currentLevel == LEVEL_CITY){
                    selectCity = cityList.get(i);
                    queryCounties();
                    Log.d("main", "onItemClick :" + selectCity.getCityName());
                }
                else if (currentLevel == LEVEL_COUNTY) {
                    String countyCode = countyList.get(i).getCountyCode();
                    Log.d("main", "城市编号:" + countyCode);
                    Intent intent = new Intent(chooseAreaActivity.this,
                            WeatherActivity.class);
                    intent.putExtra("county_code", countyCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvinces();
        Log.d("main", "onItemClick :" + "queryProvinces");

    }

    private void queryProvinces() {
        provinceList = weatherDB.loadProvinces();

        if(provinceList.size() > 0){
            dataList.clear();
            for(Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            Log.d("main", "dataList省份：" + dataList.toString());
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        }
        else{
            queryFromServer(null, "province");
            Log.d("main", "queryFromServer(null, \"province\")");
        }

    }

    private void queryCounties() {
        countyList = weatherDB.loadCounty(selectCity.getId());
        if(countyList.size() > 0){
            dataList.clear();
            for(County county :countyList){
                dataList.add(county.getCountyName());
            }
            Log.d("main", "dataList县级城市：" + dataList.toString());
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        }
        else {
            queryFromServer(selectCity.getCityCode(), "county");
            Log.d("main", "queryFromServer(selectCity.getCityCode(), \"county\")");
        }

    }

    private void queryCities() {
        cityList = weatherDB.loadCity(selectProvince.getId());
        if(cityList.size() > 0){
            dataList.clear();
            for(City city : cityList){
                dataList.add(city.getCityName());
            }
            Log.d("main", "dataList城市：" + dataList.toString());
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        }
        else{
            queryFromServer(selectProvince.getProvinceCode(),"city");
            Log.d("main", "queryFromServer(selectProvince.getProvinceCode(),\"city\")");
        }
    }

    public void queryFromServer(final String code, final String type){
        String address;
        if(!TextUtils.isEmpty(code)){
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        }
        else{
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Boolean b = false;
                if("province".equals(type)){
                    b = Utility.handleProvincesResponse(weatherDB, response);
                }
                else if("city".equals(type)){
                   b = Utility.handleCitiesResponse(weatherDB, response, selectProvince.getId());
                }
                else if("county".equals(type)){
                    b = Utility.handleCountiesResponse(weatherDB, response, selectCity.getId());
                }
                if(b){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvinces();
                            }
                            else if("city".equals(type)){
                                queryCities();
                            }
                            else if("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    private void showProgressDialog() {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if(currentLevel == LEVEL_COUNTY){
            queryCities();
            Log.d("main", " queryCities");
        }
        else if(currentLevel == LEVEL_CITY) {
            queryProvinces();
            Log.d("main", " queryProvince");
        }
        else{
            finish();
        }
    }

}
