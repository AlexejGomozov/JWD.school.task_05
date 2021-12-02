package com.javacource.task4.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Terminal {
    private static final Logger logger = LogManager.getLogger();
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean atomicBool = new AtomicBoolean(true);
    private final int terminalId;
    private int count;

    public Terminal(){
        this.terminalId = ++count;
    }

    public DeliveryVan loadingOrUnloading(DeliveryVan van) {
        if (van.getIsEmpty() == atomicBool.get()){
            van.setIsEmpty(atomicBool.compareAndSet(false,true));
            //van.load();
         } else {
           //van.unload();  //
            van.setIsEmpty(atomicBool.get()); //
         }
        return van;
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
         return terminalId == ter.terminalId;

    }
    @Override
    public int hashCode(){
        int result = terminalId;
        return 31*result;
    }
    @Override
    public String toString(){
        return new StringJoiner(", ", Terminal.class.getSimpleName() + "[", "]")
                .add("terminslId = " + terminalId)
                .toString();
    }
}
