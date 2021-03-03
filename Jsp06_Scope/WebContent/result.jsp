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
	
	<h1>RESULT</h1>
	
	pageId : <%=pageContext.getAttribute("pageId") %><br/>
	requestId : <%=request.getAttribute("requestId") %><br/>
	sessionId : <%=session.getAttribute("sessionId") %><br/>
	applicationId : <%=application.getAttribute("applicationId") %><br/>
	MyRequest : <%=request.getAttribute("myRequest") %><br/>

</body>
</html>