<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/bootstrap.css"/>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#loginForm").submit(function(e){
			var username=$("#username").val();
			var password=faultylabs.MD5($("#password").val());
			if(username==""||password==""){
				return false;
			}
			var path="<%=request.getContextPath()%>";
			$.post(path+"/user/login.shtml",{username:username,password:password},function(data){
				if(data==="success"){
					window.location.href=path+"/index.shtml";
				} else {
					alert(data);
				}
			});
			return false;
		});
		$("#register").click(function() {
			var ui = "<%=request.getContextPath()%>";
			window.location.href = ui + "/user/register.shtml";
		});
	});
</script>
</head>

<body>
	<div>
		<form class="form-inline" id="loginForm" style="margin:20px;">
			<div class="form-group">
				<label for="username">用户名</label> <input type="text"
					class="form-control" id="username" placeholder="Jane Doe">
			</div>
			<div class="form-group">
				<label for="password">密码</label> <input type="password"
					class="form-control" id="password"
					placeholder="jane.doe@example.com">
			</div>
			<button type="submit" class="btn btn-default">登录</button>
			<button type="button" id="register" class="btn btn-default">注册</button>
		</form>
	</div>
</body>
</html>