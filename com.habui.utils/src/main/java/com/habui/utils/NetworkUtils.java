/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.utils;

import com.habui.log.LogUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author habns
 */
public class NetworkUtils {
    private static final Logger log = LogUtils.getLogger(NetworkUtils.class);
    
    public static String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }
    
    public static String ipAddressToBinaryString(String ipAddress) {
        try {
            String result = "";
            String[] arr = ipAddress.split("\\.");
            if (arr.length != 4) {
                return "";
            }
            for (int i = 0; i < arr.length; ++i) {
                int val = Integer.parseInt(arr[i], -1);
                if (val > 255 || val < 0) {
                    return "";
                }
                result += org.apache.commons.lang.StringUtils.leftPad(Integer.toBinaryString(val), 8, "0");
            }
            return result;
        } 
        catch (NumberFormatException ex) {
            log.error(LogUtils.stackTrace(ex));
        }
        return "";
    }
    
    public static String ipRangeToBinaryString(String ipRange) {
        try {
            String[] arr = ipRange.split("/");
            if (arr.length == 1) {
                return ipAddressToBinaryString(arr[0].trim());
            } else if (arr.length == 2) {
                int len = Integer.parseInt(arr[1]);
                return ipAddressToBinaryString(arr[0].trim()).substring(0, len);
            }

        } 
        catch (NumberFormatException ex) {
            log.error(LogUtils.stackTrace(ex));
        }
        return "";
    }

    public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }

    public static long ipToLong(String ipAddress) {
        try {
            return ipToLong(InetAddress.getByName(ipAddress));
        } 
        catch (UnknownHostException ex) {
            log.error(LogUtils.stackTrace(ex));
        }
        return 0;
    }

    public static long[] ipRangeToRangeNumber(String ipRange) {
        try {
            String[] arr = ipRange.split("/");
            if (arr.length == 1) {
                long value = ipToLong(arr[0]);
                return new long[]{value, value};
            } else if (arr.length == 2) {
                int len = Integer.parseInt(arr[1]);
                String bin = ipAddressToBinaryString(arr[0].trim()).substring(0, len);
                long from = Long.parseLong(StringUtils.rightPad(bin, 32, "0"));
                long to = Long.parseLong(StringUtils.rightPad(bin, 32, "1"));
                return new long[]{from, to};
            }

        } catch (NumberFormatException ex) {
            log.error(LogUtils.stackTrace(ex));
        }
        return new long[]{0, 0};
    }

    public static boolean isIPAddressInRange(String ipAddress, String ipRange) {
        String bIP = ipAddressToBinaryString(ipAddress);
        String bRange = ipRangeToBinaryString(ipRange);
        return bIP.startsWith(bRange);
    }
    
}
