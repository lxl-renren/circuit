package com.circuit.circuitbreaker.base;

import org.aopalliance.intercept.MethodInvocation;


import java.util.concurrent.Callable;

public class CircuitBreakerBase implements Callable {
    private MethodInvocation methodInvocation;


    public void setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object call() throws Exception {
        try {
            return methodInvocation.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
