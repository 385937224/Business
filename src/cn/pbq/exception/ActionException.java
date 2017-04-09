package cn.pbq.exception;


public class ActionException extends SysException {

	public ActionException(){
		super("action层出现异常");
		
	}
	
	public ActionException(String message){
		super(message);
	}
}
