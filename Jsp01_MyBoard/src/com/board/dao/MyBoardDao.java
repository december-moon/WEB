package com.board.dao;

import static com.board.db.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.board.dto.MyBoardDto;


// Data Access Object : DB와 연결
public class MyBoardDao {
	
	//전체 출력
	//dao에서의 파라미터 = db에 전달할 값
	//dao에서의 return type = db에서 가져온 값 (List<MyBoardDto>)
	public List<MyBoardDto> selectList(){
		
		//1. driver 연결
		//2. 계정연결
		Connection con = getConnection();
		String sql = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
				+ " FROM MYBOARD "
				+ " ORDER BY SEQ DESC ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<MyBoardDto> list = new ArrayList<MyBoardDto>();
		
		//3. query 준비
		try {
			pstm = con.prepareStatement(sql);
			System.out.println("3. query 준비");
			
			//4. query 실행 및 리턴
			rs = pstm.executeQuery();
			while (rs.next()) {
				MyBoardDto dto = new MyBoardDto();	// 밖으로 뺐을 때 왜 안 되는지? 빼면 어떻게 되는지?
				dto.setSeq(rs.getInt(1));
				dto.setWriter(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(3));
				dto.setRegdate(rs.getDate(5));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//5. db 종료
			close(rs);
			close(pstm);
			close(con);
		}
		
		return list;
	}
	
	//하나 출력 = 한줄만 출력 (dto 한줄) / primary key = parameter
	public MyBoardDto selectOne(int seq) {
		Connection con = getConnection();
		String sql = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
				+ " FROM MYBOARD "
				+ " WHERE SEQ = ? ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MyBoardDto dto = null;
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, seq);
			System.out.println("3. query 준비: "+sql);
			
			rs=pstm.executeQuery();
			while(rs.next()) {
				dto = new MyBoardDto(rs.getInt("SEQ"), rs.getString("WRITER"), 
						rs.getString("TITLE"), rs.getString("CONTENT"), rs.getDate("REGDATE")
						);
			}
			System.out.println("4. query 실행 및 리턴");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstm);
			close(con);
			System.out.println("5. db 종료");
		}
		return dto;
	}
	
	//추가
	//dto를 전달하면 추가 n개의 rows view가 insert 된다 = n개(숫자)이기 때문에 int 타입으로 리턴함
	//쿼리문 = INSERT INTO MYBOARD VALUES(MYBOARDSEQ.NEXTVAL, ?(작성자), ?(제목), ?(내용), SYSDATE); ! PreparedStatement !
	public int insert(MyBoardDto dto){
		Connection con = getConnection();
		String sql = " INSERT INTO MYBOARD "
				+ " VALUES (MYBOARDSEQ.NEXTVAL, ?, ?, ?, SYSDATE ) ";
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, dto.getWriter());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			System.out.println("3. query 준비 : " +sql);
			
			res = pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm);
			close(con);
			System.out.println("5. db 종료");
		}
		
		return res;
	}
	
	//수정
	public int update(MyBoardDto dto){
		Connection con = getConnection();
		String sql = " UPDATE MYBOARD "
				+ " SET TITLE = ?, CONTENT = ? "
				+ " WHERE SEQ = ? ";
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getTitle());
			pstm.setString(2, dto.getContent());
			pstm.setInt(3, dto.getSeq());
			System.out.println("3. query 준비 : "+sql);
			
			res = pstm.executeUpdate();
			if(res > 0) {
				commit(con);
			}
			System.out.println("4. query 실행 및 리턴");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm);
			close(con);
			System.out.println("5. DB 종료");
		}
		
		return res;
		
	}
	
	//삭제
	//해당 글 하나만 삭제하는 거니까 Primary key인 seq만 전달하자
	public int delete(int seq){
		Connection con = getConnection();
		String sql = " DELETE FROM MYBOARD "
				+" WHERE SEQ = ? ";
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, seq);
			System.out.println("3. query 준비 : " + sql);
			
			res=pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm);
			close(con);
			System.out.println("5. db 종료");
		}
		
		return res;
		
	}
	
}