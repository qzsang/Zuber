package com.example.administrator.zuber.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.zuber.R;
import com.example.administrator.zuber.bean.CityBean;
import com.example.administrator.zuber.bean.RailwayBean;

/**
 * Created by Administrator on 2015/12/1.
 */
public class RailwayGridAdapter extends ArrayAdapter{

    private int resource;
    private TextView bef_txt = null;
    CityBean city;
    private RailwayBean bef_railwayBean = null;
    public RailwayGridAdapter(Context context, int resource, CityBean city) {
        super(context, resource);
        this.resource = resource;
        this.city = city;
    }

    public RailwayBean getBef_railwayBean() {
        return bef_railwayBean;
    }

    public void setBef_railwayBean(RailwayBean bef_railwayBean) {
        this.bef_railwayBean = bef_railwayBean;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public CityBean getCity() {
        return city;
    }

    public TextView getBef_txt() {
        return bef_txt;
    }

    @Override
    public int getCount() {
        return city.getRailways().size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(getContext(),resource,null);

        TextView txt = (TextView)convertView.findViewById(R.id.txt);
        txt.setText(city.getRailways().get(position).getLine());
        //选择默认
        setUnselect(txt);

        return convertView;
    }

    public void setSelect(TextView txt) {
        txt.setBackgroundColor(getContext().getResources().getColor(R.color.app_txt_select));
        txt.setTextColor(getContext().getResources().getColor(R.color.app_background_white));
        bef_txt = txt;

    }

    public void setUnselect(TextView txt) {
        txt.setBackgroundColor(Color.WHITE);
        txt.setTextColor(Color.BLACK);
    }

}
