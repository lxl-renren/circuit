package com.circuit.circuitbreaker.advisor;

import com.circuit.circuitbreaker.annotation.Breaker;
import com.circuit.circuitbreaker.interceptor.BreakerMethodInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class BreakerAdvisor extends AbstractPointcutAdvisor {
    private static final long serialVersionUID = 1L;

    @Autowired
    private BreakerMethodInterceptor interceptor;

    private final StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            //System.out.println(method);

            boolean ret = method.isAnnotationPresent(Breaker.class) || targetClass.isAnnotationPresent(Breaker.class);
            //System.out.println(method.getDeclaredAnnotations());
            if (method.toString().equals("public void com.circuit.circuitbreaker.sample.BreakerIns.breakerMethod()")){
//                System.out.println("matched");
//                System.out.println(method.getDeclaredAnnotations().length);
//                System.out.println(method.getDeclaredAnnotations()[0].toString());
               // System.out.println(ret);
            }
            return false;
        }
    };

    //AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(Breaker.class, false);

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.interceptor;
    }
}
