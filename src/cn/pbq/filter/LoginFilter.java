package cn.pbq.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.pbq.constant.Constant;
import cn.pbq.entity.User;


public class LoginFilter implements Filter {
	
	

	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		
//		System.out.println("!!!!!!!!!!!!!!!!!!过滤器---");
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		HttpSession session = httpServletRequest.getSession();
		String requestURI = httpServletRequest.getRequestURI();
		
		if(StringUtils.isNotBlank(requestURI) && !requestURI.contains("/login") ){
//			System.out.println("当前请求路径"+requestURI);
			User user = (User) session.getAttribute(Constant.USER);
			if(user!=null){
				//检测当前请求访问是否需要权限.
				if(PrivilegeCheck.needPrivilege(requestURI)){
					System.out.println("需呀权限");
					//获取Spring容器。
					WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
					//获取privilegeCheck 实例对象。
					PrivilegeCheck privilegeCheck = (PrivilegeCheck) webApplicationContext.getBean("privilegeCheck");
					
					//检测当前user是否拥有权限。					
					if(privilegeCheck.canVisit(requestURI, user)){
						chain.doFilter(httpServletRequest, httpServletResponse);	
					}else{
						System.out.println("不需呀权限");
						httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/common/noPrivilege.html"); 
					}
				}else {
					chain.doFilter(httpServletRequest, httpServletResponse);	
				}
					
					
			}else{
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/sys/login_loginUI.action");
				//这里好像不能用转发。只能用重归定向  去到 登录页面。
//				httpServletRequest.getRequestDispatcher("login_loginUI.action").forward(httpServletRequest, httpServletResponse);
			}
			
		}else{
			//登录操作、登录页面   的请求、放行。
			chain.doFilter(httpServletRequest, httpServletResponse);
		}	
	}

	
	@Override
	public void destroy() {	
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}



}
