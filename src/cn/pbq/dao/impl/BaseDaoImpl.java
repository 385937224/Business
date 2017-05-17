package cn.pbq.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.pbq.dao.BaseDao;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;

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
	
	@Override
	public List<T> findObjectByCondition(String sql,List<String> paremeterList){
		Query query = getSession().createQuery(sql);
		if(paremeterList!=null&& paremeterList.size()>0){
			for (int i = 0; i < paremeterList.size(); i++) {
				query.setParameter(i, paremeterList.get(i));
			}
		}

		return query.list();
	}


	@Override
	public Page getPage(SqlUtil sqlUtil, int pageNumber, int pageSize) {
		//因为该类中要用到多次session。所以提前抽取。不用每次都getSession(),这句子包含有创建的个线程的session对象意思。
		Session session = getSession();
		Query query = session.createQuery(sqlUtil.getSQL());
		List<Object> paremeterList = sqlUtil.getConditionParemeterList();
		if(paremeterList!=null&& paremeterList.size()>0){
			for (int i = 0; i < paremeterList.size(); i++) {
				query.setParameter(i, paremeterList.get(i));
			}
		}
		//得到分页的记录数目
		if(pageNumber<1)pageNumber=1;
		int firstResult=(pageNumber-1)*pageSize;
		query.setFirstResult(firstResult);//测试过这里若是天负数，则当作0处理。
		query.setMaxResults(pageSize);
		
		
		//把分页查出的list集合存到怕个对象中。
		Page page = new  Page();
		page.setList(query.list());
		
		
		//查询出总记录数量。这里是新的一次查询-------------要记得设置参数哦！！！！
		Query queryTotal = session.createQuery(sqlUtil.getCountSQL());
		if(paremeterList!=null&& paremeterList.size()>0){
			for (int i = 0; i < paremeterList.size(); i++) {
				queryTotal.setParameter(i, paremeterList.get(i));
			}
		}
		long uniqueResult =(long) queryTotal.uniqueResult();//是Long引用类型。不能直接转成int类型。执行先转成long基本数据类型。
		//把计算总页数所需的参数：总记录、每页大小 传入。由这个page对象内部自动算出 此次查询的总页数pageTotal。
		page.setPageTotalByCalculate(uniqueResult, pageSize);

		//传递。方便取出来回显。当前页码。
		page.setPageNumber(pageNumber);
		
		return page;
	}




}
