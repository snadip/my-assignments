package com.bill.finmark.assignment.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

	private static final Logger LOGGER = LogManager.getLogger(LoggerAspect.class);

	@Before("execution(* com.bill.finmark..*(..)))")
	public void logBeforeAllMethodCall(JoinPoint joinPoint) throws Throwable {

		LOGGER.info("Started with method " + joinPoint.getSignature().getName());
	}

	@After("execution(* com.bill.finmark..*(..)))")
	public void logAfterAllMethodCall(JoinPoint joinPoint) throws Throwable {

		LOGGER.info("Completed execution of method " + joinPoint.getSignature().getName());
	}

}
