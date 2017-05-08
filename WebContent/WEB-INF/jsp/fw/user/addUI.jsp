<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <script type="text/javascript" src="${basePath}js/datepicker/WdatePicker.js"></script>
    <title>用户管理</title>
    
    <script type="text/javascript">
// 	实现部门枚举。（临时方法，不断拼凑语句 挺麻烦的。）
    $(document).ready(function(){

		var deptAll= ["部门A","部门B","部门C","部门D"];

    	for(i=0;i<deptAll.length;i++){    		
    		var $option = $("<option  value='"+deptAll[i]+"' >"+deptAll[i]+"</option>");
    		$("select[name='user.dept']").append($option);	    		    		
    	}    	    	
    });
    
    
    var userNameFlag = false;
   	//难点二：因为状态量一直变化，函数不算一次执行到尾。反复触发的反复执行不是一次性从头到尾，所以不能用return【一定不能用return】。要用一个标记，这个标记不是靠这个函数retrun返回的。
   	//-----------------全局标记。
    //帐号唯一性效验。。.不只onchage的时候效验，更重要：提交时候调用这个效验函数，不然虽然填的时候提示，最终却可以随意提交。bug。
    function doVerifyUserName(){
    	$("#verifyUserName").html("");

    	var regex = /^[0-9a-zA-Z]+$/;
    	var $userName = $("input[name='user.userName']");
    	//局部刷新不要用插入新标签的做法。推荐定位插入内容
//     	var $span = $("<span style='color: red'>只能是数字或字母</span>");

    	if ( regex.test($userName.val()) ) {
			
        	var ajax=new XMLHttpRequest();
    		ajax.open("post", "${basePath}fw/user_verifyUserName.action");
    		ajax.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    		var content="user.userName="+$userName.val();
    		ajax.send(content);
    		/***********************************************/
    		//难点二：这个是监视状态变化的。一直在变。所以触发这个函数很多次。。不该用return 。return，就返回一次结果给调用者。
    		ajax.onreadystatechange=function()
    			{
    				if(ajax.readyState==4){	
    					if(ajax.status==200){
    						if(ajax.responseText=="yes"){
	   							$("#verifyUserName").html("帐号可用");    	
    							//return "yes";         //难点一：【java不可以函数中定义函数】js却可以。一点不严禁，神烦。那么这个return不是最外层的return。在submit校验有bug。
	   							//难点二：补充，虽然有4个ajax.readyState状态，触发4次，全局标记userNameFlag变化4次，最终是什么就是什么。
	   							//return的话，调用者只接该函数返回的第一个结果。。不会再接收重复返回的结果。（至少js是这样。java中不知道 ，能不能重复接收return返回值，覆盖然后获取的是最后一个return值。。）
    							userNameFlag=true;      
    						}else{						
    							$("#verifyUserName").html("帐号不可用，已经存在");
    							
    						}			
    					}
    				}
    			
    			}	
    		
    		//alert("thisFlag222222");  发现有时候先输出这个，证明程序不是一次执行到尾。一直监听状态，触发好几次function。
    		//如果用return ，先输出这个，那么就是先输出”false”。所以在submit中效验不成功。 
    	}else{
    		//插入标签的方式   有个bug。不断触发该函数就插入新的标签。【所以局部刷新的最好不要用插入标签的做法。】
    		//最好的处理是，直接在body中这个子标签后面加上<span id=>。直接定位该span写入值。
//     		$userName.after($span);
    		$("#verifyUserName").html("只能是数字或字母");
    	}
    	
		//jquery的ajx
//     	$.ajax(
//     		{
// 	    		type:"POST",
// 	    		url:"${basePath}fw/user_verifyUserName.action",
// 	    		content:{"user.userName":$userName.val()},
// 	    		success:function(backDate,textStatus,ajax){
	    					
// 	    					alert(backDate.userNameFlag);				
// 	    				}
//     		})

    }
    
    
    //提交效验  
    function doSubmit() {
    	//注意：""与null类型是不相等的。在前端js中。判断是否为空，应该用"";
    	//text()/html()是标签体文本内容。val()才是取 表单元素的value属性值。
    	//alert($("input[name='user.nickName']").val()==null);//结果为false。

//     	alert(document.getElementById(1111).value.trim());
//     	alert($("input[name='user.nickName']").val().trim());

//这种一次性判断全部，很繁琐[考虑只填了帐号，其他没填，该怎么弹窗提示！！]。。。直接分来判断还简单。
// 		if($("input[name='user.nickName']").val().trim()!=""){
// 			if($("input[name='user.userName']").val().trim()!=""){
// 				if($("input[name='user.password']").val().trim()!=""){
					
// 					document.forms[0].submit();
// 					return "";
// 				}
// 				alert("密码不能为空");
// 				return "";
// 			}
// 			alert("帐号不能为空");
// 			return "";
// 		}
// 		alert("用户名不能为空");

    	//js和java这强弱语言  一起用很容易搞混。。。
    	//对于'=='.java不可以用这来比较字符串的值，而是比较字符串地址值的。js却是用来比较字符串值的。天哟。
    	if($("input[name='user.nickName']").val().trim()==""){
    		alert("用户名不能为空");
    		return 'no';
		}
		
		if($("input[name='user.userName']").val().trim()==""){
			alert("帐号不能为空");
			return 'no';
		}
		

		if($("input[name='user.password']").val().trim()==""){
			alert("密码不能为空");
			return 'no';
		
		}
		
		//调用效验功能
		var passwordFlag = doVarifyPassword();
		doVerifyUserName();
		
		if(userNameFlag && passwordFlag){
			document.forms[0].submit();
		}else{
			alert("修正后再提交");
		}
		
		
	}
  		

  		//onclik	是当发生点击事件。就触发函数。
  		//onsubmit	当发送提交事件。
		//顺序：onsubmit----submit。 	就是说直接执行submit是不会触发onsubmit的事件。
		//不能这么写，submit()是执行提交功能并触发函数aa()。
