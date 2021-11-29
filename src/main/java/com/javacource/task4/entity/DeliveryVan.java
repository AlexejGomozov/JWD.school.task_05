package com.javacource.task4.entity;

import com.javacource.task4.util.VanIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringJoiner;

public class DeliveryVan extends Thread{

    private static final Logger logger = LogManager.getLogger();
    private final long vanId;
    private boolean isEmpty;
    private boolean isMaxPriority;
    private static int amountOfVans;


{
    vanId = VanIdGenerator.generate();
}

    public DeliveryVan(boolean isEmpty, boolean isMaxPriority){
        this.isEmpty = isEmpty;
        this.isMaxPriority = isMaxPriority;
        if(isMaxPriority){this.setPriority(Thread.MAX_PRIORITY);}
    }

    public static int getAmountOfVans() {
        return amountOfVans =(int)((Math.random()*(100-20))+20);
    }
     public long getVanId(){
      return vanId;
     }
     public boolean getIsEmpty() {
         return isEmpty;
     }
     public boolean getIsMaxPriority(){
       return isMaxPriority;
     }
         public void load(){
             this.isEmpty = false;
         }
         public void unload(){
              this.isEmpty = true;
         }


         @Override
        public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass()!=o.getClass()) return false;
        DeliveryVan van = (DeliveryVan) o;
        return vanId == van.vanId
                && isEmpty ==van.isEmpty
                && isMaxPriority == van.isMaxPriority;
         }

         @Override
    public int hashCode(){
    int result = (int)vanId;
    result = 31*result+(isEmpty ? 1 : 0);
    result = 31*result+(isMaxPriority ? 1 : 0);
    return result;
         }

         @Override
    public String toString(){
    return new StringJoiner(", ", DeliveryVan.class.getSimpleName()+ "[","]")
            .add("vanId = " + vanId)
            .add("isEmpty = " + isEmpty)
            .add("isMaxPriority = " + isMaxPriority)
            .toString();
         }
}
