package jdbc;
// ������Ʈ Build Path���� ojdbc6.jar�� �߰��ؾ��Ѵ�.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTEsst {
	public static void main(String[] args) {
		// �ٸ�
		Connection conn = null;
		// �ù���
		PreparedStatement ps = null;
		// SELECT������ ���� ���̺� ���(â���� ����)
		ResultSet rs = null;
		
		
		//������ �����ͺ��̽� URL(������) "jdbc:oracle:thin:@HOST:PORT:SID";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "web";
		String password = "web";
		
		// ���赵 �غ� Class.forName(�ҷ��� JDBC����̹� �̸�)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// �ٸ� �Ǽ�
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �غ� ����!"); // ����̹��� ���ῡ ������ ��
		} catch(SQLException sqle) {
			System.out.println("���� ����!" + sqle); // DB ������ ����ġ �� ��
		}
		
		// ������ ���������� �������� ��
		if(conn != null) {
			String sql = "SELECT SYSDATE FROM DUAL";
			
			try {
				// �ù��� ����(������� conn(�ٸ�)�� �̿��ϴ� �ù���(ps)
				ps = conn.prepareStatement(sql);
				
				// �������� sql�� �����Ű��
				rs = ps.executeQuery();
				
				// next() : ���� ���� �ִ��� ������ T. return type -> boolean
				// get~~ --> return type�� ���� �޶��� 
				// getSting(�÷���ȣ) : �÷� ���
				// getString("�÷���") : �÷� ��ü ���(while���� ���� ����)
				// getInt("�÷���") : 
				if(rs.next()) {
					System.out.println(rs.getString(1));
				} 
				else {
					System.out.println("�˻���� ����");
				}
				
			} catch (SQLException sqle) {
				System.out.println("���� !" + sqle);
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
