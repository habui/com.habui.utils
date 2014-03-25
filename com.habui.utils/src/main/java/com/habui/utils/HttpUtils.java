/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.utils;

import com.habui.log.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author habui
 */
public class HttpUtils {
    private static final Logger log = LogUtils.getLogger(HttpUtils.class);
    
    public static final int POST = 2;
    public static final int GET = 1;
    
    public static String makeRequest(String requestUrl, Map<String, String> params, int kind) {
        String json = "";
        try {
            if (kind == HttpUtils.GET) {
                String query = createQuery(params);
                requestUrl = requestUrl + "?" + query;
            }
            
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            conn.setRequestMethod("GET");
            if (kind == HttpUtils.POST) {  // POST method
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String query = createQuery(params);
                conn.setRequestProperty("Content-length",String.valueOf (query.length())); 

                OutputStream os = conn.getOutputStream();
                os.write(query.getBytes());
                os.flush();
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                json = bufferReader.readLine(); 
            } else {
                log.error(String.format("Call Restful (%s) : %s", requestUrl, conn.getResponseCode()));
            }
            conn.disconnect();
            
        }
        catch (IOException ex) {
            log.error(LogUtils.stackTrace(ex));
        }
        return json;
    }
    
    private static String createQuery(Map<String, String> params) {
        String result = "";
        try {
            Iterator it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                String key = pairs.getKey().toString();
                String value = URLEncoder.encode(pairs.getValue().toString(), "UTF-8");
                if (value != null && value.isEmpty() == false) {
                    if (result.isEmpty() == false)
                        result += "&";
                    result += String.format("%s=%s",key, value);
                }
            }
        }
        catch (Exception ex) {
            log.error(LogUtils.stackTrace(ex));
        }
        return result;
    }
}
