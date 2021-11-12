package view;

import java.util.Scanner;

import dao.UserDAO;

public class LoginView {
	public LoginView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();
		System.out.print("아이디 : ");
		String userid = sc.next();
		System.out.print("비밀번호 : ");
		String userpw = sc.next();
		if(udao.login(userid,userpw)) {
			System.out.println(userid+"님 어서오세요~");
			//메인화면
		}else {
			System.out.println("로그인 실패 / 다시 시도해 주세요.");
		}
		
	}
}






