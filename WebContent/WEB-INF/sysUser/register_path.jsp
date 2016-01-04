<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	var requestPath="<%=request.getContextPath()%>";
	
	//删除和编辑Path
	function deletePathById(id){
		$.post(requestPath + "/sysUser/deletePath.shtml",{id:id},function(data) {
			if (data==1) {
				alert("删除成功");
				$("#deletePath_"+id).closest("tr").remove();
			}else{
				alert("删除失败");
			}
		});
	}
	//删除和编辑Path
	function editPathById(id){

	}
	
	function addClickEvent(){
		$("tr td label").unbind("click"); 
		$("tr td label").click(function(event){
			var label=event.target;
			var value=$(label).text();
			$(this).parent("td").before("<td><input type='text' value='"+value+"' id='updatePath_'"+1+"/></td>");
			$(this).css("display","none");
		});
	}
	
	function getPathList(){
		$.post(requestPath + "/sysUser/showAllPaths.shtml", function(data) {
			if (data !== null) {
				var tempStr = eval(data);
				var html="<tr><td><h1>路径列表</h1></td></tr>";
			    $.each(tempStr, function(index,item){
					html+="<tr>"
						+"<td><label id='PathNameLable_"+item.id+"'>"+item.name+"</label></td>"
						+"<td><label id='PathTimeLable_"+item.id+"'>"+item.createTime+"</label></td>"
						+"<td><input type='button' id='EditPath_"+item.id+"' onclick='editPathById("+item.id+")' value='编辑'/></td>"
						+"<td><input type='button' id='deletePath_"+item.id+"' onclick='deletePathById("+item.id+")' value='删除'/></td>"
						+"</tr>";
		        });
				$("#pathTable").html(html);
				addClickEvent();//添加事件
			}
		});
	}
	$(document).ready(function(){
		getPathList();
		
		$("#register").click(function() {
			var pathStr = $("#name").val();
			if (pathStr == "") {
				alert("路径不能为空");
				return false;
			}
			$.post(requestPath + "/sysUser/createPath.shtml", {
				pathStr : pathStr
			}, function(data) {
				if (data >= 1) {
					alert("添加成功");
					$("#name").val("");
					getPathList();
				} else if (data === 0) {
					alert("名字存在，添加失败");
				} else {
					alert("内部错误，添加失败");
				}
			});
			return false;
		});
		
	});
</script>

<title>Add Path</title>

</head>

<body>
	<div style="margin-left:30%;">
		<h1>Add Path</h1>
	</div>
	<div style="float: left; width: 20%">
		<h1>添加路径</h1>
		<form>
			<table>
				<tr>
					<td>路径名字</td>
					<td><input type="text" id="name" name="name" /></td>
				</tr>
				<tr>
					<td><input type="button" value="添加" id="register" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div style="float: right; width: 50%">
		<table id="pathTable">
		</table>
	</div>
</body>
</html>