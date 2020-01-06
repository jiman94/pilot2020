package oss.pilot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oss.pilot.account.Account;
import oss.pilot.account.AccountRepository;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class SpringBootDockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDockerApplication.class, args);
    }


    @Autowired
    private AccountRepository repository;

    @Bean
    CommandLineRunner runner() {
        return args -> Arrays.asList(
                new Account(1L,"wonwoo","wonwoo@test.com"),
                new Account(2L,"kevin","kevin@test.com")
        ).forEach(repository::save);
    }
}
