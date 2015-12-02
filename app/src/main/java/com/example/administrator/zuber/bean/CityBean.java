package com.example.administrator.zuber.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/12/1.
 */
public class CityBean {
    private List<String> region;
    private List<RailwayBean> railways;

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public List<RailwayBean> getRailways() {
        return railways;
    }

    public void setRailways(List<RailwayBean> railways) {
        this.railways = railways;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "region=" + region +
                ", railways=" + railways +
                '}';
    }
}
