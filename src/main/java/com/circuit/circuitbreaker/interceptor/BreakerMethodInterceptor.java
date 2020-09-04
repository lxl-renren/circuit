package com.circuit.circuitbreaker.interceptor;

import com.circuit.circuitbreaker.breaker.CircuitBreakRunner;
import com.circuit.circuitbreaker.breaker.CircuitBreaker;
import com.circuit.circuitbreaker.breaker.CircuitBreakerFallback;
import com.circuit.circuitbreaker.annotation.Breaker;
import com.circuit.circuitbreaker.base.CircuitBreakerBase;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BreakerMethodInterceptor implements MethodInterceptor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //System.out.println("method invocation");
        Breaker breaker = methodInvocation.getMethod().getDeclaredAnnotation(Breaker.class);
        if (breaker == null) {
            breaker = methodInvocation.getThis().getClass().getDeclaredAnnotation(Breaker.class);
        }
        if (breaker != null && CircuitBreakerBase.class.equals(methodInvocation.getThis().getClass().getSuperclass())) {
            //System.out.println("break method invocation");
            String breakInst = "circuitBreaker";
            if (!breaker.circuitBreaker().equals(""))
                breakInst = breaker.circuitBreaker();
            CircuitBreaker circuitBreaker = applicationContext.getBean(breakInst, CircuitBreaker.class);
            CircuitBreakerBase circuitBreakerBase = (CircuitBreakerBase) methodInvocation.getThis();
            circuitBreakerBase.setMethodInvocation(methodInvocation);
            //System.out.println(breaker.fallback());
            CircuitBreakerFallback fallback = applicationContext.getBean(breaker.fallback(), CircuitBreakerFallback.class);
            fallback.setMethodInvocation(methodInvocation);
            return CircuitBreakRunner.run(circuitBreaker, circuitBreakerBase, fallback);
        } else {
            return methodInvocation.proceed();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
