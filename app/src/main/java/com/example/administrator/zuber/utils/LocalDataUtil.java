package com.example.administrator.zuber.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/12/1.
 */
public class LocalDataUtil {

    public String getFromAssets(Context context,String fileName)  {
        String result = "";
        InputStream in = null;
        try {
            in = context.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[]  buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer,"utf-8");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        
        return result;
    }

}
