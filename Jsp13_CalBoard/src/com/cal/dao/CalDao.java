package com.cal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cal.dto.CalDto;

import static com.cal.db.JDBCTemplate.*;

public class CalDao {
	
	public int insertCalBoard(CalDto dto) {
		Connection con = getConnection();
		String sql = " INSERT INTO CALBOARD "
				+" VALUES(CALBOARDSEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE) ";
		
		PreparedStatement pstm = null;
		int res=0;
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, dto.getId());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			pstm.setString(4, dto.getMdate());
			System.out.println("3. query 준비:"+sql);
			
			res=pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			
			if (res>0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
			System.out.println("5. db 종료");
		}
		
		return res;
		
	}
	
	public List<CalDto> getCalList(String id, String yyyyMMdd){
		Connection con = getConnection();
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, MDATE, REGDATE "
				+" FROM CALBOARD "
				+" WHERE ID =? "
				+" AND SUBSTR(MDATE, 1, 8) = ? ";
		ResultSet rs = null;
		List<CalDto> list = new ArrayList<CalDto>();
		PreparedStatement pstm = null;
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, yyyyMMdd);
			System.out.println("3. 쿼리 준비"+sql);
			
			rs=pstm.executeQuery();
			System.out.println("4. 쿼리 실행 및 리턴");
			while(rs.next()) {
				CalDto dto = new CalDto();
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setMdate(rs.getString(5));
				dto.setRegdate(rs.getDate(6));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstm, con);
		}
		
		
		return list;
	}

	public List<CalDto> getCalViewList(String id, String yyyyMM){
		Connection con = getConnection();
		String sql = " SELECT * FROM ( "
				+" SELECT (ROW_NUMBER() OVER(PARTITION BY SUBSTR(MDATE, 1, 8) ORDER BY MDATE))RN, SEQ, ID, TITLE, CONTENT, MDATE, REGDATE "
				+" FROM CALBOARD WHERE ID = ? "
				+" AND SUBSTR(MDATE, 1, 6)= ?) "
				+" WHERE RN BETWEEN 1 AND 4 ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<CalDto> list = new ArrayList<CalDto>();
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, yyyyMM);
			System.out.println("3. 쿼리 준비:"+sql);
			
			rs=pstm.executeQuery();
			System.out.println("4. 실행 및 리턴");
			
			while(rs.next()) {
				CalDto dto = new CalDto();
				dto.setSeq(rs.getInt(2));
				dto.setId(rs.getString(3));
				dto.setTitle(rs.getString(4));
				dto.setContent(rs.getString(5));
				dto.setMdate(rs.getString(6));
				dto.setRegdate(rs.getDate(7));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm,con);
			System.out.println("5. db 종료");
		}
		return list;
	}
	
}
