package com.board.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public BoardDAO(){
		try{
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			
		}catch(Exception e){
			System.out.println("DB 연결 실패"+e);
			return;
		}
	}
	
	
	//글 개수 구하기
	public int getListCount(){
		int x = 0;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement("SELECT COUNT(*) FROM MEMBERBOARD");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				x = rs.getInt(1);
			}
		}catch(Exception e){
			System.out.println("getListCount 에러:"+e);
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException e){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException e){}
		}
		return x;
	}
	
	//글 목록 보기
	public List getBoardList(int page,int limit){
		String board_list_sql = "SELECT * FROM (SELECT ROWNUM RNUM,BOARD_NUM,BOARD_ID,BOARD_SUBJECT,BOARD_CONTENT,BOARD_FILE,"
									+"BOARD_READCOUNT,BOARD_DATE FROM(SELECT* FROM MEMBERBOARD ORDER BY BOARD_NUM DESC))"
									+"WHERE RNUM>=? AND RNUM<=?";
		
		List list = new ArrayList();
		
		int startrow = (page-1)*10+1;
		int endrow = startrow +limit -1;
		try{	
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_list_sql);
			pstmt.setInt(1,startrow);
			pstmt.setInt(2,endrow);
			rs = pstmt.executeQuery();

			while(rs.next()){
				BoardBean board = new BoardBean();
				BoardDAO bod = new BoardDAO();
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_ID(rs.getString("BOARD_ID"));
				String subject = rs.getString("BOARD_SUBJECT");
				if(subject==null){
					subject="[제목없음]";
				}
				board.setBOARD_SUBJECT(subject);
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
				int replycount = bod.isReCount(rs.getInt("BOARD_NUM"));
				board.setRE_COUNT(replycount);
				list.add(board);
			}
			
			return list;	
		}catch(Exception e){
			System.out.println("getBoardList err:"+e);
		}finally{
			if(rs != null) try{rs.close();}catch(SQLException e){}
			if(pstmt != null) try{pstmt.close();}catch(SQLException e){}
		}
		
		return null;
	}
	
	
	//글 내용 보기
	public BoardBean getDetail(int num) throws Exception{
		BoardBean board = null;
		try{
			con= ds.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM MEMBERBOARD WHERE BOARD_NUM = ?");
			pstmt.setInt(1,num);
			
			rs=pstmt.executeQuery();
			
				if(rs.next()){
					board = new BoardBean();
					board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
					board.setBOARD_ID(rs.getString("BOARD_ID"));
					String subject = rs.getString("BOARD_SUBJECT");
					if(subject==null){
						subject="[제목없음]";
					}
					board.setBOARD_SUBJECT(subject);
					String contents = rs.getString("BOARD_CONTENT");
					if(contents ==null){
						contents="[내용없음]";
					}
					board.setBOARD_CONTENT(contents.replace("\r\n","<br>"));
					board.setBOARD_FILE(rs.getString("BOARD_FILE"));
					board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
					board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
					//board.setRE_COUNT(isReCount());
				}

				return board;
		}catch(Exception e){
			System.out.println("getDetail 에러:"+e);
		}finally{
			if(rs!= null)try{rs.close();}catch(SQLException e){}
			if(pstmt!= null)try{pstmt.close();}catch(SQLException e){}
		}
		return board;

	}
	
	//댓글 조회
	public List getRE_detail(int num)throws Exception{
		List list = new ArrayList();
		try{
			con= ds.getConnection();
			pstmt=con.prepareStatement("SELECT* FROM REPLY_BOARD WHERE RE_NUMBER=? ORDER BY RE_DATE ASC");
			pstmt.setInt(1,num);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				BoardBean board = new BoardBean();
				board.setRE_ID(rs.getString("RE_ID"));
				String re_contents = rs.getString("RE_WRITE");
				board.setRE_CONTENT(re_contents.replace("\r\n","<br>"));
				board.setRE_DATE(rs.getDate("RE_DATE"));
				board.setRE_COUNT(rs.getInt("REPLY_NUMBER"));
				list.add(board);
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!= null)try{rs.close();}catch(SQLException e){}
			if(pstmt!= null)try{pstmt.close();}catch(SQLException e){}
		}
		
		return list;
		
	}
	
	
	
	
	//글등록
	public boolean boardInsert(BoardBean board){
		int num =0;
		String sql = "";
		
		int result = 0 ;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement("SELECT MAX(BOARD_NUM) FROM MEMBERBOARD");
			rs = pstmt.executeQuery();
			
			if(rs.next())
				num = rs.getInt(1)+1;
			else
				num = 1;
			
			sql ="INSERT INTO MEMBERBOARD(BOARD_NUM,BOARD_ID,BOARD_SUBJECT,";
			sql+= "BOARD_CONTENT,BOARD_FILE,BOARD_READCOUNT,BOARD_DATE) VALUES(?,?,?,?,?,?,SYSDATE)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			pstmt.setString(2,board.getBOARD_ID());
			pstmt.setString(3,board.getBOARD_SUBJECT());
			pstmt.setString(4,board.getBOARD_CONTENT());
			pstmt.setString(5,board.getBOARD_FILE());
			pstmt.setInt(6,0);	
			
			result = pstmt.executeUpdate();
			if(result == 0) return false;
			
			return true;
		}catch(Exception e){
			System.out.println("boardInsert 에러:"+e);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		}
		
		return false;
	}
	
	//댓글쓰기
	public boolean boardReply(BoardBean board){
		int num = 0;
		
		int result = 0;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement("SELECT MAX(REPLY_NUMBER) FROM REPLY_BOARD");
			rs = pstmt.executeQuery();
			
			if(rs.next())
				num = rs.getInt(1) +1;
			else
				num =1;
			String sql = "INSERT INTO REPLY_BOARD VALUES(?,?,?,SYSDATE,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,board.getRE_ID());
			pstmt.setString(2,board.getRE_CONTENT());
			pstmt.setInt(3,board.getRE_NUM());
			pstmt.setInt(4,num);
			pstmt.executeUpdate();
			return true;		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	//글 수정
	public boolean boardModify(BoardBean modifyboard)throws Exception{
		String sql = "UPDATE MEMBERBOARD SET BOARD_SUBJECT=?,BOARD_CONTENT = ? WHERE BOARD_NUM =?";
		
		try{
			con = ds.getConnection();
			pstmt  = con.prepareStatement(sql);
			pstmt.setString(1,modifyboard.getBOARD_SUBJECT());
			pstmt.setString(2,modifyboard.getBOARD_CONTENT());
			pstmt.setInt(3,modifyboard.getBOARD_NUM());
			pstmt.executeUpdate();
			return true;
		}catch(Exception e){
			System.out.println("boardmodify err"+e);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		}
		return false;
	}
	

	//글삭제
	public boolean boardDelete(int num) {
		String board_delete_sql="DELETE FROM MEMBERBOARD WHERE BOARD_NUM=?";
		
		int result= 0;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_delete_sql);
			pstmt.setInt(1,num);
			result = pstmt.executeUpdate();
			if(result==0)return false;
			
			return true;
		}catch(Exception e){
			System.out.println("board delete err:"+e);
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
			}catch(Exception e){}
		}
		
		return false;
	}
	
	//댓글 삭제
	public boolean replydelete(int num,String id,int page){
		String sql = "DELETE FROM REPLY_BOARD WHERE RE_ID=?AND RE_NUMBER=?AND REPLY_NUMBER=?";
		
		int result = 0;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setInt(2,page);
			pstmt.setInt(3,num);
			result = pstmt.executeUpdate();
			if(result==0)return false;
			return true;
		}catch(Exception e){
			System.out.println("err:"+e);
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
			}catch(Exception e){}
		}
		
		return false;
	}
	
	//조회수 업데이트
	public void setReadCountUpdate(int num)throws Exception{
		String sql = "UPDATE MEMBERBOARD SET BOARD_READCOUNT = BOARD_READCOUNT +1 WHERE BOARD_NUM ="+num;
		
		try{
			con=ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			System.out.println("setReadcount err:"+e);
		}
	}
	
	//로그인 확인
	public boolean isBoardWriter(int num, String id) {
		String board_sql ="SELECT * FROM MEMBERBOARD WHERE BOARD_NUM=?";
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_sql);
			pstmt.setInt(1,num);
			rs=pstmt.executeQuery();
			rs.next();
			if(id==null){
				return false;
			}
			if( id.equals(rs.getString("BOARD_ID"))){
				return true;
			}
		}catch(SQLException e){
			System.out.println("login err:"+e);
		}
		return false;
	}
	
	//댓글 개수 구하기
	public int isReCount(int num){
		String sql = "SELECT COUNT(RE_ID) FROM REPLY_BOARD WHERE RE_NUMBER=? ";
		int result=0;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			rs =pstmt.executeQuery();
			rs.next();
			result=rs.getInt(1);
			return result;
		}catch(SQLException e){
			System.out.println("recount err:"+e);
		}
		return result;
		
	}

	//글 삭제 할때 댓글도 같이 삭제하기
	public boolean replyDelete(int num) {
		String sql = "DELETE FROM REPLY_BOARD WHERE RE_NUMBER=?";
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			pstmt.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
			}catch(Exception e){
				
			}
		}
		
		return false;
	}

}
