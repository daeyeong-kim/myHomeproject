package com.market.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.market.db.marketDAO;

public class MarketDetail implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		marketDAO mdao = new marketDAO();
		
	
		forward.setRedirect(false);
		forward.setPath("index.jsp?page=market/marketDetail");
		return forward;
	}

}
