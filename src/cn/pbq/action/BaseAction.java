package cn.pbq.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements RequestAware {

	//抽取变量封装成BaseAction。子类要能用这个成员，用protected修饰
	protected Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request=request;		
	}
	
	//专门用来保存查询条件值的。只能保存一个。复杂的就要定义个数组或对象去保存。
	protected String searchValue;
	//如果向实现像查询条件一样回显在listUI，又要设置一个变量来存储 这个页码记录。
	//页码.int基本数据类型。没赋值。默认为0.
	protected int pageNumber;
	//总页数
	protected int pageTotal;
	//总记录
	protected long sum;
	
	
	
	
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
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}


}
