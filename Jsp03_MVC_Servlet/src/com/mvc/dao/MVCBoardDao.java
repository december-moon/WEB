package com.mvc.dao;

import java.util.List;

import com.mvc.dto.MVCBoardDto;

public interface MVCBoardDao {
	
	String SELECT_LIST_SQL = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
			+ " FROM MVCBOARD "
			+ " ORDER BY SEQ DESC ";
	String SELECT_ONE_SQL = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
			+ " FROM MVCBOARD "
			+ " WHERE SEQ = ? ";
	String INSERT_SQL = " INSERT INTO MVCBOARD "
			+ " VALUES(MVCBOARDSEQ.NEXTVAL, ?, ?, ?, SYSDATE) ";
	String UPDATE_SQL = " UPDATE MVCBOARD "
			+ " SET TITLE = ?, CONTENT = ? "
			+ " WHERE SEQ = ? ";
	String DELETE_SQL = " DELETE FROM MVCBOARD "
			+ " WHERE SEQ = ? ";
	
	public List<MVCBoardDto> selectList();
	public MVCBoardDto selectOne(int seq);
	public int insert(MVCBoardDto dto);
	public int update(MVCBoardDto dto);
	public int delete(int seq);
	public int multiDelete(String[] seqs);
}
