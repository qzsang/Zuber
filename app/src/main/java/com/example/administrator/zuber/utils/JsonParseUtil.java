package com.example.administrator.zuber.utils;

import com.example.administrator.zuber.bean.CityBean;
import com.example.administrator.zuber.bean.RailwayBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/1.
 */
public class JsonParseUtil {

    public CityBean parseCity(String json) {
        CityBean cityBean = new CityBean();
        try {
            JSONObject cityJO = new JSONObject(json);

            JSONObject resultJO = cityJO.getJSONObject("result");
            //得到region
            JSONArray regionArrary = resultJO.getJSONArray("region");
            List region = new ArrayList<String>();
            for (int i = 0; i < regionArrary.length(); i++){
                region.add(regionArrary.get(i));
            }
            cityBean.setRegion(region);


            //得到railway
            List<RailwayBean> railways = new ArrayList<RailwayBean>();
            JSONArray railwayArray = resultJO.getJSONArray("railway");
            for (int i = 0 ;i < railwayArray.length(); i++) {
                JSONObject railwayJO = railwayArray.getJSONObject(i);
                RailwayBean railwayBean = new RailwayBean();
                railwayBean.setLine(railwayJO.getString("line"));
                railwayBean.setClassname(railwayJO.getString("classname"));
                JSONArray stationJo = railwayJO.getJSONArray("station");
                List station = new ArrayList<String>();
                for (int j = 0; j < stationJo.length(); j++){
                    station.add(stationJo.get(j));
                }
                railwayBean.setStation(station);
                railways.add(railwayBean);
            }
            cityBean.setRailways(railways);




        } catch (JSONException e) {

            e.printStackTrace();
        }


        return cityBean;
    }

}
