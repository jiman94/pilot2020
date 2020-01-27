package oss.pilot.jwt.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVo {

    private Integer userseq;
    private String userid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userpw;
    private String username;
    private String email;
    private String regdate;
    private String updatedate;
    private Set<Role> roles;
    
    
}
