package com.yunna.cc.reweeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yunna.cc.reweeed.adapter.DailyWeatherAdapter;
import com.yunna.cc.reweeed.adapter.HourlyWeatherAdapter;
import com.yunna.cc.reweeed.adapter.WeatherAdviceAdapter;
import com.yunna.cc.reweeed.bean.AddressBean;
import com.yunna.cc.reweeed.bean.HourlyWeatherBean;
import com.yunna.cc.reweeed.bean.SevenDayWeather;
import com.yunna.cc.reweeed.bean.TodayWeather;
import com.yunna.cc.reweeed.bean.WeatherAdviceBean;
import com.yunna.cc.reweeed.util.ResourceUtil;
import com.yunna.cc.reweeed.util.ToastUtil;
import com.yunna.cc.reweeed.view.WeatherView;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tv_city;
    TextView tv_temp;
    TextView tv_max_min;
    TextView tv_date;


    TextView tv_uv;
    TextView tv_body_temp;
    TextView tv_rain_count;
    TextView tv_humidity;
    TextView tv_atmos;
    TextView tv_sight;
    ImageView iv_now_status;
    ImageView iv_click;


    RecyclerView rlv;
    RecyclerView rlv_daily_weather;
    RecyclerView rlv_type;
    WeatherView weatherView;


    List<HourlyWeatherBean.HourlyDTO> data;
    List<SevenDayWeather.DailyDTO> dailyData;
    List<WeatherAdviceBean.DailyDTO> adviceData;

    RxPermissions rxPermissions;
    LocationClient locationClient = null;
    ssLocationListener ssLocationListener = new ssLocationListener();
    private static String lLoaction = null;
    int card;

    private int SERVER_LOAD_TIMES;
    private final int MAX_LOAD_TIMES=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(ssLocationListener);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlv = findViewById(R.id.hourly_weather);
        iv_click = f(R.id.iv_re_flush);
        rxPermissions = new RxPermissions(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        weatherView = f(R.id.weatherCard);


        weatherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (Integer.parseInt(new SimpleDateFormat("HH").format(new Date()))) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        switch (card) {
                            case 0:
                                weatherView.setMyType(WeatherView.Type.rainingNight);
                                break;
                            case 1:
                                weatherView.setMyType(WeatherView.Type.snowNight);
                                break;
                            case 2:
                                weatherView.setMyType(WeatherView.Type.night);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:

                        switch (card) {
                            case 0:
                                weatherView.setMyType(WeatherView.Type.raining);
                                break;
                            case 1:
                                weatherView.setMyType(WeatherView.Type.snow);
                                break;
                            case 2:
                                weatherView.setMyType(WeatherView.Type.sunday);
                                break;
                            default:
                                break;
                        }
                        break;

                    default:
                        break;

                }


                card++;
                if (card == 3) {
                    card = 0;
                }
            }
        });

        if(isNetworkConnected(this)){
            getRxLocation();
        }else {
            ToastUtil.shortToast("网络不保熟啊你这",getApplicationContext());
        }



    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    @SuppressLint("CheckResult")
    public void getRxLocation() {

        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(grant -> {
                    if (grant) {
                        initLocation();
                    }
                });
    }


    public void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setNeedNewVersionRgc(true);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.start();
    }


    public class ssLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(com.baidu.location.BDLocation bdLocation) {
            lLoaction = bdLocation.getDistrict();
//            getAddress();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://geoapi.qweather.com/v2/city/lookup?location=" + lLoaction + "&key=ff6f13d33aa841779574dabda82dc191").build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if(e instanceof SocketTimeoutException && SERVER_LOAD_TIMES < MAX_LOAD_TIMES){
                        SERVER_LOAD_TIMES++;
                        client.newCall(call.request()).enqueue(this);
                    }else{
                        ToastUtil.shortToast("你这网络它保熟🐎", getApplicationContext());
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    AddressBean addressId = null;
                    if (response.isSuccessful()) {
                        addressId = new Gson().fromJson(response.body().string(), AddressBean.class);
                        tv_city = f(R.id.tv_city);
                        tv_city.setText(addressId.getLocation().get(0).getAdm2() + "·" + addressId.getLocation().get(0).getName());
                    }

                    nowWeather(addressId.getLocation().get(0).getId());
                    getHourlyWeather(addressId.getLocation().get(0).getId());
                    getDailyWeather(addressId.getLocation().get(0).getId());
                    getWeatherAdvice(addressId.getLocation().get(0).getId());

                    locationClient.stop();
                }
            });


        }
    }


    public void nowWeather(String location) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://devapi.qweather.com/v7/weather/now?location=" + location + "&key=ff6f13d33aa841779574dabda82dc191").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException && SERVER_LOAD_TIMES < MAX_LOAD_TIMES){
                    SERVER_LOAD_TIMES++;
                    okHttpClient.newCall(call.request()).enqueue(this);
                }else{
                    ToastUtil.shortToast("你这网络它保熟🐎", getApplicationContext());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    TodayWeather todayWeather = new Gson().fromJson(response.body().string(), TodayWeather.class);
                    tv_temp = f(R.id.tv_temp);
                    tv_temp.setText(todayWeather.getNow().getTemp());
                    tv_date = f(R.id.tv_updateTime);
                    tv_date.setText(todayWeather.getNow().getObsTime().substring(11, 16) + "更新");

                    tv_uv = f(R.id.tv_uv_status);
                    tv_body_temp = f(R.id.tv_body_temp_status);
                    tv_rain_count = f(R.id.tv_rain_count_status);
                    tv_humidity = f(R.id.tv_humidity_status);
                    tv_atmos = f(R.id.tv_atmos_status);
                    tv_sight = f(R.id.tv_sight_status);

                    tv_uv.setText(todayWeather.getNow().getCloud() + "%");
                    tv_body_temp.setText(todayWeather.getNow().getDew() + "°");
                    tv_rain_count.setText(todayWeather.getNow().getPrecip() + "mm");
                    tv_humidity.setText(todayWeather.getNow().getHumidity() + "%");
                    tv_atmos.setText(todayWeather.getNow().getPressure() + "hPa");
                    tv_sight.setText(todayWeather.getNow().getVis() + "KM");
                    iv_now_status = f(R.id.tv_now_Status);
                    iv_now_status.setImageResource(ResourceUtil.getIconResource(todayWeather.getNow().getIcon()));

                    runOnUiThread(() -> {
                        switch (Integer.parseInt(new SimpleDateFormat("HH").format(new Date()))) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23:
                                switch (todayWeather.getNow().getText()) {
                                    case "晴":
                                    case "晴间多云":
                                    case "多云":
                                    case "少云":
                                    case "阴":
                                    case "雾":
                                    case "薄雾":
                                    case "霾":
                                    case "扬沙":
                                    case "浮尘":
                                    case "沙尘暴":
                                    case "强沙尘暴":
                                    case "浓雾":
                                    case "强浓雾":
                                    case "中度霾":
                                    case "重度霾":
                                    case "大雾":
                                    case "特强浓雾":
                                    case "新月":
                                    case "蛾眉月":
                                    case "上弦月":
                                    case "盈凸月":
                                    case "满月":
                                    case "亏凸月":
                                    case "下弦月":
                                    case "残月":
                                        weatherView.setMyType(WeatherView.Type.night);
                                        break;

                                    case "阵雨":
                                    case "强阵雨":
                                    case "雷阵雨":
                                    case "强雷阵雨":
                                    case "雷阵雨伴有冰雹":
                                    case "小雨":
                                    case "中雨":
                                    case "大雨":
                                    case "极端降雨":
                                    case "细雨":
                                    case "毛毛雨":
                                    case "暴雨":
                                    case "大暴雨":
                                    case "特大暴雨":
                                    case "冻雨":
                                    case "小到中雨":
                                    case "中到大雨":
                                    case "大到暴雨":
                                    case "暴雨到大暴雨":
                                    case "大暴雨到特大暴雨":
                                    case "雨":
                                        weatherView.setMyType(WeatherView.Type.rainingNight);
                                        break;


                                    case "雪":
                                    case "小雪":
                                    case "中雪":
                                    case "大雪":
                                    case "暴雪":
                                    case "雨夹雪":
                                    case "雨雪天气":
                                    case "阵雪":
                                    case "小到中雪":
                                    case "中到大雪":
                                    case "大到暴雪":
                                        weatherView.setMyType(WeatherView.Type.snowNight);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                            case 17:
                                switch (todayWeather.getNow().getText()) {
                                    case "晴":
                                    case "晴间多云":
                                    case "多云":
                                    case "少云":
                                    case "阴":
                                    case "雾":
                                    case "薄雾":
                                    case "霾":
                                    case "扬沙":
                                    case "浮尘":
                                    case "沙尘暴":
                                    case "强沙尘暴":
                                    case "浓雾":
                                    case "强浓雾":
                                    case "中度霾":
                                    case "重度霾":
                                    case "大雾":
                                    case "特强浓雾":
                                        weatherView.setMyType(WeatherView.Type.sunday);
                                        break;

                                    case "阵雨":
                                    case "强阵雨":
                                    case "雷阵雨":
                                    case "强雷阵雨":
                                    case "雷阵雨伴有冰雹":
                                    case "小雨":
                                    case "中雨":
                                    case "大雨":
                                    case "极端降雨":
                                    case "细雨":
                                    case "毛毛雨":
                                    case "暴雨":
                                    case "大暴雨":
                                    case "特大暴雨":
                                    case "冻雨":
                                    case "小到中雨":
                                    case "中到大雨":
                                    case "大到暴雨":
                                    case "暴雨到大暴雨":
                                    case "大暴雨到特大暴雨":
                                    case "雨":
                                        weatherView.setMyType(WeatherView.Type.raining);
                                        break;


                                    case "雪":
                                    case "小雪":
                                    case "中雪":
                                    case "大雪":
                                    case "暴雪":
                                    case "雨夹雪":
                                    case "雨雪天气":
                                    case "阵雪":
                                    case "小到中雪":
                                    case "中到大雪":
                                    case "大到暴雪":
                                        weatherView.setMyType(WeatherView.Type.snow);
                                        break;
                                    case "新月":
                                    case "蛾眉月":
                                    case "上弦月":
                                    case "盈凸月":
                                    case "满月":
                                    case "亏凸月":
                                    case "下弦月":
                                    case "残月":
                                        weatherView.setMyType(WeatherView.Type.night);
                                        break;
                                    default:
                                        break;
                                }

                                break;

                            default:
                                break;

                        }


                    });


                }
            }
        });
    }

    public void getHourlyWeather(String location) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(new Request.Builder()
                .url("https://devapi.qweather.com/v7/weather/24h?location=" + location + "&key=ff6f13d33aa841779574dabda82dc191")
                .build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException && SERVER_LOAD_TIMES < MAX_LOAD_TIMES){
                    SERVER_LOAD_TIMES++;
                    okHttpClient.newCall(call.request()).enqueue(this);
                }else{
                    ToastUtil.shortToast("获取小时天气失败！", getApplicationContext());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    HourlyWeatherBean hourlyWeatherBean = new Gson().fromJson(response.body().string(), HourlyWeatherBean.class);

                    runOnUiThread(() -> {
                        rlv = f(R.id.hourly_weather);
                        data = new ArrayList<>();
                        data.clear();
                        data.addAll(hourlyWeatherBean.getHourly());
                        HourlyWeatherAdapter adapter = new HourlyWeatherAdapter(data);
                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        rlv.setLayoutManager(manager);
                        rlv.setAdapter(adapter);

                    });

                }
            }
        });

    }

    public void getDailyWeather(String location) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(new Request.Builder()
                .url("https://devapi.qweather.com/v7/weather/7d?location=" + location + "&key=ff6f13d33aa841779574dabda82dc191")
                .build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException && SERVER_LOAD_TIMES < MAX_LOAD_TIMES){
                    SERVER_LOAD_TIMES++;
                    okHttpClient.newCall(call.request()).enqueue(this);
                }else{
                    ToastUtil.shortToast("你这网络它保熟🐎", getApplicationContext());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SevenDayWeather sevenDayWeather = new Gson().fromJson(response.body().string(), SevenDayWeather.class);

                    runOnUiThread(() -> {
                        rlv_daily_weather = f(R.id.rlv_daily_Weather);
                        dailyData = new ArrayList<>();
                        dailyData.addAll(sevenDayWeather.getDaily());

                        DailyWeatherAdapter adapter = new DailyWeatherAdapter(dailyData);
                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);

                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        rlv_daily_weather.setLayoutManager(manager);
                        rlv_daily_weather.setAdapter(adapter);


                        tv_max_min = f(R.id.tv_max_min);
                        tv_max_min.setText(dailyData.get(0).getTempMin() + "°/" + dailyData.get(0).getTempMax() + "°");

                    });
                }
            }
        });

    }

    public void getWeatherAdvice(String location) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(new Request.Builder().url("https://devapi.qweather.com/v7/indices/1d?type=0&location=" + location + "&key=ff6f13d33aa841779574dabda82dc191").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e instanceof SocketTimeoutException && SERVER_LOAD_TIMES < MAX_LOAD_TIMES){
                    SERVER_LOAD_TIMES++;
                    okHttpClient.newCall(call.request()).enqueue(this);
                }else{
                    ToastUtil.shortToast("生活指数请求失败!", getApplicationContext());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    WeatherAdviceBean weatherAdviceBean = new Gson().fromJson(response.body().string(), WeatherAdviceBean.class);

                    runOnUiThread(() -> {
                        adviceData = new ArrayList<>();
                        adviceData.addAll(weatherAdviceBean.getDaily());
                        rlv_type = f(R.id.rlv_type);

                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        rlv_type.setLayoutManager(manager);
                        rlv_type.setAdapter(new WeatherAdviceAdapter(adviceData));

                    });


                }
            }
        });
    }


    //优化组件映射
    private <T extends View> T f(int viewId) {
        return (T) findViewById(viewId);
    }


}