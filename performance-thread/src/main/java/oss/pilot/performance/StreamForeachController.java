package oss.pilot.performance;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamForeachController {

	private CopyOnWriteArrayList<Integer> list;
	private static int min = Integer.MAX_VALUE;

	@PostConstruct
	private void init() {
		list = new CopyOnWriteArrayList<>();
		// 랜덤 10만건
		Random random = new Random();
		for (int i = 0; i < 100000; i++) {
			list.add(random.nextInt());
		}
	}

	@GetMapping("/")
	public String performanceTest() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		list.stream().forEach(item -> {
			if (item < min) {
				min = item;
			}
		});
		stopWatch.stop();
		System.out.println("stream loop : " + stopWatch.getTotalTimeMillis() + "ms");
		stopWatch = new StopWatch();
		min = Integer.MAX_VALUE;
		stopWatch.start();
		list.forEach(item -> {
			if (item < min) {
				min = item;
			}
		});
		stopWatch.stop();
		System.out.println("for each : " + stopWatch.getTotalTimeMillis() + "ms");
		stopWatch = new StopWatch();
		min = Integer.MAX_VALUE;
		stopWatch.start();
		for (int item : list) {
			if (item < min) {
				min = item;
			}
		}
		stopWatch.stop();
		System.out.println("advanced for : " + stopWatch.getTotalTimeMillis() + "ms");

		stopWatch = new StopWatch();
		min = Integer.MAX_VALUE;
		stopWatch.start();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) < min) {
				min = list.get(i);
			}
		}

		stopWatch.stop();
		System.out.println("for : " + stopWatch.getTotalTimeMillis() + "ms");
		stopWatch = new StopWatch();
		min = Integer.MAX_VALUE;

		stopWatch.start();
		Collections.min(list);
		stopWatch.stop();
		System.out.println("collection : " + stopWatch.getTotalTimeMillis() + "ms");
		System.out.println("==========================================================");
		return "complete";
	}

}
