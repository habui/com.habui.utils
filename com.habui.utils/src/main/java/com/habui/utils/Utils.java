/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.utils;

import org.apache.commons.codec.binary.Base64;



/**
 *
 * @author habns
 */
public class Utils {
    public static int getRandom(int lowerBound, int upperBound) {
        return lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1));
    }
    
    public static byte[] encodeBase64(String text) {
        return Base64.encodeBase64(text.getBytes());
    }
    
    public static byte[] decodeBase64(byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }
}
