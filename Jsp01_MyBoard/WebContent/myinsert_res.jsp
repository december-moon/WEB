<%@page import="com.board.dao.MyBoardDao"%>
<%@page import="com.board.dto.MyBoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html; charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String writer = request.getParameter("writer");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	MyBoardDto dto = new MyBoardDto();
	dto.setWriter(writer);
	dto.setTitle(title);
	dto.setContent(content);
	
	MyBoardDao dao = new MyBoardDao();
	int res = dao.insert(dto);
	if(res > 0){
%>	
	<script type="text/javascript">
		alert("글 작성 성공");
		location.href="mylist.jsp";
	</script>
<% 
	}else{
%>
	<script type="text/javascript">
		alert("글 작성 실패");
		location.href="myinsert.jsp";
	</script>
<% 
	}%>
	

</body>
</html>