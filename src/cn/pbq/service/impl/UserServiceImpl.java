package cn.pbq.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import cn.pbq.util.ExcelUtil;
import cn.pbq.dao.UserDao;
import cn.pbq.entity.User;
import cn.pbq.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
//		super.setBaseDao(userDao);
		this.userDao = userDao;
	}

	
	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void delete(Serializable id) {
		userDao.delete(id);
		
	}

	@Override
	public void update(User user) {
		userDao.update(user);
		
	}

	@Override
	public User findById(Serializable id) {
		return userDao.findById(id);
		
	}


	@Override
	public List<User> getAll() {
		return userDao.getAll();
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
	

}
