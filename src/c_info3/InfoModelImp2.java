package c_info3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoModelImp2 implements InfoModel2 {

   final static String DRIVER    = "oracle.jdbc.driver.OracleDriver";
   final static String URL    = "jdbc:oracle:thin:@192.168.88.1:1521:xe";
   final static String USER    = "scott";
   final static String PASS    = "tiger";

      public InfoModelImp2() throws ClassNotFoundException {
         // 1. 드라이버 로딩
         Class.forName(DRIVER);
         System.out.println("드라이버 로딩 성공");
      }

   // 사용자 입력값을 받아서 DB에 저장하는 역할
   @Override
   public void insertRow(InfoVO2 vo) throws SQLException {//3
      // 2. 연결 객체 얻어오기
      Connection con = null;
      PreparedStatement ps = null;
      try {
         // 연결 
         con = DriverManager.getConnection(URL,USER,PASS);
         
         // sql 문장
         String sql = "insert into info_tab(name, jumin, tel, gender, age, home) "
         + " values(?,?,?,?,?,?)";
         
         ps = con.prepareStatement(sql);
         ps.setString(1, vo.getName());
         ps.setString(2, vo.getId());
         ps.setString(3, vo.getTel());
         ps.setString(4, vo.getGender());
         ps.setInt(5, vo.getAge());
         ps.setString(6, vo.getHome());
         
         //전송
         ps.executeUpdate();
         
         
      } finally {
         ps.close();
         con.close();

      }

   }

   // 레코드 전체 검색
   @Override
   public ArrayList<InfoVO2> selectAll() throws SQLException {
      // 2. 연결 객체 얻어오기
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         con = DriverManager.getConnection(URL, USER, PASS);

         //3.sql문장 
         String sql= "SELECT * from info_tab";

         //4.전송객체 얻어오기
         ps = con.prepareStatement(sql);

         //5.전송
         rs=ps.executeQuery();

         ArrayList<InfoVO2> list = new ArrayList<InfoVO2>();
         while(rs.next()) {
            InfoVO2 vo = new InfoVO2();
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
   public InfoVO2 selectByTel(String tel) throws SQLException {
      // 2. 연결 객체 얻어오기
      Connection con = null;
      PreparedStatement ps = null;
      InfoVO2 vo = new InfoVO2();
      try {
         con = DriverManager.getConnection(URL, USER, PASS);

         //3.sql문장 
         String sql= "SELECT * from info_tab where TEL=?";

         //4.전송객체 얻어오기
         ps = con.prepareStatement(sql);
         ps.setString(1, tel);

         //5.전송
         ResultSet rs=ps.executeQuery();

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
      return null;
   }

   @Override
   public int delete(String tel) throws SQLException {
      //   delete from info_tab where tel = ?

      Connection con = null;
      PreparedStatement ps = null;
      InfoVO2 vo = new InfoVO2();
      int result = 0;

      try {// getConnection는 
         con = DriverManager.getConnection(URL, USER, PASS);

         // 3. sql 문장 (########)
         String sql = "delete from info_tab where tel = ?";
         
         // 4. 전송 객체
         ps = con.prepareStatement(sql);
         ps.setString(1,tel); //vo에서 받음
         
         // 5. 전송
         result = ps.executeUpdate();// 행을 몇개 바꿧는지
         
      } finally {
         
         // 6. 닫기
         ps.close();
         con.close();

      }
      
      return result;
   }

}


