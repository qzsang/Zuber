package com.example.administrator.zuber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.example.administrator.zuber.app.LocationApplication;
import com.example.administrator.zuber.server.LocationService;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class ChooseCityActivity extends AppCompatActivity {

    @ViewInject(id = R.id.ll_gps)
    LinearLayout ll_gps;
    @ViewInject(id = R.id.lv_citys)
    ListView lv_citys;
    @ViewInject(id = R.id.txt_gps)
    TextView txt_gps;
    final String []citys = new String []{"北京","上海","广州","深圳"};
    private LocationService locationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        getSupportActionBar().hide();
        FinalActivity.initInjectedView(this);
        init();
    }

    public void init () {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,R.layout.list_city_item) {

            @Override
            public int getCount() {
                return citys.length;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = View.inflate(getContext(),R.layout.list_city_item,null);

                TextView txt_city = (TextView)convertView.findViewById(R.id.txt_city);
                txt_city.setText(citys[position]);

                return convertView;
            }
        };
        lv_citys.setAdapter(myAdapter);
        lv_citys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ChooseCityActivity.this.jumpPage(citys[position]);
            }
        });

        ll_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city != null)
                    jumpPage(city);
            }
        });

        //gps

        txt_gps.setMovementMethod(ScrollingMovementMethod.getInstance());

    }


    public void jumpPage(String city) {
        Intent intent = new Intent();
        intent.putExtra("city",city);
        setResult(RESULT_OK, intent);
        finish();
    }

    public String city = null;
    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        try {
            if (txt_gps != null) {
                txt_gps.setText(str);
                city = str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((LocationApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();
    }


    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);

                sb.append(location.getCity());
                logMsg(sb.toString());
            }
        }

    };

    public void onback(View v) {
        finish();
    }

}
