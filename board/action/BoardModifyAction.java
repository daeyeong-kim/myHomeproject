package com.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.db.BoardBean;
import com.board.db.BoardDAO;

public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// TODO Auto-generated method stub
			request.setCharacterEncoding("euc-kr");
			ActionForward forward = new ActionForward();
			boolean result = false;
			
			int num = Integer.parseInt(request.getParameter("BOARD_NUM"));
			
			BoardDAO boarddao = new BoardDAO();
			BoardBean boarddata = new BoardBean();
			
			boolean usercheck = boarddao.isBoardWriter(num,request.getParameter("BOARD_ID"));
			
			if(usercheck == false){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정할 권한이 없습니다.');");
				out.println("location.href='./Boardlist.bo';");
				out.println("</script>");
				out.close();
				return null;
			}
			
			try{
				boarddata.setBOARD_NUM(num);
				boarddata.setBOARD_SUBJECT(request.getParameter("BOARD_SUBJECT"));
				boarddata.setBOARD_CONTENT(request.getParameter("BOARD_CONTENT"));
				result = boarddao.boardModify(boarddata);
				if(result == false){
					System.out.println("err");
					return null;
				}
				forward.setRedirect(true);
				forward.setPath("./BoardDetail.bo?num="+boarddata.getBOARD_NUM());
				return forward;
			}catch(Exception e){
				e.printStackTrace();
			}
		return null;
	}

}

