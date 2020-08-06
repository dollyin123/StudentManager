package StudentManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewStudentInfo extends JDialog{ // ���Ի� �Է� ���̾�α� Ŭ����
	// �������� ����
	private JPanel[] jPanels;
	private JLabel jLabel;
	private JComboBox<String> jComboBox1, jComboBox2, ad1, ad2, ad3;
	private JTextField jTextGrade, jTextName, jTextSex, jTextPhoneNumber, jTextAddres, jTextBirthDay, jTextEntranceDay;
	private JButton jButton1, jButton2;
	private ActionListener ad1Listener, ad2Listener; // �޺��ڽ��� �׼� ������

	
	
	public NewStudentInfo(JFrame jFrame, String title) { // ���Ի� �Է� ������(ȭ�鱸�� �� ��� ����)
		super(jFrame, title);
		jPanels = new JPanel[19];
		for(int i=0; i<jPanels.length; i++) {
			jPanels[i] = new JPanel();
		}
		jLabel = new JLabel("                ���Ի� ���� �Է�                ");
		Font font = new Font("dialog", Font.BOLD, 22);
		jLabel.setFont(font);
		//�޺��ڽ�
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
		jTextGrade = new JTextField();
		new TextHint(jTextGrade, "ex) 2  ");
		jTextName = new JTextField();
		new TextHint(jTextName, "ex) ȫ�浿  ");
		jTextSex = new JTextField();
		new TextHint(jTextSex, "ex) ��  ");
		jTextPhoneNumber = new JTextField();
		new TextHint(jTextPhoneNumber, "ex) 010-1234-5678  ");
		jTextAddres = new JTextField();
		new TextHint(jTextAddres, "ex) �����154����           ");
		jTextBirthDay = new JTextField();
		new TextHint(jTextBirthDay, "ex) 1995/07/29  ");
		jTextEntranceDay = new JTextField();
		new TextHint(jTextEntranceDay, "ex) 2014/03/02  ");
		jButton1 = new JButton("�Է�");
		jButton2 = new JButton("���");
		jButton1.setFont(font);
		jButton2.setFont(font);
		
		jPanels[1].setLayout(new GridBagLayout());
		jPanels[1].add(jLabel);
		
		jPanels[2].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[2].add(new JLabel("��        �� : "));
		jPanels[2].add(jComboBox1);
		
		jPanels[3].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[3].add(new JLabel("��        �� : "));
		jPanels[3].add(jComboBox2);
		
		jPanels[4].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[4].add(new JLabel("��        �� : "));
		jPanels[4].add(jTextGrade);
		
		jPanels[5].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[5].add(new JLabel("��        �� : "));
		jPanels[5].add(jTextName);
		
		jPanels[6].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[6].add(new JLabel("��        �� : "));
		jPanels[6].add(jTextSex);
		
		jPanels[7].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[7].add(new JLabel("��ȭ��ȣ : "));
		jPanels[7].add(jTextPhoneNumber);
		
		jPanels[8].setLayout(new BorderLayout());
		jPanels[8].add(jPanels[17], "North");
		jPanels[8].add(jPanels[18], "Center");
		
		jPanels[17].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[17].add(new JLabel("��        �� : "));
		jPanels[17].add(ad1);
		jPanels[17].add(ad2);
		jPanels[17].add(ad3);
		
		jPanels[18].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[18].add(new JLabel("                    "));
		jPanels[18].add(jTextAddres); 
		
		jPanels[9].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[9].add(new JLabel("������� : "));
		jPanels[9].add(jTextBirthDay);
		
		jPanels[10].setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanels[10].add(new JLabel("���г⵵ : "));
		jPanels[10].add(jTextEntranceDay);
		
		jPanels[11].setLayout(new GridBagLayout());
		jPanels[11].add(jButton1);
		jPanels[11].add(new JLabel(" "));
		jPanels[11].add(jButton2);
		
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
		setLocation(((getToolkit().getScreenSize()).width - getWidth())/2, 
				((getToolkit().getScreenSize()).height - getHeight())/2);
		
		
		// ������
		addWindowListener(new WindowAdapter() { // â ������ �ش� â�� ����
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 dispose();
	         }  
	      });
		jButton1.addActionListener(new ActionListener() { // ���Ի� ���� �Է� Ȯ�� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "�Է��Ͻ� ������ �Է� �Ͻðڽ��ϱ�?");
				if(result == JOptionPane.YES_OPTION) {
					inputNewStudent();
					dispose();
				}
			}
		});
		jButton2.addActionListener(new ActionListener() { // ��� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		jComboBox1.addActionListener(new ActionListener() { // �Ҽӿ� �޺��ڽ� ������
			@Override
			public void actionPerformed(ActionEvent e) {
				jComboBox2.removeAllItems();
				bringMajor(jComboBox1.getSelectedItem().toString());
			}
		});
		ad1Listener = new ActionListener() { // �ּҿ� �޺��ڽ� ������
			@Override
			public void actionPerformed(ActionEvent e) {
				ad2Select(ad1.getSelectedItem().toString());
			}
		};
		ad2Listener = new ActionListener() { // �ּҿ� �޺��ڽ� ������ 2
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ad2.getSelectedItem() != null) {
					ad3Select(ad2.getSelectedItem().toString());
				}
			}
		};
		displayCombo(); // �Ҽ� ��� �޼ҵ�
		ad1Select(); // �ּ� ��� �޼ҵ�
	}
	public void displayCombo() { // �Ҽ� ��� �޼ҵ�
		jComboBox1.removeAllItems();
		jComboBox2.removeAllItems();
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.comboSelect();
		for(DTO dto : allData) {
			jComboBox1.addItem(dto.getUnivName());
		}
	}
	public void bringMajor(String univ) { // �а� ��� �޼ҵ�
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.combo2Select(univ);
		for(DTO dto : allData) {
			jComboBox2.addItem(dto.getMajorName());
		}
	}
	public void inputNewStudent() { // ���Ի� ���� db�Է� �޼ҵ�
		DAO dao = new DAO();
		String listNumber = Integer.toString(dao.listNumber());
		String univName = jComboBox1.getSelectedItem().toString();
		String majorName = jComboBox2.getSelectedItem().toString();
		String entranceDay = jTextEntranceDay.getText();
		String studentNumber = "12" + entranceDay.substring(2, 4) + String.format("%04d", Integer.parseInt(listNumber)); // 2020-03-02
		String grade = jTextGrade.getText();
		String name = jTextName.getText();
		String sex = jTextSex.getText();
		String phoneNumber = jTextPhoneNumber.getText();
		
		String addres2 = "";
		if(ad2.getSelectedItem() != null) {
			addres2 = " " + ad2.getSelectedItem().toString();
		}
		String addres3 = "";
		if(ad3.getSelectedItem() != null) {
			addres3 = " " + ad3.getSelectedItem().toString();
		}
		String addres4 = "";
		if(jTextAddres.getText().equals("ex) �����154����           ")) {
			addres4 = "";
		} else {
			addres4 = " " + jTextAddres.getText();
		}
		String address = ad1.getSelectedItem().toString() + addres2 + addres3 + addres4;
		
		String birthDay = jTextBirthDay.getText();
		dao.insert(univName, majorName, studentNumber, grade, name, sex, phoneNumber, address, birthDay, entranceDay);
		try { // �л� ����Ʈ ���� ����
		BufferedImage img = new BufferedImage(101,130,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) img.getGraphics();
		BufferedImage loadImage;
		loadImage = ImageIO.read(new File("resources/samplePicture.png"));
		g2d.drawImage(loadImage,null,0,0);
		ImageIO.write(img, "png", new File("resources/"+studentNumber+".png"));
		g2d.dispose();
		} catch (Exception e) {}
	}
	public void ad1Select() { // �ּ� ��� �޼ҵ�
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
	public void ad2Select(String ad) { // �ּ���� �޼ҵ� 2
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
	public void ad3Select(String ad) { // �ּ���� �޼ҵ� 3
		ad3.removeAllItems();
		DAO dao = new DAO();
		ArrayList<DTO> allData = dao.ad3Serch(ad);
		for(DTO dto : allData) {
			ad3.addItem(dto.getAd3());
		}
	}
}
