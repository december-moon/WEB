package com.bike.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.dao.BikeDao;
import com.bike.dto.BikeDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/BikeController")
public class BikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BikeController() {
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command = request.getParameter("command");
		System.out.println("["+command+"]");
		
		if(command.equals("view")) {
			response.sendRedirect("view.html");
		}else if(command.equals("getdata")) {
			BikeDao dao = new BikeDao();
			
			if (dao.delete()) {
				System.out.println("db 초기화 성공");
			}else {
				System.out.println("db 초기화 실패");
			}
			
			String data = request.getParameter("mydata");
			
			//JsonElement: Json요소의 모든 값들을 포함함. 어떤 형태로 data를 받을지 미정이라 일단 Element로 가져온 후
			JsonElement element = JsonParser.parseString(data); 
			//오브젝트로 받기 위해서 JsonParser.parseString() 메소드를 이용. getAs를 통해 변환해야 함
			JsonObject jsonData = element.getAsJsonObject();	
			//JsonObject로 가지고 오겠다!
			//JsonObject -> 하나의 객체를 나타낼 때 표현하기 위한 클래스. key-value park ({"String" : JsonElement} 형식)
			
			//위 두 줄을 JsonElement element = JsonParser.parseString(data).getAsJsonObject(); 한 줄로 줄이기 가능
			
			JsonElement records = jsonData.get("records");
			JsonArray recordsArray = records.getAsJsonArray(); //[] 형태로 변환함
			
			List<BikeDto> list = new ArrayList<BikeDto>(); 
			JsonArray resultArray = new JsonArray();
			
			
			for (int i=0; i<recordsArray.size(); i++) {
				/*
				 JsonElement tempElement = recordsArray.get(i);
				 JsonObject tempObject = tempElement.getAsJsonObject();
				 JsonElement nameElement = tempObject.get("자전거대여소명");
				 String name = nameElement.getAsString();
				  */
				if(recordsArray.get(i).getAsJsonObject().get("자전거대여소명")!=null) {
				String name = recordsArray.get(i).getAsJsonObject().get("자전거대여소명").getAsString();
				String addr = null;
				
				if(recordsArray.get(i).getAsJsonObject().get("소재지도로명주소") !=null) {
					addr = recordsArray.get(i).getAsJsonObject().get("소재지도로명주소").getAsString();
				}else{
					addr = recordsArray.get(i).getAsJsonObject().get("소재지지번주소").getAsString();
				}
				
				double latitude = recordsArray.get(i).getAsJsonObject().get("위도").getAsDouble();
				double longitude = recordsArray.get(i).getAsJsonObject().get("경도").getAsDouble();
				
				int bike_count = recordsArray.get(i).getAsJsonObject().get("자전거보유대수").getAsInt();
				
				BikeDto dto = new BikeDto(name, addr, latitude, longitude, bike_count);
				list.add(dto);
				
				Gson gson = new Gson();
				String jsonString = gson.toJson(dto);
				resultArray.add(JsonParser.parseString(jsonString));
				//json형태의 문자열을 json 객체로 다시 바꾸고, json 배열에 저장함(JsonArray)
				}
			}
			if(dao.insert(list)) {
				System.out.println("db 저장 성공");
			}else {
				System.out.println("db 저장 실패");
			}
			
			JsonObject result = new JsonObject();
			result.add("result", resultArray);
			
			//key: "result" value: resultArray
			//result라는 이름으로 배열을 다시 만들어줌
			
			response.getWriter().append(result+"");
		}
	}

}
