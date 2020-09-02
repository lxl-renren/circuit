package com.circuit.circuitbreaker;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
@Data
@Setter
public class CircuitBreakerConfig {
    private CircuitBreakerFallback circuitBreakerFallback;
    private int threadCount = 12;
    private ExecutorService executor;
    private long timeout = 1000;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    public  CircuitBreakerConfig(CircuitBreakerFallback circuitBreakerFallback) {
        this.circuitBreakerFallback = circuitBreakerFallback;
        executor = Executors.newFixedThreadPool(threadCount);
    }
}
