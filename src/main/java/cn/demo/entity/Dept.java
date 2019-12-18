package cn.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="dept")
@EqualsAndHashCode(callSuper = true)
public class Dept extends BaseEntity {

	
	private static final long serialVersionUID = 6556397121891389956L;

    /** 数字,具有唯一性 */
    @Column(nullable=false,unique=true,name="`no`")
    private Long no;

    /** 部门名称 */
    @Column(unique=true,nullable=false)
    private String name;

    /** 部门管理的主键-id uuid */
    @Column(unique=true,nullable=false)
    private String manager;

    /** 部门描述 */
    private String description;

    /** 部门电话 */
    private String phone;
    
}