package com.example.yangdianwen.news.HttpUtils;

import com.example.yangdianwen.news.Bean.GsonBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**这是一个解析新闻数据的封装类，包含了解析方法
 * Created by yangdianwen on 2016/6/2.
 */
public class ParseJson {
    private static final String TAG = "ParseJson";
    //用Gson解析json
    public String parseJsonstr(String jsonStr) {
        Gson gson = new Gson();
         GsonBean json = gson.fromJson(jsonStr, GsonBean.class);
        return gson.toJson(json);
    }
    //解析json
    public String dataJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Object message = jsonObject.get("message");
            Object status = jsonObject.get("status");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i <jsonArray.length(); i++) {
                JSONArray subgrp = (JSONArray) jsonArray.getJSONObject(i).get("subgrp");
                int gid = (int) jsonArray.getJSONObject(i).get("gid");
                String group = (String) jsonArray.getJSONObject(i).get("group");
                for (int j= 0; j <subgrp.length(); j++) {
                    String subgroup = (String) subgrp.getJSONObject(j).get("subgroup");
                    int subid = (int) subgrp.getJSONObject(j).get("subid");
                }
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return json;
    }
}
