package com.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.db.*;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("euc-kr");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");

		boolean result = false;
		boolean replyresult = false;
		boolean usercheck = false;
		int num = Integer.parseInt(request.getParameter("num"));
		
		BoardDAO boarddao = new BoardDAO();
		
		usercheck = boarddao.isBoardWriter(num, id);
		if(usercheck == false){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('������ ������ �����ϴ�.');");
			out.print("location.href='./Boardlist.bo';");
			out.print("</script>");
			out.close();
			return null;
		}
		
		result = boarddao.boardDelete(num);
		replyresult = boarddao.replyDelete(num);
		
		if(result==false){
			System.out.println("�Խ��� ���� ����");
			return null;
		}
		
		if(replyresult == false){
			System.out.println("��� ���� ����");
			return null;
		}
		
		System.out.println("�Խ��� ���� ����");
		forward.setRedirect(true);
		forward.setPath("./Boardlist.bo");
		return forward;
	}

}
