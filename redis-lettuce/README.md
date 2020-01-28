# Spring Boot & Redis
## Lettuce

http://localhost:8080/aa/user/add/a/ryu

http://localhost:8080/aa/user/all


@RequestMapping("/aa/user")
@GetMapping("/all")


Lettuce는 Netty (비동기 이벤트 기반 고성능 네트워크 프레임워크) 기반의 Redis 클라이언트입니다.
비동기로 요청을 처리하기 때문에 고성능을 자랑합니다.


```java
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

```yaml
dependencies {
    compile group: 'it.ozimov', name: 'embedded-redis', version: '0.7.2'
    // lettuce
    compile ('org.springframework.boot:spring-boot-starter-data-redis')

    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```
