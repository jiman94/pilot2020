package oss.pilot.redis;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import oss.pilot.redis.point.Point;
import oss.pilot.redis.point.PointRedisRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
class RedisApplicationTests {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @After
    public void tearDown() throws Exception{
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회기능() {
        // given
        String id = "minho";
        LocalDateTime refreshTime = LocalDateTime.of(2020,1,9,0,0);
        Point point = Point.builder()
                        .id(id)
                        .amount(1000L)
                        .refreshTime(refreshTime)
                        .build();
        // when
        pointRedisRepository.save(point);

        // then
        Point savedPoint = pointRedisRepository.findById(id).get();
        //assertThat(savedPoint.getAmount(), is(equalTo(1000L)));
        //assertThat(savedPoint.getRefreshTime(), is(equalTo(refreshTime)));
    }

}