package cn.pbq.service;

import java.io.Serializable;
import java.util.List;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;

public interface BaseService<T> {

	void save(T temp);
	
	void delete(Serializable id);
	
	void update(T temp);
	
	T findById(Serializable id);
	
	List<T> getAll();
	
	//根据传入的HQL语句、占位符？对应的参数。执行 hql语句得到结果。
	List<T> findObjectByCondition(String sql,List<String> paremeterList);
	
	
	//条件查询，并加入了分页
	Page getPage(SqlUtil sqlUtil,int pageNumber,int pageSize);

}
