package com.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.login.dto.MYMemberDto;
import static com.login.db.JDBCTemplate.*;

public class MYMemberDao {
	
	/*
	 * 관리자(ADMIN) 기능
	 * 1. 회원 전체 정보 확인 (탈퇴한 회원도 포함)
	 * 2. 회원 전체 정보 확인 (MYENABLED='Y' 즉, 탈퇴 안 한 회원들의 정보)
	 * 3. 회원 등급 조정 (ADMIN <-> USER)
	 * 
	 */
	
	//1. 전체정보
	public List<MYMemberDto> selectAlluser(){
		Connection con = getConnection();
		String sql = " SELECT MYNO, MYID, MYPW, MYNAME, MYADDR, MYPHONE, MYEMAIL, MYENABLED, MYROLE "
				+ " FROM MYMEMBER ORDER BY MYNO DESC ";
		PreparedStatement pstm=null;
		ResultSet rs=null;
		List<MYMemberDto> list = new ArrayList<MYMemberDto>();
		
		try {
			pstm=con.prepareStatement(sql);
			System.out.println("3. query 준비"+sql);
			
			rs=pstm.executeQuery();
			System.out.println("4. query 실행 및 리턴");
			
			while(rs.next()) {
				MYMemberDto dto = new MYMemberDto(rs.getInt(1),rs.getString(2),rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9));
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
	
	//2. 전체 정보(탈퇴 안 한)
	public List<MYMemberDto> selectEnableUser(){
		Connection con = getConnection();
		String sql = " SELECT MYNO, MYID, MYPW, MYNAME, MYADDR, MYPHONE, MYEMAIL, MYENABLED, MYROLE "
				+ " FROM MYMEMBER "
				+ " WHERE MYENABLED = 'Y' ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<MYMemberDto> list = new ArrayList<MYMemberDto>();
		
		try {
			pstm=con.prepareStatement(sql);
			System.out.println("3. query 준비 :"+sql);
			
			rs=pstm.executeQuery();
			System.out.println("4. query 실행 및 리턴");
			
			while(rs.next()) {
				MYMemberDto dto = new MYMemberDto();
				dto.setMyno(rs.getInt(1));
				dto.setMyid(rs.getString(2));
				dto.setMypw(rs.getNString(3));
				dto.setMyname(rs.getString(4));
				dto.setMyaddr(rs.getString(5));
				dto.setMyphone(rs.getNString(6));
				dto.setMyemail(rs.getString(7));
				dto.setMyenabled(rs.getString(8));
				dto.setMyrole(rs.getString(9));
				
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
	
	//3. 회원 등급 조정
	public int updateRole(int myno, String myrole) {
		Connection con = getConnection();
		String sql = " UPDATE MYMEMBER SET MYROLE = ? "
				+ " WHERE MYNO = ? ";
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, myrole);
			pstm.setInt(2, myno);
			System.out.println("3. query 준비 :"+sql);
			
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
	
	/*
	 * 사용자(USER) 기능
	 * 1. 로그인
	 * 3. 회원가입 <- 2. 아이디 중복체크
	 * 4. 내 정보 조회
	 * 5. 내 정보 수정
	 * 6. 회원 탈퇴 (delete 쓰지 말기. update : myenabled를 n으로 바꾸자.)
	 * 
	 */

	//1. 로그인
	public MYMemberDto login(String myid, String mypw) {
		Connection con = getConnection();
		String sql = " SELECT MYNO, MYID, MYPW, MYNAME, MYADDR, MYPHONE, MYEMAIL, MYENABLED, MYROLE "
				+" FROM MYMEMBER "
				+" WHERE MYID = ? "
				+" AND MYPW =? ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		//<<side effect>> 
		// MYMemberDto dto = null이 아닌 무언가 값이 들어가 있으면, 컨트롤러 if(dto !=null)문에서 무조건 로그인 성공하게 됨.
		// 왜?? null이 아니라 값이 들어가 있으니까!!
		MYMemberDto dto = null;
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, myid);
			pstm.setString(2, mypw);
			System.out.println("3. query 준비 : "+sql);
			
			rs=pstm.executeQuery();
			System.out.println("4. query 실행 및 리턴");
			while(rs.next()) {
				dto=new MYMemberDto();
				dto.setMyno(rs.getInt(1));
				dto.setMyid(rs.getString(2));
				dto.setMypw(rs.getString(3));
				dto.setMyname(rs.getString(4));
				dto.setMyaddr(rs.getString(5));
				dto.setMyphone(rs.getString(6));
				dto.setMyemail(rs.getString(7));
				dto.setMyenabled(rs.getString(8));
				dto.setMyrole(rs.getString(9));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstm, con);
		}
		
		
		return dto;
	}
	//2.중복체크
	public MYMemberDto idCheck(String myid) {
		Connection con = getConnection();
		String sql = " SELECT MYNO, MYID, MYPW, MYNAME, MYADDR, MYPHONE, MYEMAIL, MYENABLED, MYROLE "
				+ " FROM MYMEMBER WHERE MYID = ? ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		//컨트롤러에서 dto에 != null 넣어뒀기 때문에 null 하면 안된다
		MYMemberDto dto=new MYMemberDto();
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, myid);
			System.out.println("3. 쿼리준비:"+sql);
			
			rs=pstm.executeQuery();
			System.out.println("4. 쿼리 실행 및 리턴");
			
			while(rs.next()) {
				dto=new MYMemberDto();
				dto.setMyno(rs.getInt(1));
				dto.setMyid(rs.getString(2));
				dto.setMypw(rs.getString(3));
				dto.setMyname(rs.getString(4));
				dto.setMyaddr(rs.getString(5));
				dto.setMyphone(rs.getString(6));
				dto.setMyemail(rs.getString(7));
				dto.setMyenabled(rs.getString(8));
				dto.setMyrole(rs.getString(9));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstm, con);
			System.out.println("5. db 종료");
		}
		
		return dto;
	}
	//3.회원가입
	public int insertUser(MYMemberDto dto) {
		return 0;
	}
	//4.정보 조회
	public MYMemberDto selectUser(int myno) {
		Connection con = getConnection();
		String sql = " SELECT MYNO, MYID, MYPW, MYNAME, MYADDR, MYPHONE, MYEMAIL, MYENABLED, MYROLE "
				+ " FROM MYMEMBER WHERE MYNO = ? ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MYMemberDto dto = null;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, myno);
			System.out.println("3. 쿼리 준비:"+sql);
			
			rs=pstm.executeQuery();
			System.out.println("4. 쿼리 실행 및 리턴");
			while(rs.next()) {
				dto=new MYMemberDto();
				dto.setMyno(rs.getInt(1));
				dto.setMyid(rs.getString(2));
				dto.setMypw(rs.getString(3));
				dto.setMyname(rs.getString(4));
				dto.setMyaddr(rs.getString(5));
				dto.setMyphone(rs.getString(6));
				dto.setMyemail(rs.getString(7));
				dto.setMyenabled(rs.getString(8));
				dto.setMyrole(rs.getString(9));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstm, con);
			System.out.println("5. db 종료");
		}
		
		return dto;
	}
	//5.정보 수정
	public int updateUser(MYMemberDto dto) {
		
		
		
		return 0;
	}
	//6.회원 탈퇴(update)
	public int deleteUser(int myno) {
		return 0;
	}
}
