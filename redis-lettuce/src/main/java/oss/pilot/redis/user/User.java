package oss.pilot.redis.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    

	private static final long serialVersionUID = -7939920725893722993L;
	private String id;
    private String name;
    private Long salary;
}