package com.shotacon.wx.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.shotacon.wx.entity.MessageEntity;

/**
 * 主题缓存切面
 * 
 * @author shotacon
 *
 */
@Aspect
@Component
public class ThemeMicroCityCacheAspect {

	private static final Logger log = LoggerFactory.getLogger(ThemeMicroCityCacheAspect.class);

	@Pointcut("@annotation(com.shotacon.wx.aspect.TestInfo)")
//	@Pointcut("execution(* com.shotaion.wx.controller..*.*(..))")
	public void pointCut() {
		log.info("pointCut");
	}

	@Around(value = "pointCut()&&@annotation(t)")
	public Object around(ProceedingJoinPoint pjp, TestInfo t) throws Throwable {
		log.info("Around {} ", t.isActive());
//		return "Hello Bitch";
//		return pjp.proceed();
		MessageEntity a = new MessageEntity();
		a.setContent("123");
		return a;
	}

	@AfterReturning(value = "pointCut()", returning = "result")
	public void afterReturning(JoinPoint point, Object result) {
		log.info("afterReturning: {}", result);
		if (result instanceof String) {
			log.info("1");
		}
		if (result instanceof MessageEntity) {
			log.info("2");
		}
	}

	@AfterThrowing(value = "pointCut()", throwing = "e")
	public void afterThrowing(Exception e) {
		log.error("ThemeMicroCityCacheAspect throw error: ", e);
	}
}
