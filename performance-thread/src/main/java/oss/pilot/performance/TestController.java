package oss.pilot.performance;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	private Logger logger = LoggerFactory.getLogger(TestController.class);
	private ScheduledExecutorService scheduledExecutorService;

	public TestController() {
		scheduledExecutorService = Executors.newScheduledThreadPool(8);
	}

	@PostConstruct
	public void test() { // 수행시간이 긴 task (약 3초)
		Runnable task = () -> {
			logger.info("start");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("finish");
		};
		
		Runnable task2 = () -> { logger.info("1"); logger.info("1"); };

		
			
		// 0.1초마다 한번씩 task를 수행시켜주길 바라고 만듦
		scheduledExecutorService.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);
		scheduledExecutorService.scheduleAtFixedRate(task2, 0, 1000, TimeUnit.MILLISECONDS);

	}

}