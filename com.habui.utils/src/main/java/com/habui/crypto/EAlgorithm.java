/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.crypto;

/**
 *
 * @author habui
 */
public enum EAlgorithm {

    AES((short)1),
    Blowfish((short)2),
    CAST5((short)3),
    CAST6((short)4),
    DES((short)5),
    DESEDE((short)6),
    GOST_28147((short)7),
    IDEA((short)8);
    
    
    private short value;
    
    EAlgorithm(short val) {
        this.value = val;
    }
    
    public static String getAlgorithmName(EAlgorithm algorithm) {
        switch (algorithm) {
            case AES:
                return "AES";
            case Blowfish:
                return "Blowfish";
            case CAST5:
                return "CAST5";
            case CAST6:
                return "CAST6";
            case DES:
                return "DES";
            case DESEDE:
                return "DESEDE";
            case GOST_28147:
                return "GOST-28147";
            case IDEA:
                return "IDEA";
            default:
                return "";
        }
    }
}
