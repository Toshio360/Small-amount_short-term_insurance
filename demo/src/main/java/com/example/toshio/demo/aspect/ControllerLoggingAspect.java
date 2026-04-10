package com.example.toshio.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    // com.example.toshio.demo 以下の全コントローラを対象
    @Before("execution(* com.example.toshio.demo..*(..))")
    public void logControllerParams(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("▶ Controller called: {}", methodName);

        for (int i = 0; i < args.length; i++) {
            log.info("  └ arg[{}] = {}", i, args[i]);
        }
    }

    @AfterThrowing(pointcut = "execution(* com.example.toshio.demo..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {

        String methodName = joinPoint.getSignature().toShortString();

        log.error("❌ Exception in {}: {}", methodName, ex.getMessage(), ex);
    }
}
