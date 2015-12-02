package com.example.administrator.zuber.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/12/1.
 */
public class RailwayBean {
    private String line;
    private String classname;
    private List<String> station;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<String> getStation() {
        return station;
    }

    public void setStation(List<String> station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "RailwayBean{" +
                "line='" + line + '\'' +
                ", classname='" + classname + '\'' +
                ", station=" + station +
                '}';
    }
}
