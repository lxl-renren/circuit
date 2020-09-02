package com.circuit.circuitbreaker;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.*;

@Getter
@Data
@Setter
public class CircuitBreaker {
    private volatile AtomicBoolean opened;
    private CircuitBreakerConfig circuitBreakerConfig;
    //控制熔断开启策略
    private CircuitBreakerPolicy circuitBreakerPolicy;
    //控制熔断打开后关闭策略
    private CircuitOpenPolicy circuitOpenPolicy;

    public  CircuitBreaker(CircuitBreakerConfig circuitBreakerConfig) {
        this.circuitBreakerConfig = circuitBreakerConfig;
        this.circuitBreakerPolicy = new DefaultCircuitBreakerPolicy();
        this.circuitOpenPolicy = new DefaultCircuitOpenPolicy();
        opened = new AtomicBoolean(false);
    }

    public  CircuitBreaker(CircuitBreakerConfig circuitBreakerConfig, CircuitBreakerPolicy circuitBreakerPolicy, CircuitOpenPolicy circuitOpenPolicy) {
        this.circuitBreakerConfig = circuitBreakerConfig;
        this.circuitBreakerPolicy = circuitBreakerPolicy;
        this.circuitOpenPolicy = circuitOpenPolicy;
        opened = new AtomicBoolean(false);
    }

    public boolean isOpened() {
        if (opened.get()) {
            //这个主要考虑可能通过部分流量，自定义实现
            return circuitOpenPolicy.isOpened();
        }
        return false;
    }

    public boolean setOpened() {
        return opened.compareAndSet(false, true);
    }

    public boolean setClosed() {
        System.out.println("set closed");
        return opened.compareAndSet(true, false);
    }

    public void accSuccess() {
        circuitBreakerPolicy.setCallResult(true);
    }

    public void accFailed() {
        circuitBreakerPolicy.setCallResult(false);
        if (circuitBreakerPolicy.isNeedBreak() && setOpened()) {
            System.out.println("set opened");
            circuitBreakerPolicy.setBreak();
            circuitOpenPolicy.notifyBreak();
        }
    }

    //默认实现到时间就重新打开熔断器。可自定义实现其他的打开方式，比如定期只打开部分流程
    public class DefaultCircuitOpenPolicy implements  CircuitOpenPolicy {
        private TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // task to close breaker
                CircuitBreaker.this.setClosed();
            }
        };
        private Timer timer = new Timer();
        private long openDuration = 60000;

        public DefaultCircuitOpenPolicy() {}

        public DefaultCircuitOpenPolicy(long openDuration) {
            this.openDuration = openDuration;
        }

        @Override
        public boolean isOpened() {
            return true;
        }

        @Override
        public void notifyBreak() {
            timer.schedule(task, openDuration);
        }
    }

}
