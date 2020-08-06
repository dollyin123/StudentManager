package StudentManager;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SheetsQuickstart implements Runnable { // 스프레드 시트 스레드 클래스
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	
	// 바코드 저장용 변수
	private static String barCode = null;
	public static String getBarCode() {
		return barCode;
	}
	public static void setBarCode(String barCode) {
		SheetsQuickstart.barCode = barCode;
	}

	
	
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	
	public void run() { // 스프레드 시트 스레드
		try {
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport(); // 구글 접속
			final String spreadsheetId = "1A0iCaN0OogjbRna1frjHC0HnK93eOP8AHyqtXZ0V0ls"; // 시트 주소
			final String range = "sheet!A1:B"; // 시트 범위
			Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)) // 구글 접속 및 인증
					.setApplicationName(APPLICATION_NAME)
					.build();
			while(true) { // 시트 반복 스캔
				ValueRange response = service.spreadsheets().values() // 시트 스캔
						.get(spreadsheetId, range)
						.execute();
				List<List<Object>> values = response.getValues(); // 리스트에 저장
				if (values == null || values.isEmpty()) {
					System.out.println("No data found.");
				} else {
					for (List row : values) { // row 리스트에 바코드값 삽입
						if(row.get(1).equals("1")) { // 플래그 값이 1일때
							values = Arrays.asList(Arrays.asList(new Object[] {"","0"})); // 시트에 작성된 바코드 값을 지우고 플래그 값 0으로 변경
							ValueRange body = new ValueRange()
									.setValues(values);
							service.spreadsheets().values().update(spreadsheetId, range, body)
							.setValueInputOption("RAW") // "RAW"는 평범한 문자열 의미
							.execute();
							setBarCode(row.get(0).toString()); // 읽어온 바코드 값 저장
						}
					}
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {}
	}
}