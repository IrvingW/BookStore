<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>BookStore</title>

  
<%
	//response.sendRedirect("bookstore/jsp/bookAction");
%>



</head>
<body>
	<form method="POST" action="fileAction!upload" enctype="multipart/form-data" >
		File:
		<input type="file" name="file" id="file" />
		Destination:
		<input type="submit" value="Upload" name="upload" id="upload" />
	</form>
	
	<img id="Imgbox" src="fileAction!download"  height="530px" width="700px" style="margin:8px auto;">  
	

</body>
</html>