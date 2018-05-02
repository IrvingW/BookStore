<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style>
        .header {
            text-align: center;
            margin-top: 1em;
        }
        /*logo*/
        .logo h1{
            font-family: 'Montserrat', sans-serif;
            font-size: 3em;
            color: #000;
            display: inline-block;
            position:relative;
        }
        .logo h1 a{
            text-decoration:none;
            color: #000;
        }
        .logo h1 span {
            font-size: 12px;
            display: block;
            letter-spacing: 4px;
            text-transform: uppercase;
            font-family: 'Noto Sans', sans-serif;
            padding-top: 4px;
        }
        .logo h1  b {
            font-size: 8px;
            background: #ED0612;
            font-weight: normal;
            padding: 3px;
            display: inline-block;
            position: absolute;
            top: 8px;
            left: -2px;
            color: #fff;
            line-height: 8px;
        }
        .head-t {
            margin: 1em 0;
        }
        /*cart and log in*/
        .card li{
            display:inline-block;
        }
        .card li a{
            display:inline-block;
            color: #999;
            font-size: 0.9em;
            margin:0 0.5em;
        }
        .card li a i{
            margin-right:5px;
            color: #FAB005;
        }
        /*--banner--*/
        .banner-info {
            margin:8em 0;
            text-align:center;
        }
        .banner-info h3 {
            color: #fff;
            font-size: 3em;
            width: 80%;
            margin: 0 auto 1em;
        }
        /*--search form--*/
        .search-form {
            width: 55%;
            margin: 0 auto;
        }
        .search-form input[type='text']{
            width: 93%;
            padding: 10px;
            outline: none;
            font-size: 1em;
            color: #fff;
            border: 1px solid #FFF;
            background: rgba(255, 255, 255, 0.45);
        }
        .search-form input[type='submit']{
            width: 40px;
            height: 41px;
            outline: none;
            border: 1px solid #fff;
            background: url(/images/can.png) no-repeat 6px 7px #FCCD1E;
            display: inline-block;
        }
        .search-form input[type='submit']:hover {
            background: url(/images/can.png) no-repeat 6px 7px #039445;
            transition: 0.5s all;
            -webkit-transition: 0.5s all;
            -moz-transition: 0.5s all;
            -o-transition: 0.5s all;
            -ms-transition: 0.5s all;
        }
    </style>
<%
	String path = request.getContextPath();
%>
<link href="<%=path%>/bookstore/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/bookstore/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
	
</head>

<body>
    <div class="container header">
        <div class="logo">
            <h1 ><a href="home.jsp"><b>T<br>H<br>E</b>E-Book Store<span>To Find your next favourite book</span></a></h1>
        </div>
        <div class="head-t" align="right">
            <ul class="card">
            	<%
            		String btn = "";
            		String myName = (String)session.getAttribute("user_name");
            		if(myName == null) {
            			myName="Sign In";
            			btn = "onclick=\"log_in()\"";
            		}
            	%>
	        
                <li><a href="#" data-toggle="modal" data-target="#profile" <%= btn %>><i class="fa fa-user fa-fw" aria-hidden="true"></i><%= myName %></a></li>
                <li><a href="" onclick="return sign_out();"><i class="fa fa-arrow-right fa-fw" aria-hidden="true"></i>Sign Out</a></li>
                <li><a href="languageAction!switch_lang.action?lang=zh"><i class="fa fa-arrow-right fa-fw" aria-hidden="true"></i>Switch　Language</a></li>
            </ul>
        </div>
        <!--navigate bar-->
        <script src="<%=path%>/bookstore/js/jquery.min.js"></script>
        <script src="<%=path%>/bookstore/js/bootstrap.min.js"></script>
   </div>

   <div class="modal fade" id="profile">
    <div class="modal-dialog">
        <div class="modal-content">
        	<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">${user_name }'s profile</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<img id="Imgbox" height="150px" width="150px" style="margin:8px auto;">
						·<form role="form">			
							<div class="form-group hide">
								<label>Name</label> <input class="form-control" name="pre_name"  id="pre_name">
							</div>							
							<div class="form-group">
								<label>Name</label> <input class="form-control" name="user_name" disabled="true" id="name">
							</div>
							<div class="form-group">
								<label>Phone</label> <input class="form-control"  name="phone" disabled="true" id="phone">
							</div>
							<div class="form-group">
								<label>Email</label> <input class="form-control" name="email" disabled="true" id="email">
							</div>
							<div class="form-group">
								<label>Address</label> <input class="form-control" type="text" name="address" disabled="true" id="addr">
							</div>		
						</form>
						<button class="btn btn-default"><a href="edit_profile.jsp">Resume</a></button>
						<button class="btn btn-default"><a href="edit_pwd.jsp">Change the password</a></button>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>   

   

</body>
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
	    	document.getElementById("pre_name").setAttribute("value",obj["name"]);
			document.getElementById("name").setAttribute("value",obj["name"]);
			document.getElementById("phone").setAttribute("value",obj["phone"]);
			document.getElementById("email").setAttribute("value",obj["email"]);
			document.getElementById("addr").setAttribute("value",obj["addr"]);
			// download portrait file
			var download_url = "fileAction!download_portrait.action?user_name=" + obj["name"];
			document.getElementById("Imgbox").setAttribute("src",download_url);
	    }
    });
});

function sign_out() {
	var user = "<%=session.getAttribute("user_name")%>";
	if(user == "null"){
		alert("You have not signed in yet.");
		return false;
	}
	$.ajax({
	    type: "post",
	    url: "userAction!sign_out.action",
	    data: { user_name: user },
	    success: function (data) {
			if(data == "success")
				window.location.replace("home.jsp") 
			else
				alert("failed");
	    }
    });
}

function switch_lan(){
	window.location="languageAction!switch_lang.action?lang=zh";
}

function log_in() {
	window.location="sign_in.jsp";
	
}
</script>
</html>