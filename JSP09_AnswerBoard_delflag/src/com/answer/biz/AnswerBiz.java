package com.answer.biz;

import java.util.List;

import com.answer.dto.AnswerDto;

public interface AnswerBiz {
	
	public List<AnswerDto> selectList();
	public AnswerDto selectOne(int boardno);
	public boolean boardInsert(AnswerDto dto);
	public boolean boardUpdate(AnswerDto dto);
	public boolean boardDelete(int boardno);
	
	public int answerProc(AnswerDto dto);

}
