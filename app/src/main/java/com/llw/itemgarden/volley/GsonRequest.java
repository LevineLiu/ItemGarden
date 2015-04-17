package com.llw.itemgarden.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/3/24.
 */
public class GsonRequest<T> extends Request<T>{
    private final Response.Listener<T> mListener;
    private Class<T> mClass;
    private Map<String,Object> mRequestMap;
    private Gson mGson;
    private String mRequestBody;

    public GsonRequest(int method, String url, Class<T> cls, Map<String,Object> request, Response.Listener<T> listener,
                       Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mRequestMap = request;
        //mRequestBody = request;
        this.mListener = listener;
        this.mClass = cls;
        mGson = new Gson();
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try{
            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            T parseGson = mGson.fromJson(jsonString, mClass);
            return Response.success(parseGson, HttpHeaderParser.parseCacheHeaders(networkResponse));
        }catch (UnsupportedEncodingException e){
            return Response.error(new ParseError(e));
        }catch (JsonSyntaxException je){
            return  Response.error(new ParseError(je));
        }
    }

//    @Override
//    protected Map<String, String> getParams() throws AuthFailureError {
//        return mRequestMap;
//    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mRequestMap != null && mRequestMap.size() > 0?this.encodeParameters(mRequestMap, this.getParamsEncoding()):null;
    }

    private byte[] encodeParameters(Map<String, Object> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();

        try {
            Iterator var5 = params.entrySet().iterator();

            while(var5.hasNext()) {
                java.util.Map.Entry uee = (java.util.Map.Entry)var5.next();
                encodedParams.append(URLEncoder.encode((String) uee.getKey(), paramsEncoding));
                encodedParams.append('=');
                Object value = uee.getValue();
                if(value instanceof String)
                    encodedParams.append(URLEncoder.encode((String)value, paramsEncoding));
                else if(value instanceof Long){
                    encodedParams.append((long)value);
                }
                encodedParams.append('&');
            }

            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException var6) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, var6);
        }
    }
}
