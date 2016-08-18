/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.log4j.Logger;

/**
 *
 * @author habui1
 */
public class ConfigUtils {
    private static final Map<String, String> localconfig = new HashMap();
    public static final String CONFIG_HOME = "conf";
    public static final String CONFIG_FILE = "config.ini";
    private static final Logger logger = Logger.getLogger(ConfigUtils.class);
    static final CompositeConfiguration config;

    public static String getParam(String section, String name) {
      String key = section + "." + name;

      String value = (String)localconfig.get(key);

      if (value != null) {
        return value;
      }

      value = config.getString(key);
      if (value != null) localconfig.put(key, value);
      return value;
    }

    static {
      String HOME_PATH = System.getProperty("apppath");
      String APP_ENV = System.getProperty("appenv");

      if (APP_ENV == null) APP_ENV = "";
      if (!"".equals(APP_ENV)) APP_ENV = APP_ENV + ".";

      config = new CompositeConfiguration();

      File configFile = new File(HOME_PATH + File.separator + "conf" + File.separator + APP_ENV + "config.ini");
      try
      {
        config.addConfiguration(new HierarchicalINIConfiguration(configFile));

        Iterator ii = config.getKeys();

        while (ii.hasNext()) {
          String key = (String)ii.next();
          localconfig.put(key, config.getString(key));
        }

      }
      catch (ConfigurationException e) {
        logger.error(e.getMessage());
        System.exit(1);
      }
    }
}
