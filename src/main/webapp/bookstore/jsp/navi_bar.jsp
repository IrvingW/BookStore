<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String path = request.getContextPath();
%>
<link href="<%=path%>/bookstore/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
<style type="text/css">
	.autocomplete{ 
		border: 1px solid #9ACCFB; 
		background-color: white; 
		text-align: left; 
	} 
	.autocomplete li{ 
		list-style-type: none;
	}
	.item:hover{
		background-color: #9ACCFB;
	}
	.clickable { 
		cursor: default; 
	}  
</style>
</head>
<body>
<div >
      <nav class="navbar navbar-default" >
                <div class="navbar-header">
                    　    <a href="##" class="navbar-brand">Home</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="##">小说</a></li>
                    <li class="dropdown">
                        <a href="##" data-toggle="dropdown" class="dropdown-toggle">童书<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="##">绘本</a></li>
                            <li><a href="##">科普百科</a></li>
                            <li class="disabled"><a href="##">文学</a></li>
                        </ul>
                    </li>
                    <li><a href="##">教育</a></li>
                    <li><a href="##">人文社科</a></li>
                    <li><a href="##">理工科</a></li>
                    <li><a href="##">生活</a></li>
                </ul>
                
            </nav>
            
</div>
<div style="position:fixed;width:300px;height:30px;top:155px;right:80px;" id="test"  >
	<form class="navbar-form navbar-right" role="form" action="bookAction!search" method="post" >
		<div class="form-group">                  
			<input name="name" class="form-control" id="search" placeholder="请输入关键词" onkeyup="showHint(this.value)" autocomplete="off">
			<input type="submit" class="btn btn-success" value="search">			    
			<div class="autocomplete hide" id="hints"></div>    
		</div>                    
	</form>
</div>
        


<script type="text/javascript">
/*
	$("#search").blur(function(){ 
		$("#hints").slideUp();
		$("#hints").html("");
		$("#hints").hide;
	});
*/
	function showHint(str) {
		if(str ==""){
			$("#hints").html(""); //删除原有数据
			$("#hints").addClass("hide");
	   		return;
		}
		$.ajax({
		    type: "post",
		    url: "showHint.action",
		    data: { name: str },
		    success: function (strValue) {
			    $("#hints").html(""); //删除原有数据
			     if (strValue != "") {
			    	var list = strValue.split(";");
			      	for (var i = 0; i < $(list).length; i++) {	    	  
			       		$("#hints").append('<li class="item clickable" onclick="mousedown(this)">' + list[i] + '</li>');
			      	}
			     	$("#hints").removeClass('hide') 
			     	$("#hints").slideDown();	      
		     	}
		    }
	   	});
	}
	
	  //选择其中的提示内容
	  function mousedown(object) {
	   $("#search").val($(object).text());
	   $("#hints").fadeOut();
	  }
	  //文档框失去焦点，隐藏提示内容
	  function lost() {
	   $("#hints").fadeOut();
	  }
	
</script>

</body>
</html>