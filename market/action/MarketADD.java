package com.market.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.market.db.marketBean;
import com.market.db.marketDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MarketADD implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		marketDAO mDAO = new marketDAO();
		marketBean mb  = new marketBean();
		String realFolder="";
		String saveFolder = "mkMainImage";
		
		int fileSize =50*1024*1024;
		
		realFolder = request.getRealPath(saveFolder);
		try {
			MultipartRequest multi = null;
			
			multi = new MultipartRequest(request,realFolder,fileSize,"euc-kr",new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();
			mb.setMARKET_ID(multi.getParameter("MARKET_ID"));
			mb.setMARKET_SUBJECT(multi.getParameter("MARKET_SUBJECT"));
			mb.setMARKET_CONTENT(multi.getParameter("MARKET_CONTENT"));
			if(multi.getFilesystemName("MARKET_MAIN_IMAGE")==null){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('메인 이미지를 등록하세요')");
				out.println("location.href='./index.jsp?page=market/marketInsert';");
				out.println("</script>");
				out.close();
				return null;
			}
			mb.setMARKET_MAIN_IMAGE(multi.getFilesystemName("MARKET_MAIN_IMAGE"));
			mb.setMARKET_IMAGE(multi.getFilesystemName("MARKET_IMAGE"));
			mb.setMARKET_PRICE(Integer.parseInt(multi.getParameter("MARKET_PRICE")));
			mb.setMARKET_WAY(multi.getParameter("MARKET_CHECK"));
			            
			mDAO.marketInsert(mb);
			
			forward.setRedirect(true);
			forward.setPath("./marketlist.mk");
			return forward;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

}
