<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	var requestPath="<%=request.getContextPath()%>";
	
	//删除Path
	function deletePathById(id){
		$.post(requestPath + "/sysUser/deletePathById.shtml",{id:id},function(data) {
			if (data ===1) {
				$("#deletePath_"+id).closest("tr").remove();
			}else{
				alert("删除失败");
			}
		});
	}
	//提交更新了的Path
	function submitEditPathById(id){
		var nameBefore=$("#pathNameLabel_"+id).text();
		var nameAfter=$("#pathEditNameLabel_"+id).val();
		var object =$("#pathLabelTr_"+id);
		var objectEdit =$("#pathEditTr_"+id);
		if(nameBefore!==nameAfter&&nameAfter!=""){
			$.post(requestPath+"/sysUser/updatePathById.shtml",{id:id,name:nameAfter},function(data){
				if(data===1){
					$("#pathNameLabel_"+id).text(nameAfter);
					$(objectEdit).css("display","none");
					$(object).css("display","block");
				}else{
					alert("更新失败");
					return;
				}
			});
		}else{
			$(objectEdit).css("display","none");
			$(object).css("display","block");
		}
	}
	
	
	function changeModel(model,id){
		var object =$("#pathLabelTr_"+id);
		var objectEdit =$("#pathEditTr_"+id);
		if(model===1){//转换到编辑模式
			$(object).css("display","none");
			$(objectEdit).css("display","block");
		}else{//转换到显示模式
			$(objectEdit).css("display","none");
			$(object).css("display","block");
		}
	}
	
	//获取所有的list
	function getPathList(){
		$.post(requestPath + "/sysUser/showAllPaths.shtml", function(data) {
			if (data !== null) {
				var tempStr = eval(data);
				var html="<tr><td><h1>路径列表</h1></td></tr>";
			    $.each(tempStr, function(index,item){
					html+="<tr id='pathLabelTr_"+item.id+"'>"
						//+"<td><label id='pathIdLabel_"+item.id+"'>"+item.id+"</label></td>"
						+"<td><label id='pathNameLabel_"+item.id+"'>"+item.name+"</label></td>"
						+"<td><label id='pathCreateTimeLabel_"+item.id+"'>"+item.createTime+"</label></td>"
						+"<td><input type='button' id='editPath_"+item.id+"' value='编辑' onclick='changeModel(1,"+item.id+")'/></td>"
						+"<td><input type='button' id='deletePath_"+item.id+"' onclick='deletePathById("+item.id+")' value='删除'/></td>"
						+"</tr>";
					html+="<tr id='pathEditTr_"+item.id+"' style='display:none'>"
						+"<td><input id='pathEditNameLabel_"+item.id+"' type='text' value='"+item.name+"'/></td>"
						+"<td><input type='button' id='submitEditPath_"+item.id+"' onclick='submitEditPathById("+item.id+")' value='提交'/></td>"
						+"<td><input type='button' id='cancelEditPath_"+item.id+"'  onclick='changeModel(2,"+item.id+")' value='取消'/></td>"
						+"</tr>";
		        });
				$("#pathTable").html(html);
			}
		});
	}
	
	$(document).ready(function(){
		getPathList();
		$("#register").click(function() {
			var pathStr = $("#name").val();
			if (pathStr == "") {
				Alert("路径不能为空");
				return false;
			}
			$.post(requestPath + "/sysUser/createPath.shtml", {
				pathStr : pathStr
			}, function(data) {
				if (data >= 1) {
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