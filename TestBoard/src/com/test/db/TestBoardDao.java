package com.test.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestBoardDao {
	
	public int insertBoard(TestBoardDto dto) {
		
		Connection con = getConnection();
		String sql = " INSERT INTO BOARD VALUES(BOARDSEQ, NEXTVAL, ?, ?, ?, SYSDATE) ";
		
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getWriter());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			
			res = pstm.executeUpdate();
			if (res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		
		return res;
	}
	
	public int update(BoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		String sql = " UPDATE BOARD "
				+" SET TITLE =?, CONTENT =? WHERE NUM =? ";
		
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getTitle());
			pstm.setString(2, dto.getContent());
			pstm.setInt(3, dto.getNum());
			
			res=pstm.executeUpdate();
			if(res>0) {
				commit(con);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
			return res;
		
		

}
