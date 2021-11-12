package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserDTO;

public class UserDAO {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	// UserDAO 클래스의 객체는 DB에 접근하기 위해 생성해서 사용한다.
	public UserDAO() {
		// 생성되었다면 무조건 다리 받아서 conn에 넣어주기
		conn = DBConnection.getConnection();
	}
	public boolean join(UserDTO newUser) {
		// 객체에 담긴 비밀번호 암호화
		newUser.userpw = encrypt(newUser.userpw);
		int result = 0;
		// 쿼리문 내부에 가변하는 값들이 있기 때문에 그걸 문자열 연결을 통해 작성하게 되면 너무 코드가 복잡해진다.
		// 따라서 값들이 들어갈 자리에 "?" 를 이용해 sql문을 미완성으로 놔둔다.
		// "?" 는 값(문자열, 정수)을 전달하는 것. 컬럼명같은 건 넣지 못한다.
		// 따라서 Java에서 컬럼명(sql에선 컬림이 문자열("")이 아님) 같은 건 sql문에 넣어서 넘겨줘야한다.
		String sql = "INSERT INTO UMS_USER VALUES(?,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			
			//전달해준 sql문은 완성되지 않은 쿼리문이므로 ? 자리에 들어갈 값들을
			//하나씩 세팅해 주어야 한다.
			ps.setString(1, newUser.userid);//첫번째 물음표에 'apple' 세팅
			ps.setString(2, newUser.userpw);
			ps.setString(3, newUser.username);
			ps.setInt(4, newUser.userage);
			ps.setString(5, newUser.userphone);
			ps.setString(6, newUser.useraddr);
			
			//INSERT, UPDATE, DELETE : executeUpdate() - 수정된 행 수 리턴
			//SELECT : executeQuery() - 검색된 결과 테이블(rs)을 리턴
			result = ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("실패" + sqle);
		}
		// 가입이 성공했다면 1줄이 추가(테입르의 수정)되었으므로 1개가 result에 담겨있다면
		// 가입에 성공했다는 뜻이다.
		return result == 1;
	}

	public boolean checkId(String userid) {
		String sql = "SELECT COUNT(*) FROM UMS_USER WHERE USERID=?";
//		String sql = "SELECT * FROM UMS_USER WHERE USERID=?"; 
		// result가 0이라면 중복이 없고 1이면 중복이라는 뜻 -->아 맞네 회원가입을 할 때 아이디가 이미 있으면 1 반환 
		int result = 1;
//		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			rs = ps.executeQuery();
			
			// .next() : 다음 행이 있는지 확인 return type --> boolean
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
//			result = !rs.next(); // rs.next : 다음 값이 없다 -->false / 다음 값이 있다(중복) --> true
		} catch (SQLException sqle) {
			System.out.println("실패" + sqle);
		}
		return result == 0; // 중복이 없다면 0 true
//		return result
	}
	
	public boolean login(String userid, String userpw) {
		String sql = "SELECT * FROM UMS_USER WHERE USERID=? AND USERPW=?";
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, userid);
			// DB에 암호화된 비밀번호가 담겨있으므로 검색할 때에도 암호화해서 검색
			ps.setString(2, encrypt(userpw));
			
			rs = ps.executeQuery();
			
			// .next()를 하면 다음 행으로 넘어간다. 맨 처음엔 칼럼명 ture가 나오면 1행으로 넘어가서 2행을 검사한다.
			if(rs.next()) {
				// rs.next()가 있다는 뜻은 검색 성공(로그인 성공)이라는 뜼이고
				// 검색된 결과로 세션에 세팅을 해주어야 한다.
				// 검색된 행에서 각 열의 값들을 가져와서 객체로 포장 후 세팅 
				UserDTO loginUser = new UserDTO(rs.getString("USERID"),
						decrypt(rs.getString("USERPW")),
						rs.getString("USERNAME"),
						rs.getInt("USERAGE"),
						rs.getString("USERPHONE"),
						rs.getString("USERADDR")
						);
				//위에서 만들어진 객체로 세션에 세팅
				Session.put("loginUser", loginUser);
				return true;
			}
		} catch (SQLException sqle) {
			System.out.println("실패" + sqle);
		}
		// 위의 return값이 true가 아니라면 무조건 로그인 실패 
		return false;
	}
	
	public String encrypt(String userpw) {
		String result = "";
		for (int i = 0; i < userpw.length(); i++) {
			result += (char)(userpw.charAt(i)+4);
		}
		return result;
	}
	
	public String decrypt(String en_pw) {
		String result = "";
		for (int i = 0; i < en_pw.length(); i++) {
			result += (char)(en_pw.charAt(i)-4);
		}
		return result;
	}
}









