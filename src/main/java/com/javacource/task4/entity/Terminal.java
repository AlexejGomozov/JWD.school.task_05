package com.javacource.task4.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Terminal {
    private static final Logger logger = LogManager.getLogger();
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean atomicBool = new AtomicBoolean();
    //private boolean isBusy;
    private final int terminalId;
    private int count;

    public Terminal(){
        this.terminalId = ++count;
        //this.isBusy = isBusy;
    }

    public void loadingOrUnloading(DeliveryVan van) {
        try {
            lock.lock();
            if (van.getIsEmpty() && !atomicBool.get()) {
                van.load();
            }
            if (van.getIsEmpty() && atomicBool.get()) {
                van.unload();

            }
        } finally {
            lock.unlock();

        }
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
         return terminalId == ter.terminalId; //&& isBusy == ter.isBusy;

    }
    @Override
    public int hashCode(){
        int result = terminalId;
        return 31*result;       // result = 31*result + (isBusy ? 1: 0);
    }
    @Override
    public String toString(){
        return new StringJoiner(", ", Terminal.class.getSimpleName() + "[", "]")
                .add("terminslId = " + terminalId)
                .toString();    // .add(" isBusy = " + isBusy)
    }
}
