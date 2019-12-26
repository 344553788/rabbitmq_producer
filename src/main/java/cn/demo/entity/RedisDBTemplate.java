package cn.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * CREATE TABLE IF NOT EXISTS redis_table (
    id INT(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    host VARCHAR(56),
    port INT(10),
    db_index INT(4),
    user_name VARCHAR(64),
    pass VARCHAR(255),
    min_idle INT(4),
    max_idle INT(4),
    max_active INT(4),
    max_wait INT(10),
    time_out INT(10)
); 


 * @author User
 *
 */
@Data
@Entity
@Table(name="redis_table")
@EqualsAndHashCode(callSuper = true)
public class RedisDBTemplate extends BaseEntity {

	private static final long serialVersionUID = -9066443253844093736L;
    /** ip地址  @Column(insertable = false,columnDefinition = "int default 1")*/
    @Column(nullable=false, columnDefinition = "varchar(64) default 'localhost'")
    private String host;

    /** 端口号 */
    @Column(nullable=false,columnDefinition = "int(10) default 6379")
    private Integer port;

    /** 数据库索引号 */
    @Column(nullable=false,columnDefinition = "int(4) default 0")
    private Integer dbIndex;

    /** 密码 */
    private String pass;
    
    
    /** 最小空闲数 */
    @Column(nullable=false,columnDefinition = "int(4) default 0")
    private Integer minIdle = 0;
    
    /** 最大空闲数 */
    @Column(nullable=false,columnDefinition = "int(4) default 8")
    private Integer maxIdle = 8;
    
    @Column(nullable=false,columnDefinition = "int(4) default 8")
    private Integer maxActive;
    
    /** 最大等待时间 */
    @Column(nullable=false,columnDefinition = "int(10) default -1")
    private Integer maxWait = -1;
    
    @Column(nullable=false,columnDefinition = "int(10) default 6000")
    private Integer timeOut = 6000;
}
