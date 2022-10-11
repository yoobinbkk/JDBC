package c_info2;

import java.sql.*;
import java.util.*;

public class InfoModelImp1 implements InfoModel {

	final static String DRIVER 	= "oracle.jdbc.driver.OracleDriver";
	final static String URL 	= "jdbc:oracle:thin:@192.168.0.57:1521:xe";
	final static String USER 	= "scott";
	final static String PASS 	= "tiger";

	public InfoModelImp1() throws ClassNotFoundException {
		// 1. 드라이버 로딩
		Class.forName(DRIVER);
		System.out.println("드라이버 로딩 성공");
	}

	// 사용자 입력값을 받아서 DB에 저장하는 역할
	@Override
	public int insertInfo(InfoVO vo) throws SQLException {
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 (########)
			String sql = "insert into info_tab(name, jumin, tel, gender, age, home) "
					+ " values(?,?,?,?,?,?)";

			// 4. 전송 객체
			ps = con.prepareStatement(sql);

			ps.setString(1, vo.getName());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getTel());
			ps.setString(4, vo.getGender());
			ps.setInt(5, vo.getAge());
			ps.setString(6, vo.getHome());

			// 5. 전송
			result = ps.executeUpdate();
			
		} finally {
			// 6. 닫기
			ps.close();
			con.close();

		}
		
		return result;

	}

	// 레코드 전체 검색
	@Override
	public ArrayList<InfoVO> selectAll() throws SQLException {
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 (########)
			String sql = "select * from info_tab";
			
			// 4. 전송 객체
			ps = con.prepareStatement(sql);
			
			// 5. 전송
			rs = ps.executeQuery();
			
			ArrayList<InfoVO> list = new ArrayList<InfoVO>();
			while(rs.next()) {
				InfoVO vo = new InfoVO();
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("jumin"));
				vo.setTel(rs.getString("tel"));
				vo.setGender(rs.getString("gender"));
				vo.setAge(Integer.parseInt(rs.getString("age")));
				vo.setHome(rs.getString("home"));
				
				list.add(vo);
			}
			return list;

		} finally {
			// 6. 닫기
			rs.close();			
			ps.close();
			con.close();	
		}

	} // end of selectAll

	@Override
	public InfoVO selectByTel(String tel) throws SQLException {
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		InfoVO vo = new InfoVO();
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 (########)
			String sql = "select * from info_tab where tel = ?";
			
			// 4. 전송 객체
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			
			// 5. 전송
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("jumin"));
				vo.setTel(rs.getString("tel"));
				vo.setGender(rs.getString("gender"));
				vo.setAge(Integer.parseInt(rs.getString("age")));
				vo.setHome(rs.getString("home"));
			}
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();	
		}
		
		return vo;
	} // end of selectByTel()

	// 메소드명	: delete
	// 인자		: 전화번호
	// 리턴값		: 삭제한 행수
	// 역할		: 전화번호를 넘겨받아 해당 전화번호의 레코드를 삭제
	@Override
	public int delete(String tel) throws SQLException {

		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		InfoVO vo = new InfoVO();
		int result = 0;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 (########)
			String sql = "delete from info_tab where tel = ?";

			// 4. 전송 객체
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);

			// 5. 전송
			result = ps.executeUpdate();

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return result;
	}

	@Override
	public int updateByTel(InfoVO vo) throws SQLException {
		
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 (########)
			String sql = "update info_tab "
					+ " set name = ?, jumin = ?, gender = ?, age = ?, home = ? "
					+ " where tel = ?";

			// 4. 전송 객체
			ps = con.prepareStatement(sql);
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getGender());
			ps.setInt(4, vo.getAge());
			ps.setString(5, vo.getHome());
			ps.setString(6, vo.getTel());

			// 5. 전송
			result = ps.executeUpdate();

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}

		return result;

	}
	
}


