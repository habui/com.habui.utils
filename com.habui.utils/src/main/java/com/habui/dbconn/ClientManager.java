/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.dbconn;

import com.habui.utils.ConfigUtils;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 *
 * @author habns
 */
public class ClientManager  implements IManager {
    
    private ClientPoolByHost commentClientPoolByHost;
    private static final Lock createLock_ = new ReentrantLock();
    private static Map<String, ClientManager> INSTANCES = new HashMap();
    private static Logger logger_ = Logger.getLogger(ClientManager.class);
  
    public static IManager getInstance(String instanceName) {
        if (!INSTANCES.containsKey(instanceName))
        {
          createLock_.lock();
          try
          {
            if (!INSTANCES.containsKey(instanceName)) {
              INSTANCES.put(instanceName, new ClientManager(instanceName));
            }
          }
          finally
          {
            createLock_.unlock();
          }
        }
        return (IManager)INSTANCES.get(instanceName);
    }
  
  public ClientManager(String instanceName)
  {
        String driver = ConfigUtils.getParam(instanceName, "driver");
        String url = ConfigUtils.getParam(instanceName, "url");
        String user = ConfigUtils.getParam(instanceName, "user");
        String password = ConfigUtils.getParam(instanceName, "password");
        this.commentClientPoolByHost = new ClientPoolByHost(driver, url, user, password);
  }
  
    public Connection borrowClient()
    {
        Connection client = this.commentClientPoolByHost.borrowClient();
        return client;
    }
  
    public void returnClient(Connection client)
    {
        this.commentClientPoolByHost.returnObject(client);
    }

    public void invalidClient(Connection client)
    {
        this.commentClientPoolByHost.invalidClient(client);
    }
}
