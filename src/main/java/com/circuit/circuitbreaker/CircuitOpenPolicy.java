package com.circuit.circuitbreaker;

public interface CircuitOpenPolicy {
    boolean isOpened();
    void notifyBreak();
}
