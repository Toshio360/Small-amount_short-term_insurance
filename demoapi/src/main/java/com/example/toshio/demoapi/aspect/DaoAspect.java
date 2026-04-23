package com.example.toshio.demoapi.aspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
@Slf4j
@Aspect
@Component
public class DaoAspect {
    // com.example.toshio.demoapi 以下の全コントローラを対象
    @Before("execution(* com.example.toshio.demoapi.repository.*(..))")
    public void logControllerParams(JoinPoint joinPoint) {

    }

    @AfterThrowing(pointcut = "execution(* com.example.toshio.demo..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {

        String methodName = joinPoint.getSignature().toShortString();

        log.error("❌ Exception in {}: {}", methodName, ex.getMessage(), ex);
    }

}
