package com.example.administrator.zuber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zuber.adapters.RailwayGridAdapter;
import com.example.administrator.zuber.bean.CityBean;
import com.example.administrator.zuber.bean.RailwayBean;
import com.example.administrator.zuber.bean.UserSelectBean;
import com.example.administrator.zuber.utils.JsonParseUtil;
import com.example.administrator.zuber.utils.LocalDataUtil;
import com.example.administrator.zuber.view.SeekBarPressure;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @ViewInject(id = R.id.txt_title_sex_man, click = "onclickSex")
    TextView txt_title_sex_man;
    @ViewInject(id = R.id.txt_title_sex_woman, click = "onclickSex")
    TextView txt_title_sex_woman;
    @ViewInject(id = R.id.txt_title_sex_unlimit, click = "onclickSex")
    TextView txt_title_sex_unlimit;
    TextView txt_title_sex[];
    //sex end

    @ViewInject(id = R.id.txt_title_location_recommendation, click = "onclickLocation")
    TextView txt_title_location_recommendation;
    @ViewInject(id = R.id.txt_title_location_nearby, click = "onclickLocation")
    TextView txt_title_location_nearby;
    @ViewInject(id = R.id.txt_title_location_railway, click = "onclickLocation")
    TextView txt_title_location_railway;
    @ViewInject(id = R.id.txt_title_location_unlimit, click = "onclickLocation")
    TextView txt_title_location_unlimit;
    TextView txt_title_location[];

    @ViewInject(id = R.id.ll_railway)
    LinearLayout ll_railway;
    @ViewInject(id = R.id.gv_location)
    GridView gv_location;
    @ViewInject(id = R.id.ll_station)
    LinearLayout ll_station;
    @ViewInject(id = R.id.rl_station)
    RelativeLayout rl_station;
    //location end

    @ViewInject(id = R.id.txt_title_time_this_month, click = "onclickTime")
    TextView txt_title_time_this_month;
    @ViewInject(id = R.id.txt_title_time_second_month, click = "onclickTime")
    TextView txt_title_time_second_month;
    @ViewInject(id = R.id.txt_title_time_unlimit, click = "onclickTime")
    TextView txt_title_time_unlimit;
    TextView txt_title_time[];
    //time end
    @ViewInject(id = R.id.sp_seekbar)
    SeekBarPressure sp_seekbar;
    @ViewInject(id = R.id.txt_rent_title)
    TextView txt_rent_title;
    //rent end
    @ViewInject(id = R.id.txt_title_city, click = "onChooseCity")
    TextView txt_title_city;
    @ViewInject(id = R.id.rl_save, click = "onSave")
    RelativeLayout rl_save;
    //title
    private UserSelectBean userSelectBean = new UserSelectBean();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //init
        FinalActivity.initInjectedView(this);
        init();
    }

    public void onSave(View v) {
        Toast.makeText(this,"保存信息:"+userSelectBean.toString(),Toast.LENGTH_LONG).show();
        //在这里对数据进一步处理
    }
    public void onclickTime(View v) {
        setSelect("time", v);
    }

    public void onclickLocation(View v) {
        setSelect("location", v);
        switch (v.getId()) {
            case R.id.txt_title_location_railway:
                ll_railway.setVisibility(View.VISIBLE);
                if (gv_location.getAdapter() == null)
                    setLocationData();

                break;

            default:
                ll_railway.setVisibility(View.GONE);
        }
    }

    public void setLocationData() {
        String city = txt_title_city.getText().toString();
        LocalDataUtil dataManage = new LocalDataUtil();
        CityBean cityBean = null;
        if (city.indexOf("北京") >= 0) {
            String json = dataManage.getFromAssets(this, "bj_railway.json");
            cityBean = new JsonParseUtil().parseCity(json);
        } else if (city.indexOf("广州") >= 0) {
            String json = dataManage.getFromAssets(this, "gz_railway.json");
            cityBean = new JsonParseUtil().parseCity(json);
        } else if (city.indexOf("深圳") >= 0) {
            String json = dataManage.getFromAssets(this, "sz_railway.json");
            cityBean = new JsonParseUtil().parseCity(json);
        } else {//默认上海
            String json = dataManage.getFromAssets(this, "sh_railway.json");
            cityBean = new JsonParseUtil().parseCity(json);
            txt_title_city.setText("上海");
        }
        RailwayGridAdapter railwayGridAdapter = (RailwayGridAdapter)gv_location.getAdapter();
        if (railwayGridAdapter == null ) {
            railwayGridAdapter = new RailwayGridAdapter(
                    this, R.layout.grid_railway_item,
                    cityBean);
            gv_location.setAdapter(railwayGridAdapter);
            return;
        }
        railwayGridAdapter.setCity(cityBean);
        railwayGridAdapter.notifyDataSetChanged();
    }

    public void onclickSex(View v) {
        setSelect("sex", v);
    }

    //设置标题已选择
    public void setSelect(String category, View v) {
        ((TextView) v).setTextColor(this.getResources().getColor(R.color.app_txt_select));

        if (category.equalsIgnoreCase("sex")) {
            setUnselect(txt_title_sex, v);
            userSelectBean.setSex(((TextView) v).getText().toString());
        } else if (category.equalsIgnoreCase("location")) {
            setUnselect(txt_title_location, v);
        } else if (category.equalsIgnoreCase("time")) {
            setUnselect(txt_title_time, v);
            userSelectBean.setTime(((TextView) v).getText().toString());
        }


    }

    //设置标题未选择
    public void setUnselect(TextView txt_titles[], View v) {
        for (int i = 0; i < txt_titles.length; i++) {
            if (txt_titles[i].getId() != v.getId()) {
                txt_titles[i].setTextColor(this.getResources().getColor(R.color.app_txt_unselect));
            }
        }

    }

    public void onChooseCity(View v) {
        startActivityForResult(new Intent(this, ChooseCityActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                userSelectBean.getStation().clear();
                Bundle b = data.getExtras();
                String city = b.getString("city");
                txt_title_city.setText(city);
                setLocationData();
                rl_station.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void init() {
        //sex
        txt_title_sex = new TextView[]{
                txt_title_sex_man, txt_title_sex_woman, txt_title_sex_unlimit};
        //location
        txt_title_location = new TextView[]{
                txt_title_location_recommendation,
                txt_title_location_nearby,
                txt_title_location_railway,
                txt_title_location_unlimit};

        //time
        txt_title_time = new TextView[]{txt_title_time_this_month, txt_title_time_second_month, txt_title_time_unlimit};

        //预选初始化
        onclickSex(txt_title_sex_unlimit);
        onclickLocation(txt_title_location_unlimit);
        onclickTime(txt_title_time_unlimit);

        gv_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (rl_station.getVisibility() == View.GONE)
                    rl_station.setVisibility(View.VISIBLE);

                RailwayGridAdapter gridAdapter = (RailwayGridAdapter) gv_location.getAdapter();
                TextView txt = (TextView) view.findViewById(R.id.txt);
                //当前点击
                if (gridAdapter.getBef_txt() != null
                        && gridAdapter.getBef_txt() == txt) {
                    return;
                }

                //判断是否取消选择
                if (gridAdapter.getBef_txt() != null && gridAdapter.getBef_railwayBean() != null) {
                    RailwayBean railwayBean = gridAdapter.getBef_railwayBean();
                    boolean flag = true;
                    for (int i = 0; i <userSelectBean.getStation().size(); i++) {
                        if (railwayBean.getStation().lastIndexOf(userSelectBean.getStation().get(i)) > 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        gridAdapter.setUnselect(gridAdapter.getBef_txt());
                    }

                }

                gridAdapter.setSelect(txt);
                gridAdapter.setBef_railwayBean(gridAdapter.getCity().getRailways().get(position));
                //show select   end

                ll_station.removeAllViews();
                CityBean cityBean = gridAdapter.getCity();

                RailwayBean railwayBean = cityBean.getRailways().get(position);
                List<String> stations = userSelectBean.getStation();
                for (int i = 0; i < railwayBean.getStation().size(); i++) {
                    View v = LayoutInflater.from(MainActivity.this).inflate(
                            R.layout.layout_station_item,
                            ll_station, false);

                    if (i % 2 == 1) {
                        ((TextView) v.findViewById(R.id.txt_bottom))
                                .setText(railwayBean.getStation().get(i));
                    } else {
                        ((TextView) v.findViewById(R.id.txt_top))
                                .setText(railwayBean.getStation().get(i));
                    }


                    if (stations.lastIndexOf(railwayBean.getStation().get(i)) > 0) {
                        v.findViewById(R.id.v_station).setBackgroundColor(
                                MainActivity.this.getResources().getColor(R.color.app_txt_select));
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String station_bottom = (String) ((TextView) v.findViewById(R.id.txt_bottom)).getText();
                            String station_top = (String) ((TextView) v.findViewById(R.id.txt_top)).getText();
                            String station = "";

                            if (!station_top.trim().equalsIgnoreCase(""))
                                station = station_top.trim();
                            if (!station_bottom.trim().equalsIgnoreCase(""))
                                station = station_bottom.trim();

                            if (userSelectBean.getStation().lastIndexOf(station) > 0) {//删除Station
                                v.findViewById(R.id.v_station).setBackgroundColor(
                                        MainActivity.this.getResources().getColor(R.color.app_background_white));
                                userSelectBean.getStation().remove(station);
                            } else {//添加Station
                                v.findViewById(R.id.v_station).setBackgroundColor(
                                        MainActivity.this.getResources().getColor(R.color.app_txt_select));
                                userSelectBean.getStation().add(station);
                            }
                        }
                    });

                    ll_station.addView(v);
                }

            }
        });

        sp_seekbar.setOnSeekBarChangeListener(new SeekBarPressure.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBarPressure seekBar,
                                          double progressLow, double progressHigh, int mprogressLow,
                                          int mprogressHigh, double max, double min) {
                String [] rent = userSelectBean.getRent();
                rent[0] = mprogressLow*500+"";
                rent[1] = mprogressHigh*500+"";
                if (mprogressHigh == 8)
                    rent[1] = "不限";
                String title_rent = "￥" +
                        rent[0] +
                        "-";
                if (mprogressHigh != 8)
                    title_rent += "￥";
                title_rent += rent[1] ;

                txt_rent_title.setText(title_rent);
            }
        });
        sp_seekbar.setProgressLowInt(2);
        sp_seekbar.setProgressHighInt(4);

    }


    public void onBack(View v) {
        finish();
    }

    public UserSelectBean getUserSelectBean() {
        return userSelectBean;
    }
}
