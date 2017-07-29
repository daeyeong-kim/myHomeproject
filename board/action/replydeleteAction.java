package com.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.db.BoardDAO;

public class replydeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("euc-kr");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		BoardDAO boarddao = new BoardDAO();
		
		int num = Integer.parseInt(request.getParameter("num"));
		int page = Integer.parseInt(request.getParameter("page"));
		String id_chk = request.getParameter("id");
		
		
		boolean result= boarddao.replydelete(num,id,page);
		
		
		forward.setRedirect(true);
		forward.setPath("./BoardDetail.bo?num="+page);	
		return forward;
	}

	
}
