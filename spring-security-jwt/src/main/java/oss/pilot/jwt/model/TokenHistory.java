package oss.pilot.jwt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenHistory {
    String token;
    String useYn;
    String usrId;
    long createTime;
    long limitTime;
}
