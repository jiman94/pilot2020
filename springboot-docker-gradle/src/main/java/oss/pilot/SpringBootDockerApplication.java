package oss.pilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootDockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDockerApplication.class, args);
    }


	/*
	 * @Autowired private AccountRepository repository;
	 * 
	 * @Bean CommandLineRunner runner() { return args -> Arrays.asList( new
	 * Account(1L,"wonwoo","wonwoo@test.com"), new
	 * Account(2L,"kevin","kevin@test.com") ).forEach(repository::save); }
	 */
}
