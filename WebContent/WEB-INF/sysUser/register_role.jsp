<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var requestPath="<%=request.getContextPath()%>";
		$("#register").click(function(){
			var name=$("#name").val();
			var path=$("#path").val();
			if(name==""||path==""){
				Alert("名字或者路径不能为空");
				return false;
			}
			$.post(requestPath+"/sysUser/createRole.shtml",{name:name,path:path},function(data){
				if(data===1){
					alert("添加成功");
				}else if(data===0){
					alert("名字存在，添加失败");
				}else{
					alert("内部错误，添加失败");
				}
			});
			return false;
		});
	});
</script>

<title>Add Role</title>

</head>

<body>
	<p>Login</p>
	<form>
		<table>
			<tr>
				<td>角色名字</td>
				<td><input type="text" id="path" name="path"/></td>
				<td>=</td>
				<td><input type="text" id="name" name="name"/></td>
			</tr>
			<tr>
				<td><input type="button" value="添加" id="register"/></td>
			</tr>
		</table>
	</form>
</body>
</html>