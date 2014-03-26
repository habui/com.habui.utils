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
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
                bufferReader.close();
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
    
    public static String makeRequestOverSSL(String requestUrl, Map<String, String> params, int kind) {
        String json = "";
        try {
            TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] certificate, String authType) {
                    // in secure
                    return true;
                }
            };
            SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy, 
            SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 8443, sf));
            ClientConnectionManager ccm = new PoolingClientConnectionManager(registry);
 
            DefaultHttpClient httpClient = new DefaultHttpClient(ccm);
            
            String urlOverHttps = requestUrl;
            HttpUriRequest requestMethod = null;
            if (kind == HttpUtils.GET) {
                String query = createQuery(params);
                urlOverHttps += "?" + query;
                requestMethod = new HttpGet(urlOverHttps);
            } else { // POST
                HttpPost postRequest = new HttpPost(urlOverHttps);
                
                List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
                Iterator<String> it = params.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    urlParameters.add(new BasicNameValuePair(key, params.get(key)));
                }
                postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

                requestMethod = postRequest;
            }
            
            HttpResponse response = httpClient.execute(requestMethod);
            if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                json = EntityUtils.toString(response.getEntity());
            } else {
                log.error(String.format("Call Restful (%s) : %s", requestUrl, response.getStatusLine().getStatusCode()));
            }
            httpClient.close();
        }
        catch (IOException ex) {
            log.error(LogUtils.stackTrace(ex));
        } catch (KeyManagementException ex) {
            log.error(LogUtils.stackTrace(ex));
        } catch (KeyStoreException ex) {
            log.error(LogUtils.stackTrace(ex));
        } catch (NoSuchAlgorithmException ex) {
            log.error(LogUtils.stackTrace(ex));
        } catch (UnrecoverableKeyException ex) {
            log.error(LogUtils.stackTrace(ex));
        } catch (ParseException ex) {
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
