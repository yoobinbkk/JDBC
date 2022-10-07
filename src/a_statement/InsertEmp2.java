package a_statement;

import java.sql.*;

public class InsertEmp2 {

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

			// ----- 입력값
			String realName = "본명";
			int monSal = 10000;
			String jjob = "IT";
			
			// 3. SQL 문장
			String sql = "Insert Into emp(empno, ename, sal, job) "
					+ " Values (sq_temp2.nextval, '" + realName + "', " + monSal + ", '" + jjob + "')";
			System.out.println(sql);

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
