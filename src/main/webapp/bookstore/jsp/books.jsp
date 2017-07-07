<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
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
<title>书籍</title>
</head>
<body>
<div >
	<!-- nevigaton bar -->
	<div class="col-md-2" style="margin-top:20px">
	<ul class="nav nav-pills nav-stacked">
		<li class="active"><a href="##">Books</a></li>
        <li><a href="userAction">Users</a></li>
        <li><a href="orderAction">Orders</a></li>
    </ul>
	</div>
</div>
	<!-- list -->
    <div class="col-md-10">
    	<div>
    		<div class="row">
				<h2 align="center" class="page-header">Books</h2>
			</div>
			<table align="center" class="table table-striped table-bordered table-hover" id="dataTables" >
		        <thead>
			        <tr align="center">
			            <td><b>ID</b></td>
			            <td><b>name</b></td>
			            <td><b>author</b></td>
			            <td><b>price</b></td>
			            <td><b>stock</b></td>
			            <td><b>delete</b></td>
			            <td><b>update</b></td>
			        </tr>
		        </thead>
		        
		        <tbody>
		        <%
		        	List<Book> list = (List<Book>)request.getAttribute("books");
		        	if(list == null || list.size() < 1){
		        		out.print("no data");
		        	}else{
		        		for(Book book : list){
		        			String row_id = "row"+book.getId();
		        %>
		        
		         <s:url action="bookAction!delete" var="deleteLink">
		        	<s:param name="id"><%= book.getId() %></s:param>
		        </s:url>
		        
		        
		         <tr align="center" id="<%= row_id %>">
		            <td><%= book.getId() %></td>
		            <td ondblclick="modify(this)"><%= book.getBook_name()%></td>
		            <td ondblclick="modify(this)"><%= book.getAuthor()%></td>
		            <td ondblclick="modify(this)"><%= book.getPrice()%></td>
		            <td ondblclick="modify(this)"><%= book.getStock()%></td>		    
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
				<h4 class="modal-title">添加新书</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" action="bookAction!add" method="post" enctype="multipart/form-data">
							<div>
								<label>点击上传书籍封面</label>
								<input type="file" name="file">
							</div>	
							<div class="form-group">
								<label>Name</label> <input class="form-control" name="name">
							</div>
							<div class="form-group">
								<label>Author</label> <input class="form-control" name="author">
							</div>
							<div class="form-group">
								<label>Price</label> <input class="form-control" name="price">
							</div>
							<div class="form-group">
								<label>Stock</label> <input class="form-control" name="stock">
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
    var name = td.innerHTML;
    td = td.nextElementSibling;
    var author = td.innerHTML;
    td = td.nextElementSibling;
    var price = td.innerHTML;
    td = td.nextElementSibling;
    var stock = td.innerHTML;
    var url = "bookAction!update.action?id="+id+"&name="+name+"&author="+author+"&price="+price+"&stock="+stock;
    window.location.href=url;
}
	
</script>
</html>