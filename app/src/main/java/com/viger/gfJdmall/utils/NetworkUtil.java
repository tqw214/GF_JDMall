package com.viger.gfJdmall.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/8.
 */

public class NetworkUtil {

    private static final int TIME_OUT =  15 * 1000;

    public static String doGet(String urlPath, HashMap<String, String> params){
        urlPath = urlPath + parseData(Request.GET, params);
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIME_OUT);
            conn.setReadTimeout(TIME_OUT);
            if(conn.getResponseCode() == 200) {
                InputStream input = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                StringBuilder builder = new StringBuilder();
                String line;
                byte[] buffer = new byte[1024];
                while((line=br.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost(String urlPath, HashMap<String, String> params){
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIME_OUT);
            conn.setReadTimeout(TIME_OUT);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream output = conn.getOutputStream();
            String sendData = parseData(Request.POST, params);
            output.write(sendData.getBytes());
            output.flush();

            if(conn.getResponseCode() == 200) {
                InputStream input = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(input);
                BufferedReader br = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();
                byte[] buffer = new byte[1024];
                while((line=br.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseData(Request req, HashMap<String, String> params) {
        if(params != null) {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            Iterator<Map.Entry<String, String>> iterator1 = entries.iterator();
            if(req == Request.GET) {
                builder.append("?");
            }
            while(iterator1.hasNext()) {
                Map.Entry<String, String> next = iterator1.next();
                String key = next.getKey();
                String value = next.getValue();
                builder.append(key + "=" + value + "&");
            }
            String result = builder.toString();
            return result.substring(0, result.length()-1);
        }
        return "";
    }

}

enum Request {
    GET,POST;
}
