package cn.pbq.util;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.AALOAD;

/**
 * 该类主要是 来组装拼接sql语句。
 * 传递给Dao层的方法，最终还是Dao层实现与数据库的交互。
 * @author panbingqi;
 * 2017年5月10日
 *
 */
public class SqlUtil {

	
	private String from="";
	private String where="";
	private String orderBy="";
	
	private List<String> conditionParemeterList;
	

	
	/**
	 * From User。目前仅提供一个类的From语句。多次调用不会实现From User，Info 的 拼接。
	 * @param className  类名称。如User。
	 * 
	 * 
	 * User.class.getName()是包括了包名的。cn.pbq.entity.User。填这个也OK。
	 * HQl区分大小写。我我们直接写”User“字符串要注意大小写。"user"不行。"User"可以查到。
	 */
	public void setFrom(String className){
		from = " FROM " + className;
	}
	
	
	/**
	 * where  name like ？。若多个条件，多次调用这个方法。会自己完成条件的拼接。
	 * @param condition		查询条件语句,如“name like ？”。“number between ？ and ？”
	 * @param conditionParemeters  占位符？所对应的参数,如“%你%”,“100” ,“200” 。
	 * 
	 * 若是多表查询。condition就要加上个别名了。自动循环分配别名?还是自定义别名？
	 * 都可以。自动分配别名，就像setParemeter(1,*) 这样按位置区别 表。
	 */
	public void setWhere(String condition,String... conditionParemeters){
		/**
		 * 我这里这样写有点局限性。只能查“=”和一个参数的条件。
		 * 只能查找“=”的条件。如果是 ">=？" "like ？" 和“between ？ and ？”等条件呢？
		 * 所以整个conditions 除了Where 和and  其他字符串都是由condition拼接完再传入。
		 * 如   between ？and ？。
		 */		
//		if(where.length()>0){
//			where = where + "AND "+ condition +"= ?";
//		}else {
//			where ="WHERE "+ condition +"=  ?";
//		}
		
		if(where.length()>0){
			where = where + " AND "+ condition ;
		}else {
			where =" WHERE "+ condition;
		}
		
		
		if(conditionParemeterList==null){
			conditionParemeterList=new ArrayList<String>();
		}
		for (String string : conditionParemeters) {
			conditionParemeterList.add(string);
		}
		
		
	}
	

	/**
	 * 可能遇到 " order by  nickeName desc，time asc" 按照a、b两个的顺序来排序的情况。
	 * @param condition	  属性.如 nickeName
	 * @param ascOrDesc	 asc  desc
	 * 
	 * 因为这查询一个表的。所以直接写属性。若是多表。就要加上别名了。
	 */
	public void setOrderBy(String condition,String ascOrDesc){
		if(orderBy.length()>0){
			orderBy=orderBy+ " , "+condition+" "+ascOrDesc;
		}else {
			orderBy= " ORDER BY "+ condition +" "+ ascOrDesc;
		}
	}
	
	
	public String getSQL(){
		return from+where+orderBy;
	}
	
	//得到   hql语句中?占位符 对应的 参数list集合
	public List<String> getConditionParemeterList() {
		return conditionParemeterList;
	}

	public String getCountSQL(){
		return " select count(*) " + from + where; 
	}
	
	
	
	
	

	
	
	
	
}
