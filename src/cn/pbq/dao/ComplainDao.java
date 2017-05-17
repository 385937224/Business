package cn.pbq.dao;

import java.util.List;

import cn.pbq.entity.Complain;

public interface ComplainDao extends BaseDao<Complain> {

	
	List<String>  findUserByDept(String dept);
}
