/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.crypto;

/**
 *
 * @author habui
 */
public enum EMode {
    ECB((short)1),  // Electronic CodeBook
    CBC((short)2),  // Cipher Block Chaining
    OFB((short)3),  // Output Feedback Mode
    CFB((short)4),  // Cipher Feedback Mode
    SIC((short)5),  // Segmented Integer Counter mode (CounTeR mode)
    OpenPGPCFB((short)6),   //  Variation on CFB mode defined in OpenPGP
    CTS((short)7),  // Cipher Text Stealing (should be used with NoPadding)
    GOFB((short)8); // OFB mode defined for the GOST-28147 encryption algorithm  
    
    private short value;
    
    EMode(short val) {
        this.value = val;
    }
    
    public static String getModeName(EMode mode) {
        switch (mode) {
            case ECB:
                return "ECB";
            case CBC:
                return "CBC";
            case OFB:
                return "OFB";
            case CFB:
                return "CFB";
            case SIC:
                return "SIC";
            case OpenPGPCFB:
                return "OpenPGPCFB";
            case CTS:
                return "CTS";
            case GOFB:
                return "GOFB";
            default:
                return "";
        }
    }
}
