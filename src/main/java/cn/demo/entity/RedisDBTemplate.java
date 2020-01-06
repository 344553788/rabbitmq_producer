package cn.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@Table(name="redis_db_template")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RedisDBTemplate extends BaseEntity {

	private static final long serialVersionUID = -1560228272565067812L;

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
    private Integer minIdle;
    
    /** 最大空闲数 */
    @Column(nullable=false,columnDefinition = "int(4) default 8")
    private Integer maxIdle;
    
    @Column(nullable=false,columnDefinition = "int(4) default 8")
    private Integer maxActive;
    
    /** 最大等待时间 */
    @Column(nullable=false,columnDefinition = "bigint(20) default -1")
    private Long maxWait;
    
    @Column(nullable=false,columnDefinition = "bigint(20) default 6000")
    private Long timeOut;
    
    @Column(nullable=false,columnDefinition = "bigint(20) default 10000")
    private Long shutdownTimeOut;
}
