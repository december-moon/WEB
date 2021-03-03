<%@page import="com.board.dao.MyBoardDao"%>
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
	int seq=Integer.parseInt(request.getParameter("seq"));
	MyBoardDao dao = new MyBoardDao();
	int res = dao.delete(seq);
	if(res>0){
%>	
	<script type="text/javascript">
		alert("삭제 성공");
		location.href="mylist.jsp"
	</script>	
	}
<% 
}else{
%>
	<script type="text/javascript">
		alert("삭제 실패");
		location.href="myselect.jsp?seq=<%=seq%>";
	
	</script>

<%
	}
%>

</body>
</html>