package com.mvc.dto;

import java.util.Date;

public class MVCBoardDto {

	private int seq;
	private String writer;
	private String title;
	private String content;
	private Date date;
	
	public MVCBoardDto() {
		
	}
	public MVCBoardDto(int seq, String writer, String title, String content, Date date) {
		this.seq = seq;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.date = date;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
