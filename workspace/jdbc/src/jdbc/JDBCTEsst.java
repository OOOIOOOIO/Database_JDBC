package jdbc;
// 프로젝트 Build Path에서 ojdbc6.jar을 추가해야한다.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTEsst {
	public static void main(String[] args) {
		// 다리
		Connection conn = null;
		// 택배차
		PreparedStatement ps = null;
		// SELECT문으로 받은 테이블 결과(창고같은 느낌)
		ResultSet rs = null;
		
		
		//연결할 데이터베이스 URL(목적지) "jdbc:oracle:thin:@HOST:PORT:SID";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "web";
		String password = "web";
		
		// 설계도 준비 Class.forName(불러올 JDBC드라이버 이름)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 다리 건설
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 준비 실패!"); // 드라이버에 연결에 실패할 때
		} catch(SQLException sqle) {
			System.out.println("연결 실패!" + sqle); // DB 계정이 불일치 할 때
		}
		
		// 연결이 정상정으로 성공했을 때
		if(conn != null) {
			String sql = "SELECT SYSDATE FROM DUAL";
			
			try {
				// 택배차 생성(만들어진 conn(다리)를 이용하는 택배차(ps)
				ps = conn.prepareStatement(sql);
				
				// 전달해준 sql문 수행시키기
				rs = ps.executeQuery();
				
				// next() : 다음 행이 있는지 있으면 T. return type -> boolean
				// get~~ --> return type에 따라 달라짐 
				// getSting(컬럼번호) : 컬럼 출력
				// getString("컬럼명") : 컬럼 전체 출력(while문과 같이 쓰임)
				// getInt("컬럼명") : 
				if(rs.next()) {
					System.out.println(rs.getString(1));
				} 
				else {
					System.out.println("검색결과 없음");
				}
				
			} catch (SQLException sqle) {
				System.out.println("실패 !" + sqle);
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
