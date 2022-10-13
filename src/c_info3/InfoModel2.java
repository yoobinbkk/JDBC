package c_info3;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InfoModel2 {

   // 사용자 입력값을 받아서 DB에 저장하는 역할
   void insertRow(InfoVO2 vo) throws SQLException;   //insertrow 

   // 레코드 전체 검색
   
   
   
   ArrayList<InfoVO2> selectAll() throws SQLException; // end of selectAll

   /*
    * 전화번호를 넘겨서 해당하는 사람의 정보를 검색
    */
   
   InfoVO2 selectByTel(String tel) throws SQLException;
   
   /*
    * 전화번호를 넘겨받아 해당하는 사람의 정보를 삭제
    */  
   int delete(String tel) throws SQLException; //틀을 잡아넣어야함.
   
   
   
}