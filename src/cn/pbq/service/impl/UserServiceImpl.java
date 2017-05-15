package cn.pbq.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import cn.pbq.util.ExcelUtil;
import cn.pbq.dao.UserDao;
import cn.pbq.entity.Role;
import cn.pbq.entity.User;
import cn.pbq.entity.User_Role;
import cn.pbq.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	private UserDao userDao;
	//我们是xml配置注入依赖对象。要给setter方法。
	public void setUserDao(UserDao userDao) {
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~"+userDao);
		super.setBaseDao(userDao);
		this.userDao = userDao;
	}
	


	@Override
	public void exportExcel(List<User> list, OutputStream out) {
		ExcelUtil.exportExcel(list, out);
		
	}
	
	@Override
	public void importExcel(File file,String fileName) {
				
		try {
			boolean matches = fileName.matches("(?i)^.+\\.(xls)$");
			//HSSFWorkbook 只可以介绍流对象，不可以直接接收文件对象，所以要创建流对象。  可是老是忘记close资源对象。
			FileInputStream fileInputStream = new FileInputStream(file);	
			//读取
			Workbook workbook=matches? new HSSFWorkbook(fileInputStream):new XSSFWorkbook(fileInputStream);
			//读取第一个工作表
			Sheet sheet = workbook.getSheetAt(0);
			
			
			if(sheet.getPhysicalNumberOfRows()>2){     //获取该工作表中，实际记录的数目，
				User user=null;
				for(int k=2;k<sheet.getPhysicalNumberOfRows();k++){
					Row row = sheet.getRow(k);
					user =new User();
					Cell cell = row.getCell(0);
					user.setNickName(cell.getStringCellValue());//昵称
					Cell cell2 = row.getCell(1);
					user.setUserName(cell2.getStringCellValue());//用户名
					Cell cell3 = row.getCell(2);
					user.setGender(cell3.getStringCellValue());//性别
					Cell cell4 = row.getCell(3);
					user.setDept(cell4.getStringCellValue());//部门
					Cell cell5 = row.getCell(4);
					String mobile ="";
					try {
						mobile = cell5.getStringCellValue();
					} catch (Exception e) {
//						如果号码被记录为科学记录形式，那么就要用数字值形式获取，getNumericCellValue()，然后转换。
						double numericCellValue = cell5.getNumericCellValue();
						mobile=BigDecimal.valueOf(numericCellValue).toString();
					}
					user.setMobile(mobile);//手机
					
					Cell cell6 = row.getCell(5);
					user.setEmail(cell6.getStringCellValue());//邮件
					
					Cell cell7 = row.getCell(6);
					
					//POI和Excel兼容还不够好的地方。
					//有专门读取日期的方法。getDateCellValue()。但是不好用，很容易出bug。
					//首先你得设置你的excel该单元格格式为日期格式，但是即使这样设置.
					//可能原本就有数据，刚设置完日期格式后，原有数据还是保持原来格式，你要重新输入一次按回车才是日期格式。
					Date date=null;
					try {
						date =cell7.getDateCellValue();
					} catch (Exception e) {
						System.out.println("getDateCellValue()获取不了，因为你该单元格不是日期格式。");						
						System.out.println("请修正Excel文档再导入。出生日期所在列的所有单元格不是日期格式，请改成日期格式后，重新输入一次。");
						//补救方法：以字符串来获取，再把字符串转换成  日期格式。但你该单元格输入也不符合字符串日期格式或着说不是文本格式（可能是数字值形式），就没办法了。
						String stringCellValue = cell7.getStringCellValue();
						System.out.println(stringCellValue);
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						date = simpleDateFormat.parse(stringCellValue);
//						throw new RuntimeException("请把你的Excel文档的日期所在列的所有单元格改成日期格式，然后重新输入一次。");
					}
					user.setBirthday(date);
					
					//录入数据库的默认值
					user.setPassword("123456");
					user.setState(User.USER_STATE_VALID);
					
					save(user);
				}	
			}			
			workbook.close();
			fileInputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void saveUserAndRole(User user, String... roleIds) {
		userDao.save(user);
		//复选框都要注意没选的情况、、此时封装的数据是null
		if(roleIds!=null){
			for (String roleId : roleIds) {
				/**
				 * 技巧： new User_Role(userId, Role);
				 * 一般是先根据roleId找到 Role对象再放入。其实不必然。
				 * 因为User_Role所存字段 roleId只是Role对象的rileId属性，所以只要Role对象这个属性有值就ok，User_Role关系表就可以保存成功。
				 * 这样直接new Role对象，就省去了根据roleId查询得到Role对象的的操作啦！！！！！！
				 */
				User_Role user_Role = new User_Role(user.getId(), new Role(roleId));	
	
				userDao.saveUser_Role(user_Role);
			}	
		}

	}

	@Override
	public void deleteUserAndRole(Serializable id) {
		//因为外键约束、被使用关系。要先删掉中间表User_Role的才能删  User表的记录。

		userDao.deleteUser_RoleByUserId(id);

		userDao.delete(id);	
	}

	@Override
	public List<User_Role> findUser_RolByUserId(Serializable userId) {
		
		return userDao.findUser_RolByUserId(userId);
	}
	
	
	@Override
	public void updateUserAndRole(User user, String... roleId){	
		userDao.update(user);
		////hibernate特性问题。要先删除中间表中的对应数据。再全部重新加进去。
		userDao.deleteUser_RoleByUserId(user.getId());		
		if(roleId!=null && roleId.length>0){
			for (int i = 0; i < roleId.length; i++) {
				User_Role user_Role = new User_Role(user.getId(), new Role(roleId[i]));
				userDao.saveUser_Role(user_Role);
			}
		}
	}

	@Override
	public List<User> findUserByUsernameAndPassword(String username, String password) {		
		return userDao.findUserByUsernameAndPassword(username, password);
	}
	
	
}
