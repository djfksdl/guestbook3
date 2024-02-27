package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {//DB관련
	
	//필드-없음
	//생성자
	//메소드-gs
	//메소드-일반
	
	//전체가져오기
	public List<GuestVo> guestSelect() {
		List<GuestVo> guestList = new ArrayList<GuestVo>();
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

		// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/guestbook_db";
			conn = DriverManager.getConnection(url, "guestbook", "guestbook");
			
		// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query="";
			query +=" select no ";
			query +=" ,name ";
			query +=" ,password ";
			query +=" ,content ";
			query +=" ,reg_date ";
			query +=" from guestbook ";

			//바인딩
			pstmt = conn.prepareStatement(query);
			
			//실행
			rs = pstmt.executeQuery(); //select문 빼고 나머지는 executeUpdate()씀!
			
		// 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String pw =rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");
				
				//db에서 가져온 데이터 vo로 묶기
				GuestVo guesetVo = new GuestVo(no, name, pw, content, reg_date);
				guestList.add(guesetVo);
				
			}
			
			
		} catch (ClassNotFoundException e) {
		System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
		try {
		if (rs != null) {
		rs.close();
		} 
		if (pstmt != null) {
		pstmt.close();
		}
		if (conn != null) {
		conn.close();
		}
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		}
		return guestList;
		
	}
	
	//등록
	public int guestInsert(GuestVo guestVo) {
		int count = -1;
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

		// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/guestbook_db";
			conn = DriverManager.getConnection(url, "guestbook", "guestbook");
			
		// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query="";
			query +=" insert into guestbook  ";
			query +=" values(null, ?,?,?,now()) ";
			

			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,guestVo.getName());
			pstmt.setString(2, guestVo.getPw());
			pstmt.setString(3, guestVo.getContent());
			
			//실행
			count = pstmt.executeUpdate(); //select문 빼고 나머지는 executeUpdate()씀!
			
		// 4.결과처리
			System.out.println("1건 등록되었습니다.");
			
			
		} catch (ClassNotFoundException e) {
		System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
		try {
		if (rs != null) {
		rs.close();
		} 
		if (pstmt != null) {
		pstmt.close();
		}
		if (conn != null) {
		conn.close();
		}
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		}
		return count;
	}
	//비밀번호 삭제-처음에 정보 가져오고,다음에 삭제하는 2개 메소드 쓰는 방법1)
	public GuestVo guestSelectOne(GuestVo guestVo) {
		GuestVo gVo = null;
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
	
		// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/guestbook_db";
			conn = DriverManager.getConnection(url, "guestbook", "guestbook");
			
		// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			//비밀번호 일치 여부 확인
			String query="";
			query +=" select name ";
			query +=" 		,no";
			query +=" 		,password ";
			query +=" from guestbook ";
			query +=" where no=? ";
			query +=" and password=? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestVo.getNo() ) ;
			pstmt.setString(2, guestVo.getPw() ) ;
	
			
			//실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String pw = rs.getString("password");
				
				gVo = new GuestVo(no, pw); 
			}
			
		// 4.결과처리
			
		} catch (ClassNotFoundException e) {
		System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
		try {
		if (rs != null) {
		rs.close();
		} 
		if (pstmt != null) {
		pstmt.close();
		}
		if (conn != null) {
		conn.close();
		}
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		}
		
		return gVo;
	}
	
	//비밀번호 삭제-처음에 정보 가져오고,다음에 삭제하는 2개 메소드 쓰는 방법1)
	public int guestDelete(GuestVo gVo) {
		int count = -1;
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

		// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/guestbook_db";
			conn = DriverManager.getConnection(url, "guestbook", "guestbook");
			
		// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query="";
			query +=" delete from guestbook ";
			query +=" where no=? ";
			query +=" and password=? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gVo.getNo() ) ;
			pstmt.setString(2, gVo.getPw() ) ;

			
			//실행
			count = pstmt.executeUpdate(); //select문 빼고 나머지는 executeUpdate()씀!
			System.out.println("1건 삭제되었습니다.");
			
			
		// 4.결과처리
			
		} catch (ClassNotFoundException e) {
		System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
		try {
		if (rs != null) {
		rs.close();
		} 
		if (pstmt != null) {
		pstmt.close();
		}
		if (conn != null) {
		conn.close();
		}
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		}
		
		return count;
	}
	//clean삭제-쿼리문에서 조건 설정해줘서 한번에 삭제
	public int cleanDelete(GuestVo guestVo) {
		int count = -1;
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

		// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/guestbook_db";
			conn = DriverManager.getConnection(url, "guestbook", "guestbook");
			
		// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query="";
			query +=" delete from guestbook ";
			query +=" where no=? ";
			query +=" and password=? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestVo.getNo() ) ;
			pstmt.setString(2, guestVo.getPw() ) ;

			
			//실행
			count = pstmt.executeUpdate(); //select문 빼고 나머지는 executeUpdate()씀!
			System.out.println("1건 삭제되었습니다.");
			
			
		// 4.결과처리
			
		} catch (ClassNotFoundException e) {
		System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
		try {
		if (rs != null) {
		rs.close();
		} 
		if (pstmt != null) {
		pstmt.close();
		}
		if (conn != null) {
		conn.close();
		}
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		}
		
		return count;
	}
		
}//Dao끝
