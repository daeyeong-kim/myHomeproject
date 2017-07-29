package com.board.db;

import java.sql.Date;

public class BoardBean {
	private int BOARD_NUM;
	private String BOARD_ID;
	private String BOARD_SUBJECT;
	private String BOARD_CONTENT;
	private String BOARD_FILE;
	private int BOARD_READCOUNT;
	private Date BOARD_DATE;
	private String RE_ID;
	private String RE_CONTENT;
	private int RE_NUM;
	private int RE_COUNT;
	private Date RE_DATE;
	
	public Date getRE_DATE() {
		return RE_DATE;
	}
	public void setRE_DATE(Date rE_DATE) {
		RE_DATE = rE_DATE;
	}
	public int getRE_COUNT() {
		return RE_COUNT;
	}
	public void setRE_COUNT(int rE_COUNT) {
		RE_COUNT = rE_COUNT;
	}
	public String getRE_ID() {
		return RE_ID;
	}
	public void setRE_ID(String rE_ID) {
		RE_ID = rE_ID;
	}
	public String getRE_CONTENT() {
		return RE_CONTENT;
	}
	public void setRE_CONTENT(String rE_CONTENT) {
		RE_CONTENT = rE_CONTENT;
	}
	public int getRE_NUM() {
		return RE_NUM;
	}
	public void setRE_NUM(int rE_NUM) {
		RE_NUM = rE_NUM;
	}
	public int getBOARD_NUM() {
		return BOARD_NUM;
	}
	public void setBOARD_NUM(int bOARD_NUM) {
		BOARD_NUM = bOARD_NUM;
	}
	public String getBOARD_ID() {
		return BOARD_ID;
	}
	public void setBOARD_ID(String bOARD_ID) {
		BOARD_ID = bOARD_ID;
	}
	public String getBOARD_SUBJECT() {
		return BOARD_SUBJECT;
	}
	public void setBOARD_SUBJECT(String bOARD_SUBJECT) {
		BOARD_SUBJECT = bOARD_SUBJECT;
	}
	public String getBOARD_CONTENT() {
		return BOARD_CONTENT;
	}
	public void setBOARD_CONTENT(String bOARD_CONTENT) {
		BOARD_CONTENT = bOARD_CONTENT;
	}
	public String getBOARD_FILE() {
		return BOARD_FILE;
	}
	public void setBOARD_FILE(String bOARD_FILE) {
		BOARD_FILE = bOARD_FILE;
	}
	public int getBOARD_READCOUNT() {
		return BOARD_READCOUNT;
	}
	public void setBOARD_READCOUNT(int bOARD_READCOUNT) {
		BOARD_READCOUNT = bOARD_READCOUNT;
	}
	public Date getBOARD_DATE() {
		return BOARD_DATE;
	}
	public void setBOARD_DATE(Date bOARD_DATE) {
		BOARD_DATE = bOARD_DATE;
	}
}
