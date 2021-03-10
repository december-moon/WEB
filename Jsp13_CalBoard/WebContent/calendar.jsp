<%@page import="com.cal.dto.CalDto"%>
<%@page import="java.util.List"%>
<%@page import="com.cal.dao.CalDao"%>
<%@page import="com.cal.common.Util"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#calendar{
	border-collapse: collapse;
	boeder: 1px solid gray;
	}
	#calendar th{
	width: 80px;
	border: 1px solid gray;}
	#calendar td{
	width: 80px;
	height: 80px;
	border: 1px solid gray;
	text-align: left;
	vertical-align: top;
	position: relative;}
	a{
	text-decoration: none;}
	
	.list > p {
	font-size: 5px;
	margin: 1px;
	background-color: skyblue;
	}
</style>
</head>
<body>
<%
	Calendar cal = Calendar.getInstance();

	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	
	String paramYear = request.getParameter("year");
	String paramMonth = request.getParameter("month");
	if(paramYear != null){
		year = Integer.parseInt(paramYear);
	}
	if(paramYear != null){
		month = Integer.parseInt(paramMonth);
	}
	if (month > 12){
		month =1;
		year++;
	}
	if (month < 1){
		month = 12;
		year--;
	}
	
	cal.set(year, month-1, 1);
	
	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	
	CalDao dao = new CalDao();
	String yyyyMM = year + Util.isTwo(String.valueOf(month));
	List<CalDto> list = dao.getCalViewList("kh", yyyyMM);


%>

	<table id = "calendar">
		<caption>
			<a href ="calendar.jsp?year=<%=year-1%>&month=<%=month%>">◁</a>
			<a href ="calendar.jsp?year=<%=year%>&month=<%=month-1%>">◀</a>
			
			<span class=""><%=year %></span>년
			<span class=""><%=month %></span>월

			<a href ="calendar.jsp?year=<%=year%>&month=<%=month+1%>">▶</a>
			<a href ="calendar.jsp?year=<%=year+1%>&month=<%=month%>">▷</a>
		</caption>
		
		<tr>
			<th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th>
		</tr>
		
		<tr>
		
<%
		for (int i=0; i < dayOfWeek-1 ;i++){
			out.print("<td></td>");

		}
		for (int i=1; i<=lastDay; i++){
%>
			<td>
				<a href="cal.do?command=list&year=<%=year %>&month=<%=month %>&date=<%=i %>" style="color: <%=Util.fontColor(i, dayOfWeek)%>"><%=i %></a>
				<a href="insert.jsp?year=<%=year%>&month=<%=month%>&date=<%=i%>&lastDay=<%=lastDay%>">
					<img alt="" src="image/pen.png" style="width: 10px; height: 10px;" >
				</a>
				<div class="list">
					<%=Util.getCalView(i, list) %>
				</div>
			</td>	
<%
		if((dayOfWeek-1+i)%7 == 0){
			out.print("</tr><tr>");
		}
	}
		
		for (int i=0; i<(7-(dayOfWeek-1 + lastDay)%7)%7; i++){
			out.print("<td></td>");
		}
%>

				</tr>

	</table>

</body>
</html>