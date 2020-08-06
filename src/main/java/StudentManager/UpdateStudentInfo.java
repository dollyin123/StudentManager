package StudentManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateStudentInfo extends JDialog { // �л����� ���� ���̾�α� Ŭ����
	// �������� ����
	private JPanel[] jPanels;
	private JLabel titleLable;
	private JComboBox<String> jComboBox1, jComboBox2, ad1, ad2, ad3;
	private JTextField jTextField, jTextField2, jTextField3;
	private JButton exit, update;
	private String studentNumber;
	private ActionListener ad1Listener, ad2Listener; // �ּ� ����� �׼� ������

	public UpdateStudentInfo(JFrame jFrame, String title, String studentNumber) { // �л����� ���� ���̾�α� Ŭ���� ������(ȭ�� ���� �� ��� ����)
		super(jFrame, title);
		this.studentNumber = studentNumber;
		jFrame = new JFrame("�л� ���� ����");
		jPanels = new JPanel[19];
		for(int i=0; i<jPanels.length; i++) {
			jPanels[i] = new JPanel();
		}
		titleLable = new JLabel("                    �л� ���� ����                    ");
		Font font = new Font("dialog", Font.BOLD, 22);
		titleLable.setFont(font);
		font = new Font("HY�߰��", Font.PLAIN, 12);
		jComboBox1 = new JComboBox<String>();
		jComboBox2 = new JComboBox<String>();
		ad1 = new JComboBox<String>();
		ad2 = new JComboBox<String>();
		ad3 = new JComboBox<String>();
		jComboBox1.setFont(font);
		jComboBox2.setFont(font);
		ad1.setFont(font);
		ad2.setFont(font);
		ad3.setFont(font);
		jTextField = new JTextField();
		new TextHint(jTextField, "ex) 2  ");
		jTextField2 = new JTextField();
		new TextHint(jTextField2, "ex) 010-1234-5678  ");
		jTextField3 = new JTextField();
		new TextHint(jTextField3, "ex) �����154����           ");
		font = new Font("HY�߰��", Font.PLAIN, 12);
		exit = new JButton("���");
		exit.setFont(font);
		update = new JButton("����");
		update.setFont(font);
		DTO dto = new DTO();
		dto = allInfoSerch(dto);

		jPanels[1].setLayout(new GridBagLayout());
		jPanels[1].add(titleLable);

		if(Integer.toString(MainGUI.getNumber()).substring(0,2).equals("11")) { // ������ ȭ�� ����

			jPanels[2].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[2].add(new JLabel("��        �� : "));
			jPanels[2].add(jComboBox1);

			jPanels[3].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[3].add(new JLabel("��        �� : "));
			jPanels[3].add(jComboBox2);

			jPanels[4].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[4].add(new JLabel("��        �� : "));
			jPanels[4].add(jTextField);

			jPanels[5].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[5].add(new JLabel("��        �� : "));
			jPanels[5].add(new JLabel(dto.getStudentName()));

			jPanels[6].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[6].add(new JLabel("��        �� : "));
			jPanels[6].add(new JLabel(dto.getSex()));

			jPanels[7].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[7].add(new JLabel("������� : "));
			jPanels[7].add(new JLabel(dto.getBirthDay().substring(0, 10)));

			jPanels[8].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[8].add(new JLabel("���г⵵ : "));
			jPanels[8].add(new JLabel(dto.getEntranceDay().substring(0, 10)));

			jPanels[9].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[9].add(new JLabel("��ȭ��ȣ : "));
			jPanels[9].add(new JLabel(dto.getPhoneNum()));

			jPanels[10].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[10].add(new JLabel("��        �� : "));
			jPanels[10].add(new JLabel(dto.getAddress()));


		} else if(Integer.toString(MainGUI.getNumber()).substring(0,2).equals("12")) { // �л��� ȭ�� ����

			jPanels[2].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[2].add(new JLabel("��        �� : "));
			jPanels[2].add(new JLabel(dto.getUnivName()));

			jPanels[3].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[3].add(new JLabel("��        �� : "));
			jPanels[3].add(new JLabel(dto.getMajorName()));

			jPanels[4].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[4].add(new JLabel("��        �� : "));
			jPanels[4].add(new JLabel(Integer.toString(dto.getGrade())));

			jPanels[5].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[5].add(new JLabel("��        �� : "));
			jPanels[5].add(new JLabel(dto.getStudentName()));

			jPanels[6].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[6].add(new JLabel("��        �� : "));
			jPanels[6].add(new JLabel(dto.getSex()));

			jPanels[7].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[7].add(new JLabel("������� : "));
			jPanels[7].add(new JLabel(dto.getBirthDay().substring(0,10)));

			jPanels[8].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[8].add(new JLabel("���г⵵ : "));
			jPanels[8].add(new JLabel(dto.getEntranceDay().substring(0,10)));

			jPanels[9].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[9].add(new JLabel("��ȭ��ȣ : "));
			jPanels[9].add(jTextField2);

			jPanels[10].setLayout(new BorderLayout());
			jPanels[10].add(jPanels[17], "North");
			jPanels[10].add(jPanels[18], "Center");

			jPanels[17].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[17].add(new JLabel("��        �� : "));
			jPanels[17].add(ad1);
			jPanels[17].add(ad2);
			jPanels[17].add(ad3);

			jPanels[18].setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanels[18].add(new JLabel("                    "));
			jPanels[18].add(jTextField3); 

		}

		jPanels[11].setLayout(new GridBagLayout());
		jPanels[11].add(update);
		jPanels[11].add(new JLabel(" "));
		jPanels[11].add(exit);

		jPanels[12].setLayout(new BorderLayout());
		jPanels[12].add(jPanels[1], "North");
		jPanels[12].add(jPanels[2], "Center");
		jPanels[12].add(jPanels[3], "South");
		jPanels[13].setLayout(new BorderLayout());
		jPanels[13].add(jPanels[4], "North");
		jPanels[13].add(jPanels[5], "Center");
		jPanels[13].add(jPanels[6], "South");
		jPanels[14].setLayout(new BorderLayout());
		jPanels[14].add(jPanels[7], "North");
		jPanels[14].add(jPanels[8], "Center");
		jPanels[14].add(jPanels[9], "South");
		jPanels[15].setLayout(new BorderLayout());
		jPanels[15].add(jPanels[10], "North");
		jPanels[15].add(jPanels[11], "Center");
		jPanels[16].setLayout(new BorderLayout());
		jPanels[16].add(jPanels[12], "North");
		jPanels[16].add(jPanels[13], "Center");
		jPanels[16].add(jPanels[14], "South");
		jPanels[0].setLayout(new BorderLayout());
		jPanels[0].add(jPanels[16], "North");
		jPanels[0].add(jPanels[15], "Center");

		add(jPanels[0]);
		pack();
		setResizable(false);
		setLocation(((getToolkit().getScreenSize()).width - getWidth())/2 + Manager.getWindow(),
				((getToolkit().getScreenSize()).height - getHeight())/2 + Manager.getWindow());


		// ������
		addWindowListener(new WindowAdapter() { // �F ������ �ش� ���̾�α׸� ����
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				reset(); // ȭ�� �����
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				reset();
			}
		});
		update.addActionListener(new ActionListener() { // ���� Ȯ�� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Integer.toString(MainGUI.getNumber()).substring(0,2).equals("11")) { // ����/�л� ���� Ȯ��
					int result = JOptionPane.showConfirmDialog(null, "�Է��Ͻ� ������ ���� �Ͻðڽ��ϱ�?");
					if(result == JOptionPane.YES_OPTION) {
						managerUpdate();
						dispose();
						reset();
					}
				} else if(Integer.toString(MainGUI.getNumber()).substring(0,2).equals("12")) {
					int result = JOptionPane.showConfirmDialog(null, "�Է��Ͻ� ������ ���� �Ͻðڽ��ϱ�?");
					if(result == JOptionPane.YES_OPTION) {
						StudentUpdate();
						dispose();
						reset();
					}
				}
			}
		});
		jComboBox1.addActionListener(new ActionListener() { // �Ҽ� �� �а� ���ÿ� ������
			@Override
			public void actionPerformed(ActionEvent e) {
				jComboBox2.removeAllItems();
				bringMajor(jComboBox1.getSelectedItem().toString());
			}
		});
		ad1Listener = new ActionListener() { //�ּҰ˻��� ������
			@Override
			public void actionPerformed(ActionEvent e) {
				ad2Select(ad1.getSelectedItem().toString());
			}
		};
		ad2Listener = new ActionListener() { // �ּҰ˻��� ������
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ad2.getSelectedItem() != null) {
					ad3Select(ad2.getSelectedItem().toString());
				}
			}
		};
		displayCombo();
		ad1Select();
	}
	public void displayCombo() {
		jComboBox1.removeAllItems();
		jComboBox2.removeAllItems();
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.comboSelect();
		for(DTO dto : allData) {
			jComboBox1.addItem(dto.getUnivName());
		}
	}
	public void bringMajor(String univ) {
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.combo2Select(univ);
		for(DTO dto : allData) {
			jComboBox2.addItem(dto.getMajorName());
		}
	}
	public DTO allInfoSerch(DTO dto) {
		DAO dao = new DAO();
		dto = dao.AllInfoSelect(studentNumber);
		return dto;
	}
	public void managerUpdate() { // ������ �������� �޼ҵ�
		String univName = jComboBox1.getSelectedItem().toString();
		String majorName = jComboBox2.getSelectedItem().toString();
		String grade = jTextField.getText();
		DAO dao = new DAO();
		dao.updateAtManager(studentNumber, univName, majorName, grade);
	}
	public void StudentUpdate() { // �л��� �������� �޼ҵ�
		String phoneNum = jTextField2.getText();

		String addres2 = "";
		if(ad2.getSelectedItem() != null) {
			addres2 = " " + ad2.getSelectedItem().toString();
		}
		String addres3 = "";
		if(ad3.getSelectedItem() != null) {
			addres3 = " " + ad3.getSelectedItem().toString();
		}
		String addres4 = "";
		if(jTextField3.getText().equals("ex) �����154����           ")) {
			addres4 = "";
		} else {
			addres4 = " " + jTextField3.getText();
		}
		String address = ad1.getSelectedItem().toString() + addres2 + addres3 + addres4;

		DAO dao = new DAO();
		dao.updateAtStudent(studentNumber, phoneNum, address);
	}
	public void reset() { // ȭ�� �����
		StudentInfo info = new StudentInfo(studentNumber);
		Manager.setWindowClose();
		Manager.setWindowOpen();
		info.launchStudentInfo();
	}
	public void ad1Select() {
		ad3.removeAllItems();
		ad2.removeAllItems();
		ad1.removeAllItems();
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.ad1Serch();
		for(DTO dto : allData) {
			ad1.addItem(dto.getAd1());
		}
		ad1.addActionListener(ad1Listener);
	}
	public void ad2Select(String ad) {
		ad2.removeActionListener(ad2Listener);
		ad3.removeAllItems();
		ad2.removeAllItems();
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.ad2Serch(ad);
		for(DTO dto : allData) {
			ad2.addItem(dto.getAd2());
		}
		ad2.addActionListener(ad2Listener);
	}
	public void ad3Select(String ad) {
		ad3.removeAllItems();
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.ad3Serch(ad);
		for(DTO dto : allData) {
			ad3.addItem(dto.getAd3());
		}
	}
}
