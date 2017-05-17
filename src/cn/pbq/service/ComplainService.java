package cn.pbq.service;

import java.util.List;

import cn.pbq.entity.Complain;


public interface ComplainService extends BaseService<Complain>{

	
	List<String>  findUserByDept(String dept);
}
