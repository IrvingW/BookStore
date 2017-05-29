<%@page import="model.Book"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
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
<div >
	<!-- nevigaton bar -->
	<div class="col-md-2" style="margin-top:20px">
	<ul class="nav nav-pills nav-stacked">
		<li><a href="bookAction">Books</a></li>
        <li><a href="userAction">Users</a></li>
        <li class="active"><a href="##">Orders</a></li>
    </ul>
	</div>
</div>
	<!-- list -->
    <div class="col-md-10">
    	<div>
    		<div class="row">
				<h2 align="center" class="page-header">Orders</h2>
			</div>
			<table align="center" class="table table-striped table-bordered table-hover" id="dataTables" >
		        <thead>
			        <tr align="center">
			            <td><b>ID</b></td>
			            <td><b>user_id</b></td>			          
			            <td><b>date</b></td>
			            <td><b>state</b></td>
			            <td><b>delete</b></td>
			            <td><b>update</b></td>
			        </tr>
		        </thead>
		        
		        <tbody>
		        <%
		        	List<Order> list = (List<Order>)request.getAttribute("orders");
		        	if(list == null || list.size() < 1){
		        		out.print("no data");
		        	}else{
		        		for(Order order : list){
		        			String row_id = "row"+order.getId();
		        %>
		        
		        <s:url action="orderAction!delete" var="deleteLink">
		        	<s:param name="id"><%= order.getId() %></s:param>
		        </s:url>
		        
		        
		         <tr align="center" id="<%= row_id %>">
		            <td><%= order.getId() %></td>
					<td ondblclick="modify(this)"><%= order.getUser_id()%></td>
					<td ondblclick="modify(this)"><%= order.getDate()%></td>
					<td ondblclick="modify(this)"><%= order.getState()%></td>
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
    
    <!-- insert form  -->
    <button class="btn btn-primary" type="button" id="modal_btn">添加</button>
    
    <div class="modal fade" id="mymodal">
    <div class="modal-dialog">
        <div class="modal-content">
        	<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">添加新订单</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" action="orderAction!add" method="post">
							<div class="form-group">
								<label>User_id</label> <input class="form-control" name="user_id">
							</div>
							<div class="form-group">
								<label>Date</label> <input class="form-control" name="date">
							</div>
							<div class="form-group">
								<label>State</label> <input class="form-control" name="state">
							</div>
							<div class="modal-footer">
								<input type="reset" class="btn btn-default">
								<input type="submit" class="btn btn-primary">
							</div>
						</form>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

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
		
		$(function(){
		    $("#modal_btn").click(function(){
		      $("#mymodal").modal("toggle");
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
    var user_id = td.innerHTML;
    td = td.nextElementSibling;
    var date = td.innerHTML;
    td = td.nextElementSibling;
    var state = td.innerHTML;
   
   
    var url = "orderAction!update.action?id="+id+"&user_id="+user_id+"&date="+date+"&state="+state;

    window.location.href=url;
}
	
</script>
</html>