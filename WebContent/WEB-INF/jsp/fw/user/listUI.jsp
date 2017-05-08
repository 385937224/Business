<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>用户管理</title>
    <!-- 日期组件WdatePicker -->
    <script type="text/javascript" src="${basePath}js/datepicker/WdatePicker.js"></script>
    <%@include file="/common/header.jsp" %>
    <script type="text/javascript">
    	
    	//全选、反选
    	function doSelectAll(){
			// jquery 1.6 前
			//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
			//prop jquery 1.6+建议使用
			$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
    	}
    	 
    
    	//新增
      	function doAdd() {
			$("form").eq(0).attr("action","${basePath}fw/user_addUI.action").submit();
			
// 			document.forms[0].action= "${basePath}fw/user_addUI.action";
// 			document.forms[0].submit();
		}
    	
    	//编辑
    	function doEdit(id) {
			document.forms[0].action="${basePath}fw/user_editUI.action?user.id="+ id;
			document.forms[0].submit();
		}  
    	
    	//批量删除----------前面<a href="">的标签,要传参数。这里不用,因为这里是表单元素，<input>,提交是自动会把参数附带进去的。
    	function doDeleteAll() {
			$("form").eq(0).attr("action","${basePath}fw/user_deleteAll.action");
			document.getElementsByTagName("form")[0].submit();
		}
    	
    	function doExportExcel(){

    		//打开链接地址，等效<a>。用submit，会把表单的一些参数封装到后台。
     		window.open("${basePath}fw/user_exportExcel.action","_self"); 
    		
     		//用submit，会把表单的一些被选中的参数封装到后台。不必要。
// 			document.forms[0].action="";
// 			document.forms[0].submit();
    	}
    	
    	function doImportExcel(){
    		$("form").eq(0).attr("action","${basePath}fw/user_importExcel.action").submit();
    	}
    	
    	
    </script>
</head>

<body class="rightBody">
<form name="form1" action="" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>用户管理</strong></div> </div>
                <div class="search_art">    
                	<ol>
	                    <li>
	                       	 用户名：<s:textfield name="user.nickName" cssClass="s_text" id="userName"  cssStyle="width:160px;"/>
	                    </li>
	                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
	                    <li style="float:right;">
	                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
	                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
	                        <input type="button" value="导出" class="s_button" onclick="doExportExcel()"/>&nbsp;
	                    	<input name="userExcel" type="file"/>
	                        <input type="button" value="导入" class="s_button" onclick="doImportExcel()"/>&nbsp;
	                    </li>
                    </ol>               
                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td width="140" align="center">用户名</td>
                            <td width="140" align="center">帐号</td>
                            <td width="160" align="center">所属部门</td>
                            <td width="80" align="center">性别</td>
                            <td align="center">电子邮箱</td>
                            <td width="100" align="center">操作</td>
                        </tr>
                        <c:forEach items="${userList }" var="user" varStatus="varSt">
                        	<tr <c:if test="varSt.odd">bgcolor="f8f8f8"</c:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="${user.id }" /></td>
                                <td align="center">${user.nickName }</td>
                                <td align="center">${user.userName }</td>
                                <td align="center">${user.dept }</td>
                                <td align="center">${user.gender }</td>
                                <td align="center">${user.email }</td>
                                <td align="center">
                                    <a href="javascript:doEdit('${user.id }')">编辑</a>
                                    <a href="${basePath}fw/user_delete.action?user.id=${user.id}">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        <div class="c_pate" style="margin-top: 5px;">
		<table width="100%" class="pageDown" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td align="right">
                 	总共1条记录，当前第 1 页，共 1 页 &nbsp;&nbsp;
                            <a href="#">上一页</a>&nbsp;&nbsp;<a href="#">下一页</a>
					到&nbsp;<input type="text" style="width: 30px;" onkeypress="if(event.keyCode == 13){doGoPage(this.value);}" min="1"
					max="" value="1" /> &nbsp;&nbsp;
			    </td>
			</tr>
		</table>	
        </div>
        </div>
    </div>
</form>

</body>
</html>