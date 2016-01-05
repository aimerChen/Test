<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

	var requestPath="<%=request.getContextPath()%>";
	
	function changeModel(model,id){
		var object =$("#roleLabelTr_"+id);
		var objectEdit =$("#roleEditTr_"+id);
		if(model===1){//转换到编辑模式
			$(object).css("display","none");
			$(objectEdit).css("display","block");
		}else{//转换到显示模式
			$(objectEdit).css("display","none");
			$(object).css("display","block");
		}
	}
	
	//提交更新了的Role
	function submitEditPathById(id){
		var nameBefore=$("#roleNameLabel_"+id).text();
		var nameAfter=$("#roleEditNameLabel_"+id).val();
		var object =$("#roleLabelTr_"+id);
		var objectEdit =$("#RoleEditTr_"+id);
		var pathListId="";//pathListId：id之间以逗号隔开
		if(nameBefore!==nameAfter&&nameAfter!=""){
			$.post(requestPath+"/sysUser/updateRoleById.shtml",{id:id,name:nameAfter,pathListId:pathListId},function(data){
				if(data===1){
					$("#roleNameLabel_"+id).text(nameAfter);
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
	
	//获取所有的Role
	function getAllRoles(){
		$.post(requestPath + "/sysUser/findAllRoles.shtml", function(data) {
			if (data !== null) {
				var tempStr = eval(data);
				var html="<table><tr><td><h1>角色列表</h1></td></tr>";
			    $.each(tempStr, function(index,item){
					html+="<tr id='roleLabelTr_"+item.id+"'>"
						+"<td><label id='roleNameLabel_"+item.id+"'>"+item.name+"</label></td>"
						+"<td><label id='roleCreateTimeLabel_"+item.id+"'>"+item.createTime+"</label></td>"
						+"<td><input type='button' id='editRole_"+item.id+"' value='编辑' onclick='changeModel(1,"+item.id+")'/></td>"
						+"<td><input type='button' id='deleteRole_"+item.id+"' onclick='deleteRoleById("+item.id+")' value='删除'/></td>"
						+"</tr>";
					html+="<tr id='roleEditTr_"+item.id+"' style='display:none'>"
						+"<td><input id='roleEditNameLabel_"+item.id+"' type='text' value='"+item.name+"'/></td>"
						+"<td><input type='button' id='submitEditRole_"+item.id+"' onclick='submitEditRoleById("+item.id+")' value='提交'/></td>"
						+"<td><input type='button' id='cancelEditRole_"+item.id+"'  onclick='changeModel(2,"+item.id+")' value='取消'/></td>"
						+"</tr>";
		        });
			    html+="</table>";
				$("#rolesTableDiv").html(html);
			}
		});
	}
	
	//删除Role
	function deleteRoleById(id){
		$.post(requestPath + "/sysUser/deleteRoleById.shtml",{id:id},function(data) {
			if (data ===1) {
				$("#roleLabelTr_"+id).remove();
				$("#roleEditTr_"+id).remove();
			}else{
				alert("删除失败");
			}
		});
	}
	
	//在table的第row行后加入一行
  	function addTr(tab, row, trHtml){
	     //获取table最后一行 $("#tab tr:last")
	     //获取table第一行 $("#tab tr").eq(0)
	     //获取table倒数第二行 $("#tab tr").eq(-2)
	     var $tr=$("#"+tab+" tr").eq(row);
	     if($tr.size()==0){
	        alert("指定的table id或行数不存在！");
	        return;
	     }
	     $tr.after(trHtml);
	  }
		   
	
	//所有的路径
	function findAllPaths(){
		$.post(requestPath+"/sysUser/showAllPaths.shtml",function(data){
			if (data !== null) {
				var tempStr = eval(data);
				var html="";
			    $.each(tempStr, function(index,item){
			    	if(index%3===0){
						html+="<tr>"
			    	}
			    	html+="<td id='pathLabelTd_"+item.id+"'><label id='pathIdLabel_"+item.id+"' style='display:none'>"+item.id+"</label>"
						+"<input type='checkbox' class='checkboxPathClass' id='pathCheckBox_"+item.id+"'/>"
						+"<label id='pathNameLabel_"+item.id+"'>"+item.name+"</label></td>";
			    	if(index===tempStr.length-1||(index%3===2)){
						html+="</tr>";
			    	}
		        });
				$("#firstTR").after(html);
			}
		});
	}
	
	$(document).ready(function(){

		findAllPaths();
		getAllRoles();
		
		$("#register").click(function(){
			var name=$("#name").val();
			var idList=new Array();
			$(".checkboxPathClass").each(function(index,item){
				if(document.getElementById($(item).attr("id")).checked===true){
					idList.push($(item).attr("id").split("_")[1]);			
				}
			});
			if(name==""){
				alert("角色名字不能为空");
				return false;
			}
			$.post(requestPath+"/sysUser/createRole.shtml",{name:name,pathIdList:idList.toString()},function(data){
				if(data===1){
					alert("添加成功");
					getAllRoles();
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
	<div style="float:left;width:30%;border:1px;">
		<h1>添加角色</h1>
		<form>
			<table id="addRoleTable" >
				<tr id="firstTR">
					<td>角色名字</td>
					<td><input type="text" id="name" name="name"/></td>
					<td><input type="button" value="添加" id="register"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="float:right;width:50%;" id="rolesTableDiv">

	</div>
</body>
</html>