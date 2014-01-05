/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.utils;

/**
 *
 * @author habns
 */
public class Utils {
    public static int getRandom(int lowerBound, int upperBound) {
        return lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1));
    }
}
