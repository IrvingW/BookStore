<%@page import="java.util.ArrayList"%>
<%@page import="model.Book"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Orderitem" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
%>
<link href="<%=path%>/bookstore/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/bookstore/css/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="<%=path%>/bookstore/css/dataTables.responsive.css"
	rel="stylesheet">
<title>Users</title>
</head>
<body>
	<!-- list -->
	<div class="col-md-8 col-md-offset-2" >
    	<div>
    		<div class="row">
				<h2 align="center" class="page-header">Orders</h2>
			</div>
			<table align="center" class="table table-striped table-bordered table-hover" id="dataTables" >
		        <thead>
			        <tr align="center">
			            <td><b>ID</b></td>
			            <td><b>order_id</b></td>			          
			            <td><b>book_id</b></td>
			            <td><b>amount</b></td>
			            <td><b>each price</b></td>
			            <td><b>delete</b></td>
			            <td><b>update</b></td>
			        </tr>
		        </thead>
		        
		        <tbody>
		        <%
		        	List<Orderitem> list = (List<Orderitem>)request.getAttribute("orderitems");
		        	if(list == null || list.size() < 1){
		        		out.print("no data");
		        	}else{
		        		for(Orderitem item : list){
		        			String row_id = "row"+item.getId();
		        %>
		        
		        <s:url action="orderItemAction!delete" var="deleteLink">
		        	<s:param name="id"><%= item.getId() %></s:param>
		        	<s:param name="order_id"><%= item.getOrder().getId() %></s:param>
		        </s:url>
		        
		        
		         <tr align="center" id="<%= row_id %>">
		            <td><%= item.getId() %></td> 
					<td ondblclick="modify(this)"><%= item.getOrder().getId()%></td>
					<td ondblclick="modify(this)"><%= item.getBook_id()%></td>
					<td ondblclick="modify(this)"><%= item.getAmount()%></td>
					<td ondblclick="modify(this)"><%= item.getEach_price() %></td>
		            <td>
		            	<button class="btn btn-default">
		            		<a href="${deleteLink}">delete</a>
		            	</button>
		            </td>
		            <td>
		            	<button onclick="update('<%=row_id%>')" class="btn btn-success">update</button>
		            </td>
		    
		        </tr>
		        
		        <%
		        		}
		        	}
		        %>
		        </tbody>
		    </table>
    	</div>
    </div>

</body>

<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
<script src="<%=path%>/bookstore/js/bootstrap.min.js"></script>
<script src="<%=path%>/bookstore/js/jquery.dataTables.min.js"></script>
<script src="<%=path%>/bookstore/js/dataTables.bootstrap.min.js"></script>


<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
		
</script>


<script language="JavaScript" type="text/javascript">
function modify(td) {
    td.setAttribute("contenteditable","true");
}

function update(row_id){
	var row = document.getElementById(row_id);
    var td = row.firstElementChild;
    var id = td.innerHTML;
    td = td.nextElementSibling;
    var order_id = td.innerHTML;
    td = td.nextElementSibling;
    var book_id = td.innerHTML;
    td = td.nextElementSibling;
    var amount = td.innerHTML;
    td = td.nextElementSibling;
    var each_price = td.innerHTML;
   
   
    var url = "orderItemAction!update.action?id="+id+"&order_id="+order_id+"&book_id="+book_id+"&amount="+amount+"each_price"+each_price;

    window.location.href=url;
}
	
</script>
</html>