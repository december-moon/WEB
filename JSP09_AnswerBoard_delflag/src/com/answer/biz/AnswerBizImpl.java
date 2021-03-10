package com.answer.biz;

import java.util.List;

import com.answer.dao.AnswerDao;
import com.answer.dao.AnswerDaoImpl;
import com.answer.dto.AnswerDto;

public class AnswerBizImpl implements AnswerBiz {
	
	private AnswerDao dao = new AnswerDaoImpl();

	@Override
	public List<AnswerDto> selectList() {
		return dao.selectList();
	}

	@Override
	public AnswerDto selectOne(int boardno) {
		return dao.selectOne(boardno);
	}

	@Override
	public boolean boardInsert(AnswerDto dto) {
		return dao.boardInsert(dto);
	}

	@Override
	public boolean boardUpdate(AnswerDto dto) {
		return dao.boardUpdate(dto);
	}

	@Override
	public boolean boardDelete(int boardno) {
		return dao.boardDelete(boardno);
	}

	@Override
	public int answerProc(AnswerDto dto) {
		
		//business logic (service logic)에서 transaction 처리
		
		int update = dao.answerUpdate(dto.getBoardno());
		int insert = dao.answerInsert(dto);
		
		return update + insert;
	}

}
