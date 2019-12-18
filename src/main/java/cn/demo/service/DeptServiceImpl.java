package cn.demo.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.demo.dao.DeptDao;
import cn.demo.entity.Dept;
import cn.demo.manager.DeptManager;

@Service
@Transactional
public class DeptServiceImpl implements DeptService{
	
	@Resource
	private DeptDao deptDao;
	
	@Autowired
	private DeptManager deptManager;

	@Override
	public List<Dept> findAll() {
		return deptDao.findAll();
	}

	@Override
	public List<Dept> findAllById(Iterable<Long> ids) {
		return deptDao.findAllById(ids);
	}

	@Override
	public List<Dept> saveAll(Iterable<Dept> entities) {
		return deptManager.saveAll(entities);
	}

	@Override
	public Dept saveAndFlush(Dept entity) {
		return deptDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<Dept> entities) {
		deptDao.deleteInBatch(entities);
		
	}

	@Override
	public void deleteAllInBatch() {
		deptDao.deleteAllInBatch();
	}

	@Override
	public Dept getOne(Long id) {
		return deptDao.getOne(id);
	}

	@Override
	public List<Dept> findAll(Example<Dept> example, Sort sort) {
		return deptDao.findAll(example,sort);
	}

}
