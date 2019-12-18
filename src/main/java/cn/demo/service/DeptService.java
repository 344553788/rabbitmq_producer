package cn.demo.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import cn.demo.entity.Dept;

public interface DeptService {

	
	List<Dept> findAll();

	
	List<Dept> findAllById(Iterable<Long> ids);

	
	List<Dept> saveAll(Iterable<Dept> entities);

	
	Dept saveAndFlush(Dept entity);

	
	void deleteInBatch(Iterable<Dept> entities);

	
	void deleteAllInBatch();

	
	Dept getOne(Long id);

	
	 List<Dept> findAll(Example<Dept> example, Sort sort);
	
}
