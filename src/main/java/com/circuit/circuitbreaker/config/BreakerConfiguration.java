package com.circuit.circuitbreaker.config;

import com.circuit.circuitbreaker.breaker.CircuitBreaker;
import com.circuit.circuitbreaker.breaker.CircuitBreakerConfig;
import com.circuit.circuitbreaker.annotation.Breaker;
import com.circuit.circuitbreaker.breakpolicy.CircuitBreakerPolicy;
import com.circuit.circuitbreaker.breakpolicy.DefaultCircuitBreakerPolicy;
import com.circuit.circuitbreaker.interceptor.BreakerMethodInterceptor;
import com.circuit.circuitbreaker.openpolicy.CircuitOpenPolicy;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BreakerConfiguration {
    @Autowired
    private BreakerMethodInterceptor interceptor;
    //public static final String traceExecution = "execution(* com.circuit..*.*(..))";

    @Bean
    CircuitBreaker circuitBreaker(CircuitBreakerConfig circuitBreakerConfig) {
        return new CircuitBreaker(circuitBreakerConfig);
    }

    @Bean
    CircuitBreakerConfig circuitBreakerConfig() {
        return new CircuitBreakerConfig();
    }

    @Bean
    CircuitBreakerPolicy circuitBreakerPolicy() {
        return new DefaultCircuitBreakerPolicy();
    }

    @Bean
    CircuitOpenPolicy circuitOpenPolicy(CircuitBreaker circuitBreaker) {
        return circuitBreaker.new DefaultCircuitOpenPolicy();
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        //类级别
        Pointcut cpc = new AnnotationMatchingPointcut(Breaker.class, true);
        ComposablePointcut pointcut = new ComposablePointcut(cpc);;
        //方法级别
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(Breaker.class);
        pointcut.union(mpc);

        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}
