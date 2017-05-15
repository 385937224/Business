package cn.pbq.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;

import cn.pbq.constant.Constant;
import cn.pbq.entity.Complain;
import cn.pbq.entity.Info;
import cn.pbq.entity.Replytocomp;
import cn.pbq.entity.User;
import cn.pbq.service.ComplainService;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;

public class ComplainAction extends BaseAction {

	@Resource
	private ComplainService complainService;
	
	
	
	/******************************/
	private Complain complain;
	private Replytocomp replyToComp;
	private String startTime;
	private String endTime;
	
	
	
	public String listUI() {
		SqlUtil sql = new SqlUtil();
		sql.setFrom(Complain.class.getName());

		Page page = complainService.getPage(sql, pageNumber, 3);
		pageTotal=page.getPageTotal();
		sum=page.getSum();
		pageNumber=page.getPageNumber();
	
		request.put("list", page.getList());//struts标签用  #request.list来取。  “#”和“域对象”  必须写上，不然取不到。
		return "listUI";
	}
	
	
	public String add() {
		complain.setCompTime(new Date());
		complain.setState("0");
		complainService.save(complain);

		return "home";
	}
	
	
	public String dealUI() {		
		complain=complainService.findById(complain.getCompId());
			
		return "dealUI";
	}
	
	public String deal() {
		complain=complainService.findById(complain.getCompId());
		return "listUIAction";
	}
	

	
	
	
	
	
	public Complain getComplain() {
		return complain;
	}
	public void setComplain(Complain complain) {
		this.complain = complain;
	}
	public Replytocomp getReplyToComp() {
		return replyToComp;
	}
	public void setReplyToComp(Replytocomp replyToComp) {
		this.replyToComp = replyToComp;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
}
