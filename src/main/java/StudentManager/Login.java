package StudentManager;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;



public class Login { // 로그인 클래스 
	// 전역변수 선언
	private JFrame jFrame;
	private JPanel[] jPanels;
	private JLabel jLabel;
	private JRadioButton jRadioButton1, jRadioButton2;
	private ButtonGroup buttonGroup;
	private JTextField jTextField;
	private JButton jButton;
	private Thread thread, thread2;
	
	
	public Login() { // 로그인 클래스 생성자
		// 컴포넌트 객체화
		jFrame = new JFrame("Student Manager"); // 창 제목 설정
		jPanels = new JPanel[6]; // 패널 갯수 설정
		for(int i=0; i<jPanels.length; i++) { // 각 패널 객체화
			jPanels[i] = new JPanel();
		}
		jLabel = new JLabel("로그인"); // 로그인 화면 제목
		Font font = new Font("dialog", Font.BOLD, 20); // 폰트 생성
		jLabel.setFont(font); // 레이블에 폰트 적용
		buttonGroup = new ButtonGroup(); // 버튼 그룹 객체 생성
		jRadioButton1 = new JRadioButton("교수", true); // 교수 라디오 버튼 객체 생성
		jRadioButton2 = new JRadioButton("학생", false); // 학생 라디오 버튼 객체 생성
		jTextField = new JTextField(20); // 학번 및 교수코드 입력용 텍스트 필드 (크기 20)
		new TextHint(jTextField, "바코드 로딩 중..."); // 텍스트 힌트 적용
		jButton = new JButton("로그인"); // 로그인 버튼 객체 생성
		font = new Font("HY견고딕", Font.PLAIN, 12); // 폰트 재생성
		jButton.setFont(font); // 버튼에 폰트 적용

		// 리스너
		jFrame.addWindowListener(new WindowAdapter() { // 창 닫힐때 시스템 종료
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		jButton.addActionListener(new ActionListener() { // 버튼 작동 리스너
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jRadioButton1.isSelected()) { // 라디오 버튼 체크 확인 후 로그인 메소드 실행
					inputProfessorNumber();
				} else if(jRadioButton2.isSelected()) {
					inputStudentNumber();
				}
			}
		});
		// 텍스트 필드에 엔터 작동 리스너 삽입
		jTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					jButton.doClick();
				}
			}
		});
	} // 생성자 끝
	

	public void launchLogin() { // 로그인 화면 구성 및 출력 메소드 
		jPanels[1].setLayout(new GridBagLayout());
		jPanels[1].add(jLabel);

		jPanels[2].setLayout(new GridBagLayout());
		buttonGroup.add(jRadioButton1);
		buttonGroup.add(jRadioButton2);
		jPanels[2].add(jRadioButton1);
		jPanels[2].add(jRadioButton2);

		jPanels[3].setLayout(new GridBagLayout());
		jPanels[3].add(jTextField);

		jPanels[4].setLayout(new GridBagLayout());
		jPanels[4].add(jButton);

		jPanels[5].setLayout(new BorderLayout());
		jPanels[5].add(jPanels[1], "North");
		jPanels[5].add(jPanels[2], "Center");
		jPanels[5].add(jPanels[3], "South");

		jPanels[0].setLayout(new BorderLayout());
		jPanels[0].add(jPanels[5], "North");
		jPanels[0].add(jPanels[4], "South");

		jFrame.add(jPanels[0]);
		jFrame.pack();
		jFrame.setResizable(false);
		jFrame.setLocation(((jFrame.getToolkit().getScreenSize()).width - jFrame.getWidth())/2,
				((jFrame.getToolkit().getScreenSize()).height - jFrame.getHeight())/2);
		jFrame.setVisible(true);
		// 화면 구성 및 출력 끝
		
		// 자동 로그인 스레드 재생
		SheetsQuickstart quickstart = new SheetsQuickstart();
		scanGetCode getCode = new scanGetCode();
		thread = new Thread(quickstart); // 스프레드 시트 스레드
		thread2 = new Thread(getCode); // 학번 수신 스레드
		thread.setDaemon(true);
		thread2.setDaemon(true);
		thread.start();
		thread2.start();
	}
	class scanGetCode implements Runnable { // 학번 수신 확인시 자동 로그인 스레드
		@Override
		public void run() {
			try {
				while(true) {
					if(numberCheck(SheetsQuickstart.getBarCode())) { // 학번 형식 확인(숫자, 길이)
						String code = Integer.toString(((Integer.parseInt(SheetsQuickstart.getBarCode())-545728)/7)); // 학번 디코딩
						thread.interrupt(); // 스프레드 시트 스레드 종료
						SheetsQuickstart.setBarCode("");
						if(code.substring(0,2).equals("11")) { // 학번의 교수/학생 여부 확인
							jRadioButton1.doClick(); // 교수 선택
						} else if(code.substring(0,2).equals("12")) {
							jRadioButton2.doClick(); // 학생 선택
						}
						jTextField.setText(code); // 학번 입력
						jButton.doClick();
						thread2.interrupt();
					}
					Thread.sleep(100);
				}
			} catch (Exception e) {
				e.getStackTrace();
			} finally {}
		}
	}
	public void inputProfessorNumber() { // 교수 로그인 메소드
		if(numberCheck(jTextField.getText())) {
			DAO dao = new DAO();
			if(dao.professorLogin(jTextField.getText())>0) { // 교수코드 존재 확인
				MainGUI.setNumber(Integer.parseInt(jTextField.getText())); // 교수코드 저장
				Manager manager = new Manager();
				manager.launchManager();
				jFrame.dispose();
				thread.interrupt();
				thread2.interrupt();
			} else {
				JOptionPane.showMessageDialog(null, "교수코드를 확인 후 다시 시도해 주세요");
			}
		} else {
			JOptionPane.showMessageDialog(null, "교수코드를 확인 후 다시 시도해 주세요");
		}
	}
	public void inputStudentNumber() {
		if(numberCheck(jTextField.getText())) {
			DAO dao = new DAO();
			if(dao.studentLogin(jTextField.getText())>0) { // 학생코드 존재 확인
				MainGUI.setNumber(Integer.parseInt(jTextField.getText())); // 학생코드 저장
				Student student = new Student();
				student.launchStudent();
				jFrame.dispose();
				thread.interrupt();
				thread2.interrupt();
			} else {
				JOptionPane.showMessageDialog(null, "학번을 확인 후 다시 시도해 주세요");
			}
		} else {
			JOptionPane.showMessageDialog(null, "학번을 확인 후 다시 시도해 주세요");
		}
	}
	boolean numberCheck(String value) { // 입력된 코드의 형식(정수형) 확인과 길이 확인
		try { 
			Integer.parseInt(value);
			if(Integer.parseInt(value)>10000000 && Integer.parseInt(value)<100000000) { 
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) { 
			return false; 
		} 
	}
}