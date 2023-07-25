package com.example.demo.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {
    @Pointcut("execution(public * com.example.demo..*Service.*(..))")
    public void servicePublicMethodPointcut(){};

    @Pointcut("execution(* com.example.demo..*Repository.*(..))")
    public void repositoryMethodPointcut(){};

    @Pointcut("execution(* com.example.demo..*Repository.insert(..))")
    public void repositoryInsertMethodPointcut(){};
}
