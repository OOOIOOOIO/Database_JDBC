package view;

import java.util.Scanner;

import dao.UserDAO;
import dto.UserDTO;

public class JoinView {
	public JoinView() {
		Scanner sc = new Scanner(System.in);
		//새로 가입되는 "회원의 정보들"을 데이터베이스에 저장해야 하므로
		//데이터베이스에 접근하기 위한(기능을 구현해 놓은) DAO가 필요하고
		//회원정보에 접근해야 하기 때문에 UserDAO 객체가 필요하다.
		UserDAO udao = new UserDAO();
		System.out.print("아이디 : ");
		String userid = sc.next();
		String userpw="";
		if(udao.checkId(userid)) {
			while(true) {
				System.out.print("비밀번호 : ");
				userpw = sc.next();
				System.out.print("비밀번호 확인 : ");
				String userpw_re = sc.next();
				//비밀번호 검사는 따로 데이터베이스를 거치지 않기 때문에 뷰단에서 해결 가능
				if(userpw.equals(userpw_re)) {
					break;
				}else {
					System.out.println("비밀번호를 다시 확인해주세요.");
				}
			}
			
			System.out.print("이름 : ");
			String username = sc.next();
			System.out.print("나이 : ");
			int userage = sc.nextInt();
			System.out.print("핸드폰 번호 : ");
			String userphone = sc.next();
			System.out.print("주소 : ");
			sc = new Scanner(System.in);
			String useraddr = sc.nextLine();
			//여기까지 왔다면 회원 가입에 필요한 모든 정보를 입력받았다는 뜻이다.
			//그 모든 정보들을 UserTable.txt에 저장해야 하고 저장하는 코드들은
			//DAO에 구현되어 있다. DAO에 있는 어떤 메소드에 그 데이터들을 넘기기 위해서는
			//매개변수로 넘겨야 하고, 많은 데이터를 받기위해 매개변수도 많이 필요해진다.
			//그 데이터들을 따로 보내지 않고 하나의 객체(UserDTO)로 포장해서 넘겨주면
			//더욱 편리하게 쓸 수 있다.
			UserDTO newUser = new UserDTO(userid, userpw, username, userage, userphone, useraddr);
			if(udao.join(newUser)) {
				System.out.println("회원가입 성공!");
				System.out.println(username+"님 가입을 환영합니다~");
			}else {
				System.out.println("회원가입 실패 / 다시 시도해 주세요.");
			}
			
		}else {
			System.out.println("중복된 아이디가 있습니다.");
		}
	}
}








