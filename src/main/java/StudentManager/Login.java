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



public class Login { // �α��� Ŭ���� 
	// �������� ����
	private JFrame jFrame;
	private JPanel[] jPanels;
	private JLabel jLabel;
	private JRadioButton jRadioButton1, jRadioButton2;
	private ButtonGroup buttonGroup;
	private JTextField jTextField;
	private JButton jButton;
	private Thread thread, thread2;
	
	
	public Login() { // �α��� Ŭ���� ������
		// ������Ʈ ��üȭ
		jFrame = new JFrame("Student Manager"); // â ���� ����
		jPanels = new JPanel[6]; // �г� ���� ����
		for(int i=0; i<jPanels.length; i++) { // �� �г� ��üȭ
			jPanels[i] = new JPanel();
		}
		jLabel = new JLabel("�α���"); // �α��� ȭ�� ����
		Font font = new Font("dialog", Font.BOLD, 20); // ��Ʈ ����
		jLabel.setFont(font); // ���̺� ��Ʈ ����
		buttonGroup = new ButtonGroup(); // ��ư �׷� ��ü ����
		jRadioButton1 = new JRadioButton("����", true); // ���� ���� ��ư ��ü ����
		jRadioButton2 = new JRadioButton("�л�", false); // �л� ���� ��ư ��ü ����
		jTextField = new JTextField(20); // �й� �� �����ڵ� �Է¿� �ؽ�Ʈ �ʵ� (ũ�� 20)
		new TextHint(jTextField, "���ڵ� �ε� ��..."); // �ؽ�Ʈ ��Ʈ ����
		jButton = new JButton("�α���"); // �α��� ��ư ��ü ����
		font = new Font("HY�߰��", Font.PLAIN, 12); // ��Ʈ �����
		jButton.setFont(font); // ��ư�� ��Ʈ ����

		// ������
		jFrame.addWindowListener(new WindowAdapter() { // â ������ �ý��� ����
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		jButton.addActionListener(new ActionListener() { // ��ư �۵� ������
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jRadioButton1.isSelected()) { // ���� ��ư üũ Ȯ�� �� �α��� �޼ҵ� ����
					inputProfessorNumber();
				} else if(jRadioButton2.isSelected()) {
					inputStudentNumber();
				}
			}
		});
		// �ؽ�Ʈ �ʵ忡 ���� �۵� ������ ����
		jTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					jButton.doClick();
				}
			}
		});
	} // ������ ��
	

	public void launchLogin() { // �α��� ȭ�� ���� �� ��� �޼ҵ� 
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
		// ȭ�� ���� �� ��� ��
		
		// �ڵ� �α��� ������ ���
		SheetsQuickstart quickstart = new SheetsQuickstart();
		scanGetCode getCode = new scanGetCode();
		thread = new Thread(quickstart); // �������� ��Ʈ ������
		thread2 = new Thread(getCode); // �й� ���� ������
		thread.setDaemon(true);
		thread2.setDaemon(true);
		thread.start();
		thread2.start();
	}
	class scanGetCode implements Runnable { // �й� ���� Ȯ�ν� �ڵ� �α��� ������
		@Override
		public void run() {
			try {
				while(true) {
					if(numberCheck(SheetsQuickstart.getBarCode())) { // �й� ���� Ȯ��(����, ����)
						String code = Integer.toString(((Integer.parseInt(SheetsQuickstart.getBarCode())-545728)/7)); // �й� ���ڵ�
						thread.interrupt(); // �������� ��Ʈ ������ ����
						SheetsQuickstart.setBarCode("");
						if(code.substring(0,2).equals("11")) { // �й��� ����/�л� ���� Ȯ��
							jRadioButton1.doClick(); // ���� ����
						} else if(code.substring(0,2).equals("12")) {
							jRadioButton2.doClick(); // �л� ����
						}
						jTextField.setText(code); // �й� �Է�
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
	public void inputProfessorNumber() { // ���� �α��� �޼ҵ�
		if(numberCheck(jTextField.getText())) {
			DAO dao = new DAO();
			if(dao.professorLogin(jTextField.getText())>0) { // �����ڵ� ���� Ȯ��
				MainGUI.setNumber(Integer.parseInt(jTextField.getText())); // �����ڵ� ����
				Manager manager = new Manager();
				manager.launchManager();
				jFrame.dispose();
				thread.interrupt();
				thread2.interrupt();
			} else {
				JOptionPane.showMessageDialog(null, "�����ڵ带 Ȯ�� �� �ٽ� �õ��� �ּ���");
			}
		} else {
			JOptionPane.showMessageDialog(null, "�����ڵ带 Ȯ�� �� �ٽ� �õ��� �ּ���");
		}
	}
	public void inputStudentNumber() {
		if(numberCheck(jTextField.getText())) {
			DAO dao = new DAO();
			if(dao.studentLogin(jTextField.getText())>0) { // �л��ڵ� ���� Ȯ��
				MainGUI.setNumber(Integer.parseInt(jTextField.getText())); // �л��ڵ� ����
				Student student = new Student();
				student.launchStudent();
				jFrame.dispose();
				thread.interrupt();
				thread2.interrupt();
			} else {
				JOptionPane.showMessageDialog(null, "�й��� Ȯ�� �� �ٽ� �õ��� �ּ���");
			}
		} else {
			JOptionPane.showMessageDialog(null, "�й��� Ȯ�� �� �ٽ� �õ��� �ּ���");
		}
	}
	boolean numberCheck(String value) { // �Էµ� �ڵ��� ����(������) Ȯ�ΰ� ���� Ȯ��
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