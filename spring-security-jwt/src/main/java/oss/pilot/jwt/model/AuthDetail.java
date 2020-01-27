package oss.pilot.jwt.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthDetail {
    private JwtUser jwtUser;
    private String accessToken;
    private String refreshToken;
    private String serverAccessTime;
    //private List<MenuDto> menuList;
    private boolean refreshYn;
}
