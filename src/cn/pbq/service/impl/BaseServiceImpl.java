package cn.pbq.service.impl;

import java.io.Serializable;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import cn.pbq.dao.BaseDao;
import cn.pbq.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {

	
	private BaseDao<T> baseDao;
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

//	根本不需要用到具体的泛型类。
//	private Class<T> clazz;
//	private String clazzName;
//	public BaseServiceImpl() {
//		ParameterizedType genericSuperclass =(ParameterizedType) this.getClass().getGenericSuperclass();
//		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
//		clazz =(Class<T>) actualTypeArguments[0];
//		clazzName=clazz.getName();
//	}
	
	@Override
	public void save(T temp) {
		baseDao.save(temp);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	@Override
	public void update(T temp) {
		baseDao.update(temp);
	}

	@Override
	public T findById(Serializable id) {
		return baseDao.findById(id);
	}

	@Override
	public List<T> getAll() {
		return baseDao.getAll();
	}

	
	
}
