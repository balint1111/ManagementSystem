package com.example.managementsystem.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class BaseController {

    /**
     * Used for logging endpoint runtimes
     * @param start from System.currentTimeMillis
     */
    protected void endpointLogging(Long start) {
        /*https://www.baeldung.com/java-name-of-executing-method*/
        final StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        String msg = "Method: " +
                stackTraceElement[2].getFileName().replace(".java", "") +
                " - " +
                stackTraceElement[2].getMethodName() +
                " lasted " +
                (System.currentTimeMillis() - start) +
                " ms";
        log.info(msg);
    }

}
