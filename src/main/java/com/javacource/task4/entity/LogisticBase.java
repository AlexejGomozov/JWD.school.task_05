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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class LogisticBase {
    private static final Logger logger = LogManager.getLogger();
    private static LogisticBase instance;
    private final List<Terminal> terminals = new ArrayList<>();
    private final int amountOfTerminals = 5;
    private final Deque<DeliveryVan> vans = new ArrayDeque<>();
    private final Deque<DeliveryVan> vans1 = new ArrayDeque<>();
    private static ReentrantLock lock = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(amountOfTerminals, true);
    private static final AtomicBoolean initialize = new AtomicBoolean(false);

    private LogisticBase() {
        for(int i = 0; i < amountOfTerminals; i++){
            terminals.add(new Terminal());
        }

    }
    public static LogisticBase getInstance(){
        while(instance ==null){
            if(initialize.compareAndSet(false, true)){
                instance = new LogisticBase();}
        }
        return instance;
    }
    int a, b, c, d, e;

    public Deque<DeliveryVan> vansAfterLogisticBase() {

        int amountOfVans = DeliveryVan.getAmountOfVans();
        ExecutorService executorService = Executors.newFixedThreadPool(amountOfVans);
        for (int i = 0; i < amountOfVans; i++) {
            executorService.submit(new DeliveryVan(nextBoolean(), nextBoolean()) {

                @Override
                public void run() {
                    try {
                        lock.lock();
                        if (this.getIsMaxPriority() == true) {
                            vans.addFirst(this);
                        } else {
                            vans.add(this);
                        }


                        try {
                            TimeUnit.MICROSECONDS.sleep(1500);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    }finally{
                        lock.unlock();
                    }


                    logger.info(" 2222222-----------" + vans);
                    try {
                        TimeUnit.MILLISECONDS.sleep(3000);     //1500ml ,1sec-no,

                        if (semaphore.tryAcquire(5, TimeUnit.MILLISECONDS)) {  // 10ml, 50ml-no,
                            lock.tryLock(1, TimeUnit.MILLISECONDS);          // 1sec, 100 ml-no,
                            DeliveryVan vanAfter1 = vans.poll();
                            logger.info("Vans11111 " + vanAfter1 + "========" + "  a-" + a++); // for controle
                            DeliveryVan changedVan1 = terminals.get(0).loadingOrUnloading(vanAfter1);
                            vans1.addFirst(changedVan1);
                        }lock.unlock();
                        semaphore.release();

                        if (semaphore.tryAcquire(5, TimeUnit.MILLISECONDS)) {  //10ml, 20ml-no, 5ml- no, 50ml- no,
                            lock.tryLock(1, TimeUnit.SECONDS);      //1sec, 1 mlsec-no,
                            DeliveryVan vanAfter2 = vans.poll();
                            logger.info("Vans22222 " + vanAfter2 + "========" + "   b-" + b++);
                            DeliveryVan changedVan2 = terminals.get(1).loadingOrUnloading(vanAfter2);
                            vans1.addFirst(changedVan2);
                        }lock.unlock();
                        semaphore.release();

                        if (semaphore.tryAcquire(1500, TimeUnit.MILLISECONDS)){
                            lock.tryLock(1000, TimeUnit.MILLISECONDS);
                            DeliveryVan vanAfter3 = vans.poll();
                            logger.info("Vans333333 " + vanAfter3 + "========" + "   c-" + c++);
                            DeliveryVan changedVan3 = terminals.get(2).loadingOrUnloading(vanAfter3);
                            vans1.addFirst(changedVan3);
                        } lock.unlock();
                        semaphore.release();

                        if(semaphore.tryAcquire(2000,TimeUnit.MILLISECONDS)) {
                            lock.tryLock(1500, TimeUnit.MILLISECONDS);
                            DeliveryVan vanAfter4 = vans.poll();
                            logger.info("Vans44444 " +  vanAfter4 + "========" +"   d-"+ d++);
                            DeliveryVan changedVan4 = terminals.get(3).loadingOrUnloading(vanAfter4);
                            vans1.addFirst(changedVan4);
                        } lock.unlock();
                        semaphore.release();

                        if(semaphore.tryAcquire(2000,TimeUnit.MILLISECONDS)) {
                            lock.tryLock(1500, TimeUnit.MILLISECONDS);
                            DeliveryVan vanAfter5 = vans.poll();
                            logger.info("Vans55555 " +  vanAfter5 + "========" +  "   e-"+ e++);
                            DeliveryVan changedVan5 = terminals.get(4).loadingOrUnloading(vanAfter5);
                            vans1.addFirst(changedVan5);
                        }lock.unlock();
                        semaphore.release();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return vans1;
    }



//   @Override
//    public String toString(){
//      StringBuilder sb = new StringBuilder();
//       sb.append("Terminal N-" + nameOfTerminal);
//               sb.append(" " + this.vansAfterLogisticBase().toString());
//       return sb.toString();
//   }
}
