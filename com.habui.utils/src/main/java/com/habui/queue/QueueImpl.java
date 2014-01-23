/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.queue;

import java.util.concurrent.ArrayBlockingQueue;
import org.apache.log4j.Logger;

/**
 *
 * @author habns
 */
public class QueueImpl implements IQueue {
    
    private static Logger logger = Logger.getLogger(QueueImpl.class);
    private ArrayBlockingQueue<IQueueCommand> queue;
    private int workerNum;
    private int maxLength;
    private long msleepIdle;

    public QueueImpl(int workerNum, int maxLength)
    {
      this.workerNum = workerNum;
      this.maxLength = maxLength;
      this.msleepIdle = this.msleepIdle;
      this.queue = new ArrayBlockingQueue(maxLength);
    }

    public IQueueCommand take() {
        
        IQueueCommand cmd = null;
        try {
            cmd = (IQueueCommand)this.queue.take();
        }
        catch (InterruptedException e) {
            logger.error("Exception in take", e);
        }
        return cmd;
    }

    public boolean put(IQueueCommand paramQueueCommand) {
        if (this.queue.remainingCapacity() < this.workerNum) {
            logger.error("Queue exceed max length!!!!!!");
            return false;
        }
        
        boolean ret = false;
        try {
            this.queue.put(paramQueueCommand);
            ret = true;
        }
        catch (InterruptedException e) {
            logger.error("Exception in put", e);
        }
        return ret;
    }

    public void process() {
        for (int i = 0; i < this.workerNum; i++)
        {
            QueueWorker qw = new QueueWorker(this);
            new Thread(qw).start();
        }
    }

    public int size() {
        return this.queue.size();
    }

    public int remaining() {
        return this.queue.remainingCapacity();
    }

    public int getWorkerNum() {
        return this.workerNum;
    }

    public int getMaxLength() {
        return this.maxLength;
    }
    
}
