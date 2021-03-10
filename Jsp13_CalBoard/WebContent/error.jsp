<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1 style="color:red;">ERROR</h1>
	<h3><%=request.getAttribute("msg") %></h3>
	
	<a href = "index.jsp">처음으로...</a>

</body>
</html>