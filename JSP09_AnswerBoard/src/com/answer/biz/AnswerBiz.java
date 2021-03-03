package com.answer.biz;

import java.util.List;

import com.answer.dto.AnswerDto;

public interface AnswerBiz {
	
	public List<AnswerDto> selectList();
	public AnswerDto selectOne(int boardno);
	public int boardInsert(AnswerDto dto);
	public int boardUpdate(AnswerDto dto);
	public int boardDelete(int boardno);
	
	public int answerProc(AnswerDto dto);

}
