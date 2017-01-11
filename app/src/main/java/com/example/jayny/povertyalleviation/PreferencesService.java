package com.example.jayny.povertyalleviation;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jayny on 2017/1/11.
 */
public class PreferencesService {
    private Context context;
    //构造方法中传入上下文对象
    public PreferencesService(Context context) {
        super();
        this.context = context;
    }

    /**
     * 保存参数
     * @param name 姓名
     * @param pwd 年龄
     */
    public void save(String name,String pwd){
        SharedPreferences sharedPreferences = context.getSharedPreferences("itcastPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", name);
        editor.putString("pwd", pwd);  //目前是保存在内存中，还没有保存到文件中
        editor.commit();    //数据提交到xml文件中
    }

    /**
     * 获取各项配置参数
     * @return params
     */
    public Map<String,String> getPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("itcastPreference", Context.MODE_PRIVATE);
        Map<String,String> params = new HashMap<String, String>();
        params.put("username", sharedPreferences.getString("username", ""));
        params.put("age", String.valueOf(sharedPreferences.getInt("age", 0)));
        return params;
    }
}
