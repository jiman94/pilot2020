# Spring Rest Api 만들기 프로젝트

http://localhost:8080/helloworld/json

http://localhost:8080/swagger-ui.html
    
http://localhost:8080/helloworld/string

    

```build.gradle     

<swagger.version>3.0.0-SNAPSHOT</swagger.version>    
@EnableSwagger2WebFlux

io.springfox:springfox-swagger2
io.springfox:springfox-swagger-ui
io.springfox:springfox-spring-webflux
org.springframework.boot:spring-boot-starter-webflux
```


#### Boot2WithSwaggerApplication


```java

@EnableSwagger2WebFlux
@EnableWebFlux
@SpringBootApplication
public class Boot2WithSwaggerApplication {

  public static void main(String[] args) {
    SpringApplication.run(Boot2WithSwaggerApplication.class, args);
  }

}
```

#### 


```java
@Configuration
public class SwaggerConfig {

  @Bean
  public Docket swaggerApi() {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerApiInfo()).select() 
 .apis(RequestHandlerSelectors.basePackage("io.github.gsealy.boot2withswagger.controller")) 
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo swaggerApiInfo() {
    return new ApiInfoBuilder().title("webflux-swagger2 API doc")
        .description("how to use this")
        .termsOfServiceUrl("https://github.com/Gsealy")
        .contact(new Contact("Gsealy", "https://gsealy.github.io", "gsealy@gmail.com")) 
        .version("1.0")
        .build();
  }
}
```


#### Controller

```java
@RestController
@RequestMapping("/apis")
@Api(value = "Swagger test Controller", description = "learn how to use swagger")
public class SwaggerController {

  @GetMapping
  @ApiOperation(value = "GET Method", response = String.class)
  public Mono<String> get() {
    return Mono.just("this is GET Met" + "hod.");
  }

  @PostMapping
  @ApiOperation(value = "POST Method", response = String.class)
  public Mono<String> post() {
    return Mono.just("this is POST Method.");
  }

  @PutMapping
  @ApiOperation(value = "PUT Method", response = String.class)
  public Mono<String> put() {
    return Mono.just("this is PUT Method.");
  }

  @DeleteMapping
  @ApiOperation(value = "DELETE Method", response = String.class)
  public Mono<String> delete() {
    return Mono.just("this is DELETE Method.");
  }

}
```

##### 스프링 시큐리티 설정 

```
//패턴을 이용해서 접근막음
@Override 
protected void configure(HttpSecurity http) throws Exception {}

//위에서 적용한 패턴의 제외한 접근 - 정적리소스, HTML 파일 허용처리
@Override 
public void configure(WebSecurity web) throws Exception {}
HttpSecurity, WebSecurity 차이가 궁금했는데 


HttpSecurity 패턴은 보안처리
WebSecurity 패턴은 보안예외처리(정적리소스, HTML)


인증(Authentication)
권한부여(Authorization)

인증이란?
유저가 누구인지 확인하는 절차, 회원가입하고 로그인 하는 것.

인가란?
유저에 대한 권한을 허락하는 것.





#### build.gradle 

```yaml 
// 1안 lettuce 
    compile('org.springframework.boot:spring-boot-starter-data-redis')
    compile group: 'it.ozimov', name: 'embedded-redis', version: '0.7.2'
```

####    

```java
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {
    private final RedisProperties redisProperties;

    // lettuce
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
    
    
}


```

```java
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("spring.redis")
@Component
public class RedisProperties {
    private String host;
    private int port;
}
```

```java
@RequiredArgsConstructor
@EnableCaching
@Configuration
public class RedisCacheConfig {

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        // 캐시 default 유효시간 설정
        cacheConfigurations.put(CacheKey.USER, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.USER_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.BOARD, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.BOARD_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.POST, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.POST_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.POSTS, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.POST_EXPIRE_SEC)));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
```
 
#### application-local.yml

```yaml 
#debug: true # Enable debug logs.
#trace: true # Enable trace logs.
#java -Dspring.profiles.active=local -jar pilot-1.0.jar

#spring.main.allow-bean-definition-overriding=true

spring:
 main:
  allow-bean-definition-overriding: true
#  web-application-type: none

---
spring:
  profiles:
    active: local
    
logging: 
  config: classpath:logback/logback-${spring.profiles.active}.xml  

---  
spring:  
  datasource:
    url: jdbc:h2:tcp://localhost/~/test
    driver-class-name: org.h2.Driver
    username: sa
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    properties.hibernate:
      hbm2ddl.auto: update
      format_sql: true
    showSql: true
    generate-ddl: true
  url:
    base: http://localhost:8080
  redis:
    host: localhost
    port: 6379
    password:
  cache: 
    type: redis
