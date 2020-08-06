package StudentManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

public class Manager { // 교수 화면 클래스
	// 전역변수 선언
	private JFrame jFrame, jDialog;
	private JPanel[] jPanels;
	private JLabel jLabel, jLabel2;
	private JButton jButton1, jButton2, jButton3, jButton4, jButton5, jButton6, jButton7;
	private JTextField jTextField, tfUniv, tfMajor, tfGrade, tfStuNum, tfStuName;
	private JScrollPane jScrollPane;
	private JTable jTable;
	private String[] colName; // 각 행의 제목
	private Object[][] rowData; // 각 셀의 내용
	private NewStudentInfo info; // 다이얼로그
	private DefaultTableModel model;
	
	
	// 창 위치값 저장용 변수 선언
	private static int window = -20;

	public static int getWindow() {
		return window;
	}
	public static void setWindowOpen() {
		Manager.window += 20;
	}
	public static void setWindowClose() {
		Manager.window -= 20;
	}
	
	
	
	public Manager() { // 교수화면 생성자
		// 컴포넌트 객체화
		jFrame = new JFrame("교수 화면");
		jDialog = new JFrame();
		jPanels = new JPanel[13];
		for(int i=0; i<jPanels.length; i++) {
			jPanels[i] = new JPanel();
		}
		jLabel = new JLabel("학생 정보 관리");
		Font font = new Font("dialog", Font.BOLD, 22);
		jLabel.setFont(font);
		font = new Font("dialog", Font.BOLD, 12);
		jLabel2 = new JLabel("");
		jLabel2.setFont(font);
		font = new Font("HY견고딕", Font.PLAIN, 12);
		jButton7 = new JButton("logOut");
		jButton7.setFont(font);
		jButton1 = new JButton("성적입력");
		jButton1.setFont(font);
		jButton2 = new JButton("학생신규입력");
		jButton2.setFont(font);
		jTextField = new JTextField();
		new TextHint(jTextField, " 이름으로 테이블 검색 ");
		tfUniv = new JTextField(8);
		tfUniv.setEditable(false);
		tfMajor = new JTextField(8);
		tfMajor.setEditable(false);
		tfGrade = new JTextField(8);
		tfGrade.setEditable(false);
		tfStuNum = new JTextField(8);
		tfStuNum.setEditable(false);
		tfStuName = new JTextField(8);
		tfStuName.setEditable(false);
		jButton3 = new JButton("검색");
		jButton3.setFont(font);
		jButton4 = new JButton("상세정보");
		jButton4.setFont(font);
		jButton5 = new JButton("삭제");
		jButton5.setFont(font);
		jButton6 = new JButton("학생증 이미지 생성");
		jButton6.setFont(font);
		colName = new String[]{"소 속", "학 과", "학 기", "학 번", "이 름"};
		displayAll(); // 테이블에 불러온 값 넣기
		jScrollPane = new JScrollPane(jTable); // 스크롤판 생성 및 테이블 삽입
		jScrollPane.setPreferredSize(new Dimension(0, 300)); // 세로길이 300
		jScrollPane.getViewport().setBackground(Color.white); // 배경 하얀색
		
		
		// 리스너
		jFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}  
		});
		jButton1.addActionListener(new ActionListener() { // 성적입력 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		jButton2.addActionListener(new ActionListener() { // 신입생 입력 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				info = new NewStudentInfo(jDialog, "학생신규입력"); // 신입생 입력 다이얼로그 생성
				info.setModal(true); // 상위 화면 클릭 방지
				info.setVisible(true); // 화면 출력
				while(info.isVisible()) { // 화면 열려있는 동안 정지 
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				newDisplay(); // 새로운 테이블 출력
			}
		});
		jButton3.addActionListener(new ActionListener() { // 이름으로 학번 검색용 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				serchNumber(); // 학번 검색용 메소드
			}
		});
		jTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					jButton3.doClick();
				}
			}
		});
		
		jButton4.addActionListener(new ActionListener() { // 상세정보 확인용 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfStuNum.getText().isEmpty()) { // 학번이 선택되지 않았을 때
					JOptionPane.showMessageDialog(null, "상세정보를 조회할 학생을 선택해 주세요");
				} else { // 학번이 선탣 되었을 때
					StudentInfo info = new StudentInfo(tfStuNum.getText()); // 학생 상세정보 창 생성
					setWindowOpen(); // 화면위치 조정
					info.launchStudentInfo();
				}
			}
		});
		jButton5.addActionListener(new ActionListener() { // 학생정보 삭제용 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfStuNum.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "삭제할 학생 정보를 선택해 주세요");
				} else {
					int result = JOptionPane.showConfirmDialog(null, 
							tfStuName.getText()+" 학생의 정보를 삭제 하시겠습니까?", 
							"Confirm", JOptionPane.YES_NO_OPTION); // 학생정보 삭제 여부 확인
					if(result == JOptionPane.YES_OPTION) {
						delete();
						newDisplay();
					}
				}
			}
		});
		jButton6.addActionListener(new ActionListener() { // 학생증 이미지 생성용 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfStuNum.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "이미지를 생성할 학생을 선택해 주세요");
				} else {
					int result = JOptionPane.showConfirmDialog(null, 
							tfStuName.getText()+" 학생의 학생증 이미지를 생성 하시겠습니까?", 
							"Confirm", JOptionPane.YES_NO_OPTION); // 학생증 이미지 생성 여부 확인
					if(result == JOptionPane.YES_OPTION) {
						makeImage();
					}
				}
			}
		});
		jButton7.addActionListener(new ActionListener() { // 로그아웃용 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.launchLogin();
				jFrame.dispose();
			}
		});
		jTable.addMouseListener(new MouseListener() { // 마우스 작동 리스너
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) { // 마우스로 누른 테이블 열의 학생 정보 불러오기
				int row = jTable.getSelectedRow();
				tfUniv.setText(jTable.getValueAt(row, 0).toString());
				tfMajor.setText(jTable.getValueAt(row, 1).toString());
				tfGrade.setText(jTable.getValueAt(row, 2).toString());
				tfStuNum.setText(jTable.getValueAt(row, 3).toString());
				tfStuName.setText(jTable.getValueAt(row, 4).toString());
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	} // 생성자 끝
	
	public void launchManager() { // 화면 구성 및 출력
		jLabel2.setText("교수코드 : " + MainGUI.getNumber());
		jPanels[1].setLayout(new GridLayout());
		jPanels[1].add(jPanels[10]);
		jPanels[1].add(jPanels[12]);
		jPanels[1].add(new JLabel(""));
		jPanels[10].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[10].add(jLabel2);
		jPanels[12].setLayout(new FlowLayout(FlowLayout.CENTER));
		jPanels[12].add(jLabel);
		jPanels[2].setLayout(new FlowLayout(FlowLayout.TRAILING));
		jPanels[2].add(jButton1);
		jPanels[2].add(jButton2);
		jPanels[3].setLayout(new FlowLayout());
		jPanels[3].setBackground(Color.DARK_GRAY);
		jPanels[3].setForeground(Color.white);
		jPanels[3].add(jTextField);
		jPanels[3].add(jButton3);
		jPanels[3].add(jButton4);
		jPanels[3].add(jButton5);
		jPanels[3].add(new Label("                                             "));
		jPanels[3].add(jButton6);
		jPanels[11].setLayout(new FlowLayout());
		jPanels[11].setBackground(Color.LIGHT_GRAY);
		jPanels[11].setForeground(Color.white);
		jPanels[11].add(new JLabel("소속"));
		jPanels[11].add(tfUniv);
		jPanels[11].add(new JLabel("학과"));
		jPanels[11].add(tfMajor);
		jPanels[11].add(new JLabel("학기"));
		jPanels[11].add(tfGrade);
		jPanels[11].add(new JLabel("학번"));
		jPanels[11].add(tfStuNum);
		jPanels[11].add(new JLabel("이름"));
		jPanels[11].add(tfStuName);
		jPanels[4].setLayout(new BorderLayout());
		jPanels[4].add(jPanels[11], "North");
		jPanels[4].add(jScrollPane, "Center");
		jPanels[5].setLayout(new GridBagLayout());
		jPanels[5].add(jButton7);
		jPanels[6].setLayout(new BorderLayout());
		jPanels[6].add(jPanels[1], "North");
		jPanels[6].add(jPanels[2], "Center");
		jPanels[6].add(jPanels[3], "South");
		jPanels[7].setLayout(new BorderLayout());
		jPanels[7].add(jPanels[6], "North");
		jPanels[7].add(jPanels[4], "Center");
		jPanels[7].add(jPanels[5], "South");
		jPanels[8].setLayout(new GridBagLayout());
		jPanels[8].add(new Label(" "));
		jPanels[9].setLayout(new GridBagLayout());
		jPanels[9].add(new Label(" "));
		jPanels[0].setLayout(new BorderLayout());
		jPanels[0].add(jPanels[8], "East");
		jPanels[0].add(jPanels[7], "Center");
		jPanels[0].add(jPanels[9], "West");

		jFrame.add(jPanels[0]);
		jFrame.pack();
		jFrame.setResizable(false);
		jFrame.setLocation(((jFrame.getToolkit().getScreenSize()).width - jFrame.getWidth())/2,
				((jFrame.getToolkit().getScreenSize()).height - jFrame.getHeight())/2);
		jFrame.setVisible(true);
	}
	
	public void displayAll() { // 테이블에 값 불러와서 넣기
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.simpleInfoSelect(); // db에서 학생정보 가져오기
		Comparator<DTO> comparator = new Comparator<DTO>() {
			@Override
			public int compare(DTO o1, DTO o2) {
				return o1.getStudentNum().compareTo(o2.getStudentNum());
			}
		};
		allData.sort(comparator);
		rowData = new Object[allData.size()][5];
		int i=0;
		for(DTO dto : allData) { // 각 셀에 정보 넣기
			rowData[i][0] = dto.getUnivName();
			rowData[i][1] = dto.getMajorName();
			rowData[i][2] = dto.getGrade();
			rowData[i][3] = dto.getStudentNum();
			rowData[i][4] = dto.getStudentName();
			i++;
		}
		model = new DefaultTableModel(rowData, colName) { // 각 셀의 내용과 각 행의 제목을 테이블에 삽입하고 편집하지 목하게 막음
			public boolean isCellEditable(int row, int column) {return false;}
		};
		jTable = new JTable(model); // 모델을 테이블에 세팅
		MyRenderer myRenderer = new MyRenderer(); // 테이블 배경 색상 설정
		jTable.setDefaultRenderer(Object.class, myRenderer); // 테이블 배경 색상 적용
		jTable.getTableHeader().setReorderingAllowed(false); // 각 행 드래그 막기 
		jTable.getTableHeader().setResizingAllowed(false); // 각 행 사이즈 조절 막기
		jTable.setRowSelectionAllowed(false); // 각 셀의 행 드래그 막기
	}
	
	public void serchNumber() { // 이름으로 학생정보 검색용 메소드
		if(jTextField.getText().equals(" 이름으로 테이블 검색 ") || jTextField.getText().equals("")) { // 텍스트 필드가 비어있거나 텍스트 힌트일 때 새로고침
			DAO dao = new DAO();
			ArrayList<DTO> allData = dao.simpleInfoSelect(); // db에서 전체 학생정보 가져오기
			rowData = new Object[allData.size()][5];
			int i=0;
			for(DTO dto : allData) {
				rowData[i][0] = dto.getUnivName();
				rowData[i][1] = dto.getMajorName();
				rowData[i][2] = dto.getGrade();
				rowData[i][3] = dto.getStudentNum();
				rowData[i][4] = dto.getStudentName();
				i++;
			}
			model = new DefaultTableModel(rowData, colName) {
				public boolean isCellEditable(int row, int column) {return false;}
			};
			jTable.setModel(model);
		} else { // 텍스트 필드에 입력한 이름과 일치하는 학생 정보만 가져오기
			DAO dao = new DAO();
			ArrayList<DTO> allData = dao.studentNameSerch(jTextField.getText());
			rowData = new Object[allData.size()][5];
			int i=0;
			for(DTO dto : allData) {
				rowData[i][0] = dto.getUnivName();
				rowData[i][1] = dto.getMajorName();
				rowData[i][2] = dto.getGrade();
				rowData[i][3] = dto.getStudentNum();
				rowData[i][4] = dto.getStudentName();
				i++;
			}
			model = new DefaultTableModel(rowData, colName) {
				public boolean isCellEditable(int row, int column) {return false;}
			};
			jTable.setModel(model);
		}
	}
	public void newDisplay() { // 테이블 새로고침
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.simpleInfoSelect();
		rowData = new Object[allData.size()][5];
		int i=0;
		for(DTO dto : allData) {
			rowData[i][0] = dto.getUnivName();
			rowData[i][1] = dto.getMajorName();
			rowData[i][2] = dto.getGrade();
			rowData[i][3] = dto.getStudentNum();
			rowData[i][4] = dto.getStudentName();
			i++;
		}
		model = new DefaultTableModel(rowData, colName) {
			public boolean isCellEditable(int row, int column) {return false;}
		};
		jTable.setModel(model);
	}
	public void delete() { // 학생정보 삭제용 메소드
		DAO dao = new DAO();
		String studentNumber = tfStuNum.getText();
		dao.StudentInfoDelete(studentNumber);
		// 삭제된 학생 정보 비움
		tfUniv.setText("");
		tfMajor.setText("");
		tfGrade.setText("");
		tfStuNum.setText("");
		tfStuName.setText("");
		File file = new File("resources/"+studentNumber+".png"); // 학번에 해당하는 사진 삭제
		file.delete(); 
	}
	public void makeImage() { // 학생증 이미지 생성용 메소드 
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.BirthdaySerch(tfStuNum.getText());
		String BirthDay = null;
		for(DTO dto : allData) {
			BirthDay = dto.getBirthDay().substring(2, 4) + dto.getBirthDay().substring(5, 7) + dto.getBirthDay().substring(8, 10); // 1995-07-29
		}
		try {
			// 바코드 생성
			String code = Integer.toString(Integer.parseInt(tfStuNum.getText())*7+545728); // 학번 인코딩	
			Barcode barcode = BarcodeFactory.createCode128(code); // 학번 삽입
			barcode.setDrawingText(false); // 텍스트 삭제
			barcode.setBarHeight(50); // 높이
			File file = new File("resources/"+tfStuNum.getText()+"BarCode.png"); // 저장
			BarcodeImageHandler.savePNG(barcode, file);
			// 학생증 뒷면
			BufferedImage img = new BufferedImage(324, 204, BufferedImage.TYPE_INT_ARGB); // 학생증 크기
			Graphics2D g2d = (Graphics2D) img.getGraphics();
			g2d.setColor(Color.getHSBColor(0.5f, 0.4166f, 0.8f)); // 배경색
			g2d.fillRect(0, 0, 324, 204); // 네모 그리기
			g2d.setColor(Color.GRAY); // 마그네틱 색
			for(int i=19; i<68; i++) { // 마그네틱 그리기
				g2d.drawLine(0, i, 324, i);
			}
			BufferedImage loadImage = ImageIO.read(new File("resources/"+tfStuNum.getText()+"BarCode.png")); // 바코드 불러오기
			g2d.drawImage(loadImage, null, 100, 150); // 바코드 삽입
			ImageIO.write(img, "png", new File("resources/"+tfStuNum.getText()+"CardBack.png")); // 뒷면 저장
			//학생증 앞면
			g2d.setColor(Color.getHSBColor(0.5f, 0.4166f, 0.8f));
			g2d.fillRect(0, 0, 324, 204);
			g2d.setColor(Color.BLACK); // 텍스트 생성 시작
			Font font = new Font("굴림", Font.BOLD, 20);
			g2d.setFont(font);
			g2d.drawString("학       생       증", 125, 30);
			font = new Font("굴림", Font.BOLD, 15);
			g2d.setFont(font);
			g2d.drawString("학     과 : "+tfMajor.getText(), 125, 55);
			g2d.drawString("학     번 : "+tfStuNum.getText(), 125, 83);
			g2d.drawString("이     름 : "+tfStuName.getText(), 125, 111);
			g2d.drawString("생년월일 : "+BirthDay, 125, 139); // 텍스트 생성 끝
			loadImage = ImageIO.read(new File("resources/ezen.png")); // 이젠 로고 불러오기
			g2d.drawImage(loadImage,null,10,150);
			loadImage = ImageIO.read(new File("resources/"+tfStuNum.getText()+".png")); // 사진 불러오기
			g2d.drawImage(loadImage,null,10,10);
			ImageIO.write(img, "png", new File("resources/"+tfStuNum.getText()+"CardFront.png")); // 앞면 저장
			g2d.dispose();
			JOptionPane.showMessageDialog(null, "이미지 생성이 완료되었습니다");
		} catch (Exception e) {
			e.getStackTrace();
			JOptionPane.showMessageDialog(null, "이미지 생성 중 예외가 발생했습니다", "Message", JOptionPane.ERROR_MESSAGE);
		}
	}
	public boolean tryParseInt(String value) {  // 정수형 확인
		try { 
			Integer.parseInt(value); 
			return true;
		} catch (NumberFormatException e) { 
			return false; 
		} 
	}
}