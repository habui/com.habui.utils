/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.test;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author habns
 */
public class BouncyCastle {
    public static void main (String[] args) {
        String providerName = "BC";
        
        Security.addProvider(new BouncyCastleProvider());
        if (Security.getProvider(providerName) == null) 
            System.out.println(providerName + " provider is not installed");
        else
            System.out.println(providerName + " is installed");
    }
}
