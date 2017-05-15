package cn.pbq.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.pbq.dao.InfoDao;
import cn.pbq.entity.Info;
import cn.pbq.service.InfoService;

@Service
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService {

	private InfoDao infoDao;
	@Resource
	public void setInfoDao(InfoDao infoDao) {
		this.infoDao = infoDao;
		super.setBaseDao(infoDao);
	}
	
	

}
