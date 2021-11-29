package com.javacource.task4.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class LogisticsBase {
    private static final Logger logger = LogManager.getLogger();
     private static LogisticsBase instance;
    private final List<Terminal> terminals = new ArrayList<>();
    private final int amountOfTerminals = 5;
    private final Deque<DeliveryVan> vans = new ArrayDeque<>(); //vansBeforTerminal
    private static ReentrantLock lock = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(amountOfTerminals, true);


   private LogisticsBase() {
       for(int i = 0; i < amountOfTerminals; i++){
           terminals.add(new Terminal());
       }
    }
    public static LogisticsBase getInstance(){
       if(instance ==null){instance = new LogisticsBase();}
     return instance;
    }
   // public String nameOfTerminal;

   public Deque<DeliveryVan> vansAfterLogisticBase() {
       int amountOfVans = DeliveryVan.getAmountOfVans();
       ExecutorService executorService = Executors.newFixedThreadPool(amountOfVans);
       for (int i = 0; i < amountOfVans; i++) {
           executorService.submit(new DeliveryVan(nextBoolean(), nextBoolean()) {
               @Override
               public void run() {
                   //for(int i = 0; i<terminals.size(); i++){
                   //for (Terminal terminal : terminals) {
                      //  nameOfTerminal = terminal.toString();
//                      try {
                          lock.lock();
                          if (this.getIsMaxPriority() == true) {
                              vans.addFirst(this);
                          } else {
                              vans.add(this);  // или addLast  ???
                          }
//                      }finally{
                          lock.unlock();
//                      }
                       try {

                           semaphore.acquire();
                           terminals.get(0).loadingOrUnloading(vans.getFirst());
                           semaphore.release();

                           semaphore.acquire();
                           terminals.get(1).loadingOrUnloading(vans.getFirst());
                           semaphore.release();

                           semaphore.acquire();
                           terminals.get(2).loadingOrUnloading(vans.getFirst());
                           semaphore.release();

                           semaphore.acquire();
                           terminals.get(3).loadingOrUnloading(vans.getFirst());
                           semaphore.release();

                           semaphore.acquire();
                           terminals.get(4).loadingOrUnloading(vans.getFirst());
                           semaphore.release();

                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       // logger.info("befor " + vans);
               }
           });
       }
       executorService.shutdown();
       try {
           executorService.awaitTermination(1, TimeUnit.HOURS);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       return vans;
   }
//   @Override
//    public String toString(){
//      StringBuilder sb = new StringBuilder();
//       sb.append("Terminal N-" + nameOfTerminal);
//               sb.append(" " + this.vansAfterLogisticBase().toString());
//       return sb.toString();
//   }
}

