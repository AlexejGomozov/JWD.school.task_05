package com.javacource.task4.entity;

import com.javacource.task4.util.VanIdGenerator;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeliveryVan extends Thread{

    private static AtomicBoolean atomicBool = new AtomicBoolean(false);
    private final long vanId;
    private boolean emptyVan;
    private boolean isMaxPriority;
    private static int amountOfVans;

{
    vanId = VanIdGenerator.generate();
}

    public DeliveryVan(boolean emptyVan, boolean isMaxPriority){
        this.emptyVan = emptyVan;
        this.isMaxPriority = isMaxPriority;
        if(isMaxPriority){this.setPriority(Thread.MAX_PRIORITY);}
    }

    public static int getAmountOfVans() {
        return amountOfVans =(int)((Math.random()*(100-20))+20);
    }
     public long getVanId(){
      return vanId;
     }
     public boolean getEmptyVan() {
         return emptyVan;
     }
     public void setEmptyVan(boolean emptyVan){
        this.emptyVan = emptyVan;
     }
     public boolean getIsMaxPriority(){
       return isMaxPriority;
     }
         public boolean load(){
             setEmptyVan(atomicBool.get());
            return getEmptyVan();
         }
         public boolean unload(){
    setEmptyVan(atomicBool.compareAndSet(false,true));
             return getEmptyVan();
         }


         @Override
        public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass()!=o.getClass()) return false;
        DeliveryVan van = (DeliveryVan) o;
        return vanId == van.vanId
                && emptyVan == van.emptyVan
                && isMaxPriority == van.isMaxPriority;
         }

         @Override
    public int hashCode(){
    int result = (int)vanId;
    result = 31*result+(emptyVan ? 1 : 0);
    result = 31*result+(isMaxPriority ? 1 : 0);
    return result;
         }

         @Override
    public String toString(){
    return new StringJoiner(", ", DeliveryVan.class.getSimpleName()+ "[","]")
            .add("vanId = " + vanId)
            .add("emptyVan = " + emptyVan)
            .add("isMaxPriority = " + isMaxPriority)
            .toString();
         }
}
