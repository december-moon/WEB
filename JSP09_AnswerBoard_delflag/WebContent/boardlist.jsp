<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <% request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>LIST</h1>

	<table border="1">
		<col width="50"/>
		<col width = "500"/>
		<col width="100"/>
		<col width="100"/>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
					<th colspan="4">---------작성된 글이 없습니다--------</th>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="dto">
					<tr>
						<td>${dto.board.dto }</td>
						<td>
							<c:choose>
								<c:when test="${dto.delflag eq 'Y' }">
									<c:out value="------삭제된 글입니다------"></c:out>
								</c:when>
								<c:otherwise>
									<c:forEach begin="1" end="${dto.titletab }">
										&nbsp;
									</c:forEach>
									<a href="">${dto.title }</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${dto.write }</td>
						<td>${dto.regdate }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<tr>
			<td colspan="4" align="right">
				<input type="button" value="글작성" onclick=""/>
			</td>
		</tr>
	</table>

</body>
</html>