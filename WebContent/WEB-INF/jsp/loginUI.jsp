<%@page language="java" contentType="text/html; charset=utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link href="${pageContext.request.contextPath}/css/login/style.css"
		rel='stylesheet' type='text/css' />
<!-- 	<meta name="viewport" content="width=device-width, initial-scale=1"> -->
	<script type="text/javascript">
	
	//---》http://localhost:8080/Business/sys/login_login.action
// 	alert(window.location)  
	
	//---》http://localhost:8080/Business/sys/home_fwframe.action
// 	alert(window.parent.location)  
	
	
	if(window.parent!=window){	
// 		为什么是重新加载标签呢？
// 		因为父标签的请求是要登录之后才可以访问的。所以重新加载，执行filter中的 方法。看下有没 user在session中。
		window.parent.location.reload(true);
		
// 		这个会导致无限重载。因为sys/login_login.action是放行的。重载这个就是重新执行login()。
// 		window.location.reload(true);
	}
	</script>

</head>
<body>

	<div class="main">
		<div class="header">
			<h1>1登录帐123户</h1>
		</div>
		<p>${falseLogin}</p>
		<form action="${pageContext.request.contextPath}/sys/login_login.action" method="post">
			<center>
				<ul class="right-form">
					<div>
<!-- 					两个实用的属性placeholder="Username"、required -->
						<li><input type="text" placeholder="Username" required  name="user.userName"/></li>
						<li><input type="password" placeholder="Password" required name="user.password"/></li>
						<input type="submit"  value="登录">
					</div>
				</ul>
			</center>
		</form>
	</div>

</body>
</html>