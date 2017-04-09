package cn.pbq.exception;

/**
 * 小项目觉得不必要 特意自定义异常，来管理下面的异常。这个SysException总异常 跟Exception类基本一样。
 * @author panbingqi;
 */
public class SysException extends Exception{

	//这个变量。主要还是能在Error.jsp页面调用 ${exception.message},来显示异常信息。
	//
	//怎么传到前台，不需要我们去手动保存到域对象。当发送异常的时候，只要struts的异常映射配置中 配置该异常 就ok。
	//当捕抓到该异常就会把该类的变量 一起，映射到所对应的<result>
	private String message;	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
	public SysException(){
		
	}
	
	public SysException(String message){
		super(message);
		this.message=message;
	}
	
	public SysException(Throwable throwable){
		super(throwable);
	}
	
	public SysException(String message,Throwable throwable){
		super(message, throwable);
		this.message=message;
	}
}
