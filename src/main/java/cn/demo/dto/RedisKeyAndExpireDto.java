package cn.demo.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RedisKeyAndExpireDto  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1387101195485091503L;

	
	private String key;
	
	private Long time;
}
