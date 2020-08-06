package StudentManager;

public class MainGUI { // 메인 메소드 클래스
	
	
	private static int number; // 학번 및 교수번호 저장용 변수
	public static int getNumber() {
		return number;
	}
	public static void setNumber(int number) {
		MainGUI.number = number;
	}
	
	
   public static void main(String[] args) { // 메인 스레드 
      Login login = new Login(); // 로그인 클래스 객체화
      login.launchLogin(); // 로그인 화면 구성 및 출력
   }
}