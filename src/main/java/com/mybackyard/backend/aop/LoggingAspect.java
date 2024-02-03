package com.mybackyard.backend.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Repositories
    @Pointcut("execution(* com.mybackyard.backend.repository.*.*(..))")
    public void repositoryMethods() {}

    @Before("repositoryMethods()")
    public void logRepositoryMethods(JoinPoint joinPoint) {

        logger.info("Repository method called: {}::{}",
                joinPoint.getThis().getClass().getGenericInterfaces()[0], // get my interface name from the chain
                joinPoint.getSignature().getName()
                );
    }

    // Controllers
    @Pointcut("execution(* com.mybackyard.backend.controller.*.*(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logControllerMethods(JoinPoint joinPoint) {

        logger.info("Controller method called: {}::{}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName()
                );
    }

    // Services
    @Pointcut("within(com.mybackyard.backend.service.implementation..*) || within(com.mybackyard.backend.dto.service.implementation..*)")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logServiceMethods(JoinPoint joinPoint) {

        logger.info("Service method called: {}::{}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName()
                );
    }
}
