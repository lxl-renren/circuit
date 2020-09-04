package com.circuit.circuitbreaker;

import com.circuit.circuitbreaker.sample.BreakerIns;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE, classes = CircuitbreakerApplication.class)
public class BreakerTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BreakerIns breakerIns;

    @Test
    public void test() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            int finalI = i;
            Thread tt = new Thread(new Runnable() {
				@Override
				public void run() {
                    breakerIns.breakerMethod(finalI);
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

