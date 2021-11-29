package com.javacource.task4._main;

import com.javacource.task4.entity.LogisticsBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final Logger logger = LogManager.getLogger();
   //private static ReentrantLock lock = new ReentrantLock();
    private static LogisticsBase base;
    private static LogisticsBase LogisticBase;

    public static void main(String[]args){
       // lock.lock();
         base = LogisticBase.getInstance();

        // base.VansAfterBase();

         logger.info("Vans " +  base.vansAfterLogisticBase());
         //lock.unlock();
    }
}