//     $(":submit").submit(aa());   //对$(":submit")的标签元素执行提交。


	//密码一致性效验，同时也要放到sumit（）中。
	function doVarifyPassword() {
		
		var $password1 = $("input[name='user.password']").val();
		var $password2 = $("#password2").val();
		if($password2 ==$password1){
			//若是先错，后对，那么会一直显示"密码不一致"。所以当对的时候要  清除“不好的信息”。
			$("#varifypassword").html("ok");
			return true;
		}else{
			$("#varifypassword").html("密码不一致");
			return false;
		}
	}



    </script>
    
</head>

<body class="rightBody">

<form id="form" name="form" action="${basePath }fw/user_add.action" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>用户管理</strong>&nbsp;-&nbsp;新增用户</div></div>
    <div class="tableH2">新增用户</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">所属部门：</td>
            <td>
	 			<select name="user.dept" >
        	 		<option>请选择部门</option>
        	 	</select>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">头像：</td>
            <td>
                <input type="file" name="headImg"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">用户名：</td>
            <td><input type="text" name="user.nickName" id="1111"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">帐号：</td>
            <td><input type="text" name="user.userName" onchange="doVerifyUserName()" />
            	<span id="verifyUserName" style="color: red"></span>
            </td>
        </tr>
        <tr>
			<!--doPassword2()效验密码一致性，确认密码。 -->
            <td class="tdBg" width="200px">密码：</td>
            <td><input type="text" name="user.password" /></td>
        </tr>
        <tr>
			<!--属性name=user.password相同，struts封装的时候，不是以覆盖形式，而是两个<input>的值会一起封装到后台user.password中。 -->
			<!--所以第二个<input>框不需要name属性，只要作用是确认密码一致。 -->
            <td class="tdBg" width="200px">再输入一次密码：</td>
            <td><input type="text"  id="password2" onchange="doVarifyPassword()" />
            	<span id="varifypassword" style="color: red"></span>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">性别：</td>
            <td>
            	男<input type="radio" name="user.gender" value="男" />
       			女<input type="radio" name="user.gender" value="女">     	
			</td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色：</td>
            <td><c:forEach items="${roleList}" var="roleTemp"><input type="checkbox" name="roledIdRow" value="${roleTemp.roleId}" >${roleTemp.name}</c:forEach></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">电子邮箱：</td>
            <td><input type="text" name="user.email"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">手机号：</td>
            <td><input type="text" name="user.mobile"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">生日：</td>
            <td><input type="text" id="birthday" name="user.birthday"  
            		readonly="true"  onfocus="WdatePicker({skin:'whyGreen', el:'birthday',dateFmt:'yyyy-MM-dd'})" />
            </td>
        </tr>
		<tr>
            <td class="tdBg" width="200px">状态：</td>
            <td>
            	有效<input type="radio"  name="user.state" value="1"/>
            	无效<input type="radio"  name="user.state" value="0"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td><textarea name="user.memo" cols="75" rows="3"/></textarea></td>
        </tr>
    </table>
    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存"  onclick="doSubmit()"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>