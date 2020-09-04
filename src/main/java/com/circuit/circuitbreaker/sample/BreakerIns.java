package com.circuit.circuitbreaker.sample;

import com.circuit.circuitbreaker.annotation.Breaker;
import com.circuit.circuitbreaker.base.CircuitBreakerBase;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
//@Breaker(fallback = "breakerInsFallback-class")
public class BreakerIns extends CircuitBreakerBase {
    @Breaker(fallback = "breakerInsFallback")
    public void breakerMethod(int i) {
        System.out.println("execute the " + i);
        try {
            Thread.sleep((long) (Math.random()*5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("execute breaker method ...");
    }
}
