# Database_JDBC
Study SQL and connect JAVA-SQL(Oracle) using JDBC
-------------------------------------------------
### SQL files
#### 기본 SQL 문법
---------------------------
> TABLE    
> DML    
> GROUP BY     
> JOIN    
> JDBC_TABLE    
-------------------------------------------
### JAVA
#### JDBC API를 이용해 Oracle Database와 연결하기
>JDBC    
----------------------------------
#### JDBC( Java DataBase Connectivity)

 Java와 DataBase를 연결하는 자바 API
 Java에서 DataBase를 컨트롤 할 수 있는 방법들을 제공하는 API

 프로젝트 buildpath 에서 ojdbc6.jar을 추가해야 한다.

 Connection : Java와 DataBase를 잇는 다리 역할

 PreparedStatement : Query문을 전달, 다리 위를 왔다갔다 할 택배차 역할

 ResultSet :  SELECT문의 결과를 가지고 올 타입

연결할 데이터베이스 URL(목적지) 형식 Stirng url = "jdbc:oracle:thin:@HOST:PORT:SID";

< 순서 >

 Connection conn, PreparedStatement ps, ResultSet rs을 선언
 URL, userID, userPw를 변수에 담는다. URL은 연결할 데이터베이스이다. --> try문 안에 들어가도 된다.
 try-catch로
 
try
	Class.forName(불러올 JDBC드라이버) --> DB가 제공하는 드라이버클래스를 로드
	conn = DriverManager.getConnection(url, userID, userPw)
	연결 성공 출력
catch
	ClassNotFoundException e --> 드라이버에 연결을 실패할 경우
	연결 실패 출력
cathch
	SQLException sqle --> DB 계정이 불일치할 경우 
	계정 불일치 출력 

묶어준다.


 JAVA에서 DB에 연결을 하였으면 이제 데이터베이스에서 테이블을 생성하여야 한다.
데이터베이스에서 테이블을 만들고 데이터를 삽입하는 작업을 거쳐야한다.(DB에서 기본으로 제공하는 
TABlE은 바로 SELECT으로 가져올 수 있다.)

 이후 JAVA에서
ps = conn.preparedStatement(SQL QUERY) 를 통해 전달 준비를 하고

rs = ps.excuteQuery() 를 통해 DB로 SQL문을 전달시킨다.
여기서 ps.excuteQuery()는 SQL문이 SELECT문일 때 사용하고 INSERT, UPDATE, DELETE는 ps.excuteUpdate()를 사용한다.
만약 SQL문이 INSERT, UPDATE, DELETE와 같을 경우 "INSERT INTO UMS_USER VALUES(?,?,?,?,?,?)" ? 식으로 되어 있을 텐데
? 는 ps.setString(순서, 넣을값),Int(),..으로 넣어준 후 ps.excuteUpdate()를 실행한다.
SELECT문도 만약 ?가 있을 경우 ps.setString(),Int,...로 넣어준 후 ps.excuteQuery()를 실행한다.

이후 rs.next로 데이터가 옳바르게 검색되었는지 확인을 한다.
rs.next는 현재 컬럼명을 가르키고 있고 rs.next를 실행하면 다음 행이 있는지 boolean값으로 리턴을 한 후 만약 값이 있다면 다음 행으로 옮겨간다.

rs.getInt(컬렴순서 혹은 컬럼명), String(),.. 등으로 rs.next가 가르키고 있는 데이터를 추출한다.
