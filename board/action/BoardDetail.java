package com.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.db.BoardBean;
import com.board.db.BoardDAO;

public class BoardDetail implements Action{
		public ActionForward execute(HttpServletRequest request,HttpServletResponse response)throws Exception{
			request.setCharacterEncoding("euc-kr");
			
			BoardDAO boarddao  = new BoardDAO();
			BoardBean boarddata = new BoardBean();
			List replylist = new ArrayList();
			
			int num = Integer.parseInt(request.getParameter("num"));
			boarddao.setReadCountUpdate(num);
			boarddata =  boarddao.getDetail(num);
			replylist = boarddao.getRE_detail(num);
			
			if(boarddata ==null){
				//System.out.println("상세보기 실패");
				return null;
			}
			//System.out.println("상세보기 성공");
			
			
			request.setAttribute("replylist",replylist);
			request.setAttribute("boarddata",boarddata);
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("index.jsp?page=board/board_view");
			return forward;
		}

}
