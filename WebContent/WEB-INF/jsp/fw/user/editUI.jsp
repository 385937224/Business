<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@include file="/common/header.jsp"%>
<script type="text/javascript"
	src="${basePath}js/datepicker/WdatePicker.js"></script>
<title>用户管理</title>

<script type="text/javascript">
	//实现部门回显。（临时方法，复杂在拼凑语句哪里。）
	$(document)
			.ready(
					function() {		
						var deptAll = [ "部门A", "部门B", "部门C", "部门D" ];
						var userdept = "${user.dept}";
						for (i = 0; i < deptAll.length; i++) {
							var selectString = deptAll[i] == userdept ? "selected": " ";		    		
							var $option = $("<option  value='"+deptAll[i]+"' "+selectString+" >"
									+ deptAll[i] + "</option>");
							$("select[name='user.dept']").append($option);
						}
					})

	//全局userName唯一性标志。  若doVerifyUserName中用return会有bug。因为ajax原理导致的。
	//一直在监听ajax状态，反复触发函数function。所以程序不是一次执行到尾，反复执行多次，return多次。调用者只接收函数返回的第一个结果，return不行。
	var userNameFlag = false;
	
	//用户校验功能。 jquery-ajax完成。   
	//最后面 有用原生js--ajax写的用户校验功能。同步、异步请求的案例。亲测OK。效果等效于这个。
	function doVerifyUserName() {
		$("#verifyUserName").html("");
		var regex = /^[1-9a-zA-Z]+$/;
		var $userName = $("input[name='user.userName']");
		if (regex.test($userName.val())) {
			$.ajax(
				{
					type:"post",
					url:"${basePath}fw/user_verifyUserName.action",
					data:{"user.userName":$userName.val(),"user.id":"${user.id}"},
					
					
					//原生js的XMLHttpRequest.open(method, url, async)中的async 是指请求 使用异步地执行。
					//						如果这个参数是 false，请求是同步的，后续对 send() 的调用将阻塞，直到响应完全接收。即为true的时候不需要onreadystatechange一直监视变化状态。只需要ajax.readyState == 4。
					//						如果这个参数是 true 或省略，请求是异步的，且通常需要一个 onreadystatechange 事件句柄来简直状态。
					
					//async. 默认是true，即为异步方式【有多个线程一起执行】，$.Ajax执行后，会继续执行ajax后面的脚本，【会继续哦，这就导致后台没回传校验结果时候，就继续执行submit()根本原因】。直到服务器端返回数据后，触发$.Ajax里的success方法，这时候执行的是两个线程。【两个线程一起执行呀。】
					//若要将其设置为false，则所有的请求均为同步请求【所以请求同步，总共就只有一个线程】，在没有返回值之前，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
					async:false,
					
					success:function(backData,textStatus,ajax){
						if(backData=='yes'){
							$("#verifyUserName").html("帐号可用");
							userNameFlag=true;
						}else{
							$("#verifyUserName").html("帐号不可用，已经存在");
						}
					}
				}		
			)	
		}else {
			$("#verifyUserName").html("只能是数字或字母");
		} 
		
	}
	
	
	//密码一致性效验，同时也要放到sumit（）中。
	function doVarifyPassword() {

		var $password1 = $("input[name='user.password']").val();
		var $password2 = $("#password2").val();
		if ($password2 == $password1) {
			$("#varifypassword").html("ok");
			return true;
		} else {
			$("#varifypassword").html("密码不一致");
			return false;
		}
	}
	
	//提交效验  
	function doSubmit() {

		if ($("input[name='user.nickName']").val().trim() == "") {
			alert("用户名不能为空");
			return 'no';
		}

		if ($("input[name='user.userName']").val().trim() == "") {
			alert("帐号不能为空");
			return 'no';
		}

		if ($("input[name='user.password']").val().trim() == "") {
			alert("密码不能为空");
			return 'no';

		}

		//调用效验功能
		var passwordFlag = doVarifyPassword();
		alert(passwordFlag);
		doVerifyUserName();
		alert(userNameFlag);

		if (userNameFlag && passwordFlag) {
			document.forms[0].submit();
		} else {
			alert("修正后再提交");
		}

	}

</script>
</head>

