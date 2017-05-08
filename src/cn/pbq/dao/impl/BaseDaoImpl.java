package cn.pbq.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.pbq.dao.BaseDao;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	/**
	 * 1.注解方式：不能这样sessionFactory的直接注入变量。因为父类BaseDaoImpl 继承了  HibernateDaoSupport。
	 * 原因是HibernateDaoSupport是抽象类,且方法都是final修饰的, 
	 * 这样就不能为其注入sessionFactory或者hibernateTemplate。 
	 * 2.若使用xml配置的话,就可以直接给HibernateDaoSupport注入. 
	 */
//	@Resource(name="sessionFactory")
//	private SessionFactory sessionFactory;
	
	/**
	 * 既用HibernateDaoSupport又用注解，只能这么写。
	 * 这注入可以写在BaseDapImpl中，这样其他继承BaseDaoImpl的dao类就不需要反复注入。
	 */
    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory){ 
    	//1.这里先 调用的BaseDaoImpl对象的set方法，
    	//2.BaseDaoImpl对象又继承 HibernateDaoSupport。继续调用的是超级父类的set方法。
         super.setSessionFactory(sessionFactory);  
    } 


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
