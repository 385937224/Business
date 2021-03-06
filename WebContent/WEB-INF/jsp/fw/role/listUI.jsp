<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@include file="/common/header.jsp"%>
<title>11我的角色管理</title>
<script type="text/javascript">
	//全选、全反选
	function doSelectAll() {
		// jquery 1.6 前
		//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
		//prop jquery 1.6+建议使用
		$("input[name=selectedRow]").prop("checked",
				$("#selAll").is(":checked"));
	}

	function doAdd() {
		document.forms[0].action = "${basePath}fw/role_addUI.action";
		document.forms[0].submit();
	}

	function doEdit(id) {
		document.forms[0].action = "${basePath}fw/role_editUI.action?role.roleId="+id;
		document.forms[0].submit();
	}
	
	function doDelete(id) {
		document.forms[0].action = "${basePath}fw/role_delete.action?role.roleId="+id;
		document.forms[0].submit();
	}
	
	function doDeleteAll(){
		document.forms[0].action = "${basePath}fw/role_deleteAll.action";
		document.forms[0].submit();
	}
	
	function doSearch() {
		document.forms[0].action = "${basePath}fw/role_listUI.action";
		document.forms[0].submit();
	}

	var goPageUri="${basePath}fw/role_listUI.action?pageNumber=";
</script>
</head>
<body class="rightBody">
	<form name="form1" action="" method="post">
		<div class="p_d_1">
			<div class="p_d_1_1">
				<div class="content_info">
					<div class="c_crumbs">
						<div>
							<b></b><strong>角色管理 </strong>
						</div>
					</div>
					<div class="search_art">
						<li>角色名称：<s:textfield name="role.name" cssClass="s_text"
								id="roleName" cssStyle="width:160px;" onchange="doSearch()" />
						</li>
						<li><input type="button" class="s_button" value="搜 索"
							onclick="doSearch()" /></li>
						<li style="float: right;"><input type="button" value="新增"
							class="s_button" onclick="doAdd()" />&nbsp; <input type="button"
							value="删除" class="s_button" onclick="doDeleteAll()" />&nbsp;</li>
					</div>

					<div class="t_list" style="margin: 0px; border: 0px none;">
						<table width="100%" border="0">
							<tr class="t_tit">
								<td width="30" align="center"><input type="checkbox"
									id="selAll" onclick="doSelectAll()" /></td>
								<td width="120" align="center">角色名称</td>
								<td align="center">权限</td>
								<td width="80" align="center">状态</td>
								<td width="120" align="center">操作</td>
							</tr>
							<c:forEach items="${roleList}" varStatus="varS" var="roleTemp">
								<tr bgcolor="f8f8f8">
									<td align="center"><input type="checkbox"
										name="selectedRow" value="${roleTemp.roleId}" /></td>
									<td align="center">${roleTemp.name}</td>
									<td align="center">
										<c:forEach items="${roleTemp.rolePrivileges}" var="rolePrivilegesTemp">
                                 			${map[rolePrivilegesTemp.id.code]} 
<%-- 											<c:out value="${map[rolePrivilegesTemp.id.code]}"></c:out> --%>
										</c:forEach>  						 
									</td>
									<td align="center">${roleTemp.state}</td>
									<td align="center"><a href="javascript:doEdit('${roleTemp.roleId}')">编辑</a> <a
										href="javascript:doDelete('${roleTemp.roleId}')">删除</a></td>
								</tr>
							</c:forEach>
<%-- 							<s:iterator value="roleList" status="st"> --%>
<!--                             <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if> > -->
<%--                                 <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='roleId'/>"/></td> --%>
<%--                                 <td align="center"><s:property value="name"/></td> --%>
<!--                                 <td align="center"> -->
<%--                                 	<s:iterator value="rolePrivileges"> --%>
<%--                                 		<s:property value="id.code"/> --%>
<!--                                 		不断刷新，顺序也是会变的。 -->
<%--                                 	</s:iterator>	 --%>
<!--                                 </td> -->
<%--                                 <td align="center"><s:property value="state==1?'有效':'无效'"/></td> --%>
<!--                                 <td align="center"> -->
<%--                                     <a href="javascript:doEdit('<s:property value='roleId'/>')">编辑</a> --%>
<%--                                     <a href="javascript:doDelete('<s:property value='roleId'/>')">删除</a> --%>
<!--                                 </td> -->
<!--                             </tr> -->
<%--                            </s:iterator> --%>
						</table>
					</div>
				</div>
				<div class="c_pate" style="margin-top: 5px;">
					<table width="100%" class="pageDown" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td align="right">
								<jsp:include page="/common/page.jsp"></jsp:include>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>

</body>
</html>