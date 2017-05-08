package cn.pbq.service;

import java.io.Serializable;
import java.util.List;

import cn.pbq.entity.Role;

public interface BaseService<T> {

	void save(T temp);
	
	void delete(Serializable id);
	
	void update(T temp);
	
	T findById(Serializable id);
	
	List<T> getAll();
}
