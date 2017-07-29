package com.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.db.BoardBean;
import com.board.db.BoardDAO;

public class ReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("euc-kr");
		ActionForward forward = new ActionForward();
		
		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		
		boarddata.setRE_ID(request.getParameter("reply_id"));
		boarddata.setRE_CONTENT(request.getParameter("reply_text"));
		boarddata.setRE_NUM(Integer.parseInt(request.getParameter("num")));
		boolean result =false;
		
		result = boarddao.boardReply(boarddata);
		if(result==false){
			System.out.println("댓글 달기 실패");
		}
		
		// System.out.println("댓글 달기 성공");
		
		forward.setRedirect(true);
		forward.setPath("./BoardDetail.bo?num="+boarddata.getRE_NUM());
		
		return forward;
	}

}
