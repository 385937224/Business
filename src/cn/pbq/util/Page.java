package cn.pbq.util;

import java.util.List;

public class Page {

	//每页大小
	private int pageSize;
	//页码
	private int pageNumber;
	//总页数
	private int pageTotal;
	//查询得到的总记录数
	private long sum;
	//用去存放  hql查询得到的结果集合
	private List list;

	
	public Page() {
	}
	
	
	/**
	 * 通过计算得出总页数。传入总记录、每页大小。
	 * @param sum		总记录数
	 * @param pageSize	每页大小
	 */
	public void setPageTotalByCalculate(long sum,int pageSize){
		this.sum=sum;
		if(pageSize>0){
			this.pageTotal=(int) sum/pageSize;
			if(sum%pageSize>0){
				this.pageTotal=this.pageTotal+1;
			}
			
		}else {
			System.out.println("pageSize不能填负数或0");
		}
		
	}
	
	
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public long getSum() {
		return sum;
	}
	public void setSum(long sum) {
		this.sum = sum;
	}

	
}
