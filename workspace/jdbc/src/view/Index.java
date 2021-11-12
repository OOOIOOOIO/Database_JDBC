package view;

import java.util.Scanner;

//첫 시작화면
public class Index {
	public static void main(String[] args) {
		System.out.println("UMS DB와 연동하기");
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1. 회원가입\n2. 로그인\n3. 나가기");
			int choice = sc.nextInt();
			
			if(choice == 1) {
				new JoinView();
			} else if(choice == 2) {
				//로그인
				new LoginView();
			} else if(choice == 3) {
				//나가기
				System.out.println("안녕히가세요~");
				break;
			}
		}
	}
}










