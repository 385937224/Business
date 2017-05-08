<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="gb2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>系统异常信息</title>

	</HEAD>
	${reqeust.info }
	<body leftMargin="0" topMargin="0"  bgcolor="#f3f3f3">
		<form id="Form1" method="post">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr><td height="100"></td></tr>
				<tr>
					<td vAlign="top" align="center">
						<table style="background-color:#FFFFFF;BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px solid; BORDER-BOTTOM: #cccccc 1px solid; BORDER-COLLAPSE: collapse"
							cellSpacing="0" cellPadding="0" width="500" border="1">
							<tr height="30">
								<td align="center" background="image/main/l-bg7.jpg" colSpan="2"><font color="#ffff66"><b>${request.info }</b></font></td>
							</tr>
							<tr height="100">
								<td align="center">
									<table width="500" border="0">
										<tr align="center" height="100">
											<td>一请求信息：${request.info }
												二异常信息：${exception.errorMsg}
												三封装的信息：<s:property value="exception.errorMsg"/>
											</td>
											<%
// 												String url = (String)request.getAttribute("url");
// 												response.setHeader("Refresh","3;url=" + url);
											%>
										</tr>
										<tr height="50" align="center">
											<td></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</HTML>