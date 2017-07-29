package com.market.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.sql.DataSource;

public class marketDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public marketDAO(){
		try{
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			
		}catch(Exception e){
			System.out.println("DB연결 실패");
			return;
		}
	
	}
	
	//리스트 개수 구하기
	public int marketListCount(){
		int x = 0 ;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement("SELECT COUNT(*) FROM MARKET");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				x= rs.getInt(1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException e){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException e){}	
		}
		
		return x;
	}
	
	//글 목록 리스트에 넣기
	public List marketList(){
		List marketlist = new ArrayList();
		String sql = "SELECT MARKET_NUM,MARKET_ID,MARKET_SUBJECT,MARKET_MAIN_IMAGE,MARKET_PRICE,MARKET_CHECK FROM MARKET ORDER BY MARKET_NUM DESC";
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				marketBean market = new marketBean();
				market.setMARKET_NUM(rs.getInt("MARKET_NUM"));
				market.setMARKET_ID(rs.getString("MARKET_ID"));
				market.setMARKET_SUBJECT(rs.getString("MARKET_SUBJECT"));
				market.setMARKET_MAIN_IMAGE(rs.getString("MARKET_MAIN_IMAGE"));
				market.setMARKET_PRICE(rs.getInt("MARKET_PRICE"));
				market.setMARKET_CHECK(rs.getInt("MARKET_CHECK"));
				
				marketlist.add(market);
			}
			return marketlist;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getmarketlist err:"+e);
		}finally{
			if(rs != null) try{rs.close();}catch(SQLException e){}
			if(pstmt != null) try{pstmt.close();}catch(SQLException e){}
		}
		return null;
	}
	
	//높은 가격 순으로
	public List marketListHigh() {
		// TODO Auto-generated method stub
		List marketlist = new ArrayList();
		String sql = "SELECT MARKET_NUM,MARKET_ID,MARKET_SUBJECT,MARKET_MAIN_IMAGE,MARKET_PRICE,MARKET_CHECK FROM MARKET ORDER BY MARKET_PRICE DESC";
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				marketBean market = new marketBean();
				market.setMARKET_NUM(rs.getInt("MARKET_NUM"));
				market.setMARKET_ID(rs.getString("MARKET_ID"));
				market.setMARKET_SUBJECT(rs.getString("MARKET_SUBJECT"));
				market.setMARKET_MAIN_IMAGE(rs.getString("MARKET_MAIN_IMAGE"));
				market.setMARKET_PRICE(rs.getInt("MARKET_PRICE"));
				market.setMARKET_CHECK(rs.getInt("MARKET_CHECK"));
				
				marketlist.add(market);
			}
			return marketlist;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getmarketlist err:"+e);
		}finally{
			if(rs != null) try{rs.close();}catch(SQLException e){}
			if(pstmt != null) try{pstmt.close();}catch(SQLException e){}
		}
		
		return null;
	}
	//낮은 가격순으로
	public List marketListlow() {
		// TODO Auto-generated method stub
				List marketlist = new ArrayList();
				String sql = "SELECT MARKET_NUM,MARKET_ID,MARKET_SUBJECT,MARKET_MAIN_IMAGE,MARKET_PRICE,MARKET_CHECK FROM MARKET ORDER BY MARKET_PRICE ASC";
				
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						marketBean market = new marketBean();
						market.setMARKET_NUM(rs.getInt("MARKET_NUM"));
						market.setMARKET_ID(rs.getString("MARKET_ID"));
						market.setMARKET_SUBJECT(rs.getString("MARKET_SUBJECT"));
						market.setMARKET_MAIN_IMAGE(rs.getString("MARKET_MAIN_IMAGE"));
						market.setMARKET_PRICE(rs.getInt("MARKET_PRICE"));
						market.setMARKET_CHECK(rs.getInt("MARKET_CHECK"));
						
						marketlist.add(market);
					}
					return marketlist;
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("getmarketlist err:"+e);
				}finally{
					if(rs != null) try{rs.close();}catch(SQLException e){}
					if(pstmt != null) try{pstmt.close();}catch(SQLException e){}
				}
				
				return null;
	}
	//내가 올린 게시물
	public List marketListme(String s) {
		// TODO Auto-generated method stub
		List marketlist = new ArrayList();
		String sql = "SELECT MARKET_NUM,MARKET_ID,MARKET_SUBJECT,MARKET_MAIN_IMAGE,MARKET_PRICE,MARKET_CHECK FROM MARKET WHERE MARKET_ID=? ORDER BY MARKET_PRICE ASC";
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,s);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				marketBean market = new marketBean();
				market.setMARKET_NUM(rs.getInt("MARKET_NUM"));
				market.setMARKET_ID(rs.getString("MARKET_ID"));
				market.setMARKET_SUBJECT(rs.getString("MARKET_SUBJECT"));
				market.setMARKET_MAIN_IMAGE(rs.getString("MARKET_MAIN_IMAGE"));
				market.setMARKET_PRICE(rs.getInt("MARKET_PRICE"));
				market.setMARKET_CHECK(rs.getInt("MARKET_CHECK"));
				
				marketlist.add(market);
			}
			return marketlist;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getmarketlist err:"+e);
		}finally{
			if(rs != null) try{rs.close();}catch(SQLException e){}
			if(pstmt != null) try{pstmt.close();}catch(SQLException e){}
		}
		
		return null;
	}

	//리스트 등록하기
	public void marketInsert(marketBean mb) {
		// TODO Auto-generated method stub
		int num =0;
		String sql = "";
		
		int result = 0;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement("SELECT MAX(MARKET_NUM) FROM MARKET");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				num = rs.getInt(1)+1;
			}else{
				num =1;
			}
			
			sql ="INSERT INTO MARKET VALUES(?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2,mb.getMARKET_ID());
			pstmt.setString(3, mb.getMARKET_SUBJECT());
			pstmt.setString(4, mb.getMARKET_CONTENT());
			pstmt.setString(5, mb.getMARKET_MAIN_IMAGE());
			pstmt.setString(6, mb.getMARKET_IMAGE());
			pstmt.setInt(7,mb.getMARKET_PRICE());
			pstmt.setString(8, mb.getMARKET_WAY());
			pstmt.setInt(9,0);
			
			//타임구하기
			Calendar calendar = Calendar.getInstance();
            java.util.Date date = calendar.getTime();
            String today = (new SimpleDateFormat("yyyy년MM월dd일HH시mm분").format(date));
			System.out.println(today);
			pstmt.setString(10, today);
			
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println(e);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		}
		
	}

	public void marketsell(int page) {
		// TODO Auto-generated method stub
		String sql = "UPDATE MARKET SET MARKET_CHECK=1 WHERE MARKET_NUM=?";
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		}
		
	}

	public void marketrestart(int page) {
		// TODO Auto-generated method stub
		String sql = "UPDATE MARKET SET MARKET_CHECK=0 WHERE MARKET_NUM=?";
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		}
		
	}
	
	
	
	
}
