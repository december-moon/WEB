<%@page import="com.mvc.dto.MVCBoardDto"%>
<%@page import="com.mvc.dao.MVCBoardDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

	function deleteProc(seq){
		if(confirm(seq+"번 글을 삭제하시겠습니까?")){
			location.href="mycontroller.jsp?command=delete&seq="+seq;
		}
	}
	
</script>
</head>
<body>
<%
	// int seq = Integer.parseInt(request.getParameter("seq"));
	// MVCBoardDaoImpl dao = new MVCBoardDaoImpl();
	
	//컨트롤러에서 보내준 거 받기 (*오브젝트 형변환)
	MVCBoardDto dto = (MVCBoardDto) request.getAttribute("dto");
	
%>

</body>
	<h1>select</h1>
	<!--  <form action="mycontroller.jsp" method="post"> -->
	<table border="1">
		<tr>
			<th>작성자</th>
			<td><%=dto.getWriter()%></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><%=dto.getTitle() %></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea rows="10" cols="60" readonly="readonly"><%=dto.getContent() %></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<input type="button" value="목록" onclick="location.href=mylist.jsp"/>
				<input type="button" value="수정" onclick="location.href='mycontroller.jsp?command=updateform&seq=<%=dto.getSeq()%>'"/>
				<input type="button" value="삭제" onclick="deleteProc(<%=dto.getSeq()%>);"/>
	</table>
</html>