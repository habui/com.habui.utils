/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.utils;

import com.google.gson.Gson;

/**
 *
 * @author habns
 */
public class Utils {
    private static Gson gson;
    
    static {
        gson = new Gson();
    }
    public static String toJSON(Object obj) {
        return gson.toJson(obj);
    }
}
