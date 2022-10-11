package c_info2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoView2t {

	// 1. 멤버 변수 선언
	JFrame f;
	JTextField tfName, tfID, tfTel, tfGender, tfAge, tfHome;
	JTextArea ta;
	JButton bAdd, bShow, bSearch, bDelete, bCancel, bExit;

	// 비지니스 로직 - 모델단
	InfoModel model;

	// 2. 멤버 변수 객체 생성
	InfoView2t() {
		f = new JFrame("DB Test");

		tfName = new JTextField();
		tfID = new JTextField();
		tfTel = new JTextField();
		tfGender = new JTextField();
		tfAge = new JTextField();
		tfHome = new JTextField();

		ta = new JTextArea(40, 30);

		bAdd = new JButton("Add");
		bShow = new JButton("Show");
		bSearch = new JButton("Search");
		bDelete = new JButton("Delete");
		bCancel = new JButton("Cancel");
		bExit = new JButton("수정하기");


		// 모델 객체 생성
		try {
			model = new InfoModelImp1();
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
		JPanel pWest = new JPanel(new GridLayout(6,2));
		pWest.setPreferredSize(new Dimension(250,1));
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
		f.add(ta , BorderLayout.CENTER);
		JPanel pSouth = new JPanel(new GridLayout(1,6));
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
	}

	void eventProc() {
		// Add 버튼이 눌렸을 때
		bAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertData();
			} // end of actionPerformed
		}); // end of addActionListener

		// Show 버튼이 눌렸을 때
		bShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAll();
			} // end of actionPerformed
		}); // end of addActionListener

		// Search 버튼이 눌렸을 때
		bSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectByTel();
			} // end of actionPerformed
		}); // end of addActionListener

		// 전화번호 텍스트 필드에서 엔터 쳤을 때
		tfTel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectByTel();
			} // end of actionPerformed
		}); // end of addActionListener

		// 삭제 버튼
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteByTel();
			} // end of actionPerformed
		}); // end of addActionListener
		
		// 삭제 버튼
		bCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearText();
				ta.setText(null);
			} // end of actionPerformed
		}); // end of addActionListener
		
		// 삭제 버튼
		bExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateByTel();
			} // end of actionPerformed
		}); // end of addActionListener

	} // end of eventProc
	
	
	void updateByTel() {
		// (1) 사용자 입력값 얻어오기
		String name = tfName.getText();
		String id = tfID.getText();
		String tel = tfTel.getText();
		String gender = tfGender.getText();
		int age = Integer.parseInt(tfAge.getText()); 
		String home = tfHome.getText();

		// (2) 1번의 값들을 InfoVO에 지정
		InfoVO vo = new InfoVO();
		vo.setName(name);
		vo.setId(id);
		vo.setTel(tel);
		vo.setGender(gender);
		vo.setAge(age);
		vo.setHome(home);
		
		// (2) 모델단에 deleteByTel() 호출
		try {
			int i = model.updateByTel(vo);
			if (i>0) {
				ta.setText(i + " 개의 행이 성공적으로 수정되었습니다.");
			} else {
				ta.setText("해당 전화번호가 없어 실패했습니다.");
			}
		} catch (SQLException e) {
			ta.setText("수정 실패 : " + e.getMessage());
		}

		// 화면을 지우기
		clearText();
		
	}
	
	void deleteByTel() {
		// (1) 입력한 전화번호 값을 얻어오기
		String tel = tfTel.getText();
		
		// (2) 모델단에 deleteByTel() 호출
		try {
			int i = model.delete(tel);
			if (i>0) {
				ta.setText(i + " 개의 행이 성공적으로 삭제되었습니다.");
			} else {
				ta.setText("해당 전화번호가 없습니다.");
			}
		} catch (SQLException e) {
			ta.setText("삭제 실패 : " + e.getMessage());
		}
		
		// 화면을 지우기
		clearText();
		
	}

	void selectByTel() {
		try {
			// (1) 입력한 전화번호 값을 얻어오기
			String tel = tfTel.getText();

			// (2) 모델단에 selectByTel() 호출
			InfoVO vo = model.selectByTel(tel);

			// (3) 받은 결과를 각각의 텍스트 필드에 지정(출력)
			tfName.setText(vo.getName());
			tfID.setText(vo.getId());
			tfTel.setText(vo.getTel());
			tfGender.setText(vo.getGender());
			tfAge.setText(String.valueOf(vo.getAge()));
			tfHome.setText(vo.getHome());

		} catch (Exception ex) {
			ta.setText("전화번호 검색 실패 : " + ex.getMessage());
		}
	}

	void selectAll() {
		try {
			ArrayList<InfoVO> data = model.selectAll();
			ta.setText(" ---- 검색 결과 ---- \n");
			for (InfoVO vo : data) {
				ta.append(vo.toString());
			}
		} catch (SQLException e) {
			ta.setText("검색 실패 : " + e.getMessage());
		}
	}

	void insertData() {
		// (1) 사용자 입력값 얻어오기
		String name = tfName.getText();
		String id = tfID.getText();
		String tel = tfTel.getText();
		String gender = tfGender.getText();
		int age = Integer.parseInt(tfAge.getText()); 
		String home = tfHome.getText();

		// (2) 1번의 값들을 InfoVO에 지정
		InfoVO vo = new InfoVO();
		// InfoVO vo = new InfoVO(name, id, tel, gender, age, home);
		vo.setName(name);
		vo.setId(id);
		vo.setTel(tel);
		vo.setGender(gender);
		vo.setAge(age);
		vo.setHome(home);

		// (3) 모델의 insertData() 호출
		try {
			int i = model.insertInfo(vo);
			if (i>0) {
				ta.setText(i + " 개의 행이 성공적으로 삽입되었습니다.");
			} else {
				ta.setText("삽입 실패했습니다.");
			}
		} catch (SQLException e) {
			ta.setText("삽입 실패 : " + e.getMessage());
		}

		// (4) 화면의 입력값들을 지우기
		clearText();
	}

	void clearText() {
		tfName.setText(null);
		tfID.setText(null);
		tfTel.setText(null);
		tfGender.setText(null);
		tfAge.setText(null);
		tfHome.setText(null);
	}

	public static void main(String[] args) {

		InfoView2t info = new InfoView2t();
		info.addLayout();
		info.eventProc();


	}

}