<body class="rightBody">
	<form id="form" name="form" action="${basePath}fw/user_edit.action"
		method="post" enctype="multipart/form-data">
		<input type="hidden" name="user.id" value="${user.id} " />
		<div class="p_d_1">
			<div class="p_d_1_1">
				<div class="content_info">
					<div class="c_crumbs">
						<div>
							<b></b><strong>用户管理</strong>&nbsp;-&nbsp;编辑用户
						</div>
					</div>
					<div class="tableH2">编辑用户</div>
					<table id="baseInfo" width="100%" align="center" class="list"
						border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="tdBg" width="200px">所属部门：</td>
							<td>${user.dept}<select name="user.dept">
									<option>请选择部门</option>
							</select>
							</td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">头像：</td>
							<td><img alt="" src="/taxupload/${user.headImg}" width="100"
								height="100"> <input type="file" name="headImg" /></td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">用户名：</td>
							<td><input type="text" name="user.nickName"
								value="${user.nickName}" /></td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">帐号：</td>
							<td><input type="text" name="user.userName"
									value="${user.userName}"   onchange="doVerifyUserName()"/>	
								<span id="verifyUserName" style="color: red"></span>
							</td>	
						</tr>
						<tr>
							<td class="tdBg" width="200px">密码：</td>
							<td><input type="text" name="user.password"
								value="${user.password}" /></td>
						</tr>
						<tr>
				            <td class="tdBg" width="200px">再输入一次密码：</td>
				            <td><input type="text" value="${user.password}" id="password2" onchange="doVarifyPassword()" />
				            	<span id="varifypassword" style="color: red"></span>
				            </td>
				        </tr>
						<tr>
							<td class="tdBg" width="200px">性别：</td>
							<td>男<input type="radio" name="user.gender" value="男"
								${'男'==user.gender?'checked':''} /> 女<input type="radio"
								name="user.gender" value="女" ${'女'==user.gender?'checked':''} />
							</td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">角色：</td>
							<td></td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">电子邮箱：</td>
							<td><input type="text" name="user.email"
								value="${user.email }" /></td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">手机号：</td>
							<td><input type="text" name="user.mobile"
								value="${user.mobile }" /></td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">生日：</td>
							<td><input type="text" id="birthday" name="user.birthday"
								value="${user.birthday}" readonly="readonly"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">状态：</td>
							<td>有效<input type="checkbox" name="user.state" value="有效"
								${'有效'==user.state?'checked':''} /> 无效<input type="checkbox"
								name="user.state" value="无效" ${'无效'==user.state?'checked':''} />
							</td>
						</tr>
						<tr>
							<td class="tdBg" width="200px">备注：</td>
							<td><textarea name="user.memo" cols="75" rows="3">${user.memo}</textarea></td>
						</tr>
					</table>

					<div class="tc mt20">
						<input type="button" class="btnB2" value="保存" onclick="doSubmit()"/>
						&nbsp;&nbsp;&nbsp;&nbsp; 
						<input type="button"	onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>


<script type="text/javascript">

//	原生js的ajax测试。async=false 时候的代码。此时为同步请求。只有一个线程。不需要 onreadystatechange =function(); 事件 一直监听。

// 	function doVerifyUserName() {
// 	$("#verifyUserName").html("");
// 	var regex = /^[1-9a-zA-Z]+$/;
// 	var $userName = $("input[name='user.userName']");
// 	if (regex.test($userName.val())) {

// 		var ajax = new XMLHttpRequest();
// 		ajax.open("post", "${basePath}fw/user_verifyUserName.action",false);
// 		alert("11111111111111111111111111111111111");
// 		ajax.setRequestHeader("content-type",
// 				"application/x-www-form-urlencoded");
// 		//var content = "user.userName=" + $userName.val() + "&user.id="+${user.id};//js中直接用el取值，当作字符串。不能把el看着变量用。
// 		var content = "user.userName=" + $userName.val() + "&user.id=${user.id}";
// 		alert("222222222222222222222222222222");
// 		ajax.send(content);        //同步的时候，send()堵塞是指，代码不会继续执行后面的，就停在这，直到响应完全接收后才继续执行。
// 		alert("333333333333333333333333333333");
// 		/***********************************************/
// 		alert(ajax.readyState);
// 		if (ajax.readyState == 4) { 				 
// 			alert("4444444444444444444");
// 			if (ajax.status == 200) {
// 				if (ajax.responseText == "yes") {
// 					$("#verifyUserName").html("帐号可用");
// 					userNameFlag = true;
// 				} else {
// 					$("#verifyUserName").html("帐号不可用，已经存在");
// 				}
// 			}
// 		}			
// 	} else {

// 		$("#verifyUserName").html("只能是数字或字母");
// 	}
// }

//原生js的ajax测试。async=true 时候的代码。此时为异步请求。同时有两个线程。
// function doVerifyUserName() {
// 	$("#verifyUserName").html("");
// 	var regex = /^[1-9a-zA-Z]+$/;
// 	var $userName = $("input[name='user.userName']");
// 	if (regex.test($userName.val())) {

// 		var ajax = new XMLHttpRequest();
// 		ajax.open("post", "${basePath}fw/user_verifyUserName.action",false);
// 		alert("11111111111111111111111111111111111");
// 		ajax.setRequestHeader("content-type",
// 				"application/x-www-form-urlencoded");
// 		//var content = "user.userName=" + $userName.val() + "&user.id="+${user.id};//js中直接用el取值，当作字符串。不能把el看着变量用。
// 		var content = "user.userName=" + $userName.val() + "&user.id=${user.id}";
// 		alert("222222222222222222222222222222");
// 		ajax.send(content);
// 		alert("333333333333333333333333333333");
// 		/***********************************************/
// 		ajax.onreadystatechange = function() {
// 			alert("4444444444444444444444444444444");
// 			if (ajax.readyState == 4) {
// 				alert("5555555555555555555555555");
// 				if (ajax.status == 200) {
// 					if (ajax.responseText == "yes") {
// 						$("#verifyUserName").html("帐号可用");

// 						userNameFlag = true;
// 					} else {
// 						$("#verifyUserName").html("帐号不可用，已经存在");

// 					}
// 				}
// 			}
// 		}
	
// 	} else {

// 		$("#verifyUserName").html("只能是数字或字母");
// 	}
// }

</script>


