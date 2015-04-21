package com.llw.itemgarden.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import com.llw.itemgarden.model.ServiceResult;

/**
 * @author Created by liulewen on 2015/3/24.
 */
public class GsonRequest extends Request<ServiceResult>{
    private final Response.Listener<ServiceResult> mListener;
    private Map<String,Object> mRequestMap;
    private Gson mGson;
    private String mRequestBody;

    public GsonRequest(int method, String url, Map<String,Object> request, Response.Listener<ServiceResult> listener,
                       Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mRequestMap = request;
        //mRequestBody = request;
        this.mListener = listener;
        mGson = new Gson();
    }

    public GsonRequest(String url, Map<String,Object> request, Response.Listener<ServiceResult> listener,
                       Response.ErrorListener errorListener){
        this(Method.POST, url, request, listener, errorListener);
    }

    @Override
    protected void deliverResponse(ServiceResult response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<ServiceResult> parseNetworkResponse(NetworkResponse networkResponse) {
        try{
            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            ServiceResult parseGson = mGson.fromJson(jsonString, ServiceResult.class);
            return Response.success(parseGson, HttpHeaderParser.parseCacheHeaders(networkResponse));
        }catch (UnsupportedEncodingException e){
            return Response.error(new ParseError(e));
        }catch (JsonSyntaxException je){
            return  Response.error(new ParseError(je));
        }
    }


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
