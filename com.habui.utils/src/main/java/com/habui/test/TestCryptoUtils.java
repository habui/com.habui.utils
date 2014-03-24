/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.test;

import com.habui.crypto.CryptoUtils;
import com.habui.crypto.EAlgorithm;
import com.habui.crypto.EMode;
import com.habui.crypto.EPaddingMode;

/**
 *
 * @author habns
 */
public class TestCryptoUtils {
    public static void main(String[] args) {
        CryptoUtils cryptoUtils = new CryptoUtils(EAlgorithm.DES, EMode.CBC, EPaddingMode.PKCS7Padding);
        byte[]  input = new byte[] { 
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        byte[]	keyBytes = new byte[] { 
                0x01, 0x23, 0x45, 0x67, (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef };
        byte[]	ivBytes = new byte[] { 
                0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

        byte[] cipherText = cryptoUtils.encrypt(input, keyBytes, ivBytes);
        
        if (cipherText != null) {
            System.out.println("Cipher : " + Utils.toHex(cipherText));
        }
    }
}
