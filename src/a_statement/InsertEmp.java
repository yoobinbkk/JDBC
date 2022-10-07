package a_statement;

import java.sql.*;

public class InsertEmp {

	public static void main(String[] args) {

		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("driver success");

			// 2. 연결 객체 얻어오기
			String url = "jdbc:oracle:thin:@192.168.0.57:1521:xe";
			String user = "scott";
			String pass = "tiger";

			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("db connect success");

			// 3. SQL 문장
			// insert 문을 적기
			String sql = "Insert into emp (empno, ename, sal) values (6378, 'Ragnar', 8000)";
			
			// 10번 부서의 사원의 월급을 10% 인상
			// String sql = "UPDATE emp SET sal=sal*1.1 WHERE deptno=10";
			
			// 월급이 10000 이상인 사원들 삭제
			// String sql = "DELETE from emp where sal>=10000";

			// 4. SQL 전송 객체
			Statement stmt = con.createStatement();

			// 5. SQL 전송
			/*
			- ResultSet executeQuery()	:	SELECT
			- int executeUpdate()		:	INSERT/DELETE/UPDATE
			 */
			int result = stmt.executeUpdate(sql);
			System.out.println(result + "행을 실행");

			// 6. 닫기
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println("DB 실패 : " + e);
		}


	}

}
