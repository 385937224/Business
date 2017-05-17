package cn.pbq.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.StaticApplicationContext;

public class Constant {

	//各个子系统权限。
	public static String PRiVILEGE_XZGL = "xzgl";
	public static String PRiVILEGE_HQFU = "hqfw";
	public static String PRiVILEGE_ZXXX = "zxxx";
	public static String PRiVILEGE_FW = "fw";
	public static String PRiVILEGE_MDKJ = "mdkj";
	public static Map<String, String> 	PRIVILEGE_MAP;
	public static String[]	PRIVILEGE_ARRAY ;
	
	//保存到session域对象的 别称
	public static String USER = "user";
	
	//info信息分类
	public static String INFO_TYPE_XXGG = "xxgg";
	public static String INFO_TYPE_HYTZ = "hytz";
	public static String INFO_TYPE_FWSM = "fwsm";
	public static Map<String, String> INFO_TYPE_MAP;
	
	//info信息分类
	public static String[] DEPT_ARRAY;
	

	
	static{
		PRIVILEGE_ARRAY = new String[]{PRiVILEGE_XZGL,PRiVILEGE_HQFU,PRiVILEGE_ZXXX,PRiVILEGE_FW,PRiVILEGE_MDKJ};
		
		DEPT_ARRAY=new String[]{"部门A","部门B","部门C","部门D"};
		
		PRIVILEGE_MAP =new HashMap<String, String>();
		PRIVILEGE_MAP.put(PRiVILEGE_XZGL, "行政管理");
		PRIVILEGE_MAP.put(PRiVILEGE_HQFU, "后勤服务");
		PRIVILEGE_MAP.put(PRiVILEGE_ZXXX, "在线学习");
		PRIVILEGE_MAP.put(PRiVILEGE_FW, "服务列表");
		PRIVILEGE_MAP.put(PRiVILEGE_MDKJ, "我的空间");
		
		
		INFO_TYPE_MAP = new HashMap<String, String>();
		INFO_TYPE_MAP.put(INFO_TYPE_FWSM, "服务说明");
		INFO_TYPE_MAP.put(INFO_TYPE_XXGG, "信息公告");
		INFO_TYPE_MAP.put(INFO_TYPE_HYTZ, "会议通知");
		
		
	}
	
	
	
	

	
}
