<%@page import="com.mvc.dto.MVCBoardDto"%>
<%@page import="java.util.List"%>
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
<%
	//object 타입으로 감싸져서 전달되었으므로 list로 형변환 필요함. 오브젝트가 제일 큰 타입이라서!!
	List<MVCBoardDto> list = (List<MVCBoardDto>) request.getAttribute("list");
%>
<body>

	<h1>List</h1>
	<table border="1">
		<tr>
			<th><input type="checkbox" name="all" onclick="allCheck(this.checked);"></th>
			<th>번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>작성일</th>
		</tr>
		
<%
		for (MVCBoardDto dto : list){
%>	
		<tr>
			<th><input type="checkbox" name="chk" value="<%=dto.getSeq() %>"/></th>
			<td><%=dto.getSeq() %></td>
			<td><%=dto.getWriter()%></td>
			<td><a href="mycontroller.jsp?command=selectone&seq=<%=dto.getSeq()%>"><%=dto.getTitle() %></a></td>
			<td><%=dto.getRegdate() %></td>
		</tr>
<%
		}
%>		
		<tr>
			<td colspan="5" align="right">
				<input type="submit" value="삭제"/>
				<input type="button" value="글작성" onclick="location.href='mycontroller.jsp?command=insertform'"/>
			</td>
		</tr>
	</table>


</body>
</html>