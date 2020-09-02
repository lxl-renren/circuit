package com.circuit.circuitbreaker;

public interface CircuitBreakerPolicy {
    boolean isNeedBreak();
    void setBreak();
    void setCallResult(boolean result);
}
