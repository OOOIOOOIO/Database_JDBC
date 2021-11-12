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
	// UserDAO Ŭ������ ��ü�� DB�� �����ϱ� ���� �����ؼ� ����Ѵ�.
	public UserDAO() {
		// �����Ǿ��ٸ� ������ �ٸ� �޾Ƽ� conn�� �־��ֱ�
		conn = DBConnection.getConnection();
	}
	public boolean join(UserDTO newUser) {
		// ��ü�� ��� ��й�ȣ ��ȣȭ
		newUser.userpw = encrypt(newUser.userpw);
		int result = 0;
		// ������ ���ο� �����ϴ� ������ �ֱ� ������ �װ� ���ڿ� ������ ���� �ۼ��ϰ� �Ǹ� �ʹ� �ڵ尡 ����������.
		// ���� ������ �� �ڸ��� "?" �� �̿��� sql���� �̿ϼ����� ���д�.
		// "?" �� ��(���ڿ�, ����)�� �����ϴ� ��. �÷����� �� ���� ���Ѵ�.
		// ���� Java���� �÷���(sql���� �ø��� ���ڿ�("")�� �ƴ�) ���� �� sql���� �־ �Ѱ�����Ѵ�.
		String sql = "INSERT INTO UMS_USER VALUES(?,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			
			//�������� sql���� �ϼ����� ���� �������̹Ƿ� ? �ڸ��� �� ������
			//�ϳ��� ������ �־�� �Ѵ�.
			ps.setString(1, newUser.userid);//ù��° ����ǥ�� 'apple' ����
			ps.setString(2, newUser.userpw);
			ps.setString(3, newUser.username);
			ps.setInt(4, newUser.userage);
			ps.setString(5, newUser.userphone);
			ps.setString(6, newUser.useraddr);
			
			//INSERT, UPDATE, DELETE : executeUpdate() - ������ �� �� ����
			//SELECT : executeQuery() - �˻��� ��� ���̺�(rs)�� ����
			result = ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("����" + sqle);
		}
		// ������ �����ߴٸ� 1���� �߰�(���Ը��� ����)�Ǿ����Ƿ� 1���� result�� ����ִٸ�
		// ���Կ� �����ߴٴ� ���̴�.
		return result == 1;
	}

	public boolean checkId(String userid) {
		String sql = "SELECT COUNT(*) FROM UMS_USER WHERE USERID=?";
//		String sql = "SELECT * FROM UMS_USER WHERE USERID=?"; 
		// result�� 0�̶�� �ߺ��� ���� 1�̸� �ߺ��̶�� �� -->�� �³� ȸ�������� �� �� ���̵� �̹� ������ 1 ��ȯ 
		int result = 1;
//		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			rs = ps.executeQuery();
			
			// .next() : ���� ���� �ִ��� Ȯ�� return type --> boolean
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
//			result = !rs.next(); // rs.next : ���� ���� ���� -->false / ���� ���� �ִ�(�ߺ�) --> true
		} catch (SQLException sqle) {
			System.out.println("����" + sqle);
		}
		return result == 0; // �ߺ��� ���ٸ� 0 true
//		return result
	}
	
	public boolean login(String userid, String userpw) {
		String sql = "SELECT * FROM UMS_USER WHERE USERID=? AND USERPW=?";
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, userid);
			// DB�� ��ȣȭ�� ��й�ȣ�� ��������Ƿ� �˻��� ������ ��ȣȭ�ؼ� �˻�
			ps.setString(2, encrypt(userpw));
			
			rs = ps.executeQuery();
			
			// .next()�� �ϸ� ���� ������ �Ѿ��. �� ó���� Į���� ture�� ������ 1������ �Ѿ�� 2���� �˻��Ѵ�.
			if(rs.next()) {
				// rs.next()�� �ִٴ� ���� �˻� ����(�α��� ����)�̶�� ���̰�
				// �˻��� ����� ���ǿ� ������ ���־�� �Ѵ�.
				// �˻��� �࿡�� �� ���� ������ �����ͼ� ��ü�� ���� �� ���� 
				UserDTO loginUser = new UserDTO(rs.getString("USERID"),
						decrypt(rs.getString("USERPW")),
						rs.getString("USERNAME"),
						rs.getInt("USERAGE"),
						rs.getString("USERPHONE"),
						rs.getString("USERADDR")
						);
				//������ ������� ��ü�� ���ǿ� ����
				Session.put("loginUser", loginUser);
				return true;
			}
		} catch (SQLException sqle) {
			System.out.println("����" + sqle);
		}
		// ���� return���� true�� �ƴ϶�� ������ �α��� ���� 
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









