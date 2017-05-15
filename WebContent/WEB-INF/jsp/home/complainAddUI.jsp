<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("basePath", request.getContextPath()+"/") ;
%>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>我要投诉</title>
    
    <script type="text/javascript">
    	function doAdd() {
			document.forms[0].action="${basePath}fw/complain_add.action";
			document.forms[0].submit();
		}
    
    
    
    </script>
    
</head>
<body>
<form id="form" name="form" action="" method="post" enctype="multipart/form-data">
    <div class="vp_d_1">
        <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
        <div class="vp_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>工作主页</strong>&nbsp;-&nbsp;我要投诉</div></div>
    <div class="tableH2">我要投诉</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="250px">投诉标题：</td>
            <td><s:textfield name="complain.title"/></td>
        </tr>
        <tr>
            <td class="tdBg">被投诉人部门：</td>
            <td></td>
        </tr>
        <tr>
            <td class="tdBg">被投诉人姓名：</td>
            <td></td>
        </tr>
        <tr>
            <td class="tdBg">投诉内容：</td>
            <td><s:textarea id="editor" name="complain.compContent" cssStyle="width:90%;height:160px;" /></td>
        </tr>
        <tr>
            <td class="tdBg">是否匿名投诉：</td>
            <td><s:radio name="complain.anonymity" list="#{'false':'非匿名投诉','true':'匿名投诉' }" value="0"/></td>
        </tr>       
    </table>
    
    
<!--     Struts的value属性不支持表达。 好像支持EL、jsp<%  %>.还是用回原struts的#取值。-->
<%-- 	<s:hidden name="complain.complainant" value="${sessionScope.user.nickName }"></s:hidden> --%>
	<s:hidden name="complain.complainant" value="%{#session.user.nickName}"></s:hidden>
	<s:hidden name="complain.compCompany" value="%{#session.user.dept}"></s:hidden>
	<s:hidden name="complain.compMobile"  value="%{#session.user.mobile}"></s:hidden>
			

    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存"  onclick="doAdd()"/>
        &nbsp;&nbsp;&nbsp;&nbsp;	
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回首页" />
    </div>
    </div></div>
    <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
    </div>
</form>
</body>
</html>