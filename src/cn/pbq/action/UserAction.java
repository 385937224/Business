package cn.pbq.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.pbq.entity.Role;
import cn.pbq.entity.User;
import cn.pbq.entity.User_Role;
import cn.pbq.service.RoleService;
import cn.pbq.service.UserService;
import cn.pbq.util.Page;
import cn.pbq.util.SqlUtil;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
 



public class UserAction extends ActionSupport implements RequestAware{

	
	private Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request=request;		
	}

	
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Resource
	private RoleService roleService;


	/***********************封装提交的参数***************************/
	private User user;
	private File headImg;
	private String headImgFileName;
	private String headImgContentType;
	private String[] selectedRow;
	private String[] roledIdRow;
	private File userExcel;
	private String userExcelFileName;
	private String userExcelContentType;
	//专门用来保存查询条件值的。只能保存一个。复杂的就要定义个数组或对象去保存。
	private String searchValue;
	//如果向实现像查询条件一样回显在listUI，又要设置一个变量来存储 这个页码记录。
	//页码.int基本数据类型。没赋值。默认为0.
	private int pageNumber;
	//总页数
	private int pageTotal;
	//总记录
	private long sum;
	
	/**
	 * 2.优化列表页面（第2次改造）： 条件查询 并进行分页。
	 */
	public String listUI() {
			
			SqlUtil sqlUtil = new SqlUtil();
			//是包括了包名的。cn.pbq.entity.User。也OK。
			//HQl区分大小写。我我们直接写”User“字符串要注意大小写。"user"不行。"User"可以查到。
			sqlUtil.setFrom(User.class.getName());
			if(user!=null){
				if(StringUtils.isNotBlank(user.getNickName())){
					
					sqlUtil.setWhere("nickName like ?","%"+user.getNickName()+"%");
				}
			}

//			sqlUtil.setOrderBy("nickName", "asc");  //user.nickName会出错。因为  小写的user。	
			if(pageNumber<1)pageNumber=1;
			Page page = userService.getPage(sqlUtil, pageNumber, 3);
			pageTotal=page.getPageTotal();
			sum=page.getSum();
			
			
			request.put("userList", page.getList());
			return "listUI";
		}
	
	
	
	//新增页面
	public String addUI(){
		List<Role> roleList = roleService.getAll();
		ActionContext.getContext().getContextMap().put("roleList", roleList);
		return "addUI";
	}
	
	//保存新增
	public String add() throws Exception {
		
//		使用了perperties配置文件，把上传文件夹的虚拟路径和实际的路径分隔开来
//		服务器的Servlet.xml中修改过：用了虚拟路径，实现服务器与上传文件存放文件夹分隔。
		Properties properties = new Properties();
		InputStream in = UserAction.class.getResourceAsStream("/uploadPath.properties");
		properties.load(in);
		String path = properties.getProperty("user_uploadPath");
			
		if(headImg!=null){			
//			String path= ServletActionContext.getServletContext().getRealPath("/upload");
			String uuid = UUID.randomUUID().toString().replace("-", "");
			headImgFileName = uuid +headImgFileName.substring(headImgFileName.lastIndexOf("."));
			File destFile = new File(path, headImgFileName);
			FileUtils.copyFile(headImg, destFile);
//			System.out.println(headImgFileName);
			
//			保存到数据库实际是个路径名。服务器虚拟路径使用。可以再拼上些文件层次 ,如："user/"+headImgFileName2
			user.setHeadImg(headImgFileName);
		}

	
//		if(roledIdRow!=null){
//			for (int i = 0; i < roledIdRow.length; i++) {
//				System.out.println(roledIdRow[i]);
//			}
//		}
		
//		userService.save(user);
		userService.saveUserAndRole(user, roledIdRow);
//		return "listUI";
		return "listAction";
	}
	
	
	//跳转到编辑页面
	public String editUI(){
		
//		从ListUI进到editUI时候，把查询条件框里的值先保存起来。防止覆盖。
		searchValue= user.getNickName();
		
		user = userService.findById(user.getId());
		List<User_Role> user_RolList = userService.findUser_RolByUserId(user.getId());
		/**
		 * 循环显示怎么封装的。
		 */
//		for (User_Role user_Role : user_RolList) {
//			System.out.println(user_Role.getUserId());		
//			System.out.println(user_Role.getUserRoleID());
//			System.out.println("111111111111111111111");
//			//我们只查了user_role表，有role.roleId。所以hibernate会帮我们封装。这里可以取到。但取不到role对象。
//			System.out.println(user_Role.getRole().getRoleId());
//			//此处错误。因为我们只查了user_role表。这表中的字段没有role的。所以这个role对象没被初始化。
//			System.out.println(user_Role.getRole());
//		}
		
		

		if(user_RolList!=null && user_RolList.size()>0){			
			/**
			 * 没初始化数组。下面 roledIdRow[i]=roleId  是无法赋值成功。
			 * 结果导致一直设置成功，roledIdRow数组  报异常：NullPointerException.
			 * 而且强制你设置这个数组的初始化大小。
			 */
			roledIdRow= new String[user_RolList.size()];
			int i=0;
			//取出roleId放到全局成员变量数组，方便<s:checkboxlist>进行回显。
			for (User_Role user_Role : user_RolList) {
					String roleId = user_Role.getRole().getRoleId();
					roledIdRow[i]=roleId;
					i++;
			}	
		}
		
		
	
		//找出所以角色
		List<Role> roleList = roleService.getAll();		
		ActionContext.getContext().getContextMap().put("roleList", roleList);
//		这里不必要再放到域对象中。因为赋给Action类中的成员变量user。那么struts会自动把成员变量放到域对象中。jsp页面用EL表达式（只能取域对象的值）能取。
//		request.put("user", user);
		return "editUI" ; 
	}
	
	//保存编辑
	//保存编辑的时候也要处理上传的头像文件的新上传文件就没了。
	public String edit()throws Exception{
//		System.out.println("@@@@@@@@@@@@@@"+searchValue);
		
		String id = user.getId();
		User userInDB = userService.findById(id);
		user.setHeadImg(userInDB.getHeadImg());
		
		if(headImg!=null){
			Properties properties = new Properties();
			//类字节码方式获取 文件路径。
			InputStream in = UserAction.class.getResourceAsStream("/uploadPath.properties");
			properties.load(in);
			String path = properties.getProperty("user_uploadPath");
			
//			String path= ServletActionContext.getServletContext().getRealPath("/upload");
			String uuid = UUID.randomUUID().toString().replace("-", "");
			headImgFileName = uuid +headImgFileName.substring(headImgFileName.lastIndexOf("."));
			File destFile = new File(path, headImgFileName);
			FileUtils.copyFile(headImg, destFile);
//			System.out.println(headImgFileName);
			
//			保存到数据库实际是个路径名。服务器虚拟路径使用。可以再拼上些文件层次 ,如："user/"+headImgFileName2
			user.setHeadImg(headImgFileName);
		}
		userService.updateUserAndRole(user, roledIdRow);
//		userService.update(user);
		return "listAction";
	}
	
	//删除
	public String delete(){
//		userService.delete(user.getId());

		searchValue=user.getNickName();
		
		//连同该用户具有的  角色都删了。
		userService.deleteUserAndRole(user.getId());
		return "listAction";
	}
	
	//批量删除	
	public String deleteAll(){
		searchValue=user.getNickName();
		if(selectedRow!= null){
			for (String id : selectedRow) {
//				userService.delete(id);
				userService.deleteUserAndRole(id);
			}
		}	
		return "listAction";
	}
	
	
