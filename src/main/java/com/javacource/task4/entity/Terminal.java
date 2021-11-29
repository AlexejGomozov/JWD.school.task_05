package com.javacource.task4.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Terminal {
    private static final Logger logger = LogManager.getLogger();
    private static ReentrantLock lock = new ReentrantLock();
    private boolean isBusy;
    private final int terminalId;
    private int count;

    public Terminal(){
        this.terminalId = ++count;
        this.isBusy = isBusy;
    }

    public void loadingOrUnloading(DeliveryVan van){
      try {
          lock.lock();
          try {
              if (van.getIsEmpty()) {
                  van.load();
                  TimeUnit.MILLISECONDS.sleep(50);
              } else {
                  van.unload();
                  TimeUnit.MILLISECONDS.sleep(50);
              }
          } catch (InterruptedException e) {
              logger.error("Exception " + e);
              Thread.currentThread().interrupt();
          }
      }finally{
            lock.unlock();
        }
    }
     public boolean getIsBusy(){
        return isBusy;
     }
     public void setIsBousy(boolean isBusy){
        this.isBusy = isBusy;
     }
      public int getTerminalId(){
        return terminalId;
      }


    @Override
    public boolean equals(Object o){
        if(this == o)  return true;
        if(o==null) return false;
        if(this.getClass() != o.getClass()) return false;
        Terminal ter = (Terminal)o;
         return terminalId == ter.terminalId
                 && isBusy == ter.isBusy;
    }
    @Override
    public int hashCode(){
        int result = terminalId;
        result = 31*result + (isBusy ? 1: 0);
        return result;
    }
    @Override
    public String toString(){
        return new StringJoiner(", ", Terminal.class.getSimpleName() + "[", "]")
                .add("terminslId = " + terminalId)
                .add(" isBusy = " + isBusy)
                .toString();
    }
}
