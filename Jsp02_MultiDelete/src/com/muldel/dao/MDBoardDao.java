package com.muldel.dao;

import java.util.List;

import com.muldel.dto.MDBoardDto;

public interface MDBoardDao {
	
	//어차피 써야 할 것들 상수로 적어 두자
	
	String SELECT_LIST_SQL = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
			+ " FROM MDBOARD "
			+ " ORDER BY SEQ DESC ";
	String SELECT_ONE_SQL = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
			+ " FROM MDBOARD "
			+ " WHERE SEQ=? ";
	String INSERT_SQL = " INSERT INTO MDBOARD "
			+" VALUES (MDBOARDSEQ.NEXTVAL, ?, ?, ?, SYSDATE) ";
	String UPDATE_SQL = " UPDATE MDBOARD "
			+" SET TITLE=?, CONTENT=? "
			+" WHERE SEQ=? ";
	String DELETE_SQL = " DELETE FROM MDBOARD WHERE SEQ = ? ";

	//List로 받는 이유: dao에서 db에 요청했을 때 받는 dto들의 리스트
	public List<MDBoardDto> selectList();
	
	//내가 원하는 int 값 하나만 = primary key(int seq)
	public MDBoardDto selectOne(int seq);
	
	//전달해 준 dto 값에 있는 것들을 (? ? ?)에 담아 하나씩 전달해 줌
	public int insert(MDBoardDto dto);

	public int update(MDBoardDto dto);
	
	//내가 원하는 int 값 하나만 = primary key(int seq)
	public int delete(int seq);
	
	public int multiDelete(String[] seqs);
	
}
