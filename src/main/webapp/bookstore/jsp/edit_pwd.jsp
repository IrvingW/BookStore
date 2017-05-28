<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<div class="col-lg-12">
			<form role="form" action="modify_pwd" method="post">
				<div class="form-group hide">
					<label>Old pwd</label> <input class="form-control" name="old_pwd" id="old_pwd">
				</div>
				<div class="form-group hide">
					<label>New pwd</label> <input class="form-control" name="new_pwd" id="new_pwd" disabled="disabled">
				</div>
				<input id="submit_btn" type="submit" class="btn btn-danger" disabled="disabled">
					
			</form>
						
		</div>

</body>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
<script type="text/javascript">
	$("#old_pwd").blur(function() {
			var old_pwd = $("#old_pwd").val();
			$.ajax({
			    type: "post",
			    url: "check_pwd.action",
			    data: { password : old_pwd },
			    success: function (result) {
			    	
			    	if(result == "login"){
			    		alert("login");
						window.location="sign_in.jsp"
			    	}
			    	else if(result == "right"){
				    	alert("ok");
				    	$("#new_pwd").removeAttr("disabled");
				    	$("#submit_btn").removeAttr("disabled");
				    }
			    	else if(result == "wrong"){
				    	alert("no");
				    	$("#new_pwd").attr("disabled","disabled");
				    	$("#submit_btn").attr("disabled","disabled");
				    }
			    }
		   });
	});
</script>
</html>