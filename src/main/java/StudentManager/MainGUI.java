package StudentManager;

public class MainGUI { // ���� �޼ҵ� Ŭ����
	
	
	private static int number; // �й� �� ������ȣ ����� ����
	public static int getNumber() {
		return number;
	}
	public static void setNumber(int number) {
		MainGUI.number = number;
	}
	
	
   public static void main(String[] args) { // ���� ������ 
      Login login = new Login(); // �α��� Ŭ���� ��üȭ
      login.launchLogin(); // �α��� ȭ�� ���� �� ���
   }
}