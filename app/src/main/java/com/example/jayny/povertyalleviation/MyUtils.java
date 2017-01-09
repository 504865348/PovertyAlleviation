package com.example.jayny.povertyalleviation;

import android.util.Log;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jayny on 2016/12/6.
 */
public class MyUtils {
    public static String postGetJson(String url, String method , Map<String,String> content) {
        try {
            URL mUrl = new URL(url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
//            mHttpURLConnection.setConnectTimeout(10000);
//            mHttpURLConnection.setReadTimeout(10000);
            mHttpURLConnection.setRequestMethod(method);
            mHttpURLConnection.setRequestProperty("Accept", "application/json");
            if(method.equals("POST")){
                mHttpURLConnection.setRequestProperty("Content-Type", "application/json");
                mHttpURLConnection.setDoInput(true);
                mHttpURLConnection.setDoOutput(true);
                mHttpURLConnection.setUseCaches(false);
                mHttpURLConnection.setInstanceFollowRedirects(true);
            }
            mHttpURLConnection.connect();
            if(method.equals("POST")){
                DataOutputStream dos = new DataOutputStream(mHttpURLConnection.getOutputStream());
                dos.write(getJSON(content).toString().getBytes("UTF-8"));
                dos.flush();
                dos.close();
            }
            int respondCode = mHttpURLConnection.getResponseCode();
            Log.d("respondCode","respondCode="+respondCode );
            String type = mHttpURLConnection.getContentType();
            Log.d("type", "type="+type);
            String encoding = mHttpURLConnection.getContentEncoding();
            Log.d("encoding", "encoding="+encoding);
            int length = mHttpURLConnection.getContentLength();
            Log.d("length", "length=" + length);
//            String key = mHttpURLConnection.getHeaderField();
//            Log.d("key", "key="+key);
            Map<String, List<String>> map = mHttpURLConnection.getHeaderFields();
            if (respondCode == 200) {
                InputStream is = mHttpURLConnection.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                is.close();
                message.close();
                String msg = new String(message.toByteArray());
                System.out.println(msg);
                return msg;
            }else {
                return "error";
            }
        }catch(Exception e){
            Log.e("exception", e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

    public static JSONObject getJSON(Map<String,String> map) throws Exception{
        Iterator iter = map.entrySet().iterator();
        JSONObject holder = new JSONObject();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();
            String key = (String) pairs.getKey();
            String v = (String) pairs.getValue();
            holder.put(key, v);
        }
        return holder;
    }
}
