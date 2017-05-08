<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
// 	直接写请求路径也ok。tomcat服务器会自动 加上当前项目的一些前缀路径。
// 	response.sendRedirect("sys/login_loginUI.action");
// 	为了保险，还是加上自己写上前缀。可能到了别的服务器就不一样了。
	response.sendRedirect(basePath + "sys/login_loginUI.action");
	



// 	这里用转发dispatcher是不成功的，只能重定向。可能在servlet中才可以用转发吧。
// 	request.getRequestDispatcher("/aa.action").forward(request, response);
%>
