<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#register").click(function(){
			var name=$("#name").val();
			var password=faultylabs.MD5($("#password").val());
			if(name==""||password==""){
				return false;
			}
			if($("#password").val()==$("#repeatPassword").val()){
				var path="<%=request.getContextPath()%>";
				$.post(path+"/user/register.shtml",{name:name,password:password},function(data){
					if(data==="success"){
						window.location.href=path+"/user/login.shtml";
					}else{
						alert("注册失败");
					}
				});
				return true;
			}else{
				alert("密码不一致");				
			}
			return false;
		});
	});
</script>

<title>Login</title>

</head>

<body>
	<p>Login</p>
	<form id="registerForm" method="post" action="<%=request.getContextPath()%>/user/register.shtml" >
		<table>
			<tr>
				<td>用户名</td>
				<td><input type="text" id="name" name="name"/></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" id="password" name="password"/></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" id="repeatPassword" name="repeatPassword"/></td>
			</tr>
			<tr>
				<td><input type="button" value="注册" id="register"/></td>
			</tr>
		</table>
	</form>
</body>
</html>