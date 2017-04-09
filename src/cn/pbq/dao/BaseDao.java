package cn.pbq.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {

	void save(T temp);
	
	void delete(Serializable id);
	
	void update(T temp);
	
	T findById(Serializable id);
	
	List<T> getAll();
	
}
