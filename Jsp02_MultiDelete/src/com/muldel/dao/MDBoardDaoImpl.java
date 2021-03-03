package com.muldel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.muldel.db.JDBCTemplate.*;

import com.muldel.dto.MDBoardDto;

public class MDBoardDaoImpl implements MDBoardDao {

	@Override
	public List<MDBoardDto> selectList() {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs= null;
		List<MDBoardDto> list = new ArrayList<MDBoardDto>();
		
		try {
			pstm = con.prepareStatement(SELECT_LIST_SQL);
			System.out.println("3. query준비: " + SELECT_LIST_SQL);
			
			rs=pstm.executeQuery();
			System.out.println("4. query 실행 및 리턴");
			while (rs.next()) {
				MDBoardDto dto = new MDBoardDto();
				dto.setSeq(rs.getInt(1));
				dto.setWriter(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setRegdate(rs.getDate(5));
				
				//list에 담아 리턴하기 필수
				list.add(dto);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstm);
			close(con);
			System.out.println("5. db 종료");
		}
		
		return list;
	}

	@Override
	public MDBoardDto selectOne(int seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MDBoardDto dto = null;
		
		
		try {
			pstm=con.prepareStatement(SELECT_ONE_SQL);
			pstm.setInt(1, seq);
			System.out.println("3. query 준비: "+SELECT_ONE_SQL);
			
			rs=pstm.executeQuery();
			
			while(rs.next()) {
				dto= new MDBoardDto(rs.getInt("SEQ"), rs.getString("WRITER"), 
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

	@Override
	public int insert(MDBoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		
		try {
			pstm=con.prepareStatement(INSERT_SQL);
			pstm.setString(1, dto.getWriter());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			System.out.println("3. query 준비 : "+INSERT_SQL);
			
			res = pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			if (res > 0) {
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

	@Override
	public int update(MDBoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(UPDATE_SQL);
			pstm.setString(1, dto.getTitle());
			pstm.setString(2, dto.getContent());
			pstm.setInt(3, dto.getSeq());
			System.out.println("3. query 준비 : "+UPDATE_SQL);
			
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
			System.out.println("5. DB 종료");
		}
		
		return res;
	}

	@Override
	public int delete(int seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm=con.prepareStatement(DELETE_SQL);
			pstm.setInt(1, seq);
			System.out.println("3. query 준비 : "+DELETE_SQL);
			
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
			System.out.println("5. db종료");
		}
		
		return res;
	}

	@Override
	public int multiDelete(String[] seqs) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		int[] cnt = null;
		
		try {
			pstm=con.prepareStatement(DELETE_SQL);
			for(int i=0; i<seqs.length; i++) {
				//String type으로도 실행할 수 있다
				pstm.setString(1, seqs[i]);
				
				//메모리에 적재해 놓고 executeBatch() 메소드가 호출될 때, 한번에 실행
				pstm.addBatch();
				System.out.println("3. query 준비 : "+ DELETE_SQL +" (삭제할 번호: "+seqs[i] + ")");
			}
			
			//메모리에 적재되어 있는 sql문들을 한번에 실행
			//성공 -2, 실패 -3. int형 배열[]로 리턴됨!! ex)[-2, -2, -2, -3]...
			cnt = pstm.executeBatch();
			System.out.println("4. query 실행 및 리턴");
			
			// 자바 내부에서 정한 규칙! -2 : 성공, -3 : 실패
			for (int i=0; i<cnt.length; i++) {
				if (cnt[i] == -2) {
					
					//-2인(성공한) 개수 가지고 옴
					res++;
				}
			}
			
			//개수 확인
			//seqs의 길이와 성공한 개수랑 같다면? commit 하자
			if (seqs.length == res) {
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
