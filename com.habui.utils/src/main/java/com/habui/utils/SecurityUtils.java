/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.utils;

import com.habui.log.LogUtils;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author habns
 */
public class SecurityUtils {
    private static final Logger log = LogUtils.getLogger(SecurityUtils.class);
    
    public  static Pattern scriptPattern_1 = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    public  static Pattern scriptPattern_2 = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    public  static Pattern scriptPattern_3 = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    public  static Pattern scriptPattern_4 = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    public  static Pattern scriptPattern_5 = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    public  static Pattern scriptPattern_6 = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);        
    public  static Pattern scriptPattern_7 = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    public  static Pattern scriptPattern_8 = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    public  static Pattern scriptPattern_9 = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    public  static Pattern scriptPattern_10 = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    
    
    public static String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            value = value.replaceAll("", "");

            // Avoid anything between script tags
            
            value = scriptPattern_1.matcher(value).replaceAll("");

            // Avoid anything in a src='...' type of expression
            
            value = scriptPattern_2.matcher(value).replaceAll("");

            
            value = scriptPattern_3.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            
            value = scriptPattern_4.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            value = scriptPattern_5.matcher(value).replaceAll("");

            // Avoid eval(...) expressions
            
            value = scriptPattern_6.matcher(value).replaceAll("");

            // Avoid expression(...) expressions
            value = scriptPattern_7.matcher(value).replaceAll("");

            // Avoid javascript:... expressions
            
            value = scriptPattern_8.matcher(value).replaceAll("");

            // Avoid vbscript:... expressions
            
            value = scriptPattern_9.matcher(value).replaceAll("");

            // Avoid onload= expressions
            
            value = scriptPattern_10.matcher(value).replaceAll("");
        }
        return value;
    }
}
