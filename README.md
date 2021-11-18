# Database_JDBC
Study SQL and connect JAVA-SQL(Oracle) using JDBC
-------------------------------------------------
### SQL files
#### 기본 SQL 문법
---------------------------
- TABLE    
- DML    
- GROUP BY     
- JOIN    
- JDBC_TABLE    
-------------------------------------------
### JAVA

#### JDBC API를 이용해 Oracle Database와 연결하기

- JDBC : 데이터베이스를 이용한 회원관리(회원가입, 로그인)프로젝트
  - dao    
  - dto   
  - view    
  - jdbc : jdbc 연습한 소스파일
----------------------------------
#### JDBC( Java DataBase Connectivity)
&nbsp;&nbsp;JDBC란 Java와 DataBase를 연결하는 자바 API. Java에서 DataBase를 컨트롤 할 수 있는 방법들을 제공하는 API.
JDBC API를 이용하기 위해선프로젝트 buildpath 에서 ojdbc6.jar을 추가해야 한다.

- Connection : Java와 DataBase를 잇는 다리 역할

- PreparedStatement : Query문을 전달, 다리 위를 왔다갔다 할 택배차 역할

- ResultSet :  SELECT문의 결과를 가지고 올 타입(저장하는 창고같은 느낌)

- 연결할 데이터베이스 URL(목적지) 형식 : Stirng url = "jdbc:oracle:thin:@HOST:PORT:SID";

- DB가 제공하는 드라이버클래스 로드하는 법 : Class.forName(불러올 JDBC드라이버명)

## < 순서 >

#### 1.
&nbsp;&nbsp;필드에     

&nbsp;&nbsp;Connection conn, PreparedStatement ps, ResultSet rs을 선언 (ex)Connection conn = null;)

&nbsp;&nbsp;URL, userID, userPw를 변수에 담는다. (--> try문 안에 들어가도 된다.)

&nbsp;&nbsp;try-catch로

try
	Class.forName(불러올 JDBC드라이버명) --> DB가 제공하는 드라이버클래스를 로드
	conn = DriverManager.getConnection(url, userID, userPw)
	연결 성공 출력
catch
	ClassNotFoundException e --> 드라이버에 연결을 실패할 경우
	연결 실패 출력
catch
	SQLException sqle --> DB 계정이 불일치할 경우 
	계정 불일치 출력 

묶어준다.

#### 2.  
&nbsp;&nbsp;JAVA에서 DB에 연결을 하였으면 이제 데이터베이스에서 테이블을 생성하여야 한다.
데이터베이스에서 테이블을 만들고 데이터를 삽입하는 작업을 거쳐야한다.(DB에서 기본으로 제공하는 
TABlE은 바로 SELECT으로 가져올 수 있다.)

&nbsp;&nbsp;JAVA에서
ps = conn.preparedStatement(SQL QUERY) 를 통해 전달 준비를 하고

rs = ps.excuteQuery() 를 통해 DB로 SQL문을 전달시킨다.
여기서 ps.excuteQuery() SQL문이 SELECT문일 때 사용하고 
INSERT, UPDATE, DELETE는 ps.excuteUpdate()를 사용한다.    

&nbsp;&nbsp;만약 SQL문이 INSERT, UPDATE, DELETE와 같을 경우 "INSERT INTO UMS_USER VALUES(?,?,?,?,?,?)" ? 식으로 되어 있을 텐데
"?" 는 ps.setString(순서, 넣을값),Int(),..으로 넣어준 후 ps.excuteUpdate()를 실행한다.
SELECT문도 만약 ?가 있을 경우 ps.setString(),Int,...로 넣어준 후 ps.excuteQuery()를 실행한다.

&nbsp;&nbsp;이후 rs.next로 데이터가 옳바르게 검색되었는지 확인을 한다.
rs.next는 현재 컬럼명을 가르키고 있고 rs.next를 실행하면 다음 행이 있는지 boolean값으로 리턴을 한 후 만약 값이 있다면 다음 행으로 옮겨간다.
rs.getInt(컬렴순서 혹은 컬럼명), String(),.. 등으로 rs.next가 가르키고 있는 데이터를 추출하고 print문을 이용해 확인한다.

&nbsp;&nbsp;이렇게 대충 JDBC API를 어떻게 이용하는지 알아보았다.
 
--------------------------------------------------------
#### JDBC 실습 코드 

	package jdbc;      
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

