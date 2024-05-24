package com.study.allinonestudy.aop;

import com.praiseutil.timelog.utility.LogTrace;
import com.praiseutil.timelog.utility.TraceStatus;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;




@Aspect
@Component
@RequiredArgsConstructor
@Log
public class TimeLogAOP {

    private final LogTrace logTrace;

    @Pointcut("execution(* com.study.allinonestudy.service..*(..))")
    public void allService() {}

    @Pointcut("execution(* com.study.allinonestudy.controller..*(..))")
    public void allController() {}

    @Around("allController() || allService()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus traceStatus = logTrace.begin(joinPoint.getSignature().toShortString());
        try {
            Object result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        } finally {
            logTrace.end(traceStatus);
        }
    }
}
