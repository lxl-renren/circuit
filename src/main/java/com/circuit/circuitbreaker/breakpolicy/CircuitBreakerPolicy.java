package com.circuit.circuitbreaker.breakpolicy;

public interface CircuitBreakerPolicy {
    boolean isNeedBreak();
    void setBreak();
    void setCallResult(boolean result);
}
