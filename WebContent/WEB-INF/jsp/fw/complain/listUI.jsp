<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>投诉受理管理</title>
    <script type="text/javascript">
    
    	function doDeal(id){
    		document.forms[0].action= "${basePath}fw/complain_dealUI.action?complain.compId=" +id;
    		document.forms[0].submit();
    	}
    
    	function doSearch(){
    		document.forms[0].action= "${basePath}fw/complain_listUI.action";
    		document.forms[0].submit();
    	}
    
    	var goPageUri="${basePath}fw/complain_listUI.action?pageNumber=";
    </script>
    
    
    
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>投诉受理管理</strong></div> </div>
                <div class="search_art">
                    <li>
                       	投诉标题：<s:textfield name="complain.title" cssClass="s_text"  cssStyle="width:160px;"/>
                    </li>
                    <li>
                       	投诉时间：<s:textfield id="startTime" name="startTime" cssClass="s_text"  cssStyle="width:100px;"/>
                              - 
                             <s:textfield id="endTime" name="endTime" cssClass="s_text"  cssStyle="width:100px;"/>三种格式："yyyy-MM-dd","yyyy","yyyy-MM"
                    </li>
                    <li>   
<%--                    ${complain.state=='0'?"a":"b"}和${complain.state==0?"a":"b"} 是有区别的。 区别在于：${""==0}结果为true--%>
                       	状态：<select  name="complain.state">
                       			<option  ></option>     
                       			<option value="0"   ${complain.state=='0'?"selected":""} >未受理</option>
                       			<option value="1"   ${complain.state=='1'?"selected":""} >已受理</option>
                       		</select>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
<!--                     <li style="float:right;"> -->
<!--                     	<input type="button" value="统计" class="s_button" onclick="doAnnualStatistic()"/>&nbsp; -->
<!--                     </li> -->

                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td align="center">投诉标题</td>
                            <td width="120" align="center">被投诉部门</td>
                            <td width="120" align="center">被投诉人</td>
                            <td width="140" align="center">投诉时间</td>
                            <td width="100" align="center">受理状态</td>
                            <td width="100" align="center">操作</td>
                        </tr>
 							<s:iterator value="#request.list"   status="st">
 							
                            <tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if> >
                                <td align="center"><s:property value="title"/></td>
                                <td align="center"><s:property value="deptOfPeople"/></td>
                                <td align="center"><s:property value="people"/></td>
                                <td align="center"><s:date name="compTime" format="yyyy-MM-dd"/></td>
                                <td align="center"><s:property value="state==1?'已受理':'未受理'"/></td>
                                <td align="center">
                                    <a href="javascript:doDeal('<s:property value='compId'/>')">受理</a>
                                </td>
                            </tr>
							</s:iterator>
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