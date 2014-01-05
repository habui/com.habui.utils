/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.crypto;

import java.security.Security;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author habui
 */
public class CryptoUtils {
    
    private static BouncyCastleProvider bc = new BouncyCastleProvider();
    
    private Cipher encryptCipher = null;
    private Cipher descryptCipher = null;
    
    
    static {
        Security.addProvider(bc);
    }
    
    public CryptoUtils(EAlgorithm algorithm, EMode mode, EPaddingMode paddingMode) throws Exception {
        String method = String.format("%s/%s/%s", EAlgorithm.getAlgorithmName(algorithm), EMode.getModeName(mode), EPaddingMode.getPaddingMode(paddingMode));
        encryptCipher = Cipher.getInstance(method, bc);
        descryptCipher = Cipher.getInstance(method, bc);
    }
    
    public void encrypt(byte[] input, byte[] output) {
        
    }
    
    public void descrypt(byte[] input, byte[] output) {
        
    }
    
}
