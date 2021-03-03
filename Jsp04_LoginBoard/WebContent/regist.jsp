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
	function idCheckProc(){
		var chk = document.getElementsByName("myid")[0].title;
		if(chk == 'n'){
			alert("id 중복체크를 해주세요");
			document.getElementsByName("myid")[0].focus();
		}
	}
	
	function idCheck(){
		var myid = document.getElementsByName("myid")[0].value;
		
		if (myid == null || myid.trim() == ""){
			alert("아이디를 입력해 주세요");
		}else{
			open("logincontroller.jsp?command=idchk&myid="+myid, "", "width=200, height=200");
		}
	}

</script>
</head>
<body>

	<h1>회원가입</h1>
	<form action="logincontroller.jsp" method="post">
		<input type="hidden" name="command" value="insertuser"/>
		<table border="1">
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="myid" required="required" title="n"/>
					<input type="button" value="중복체크" onclick="idCheck();"/>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
					<td><input type ="password" name="mypw" required="required" onclick="idCheckProc();" /></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="myname" required="required" onclick="idCheckProc();"></td>
			</tr>
			<tr>
				<th>주소</th>
				<td><input type="text" name="myaddr" required="required" onclick="idCheckProc();"></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="myphone" required="required" onclick="idCheckProc();"></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="myemail" required="required" onclick="idCheckProc();"></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="submit" value="가입"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>