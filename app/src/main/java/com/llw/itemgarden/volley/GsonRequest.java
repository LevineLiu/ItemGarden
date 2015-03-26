package com.llw.itemgarden.volley;

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
import java.util.Map;

/**
 * @author Created by liulewen on 2015/3/24.
 */
public class GsonRequest<T> extends Request<T>{
    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/gson; charset=%s", PROTOCOL_CHARSET);

    private final Response.Listener<T> mListener;
    private Class<T> mClass;
    private Map<String,String> mRequestMap;
    private Gson mGson;
    public GsonRequest(int method, String url, Class<T> cls, Map<String, String> request, Response.Listener<T> listener,
                       Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mRequestMap = request;
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

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mRequestMap;
    }
//    @Override
//    public String getBodyContentType() {
//        return PROTOCOL_CONTENT_TYPE;
//    }
//
//    @Override
//    public byte[] getBody() throws AuthFailureError {
//        try {
//            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
//        } catch (UnsupportedEncodingException uee) {
//            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
//                    mRequestBody, PROTOCOL_CHARSET);
//            return null;
//        }
//    }
}
