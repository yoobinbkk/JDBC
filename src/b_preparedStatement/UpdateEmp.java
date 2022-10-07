package b_preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateEmp {

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
			// 입력값
			int sabun = 7698;
			String saname = "아무개";	
			int salary = 15000;
			
			// 7698 사원 이름과 월급을 변경하세요
			String sql = "Update emp Set ename = ?, sal = ? where empno = ?";

			// 4. SQL 전송 객체
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, saname);
			stmt.setInt(2, salary);
			stmt.setInt(3, sabun);

			// 5. SQL 전송
			/*
			- ResultSet executeQuery()	:	SELECT
			- int executeUpdate()		:	INSERT/DELETE/UPDATE
			 */
			int result = stmt.executeUpdate();	// ******************** 이미 sql 연결함
			System.out.println(result + "행을 실행");

			// 6. 닫기
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println("DB 실패 : " + e);
		}
		
	}

}
