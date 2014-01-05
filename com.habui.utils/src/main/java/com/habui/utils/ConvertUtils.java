/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.utils;

import com.habui.log.LogUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author habui
 */
public class ConvertUtils {
    private static Logger log = LogUtils.getLogger(ConvertUtils.class);
    
    public static int convertInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {
            log.error(LogUtils.stackTrace(ex));
        }
        return defaultVal;
    }
}
