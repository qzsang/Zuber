package com.example.administrator.zuber.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/12/1.
 * 保存用户筛选信息
 */
public class UserSelectBean {
    private String sex;
    private String []rent = new String[2];
    private List<String> station;
    private String time;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String[] getRent() {
        return rent;
    }

    public void setRent(String[] rent) {
        this.rent = rent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getStation() {
        if (station == null){
            station = new ArrayList<String>();
        }
        return station;
    }

    public void setStation(List<String> station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "UserSelectBean{" +
                "sex='" + sex + '\'' +
                ", rent=" + Arrays.toString(rent) +
                ", station=" + station +
                ", time='" + time + '\'' +
                '}';
    }
}
