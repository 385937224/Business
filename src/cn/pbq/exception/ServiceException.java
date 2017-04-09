package cn.pbq.exception;

public class ServiceException extends SysException {

	public ServiceException(){
		super("业务层出现异常");
	}
	

	public  ServiceException(String message) {
		super(message);	
	}
}
