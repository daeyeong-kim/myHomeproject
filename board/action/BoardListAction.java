package com.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.db.BoardBean;
import com.board.db.BoardDAO;

public class BoardListAction implements Action {
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ActionForward forward = new ActionForward();
	
	BoardDAO boarddao = new BoardDAO();
	List boardlist = new ArrayList();
	
	int page =1;
	int limit =10;

	if(request.getParameter("page") !=null){
		page=Integer.parseInt(request.getParameter("page"));
	}
	
	int listcount = boarddao.getListCount();
	boardlist = boarddao.getBoardList(page, limit);
	
	int maxpage= (int)((double)listcount/limit+0.95);
	int startpage = (((int)((double)page /10 + 0.9))-1) *10+1;
	int endpage = (listcount/10)+1;
	
	if(endpage>startpage+10 - 1)endpage = maxpage;
	
	request.setAttribute("page", page);
	request.setAttribute("maxpage", maxpage);
	request.setAttribute("startpage", startpage);
	request.setAttribute("endpage", endpage);
	request.setAttribute("listcount",listcount);
	request.setAttribute("boardlist", boardlist);
	
	forward.setRedirect(false);
	forward.setPath("index.jsp?page=board/main");
	return forward;
	}
}
