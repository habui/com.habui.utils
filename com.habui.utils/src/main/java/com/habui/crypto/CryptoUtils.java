/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.crypto;

import com.habui.log.LogUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.logging.Level;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author habui
 */
public class CryptoUtils {
    private static Logger logger = Logger.getLogger(CryptoUtils.class);
    private static BouncyCastleProvider bc = new BouncyCastleProvider();
    
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;
    
    private EAlgorithm algorithm;
    private EMode mode;
    private EPaddingMode paddingMode;
    
    
    static {
        Security.addProvider(bc);
    }
    
    public CryptoUtils(EAlgorithm algorithm, EMode mode, EPaddingMode paddingMode) {
        try {
            String method = String.format("%s/%s/%s", EAlgorithm.getAlgorithmName(algorithm), EMode.getModeName(mode), EPaddingMode.getPaddingMode(paddingMode));
            encryptCipher = Cipher.getInstance(method, bc);
            decryptCipher = Cipher.getInstance(method, bc);
        
            this.algorithm = algorithm; this.mode = mode; this.paddingMode = paddingMode;
        }
        catch (NoSuchAlgorithmException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (NoSuchPaddingException ex) {
            logger.error(LogUtils.stackTrace(ex));
        }
    }
    
    public byte[] encrypt(byte[] plainText, byte[] keyBytes, byte[] ivBytes)  {
        if (ivBytes == null || ivBytes.length == 0)
            return null;
        
        IvParameterSpec ivSpec =new IvParameterSpec(ivBytes);
        SecretKeySpec key = new SecretKeySpec(keyBytes, EAlgorithm.getAlgorithmName(this.algorithm));
        
        byte[] cipherText = null;
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            
            cipherText = new byte[encryptCipher.getOutputSize(plainText.length)];
            int ctLength = encryptCipher.update(plainText, 0, plainText.length, cipherText, 0);
            ctLength += encryptCipher.doFinal(cipherText, ctLength);
            
        }
        catch (InvalidKeyException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (InvalidAlgorithmParameterException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (ShortBufferException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (IllegalBlockSizeException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (BadPaddingException ex) {
            logger.error(LogUtils.stackTrace(ex));
        }
        return cipherText;
    }
    
    public byte[] descrypt(byte[] cipherText, byte[] keyBytes, byte[] ivBytes) {
        if (ivBytes == null || ivBytes.length == 0)
            return null;
        
        IvParameterSpec ivSpec =new IvParameterSpec(ivBytes);
        SecretKeySpec key = new SecretKeySpec(keyBytes, EAlgorithm.getAlgorithmName(this.algorithm));
        
        byte[] plainText = null;
        try {
            decryptCipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            
            plainText = new byte[decryptCipher.getOutputSize(cipherText.length)];
            
            int ptLength = decryptCipher.update(cipherText, 0, cipherText.length, plainText, 0);
            ptLength += decryptCipher.doFinal(plainText, ptLength);
            
        }
        catch (InvalidKeyException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (InvalidAlgorithmParameterException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (ShortBufferException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (IllegalBlockSizeException ex) {
            logger.error(LogUtils.stackTrace(ex));
        } catch (BadPaddingException ex) {
            logger.error(LogUtils.stackTrace(ex));
        }
        return plainText;
    }
    
}
