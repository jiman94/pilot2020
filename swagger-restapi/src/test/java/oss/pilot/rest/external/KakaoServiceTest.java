package oss.pilot.rest.external;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoServiceTest {

    @Autowired
    private KakaoService kakaoService;

    @Test @Ignore
    public void whenGetKakaoProfile_thenReturnProfile() {

        String accessToken = "xxxx";
        // given
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        // then
        assertNotNull(profile);
        assertEquals(profile.getId(), Long.valueOf(1066788171));
    }
}