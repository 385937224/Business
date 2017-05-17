package cn.pbq.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.pbq.dao.ComplainDao;
import cn.pbq.entity.Complain;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;


@Repository
public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements ComplainDao {

	@Override
	public List<String> findUserByDept(String dept) {
		String hql="select nickName from User where dept=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, dept);
		List<String> list = query.list();

		return list;
	}


}
