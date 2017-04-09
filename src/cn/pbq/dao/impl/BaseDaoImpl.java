package cn.pbq.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.pbq.dao.BaseDao;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	private Class<T> clazz;
	private String	clazzName;
	
	
	public BaseDaoImpl() {
		ParameterizedType parameterizedType =(ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		clazz =(Class<T>) actualTypeArguments[0];
		clazzName =clazz.getName();
	}
	

	@Override
	public void save(T temp) {
		getHibernateTemplate().save(temp);
	}

	@Override
	public void delete(Serializable id) {
		T findById = findById(id);
		getSession().delete(findById);
		
//		Query query = getSession().createQuery("delete from " + clazzName + " where id=?");
//		query.setParameter(0, id);
//		query.executeUpdate();

		
//		Query query = getSession().createQuery("delete from " + clazzName + " where id= "+ id );
//		int executeUpdate = query.executeUpdate();

	}

	@Override
	public void update(T temp) {
		getHibernateTemplate().update(temp);
	}

	@Override
	public T findById(Serializable id) {
		T t = getHibernateTemplate().get(clazz, id);
		return t;
	}

	@Override
	public List<T> getAll() {
		Query query = getSession().createQuery("from " + clazzName);
		return query.list();
	}



	
}
