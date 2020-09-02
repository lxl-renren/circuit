package com.circuit.circuitbreaker;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultCircuitBreakerPolicy implements  CircuitBreakerPolicy {
    private int failCount;
    private Queue<Boolean> window;
    private int fallThr = 10;
    private int windowSize = 20;
    private Boolean front;

    public DefaultCircuitBreakerPolicy() {
        window = new LinkedList<Boolean>();
    }

    @Override
    public synchronized boolean isNeedBreak() {
        if (failCount >= fallThr)
            return true;
        return false;
    }

    @Override
    public synchronized void setBreak() {
        window.clear();
        front = null;
        failCount = 0;
    }

    @Override
    public synchronized void setCallResult(boolean result) {
        window.offer(result);
        if (!result)
            failCount++;
        if (window.size() > windowSize) {
            front = window.poll();
            //队首也是失败的
            if (!front)
                failCount--;
        }
        System.out.println(failCount + " " + result + " " + front);
    }
}
