package com.llw.itemgarden.volley;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author Created by liulewen on 2015/3/13.
 */
public class VolleyJsonObjectRequest extends JsonObjectRequest{

    /**
     * @param params 为null时候使用Get,否则使用的是Post
     */
    public VolleyJsonObjectRequest(String url, Map<String, String>params, Response.Listener<JSONObject> successListener,
                                   Response.ErrorListener errorListener){
        super(url, params == null ? null : new JSONObject(params), successListener, errorListener);
    }
}
