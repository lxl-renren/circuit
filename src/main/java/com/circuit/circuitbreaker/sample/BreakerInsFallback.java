package com.circuit.circuitbreaker.sample;

import com.circuit.circuitbreaker.breaker.CircuitBreakerFallback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BreakerInsFallback extends CircuitBreakerFallback {
    @Override
    public Object run() {
        System.out.println("execute fallback. arguments: ");
        for(Object arg : methodInvocation.getArguments()) {
            System.out.println(arg);
        }
        System.out.println("method name");
        System.out.println(methodInvocation.getMethod());
        return null;
    }
}
