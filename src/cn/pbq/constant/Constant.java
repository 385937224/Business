package cn.pbq.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.StaticApplicationContext;

public class Constant {

	public static String PRiVILEGE_XZGL = "xzgl";
	public static String PRiVILEGE_HQFU = "hqfw";
	public static String PRiVILEGE_ZXXX = "zxxx";
	public static String PRiVILEGE_FW = "fw";
	public static String PRiVILEGE_MDKJ = "mdkj";
	
	public static Map<String, String> 	PRIVILEGE_MAP;
	public static String[]	PRIVILEGE_ARRAY ;
	
	static{
		PRIVILEGE_ARRAY = new String[]{PRiVILEGE_XZGL,PRiVILEGE_HQFU,PRiVILEGE_ZXXX,PRiVILEGE_FW,PRiVILEGE_MDKJ};
		
		PRIVILEGE_MAP =new HashMap<String, String>();
		PRIVILEGE_MAP.put(PRiVILEGE_XZGL, "行政管理");
		PRIVILEGE_MAP.put(PRiVILEGE_HQFU, "后勤服务");
		PRIVILEGE_MAP.put(PRiVILEGE_ZXXX, "在线学习");
		PRIVILEGE_MAP.put(PRiVILEGE_FW, "服务列表");
		PRIVILEGE_MAP.put(PRiVILEGE_MDKJ, "我的空间");
		
		
	}
	
	
	
	
	public static String USER = "user";
	
}
