package com.circuit.circuitbreaker;


public interface CircuitBreakerFallback<V> {
    public <V> V run();
}
