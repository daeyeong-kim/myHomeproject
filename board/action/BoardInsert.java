package com.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.db.BoardBean;
import com.board.db.BoardDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardInsert implements Action {

	@SuppressWarnings("deprecation")
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		ActionForward forward = new ActionForward();
		String realFolder = "";
		String saveFolder = "boardupload";
		
		int fileSize = 5000 * 1024 * 1024 ;
		
		realFolder = request.getRealPath(saveFolder);
		
		boolean result =false;
		
		try{
			MultipartRequest multi = null;
			
			multi = new MultipartRequest(request,realFolder,fileSize,"euc-kr",new DefaultFileRenamePolicy());
			
			boarddata.setBOARD_ID(multi.getParameter("BOARD_ID"));
			boarddata.setBOARD_SUBJECT(multi.getParameter("BOARD_SUBJECT"));
			boarddata.setBOARD_CONTENT(multi.getParameter("BOARD_CONTENT"));
			boarddata.setBOARD_FILE(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
			
			result = boarddao.boardInsert(boarddata);
			
			if(result == false){
				System.out.println(boarddata.getBOARD_ID()+"게시판 등록 실패");
				return null;
			}
			System.out.println(boarddata.getBOARD_ID()+"게시판 등록 완료");
			forward.setRedirect(true);
			forward.setPath("Boardlist.bo");
			return forward;
		}catch(Exception e){
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

}
