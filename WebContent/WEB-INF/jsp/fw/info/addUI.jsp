<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>

    <script>
    	
    </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${basePath }fw/info_add.action" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong>&nbsp;-&nbsp;新增信息</div></div>
    <div class="tableH2">新增信息</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">信息分类：</td>
            <td>
            	<select name="info.infoType" Style="width:60%" >
            	<c:forEach items="${infoTypeMap }" var="infoType">
            		<option value="${infoType.key}">${infoType.value}</option>
            	</c:forEach>
            	</select>
            </td>
            <td class="tdBg" width="200px">来源：</td>
            <td><input type="text" name="info.source"   ></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">信息标题：</td>
            <td colspan="3"><input type="text" name="info.title" Style="width:90%" ></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">信息内容：</td>
<%--             <s:textarea id="editor" name="info.content" cssStyle="width:90%;height:160px;" /> --%>
            <td colspan="3"><textarea  name="info.content"  rows="11"   Style="width:90%" ></textarea></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td colspan="3"><textarea  name="info.memo"  rows="3"   Style="width:90%" ></textarea></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">创建人：</td>
            <td>
            	<input type="text" name="info.creator" Style="width:90%" value="${sessionScope.user.nickName}"  disabled="disabled">
            </td>
            <td class="tdBg" width="200px">创建时间：</td>
            <td>
             
            </td>
        </tr>
    </table>
    
    <div class="tc mt20">
        <input type="submit" class="btnB2" value="保存" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>