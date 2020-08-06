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

public class Manager { // ���� ȭ�� Ŭ����
	// �������� ����
	private JFrame jFrame, jDialog;
	private JPanel[] jPanels;
	private JLabel jLabel, jLabel2;
	private JButton jButton1, jButton2, jButton3, jButton4, jButton5, jButton6, jButton7;
	private JTextField jTextField, tfUniv, tfMajor, tfGrade, tfStuNum, tfStuName;
	private JScrollPane jScrollPane;
	private JTable jTable;
	private String[] colName; // �� ���� ����
	private Object[][] rowData; // �� ���� ����
	private NewStudentInfo info; // ���̾�α�
	private DefaultTableModel model;
	
	
	// â ��ġ�� ����� ���� ����
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
	
	
	
	public Manager() { // ����ȭ�� ������
		// ������Ʈ ��üȭ
		jFrame = new JFrame("���� ȭ��");
		jDialog = new JFrame();
		jPanels = new JPanel[13];
		for(int i=0; i<jPanels.length; i++) {
			jPanels[i] = new JPanel();
		}
		jLabel = new JLabel("�л� ���� ����");
		Font font = new Font("dialog", Font.BOLD, 22);
		jLabel.setFont(font);
		font = new Font("dialog", Font.BOLD, 12);
		jLabel2 = new JLabel("");
		jLabel2.setFont(font);
		font = new Font("HY�߰��", Font.PLAIN, 12);
		jButton7 = new JButton("logOut");
		jButton7.setFont(font);
		jButton1 = new JButton("�����Է�");
		jButton1.setFont(font);
		jButton2 = new JButton("�л��ű��Է�");
		jButton2.setFont(font);
		jTextField = new JTextField();
		new TextHint(jTextField, " �̸����� ���̺� �˻� ");
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
		jButton3 = new JButton("�˻�");
		jButton3.setFont(font);
		jButton4 = new JButton("������");
		jButton4.setFont(font);
		jButton5 = new JButton("����");
		jButton5.setFont(font);
		jButton6 = new JButton("�л��� �̹��� ����");
		jButton6.setFont(font);
		colName = new String[]{"�� ��", "�� ��", "�� ��", "�� ��", "�� ��"};
		displayAll(); // ���̺� �ҷ��� �� �ֱ�
		jScrollPane = new JScrollPane(jTable); // ��ũ���� ���� �� ���̺� ����
		jScrollPane.setPreferredSize(new Dimension(0, 300)); // ���α��� 300
		jScrollPane.getViewport().setBackground(Color.white); // ��� �Ͼ��
		
		
		// ������
		jFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}  
		});
		jButton1.addActionListener(new ActionListener() { // �����Է� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		jButton2.addActionListener(new ActionListener() { // ���Ի� �Է� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				info = new NewStudentInfo(jDialog, "�л��ű��Է�"); // ���Ի� �Է� ���̾�α� ����
				info.setModal(true); // ���� ȭ�� Ŭ�� ����
				info.setVisible(true); // ȭ�� ���
				while(info.isVisible()) { // ȭ�� �����ִ� ���� ���� 
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				newDisplay(); // ���ο� ���̺� ���
			}
		});
		jButton3.addActionListener(new ActionListener() { // �̸����� �й� �˻��� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				serchNumber(); // �й� �˻��� �޼ҵ�
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
		
		jButton4.addActionListener(new ActionListener() { // ������ Ȯ�ο� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfStuNum.getText().isEmpty()) { // �й��� ���õ��� �ʾ��� ��
					JOptionPane.showMessageDialog(null, "�������� ��ȸ�� �л��� ������ �ּ���");
				} else { // �й��� ���� �Ǿ��� ��
					StudentInfo info = new StudentInfo(tfStuNum.getText()); // �л� ������ â ����
					setWindowOpen(); // ȭ����ġ ����
					info.launchStudentInfo();
				}
			}
		});
		jButton5.addActionListener(new ActionListener() { // �л����� ������ ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfStuNum.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "������ �л� ������ ������ �ּ���");
				} else {
					int result = JOptionPane.showConfirmDialog(null, 
							tfStuName.getText()+" �л��� ������ ���� �Ͻðڽ��ϱ�?", 
							"Confirm", JOptionPane.YES_NO_OPTION); // �л����� ���� ���� Ȯ��
					if(result == JOptionPane.YES_OPTION) {
						delete();
						newDisplay();
					}
				}
			}
		});
		jButton6.addActionListener(new ActionListener() { // �л��� �̹��� ������ ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfStuNum.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "�̹����� ������ �л��� ������ �ּ���");
				} else {
					int result = JOptionPane.showConfirmDialog(null, 
							tfStuName.getText()+" �л��� �л��� �̹����� ���� �Ͻðڽ��ϱ�?", 
							"Confirm", JOptionPane.YES_NO_OPTION); // �л��� �̹��� ���� ���� Ȯ��
					if(result == JOptionPane.YES_OPTION) {
						makeImage();
					}
				}
			}
		});
		jButton7.addActionListener(new ActionListener() { // �α׾ƿ��� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.launchLogin();
				jFrame.dispose();
			}
		});
		jTable.addMouseListener(new MouseListener() { // ���콺 �۵� ������
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) { // ���콺�� ���� ���̺� ���� �л� ���� �ҷ�����
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
	} // ������ ��
	
	public void launchManager() { // ȭ�� ���� �� ���
		jLabel2.setText("�����ڵ� : " + MainGUI.getNumber());
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
		jPanels[11].add(new JLabel("�Ҽ�"));
		jPanels[11].add(tfUniv);
		jPanels[11].add(new JLabel("�а�"));
		jPanels[11].add(tfMajor);
		jPanels[11].add(new JLabel("�б�"));
		jPanels[11].add(tfGrade);
		jPanels[11].add(new JLabel("�й�"));
		jPanels[11].add(tfStuNum);
		jPanels[11].add(new JLabel("�̸�"));
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
	
	public void displayAll() { // ���̺� �� �ҷ��ͼ� �ֱ�
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.simpleInfoSelect(); // db���� �л����� ��������
		Comparator<DTO> comparator = new Comparator<DTO>() {
			@Override
			public int compare(DTO o1, DTO o2) {
				return o1.getStudentNum().compareTo(o2.getStudentNum());
			}
		};
		allData.sort(comparator);
		rowData = new Object[allData.size()][5];
		int i=0;
		for(DTO dto : allData) { // �� ���� ���� �ֱ�
			rowData[i][0] = dto.getUnivName();
			rowData[i][1] = dto.getMajorName();
			rowData[i][2] = dto.getGrade();
			rowData[i][3] = dto.getStudentNum();
			rowData[i][4] = dto.getStudentName();
			i++;
		}
		model = new DefaultTableModel(rowData, colName) { // �� ���� ����� �� ���� ������ ���̺� �����ϰ� �������� ���ϰ� ����
			public boolean isCellEditable(int row, int column) {return false;}
		};
		jTable = new JTable(model); // ���� ���̺� ����
		MyRenderer myRenderer = new MyRenderer(); // ���̺� ��� ���� ����
		jTable.setDefaultRenderer(Object.class, myRenderer); // ���̺� ��� ���� ����
		jTable.getTableHeader().setReorderingAllowed(false); // �� �� �巡�� ���� 
		jTable.getTableHeader().setResizingAllowed(false); // �� �� ������ ���� ����
		jTable.setRowSelectionAllowed(false); // �� ���� �� �巡�� ����
	}
	
	public void serchNumber() { // �̸����� �л����� �˻��� �޼ҵ�
		if(jTextField.getText().equals(" �̸����� ���̺� �˻� ") || jTextField.getText().equals("")) { // �ؽ�Ʈ �ʵ尡 ����ְų� �ؽ�Ʈ ��Ʈ�� �� ���ΰ�ħ
			DAO dao = new DAO();
			ArrayList<DTO> allData = dao.simpleInfoSelect(); // db���� ��ü �л����� ��������
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
		} else { // �ؽ�Ʈ �ʵ忡 �Է��� �̸��� ��ġ�ϴ� �л� ������ ��������
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
	public void newDisplay() { // ���̺� ���ΰ�ħ
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
	public void delete() { // �л����� ������ �޼ҵ�
		DAO dao = new DAO();
		String studentNumber = tfStuNum.getText();
		dao.StudentInfoDelete(studentNumber);
		// ������ �л� ���� ���
		tfUniv.setText("");
		tfMajor.setText("");
		tfGrade.setText("");
		tfStuNum.setText("");
		tfStuName.setText("");
		File file = new File("resources/"+studentNumber+".png"); // �й��� �ش��ϴ� ���� ����
		file.delete(); 
	}
	public void makeImage() { // �л��� �̹��� ������ �޼ҵ� 
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.BirthdaySerch(tfStuNum.getText());
		String BirthDay = null;
		for(DTO dto : allData) {
			BirthDay = dto.getBirthDay().substring(2, 4) + dto.getBirthDay().substring(5, 7) + dto.getBirthDay().substring(8, 10); // 1995-07-29
		}
		try {
			// ���ڵ� ����
			String code = Integer.toString(Integer.parseInt(tfStuNum.getText())*7+545728); // �й� ���ڵ�	
			Barcode barcode = BarcodeFactory.createCode128(code); // �й� ����
			barcode.setDrawingText(false); // �ؽ�Ʈ ����
			barcode.setBarHeight(50); // ����
			File file = new File("resources/"+tfStuNum.getText()+"BarCode.png"); // ����
			BarcodeImageHandler.savePNG(barcode, file);
			// �л��� �޸�
			BufferedImage img = new BufferedImage(324, 204, BufferedImage.TYPE_INT_ARGB); // �л��� ũ��
			Graphics2D g2d = (Graphics2D) img.getGraphics();
			g2d.setColor(Color.getHSBColor(0.5f, 0.4166f, 0.8f)); // ����
			g2d.fillRect(0, 0, 324, 204); // �׸� �׸���
			g2d.setColor(Color.GRAY); // ���׳�ƽ ��
			for(int i=19; i<68; i++) { // ���׳�ƽ �׸���
				g2d.drawLine(0, i, 324, i);
			}
			BufferedImage loadImage = ImageIO.read(new File("resources/"+tfStuNum.getText()+"BarCode.png")); // ���ڵ� �ҷ�����
			g2d.drawImage(loadImage, null, 100, 150); // ���ڵ� ����
			ImageIO.write(img, "png", new File("resources/"+tfStuNum.getText()+"CardBack.png")); // �޸� ����
			//�л��� �ո�
			g2d.setColor(Color.getHSBColor(0.5f, 0.4166f, 0.8f));
			g2d.fillRect(0, 0, 324, 204);
			g2d.setColor(Color.BLACK); // �ؽ�Ʈ ���� ����
			Font font = new Font("����", Font.BOLD, 20);
			g2d.setFont(font);
			g2d.drawString("��       ��       ��", 125, 30);
			font = new Font("����", Font.BOLD, 15);
			g2d.setFont(font);
			g2d.drawString("��     �� : "+tfMajor.getText(), 125, 55);
			g2d.drawString("��     �� : "+tfStuNum.getText(), 125, 83);
			g2d.drawString("��     �� : "+tfStuName.getText(), 125, 111);
			g2d.drawString("������� : "+BirthDay, 125, 139); // �ؽ�Ʈ ���� ��
			loadImage = ImageIO.read(new File("resources/ezen.png")); // ���� �ΰ� �ҷ�����
			g2d.drawImage(loadImage,null,10,150);
			loadImage = ImageIO.read(new File("resources/"+tfStuNum.getText()+".png")); // ���� �ҷ�����
			g2d.drawImage(loadImage,null,10,10);
			ImageIO.write(img, "png", new File("resources/"+tfStuNum.getText()+"CardFront.png")); // �ո� ����
			g2d.dispose();
			JOptionPane.showMessageDialog(null, "�̹��� ������ �Ϸ�Ǿ����ϴ�");
		} catch (Exception e) {
			e.getStackTrace();
			JOptionPane.showMessageDialog(null, "�̹��� ���� �� ���ܰ� �߻��߽��ϴ�", "Message", JOptionPane.ERROR_MESSAGE);
		}
	}
	public boolean tryParseInt(String value) {  // ������ Ȯ��
		try { 
			Integer.parseInt(value); 
			return true;
		} catch (NumberFormatException e) { 
			return false; 
		} 
	}
}