package cn.pbq.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.pbq.dao.ComplainDao;
import cn.pbq.entity.Complain;
import cn.pbq.service.ComplainService;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;


@Service
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService {


	private ComplainDao complainDao;
	@Resource
	public void setComplainDao(ComplainDao complainDao) {
		this.complainDao = complainDao;
		super.setBaseDao(complainDao);
	}
	
	
	@Override
	public List<String> findUserByDept(String dept) {
		return complainDao.findUserByDept(dept);
	}

	

}
