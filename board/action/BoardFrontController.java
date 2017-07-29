package com.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardFrontController extends HttpServlet {
       
	protected void doProcess(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		String RequestURI =request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		
		if(command.equals("/BoardAddAction.bo")){
			action = new BoardInsert();
			try{
				forward = action.execute(request,response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(command.equals("/Boardlist.bo")){
			action = new BoardListAction();
			try{
				forward=action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/BoardDetail.bo")){
			action = new BoardDetail();
			try{
				forward = action.execute(request,response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/BoardDeleteAction.bo")){
			action = new BoardDeleteAction();
			try{
				forward = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/BoardModify.bo")){
			action = new BoardModifyView();
			try{
				forward = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/BoardModifyAction.bo")){
			action = new BoardModifyAction();
			try{
				forward = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/BoardReplyAction.bo")){
			action = new ReplyAction();
			try{
				forward = action.execute(request,response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/replydeleteAction.bo")){
			action = new replydeleteAction();
			try{
				forward =  action.execute(request,response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		//
		if(forward!=null){
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());	
			}else{
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request,response);
				
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

}
