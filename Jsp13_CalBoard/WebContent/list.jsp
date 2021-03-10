<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:useBean id = "util" class="com.cal.common.Util"></jsp:useBean>

	<h1>일정 목록</h1>

	<form action = "cal.do" method="post">
		<input type="hidden" name="command" value="muldel"/>
		
		<table border="1">
			<tr>
				<th><input type="checkbox" name="all" onclick="allCheck(this.checked);"></th>
				<th>번호</th>
				<th>제목</th>
				<th>일정</th>
				<th>작성일</th>
			</tr>
			<c:choose>
				<c:when test = "${empty list }">
				<tr>
					<th colspan="5">-----일정이 없습니다-----</th>
				</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${list }" var="dto">
						<tr>
							<th><input type="checkbox" name="chk" value="${dto.seq }"></th>
							<td>${dto.seq}</td>
							<td><a href="">${dto.title }</a></td>
							<td>
								<jsp:setProperty property="todates" name="util" value="${dto.mdate }"/>
								<jsp:getProperty property="todates" name="util"/>
							</td>
							<td>
								<fmt:formatDate value="${dto.regdate }" pattern="yyyy.MM.dd"/>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<tr>
				<td colspan="5">
					<input type = "submit" value="삭제"/>
					<input type = "button" value="달력" onclick=""/>
		</table>
	
	</form>

</body>
</html>