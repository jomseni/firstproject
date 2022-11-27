package com.example.firstproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect //AOP 선언, AOP 클래스 선언 : 부가 기능을 주입하는 클래스
@Component // Ioc 컨테이너가 해당 객체를 생성 및 관리
@Slf4j
public class DebuggingAspect {
    // 대상 메소드 선택 : CommnetService#create()
    //어느 지점,대상을 타겟으로 하여 부가기능을 넣을 지 정하는 어노테이션!
    @Pointcut("execution(* com.example.firstproject.service.CommentService.*(..))")  //괄호 안에는 대상의 경로를 작성 해주면 된다. (..)은 어떠한 파라미터가 들어와도 된다는 뜻
    private void cut(){} // 이름은 그냥 임의로 설정한 것

    // 실행 시점 설정(타겟으로 한 메서드에 찔러 넣어서 부가기능을 주입할 건데 이 부가기능을 타겟 메서드(CommnetService#create()) 실행 이전에 실행시킨다.
    //cut()의 대상이 수행되기 이전
    @Before("cut()")
    public void loggingArgs(JoinPoint joinPoint){ //cut()의 대상 메소드
        // 입력 값 가져오기
        Object[] args = joinPoint.getArgs();

        //클래스명
        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();
        // 메소드명
        String methodName = joinPoint.getSignature()
                .getName();
        //입력 값 로깅 하기
        //CommentService#create()의 입력값 => 5
        //CommentService#create()의 입력값 => CommentDto(id=null, ...)
        for(Object obj : args){ // foreach문
            log.info("{}#{}의 입력 값 = > {}", className,methodName,obj);
        }
    }

    // 실행 시점 설정 : cut()에 지정된 대상 호출 성공 후!
    @AfterReturning(value ="cut()", returning = "returnObj")
    public void loggingReturnValue(JoinPoint joinPoint, // cut()의 대상 메소드
                                   Object returnObj){ //return 값

        //클래스명
        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();
        // 메소드명
        String methodName = joinPoint.getSignature()
                .getName();
        //반환 값 로깅
        //CommentService#create()의 반환 값 => CommentDto(id=10,...)
        log.info("{}#{}의 반환 값 => {}",className,methodName,returnObj);

    }
}
