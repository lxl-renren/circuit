package com.circuit.circuitbreaker.breaker;


import java.util.concurrent.*;

public class CircuitBreakRunner {
//    public static <V> V run(CircuitBreaker circuitBreaker, Callable<V> callable) {
//        return run(circuitBreaker, callable, circuitBreaker.getCircuitBreakerConfig().getCircuitBreakerFallback());
//    }

    public static <V> V run(CircuitBreaker circuitBreaker, Callable<V> callable, CircuitBreakerFallback fallback) {
        CircuitBreakerConfig config = circuitBreaker.getCircuitBreakerConfig();
        if (circuitBreaker.isOpened()) {
            System.out.println("circuit breaker is opened");
            return (V) fallback.run();
        }

        Future<V> future = config.getExecutor().submit(callable);
        try {
            V ret = future.get(config.getTimeout(), config.getTimeUnit());
            circuitBreaker.accSuccess();
            return ret;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            //e.printStackTrace();
            circuitBreaker.accFailed();
            return (V) fallback.run();
        }
    }
}


