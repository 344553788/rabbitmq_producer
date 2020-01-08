package cn.demo.protocal.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class RedisDBRequest implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4616859026616266360L;

	private Integer pageNum;
	
	private Integer pageSize;
}
