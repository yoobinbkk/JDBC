package a_statement;

import java.sql.*;

public class SelectEmpDept {

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

			// 3. SQL 문장 만들기 (*****)
			//		-> 20번 부서의 사원들의 정보 - 사번, 사원명, 월급, 부서명, 근무지
			String sql = "SELECT E.EMPNO EMPNO, E.ENAME ENAME, E.SAL SAL, E.DEPTNO DEPTNO, D.LOC LOC "
					  + " FROM EMP E INNER JOIN DEPT D ON E.DEPTNO = D.DEPTNO "
					  + " WHERE E.DEPTNO = 20";

			// 4. 전송 객체 얻어오기
			Statement stmt = con.createStatement();

			// 5. SQL 전송
			/*
				- ResultSet executeQuery()	:	SELECT
				- int executeUpdate()		:	INSERT/DELETE/UPDATE
			 */
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 여기에 출력
				int empno 		= 	rs.getInt("EMPNO");
				String ename 	= 	rs.getString("ENAME");
				int sal 		= 	rs.getInt("SAL");
				String deptno 	= 	rs.getString("DEPTNO");
				String loc 		= 	rs.getString("LOC");
				System.out.println("\t" + empno + "\t/\t" + ename + "\t/\t" + sal + "\t/\t" + deptno + "\t/\t" + loc);
			}

			// 6. 닫기
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println("실패 : " + e);
		}

	}

}
