package c_info3;

import java.awt.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InfoView2 {

   // 1. 멤버 변수 선언

   JFrame f;
   JTextField tfName, tfID, tfTel, tfGender, tfAge, tfHome;
   JTextArea ta;
   JButton bAdd, bShow, bSearch, bDelete, bCancel, bExit;

   // 비지니스 로직
   InfoModel2 model; //인포모델은 타입 인터페이스 -- 다형성

   // 2. 멤버 변수 객체 생성

   InfoView2() {

      f = new JFrame("DB Test");

      tfName = new JTextField();
      tfID = new JTextField();
      tfTel = new JTextField();
      tfGender = new JTextField();
      tfAge = new JTextField();
      tfHome = new JTextField();

      
      
      ta = new JTextArea(40, 30);

      bAdd = new JButton("Add", new ImageIcon("src\\b_info\\imgs\\book.PNG"));
      bShow = new JButton("Show", new ImageIcon("src\\b_info\\imgs\\book.PNG"));
      bSearch = new JButton("Search", new ImageIcon("src\\b_info\\imgs\\book.PNG"));
      bDelete = new JButton("Delete", new ImageIcon("src\\b_info\\imgs\\book.PNG"));
      bCancel = new JButton("Cancel", new ImageIcon("src\\b_info\\imgs\\book.PNG"));
      bExit = new JButton("Exit (alt+x)", new ImageIcon("src\\b_info\\imgs\\iexit.PNG"));
      bAdd.setVerticalTextPosition(JButton.BOTTOM); // Text를 vertical에서 어떻게 배치?
      bAdd.setHorizontalTextPosition(JButton.CENTER); // Text를 Horizontal에서 어떻게 배치?
      bAdd.setRolloverIcon(new ImageIcon("src\\b_info\\imgs\\lightbulb.PNG"));
      bAdd.setPressedIcon(new ImageIcon("src\\b_info\\imgs\\brain.PNG"));
      bExit.setVerticalTextPosition(JButton.BOTTOM);
      bExit.setHorizontalTextPosition(JButton.CENTER);
      bExit.setRolloverIcon(new ImageIcon("src\\b_info\\imgs\\lightbulb.PNG"));
      bExit.setPressedIcon(new ImageIcon("src\\b_info\\imgs\\brain.PNG"));
      
      try {
         model = new InfoModelImp2(); //InfoModelImp1 <-자식클래스와 연결해주는 -- 트라이와 캐치를 꼭 씌워줘야됨 빨간줄이 쳐있으면 이렇게 트라이로 채워줘야함
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }

   }

   // 3. 화면 구성하고 출력
   // 전체 프레임 BorderLayout 지정
   // - West : JPanel 붙이기 (GridLayout(6,2))
   // - Center : TextArea
   // - South : JPanel 붙이기 (GridLayout(1,6))

   void addLayout() {

      f.setLayout(new BorderLayout());

      // 붙이기 작업
      JPanel pWest = new JPanel(new GridLayout(6, 2));
      pWest.setPreferredSize(new Dimension(250, 1));
      JLabel Name = new JLabel("Name");
      Name.setHorizontalAlignment(JLabel.CENTER);
      JLabel ID = new JLabel("ID");
      ID.setHorizontalAlignment(JLabel.CENTER);
      JLabel Tel = new JLabel("Tel");
      Tel.setHorizontalAlignment(JLabel.CENTER);
      JLabel Sex = new JLabel("Sex");
      Sex.setHorizontalAlignment(JLabel.CENTER);
      JLabel Age = new JLabel("Age");
      Age.setHorizontalAlignment(JLabel.CENTER);
      JLabel Home = new JLabel("Home");
      Home.setHorizontalAlignment(JLabel.CENTER);
      pWest.add(Name);
      pWest.add(tfName);
      pWest.add(ID);
      pWest.add(tfID);
      pWest.add(Tel);
      pWest.add(tfTel);
      pWest.add(Sex);
      pWest.add(tfGender);
      pWest.add(Age);
      pWest.add(tfAge);
      pWest.add(Home);
      pWest.add(tfHome);

      f.add(pWest, BorderLayout.WEST);
      f.add(ta, BorderLayout.CENTER);

      JPanel pSouth = new JPanel(new GridLayout(1, 6));
      pSouth.add(bAdd);
      pSouth.add(bShow);
      pSouth.add(bSearch);
      pSouth.add(bDelete);
      pSouth.add(bCancel);
      pSouth.add(bExit);
      f.add(pSouth, BorderLayout.SOUTH);

      // 화면 출력
      f.setBounds(600, 300, 820, 400);
      f.setVisible(true);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   }// Addlayout

   void eventproc() {
      // Add 버튼
      bAdd.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            insertData();
         }
      });
      
      bShow.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            selectAll();
         }
      });
      bExit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            selectAll();
         }
      });      
      bDelete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            selectAll();      
         }
      });
      
       
   }// end of event proc()

   

   void selectAll() {
      try {
         ArrayList<InfoVO2> data = model.selectAll();
         ta.setText(" ---- 검색결과 -----/n/n");
         for (InfoVO2 vo : data) {
            ta.append(vo.toString());
         }
      } catch (SQLException e) {
         ta.setText("검색실패: " + e.getMessage());
      }

   }

   void insertData() {
      // (1) 사용자입력값 얻어오기
      String name = tfName.getText(); // 필드에서 텍스트를 가져온다는뜻 텍스트필드에 있는 내용을 가져오는거다
      String id = tfID.getText();
      String tel = tfTel.getText();
      String gender = tfGender.getText();
      int age = Integer.parseInt(tfAge.getText());
      String home = tfHome.getText();

      // (2) 1번의 값들을 InfoVO에 지정
      InfoVO2 vo = new InfoVO2();
      vo.setName(name);
      vo.setId(id);
      vo.setTel(tel);
      vo.setGender(gender);
      vo.setAge(age);
      vo.setHome(home);

      // (3) 모델의 insertInfo() 호출
      try {
         model.insertRow(vo);
         ta.setText("성공!!!");
      } catch (SQLException e) {
         ta.setText("삽입 실패 : " + e.getMessage());
      }
      clearText();

   }


      void deleteByTel() {
         // (1) 입력한 전화번호 값을 얻어오기
         String tel = tfTel.getText();
         
         // (2) 모델단에 deleteByTel() 호출
         try {
            int i = model.delete(tel);
            if (i==1) {
               ta.setText(i + " 개의 행이 성공적으로 삭제되었습니다.");
            }    else {
               ta.setText("해당 전화번호는 없습니다.");
            }
         }catch (Exception e) {
            
            System.out.println(("오류 발생"+ e));}
   
             
         
      }
      
   
   // (4) 화면의 입력값들을 지우기
   void clearText() {
      tfName.setText(null);
      tfID.setText(null);
      tfTel.setText(null);
      tfGender.setText(null);
      tfAge.setText(null);
      tfHome.setText(null);

   }

   public static void main(String[] args) {

      InfoView2 info = new InfoView2();
      info.addLayout();
      info.eventproc();
   }
}
