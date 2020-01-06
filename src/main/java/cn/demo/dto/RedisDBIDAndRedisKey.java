package cn.demo.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RedisDBIDAndRedisKey  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3768407662225865755L;

	
	private Long id;
	
	private String redisKey;
	
	private Long time;
}
