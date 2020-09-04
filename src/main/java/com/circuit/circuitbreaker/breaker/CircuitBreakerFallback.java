package com.circuit.circuitbreaker.breaker;


import org.aopalliance.intercept.MethodInvocation;

public abstract class CircuitBreakerFallback<V> {
    protected MethodInvocation methodInvocation;

    public void setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    public abstract  <V> V run();
}
