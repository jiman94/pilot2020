package oss.pilot.redis.point;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;

@Getter
@RedisHash("point")
public class Point implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 711382273116052596L;
	@Id
    private String id;
    private Long amount;
    private LocalDateTime refreshTime;

    @Builder
    public Point(String id, Long amount, LocalDateTime refreshTime){
        this.id = id;
        this.amount = amount;
        this.refreshTime = refreshTime;
    }

    public void refresh(long amount, LocalDateTime refreshTime){
        if(refreshTime.isAfter(this.refreshTime)){
            this.amount = amount;
            this.refreshTime = refreshTime;
        }
    }
}