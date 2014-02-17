/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.utils;

import eu.medsea.mimeutil.MimeUtil;
import java.io.File;
import java.util.Collection;
import org.apache.log4j.Logger;

/**
 *
 * @author habns
 */
public class MimeUtils {
    private static final Logger logger = Logger.getLogger(MimeUtils.class);
    
    static {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
    }
    
    public static String get(File file) {
        Collection<?> mimeTypes = MimeUtil.getMimeTypes(file);
        return mimeTypes.toString();
    }
}
