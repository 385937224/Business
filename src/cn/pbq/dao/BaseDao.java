package cn.pbq.dao;

import java.io.Serializable;
import java.util.List;

import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;

public interface BaseDao<T> {

	void save(T temp);
	
	void delete(Serializable id);
	
	void update(T temp);
	
	T findById(Serializable id);
	
	//查询所有
	List<T> getAll();
	
	//条件查询
	List<T> findObjectByCondition(String sql,List<String> paremeterList);
	
	//条件查询，并加入了分页
	Page getPage(SqlUtil sqlUtil,int pageNumber,int pageSize);
	
}
