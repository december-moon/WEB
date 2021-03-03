<%@page import="com.mvc.dto.MVCBoardDto"%>
<%@page import="java.util.List"%>
<%@page import="com.mvc.biz.MVCBoardBizImpl"%>
<%@page import="com.mvc.biz.MVCBoardBiz"%>
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

<%
	/*
		controller지만 각 명령으로 연결되는 이유: 4번의 forward 때문.
		컨트롤러 거쳐야 하는 이유 ? request에 list를 저장해 mylist로 전달하기 위함
	*/

	//index에서 요청한 값(command)을 받음
	String command = request.getParameter("command");
	System.out.printf("[%s]\n", command);
	
	MVCBoardBiz biz = new MVCBoardBizImpl();
	
	//요청한 명령을 확인한다
	if(command.equals("list")){
		//1. 보내준 값이 있으면, 받는다.
		//2. db에 전달할 값이 있으면 전달하고,
		//	 없으면 없는 대로 호출한다.
		List<MVCBoardDto> list = biz.selectList();
		
		//3. 화면에 전달할 값이 있으면, request 객체에 담아 준다.
		//setAttribute: list가 무슨 값인지 인지를 못해서 object로 담아 버림. (이유: 오브젝트가 최상위 클래스이므로 어떤 타입이든 받을 수 있음)
		request.setAttribute("list", list);
		
		//4. 보낸다.
		pageContext.forward("mylist.jsp");
		
		//위 1~4 순서 꼭 기억하기
		
	}else if (command.equals("insertform")){
		//1.
		//2
		//3
		//4
		response.sendRedirect("myinsert.jsp");
		/*
			pageContext.forward() : 페이지 위임(request, response 객체가 그대로 전달)
			response.sendRedirect() : 페이지 이동(새로운 request, response 객체)
		*/
	}else if (command.equals("insertres")){
		//1.보내준 값이 있으면 받는다
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//2.db에 전달할 값이 있으면 전달하고 없으면 그냥 호출한다
		MVCBoardDto dto = new MVCBoardDto(0, writer, title, content, null);
		int res = biz.insert(dto);
		
		//3.화면에 전달할 값이 있으면 request 객체에 담아 준다.(없음)
		
		//4.보낸다
		if(res > 0){
%>
		<script type="text/javascript">
			alert("글 작성 성공");
			location.href='mycontroller.jsp?command=list';
		</script>
<%
		}else{
%>
		<script type="text/javascript">
			alert("글 작성 실패");
			location.href='mycontroller.jsp?command=insertform';
		</script>
<% 					
		}
	}else if (command.equals("updateres")){
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		MVCBoardDto dto = new MVCBoardDto(0, writer, title, content, null);
		int res = biz.update(dto);
		
		if(res > 0){
			%>
			<script type="text/javascript">
				alert("글 수정 성공");
				location.href='mycontroller.jsp?command=list';
			</script>
	<%
			}else{
	%>
			<script type="text/javascript">
				alert("글 수정 실패");
				location.href='mycontroller.jsp?command=updateform';
			</script>	
		<%
			}
	}else if (command.equals("selectone")){
		//1.
		int seq = Integer.parseInt(request.getParameter("seq"));
		//2.
		MVCBoardDto dto = biz.selectOne(seq);
		//3.
		request.setAttribute("dto", dto);
		//4.
		pageContext.forward("myselect.jsp");
		
	}else if (command.equals("updateform")){
		//1.
		int seq = Integer.parseInt(request.getParameter("seq"));
		//2.
		MVCBoardDto dto = biz.selectOne(seq);
		//3.
		request.setAttribute("dto", dto);
		//4.
		pageContext.forward("myupdate.jsp");
	}else if (command.equals("updateres")){
		//1.
		int seq = Integer.parseInt(request.getParameter("seq"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//2.
		MVCBoardDto dto = new MVCBoardDto();
		dto.setSeq(seq);
		dto.setTitle(title);
		dto.setContent(content);
		int res = biz.update(dto);
		//3.
		//4.
		if(res > 0){
%>
		<script type="text/javascript">
			alert("수정 성공");
			location.href="mycontroller.jsp?command=selectone&seq=<%=seq%>";
		</script>         
<%
		}else{
%>
		<script type="text/javascript">
			alert("수정 실패");
			//history.back() 바로 뒷페이지
			history.back();
		</script>
<%
			}
		}else if(command.equals("delete")){
			//1.
			int seq = Integer.parseInt(request.getParameter("seq"));
			//2.
			int res = biz.delete(seq);
			//3. 없
			//4.
			if(res > 0){
%>
			<script type="text/javascript">
				alert("삭제 성공");
				location.href="mycontroller.jsp?command=list";
			</script>
<%
			}else{

%>
			<script type="text/javascript">
				alert("삭제 실패");
				location.href="mycontroller.jsp?command=selectone&seq=<%=seq%>";
			</script>
<%
			}
		}
%>
</body>
</html>