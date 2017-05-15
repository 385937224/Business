<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@include file="/common/header.jsp"%>
<title>信息发布管理</title>
	<script type="text/javascript">
	
		function doSelectAll(){
			$("input[name='selectedRow']").prop("checked",$("#selAll").is(":checked"));
		}
		
		function doAdd() {
			document.forms[0].action = "${basePath}fw/info_addUI.action";
			document.forms[0].submit();
		}
	
		function doDelete(id) {
			document.forms[0].action = "${basePath}fw/info_delete.action?info.infoId="+id;
			document.forms[0].submit();
		}
		
		function doEdit(id) {
			document.forms[0].action = "${basePath}fw/info_editUI.action?info.infoId="+id;
			document.forms[0].submit();
		}
		
		function doDeleteAll() {
			document.forms[0].action = "${basePath}fw/info_deleteAll.action";
			document.forms[0].submit();
		}
		
		function changeState(id,changeState) {
			//JSON文本格式。
			$.ajax({

				type:"POST",
				url:"${basePath}fw/info_changeState.action",
				data:{"info.infoId":id,"info.state":changeState},
				async:true,
				success:function(data,status,ajax){
					

					if(changeState==1){
						$("#state_"+id).text("发布");		
// 						\"是 java、js中用来转义成  引号" 。
// 						Html中          &apos;是单引号的转义符。。&quot;是双引号"的。
						$("#doState_"+id).html("<a href='javascript:changeState(\""+ id +"\",0)'  >停用</a>");
// 						$("#doState_"+id).html("<a href='javascript:changeState(&apos;"+ id +"&apos;,0)'  >停用</a>");
// 						$("#doState_"+id).html('<a href="javascript:changeState(\''+ id +'\',0)"  >停用</a>');
					}else {
						$("#state_"+id).text("停用");							
						$("#doState_"+id).html("<a href=\"javascript:changeState('"+ id +"',1)\"  >发布1</a>");
// 						$("#doState_"+id).html('<a href="javascript:changeState(\''+ id +'\',1)"  >发布</a>');
// 						$("#doState_"+id).html("<a href='javascript:changeState(\""+ id +"\",1)'  >发布</a>");
// 						$("#doState_"+id).html("<a href='javascript:changeState(&apos;"+ id +"&apos;,1)' >发布</a>");
						

					}						

					
				}
			})
		}
	
		function doSearch(){
			document.forms[0].action = "${basePath}fw/info_listUI.action";
			document.forms[0].submit();
		}
		
		var goPageUri="${basePath}fw/info_listUI.action?pageNumber=";
	</script>
</head>
<body class="rightBody">
	<form name="form1" action="" method="post">
		<div class="p_d_1">
			<div class="p_d_1_1">
				<div class="content_info">
					<div class="c_crumbs">
						<div>
							<b></b><strong>信息发布管理</strong>
						</div>
					</div>
					<div class="search_art">
						<li>信息标题：<s:textfield name="info.title" cssClass="s_text"
								id="infoTitle" cssStyle="width:160px;"  onchange="doSearch()"/>
						</li>
						<li><input type="button" class="s_button" value="搜 索"
							onclick="doSearch()" /></li>
						<li style="float: right;">
							<input type="button" value="新增" class="s_button" onclick="doAdd()" />&nbsp; 
							<input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;</li>
					</div>

					<div class="t_list" style="margin: 0px; border: 0px none;">
						<table width="100%" border="0">
							<tr class="t_tit">
								<td width="30" align="center"><input type="checkbox"
									id="selAll" onclick="doSelectAll()" /></td>
								<td align="center">信息标题</td>
								<td width="120" align="center">信息分类</td>
								<td width="120" align="center">创建人</td>
								<td width="140" align="center">创建时间</td>
								<td width="80" align="center">状态</td>
								<td width="120" align="center">操作</td>
							</tr>
							<s:iterator value="#request.list" status="st">
								<tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if>>
									<td align="center"><input type="checkbox"
										name="selectedRow" value="<s:property value='infoId'/>" /></td>
									<td align="center"><s:property value="title" /></td>
									<td align="center"><s:property value="#request.infoTypeMap[infoType]" />
									</td>   
									<td align="center"><s:property value="creator" /></td>
									<td align="center"><s:date name="createTime"
											format="yyyy-MM-dd HH:mm" /></td>
									<td id="state_<s:property value='infoId' />" align="center">
										<s:property value="state==1?'发布':'停用'" />
									</td>
									<td align="center">
										<span id="doState_<s:property value='infoId' />" > 
											<s:if test="state==1">
												<a href="javascript:changeState('<s:property value='infoId' />',0)"  >停用</a>
											</s:if><s:else>
												<a href="javascript:changeState('<s:property value='infoId'/>',1)"   >发布</a>
											</s:else> 
										</span> 
										<a href="javascript:doEdit('<s:property value='infoId'/>')">编辑</a>
										<a href="javascript:doDelete('<s:property value='infoId'/>')">删除</a>
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