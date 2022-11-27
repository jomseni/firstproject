package com.example.firstproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect  //AOP 클래스로 선언 하기 위한 어노테이션
@Component
@Slf4j
public class PerformanceAspect {

    //특정 어노테이션을 대상 지정
    @Pointcut("@annotation(com.example.firstproject.annotation.RunningTime)")
    private  void enableRunningTime(){}

    //기본 패키지의 모든 메소드 지정
    //하위의 모든 메서드를 지정  (..) 파라미터 갯수는 상관없다는 뜻!
    @Pointcut("execution(* com.example.firstproject..*.*(..))")
    private void cut() {}


    //실행 시점 설정 : 두 조건을 모두 만족하는 대상을 전후로 부가 기능을 삽입!
    @Around("cut() && enableRunningTime()")
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable { //Around어노테이션을 이용한 AOP를 사용할 땐 ProceedingJoinPoint를 사용한다.
        //메소드 수행 전 , 측정 시작(시간)
        StopWatch stopWatch = new StopWatch();  //시간 측정을 위한 스프링에서 제공하는 도구 => StopWatch
        stopWatch.start();
        //메소드를 수행
        Object reeturningObj = joinPoint.proceed();
        //메소드 수행 후, 측정 종료 및 로깅
        stopWatch.stop();
                // 메소드명
        String methodName = joinPoint.getSignature()
                .getName();
        log.info("{}의 총 수행 시간 => {} sec",methodName,stopWatch.getTotalTimeSeconds());


        //RunningTime(실행 시간 측정) 부가기능이 cut에서 지정된 기본 패키지 모든 메소드 이면서 동시에 RunningTime어노테이션을 가지고 있는 것을 찾아서 수행 시간 측정
    }
}
