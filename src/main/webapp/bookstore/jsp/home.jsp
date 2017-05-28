<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.product {
    padding: 3em 0 0em;
    }
    
  	.box{float:left; width:220px; height:270px; margin-left:5px; margin-bottom:10px; border:1px solid #e0e0e0; text-align:center;padding: 10px;} 
	.box p{line-height:20px; padding:4px 4px 10px 4px; text-align:left} 
	.box:hover{border:1px solid #f90} 
	.box h4 span{font-size:20px} 
	.box em {line-height:32px; font-size:14px; color:#f30;font-weight:500}
	.title{font-size: 15px;font-family: cursive;}
	.title:HOVER{font-size:16px; color:#f30;}
	.u-flyer{display: block;width: 50px;height: 50px;border-radius: 50px;position: fixed;z-index: 9999;} 
	 
	.m-sidebar{position: fixed;top: 0;right: 0;background: #000;z-index: 2000;width: 35px;height: 100%;font-size: 12px;color: #fff;} 
	.cart{color: #fff;text-align:center;line-height: 20px;padding: 200px 0 0 0px;} 
	.cart span{display:block;width:20px;margin:0 auto;} 
	.cart i{width:35px;height:35px;display:block; background:url(car.png) no-repeat;} 
	#msg{position:fixed; top:300px; right:35px; z-index:10; width:1px; height:52px; line-height:52px; font-size:15px; text-align:center; color:#fff; background:#360; display:none} 


	.spinner {
  width: 100px;
}
.spinner input {
  text-align: right;
}
.input-group-btn-vertical {
  position: relative;
  white-space: nowrap;
  width: 1%;
  vertical-align: middle;
  display: table-cell;
}
.input-group-btn-vertical > .btn {
  display: block;
  float: none;
  width: 100%;
  max-width: 100%;
  padding: 8px;
  margin-left: -1px;
  position: relative;
  border-radius: 0;
}
.input-group-btn-vertical > .btn:first-child {
  border-top-right-radius: 4px;
}
.input-group-btn-vertical > .btn:last-child {
  margin-top: -2px;
  border-bottom-right-radius: 4px;
}
.input-group-btn-vertical i{
  position: absolute;
  top: 0;
  left: 4px;
}

</style>
</head>
<body >
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navi_bar.jsp"></jsp:include>
<%
	String path = request.getContextPath();
%>
<!--video-->
    <div data-vide-bg="<%=path%>/bookstore/video/video" style="wmode:transparent;z-index:-1">
        <div class="container header">
            <div class="banner-info">
                <h3>To find your next favourite book in our store! </h3>
                <div class="search-form">
                    <form action="#" method="post">
                        <input type="text" placeholder="Search..." name="Search...">
                        <input type="submit" value="Go" >
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>window.jQuery || document.write('<script src="<%=path%>/bookstore/js/jquery.min.js"><\/script>')</script>
    <script src="<%=path%>/bookstore/js/jquery.vide.min.js"></script>

<!-- right cart -->
<div class="m-sidebar"> 
    <div class="cart">
    	<a href="cartAction!pay" onclick="return check_login();"> 
	        <i></i> 
	        <span >购物车</span> 
    	</a>
    </div> 
</div> 
<div id="msg">已成功加入购物车！</div> 


<!-- products -->
<div class="product">
        <div class="container">
            <!--head line-->
            <div align="center">
                <h3>Special Offers</h3>         
            </div>
            
            <!--products list-->            
            <div class="box">         	 
				<img src="<%= path %>/bookstore/image/book_1.jpg" width="130" height="130"> 
              	<div style="margin-top: 10px;">			    	
                   	<a class="title">月亮与六便士</a>
             	</div>
			    <p ><em class="item_price">￥6.00</em></p>				
			    <div style="margin-bottom: 10px;">
                	<button class="btn btn-default detail" type="button" data-toggle="modal" data-target="#myModal">详细信息</button>                               
                    <button class="btn btn-success addcart" type="button">加入购物车</button>             
                    <p class="hide">1</p> 
                </div>              
			</div> 
            
            
            
        </div>
    </div>
    
    <!--Detail-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content modal-info">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body modal-spa">
                    <div class="col-md-3 span-2">
                        <div >
                            <img id="detail_pic" src="<%= path %>/bookstore/image/book_1.jpg">
                        </div>
                    </div>
                    <div class="col-md-9 span-5 ">
                        <p class="title" id="detail_title"></p>
                        <p class="title" id="detail_author"></p>
                        <p class="title" id="detail_stock"></p>
                        <p class="title" id="detail_price">￥6.00</p> 
                        <label>数量：</label>
                        <div class="input-group spinner">  
							<input type="text" class="form-control" value="1" id="detail_cnt">  
							<div class="input-group-btn-vertical">  
								<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>  
								<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>  
							</div>  
						</div>                    
                        
                        <h4 style="margin-top: 30px;">简介</h4>
                        <a data-toggle="collapse" data-target="#intro" style="font-family: inherit;font-size: 13px;" >展开 >></a>
                        <div id="intro" class="collapse">
                        	<p>some introductions</p>
                        </div>
                        
	                       	  
						<div class="modal-footer">	                          	
                            <button class="btn btn-danger" id="detail_add" style="margin-top: 10px;">加入购物车</button>
                            <p class="hide">1</p>  
                        </div>
                    </div>
                    <div class="clearfix"> </div>
                </div>
            </div>
        </div>
    </div>
	
	<!-- footer -->
	<jsp:include page="footer.jsp"></jsp:include>

</body>
  
<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
<script type="text/javascript">
	function add_to_cart(btn) {
		//get book_id
		var book_id = $(btn).next().text();
		$.ajax({
		    type: "post",
		    url: "cartAction!add_product.action",
		    data: { book_id: book_id },
		    success: function (strValue) {
			    if(strValue == "success"){
			    	$("#msg").show().animate({width: '250px'}, 200).fadeOut(1000); //提示信息 
			    }
			    	
			    else if(strValue == "login"){
			    	alert("添加失败,请重新登陆");
			    	window.location="sign_in.jsp";
			    }
		    }
	   	});
	}
	

</script>

<!-- for fly to cart  -->


<script src="<%=path%>/bookstore/js/jquery.fly.min.js"></script> 
<script type="text/javascript">
function fly(img) {
	 
    var flyer = $('<img class="u-flyer" src="'+img+'">'); 
    flyer.fly({ 
        start: { 
            left: event.screenX-50, //开始位置（必填）#fly元素会被设置成position: fixed 
            top: event.screenY-150 //开始位置（必填） 
        }, 
        end: { 
            left: 1350, //结束位置（必填） 
            top: 200, //结束位置（必填） 
            width: 0, //结束时宽度 
            height: 0 //结束时高度 
        }, 
        onEnd: function(){ //结束回调 
            $("#msg").show().animate({width: '250px'}, 200).fadeOut(1000); //提示信息            
            this.destory(); //移除dom 
        } 
    });
}	 

 
   
    $(".addcart").click(function(event){
    	var btn = $(this);
    	var img = btn.parent().parent().find('img').attr('src');
    	add_to_cart(btn);
    	fly( img);
    });
    
    $("#detail_add").click(function (event) {
    	var count = $("#detail_cnt").val();
		var btn = $(this);
		var img = btn.parents().find(".modal").find('img').attr('src');
		for(var i = 0;i<count;i++)
			add_to_cart(btn);
		fly( img);
	});


$(".detail").click(function () {
	var book_id = $(this).next().next().text();
	$.ajax({
	    type: "post",
	    url: "bookAction!show_detail.action",
	    data: {id: book_id },
	    success: function (data) {
	    	var json = eval("("+data+")");
		   // $("#detail_title").val();
		    $("#detail_title").text("书名： "+json["name"]);
		    $("#detail_price").text("价格： ￥"+json["price"]);
		    $("#detail_author").text("作者： "+json["author"]);
		    $("#detail_stock").text("库中仅剩： "+json["stock"]);
	    	//var modal = $("#myModal");
	    }
   	});
	
})

function check_login() {
	var test = 1;
	var user = "<%=session.getAttribute("user_name")%>"
	if(user == "null"){
		alert("您尚未登陆");
		return false;
	}
}

</script>

<!-- for minus and add  -->
<script type="text/javascript">
(function ($) {  
	  $('.spinner .btn:first-of-type').on('click', function() {  
	    $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);  
	  });  
	  $('.spinner .btn:last-of-type').on('click', function() {  
	    $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);  
	  });  
	})(jQuery); 
</script>

</html>