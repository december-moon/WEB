package com.ajax.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/ScoreController")
public class ScoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ScoreController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String name = request.getParameter("name");
		int kor = Integer.parseInt(request.getParameter("kor"));
		int eng = Integer.parseInt(request.getParameter("eng"));
		int math = Integer.parseInt(request.getParameter("math"));
		
		int sum = kor + eng + math;
		double avg = (double)sum/3;
		
		//json-simple-1.1.1.jar
		//json object를 만들자.
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("sum", sum);
		obj.put("avg", String.format("%.2f", avg));
		// {"name":name, "sum":sum, "avg":avg}의 형태
		
		System.out.println("server에서 보내는 데이터 : "+obj.toJSONString());
		//toJSONString() : json 형식의 문자열로 만들자. 제이슨 객체의 주소값을 리턴하는 게 아니라 문자열을 리턴해야 하기 때문
		
		PrintWriter out = response.getWriter();
		out.println(obj.toJSONString());
		
	}

}
