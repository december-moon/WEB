<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");%>
  <!-- core 태그 라이브러리 선언 -->
  <%@
  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
  %>
  <%request.setCharacterEncoding("UTF-8");
  response.setContentType("text/html; charset=UTF-8");
  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>JSTL : Jsp Standard Tag Library</h1>
	
	<table border="1">
		<tr>
			<th>이름</th>
			<th>국어</th>
			<th>영어</th>
			<th>수학</th>
			<th>총점</th>
			<th>평균</th>
			<th>등급</th>
		</tr>
		<c:forEach items="${list }" var = "score">
		<!-- item는 객체 var은 변수명
		items?  -->
		<tr>
			<td>
			<!-- eq:== / ne: != / empty: null-- -->
			<!-- 여기에서의 score는 위에서 쓰인 var=score의 스코어 -->
				<c:if test="${score.name eq '이름10' }">
					<c:out value="홍길동"></c:out>
				</c:if>
				<c:choose>
					<c:when test="${score.name eq '이름20' }">
						<c:out value="${score.name }님!"></c:out>
					</c:when>
					<c:when test="${score.name eq '이름30' }">
						<c:out value="${score.name }"></c:out>
					</c:when>
					<c:otherwise>
						<c:out value="누구지?"></c:out>
					</c:otherwise>
				</c:choose>
				<!-- 위에서 if와 choose는 완전 별개. 홍길동이 출력된 이유는 컨트롤러에서의 이름10이 true이기 때문이고,
				그 이후 choose문 발동. 이름20 이름30이 아니므로 누구지가 함께 출력된 것 -->
				<!-- c:choose 안에 주석 넣으면 오류 남. 실행 위치는 jvm! 태그처럼 보이지만 jsp임.
				즉 서버 안에서 돌고 있다 -->
			</td>
			<td>${score.kor }</td>
			<td>${score.eng }</td>
			<td>${score.math }</td>
			<td>${score.sum }</td>
			<td>${score.avg }</td>
			<td>
				<c:choose>
					<c:when test="${score.grade eq 'A' || score.grade eq 'B' }">
						<c:out value="PASS"></c:out>
					</c:when>
					<c:otherwise>
						<c:out value="FAIL"></c:out>
					</c:otherwise>
				</c:choose>
			</c:forEach>
	</table>
	
	<c:forEach var="i" begin="1" end="10" step="1">
		${i*2 }<br/>
	</c:forEach>
	
	<!-- 숙제: 구구단 출력 -->

</body>
</html>