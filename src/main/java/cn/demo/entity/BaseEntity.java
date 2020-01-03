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
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(columnDefinition = "INT(11) UNSIGNED")
	protected Long id;

	@Version
	@Column(columnDefinition = "INT(11) UNSIGNED")
	protected Long version;
	
	  /** 创建时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="create_time", columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP",insertable=false)
    private Date createTime;

    /** 修改时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="update_time", columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",insertable=false,updatable = false)
    private Date updateTime;
    
    
    //int(4) unsigned zerofill NOT NULL DEFAULT '0000'
    @Column(name="is_active", columnDefinition = "INT UNSIGNED DEFAULT '1'",insertable = false, updatable = true,nullable = false)
    private Integer isActive;
    
    @Column(name="is_deleted", columnDefinition = "INT UNSIGNED DEFAULT '0'",insertable = false, updatable = true,nullable = false)
    private Integer isDeleted;

}
