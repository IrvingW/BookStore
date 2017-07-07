<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
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
<title>用户</title>
</head>
<body>
<div >
	<!-- nevigaton bar -->
	<div class="col-md-2" style="margin-top:20px">
	<ul class="nav nav-pills nav-stacked">
		<li><a href="bookAction">Books</a></li>
        <li class="active"><a href="##"><i class="fa fa-user fa-fw"></i>Users</a></li>
        <li><a href="orderAction"><i class="fa fa-reorder fa-fw"></i>Orders</a></li>
    </ul>
	</div>
</div>
	<!-- list -->
    <div class="col-md-10">
    	<div>
    		<div class="row">
				<h2 align="center" class="page-header">Users</h2>
			</div>
			<table align="center" class="table table-striped table-bordered table-hover"  id="dataTables" >
		        <thead>
			        <tr align="center">
			            <td><b>ID</b></td>
			            <td><b>name</b></td>
			            <td><b>password</b></td>
			            <td><b>phone</b></td>
			            <td><b>email</b></td>
			            <td><b>role</b></td>
			            <td><b>address</b></td>
			            <td><b>delete</b></td>
			            <td><b>update</b></td>
			        </tr>
		        </thead>
		        
		        <tbody>
		        <%
		        	List<User> list = (List<User>)request.getAttribute("users");
		        	if(list == null || list.size() < 1){
		        		out.print("no data");
		        	}else{
		        		for(User user : list){
		        			String row_id = "row"+user.getId();
		        %>
		        
		         <s:url action="userAction!delete" var="deleteLink">
		        	<s:param name="id"><%= user.getId() %></s:param>
		        </s:url>
		        
		        
		         <tr align="center" id="<%= row_id %>">
		            <td><%= user.getId() %></td>
		            <td ondblclick="modify(this)"><%= user.getUser_name()%></td>
		            <td ondblclick="modify(this)"><%= user.getPassword()%></td>
		            <td ondblclick="modify(this)"><%= user.getPhone()%></td>
		            <td ondblclick="modify(this)"><%= user.getEmail()%></td>
		            <td ondblclick="modify(this)"><%= user.getRole()%></td>
		            <td ondblclick="modify(this)"><%= user.getAddress()%></td>
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
				<h4 class="modal-title">添加新用户</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" action="userAction!add" method="post">
							<div class="form-group">
								<label>Name</label> <input class="form-control" name="user_name">
							</div>
							<div class="form-group">
								<label>Password</label> <input class="form-control" name="password">
							</div>
							<div class="form-group">
								<label>Phone</label> <input class="form-control"  name="phone">
							</div>
							<div class="form-group">
								<label>Email</label> <input class="form-control" name="email">
							</div>
							<div class="form-group">
								<label>Role</label> <input class="form-control" name="role">
							</div>
							<div class="form-group">
								<label>Address</label> <input class="form-control" type="text" name="address">
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
    var user_name = td.innerHTML;
    td = td.nextElementSibling;
    var password = td.innerHTML;
    td = td.nextElementSibling;
    var phone = td.innerHTML;
    td = td.nextElementSibling;
    var email = td.innerHTML;
    td = td.nextElementSibling;
    var role = td.innerHTML;
    td = td.nextElementSibling;
    var address = td.innerHTML;
    var url = "userAction!update.action?id="+id+"&user_name="+user_name+"&password="+password+"&phone="+phone+"&email="+email+"&role="+role+"&address="+address;
    window.location.href=url;
}
	
</script>
</html>