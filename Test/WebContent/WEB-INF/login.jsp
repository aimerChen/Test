<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js"></script>
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
	<!-- <shiro:guest>
    Hi there!  Please <a href="login.jsp">Login</a> or <a href="signup.jsp">Signup</a> today!
	</shiro:guest> -->
	<form id="loginForm">
		<!-- action="/user/login.shtml" method="post" -->
		<table>
			<tr>
				<td>用户名</td>
				<td><input type="text" id="username" name="username" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" id="password" name="password" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="登录" /></td>
				<td><input type="button" value="注册" id="register" /></td>
			</tr>
		</table>
	</form>
</body>
</html>