package cn.pbq.action;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

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
	private List<String> nickNameList;
	
	
	public String listUI() throws Exception{
		SqlUtil sql = new SqlUtil();
		sql.setFrom(Complain.class.getName());
		
		if(complain!=null){
			//第二第三次点击搜索的时候，complain不为null。因为前台的标签虽然空，但不是null，是""空字符串。也封装进title、State、endTime。
			//封装到了State变量中，值为""。${""==0}恒为真。bug。所有标签的EL表达式有点问题出现。
//			System.out.println("".equals(complain.getTitle()));//-----》第二次之后，有值且结果为真
			if(StringUtils.isNotBlank(complain.getTitle())){
				sql.setWhere("title like ?", "%"+complain.getTitle()+"%");
			}
			if(StringUtils.isNotBlank(complain.getState())){
				
				sql.setWhere("state = ?", complain.getState());
			}
		}
		
		if(StringUtils.isNotBlank(startTime)){
			
			/**
			 * hql中，传入的参数要与属性compTime是一样的类型。compTime是Date日期，
			 * 那么传入的startTime字符串要转化成日期。
			 * 用DateUtil
			 */
			Date parseDate = DateUtils.parseDate(startTime, new String[]{"yyyy-MM-dd","yyyy","yyyy-MM"});
			sql.setWhere("compTime > ?",parseDate );
		}
		if(StringUtils.isNotBlank(endTime)){
			Date parseDate = DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd","yyyy","yyyy-MM"});
			sql.setWhere("compTime < ?", parseDate);
		}

	
		
		Page page = complainService.getPage(sql, pageNumber, 3);
		pageTotal=page.getPageTotal();
		sum=page.getSum();
		pageNumber=page.getPageNumber();
	
		request.put("list", page.getList());//struts标签用  #request.list来取。  “#”和“域对象”  必须写上，不然取不到。
		return "listUI";
	}
	
	
	public String getUserJson(){

		nickNameList = complainService.findUserByDept(complain.getDeptOfPeople());				
		return "success";
	}
	
	
	
	public String add() {
		complain.setCompTime(new Date());
		complain.setState(Complain.STATE_UNDONE);
		complainService.save(complain);

		return "home";
	}
	
	
	public String dealUI() {		
		complain=complainService.findById(complain.getCompId());
			
		return "dealUI";
	}
	
	public String deal() {
		complain=complainService.findById(complain.getCompId());
		//如果状态没改成已受理，才进行修改。省得次次涂抹变量State的值
		if(!Complain.STATE_DONE.equals(complain.getState())){
			complain.setState(Complain.STATE_DONE);
		}
	
		
		if(replyToComp!=null){
			/**
			 * replyToComp表的compId对应的是complain变量，此时这个变量是null。级联保存的时候，导致表的compId为null。
			 * 所以这里要手动设置这个replyToComp的complain变量，这样级联保存时候，表的compId字段才会有。
			 */
			replyToComp.setComplain(complain);
			
			replyToComp.setReplyTime(new Date());
			//这里会因为懒加载缘故，报错说session已经关闭了（指 'Dao层与数据库 '的会话session）。
			//因为session关闭导致懒加载无法加载，replytocomps去不了值保错。
			complain.getReplytocomps().add(replyToComp);
		}

		complainService.update(complain);
		
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
	public List<String> getNickNameList() {
		return nickNameList;
	}
	public void setNickNameList(List<String> nickNameList) {
		this.nickNameList = nickNameList;
	}
	
	
	
}
