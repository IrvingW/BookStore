<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册界面</title>
    <%
		String path = request.getContextPath();
	%>
    <link rel="stylesheet" href="<%= path %>/bookstore/css/bootstrap.min.css">
    <style>
        body{
            background:url("<%= path %>/bookstore/image/sign_up_bg.jpg") ;
            background-size: cover;
            background-repeat: no-repeat;
            backgroud-position:center;
            z-index: -1;
        }
        .row{
            padding-bottom: 15px;
        }
        #Sign_up{
            max-width: 800px;
            padding: 20px 100px 20px 50px;
            margin-top:50px;
            background:transparent;
            background-color:rgba(255,255,255,0.9);
            border-radius: 5px;
        }
        button{
            marign-bottom: 20px;
            float:right;
        }
        span .required{
            color: red;
            font-size: 150%;
        }
    </style>
</head>
<body>
<form role="form" class="form-horizontal" name="form1" method="post" action="userAction!sign_up" onsubmit="return submit_check()">
    <div class="container" id="Sign_up">

        <h1 style="padding-bottom: 30px;text-align:center">欢 迎 注 册</h1>
        <div class="form-group row " id="user_name_div">
            <label class="col-md-2 control-label">用户名<span class="required"> *</span></label>
            <div  class="col-md-10"  >
                <input class="form-control"  type="text" name="user_name" placeholder="用户名" autofocus="autofocus" maxlength="20" onchange="exsit();" >
				<div id="toolTip"></div>
            </div>
            
            <span class="icon"></span>
        </div> <!-- user name-->

        <div class="form-group row"  id="email_div" >
            <label class="col-md-2 control-label">邮箱<span class="required"> *</span></label>
            <div  class="col-md-10" >
                <input class="form-control" id="e-mail" type="email" name="email" placeholder="请输入邮箱" autofocus="autofocus" maxlength="30" onchange="email_check()">
            </div>
            <span class="icon"></span>
        </div> <!-- email-->

        <div class="form-group row" id="password_div">
            <label class="col-md-2 control-label">密码<span class="required"> *</span></label>
            <div  class="col-md-10">
                <input class="form-control"  type="password" name="password" placeholder="请输入密码" autofocus="autofocus" maxlength="20"  onchange="done('password_div')">

            </div>
            <span class="icon"></span>
        </div> <!-- password-->
        <div class="form-group row" id="password2_div">
            <label class="col-md-2 control-label">确认密码<span class="required"> *</span></label>
            <div  class="col-md-10">
                <input class="form-control"  type="password" name="password2" placeholder="请再次输入密码" autofocus="autofocus" maxlength="20"  onchange="check_password()">

            </div>
            <span class="icon"></span>
        </div> <!-- ensure password-->

        <div class="form-group row"  id="phone_div">
            <label class="col-md-2 control-label">手机</label>
            <div  class="col-md-10">
                <input class="form-control"  type="text" name="phone" placeholder="请输入手机号" autofocus="autofocus" maxlength="100" onchange="done('phone_div')">

            </div>
            <span class="icon"></span>
        </div> <!-- phone-->

        <div class="form-group " style="padding-bottom: 30px" id="address_div">
            <label class="col-md-2 control-label">地址</label>
            <div  class="col-md-10">
                <textarea class="form-control" name="address" rows="3" onchange="done('address_div')">
                </textarea>

            </div>
            <span class="icon"></span>
        </div><!-- address-->

        <div>
            <a href="#" style="margin-left: 120px ">用户协议</a>
            <button class="btn btn-success" type="submit">立即注册</button>
        </div>

    </div>