```
   
    
### 0. 개요

- SpringBoot2 framework 기반에서 RESTful api 


### 1. 개발환경

- Java 8~11
- SpringBoot 2.x
- SpringSecurity 5.x
- JPA, H2


### 2. 프로젝트 실행

- H2 database 설치
    - https://www.h2database.com/html/download.html
    
    
### 3. DDL
create table user (
       msrl bigint not null auto_increment,
        name varchar(100) not null,
        password varchar(100),
        provider varchar(100),
        uid varchar(50) not null,
        primary key (msrl)
    ) engine=InnoDB;
    
create table user_roles (
       user_msrl bigint not null,
        roles varchar(255)
    ) engine=InnoDB;
    
    
alter table user 
add constraint UK_a7hlm8sj8kmijx6ucp7wfyt31 unique (uid);

alter table user_roles 
       add constraint FKel3d4qj41g0sy1mtp4sh055g7 
       foreign key (user_msrl) 
       references user (msrl);
       
       
### 개발 참조 

- SpringBoot2로 Rest api 만들기(1) – Intellij Community에서 프로젝트생성
    - Document
        - https://daddyprogrammer.org/post/19/spring-boot1-start-intellij/
- SpringBoot2로 Rest api 만들기(2) – HelloWorld
    - Document
        - https://daddyprogrammer.org/post/41/spring-boot2-helloworld/
- SpringBoot2로 Rest api 만들기(3) – H2 Database 연동
    - Document
        - https://daddyprogrammer.org/post/152/spring-boot2-h2-database-intergrate/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/h2
- SpringBoot2로 Rest api 만들기(4) – Swagger API 문서 자동화
    - Document
        - https://daddyprogrammer.org/post/313/swagger-api-doc/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/swagger
- SpringBoot2로 Rest api 만들기(5) – API 인터페이스 및 결과 데이터 구조 설계
    - Document
        - https://daddyprogrammer.org/post/404/spring-boot2-5-design-api-interface-and-data-structure/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/api-structure
- SpringBoot2로 Rest api 만들기(6) – ControllerAdvice를 이용한 Exception처리
    - Document 
        - https://daddyprogrammer.org/post/446/spring-boot2-5-exception-handling/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/controller-advice
- SpringBoot2로 Rest api 만들기(7) – MessageSource를 이용한 Exception 처리
    - Document
        - https://daddyprogrammer.org/post/499/springboot2-message-exception-handling-with-controlleradvice/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/messagesource
- SpringBoot2로 Rest api 만들기(8) – SpringSecurity를 이용한 인증 및 권한부여
    - Document
        - https://daddyprogrammer.org/post/636/springboot2-springsecurity-authentication-authorization/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/security
- SpringBoot2로 Rest api 만들기(9) – Unit Test
    - Document
        - https://daddyprogrammer.org/post/938/springboot2-restapi-unit-test/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/junit-test
- SpringBoot2로 Rest api 만들기(10) – Social Login kakao
    - Document
        - https://daddyprogrammer.org/post/1012/springboot2-rest-api-social-login-kakao/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/social-kakao
- SpringBoot2로 Rest api 만들기(11) – profile을 이용한 환경별 설정 분리
    - Document
        - https://daddyprogrammer.org/post/2421/springboot2-seperate-environment-by-profile/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/seperate-profile
 - SpringBoot2로 Rest api 만들기(12) – Deploy & Nginx 연동 & 무중단 배포 하기
    - Document
        - https://daddyprogrammer.org/post/2445/springboot2-blue-green-deploy-nginx/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/gracefullyshutdown
  - SpringBoot2로 Rest api 만들기(13) – Jenkins 배포(Deploy) + Git Tag Rollback
    - Document
        - https://daddyprogrammer.org/post/2697/springboot2-jenkins-deploy-gittag-rollback/
  - SpringBoot2로 Rest api 만들기(14) – 간단한 JPA 게시판(board) 만들기
    - Document
        - https://daddyprogrammer.org/post/2695/springboot2-simple-jpa-board/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/feature/board
  - SpringBoot2로 Rest api 만들기(15) – Redis로 api 결과 캐싱(Caching)처리
    - Document
        - https://daddyprogrammer.org/post/3870/spring-rest-api-redis-caching/
    - Git
        - https://github.com/codej99/SpringRestApi/tree/cache-data-redis
