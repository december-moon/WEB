package com.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.biz.MVCBoardBiz;
import com.mvc.biz.MVCBoardBizImpl;
import com.mvc.dto.MVCBoardDto;

@WebServlet("/MVCController")
public class MVCController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MVCController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command = request.getParameter("command");
		MVCBoardBiz biz = new MVCBoardBizImpl();
		
		//index.jsp에서 list로 보냄
		if(command.equals("list")) {
			//1.
			//2.biz에서 리스트 호출
			List<MVCBoardDto> list = biz.selectList();
			//3.
			request.setAttribute("list", list);
			//4. 
			dispatch(request, response, "mvclist.jsp");
		}else if(command.equals("select")) {
			//1. mvclist에서 보내준 값 받아주기
			int seq = Integer.parseInt(request.getParameter("seq"));
			//2. 1번값을 데이터베이스에 담아 리턴
			MVCBoardDto dto = biz.selectOne(seq);
			//3.
			request.setAttribute("dto", dto);
			//4.
			dispatch(request, response, "mvcselect.jsp");
		}else if (command.equals("updateform")) {
			//1.mvclist에서 보내준 값 받아주기
			int seq = Integer.parseInt(request.getParameter("seq"));
			//2.
			MVCBoardDto dto = biz.selectOne(seq);
			//3. request에 담기
			request.setAttribute("dto", dto);
			//4.
			dispatch(request, response, "mvcupdate.jsp");
		}else if(command.equals("updateres")) {
			//1. mvcupdate에서 보내준 값 받기
			int seq = Integer.parseInt(request.getParameter("seq"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			//2.
			MVCBoardDto dto = new MVCBoardDto(seq, null, title, content, null);
			int res=biz.update(dto);
			//3.
			//4.
			if(res>0) {
				//forward를 하면 request가 계속 연결되어 있기 때문에 새로고침 시 값이 계속 전달됨
				response.sendRedirect("mvc.do?command=select&seq="+seq);
			}else {
				response.sendRedirect("mvc.do?command=updateform.do&seq="+seq);
			}
		}else if(command.equals("delete")) {
			//1.
			int seq = Integer.parseInt(request.getParameter("seq"));
			//2.
			int res = biz.delete(seq);
			//3.
			//4.
			if(res>0) {
				//forward를 하면 request가 계속 연결되어 있기 때문에 새로고침 시 값이 계속 전달됨
				response.sendRedirect("mvc.do?command=list");
			}else {
				response.sendRedirect("mvc.do?command=select&seq"+seq);
			}
		}else if (command.equals("insertform")) {
			//1. 2. 3 없음
			//4.
			response.sendRedirect("mvcinsert.jsp");
		}else if (command.equals("insertres")) {
			//1.
			String writer = request.getParameter("writer");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			//2.
			MVCBoardDto dto = new MVCBoardDto(0, writer, title, content, null);
			int res = biz.insert(dto);
			//3.
			//4.
			if(res > 0) {
				response.sendRedirect("mvc.do?command=list");
			}else {
				response.sendRedirect("mvc.do?command=insertform");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	
		/*
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command = request.getParameter("command");
		MVCBoardBiz biz = new MVCBoardBizImpl();
		
		System.out.println("["+command+"]");
		
		if(command.equals("list")) {
			List<MVCBoardDto> list = biz.selectList();
			request.setAttribute("list", list);
			dispatch(request, response, "list.jsp");
			response.sendRedirect(command);
			
		}else if (command.equals("select")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			MVCBoardDto dto = biz.selectOne(seq);
			
			request.setAttribute("dto", dto);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("select.jsp");
			dispatcher.forward(request, response);
			
		}else if (command.equals("insert")) {
			/*
			int seq = Integer.parseInt(request.getParameter("seq"));
			MVCBoardDto dto = biz.insert(seq);
			request.setAttribute("dto", dto);
			*/
			
		/*
			RequestDispatcher dispatcher = request.getRequestDispatcher("insert.jsp");
			dispatcher.forward(request, response);
			
		} else if (command.equals("update")) {
			
			int seq = Integer.parseInt(request.getParameter("seq"));
			
			MVCBoardDto dto = biz.selectOne(seq);
			
			request.setAttribute("dto", dto);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("update.jsp");
			dispatcher.forward(request, response);
			
		} else if (command.equals("updateres")) {
			
			PrintWriter out = response.getWriter();
			
			int seq = Integer.parseInt(request.getParameter("seq"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			MVCBoardDto dto = new MVCBoardDto();
			dto.setSeq(seq);
			dto.setTitle(title);
			dto.setContent(content);
			
			MVCBoardDto dto1 = biz.selectOne(seq);
			
			request.setAttribute("dto", dto1);
			
			String str = "<script type='text/javascript'>"
						+ "alert('수정 성공');"
						+ "location.href='controller.do?command=select&seq="+dto.getSeq()+"'"
						+ "</script>";
			
			String str1 = "";
			str1 = "<script type='text/javascript'>";
			str1 += "alert('"+"수정 실패"+"');";
			str1 += "location.href='controller.do?command=update&seq="+dto.getSeq()+"';";
			str1 += "</script>";
			
			int res = biz.update(dto);
			if (res > 0) {
				out.print(str);

			} else {
				out.print(str1);
			}
			
		} else if (command.equals("insertres")) {
			
			String writer = request.getParameter("writer");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			MVCBoardDto dto = new MVCBoardDto();
			dto.setWriter(writer);
			dto.setTitle(title);
			dto.setContent(content);
			
			String str = "";
			str = "<script type='text/javascript'>"
					+ "alert('저장 성공');"
					+ "location.href='controller.do?command=list';"
					+ "</script>";
			
			String str1 = "";
			str1 = "<script type='text/javascript'>"
					+ "alert('저장 실패');"
					+ "location.href='controller.do?command=insert';"
					+ "</script>";
			
			PrintWriter out = response.getWriter();
			
			int res = biz.insert(dto);
			if (res > 0) {
				out.print(str);
			} else {
				out.print(str1);
			}
		} else if (command.equals("delete")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			
			int res = biz.delete(seq);
			
			MVCBoardDto dto = new MVCBoardDto();
			
			String str = "";
			str = "<script type='text/javascript'>"
					+ "alert('삭제 성공');"
					+ "location.href='controller.do?command=list';"
					+ "</script>";
			
			String str1 = "";
			str1 = "<script type='text/javascript'>"
					+ "alert('삭제 실패');"
					+ "location.href='controller.do?command=select&seq="+dto.getSeq()+"';"
					+ "</script>";
			
			PrintWriter out = response.getWriter();
			
			if (res > 0) {
				out.print(str);
			} else {
				out.print(str1);
			}
		} 
		 else if (command.equals("muldel")) {
			String[] seqs = request.getParameterValues("chk");
			
			if (seqs == null || seqs.length == 0) {
				String st = "<script type='text/javascript'>"
						+ "alert('삭제할 글을 하나 선택하여 주세요');"
						+ "location.href='controller.do?command=list';"
						+ "</script>";
			} else {
				String str = "";
				str = "<script type='text/javascript'>"
						+ "alert('삭제 성공');"
						+ "location.href='controller.do?command=list';"
						+ "</script>";
				
				String str1 = "";
				str1 = "<script type='text/javascript'>"
						+ "alert('삭제 실패');"
						+ "location.href='controller.do?command=list';"
						+ "</script>";
				
				PrintWriter out = response.getWriter();
				int res = biz.multiDelete(seqs);
				if (res > 0) {
					out.print(str);
				} else {
					out.print(str1);
				}
			}
		}
		*/
	
	}
	//하나하나 생성하기 힘드니 메소드로 dispatcher 만들기
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