//	导出Excel
	public void exportExcel(){
		try {
			List<User> userList = userService.getAll();
//			System.out.println("!!!!!!!!!!!!"+userList.get(1).getBirthday().getClass());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachement;filename="+new String("ni.xlsx".getBytes(),"ISO-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			userService.exportExcel(userList, out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//批量导入用户
	public String importExcel() {
		if(userExcel!=null){
			if(userExcelFileName.matches("(?i)^.+\\.((xlsx)|(xls))$")){
				userService.importExcel(userExcel, userExcelFileName);
			}
		}
//		System.out.println(userExcelFileName);
		return "listAction";
	}
	
	
	
	//ajax校验用户 的唯一性
	public void verifyUserName(){
		List<User> userList = userService.getAll();
		String userNameFlag= "yes";
//		字符串比较是否相等用equals().用== 是比较类型的或数字的大小。
//		if(userList.get(i).getUserName()==user.getUserName())-------结果一直为false。
		
		for (int i = 0; i < userList.size(); i++) {
//			System.out.println(user.getId());
			if(user.getId()==null){
				if(userList.get(i).getUserName().equals(user.getUserName())){
					userNameFlag= "no";
				}
			}else {
				if(userList.get(i).getUserName().equals(user.getUserName())&& !user.getId().equals(userList.get(i).getId())){
					userNameFlag= "no";
				}
			}

				
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(userNameFlag);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	
	
	
	
	
	/*************************/
	public File getUserExcel() {
		return userExcel;
	}

	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}

	public String getUserExcelFileName() {
		return userExcelFileName;
	}

	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}

	public String getUserExcelContentType() {
		return userExcelContentType;
	}

	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}

	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String[] getRoledIdRow() {
		return roledIdRow;
	}
	public void setRoledIdRow(String[] roledIdRow) {
		this.roledIdRow = roledIdRow;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	public long getSum() {
		return sum;
	}
	public void setSum(long sum) {
		this.sum = sum;
	}

	
	
	

/**	
	// 列表页面。列举所有记录。
	public String listUI() {
	//	Map<String, Object> request = ActionContext.getContext().getContextMap();
		List<User> userList = userService.getAll();	
		request.put("userList", userList);
		return "listUI";
	}
*/	
	
	/**
	 * 1.改造列表页面（第1次改造）： 条件查询 出对应匹配的记录。当没有条件的时候，就是查询出user所有记录。
	 */
	/*
	public String listUI() {
		
	//	Map<String, Object> request = ActionContext.getContext().getContextMap();	
		
		SqlUtil sqlUtil = new SqlUtil();
		//是包括了包名的。cn.pbq.entity.User。也OK。
		//HQl区分大小写。我我们直接写”User“字符串要注意大小写。"user"不行。"User"可以查到。
		sqlUtil.setFrom(User.class.getName());
		if(user!=null){
			if(StringUtils.isNotBlank(user.getNickName())){
				
				sqlUtil.setWhere("nickName like ?","%"+user.getNickName()+"%");
			}
		}

		sqlUtil.setOrderBy("nickName", "asc");  //user.nickName会出错。因为  小写的user。
		String sql = sqlUtil.getSQL();
		List<String> paremeterList = sqlUtil.getConditionParemeterList();	
		

		List<User> userList = userService.findObjectByCondition(sql, paremeterList);
		request.put("userList", userList);
		return "listUI";
	}
	*/
	
	
}
