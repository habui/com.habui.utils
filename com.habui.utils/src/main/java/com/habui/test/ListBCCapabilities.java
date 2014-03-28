/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.test;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author habns
 */
public class ListBCCapabilities {
    public static void main (String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        Provider provider = Security.getProvider("BC");
        Iterator it = provider.keySet().iterator();
        while (it.hasNext()) {
            String entry = (String)it.next();
            
            if (entry.startsWith("Alg.Alias")) {
                entry = entry.substring("Alg.Alias.".length());
            }
            
            String factoryClass = entry.substring(0, entry.indexOf("."));
            String name = entry.substring(factoryClass.length() + 1);
            System.out.println(factoryClass + ": " + name);
        }
    }
}
