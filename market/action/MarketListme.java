package com.market.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.market.db.marketDAO;

public class MarketListme implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		marketDAO mdao = new marketDAO();
		String id = request.getParameter("id");
		System.out.println(id);
		int listcount = mdao.marketListCount();  //게시물 개수
		List marketlist = mdao.marketListme(id);
		
		request.setAttribute("count", listcount);
		request.setAttribute("marketlist", marketlist);
		
		forward.setRedirect(false);
		forward.setPath("index.jsp?page=market/marketMain");
		return forward;
	}

}
