<%@page import="com.login.dto.MYMemberDto"%>
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
<script type="text/javascript">
	function updateRole(myno){
		location.href="logincontroller.jsp?command=updateroleform&myno="+myno;
	}
	
</script>
</head>
<body>
<%
	List<MYMemberDto> list = (List<MYMemberDto>) request.getAttribute("list");
%>
	<h1>회원 전체 정보 : 가입한 회원만</h1>
	
	<table border="1">
		<tr>
			<th>번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>전화번호</th>
			<th>이메일</th>
			<th>등급 변경</th>
		</tr>
<%
	for(int i=0; i< list.size(); i++){
%>		
		<tr>
			<td><%=list.get(i).getMyno() %></td>
			<td><%=list.get(i).getMyid() %></td>
			<td><%=list.get(i).getMyname() %></td>
			<td><%=list.get(i).getMyphone() %></td>
			<td><%=list.get(i).getMyemail() %></td>
			<td><input type="button" value="변경" onclick="updateRole(<%=list.get(i).getMyno() %>);"></td>
		</tr>
<%	}
%>	
		<tr>
			<td colspan="6" align="right">
				<input type="button" value="메인" onclick="" />
			</td>
		</tr>	
	</table>
</body>
</html>