package StudentManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Student {
	// �������� ����
	private JFrame jFrame;
	private JPanel[] jPanels;
	private JLabel jLabel;
	private JButton jButton1, jButton2, jButton3, jButton4, jButton5;
	private JTextField tfUniv, tfMajor, tfgrade, tfStuNum, tfStuName;
	
	
	// �л�ȭ�� ������
	public Student() {
		jFrame = new JFrame("�л� ȭ��");
		jPanels = new JPanel[16];
		for(int i=0; i<jPanels.length; i++) {
			jPanels[i] = new JPanel();
		}
		DAO dao = new DAO();
		DTO dto = dao.simpleOneInfoSelect(Integer.toString(MainGUI.getNumber()));
		jLabel = new JLabel(dto.getStudentName() + " �� ȯ���մϴ�!");
		Font font = new Font("dialog", Font.BOLD, 16);
		jLabel.setFont(font);
		tfUniv = new JTextField(8);
		tfUniv.setEditable(false);
		tfUniv.setText(dto.getUnivName());
		tfMajor = new JTextField(8);
		tfMajor.setEditable(false);
		tfMajor.setText(dto.getMajorName());
		tfgrade = new JTextField(8);
		tfgrade.setEditable(false);
		tfgrade.setText(Integer.toString(dto.getGrade()));
		tfStuNum = new JTextField(8);
		tfStuNum.setEditable(false);
		tfStuNum.setText(dto.getStudentNum());
		tfStuName = new JTextField(8);
		tfStuName.setEditable(false);
		tfStuName.setText(dto.getStudentName());
		font = new Font("HY�߰��", Font.PLAIN, 12);
		jButton1 = new JButton("������");
		jButton1.setFont(font);
		jButton2 = new JButton("������û");
		jButton2.setFont(font);
		jButton3 = new JButton("�ð�ǥ");
		jButton3.setFont(font);
		jButton4 = new JButton("�������");
		jButton4.setFont(font);
		jButton5 = new JButton("logOut");
		jButton5.setFont(font);
		
		
		//������
		jFrame.addWindowListener(new WindowAdapter() { // â ������ �ý��� ����
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		jButton1.addActionListener(new ActionListener() { // �л� ������ Ȯ��
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentInfo info = new StudentInfo(Integer.toString(MainGUI.getNumber()));
				Manager.setWindowOpen();
				info.launchStudentInfo();
			}
		});
		jButton2.addActionListener(new ActionListener() { // ������û ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO ������û
			}
		});
		jButton3.addActionListener(new ActionListener() { // �ð�ǥ ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �ð�ǥ
			}
		});
		jButton4.addActionListener(new ActionListener() { // ���� ��� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				String folderPath = ""; // ���� ��ġ�� ���ڿ� ����

				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // ���� ���ñ� ����
				chooser.setCurrentDirectory(new File("")); // �⺻ ��ġ ����
				chooser.setAcceptAllFileFilterUsed(false); // ������� ����
				chooser.setDialogTitle("���ε� �̹��� ����"); // ���� ���ñ� ����
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // ���ϰ� �ּ� ������
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".png", "png"); // png�� �˻� ���� ����
				chooser.setFileFilter(filter); // ���� ����
				
				int returVal = chooser.showOpenDialog(null);
				if(returVal == JFileChooser.APPROVE_OPTION) { // ������ ���� �Ǿ��� ��
					folderPath = chooser.getSelectedFile().toString(); // ������ ���� ��ġ ����
					try { // �̹��� ���� �� ����
						BufferedImage img = new BufferedImage(101,130,BufferedImage.TYPE_INT_ARGB);
						Graphics2D g2d = (Graphics2D) img.getGraphics();
						BufferedImage loadImage;
						loadImage = ImageIO.read(new File(folderPath)); // �������� �ҷ�����
						g2d.drawImage(loadImage,null,0,0);
						ImageIO.write(img, "png", new File("resources/"+MainGUI.getNumber()+".png"));
						g2d.dispose();
						JOptionPane.showMessageDialog(null, "�̹����� ���ε� �Ǿ����ϴ�");
					} catch (Exception e1) {
						e1.getStackTrace();
						JOptionPane.showMessageDialog(null, "�̹��� ���ε� �� ���ܰ� �߻� �Ǿ����ϴ�", "Message", JOptionPane.ERROR_MESSAGE);
					}
				} else if(returVal == JFileChooser.CANCEL_OPTION) {
					folderPath = "";
					JOptionPane.showMessageDialog(null, "�̹��� ���ε尡 ��� �Ǿ����ϴ�");
				}
			}
		});
		jButton5.addActionListener(new ActionListener() { // �α׾ƿ� ��ư
			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.launchLogin();
				jFrame.dispose();
			}
		});
	}
	public void launchStudent() { // �л�ȭ�� ���� �� ���
		jPanels[14].setLayout(new GridBagLayout());
		jPanels[14].add(new JLabel(" "));
		jPanels[15].setLayout(new GridBagLayout());
		jPanels[15].add(jLabel);
		jPanels[1].setLayout(new BorderLayout());
		jPanels[1].add(jPanels[15], "Center");
		jPanels[1].add(jPanels[14], "South");

		jPanels[2].setLayout(new FlowLayout());
		jPanels[2].setBackground(Color.LIGHT_GRAY);
		jPanels[2].setForeground(Color.white);
		jPanels[2].add(new JLabel("�Ҽ� : "));
		jPanels[2].add(tfUniv);
		jPanels[3].setLayout(new FlowLayout());
		jPanels[3].setBackground(Color.LIGHT_GRAY);
		jPanels[3].setForeground(Color.white);
		jPanels[3].add(new JLabel("�а� : "));
		jPanels[3].add(tfMajor);
		jPanels[4].setLayout(new FlowLayout());
		jPanels[4].setBackground(Color.LIGHT_GRAY);
		jPanels[4].setForeground(Color.white);
		jPanels[4].add(new JLabel("�б� : "));
		jPanels[4].add(tfgrade);
		jPanels[5].setLayout(new FlowLayout());
		jPanels[5].setBackground(Color.LIGHT_GRAY);
		jPanels[5].setForeground(Color.white);
		jPanels[5].add(new JLabel("�й� : "));
		jPanels[5].add(tfStuNum);
		jPanels[6].setLayout(new FlowLayout());
		jPanels[6].setBackground(Color.LIGHT_GRAY);
		jPanels[6].setForeground(Color.white);
		jPanels[6].add(new JLabel("�̸� : "));
		jPanels[6].add(tfStuName);
		
		jPanels[7].setLayout(new GridLayout(5,1));
		jPanels[7].add(jPanels[2]);
		jPanels[7].add(jPanels[3]);
		jPanels[7].add(jPanels[4]);
		jPanels[7].add(jPanels[5]);
		jPanels[7].add(jPanels[6]);

		jPanels[8].setLayout(new GridLayout(4,1));
		jPanels[8].add(jButton1);
		jPanels[8].add(jButton2);
		jPanels[8].add(jButton3);
		jPanels[8].add(jButton4);

		jPanels[9].setLayout(new GridBagLayout());
		jPanels[9].add(jButton5);
		jPanels[10].setLayout(new BorderLayout());
		jPanels[10].add(new JLabel(" "), "North");
		jPanels[10].add(jPanels[9], "Center");

		jPanels[11].setLayout(new BorderLayout());
		jPanels[11].add(jPanels[1], "North");
		jPanels[11].add(jPanels[7], "West");
		jPanels[11].add(jPanels[8], "East");
		jPanels[11].add(jPanels[10], "South");

		jPanels[12].setLayout(new GridBagLayout());
		jPanels[12].add(new Label(" "));
		jPanels[13].setLayout(new GridBagLayout());
		jPanels[13].add(new Label(" "));
		jPanels[0].setLayout(new BorderLayout());
		jPanels[0].add(jPanels[11], "Center");
		jPanels[0].add(jPanels[12], "East");
		jPanels[0].add(jPanels[13], "West");

		jFrame.add(jPanels[0]);
		jFrame.pack();
		jFrame.setResizable(false);
		jFrame.setLocation(((jFrame.getToolkit().getScreenSize()).width - jFrame.getWidth())/2,
				((jFrame.getToolkit().getScreenSize()).height - jFrame.getHeight())/2);
		jFrame.setVisible(true);
	}
}
