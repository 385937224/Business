package cn.pbq.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jdt.internal.compiler.util.Sorting;
import org.junit.Test;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import cn.pbq.entity.User;


public class ExcelUtil {

	public static void exportExcel(List<User> list,OutputStream out) {
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Sheet1");
			
			//设置本工作表的列宽度。宽度单位是字符的1/256。设置为256---该列宽度才是一个字符的宽度。
			//取7是因为这个用户表只需要用到6列。
			for(int i=0;i<7;i++){
				sheet.setColumnWidth(i, 20*256);
			}
			
			//设置单元格样式:居中。用于主副标题
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			
			//标题单元格合并
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 6);
			sheet.addMergedRegion(cellRangeAddress);
			
			//标题
			XSSFCell cell00 = sheet.createRow(0).createCell(0);
			cell00.setCellStyle(cellStyle);
			cell00.setCellValue("用户管理");
			
			//副标题
			String[] title= {"昵称","用户名","性别","部门","手机","邮件","出生日期"};
			XSSFRow row1 = sheet.createRow(1);
			for(int i=0;i<7;i++){
				XSSFCell cell1 = row1.createCell(i);
				cell1.setCellStyle(cellStyle);
				cell1.setCellValue(title[i]);
				
			}
			
			//填入用户的信息

			if(list!=null){
				for(int j=0;j<list.size();j++){
					XSSFRow row2 = sheet.createRow(j+2);
					row2.createCell(0).setCellValue(list.get(j).getNickName());
					row2.createCell(1).setCellValue(list.get(j).getUserName());
					row2.createCell(2).setCellValue(list.get(j).getGender());
					row2.createCell(3).setCellValue(list.get(j).getDept());
					row2.createCell(4).setCellValue(list.get(j).getMobile());
					row2.createCell(5).setCellValue(list.get(j).getEmail());
					row2.createCell(6).setCellValue(list.get(j).getBirthday());
					
					//若不判断一下，只要日期Date类型为null。就不弹出窗口。
					//date类型转化为字符串类型在写入。但是只要get得到的Date为null，会导致整个excel表的下载框都不弹出来。
					//上面其他的String类型get得到null也可以继续弹出下载框，就这个Date类型转化String后任然会有bug，只要为null就不弹窗。
//					if(list.get(j).getBirthday()!=null){
//						row2.createCell(6).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(list.get(j).getBirthday()));
//					}
					/**
					 * 这是直接写入Date类型的。
					 * 打开excel后，日期显示的是不对的，要把日期那一整列单元格 改成 日期格式。
					 */
					if(list.get(j).getBirthday()!=null){
						row2.createCell(6).setCellValue(list.get(j).getBirthday());
					}
				}
			}
		

			//导出到excel中
			workbook.write(out);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}
	
	
	/**
	 * 自己的测试使用的
	 * @throws Exception
	 */
	
	//要记得关资源。自己写代码切记。
	public static void test() throws Exception{
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		XSSFSheet sheet = xssfWorkbook.createSheet("工作表1");
		//合并单元格
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 3);
		sheet.addMergedRegion(cellRangeAddress);
		
		//单元个中写入内容。
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("用户管理");
		
		//设置居中样式
		XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
//		cell.setCellStyle(cellStyle);
	
		String[] title= {"昵称","用户名","性别","部门","手机","邮件","出生日期"};
		/*****************大致原理先设置好每行，最后一起 write到 输出流中。************/
		// 每次new同一行的row。导致这行原来设置的（如该行中的某个 cell设置的）值都没了。
		// 同一个sheet、同一行row、同一个cell对象，new一次就好，不要重复new。
		XSSFRow row1 = sheet.createRow(1);
		for(int i=0;i<title.length;i++){	
			XSSFCell cell1 = row1.createCell(i);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue(title[i]);
		}
		
		String a =null;
		row1.createCell(9).setCellValue(new Date());
		
		//错误案例  
		//sheet.createRow(j+2).createCell(0); 虽然cell不同，但是每次createRow(j+2)，都是同一个j+2的，
		//前面的设置cell因为新create的row导致没了。
//		for(int j=0;j<list.size();j++){
//			XSSFCell cellj0 = sheet.createRow(j+2).createCell(0);
//			cellj0.setCellValue(list.get(j).getNickName());
//			XSSFCell cellj1 = sheet.createRow(j+2).createCell(1);
//			cellj1.setCellValue(list.get(j).getUserName());
//		}

		
		
		File file = new File("D:\\Program Files\\apache-tomcat-upload\\223.xlsx");
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		xssfWorkbook.write(fileOutputStream);
		xssfWorkbook.close();

		
	}
	
	public static void main(String[] args) throws Exception{
//		ExcelUtil.test();

	}
	
}
