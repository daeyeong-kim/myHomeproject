package com.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.db.BoardBean;
import com.board.db.BoardDAO;

public class BoardModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ActionForward forward = new ActionForward();
			request.setCharacterEncoding("euc-kr");
			
			BoardDAO boarddao = new BoardDAO();
			BoardBean boarddata = new BoardBean();
			
			int num = Integer.parseInt(request.getParameter("num"));
			boarddata = boarddao.getDetail(num);
		
			if(boarddata == null){
				return null;
			}
			
			request.setAttribute("boarddata", boarddata);
			forward.setRedirect(false);
			forward.setPath("index.jsp?page=board/board_modify");
		return forward;
	}

}
