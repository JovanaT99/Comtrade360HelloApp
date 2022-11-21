package com.comtrade.helloApp.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {
    Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.comtrade.helloApp.controllers..*(..)))")
    public Object loggMethods(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        log.info("Method: "+methodSignature.getDeclaringTypeName()+"."+methodSignature.getName());
        log.info("Arguments: "+ Arrays.toString(point.getArgs()));
        log.info("Target: "+point.getTarget().getClass().getName());


        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = point.proceed();
        stopWatch.stop();

        log.info("Execution time: "+ stopWatch.getTotalTimeMillis()+"ms");
        return result;
    }

}
