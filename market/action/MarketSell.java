package com.market.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.market.db.marketDAO;

public class MarketSell implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		marketDAO mdao = new marketDAO();
		int page = Integer.parseInt(request.getParameter("page"));
		mdao.marketsell(page);
		
		forward.setRedirect(false);
		forward.setPath("./marketlist.mk");
		return forward;
	}

}
