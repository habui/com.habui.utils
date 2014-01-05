/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.crypto;

/**
 *
 * @author habui
 */
public enum EPaddingMode {
    NoPadding((short)1),
    PKCS7Padding((short)2),
    ISO10126_2Padding((short)3),
    ISO7816_4Padding((short)4),
    TBCPadding((short)5),
    X9_23Padding((short)6);
    
    private short value;
    EPaddingMode(short val) {
        this.value = val;
    }
    
    public static String getPaddingMode(EPaddingMode mode) {
        switch (mode) {
            case NoPadding:
                return "NoPadding";
            case PKCS7Padding:
                return "PKCS7Padding";
            case ISO10126_2Padding:
                return "ISO10126-2Padding";
            case ISO7816_4Padding:
                return "ISO7816-4Padding";
            case TBCPadding:
                return "TBCPadding";
            case X9_23Padding:
                return "X9-23Padding";
            default:
                return "";
        }
    }
}
