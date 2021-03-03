package com.muldel.biz;

import java.util.List;

import com.muldel.dto.MDBoardDto;

public interface MDBoardBiz {
	
		public List<MDBoardDto> selectList();
		public MDBoardDto selectOne(int seq);
		public int insert(MDBoardDto dto);
		public int update(MDBoardDto dto);
		public int delete(int seq);
		
		public int multiDelete(String[] seq);

}
