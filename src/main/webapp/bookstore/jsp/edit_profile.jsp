<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="col-lg-6 col-md-offset-3">

		<img id="profile_img" height="150px" width="150px" style="margin:8px auto;">
		
		<div style="margin-top: 50px;" id="introduce">
			<h3>个人介绍</h3>
		</div>
		<button class="btn btn-success" onclick="edit_intro();">修改个人介绍</button>
		
		<div style="margin-top: 50px;" id="edit_area" class="hide">
			<div id="editor" >
		        <p>欢迎使用 <b>wangEditor</b> 富文本编辑器</p>
		     </div>
			<button class="btn btn-success" style="margin-top: 20px;" onclick="send_intro()">提交</button>
		</div>
		
		<div  style="margin-top: 50px;">
			<h3>基本信息</h3>
			<form role="form" action="update_pro" method="post" enctype="multipart/form-data">
			<div>
				<label>点击上传新头像</label><input type="file" name="file">
			</div>			
			<div class="form-group hide">
				<label>Name</label> <input class="form-control" name="pre_name"  id="pre_name_input">
			</div>							
			<div class="form-group">
				<label>Name</label> <input class="form-control" name="user_name" id="name_input">
			</div>
			<div class="form-group">
				<label>Phone</label> <input class="form-control"  name="phone" id="phone_input">
			</div>
			<div class="form-group">
				<label>Email</label> <input class="form-control" name="email" id="email_input">
			</div>
			<div class="form-group">
				<label>Address</label> <input class="form-control" type="text" name="address"id="addr_input">
			</div>
			<button class="btn btn-success" type="submit">提交</button>
		
			</form>	
		</div>		
</div>


<%
	String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/bookstore/js/wangEditor.min.js"></script>
<script type="text/javascript">
	// rich text editor
	var E = window.wangEditor
	var editor = new E('#editor')
	editor.create()
	
	function send_intro(){
		var intro = editor.txt.html();
		var user_name = "<%= session.getAttribute("user_name")%>";
		var url = "userAction!saveIntro.action?user_name=" + user_name + "&introduce=" + intro;
	    window.location.href=url;
	}
	
	function edit_intro(){
		$("#edit_area").removeClass();
	}
	
</script>


<script type="text/javascript">
$(document).ready(function() {
	var myName="<%=session.getAttribute("user_name")%>";
	
	if(myName == "null")
		return;
	
	$.ajax({
	    type: "post",
	    url: "profile.action",
	    data: { user_name: myName },
	    success: function (data) {
	    	var obj = eval("("+data+")");
	    	document.getElementById("pre_name_input").setAttribute("value",obj["name"]);
			document.getElementById("name_input").setAttribute("value",obj["name"]);
			document.getElementById("phone_input").setAttribute("value",obj["phone"]);
			document.getElementById("email_input").setAttribute("value",obj["email"]);
			document.getElementById("addr_input").setAttribute("value",obj["addr"]);
			// download portrait file
			var download_url = "fileAction!download_portrait.action?user_name=" + obj["name"];
			document.getElementById("profile_img").setAttribute("src",download_url);
			$("#introduce").append(obj["intro"]);
	    }
    });
});


</script>

</body>
</html>