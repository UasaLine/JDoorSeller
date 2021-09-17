package com.jds.model.tools;

import com.jds.controller.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvChecker {
    static private Logger logger = LoggerFactory.getLogger(OrderController.class);

    static public boolean sql() {


        if (envNameCheck("SQL_DB_URL") &
                envNameCheck("SQL_DB_NAME") &
                envNameCheck("SQL_DB_PASS")) {
            return true;
        }
        return false;
    }

    static private boolean envNameCheck(String envName) {
        String value = System.getenv(envName);
        if (value == null || " ".equals(value) || "".equals(value)) {
            logger.warn("env properties - " + envName + " not installed");
            return false;
        }
        return true;
    }

}
