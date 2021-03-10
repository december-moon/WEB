package com.cal.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cal.common.Util;
import com.cal.dao.CalDao;
import com.cal.dto.CalDto;

@WebServlet("/cal.do")
public class CalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CalController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command = request.getParameter("command");
		System.out.printf("<%s>\n", command);
		
		CalDao dao = new CalDao();
		try {
			if(command.equals("calendar")) {
				response.sendRedirect("calendar.jsp");
			}else if(command.equals("insert")) {
				String year = request.getParameter("year");
				String month = request.getParameter("month");
				String date = request.getParameter("date");
				String hour = request.getParameter("hour");
				String min = request.getParameter("min");
				String mdate = year + Util.isTwo(month)+Util.isTwo(date) + Util.isTwo(hour)+Util.isTwo(min);
				
				String id = request.getParameter("id");
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				
				CalDto dto = new CalDto(0, id, title, content, mdate, null);
				int res = dao.insertCalBoard(dto);
				if(res > 0) {
					response.sendRedirect("calendar.jsp");
				}else {
					request.setAttribute("msg", "일정 추가 실패");
					dispatch("error.jsp", request, response);
				
			}
		}else if (command.equals("list")) {
			
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String date = request.getParameter("date");
			
			String yyyyMMdd = year + Util.isTwo(month) + Util.isTwo(date);
			
			List<CalDto> list = dao.getCalList("kh", yyyyMMdd);
			
			request.setAttribute("list", list);
			dispatch("list.jsp", request, response);
		} 
			
	}catch(Exception e){
		request.setAttribute("msg", "command 오류");
		dispatch("error.jsp", request,response);
	}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void dispatch(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(path);
		dispatch.forward(request, response);
		
	}

}
