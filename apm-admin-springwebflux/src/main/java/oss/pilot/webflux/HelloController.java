package oss.pilot.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello WebFlux!!!";
	}

	@GetMapping("/hello/mono")
	public Mono helloMono() {
		return Mono.just("Hello Mono!!!");
	}

    @GetMapping("/hello/flux")
    public Flux helloFlux() {
        return Flux.just(
            "Hello Flux!!!",
            "Hello Reactor 3!!!",
            "Hello Reactive Streams!!!");
    }

	public static void main(String[] args) {
		SpringApplication.run(HelloController.class, args);
	}
}