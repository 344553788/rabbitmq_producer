package cn.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	/**
	 * 序列號uuid
	 */
	private static final long serialVersionUID = -8774498438199983383L;

	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(columnDefinition = "INT(11) UNSIGNED")
	protected  Long id;

	@Version
	@Column(columnDefinition = "INT(11) UNSIGNED")
	protected Long version;
	
	  /** 创建时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP",insertable=false)
    private Date createTime;

    /** 修改时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",insertable=false,updatable = false)
    private Date updateTime;
    
    
    @Column(columnDefinition = "INT(11) UNSIGNED DEFAULT 1",nullable = false)
    private Integer isActive;
    
    @Column(columnDefinition = "INT(11) UNSIGNED DEFAULT 0",nullable = false)
    private Integer isDeleted;

}
