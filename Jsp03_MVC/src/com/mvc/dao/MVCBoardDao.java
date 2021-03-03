package com.mvc.dao;

import java.util.List;

import com.mvc.dto.MVCBoardDto;

public interface MVCBoardDao {


	String SEL_LIST = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
			+ "FROM MVCBOARD "
			+ " ORDER BY SEQ DESC ";
	String SEL_ONE = "SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
			+" FROM MVCBOARD "
			+ "WHERE SEQ=? ";
	String INS = " INSERT INTO MVCBOARD "
			+ " VALUES(MVCBOARDSEQ.NEXTVAL, ?, ?, ?, SYSDATE) ";
	String UPD = " UPDATE MVCBOARD "
			+" SET TITLE=?, CONTENT=? WHERE SEQ=? ";
	String DEL = " DELETE FROM MVCBOARD WHERE SEQ=? ";
	
	public List<MVCBoardDto> selectList();
	
	public MVCBoardDto selectOne(int seq);
	
	public int insert(MVCBoardDto dto);
	
	public int update(MVCBoardDto dto);
	
	public int delete(int seq);

}