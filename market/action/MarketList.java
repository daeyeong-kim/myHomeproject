package com.market.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.market.db.marketDAO;

public class MarketList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		ActionForward forward = new ActionForward();
		marketDAO mdao = new marketDAO();
		
		int listcount = mdao.marketListCount();  //게시물 개수
		List marketlist = mdao.marketList();
		
		request.setAttribute("count", listcount);
		request.setAttribute("marketlist", marketlist);
		
		forward.setRedirect(false);
		forward.setPath("index.jsp?page=market/marketMain");
		return forward;
	}

}
