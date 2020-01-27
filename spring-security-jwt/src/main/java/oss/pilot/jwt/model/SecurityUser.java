package oss.pilot.jwt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SecurityUser {

    private String username;
    private String password;
    private Set<Role> roles;
}
