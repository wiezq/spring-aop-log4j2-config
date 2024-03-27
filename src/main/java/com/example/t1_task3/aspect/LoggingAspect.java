package com.example.t1_task3.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@com.example.t1_task3.aspect.Loggable *)")
    public void loggingPointcut() {
    }

    @Before("loggingPointcut()")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();

        StringBuilder parameters = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            parameters.append(parameterNames[i]).append("=").append(parameterValues[i]);
            if (i < parameterNames.length - 1) {
                parameters.append(", ");
            }
        }

        log.info("Calling method: {} with parameters: ({})", methodSignature.getName(), parameters);
    }

    @AfterReturning(pointcut = "loggingPointcut()", returning = "result")
    public void logAfterMethodCall(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        boolean hasReturnType = signature instanceof MethodSignature
                && ((MethodSignature) signature).getReturnType() != void.class;
        if (hasReturnType) {
            log.info("Method {} returned: {}", methodName, result);
        }
    }

    @AfterThrowing(pointcut = "loggingPointcut()", throwing = "ex")
    public void logAfterMethodThrowsException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("Method {} threw exception: {}", methodName, ex.getMessage());
    }
}