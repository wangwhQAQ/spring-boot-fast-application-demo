package com.example.demo.Aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class TestAspectImplClass {
    private Logger logger = LoggerFactory.getLogger(TestAspectImplClass.class);

    @Pointcut(value = "execution(* com.example.demo.Service.Impl.*.*(..))")
    public void TesAspectCut1(){

    }

    @Pointcut(value = "execution(* com.example.demo.Service.*.*(..))")
    public void TesAspectCut2(){

    }

    //  ProceedingJoinPoint   类型作为参数  只能用于环绕通知  因为它暴露了 proceed 方法
    @Around("TesAspectCut1()")
    public Object methodInjection(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        ObjectMapper m = new ObjectMapper();
        Object[] array = pjp.getArgs();

        logger.info("调用前:" + className + " -- " + methodName + "-- args=" + m.writeValueAsString(array));

        Object obj = pjp.proceed();

        logger.info("调用后:" + className + " -- " + methodName + "-- args=" + m.writeValueAsString(obj));

        return obj;
    }

    @Around("TesAspectCut2()")
    public Object methodInjection2(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        ObjectMapper m = new ObjectMapper();
        Object[] array = pjp.getArgs();

        logger.info("调用前:" + className + " -- " + methodName + "-- args=" + m.writeValueAsString(array));

        Object obj = pjp.proceed();

        logger.info("调用后:" + className + " -- " + methodName + "-- args=" + m.writeValueAsString(obj));

        return obj;
    }

    @Around("@annotation(com.example.demo.Aspect.Interface.TestAspect)")
    public Object methodInjection3(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        ObjectMapper m = new ObjectMapper();
        Object[] array = pjp.getArgs();

        logger.info("调用前:" + className + " -- " + methodName + "-- args=" + m.writeValueAsString(array));

        Object obj = pjp.proceed();

        logger.info("调用后:" + className + " -- " + methodName + "-- args=" + m.writeValueAsString(obj));

        return obj;
    }
}
