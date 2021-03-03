package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mvc.dto.MVCBoardDto;
import static com.mvc.db.JDBCTemplate.*;

public class MVCBoardDaoImpl implements MVCBoardDao {

	@Override
	public List<MVCBoardDto> selectList() {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<MVCBoardDto> list = new ArrayList<MVCBoardDto>();
		MVCBoardDto dto = null;
		
		try {
			pstm = con.prepareStatement(SEL_LIST);
			System.out.println("3. query 준비: "+SEL_LIST);
			
			rs=pstm.executeQuery();
			System.out.println("4. query 실행 및 리턴");
			
			while(rs.next()) {
				dto = new MVCBoardDto(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getDate(5));
				
			list.add(dto);
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstm, con);
			System.out.println("5. db 종료");
		}
		return list;
	}

	@Override
	public MVCBoardDto selectOne(int seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MVCBoardDto dto = null;
		
		try {
			pstm=con.prepareStatement(SEL_ONE);
			pstm.setInt(1, seq);
			System.out.println("3. query 준비 "+SEL_ONE);
			
			rs=pstm.executeQuery();
			
			while(rs.next()) {
				dto=new MVCBoardDto(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getDate(5));
			}
			System.out.println("4. query 실행 및 리턴");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstm, con);
			System.out.println("5. db 종료");
		}
		
		return dto;
	}

	@Override
	public int insert(MVCBoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		
		try {
			pstm=con.prepareStatement(INS);
			pstm.setString(1, dto.getWriter());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			System.out.println("3. query 준비"+INS);
			
			res=pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			if(res > 0) {
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

	@Override
	public int update(MVCBoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(UPD);
			pstm.setString(1, dto.getTitle());
			pstm.setString(2, dto.getContent());
			pstm.setInt(3, dto.getSeq());
			System.out.println("3. query 준비 : "+UPD);
			
			res=pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			
			if(res>0) {
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

	@Override
	public int delete(int seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm=con.prepareStatement(DEL);
			pstm.setInt(1, seq);
			System.out.println("3. query 준비: "+DEL);
			
			res=pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			if(res>0) {
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

}
