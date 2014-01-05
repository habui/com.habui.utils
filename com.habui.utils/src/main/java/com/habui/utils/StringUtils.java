/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.utils;

import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author habns
 */
public class StringUtils {
    public static String leftPad(String value, int length, String pad) {
        return org.apache.commons.lang.StringUtils.leftPad(value, length, pad);
    }
    
    public static String rightPad(String value, int length, String pad) {
        return org.apache.commons.lang.StringUtils.rightPad(value, length, pad);
    }
    
    public static String escapeHtml(String str) {
        return StringEscapeUtils.escapeHtml(str);
    }
    
    public static String unescapeHtml(String str) {
        return StringEscapeUtils.unescapeHtml(str);
    }
}
