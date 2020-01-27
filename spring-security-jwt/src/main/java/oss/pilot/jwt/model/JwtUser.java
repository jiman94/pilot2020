package oss.pilot.jwt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUser {

    private String userid;
    private String userNm;
    private String agentCd;
    private String roles;
    private boolean loginOk;
    private String expireDateTime;
    private String originTime;

}
