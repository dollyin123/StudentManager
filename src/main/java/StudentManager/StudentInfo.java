package StudentManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentInfo { // �л� ������ Ŭ����
	//�������� ����
	private JFrame jFrame, jDialog;
	private JPanel[] jPanel;
	private ImageIcon imageIcon;
	private JLabel titleLable, imageLable, univName, Grade, majorName, PhoneNum, address;
	private JScrollPane jScrollPane;
	private JTable jTable;
	private String[] colName;
	private Object[][] rowData;
	private JButton exit, managerUpdate;
	private String studentNumber;
	private UpdateStudentInfo info;
	
	
	public StudentInfo(String studentNuber) { // �л� ������ Ŭ���� ������ 
		this.studentNumber = studentNuber;
		jFrame = new JFrame("�л� �� ����");
		jPanel = new JPanel[12];
		for(int i=0; i<jPanel.length; i++) {
			jPanel[i] = new JPanel();
		}
		titleLable = new JLabel("�л� �� ����");
		Font font = new Font("dialog", Font.BOLD, 22);
		titleLable.setFont(font);
		imageIcon = new ImageIcon("resources/"+studentNuber+".png");
		imageLable = new JLabel(imageIcon);
		univName = new JLabel();
		Grade = new JLabel();
		majorName = new JLabel();
		PhoneNum = new JLabel();
		address = new JLabel();
		colName = new String[] {"�����ڵ�", "���Ǹ�", "����"};
		lectureTableView();
		jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(0, 121));
		jScrollPane.getViewport().setBackground(Color.white);
		font = new Font("HY�߰��", Font.PLAIN, 12);
		exit = new JButton("�ݱ�");
		exit.setFont(font);
		managerUpdate = new JButton("����");
		managerUpdate.setFont(font);
		
		
		// ������
		jFrame.addWindowListener(new WindowAdapter() { // â ������ �ش� â�� ����
			@Override
			public void windowClosing(WindowEvent e) {
				Manager.setWindowClose(); // â ��ġ ����
				jFrame.dispose();
			}
		});
		exit.addActionListener(new ActionListener() { // â ���� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				Manager.setWindowClose();
				jFrame.dispose();
			}
		});
		managerUpdate.addActionListener(new ActionListener() { // ���� â ���� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				info = new UpdateStudentInfo(jDialog, "�л����� ����", studentNumber);
				info.setModal(true);
				info.setVisible(true);
				while(info.isVisible()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				jFrame.dispose();
			}
		});
	}
	public void launchStudentInfo() { // ȭ�� ���� �� ���
		DTO dto = new DTO();
		dto = allInfoSerch(dto);
		jPanel[1].setLayout(new GridBagLayout());
		jPanel[1].add(titleLable);
		jPanel[2].setLayout(new GridLayout());
		jPanel[2].add(imageLable);
		jPanel[3].setLayout(new GridLayout(6,1));
		univName.setText(dto.getUnivName());
		jPanel[3].add(new JLabel("  �Ҽ� : " + univName.getText())); // �����ڰ� �ٲ� ��
		majorName.setText(dto.getMajorName());
		jPanel[3].add(new JLabel("  �а� : " + majorName.getText())); // �����ڰ� �ٲ� ��
		Grade.setText(Integer.toString(dto.getGrade()));
		jPanel[3].add(new JLabel("  �б� : " + Grade.getText())); // �����ڰ� �ٲ� ��
		jPanel[3].add(new JLabel("  �й� : " + dto.getStudentNum()));
		jPanel[3].add(new JLabel("  �̸� : " + dto.getStudentName()));
		jPanel[3].add(new JLabel("  ���� : " + dto.getSex()));
		jPanel[4].setLayout(new GridLayout(4,1));
		jPanel[4].add(new JLabel("  ������� : " + dto.getBirthDay().substring(0, 10)));
		jPanel[4].add(new JLabel("  ���г⵵ : " + dto.getEntranceDay().substring(0, 10)));
		PhoneNum.setText(dto.getPhoneNum());
		jPanel[4].add(new JLabel("  ��ȭ��ȣ : " + PhoneNum.getText())); // �л��� �ٲ� ��
		address.setText(dto.getAddress());
		jPanel[4].add(new JLabel("  �ּ� : " + address.getText())); // �л��� �ٲ� ��
		jPanel[5].setLayout(new BorderLayout());
		jPanel[5].add(jScrollPane);
		jPanel[6].setLayout(new GridBagLayout());
		jPanel[6].add(managerUpdate);
		jPanel[6].add(new JLabel(" "));
		jPanel[6].add(exit);
		jPanel[7].setLayout(new BorderLayout());
		jPanel[7].add(jPanel[1], "North");
		jPanel[7].add(jPanel[2], "West");
		jPanel[7].add(jPanel[3], "Center");
		jPanel[8].setLayout(new BorderLayout());
		jPanel[8].add(jPanel[4], "North");
		jPanel[8].add(jPanel[5], "Center");
		jPanel[8].add(jPanel[6], "South");
		jPanel[9].setLayout(new BorderLayout());
		jPanel[9].add(jPanel[7], "North");
		jPanel[9].add(jPanel[8], "South");
		jPanel[10].setLayout(new GridBagLayout());
		jPanel[10].add(new Label(" "));
		jPanel[11].setLayout(new GridBagLayout());
		jPanel[11].add(new Label(" "));
		jPanel[0].setLayout(new BorderLayout());
		jPanel[0].add(jPanel[9], "Center");
		jPanel[0].add(jPanel[10], "East");
		jPanel[0].add(jPanel[11], "West");
		
		jFrame.add(jPanel[0]);
		jFrame.pack();
		jFrame.setResizable(false);
		jFrame.setLocation(((jFrame.getToolkit().getScreenSize()).width - jFrame.getWidth())/2 + Manager.getWindow(),
				((jFrame.getToolkit().getScreenSize()).height - jFrame.getHeight())/2 + Manager.getWindow());
		jFrame.setVisible(true);
	}
	
	
	public void lectureTableView() {// ���� ���� ��� �޼ҵ�
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.lectureSelect(studentNumber);
		rowData = new Object[allData.size()][3];
		int i=0;
		for(DTO dto : allData) {
			rowData[i][0] = dto.getLectureCode();
			rowData[i][1] = dto.getLectureName();
			rowData[i][2] = dto.getScore();
			i++;
		}
		DefaultTableModel model = new DefaultTableModel(rowData, colName) {
			public boolean isCellEditable(int row, int column) {return false;}
		};
		jTable = new JTable(model);
		MyRenderer myRenderer = new MyRenderer();
		jTable.setDefaultRenderer(Object.class, myRenderer);
		jTable.getTableHeader().setReorderingAllowed(false);
		jTable.setRowSelectionAllowed(false);
	}
	public DTO allInfoSerch(DTO dto) { // db���� ������ �ҷ����� �޼ҵ�
		DAO dao = new DAO();
		dto = dao.AllInfoSelect(studentNumber);
		return dto;
	}
}
