package cn.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.demo.entity.RedisDBTemplate;


@Repository
public interface RedisDBTemplateDao extends JpaRepository<RedisDBTemplate,Long>,CrudRepository<RedisDBTemplate, Long>{
	
}