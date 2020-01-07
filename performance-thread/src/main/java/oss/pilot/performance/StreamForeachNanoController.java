package oss.pilot.performance;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamForeachNanoController {

	private CopyOnWriteArrayList<Integer> list;
	private static int min = Integer.MAX_VALUE;

	@PostConstruct
	private void init() {
		list = new CopyOnWriteArrayList<>();

		Random random = new Random();
		for (int i = 0; i < 100000; i++) {
			list.add(random.nextInt());
		}
	}

	@GetMapping("/")
	public String performanceTest() {

//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();

		long start = 0, end = 0;
		start = System.nanoTime();
		list.stream().forEach(item -> {
			if (item < min) {
				min = item;
			}
		});
		end = System.nanoTime();
		System.out.println("stream loop : " + String.format("%,d", (end - start)) + "ns");

		min = Integer.MAX_VALUE;
		start = System.nanoTime();
		list.forEach(item -> {
			if (item < min) {
				min = item;
			}
		});
		end = System.nanoTime();
		System.out.println("for each : " + String.format("%,d", (end - start)) + "ns");

		min = Integer.MAX_VALUE;
		start = System.nanoTime();
		for (int item : list) {
			if (item < min) {
				min = item;
			}
		}
		end = System.nanoTime();
		System.out.println("advanced for : " + String.format("%,d", (end - start)) + "ns");

		min = Integer.MAX_VALUE;
		start = System.nanoTime();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) < min) {
				min = list.get(i);
			}
		}
		end = System.nanoTime();
		System.out.println("for : " + String.format("%,d", (end - start)) + "ns");

		min = Integer.MAX_VALUE;
		start = System.nanoTime();
		Collections.min(list);
		end = System.nanoTime();
		System.out.println("collection : " + String.format("%,d", (end - start)) + "ns");
		System.out.println("==========================================================");

		return "complete";
	}

}
