<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆界面</title>

	<%
		String path = request.getContextPath();			
			
 	%>
    <link rel="stylesheet" href="<%=path %>/bookstore/css/bootstrap.min.css">
    <style>
        body{
            background:url("<%=path %>/bookstore/image/sign_in_bg.jpg") ;
            background-size: cover;
            background-repeat: no-repeat;
            backgroud-position:center;
            z-index: -1;
        }
        #forget{
            margin-left: 100px;}
        #mysign{
            margin-top:170px;
            float:right;
            margin-right: 280px;
            max-width: 300px;

            padding:40px 30px 45px 30px;
            background:transparent;
            background-color:rgba(255,255,255,0.8);
            border-radius: 5px;
        }

        .mybutton{
            width:240px;
            margin-top: 10px;
            padding-top: 10px;
        }
        .mycheck{
            padding-top: 10px;
        }
        
        #toolTip{
        	display:none;
        	color:red;
        	margin-top:5px;
        	margin-buttom:5px;
        }

    </style>
</head>
<body>
<form role="form " action="userAction!sign_in" method="post" onsubmit="return signInCheck()">
    <div class="container" id="mysign">
    	<div id="toolTip"></div>
    	<!-- use to give log in tip -->
    	        
        <div class="form-group " >
            <label >登录名：</label>
            <input name="user_name"  type="text" class="form-control" placeholder="用户名 /邮箱 " id="user_name">
        </div>

        <div class="from-group " >
            <label>密码：</label>
             <input name="password" type="password" class="form-control" placeholder="请输入密码" id="pwd">
        </div>
        <div class="checkbox mycheck" >
            <label>
                <input type="checkbox" id="remember" onclick="rem()"><p>记住我</p>
            </label>
            <a href="#" id="forget" >忘记密码</a>
        </div>
        <div>
            <button class="btn btn-success mybutton" type="submit">登陆</button>
        </div>
        <div>
            <a class="btn btn-default mybutton" href="sign_up.jsp">注册</a>
        </div>


    </div>
</form>
</body>
<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    //记住密码功能
    var username="";
    var password="";
	var strCookie = document.cookie;    // 获取cookie字符串
    // alert(strCookie);               
     var arrCookie = strCookie.split(";");        //从分号的位置 分割字符串strCookie为字符串数组
    // alert(arrCookie);              
     var userId;
     //遍历cookie数组，处理每个cookie对
     for(var i=0;i<arrCookie.length;i++){	
         var arr = arrCookie[i].split("=");           //从 = 的位置 分割每对cookie

         if( arr[0] == "username" ){      
             username = arr[1];           
         }
         if( arr[0] == " password" ){      
             password = arr[1];         
             break;
         }
     }
    
    //自动填充用户名和密码
    if((username != null && password != null) && (username != "" && password != "")){
        $("#user_name").val(username);
        $("#pwd").val(password);
        $("#remember").attr("checked","checked");
        
    }

});	
</script>

<script language="JavaScript" type="text/javascript">
$(document).ready(function() {
	var login="<%=request.getAttribute("login")%>";
	
	if(login == "null")
		return;
		
	alert("登陆未成功！请重新登陆")
});



	//记住密码功能
	
	function setCookie(){
		 var username = document.getElementById("user_name").value;
	     var password = document.getElementById("pwd").value;
	     document.cookie = "username="+username;
	     document.cookie = "password="+password;
	}

	function rem(){
		var remFlag = $("input[type='checkbox']").is(':checked');
		if(remFlag==true){ //如果选中设置remFlag为1
		    //cookie存用户名和密码,回显的是真实的用户名和密码,存在安全问题.
		    var conFlag = confirm("记录密码功能不宜在公共场所(如网吧等)使用,以防密码泄露.您确定要使用此功能吗?");
		    if(conFlag){ //确认标志
		    	setCookie();
		    }else{
		        $("input[type='checkbox']").removeAttr('checked');
		    }
		}
		else{ //remove cookie
			document.cookie = "username="+"";
		    document.cookie = "password="+"";
		}
	}


    function getResult(){
        if(http_request.readyState == 4){
            if(http_request.status == 200){
                var tip = http_request.responseText;
                showTip(tip);

            }else{
                alert("request page wrong")
            }
        }
    }

    function showTip(tip){

        document.getElementById("toolTip").innerHTML = tip;
        document.getElementById("toolTip").style.display="block";
    }

    function signInCheck(){
        var user = document.getElementById("user_name").value;
        var password = document.getElementById("pwd").value;
        var tip;

        if(user == ""){
            tip = "请输入用户名";
            showTip(tip);
            document.getElementById("user_name").focus();
            return false;
        }
        else if(password ==""){
            tip = "请输入密码";
            showTip(tip);
            document.getElementById("pwd").focus();
            return false;
        }        
        return;
    }
</script>
</html>