</form>
</body>
<script language="JavaScript" type="text/javascript">
    function email_check() {
        var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        var str = document.getElementById("e-mail").value;
        if(reg.test(str)){
            done("email_div");
        }else{
            alert("邮箱格式错误");
            var form = document.getElementById("email_div");
            form.setAttribute("class" ," form-group row has-warning has-feedback");
            var graph = form.lastElementChild;
            graph.setAttribute("class","glyphicon glyphicon-warning-sign form-control-feedback");
        }
    }

    function check_password() {
        var form = document.getElementById("password_div");
        var form2 = document.getElementById("password2_div");
        ///////////////
        var p1 = form1.password.value;
        var p2 = form1.password2.value;

        var graph = form2.lastElementChild;


        if(p1 == p2){
            form2.setAttribute("class" ," form-group row has-success has-feedback");
            graph.setAttribute("class","glyphicon glyphicon-ok form-control-feedback");
            return true;
        }
        else{
            form2.setAttribute("class" ," form-group row has-error has-feedback");
            graph.setAttribute("class","glyphicon glyphicon-remove form-control-feedback");
            alert("  输入密码不一致——请重新输入");
            return false;
        }

    }
    
    function exsit(){
    	//check if the username exited
    	var url='Sign_up_servlet?user_name='+form1.user_name.value;
    	http_request = false;
    	if (window.XMLHttpRequest) {    							   // 非IE浏览器
            http_request = new XMLHttpRequest();	                      //创建XMLHttpRequest对象
        } else if (window.ActiveXObject) {     						   // IE浏览器
            try {
                http_request = new ActiveXObject("Msxml2.XMLHTTP");	   //创建XMLHttpRequest对象
            } catch (e) {
                try {
                    http_request = new ActiveXObject("Microsoft.XMLHTTP");  //创建XMLHttpRequest对象
               } catch (e) {}
            }
        }
        if (!http_request) {
            alert("不能创建XMLHttpRequest对象实例！");
            return false;
        }
        http_request.onreadystatechange = getResult;					   //调用返回结果处理函数
        http_request.open('GET', url, true);							   //创建与服务器的连接
        http_request.send(null);									   //向服务器发送请求
    }
    
    function getResult(){
    	if (http_request.readyState == 4) {     		
            if (http_request.status == 200) {
            	var info = http_request.responseText;
            	if(info.indexOf("ok")!=-1){
            		
            		document.getElementById("toolTip").innerHTML="用户名创建成功";
            		document.getElementById("toolTip").style.color = "green";
            		done('user_name_div');
            	}
            	else{
            		document.getElementById("toolTip").innerHTML="用户名已存在，请更换";
            		document.getElementById("toolTip").style.color = "red";
            		
            		var form = document.getElementById("user_name_div");
                    form.setAttribute("class" ," form-group row has-error has-feedback");
                    var graph = form.lastElementChild;
                    graph.setAttribute("class","glyphicon glyphicon-remove form-control-feedback");
                    
            	}
            	
            	
                
            } else {     						// 请求页面有错误
                alert("您所请求的页面有错误！");
            }
        }
    }

    function done(id) {
        var form = document.getElementById(id);
        form.setAttribute("class" ," form-group row has-success has-feedback");
        var graph = form.lastElementChild;
        graph.setAttribute("class","glyphicon glyphicon-ok form-control-feedback");

    }

    function submit_check() {
        var form = document.getElementById("user_name_div");
        var input = form1.user_name.value;
        if(input == ""){
            alert("用户名不能为空！");
            return false;
        }

        var form = document.getElementById("email_div");
        var input = form1.email.value;
        if(input == ""){
            alert("邮箱不能为空！");
            return false;
        }

        var form = document.getElementById("password_div");
        var input = form1.password.value;
        if(input == ""){
            alert("密码不能为空！");
            return false;
        }

        var form = document.getElementById("password2_div");
        var input = form1.password2.value;
        if(input == ""){
            alert("请确认密码！")
            return false;
        }
        
        var exsited = document.getElementById("toolTip");
        if (exsited.style.color == "red"){
        	alert("用户名已被注册\n请更换用户名");
        	return false;
        }

        return check_password();
        
        
        
	
    }
</script>  <!--check if the two password is the same-->
</html>