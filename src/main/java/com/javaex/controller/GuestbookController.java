package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
       
	//생성자

    //메소드-일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookController.goGet()");//실행확인
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("alform".equals(action)) { //메인화면
			System.out.println("alform:메인화면");
			
			//DB관련
			GuestDao guestDao = new GuestDao();
			
			//리스트 불러오기
			List<GuestVo> guestList = guestDao.guestSelect();
			
			//데이터 담기
			request.setAttribute("guestList", guestList);
			
			//포워드
			WebUtil.forward(request, response, "/addList.jsp");
			
		}else if("insert".equals(action)) {//등록
			System.out.println("alform:등록");
			
			//값 저장하기
			String name= request.getParameter("name");
			String pw= request.getParameter("pw");
			String content= request.getParameter("content");
			String reg_date = request.getParameter("reg_date");
			
			//Vo로 묶기
			GuestVo guestVo = new GuestVo(name, pw, content, reg_date);
			
			//DB관련연결
			GuestDao guestDao = new GuestDao();
			
			//Dao메소드로 연결
			guestDao.guestInsert(guestVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/guestbook3/gbc?action=alform");
			
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
