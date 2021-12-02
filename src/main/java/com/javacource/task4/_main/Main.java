package com.javacource.task4._main;

import com.javacource.task4.entity.LogisticBase;
import com.javacource.task4.exception.ThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    private static LogisticBase base;
    private static LogisticBase LogisticBase;

    public static void main(String[]args) throws ThreadException {

         base = LogisticBase.getInstance();

         logger.info("Vans after the logistic base " +  base.vansAfterLogisticBase());
    }
}
