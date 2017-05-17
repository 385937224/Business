package cn.pbq.action;

import java.util.List;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.pbq.constant.Constant;
import cn.pbq.entity.Complain;
import cn.pbq.entity.Info;
import cn.pbq.entity.User;
import cn.pbq.service.ComplainService;
import cn.pbq.service.InfoService;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;

public class HomeAction extends ActionSupport{
	
	@Resource
	private InfoService infoService;
	@Resource
	private ComplainService complainService;
	
	private List<Info> infoList;
	private List<Complain> compList;
	private Info info;
	private Complain complain;
	
	
	//整个系统的首页
	public String home() {	
		
		SqlUtil sqlUtil = new SqlUtil();
		sqlUtil.setFrom(Info.class.getName());
		sqlUtil.setOrderBy("createTime", "asc");
		Page page = infoService.getPage(sqlUtil, 0, 4);
		infoList = page.getList();
		
		SqlUtil sqlUtil2 =new SqlUtil();
		sqlUtil2.setFrom(Complain.class.getName());
		User user =(User) ActionContext.getContext().getSession().get("user");
		sqlUtil2.setWhere("complainant=?", user.getNickName());
		sqlUtil2.setOrderBy("compTime", "asc");
		page = complainService.getPage(sqlUtil2, 0, 2);
		compList = page.getList();
		
		return "home";
	}
		
	
	
	public String complainAddUI(){		
		ActionContext.getContext().getContextMap().put("deptList", Constant.DEPT_ARRAY);
		return "complainAddUI";
	}
	

	
	public String infoViewUI(){		
		
		info=infoService.findById(info.getInfoId());
		return "infoViewUI";
	}
	
	public String complainViewUI(){		
		System.out.println(complain.getCompId());
		complain=complainService.findById(complain.getCompId());
		return "complainViewUI";
	}

	
	//这几个是FW子系统的框架页面。
	public String fwframe() {		
		return "fwframe";
	}
	public String fwtop() {		
		return "fwtop";
	}
	public String fwleft() {		
		return "fwleft";
	}



	public List<Info> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public List<Complain> getCompList() {
		return compList;
	}
	public void setCompList(List<Complain> compList) {
		this.compList = compList;
	}
	public Complain getComplain() {
		return complain;
	}
	public void setComplain(Complain complain) {
		this.complain = complain;
	}
	
	
	
	
}
