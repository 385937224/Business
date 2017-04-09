package cn.pbq.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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

import antlr.StringUtils;
import cn.pbq.entity.User;

import cn.pbq.service.UserService;
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

	/***********************封装提交的参数***************************/
	private User user;
	private File headImg;
	private String headImgFileName;
	private String headImgContentType;
	private String[] selectedRow;
	private File userExcel;
	private String userExcelFileName;
	private String userExcelContentType;

	
	// 列表页面
	public String listUI() {
	//	Map<String, Object> request = ActionContext.getContext().getContextMap();
		List<User> userList = userService.getAll();	
		request.put("userList", userList);
		return "listUI";
	}
	
	//新增页面
	public String addUI(){
		
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
		userService.save(user);
		return "listAction";
	}
	
	
	//跳转到编辑页面
	public String editUI(){
		user = userService.findById(user.getId());
		
		request.put("user", user);
		return "editUI";
	}
	
	//保存编辑
	//保存编辑的时候也要处理上传的头像文件的新上传文件就没了。
	public String edit()throws Exception{
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
		
		userService.update(user);
		return "listAction";
	}
	
	//删除
	public String delete(){
		userService.delete(user.getId());
		return "listAction";
	}
	
	//批量删除	
	public String deleteAll(){
		if(selectedRow!= null){
			for (String id : selectedRow) {
				userService.delete(id);
			}
		}	
		return "listAction";
	}
	
	
//	导出Excel
	public void exportExcel(){
		try {
			List<User> userList = userService.getAll();
			System.out.println("!!!!!!!!!!!!"+userList.get(1).getBirthday().getClass());
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
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		List<User> userList = userService.getAll();
		String userNameFlag= "yes";
//		字符串比较是否相等用equals().用== 是比较类型的或数字的大小。
//		if(userList.get(i).getUserName()==user.getUserName())-------结果一直为false。
		
		for (int i = 0; i < userList.size(); i++) {
			System.out.println(user.getId());
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
	
}
