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
    private final Deque<DeliveryVan> vansBeforeBase = new ArrayDeque<>();
    private final Deque<DeliveryVan> vansAfterBase = new ArrayDeque<>();
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
                            vansBeforeBase.addFirst(this);
                        } else {
                            vansBeforeBase.add(this);
                        }
                                     try {
                                         TimeUnit.MICROSECONDS.sleep(1500);
                                     } catch (InterruptedException ex) {
                                         logger.error("Error " + ex);
                                     }
                    }finally{
                        lock.unlock();
                    }

                    try {
                        TimeUnit.MILLISECONDS.sleep(3000);

                        if (semaphore.tryAcquire(5, TimeUnit.MILLISECONDS)) {
                            lock.tryLock(1, TimeUnit.MILLISECONDS);
                            DeliveryVan vanForLoading1 = vansBeforeBase.poll();
                            DeliveryVan changedVan1 = terminals.get(0).loadingOrUnloading(vanForLoading1);
                            vansAfterBase.addFirst(changedVan1);
                        }lock.unlock();
                        semaphore.release();

                        if (semaphore.tryAcquire(5, TimeUnit.MILLISECONDS)) {
                            lock.tryLock(1, TimeUnit.SECONDS);
                            DeliveryVan vanForLoading2 = vansBeforeBase.poll();
                            DeliveryVan changedVan2 = terminals.get(1).loadingOrUnloading(vanForLoading2);
                            vansAfterBase.addFirst(changedVan2);
                        }lock.unlock();
                        semaphore.release();

                        if (semaphore.tryAcquire(1500, TimeUnit.MILLISECONDS)){
                            lock.tryLock(1000, TimeUnit.MILLISECONDS);
                            DeliveryVan vanForLoading3 = vansBeforeBase.poll();
                            DeliveryVan changedVan3 = terminals.get(2).loadingOrUnloading(vanForLoading3);
                            vansAfterBase.addFirst(changedVan3);
                        } lock.unlock();
                        semaphore.release();

                        if(semaphore.tryAcquire(2000,TimeUnit.MILLISECONDS)) {
                            lock.tryLock(1500, TimeUnit.MILLISECONDS);
                            DeliveryVan vanForLoading4 = vansBeforeBase.poll();
                            DeliveryVan changedVan4 = terminals.get(3).loadingOrUnloading(vanForLoading4);
                            vansAfterBase.addFirst(changedVan4);
                        } lock.unlock();
                        semaphore.release();

                        if(semaphore.tryAcquire(2000,TimeUnit.MILLISECONDS)) {
                            lock.tryLock(1500, TimeUnit.MILLISECONDS);
                            DeliveryVan vanForLoading5 = vansBeforeBase.poll();
                            DeliveryVan changedVan5 = terminals.get(4).loadingOrUnloading(vanForLoading5);
                            vansAfterBase.addFirst(changedVan5);
                        }lock.unlock();
                        semaphore.release();

                    } catch (InterruptedException e) {
                        logger.error("Exception " + e);
                    }
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.error("Exception " + e);
        }
        return vansAfterBase;
    }




}
