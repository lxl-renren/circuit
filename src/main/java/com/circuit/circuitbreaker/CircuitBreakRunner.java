package com.circuit.circuitbreaker;


import java.util.concurrent.*;

public class CircuitBreakRunner {
    public static <V> V run(CircuitBreaker circuitBreaker, Callable<V> callable) {
        CircuitBreakerConfig config = circuitBreaker.getCircuitBreakerConfig();
        if (circuitBreaker.isOpened()) {
            System.out.println("invoked is opened");
            return (V) config.getCircuitBreakerFallback().run();
        }

        Future<V> future = config.getExecutor().submit(callable);
        try {
            V ret = future.get(config.getTimeout(), config.getTimeUnit());
            circuitBreaker.accSuccess();
            return ret;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            //e.printStackTrace();
            circuitBreaker.accFailed();
            return (V) config.getCircuitBreakerFallback().run();
        }
    }
}


