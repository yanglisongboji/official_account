package com.shotacon.wx.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestMsgAspect {

	private static final Logger log = LoggerFactory.getLogger(TestMsgAspect.class);

	@Pointcut("@annotation(com.shotacon.wx.aspect.TestMsg)")
	public void pointCut() {
		log.info("pointCut");
	}

	@Around(value = "pointCut()&&@annotation(t)")
	public Object around(ProceedingJoinPoint pjp, TestMsg t) throws Throwable {
		log.info("Around {} ", t.isActive());
		Object object = pjp.getArgs()[0];
		log.info(String.valueOf(object));
		return "";
	}
}
