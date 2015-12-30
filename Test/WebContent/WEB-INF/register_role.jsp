<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#register").click(function(){
			var name=$("#name").val();
			var priority=$("#priority").val();
			if(name==""||priority<0||priority>10){
				return false;
			}
			var path="<%=request.getContextPath()%>";
			$.post(path+"/role/createRole.shtml",{name:name,priority:priority},function(data){
				if(data==="success"){
					alert("添加成功");
				}else{
					alert("添加失败");
				}
				
			});
			return true;
		});
	});
</script>

<title>Add Role</title>

</head>

<body>
	<p>Login</p>
	<form id="registerForm" method="post" action="<%=request.getContextPath()%>/user/register.shtml" >
		<table>
			<tr>
				<td>角色名字</td>
				<td><input type="text" id="name" name="name"/></td>
				<td><input type="text" id="priority" name="priority"/></td>
			</tr>
			<tr>
				<td><input type="button" value="添加" id="register"/></td>
			</tr>
		</table>
	</form>
</body>
</html>