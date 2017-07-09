<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@ page import="model.Statistic" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
%>
<link href="<%=path%>/bookstore/css/bootstrap.min.css" rel="stylesheet">
<title>统计数据</title>


</head>
<body>
<div >
	<!-- nevigaton bar -->
	<div class="col-md-2" style="margin-top:20px">
	<ul class="nav nav-pills nav-stacked">
		<li><a href="bookAction">Books</a></li>
        <li><a href="userAction">Users</a></li>
        <li><a href="orderAction">Orders</a></li>
        <li class="active"><a href="##">Statistics</a></li>
    </ul>
	</div>
</div>

	<!-- list -->
    <div class="col-md-10">
    	<div>
    		<div class="row">
				<h2 align="center" class="page-header">Statistics</h2>
			</div>
    	</div>
		
		<div id="container" style="min-width: 400px;min-height: 400px;"></div>
		
		<!-- insert form  -->
    	<button class="btn btn-primary" type="button" id="user_modal_btn">用户数据</button>
    	<button class="btn btn-primary" type="button" id="book_modal_btn">书籍数据</button>
    	<button class="btn btn-primary" type="button" id="category_modal_btn">书种数据</button>

	</div>
	
	<!-- user modal -->
	<div class="modal fade" id="user_modal">
    <div class="modal-dialog">
        <div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" action="statisticAction!user" method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label>User Name</label> <input class="form-control" name="user_name">
							</div>
							<div class="form-group">
								<label>Start time</label> <input class="form-control" type="date" name="start_time">
							</div>
							<div class="form-group">
								<label>End time</label> <input class="form-control" type="date" name="end_time">
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
	
	
	<!-- book modal -->
	<div class="modal fade" id="book_modal">
    <div class="modal-dialog">
        <div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" action="statisticAction!book" method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label>Book Name</label> <input class="form-control" name="book_name">
							</div>
							<div class="form-group">
								<label>Start time</label> <input class="form-control" type="date" name="start_time">
							</div>
							<div class="form-group">
								<label>End time</label> <input class="form-control" type="date" name="end_time">
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
	
	
	<!-- category modal -->
	<div class="modal fade" id="category_modal">
    <div class="modal-dialog">
        <div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form role="form" action="statisticAction!category" method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label>Category</label> <input class="form-control" name="category">
							</div>
							<div class="form-group">
								<label>Start time</label> <input class="form-control" type="date" name="start_time">
							</div>
							<div class="form-group">
								<label>End time</label> <input class="form-control" type="date" name="end_time">
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
</body>

<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
<script src="<%=path%>/bookstore/js/bootstrap.min.js"></script>
<script src="<%=path%>/bookstore/js/highcharts.js"></script>
<script src="<%=path%>/bookstore/js/exporting.js"></script>
<script>
	$(function(){
		$("#user_modal_btn").click(function(){
			$("#user_modal").modal("toggle");
		});
	});
	$(function(){
		$("#book_modal_btn").click(function(){
			$("#book_modal").modal("toggle");
		});
	});
	$(function(){
		$("#category_modal_btn").click(function(){
			$("#category_modal").modal("toggle");
		});
	});
</script>

<script type="text/javascript">
$(function () {
	
	var date_array = new Array();
	var count_array = new Array();
 	<%
 		List<Statistic> statistics = (List<Statistic>)request.getAttribute("statistics");
 		if(statistics !=null){
 			DateFormat dateFormat = DateFormat.getDateInstance();
			for(Statistic statistic : statistics){
				
		
	%>
			date_array.push("<%= dateFormat.format(statistic.getDate()) %>");
			count_array.push(<%= statistic.getCount() %>);
	<%
			}
		}
	%>	
	var array = date_array;
	var name = "<%= request.getAttribute("name") %>";
	if(name == "null")
		return;
	else{
	    $('#container').highcharts({
	        chart: {
	            type: 'line'
	        },
	        title: {
	            text: '<%= request.getAttribute("name")%>'
	        },
	        subtitle: {
	            text: '的购书数据'
	        },
	        xAxis: {
	            categories: date_array
	        },
	        yAxis: {
	            title: {
	                text: '本数(本)'
	            }
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true          // 开启数据标签
	                },
	                enableMouseTracking: true // 关闭鼠标跟踪，对应的提示框、点击事件会失效
	            }
	        },
	        series: [{
	            name: '购买书籍',
	            data: count_array
	        }]
	    });
	}
});

</script>

</html>