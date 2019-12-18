package cn.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cn.demo.entity.Dept;


@Repository
public interface DeptDao extends JpaRepository<Dept,Long>,CrudRepository<Dept, Long>{
	
}