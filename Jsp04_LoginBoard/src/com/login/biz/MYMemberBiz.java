package com.login.biz;

import java.util.List;

import com.login.dao.MYMemberDao;
import com.login.dto.MYMemberDto;

public class MYMemberBiz {
	
	MYMemberDao dao;
	
	public MYMemberBiz() {
		dao = new MYMemberDao();
	}
	
	/*
	 * 관리자(ADMIN) 기능
	 * 1. 회원 전체 정보 확인 (탈퇴한 회원도 포함)
	 * 2. 회원 전체 정보 확인 (MYENABLED='Y' 즉, 탈퇴 안 한 회원들의 정보)
	 * 3. 회원 등급 조정 (ADMIN <-> USER)
	 * 
	 */
	
	//1. 전체정보
	public List<MYMemberDto> selectAlluser(){
		return dao.selectAlluser();
	}
	
	//2. 전체 정보(탈퇴 안 한)
	public List<MYMemberDto> selectEnableUser(){
		return dao.selectEnableUser();
	}
	
	//3. 회원 등급 조정
	public int updateRole(int myno, String myrole) {
		return dao.updateRole(myno, myrole);
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
		return dao.login(myid, mypw);
	}
	//2.중복체크
	public MYMemberDto idCheck(String myid) {
		return dao.idCheck(myid);
	}
	//3.회원가입
	public int insertUser(MYMemberDto dto) {
		return 0;
	}
	//4.정보 조회
	public MYMemberDto selectUser(int myno) {
		return dao.selectUser(myno);
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
