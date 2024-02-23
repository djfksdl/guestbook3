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
//		System.out.println("GuestbookController.goGet()");//실행확인
		
		String action = request.getParameter("action");
//		System.out.println(action);
		
		if("alform".equals(action)) { //메인화면
			System.out.println("alform:메인화면");
			
			//DB관련
			GuestDao guestDao = new GuestDao();
			
			//리스트 불러오기
			List<GuestVo> guestList = guestDao.guestSelect();
			
			//데이터 담기(리스트 주소를 request에 담기)
			request.setAttribute("guestList", guestList);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/addList.jsp");
			
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
			
			
		}else if("dform".equals(action)) {
			System.out.println("dform:삭제폼");
			
			//request에서 파라미터 받기
			//int no = Integer.parseInt(request.getParameter("no"));
			
			

//			//리다이렉트
			WebUtil.forward(request, response, "/WEB-INF/deleteForm.jsp");
		}else if("delete".equals(action) ) {
			//받은 값을 저장
			int no = Integer.parseInt(request.getParameter("no"));
//			System.out.println(no);
			String pw = request.getParameter("pw");
			
			//Vo로 묶기
			GuestVo guestVo = new GuestVo(no,pw);
			//System.out.println(guestVo);
			
			//DB관련
			GuestDao guestDao = new GuestDao();
			
			//비밀번호 확인 방법1: select로 불러오고 일치하면 delete메소드 쓰기
			//Dao메소드쓰기 + 가져오기
//			GuestVo gVo =guestDao.guestSelectOne(guestVo);//no pw
//			System.out.println("확인완료");
//			if(gVo != null) {//비밀번호 일치=> 삭제 메소드+리다이렉트
//				//일치하면 지우고, 아니면 안지우는거하기(못지움)
//				guestDao.guestDelete(gVo);
//				WebUtil.redirect(request, response, "/guestbook3/gbc?action=alform");
//				System.out.println("비밀번호 일치후 삭제");
//				
//			}else {
//
//				WebUtil.redirect(request, response, "/guestbook3/gbc?action=alform");
//				System.out.println("비밀번호 일치하지않음");
//			}
			
			//비밀번호 확인 방법2: delete문에where로 조건 주기
			//Dao메소드 쓰기
			guestDao.cleanDelete(guestVo);
			WebUtil.redirect(request, response, "/guestbook3/gbc?action=alform");
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
