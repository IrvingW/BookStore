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
	    }
    });
});
</script>

</body>
</html>