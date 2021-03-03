package com.mvc.biz;

import java.util.List;

import com.mvc.dto.MVCBoardDto;

public interface MVCBoardBiz {

	public List<MVCBoardDto> selectList();
	public MVCBoardDto selectOne(int seq);
	public int insert(MVCBoardDto dto);
	public int update(MVCBoardDto dto);
	public int delete(int seq);
	public int multiDelete(String[] seqs);
}
