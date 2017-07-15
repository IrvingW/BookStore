<%@page import="model.Orderitem"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="model.Order"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<% List<Order> list = (List<Order>) request.getAttribute("orders"); %>
<div class="col-md-8 col-md-offset-2">

		<table align="center" class="table table-striped table-bordered table-hover" >
		        <thead>
			        <tr align="center">
			        	<td class="hide">id</td>
			            <td><b>时间</b></td>
			            <td><b>总价</b></td>
			            
						        	<td><b>图书编号：</b></td>
							        <td><b>单价：</b></td>
							        <td><b>数量：</b></td>	
		            
			        </tr>
		        </thead>
		        
		        <tbody>
		        <%	
		        	int row = 0;
		        	if(list == null || list.size() < 1){
		        		out.print("no data");
		        	}else{
		        		for(Order order : list){
		        			double price= 0;
		        			for(Orderitem item : order.getOrderitems()){
		        				double each_price = item.getEach_price();
		        				double count = item.getAmount();
		        				price += each_price * count;
		        			}
		        			
		        		
		        %>
		        
		         <tr align="center">
		         	<td class="hide"><%= order.getId()%></td>
		            <td><%= order.getDate()%></td>
		            <td><%= price%></td>
		            <td> * </td>
		            <td> * </td>
		           <td> * </td>
		    
		            
		        </tr>
				        <% 
				        	for(Orderitem item: order.getOrderitems()){
				        %>
				        	<div class="collapse">
						        <tr align="center">
						        <td> * </td>
						        <td> * </td>
						       
						        	<td><%= item.getBook_id() %></td>
							        <td><%= item.getEach_price() %></td>
							        <td><%= item.getAmount() %></td>
							       
						        </tr>
		        			</div>
		        		<%
				        	}			        		
		        		%>
		        <%
		        		}
		        	}
		        %>
		        </tbody>
		    </table>
	</div>	    
		    

</body>




</html>