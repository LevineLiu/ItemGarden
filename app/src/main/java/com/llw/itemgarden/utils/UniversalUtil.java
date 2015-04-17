package com.llw.itemgarden.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/15.
 */
public class UniversalUtil {

    public static void getRegion(Context context, ArrayList<String> provinceList, Map<String, ArrayList<String>> cityMap,
                                 Map<String, ArrayList<String>> districtMap){
        try{
            InputStream inputStream = context.getAssets().open("region.json");
            StringBuffer stringBuffer = new StringBuffer();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1){
                stringBuffer.append(new String(buffer, 0, len, "utf-8"));
            }
            inputStream.close();
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            JSONArray provinceArray = jsonObject.getJSONArray("province");
            for(int i=0; i<provinceArray.length(); i++){
                JSONObject jsonProvince = provinceArray.getJSONObject(i);
                String province = jsonProvince.getString("name");
                provinceList.add(province);
                JSONArray cityArray = jsonProvince.getJSONArray("city");
                ArrayList<String> cityList = new ArrayList<>();
                for(int j=0; j<cityArray.length(); j++){
                    JSONObject jsonCity = cityArray.getJSONObject(j);
                    String city = jsonCity.getString("name");
                    cityList.add(city);
                    JSONArray districtArray = jsonCity.getJSONArray("area");
                    ArrayList<String> districtList = new ArrayList<>();
                    for(int k=0; k<districtArray.length(); k++){
                        JSONObject jsonDistrict = districtArray.getJSONObject(k);
                        String district = jsonDistrict.getString("name");
                        districtList.add(district);
                    }
                    districtMap.put(city, districtList);
                }
                cityMap.put(province, cityList);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void getGoodsCategory(Context context, ArrayList<String> categoryList, Map<String, ArrayList<String>> subCategoryMap){
        try{
            InputStream inputStream = context.getAssets().open("goods_category.json");
            StringBuffer stringBuffer = new StringBuffer();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1){
                stringBuffer.append(new String(buffer, 0, len, "utf-8"));
            }
            inputStream.close();
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            JSONArray categoryArray = jsonObject.getJSONArray("category");
            for(int i=0; i<categoryArray.length(); i++){
                JSONObject categoryJson = categoryArray.getJSONObject(i);
                String category = categoryJson.getString("name");
                categoryList.add(category);
                JSONArray subCategoryArray = categoryJson.getJSONArray("category1");
                ArrayList<String> subCategoryList = new ArrayList<>();
                for(int j=0; j<subCategoryArray.length(); j++){
                    JSONObject subCategoryJson = subCategoryArray.getJSONObject(j);
                    String subCategory = subCategoryJson.getString("name");
                    subCategoryList.add(subCategory);
                }
                subCategoryMap.put(category, subCategoryList);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
