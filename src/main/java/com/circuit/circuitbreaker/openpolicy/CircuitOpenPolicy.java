package com.circuit.circuitbreaker.openpolicy;

public interface CircuitOpenPolicy {
    boolean isOpened();
    void notifyBreak();
}
