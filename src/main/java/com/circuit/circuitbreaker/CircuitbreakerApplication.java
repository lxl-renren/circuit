package com.circuit.circuitbreaker;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

//@SpringBootApplication
public class CircuitbreakerApplication {

	public static void main(String[] args) throws InterruptedException {
		//SpringApplication.run(CircuitbreakerApplication.class, args);
		CircuitBreakerConfig config = new CircuitBreakerConfig(new CircuitBreakerFallback<Integer>() {
			@Override
			public Object run() {
				return 2;
			}
		});
		CircuitBreaker breaker = new CircuitBreaker(config);
		Callable callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep((long) (Math.random() * 5000));
				return 1;
			}
		};
		List<Thread> threads = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			Thread tt = new Thread(new Runnable() {
				@Override
				public void run() {
					Integer ret = (Integer) CircuitBreakRunner.run(breaker, callable);
				}
			});
			tt.start();
			Thread.sleep(20);
			threads.add(tt);
		}
		for(Thread tt : threads) {
			tt.join();
		}
	}

}
