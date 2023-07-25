package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static java.lang.reflect.Proxy.newProxyInstance;

public class JDkProxyTest {
    private static final Logger log = LoggerFactory.getLogger(JDkProxyTest.class);
    public static void main(String[] args) {
        var calculator = new CalculatorImpl();
        Calculator proxyInstance = (Calculator) newProxyInstance(LoggingInvocationHandler.class.getClassLoader()
                , new Class[]{Calculator.class}
                , new LoggingInvocationHandler(calculator));
        var result = proxyInstance.add(1,2);
        log.info("add->{}",result);
    }
}
class CalculatorImpl  implements Calculator{
    @Override
    public int add(int a, int b) {
        return a+b;
    }
}
interface Calculator{
    int add(int a,int b);
}

class LoggingInvocationHandler implements InvocationHandler{
    private static final Logger log = LoggerFactory.getLogger(LoggingInvocationHandler.class);
    private final Object target;
    public LoggingInvocationHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("{} executed in {}",method.getName(),target.getClass().getCanonicalName());
        return method.invoke(target,args);
    }
}
