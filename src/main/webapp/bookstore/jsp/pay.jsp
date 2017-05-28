<%@page import="model.Cart_item"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="model.Cart_item" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<link href="<%=path%>/bookstore/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/bookstore/css/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="<%=path%>/bookstore/css/dataTables.responsive.css"
	rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>


<div class="col-md-8 col-md-offset-2" >
    	
    		<div class="row" >
				<h4 align="left" class="page-header">我的购物车</h4>
			</div>

			<table align="center" class="table table-striped table-bordered table-hover" id="dataTables" >
		        <thead>
			        <tr align="center">
			            <td><b>书名</b></td>
			            <td><b>数量</b></td>
			            <td><b>单价（元）</b></td>
			            <td><b>合计（元）</b></td>
			            <td><b>删除</b></td>
			        </tr>
		        </thead>
		        
		        <tbody>
		        <%
		        	double money = 0;
		        	List<Cart_item> list = (List<Cart_item>)request.getAttribute("cart");
		        	if(list == null || list.size() < 1){
		        		out.print("no data");
		        	}else{
		        		for(Cart_item item : list){
		        			double p = item.getPrice();
		        			double c = item.getCount();
		        			double total = p * c;
		        			
		        			money += total;
		        %>
		        
		         <s:url action="cartAction!rmv_product" var="deleteLink">
		        	<s:param name="book_id"><%=item.getBook_id()%></s:param>
		        </s:url>
		        
		        
		         <tr align="center">
		            <td><%= item.getBook_name()%></td>
		            <td><%= item.getCount()%></td>
		            <td><%= item.getPrice()%></td>
		            <td><%= total%></td>		    
		            <td>
		            	<button class="btn btn-default">
		            		<a href="${deleteLink}">remove</a>
		            	</button>
		            </td>
		        </tr>
		        
		        <%
		        		}
		        	}
		        %>
		        </tbody>
		    </table>
		    
	  
	    <div align="right" style="margin-right: 100px;">
	   		<h4 style="font-family: sans-serif;color: red;">总计： ￥<%= money %></h4>
	   		<button class="btn btn-danger" id="make_order">确认订单</button>
		</div>
	</div>
		    
		    
	
</body>

<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
<script src="<%=path%>/bookstore/js/bootstrap.min.js"></script>
<script src="<%=path%>/bookstore/js/jquery.dataTables.min.js"></script>
<script src="<%=path%>/bookstore/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#dataTables').DataTable({
		responsive : true
	});
});
</script>
</html>