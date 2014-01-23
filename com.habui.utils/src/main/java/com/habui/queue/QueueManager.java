/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.queue;

import java.util.Map;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

/**
 *
 * @author habns
 */
public class QueueManager
{
  private static Map<String, QueueManager> instances = new NonBlockingHashMap();
  private IQueue queue;
  
  public static QueueManager getInstance(String name) {
        QueueManager instance = (QueueManager)instances.get(name);
        if (instance == null) {
            synchronized (QueueManager.class)
            {
                if (instance == null)
                {
                  instance = new QueueManager();
                  instances.put(name, instance);
                }
            }
        }
        return instance;
  }
  
  public void init(int workerNum, int maxLength) {
    this.queue = new QueueImpl(workerNum, maxLength);
  }
  
  public void process() {
    this.queue.process();
  }
  
  public int size() {
    return this.queue.size();
  }
  
  public int remaining() {
    return this.queue.remaining();
  }
  
  public void put(IQueueCommand cmd) {
    this.queue.put(cmd);
  }
}

