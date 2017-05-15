package cn.pbq.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.pbq.constant.Constant;
import cn.pbq.entity.Info;
import cn.pbq.entity.User;
import cn.pbq.service.InfoService;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;

public class InfoAction extends BaseAction {

	@Resource
	private InfoService infoService;
	
	
	/****************************/
	private Info info;
	private String[] selectedRow;
	
	
	private List<Info> infoList; //如果 设这个全局成员变量，是为了方便前端struts标签自动回显。

	//优化listUI()。
	public String listUI() {
		request.put("infoTypeMap", Constant.INFO_TYPE_MAP);
		SqlUtil sql = new SqlUtil();
		sql.setFrom(Info.class.getName());
		if(info!=null){
			if(StringUtils.isNotBlank(info.getTitle())){
				sql.setWhere("title like ?", "%"+info.getTitle()+"%");
			}
		}
		
		//if(pageNumber<1)pageNumber=1;//每个action中 都设置一次，好容易忘记。
		Page page = infoService.getPage(sql, pageNumber, 3);
		pageTotal=page.getPageTotal();
		sum=page.getSum();
		pageNumber=page.getPageNumber();
	
		request.put("list", page.getList());//struts标签用  #request.list来取。  “#”和“域对象”  必须写上，不然取不到。
		return "listUI";
	}
	
	
//	public String listUI() {
//		request.put("infoTypeMap", Constant.INFO_TYPE_MAP);
//		List<Info> list = infoList = infoService.getAll();
//		request.put("list", list);//struts标签用  #request.list来取。  “#”和“域对象”  必须写上，不然取不到。
//		return "listUI";
//	}
	
	
	public String addUI() {
		request.put("infoTypeMap", Constant.INFO_TYPE_MAP);	
		return "addUI";
	}
	
	
	public String add() {
		info.setCreateTime(new Date());
		info.setState("1");
		//其实可以在前台sessionaScope中取。如果取不到，这里再取。		
		if(StringUtils.isBlank(info.getCreator())){
			User user =(User) ActionContext.getContext().getSession().get(Constant.USER);
			info.setCreator(user.getNickName());
		}
		
		infoService.save(info);
		return "listUIAction";
	}
	
	
	public String editUI() {
		searchValue=info.getTitle();
		
		request.put("infoTypeMap", Constant.INFO_TYPE_MAP);	
		info = infoService.findById(info.getInfoId());
		return "editUI";
	}
	
	public String edit() {
		infoService.update(info);
		return "listUIAction";
	}
	
	
	public String delete() {
		searchValue=info.getTitle();
		infoService.delete(info.getInfoId());
		return "listUIAction";
	}

	
	public String deleteAll() {
		searchValue=info.getTitle();
		if(selectedRow!=null){
			for (String infoId : selectedRow) {
				//批量删除，hibernate应该有个对应批量删的方法，好像batch**。与这里delete() 区别在于 ：效率的问题。
				infoService.delete(infoId);
			}	
		}
		return "listUIAction";
	}
	
	public void changeState() throws Exception{
		//update，更新是整个对象。 在info表的记录 全部字段信息都更新。
		//不能直接用update(info)，因为这次请求，info对象只有infoID和state有值。其他属性为null。直接更新。
		//会把其他值（数据库的其他字段）全变为空。我们要单独值对state 字段设置。
//		infoService.update(info);
		
		Info infoTemp = infoService.findById(info.getInfoId());
		infoTemp.setState(info.getState());
		infoService.update(infoTemp);
		
	
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter writer = response.getWriter();
		writer.write("asdasdasda");
		writer.flush();
		writer.close();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public List<Info> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}


	
	
	
	
	
	
	
	
	
	
}
