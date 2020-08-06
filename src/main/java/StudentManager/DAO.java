package StudentManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
	String  driver = "oracle.jdbc.driver.OracleDriver";
	String  url = "jdbc:oracle:thin:@localhost:1521:xe";
	String  userid = "scott";
	String  passwd = "tiger";
	public DAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//강의 및 성적 조회
	public ArrayList<DTO> lectureSelect(String studentNumber){
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet nrs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT * FROM score_table WHERE student_num='"+studentNumber+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DTO dto = new DTO();
				dto.setLectureCode(rs.getString("LECTURE_CODE"));
				String code = rs.getString("LECTURE_CODE");
				query = "SELECT * FROM lecture_table WHERE lecture_code='"+code+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setLectureName(nrs.getString("LECTURE_NAME"));
				}
				dto.setScore(rs.getString("SCORE"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(nrs != null) nrs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	} 

	//전체학생 리스트용
	public ArrayList<DTO> simpleInfoSelect() {
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet nrs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT * FROM student_info";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DTO dto = new DTO();
				String univcode = rs.getString("UNIV_CODE");
				query = "SELECT * FROM univ_table WHERE UNIV_CODE='"+univcode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setUnivName(nrs.getString("UNIV_NAME"));
				}
				String majorCode = rs.getString("MAJOR_CODE");
				query = "SELECT * FROM major_table WHERE MAJOR_CODE='"+majorCode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setMajorName(nrs.getString("MAJOR_NAME"));
				}
				dto.setGrade(rs.getInt("GRADE"));
				dto.setStudentNum(rs.getString("STUDENT_NUM"));
				dto.setStudentName(rs.getString("STUDENT_NAME"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(nrs != null) nrs.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//end finally
		return list;
	} //end simpleInfoSelect()

	//메인화면 학번검색용
	public ArrayList<DTO> studentNameSerch(String name) { 
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet nrs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT * FROM student_info WHERE student_name = '"+name+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DTO dto = new DTO();
				String univcode = rs.getString("UNIV_CODE");
				query = "SELECT * FROM univ_table WHERE UNIV_CODE='"+univcode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setUnivName(nrs.getString("UNIV_NAME"));
				}
				String majorCode = rs.getString("MAJOR_CODE");
				query = "SELECT * FROM major_table WHERE MAJOR_CODE='"+majorCode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setMajorName(nrs.getString("MAJOR_NAME"));
				}
				dto.setGrade(rs.getInt("GRADE"));
				dto.setStudentNum(rs.getString("STUDENT_NUM"));
				dto.setStudentName(rs.getString("STUDENT_NAME"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(nrs != null) nrs.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//end finally
		return list;
	} // end studentNumSerch()

	//콤보박스1 채우기용
	public ArrayList<DTO> comboSelect() { 
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT univ_name FROM univ_table";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DTO dto = new DTO();
				dto.setUnivName(rs.getString("univ_name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 콤보박스2 채우기용
	public ArrayList<DTO> combo2Select(String univ) {
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet nrs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT univ_code FROM univ_table WHERE univ_name = '"+univ+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String univCode = rs.getString("univ_code");
				query = "SELECT major_name FROM major_table WHERE univ_code = '"+univCode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					DTO dto = new DTO();
					dto.setMajorName(nrs.getString("major_name"));
					list.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(nrs != null) nrs.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}


	// 교수용 로그인
	public int professorLogin(String professorNumber) {
		int numCheck=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT COUNT(*) FROM professor_info WHERE professor_num LIKE '"+professorNumber+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				numCheck = rs.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return numCheck;
	}


	// 학생용 로그인
	public int studentLogin(String studentNumber) {
		int numCheck=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT COUNT(*) FROM student_info WHERE student_num LIKE '"+studentNumber+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				numCheck = rs.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return numCheck;
	}

	public int listNumber() { // 시퀀스 번호 구하는 용도
		int numCheck=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT last_number FROM user_sequences WHERE sequence_name = 'SQN1'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				numCheck = rs.getInt("last_number");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return numCheck;
	}
	
	//신규정보 입력
	public void insert(String univName, String majorName, String studentNumber, 
			String grade, String name, String sex, String phoneNumber, 
			String address, String birthDay, String entranceDay) {
		Connection conn = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		String univCode = null;
		String majorCode = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT univ_code FROM univ_table WHERE univ_name = '"+univName+"'"; 
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				univCode = rs.getString("univ_code");
			}
			query = "SELECT major_code FROM major_table WHERE major_name = '"+majorName+"'"; 
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				majorCode = rs.getString("major_code");
			}
			query = "INSERT INTO STUDENT_INFO VALUES (sqn1.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, univCode);
			pstmt.setString(2, majorCode);
			pstmt.setString(3, studentNumber);
			pstmt.setInt(4, Integer.parseInt(grade));
			pstmt.setString(5, name);
			pstmt.setString(6, sex);
			pstmt.setString(7, phoneNumber);
			pstmt.setString(8, address);
			pstmt.setString(9, birthDay);
			pstmt.setString(10, entranceDay);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace(); 
			}
		}
	} 

	// 학생정보 삭제
	public void StudentInfoDelete(String studentNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String sql = "DELETE  FROM student_info WHERE student_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentNumber);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null)  pstmt.close();
				if(conn!=null)      conn.close();
			}catch(SQLException e) {e.printStackTrace();}
		}
	}

	// 생일검색
	public ArrayList<DTO> BirthdaySerch(String studentNumber) { 
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT birthday FROM student_info WHERE student_num = '"+studentNumber+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DTO dto = new DTO();
				dto.setBirthDay(rs.getString("birthday"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 전체정보 검색
	public DTO AllInfoSelect(String studentNumber) { 
		DTO dto = new DTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet nrs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT * FROM student_info WHERE student_num = '"+studentNumber+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String univcode = rs.getString("UNIV_CODE");
				query = "SELECT UNIV_NAME FROM univ_table WHERE UNIV_CODE='"+univcode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setUnivName(nrs.getString("UNIV_NAME"));
				}
				String majorCode = rs.getString("MAJOR_CODE");
				query = "SELECT MAJOR_NAME FROM major_table WHERE MAJOR_CODE='"+majorCode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setMajorName(nrs.getString("MAJOR_NAME"));
				}
				dto.setGrade(rs.getInt("GRADE"));
				dto.setStudentNum(rs.getString("STUDENT_NUM"));
				dto.setStudentName(rs.getString("STUDENT_NAME"));
				dto.setSex(rs.getString("SEX"));
				dto.setBirthDay(rs.getString("BIRTHDAY"));
				dto.setEntranceDay(rs.getString("ENTRANCE_DAY"));
				dto.setPhoneNum(rs.getString("PHONE_NUM"));
				dto.setAddress(rs.getString("ADDRES"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(nrs != null) rs.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	//교수용 학생정보 수정
	public void updateAtManager(String studentNumber, String univName, String majorName, String grade) {
		Connection conn = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		String univCode = null;
		String majorCode = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT univ_code FROM univ_table WHERE univ_name = '"+univName+"'"; 
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				univCode = rs.getString("univ_code");
			}
			query = "SELECT major_code FROM major_table WHERE major_name = '"+majorName+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				majorCode = rs.getString("major_code");
			}
			query = "UPDATE student_info SET univ_code = ?, major_code = ?, grade = ?  WHERE student_num = '"+studentNumber+"'";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, univCode);
			pstmt.setString(2, majorCode);
			pstmt.setInt(3, Integer.parseInt(grade));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace(); 
			}
		}
	} 
	
	// 학생용 정보수정
	public void updateAtStudent(String studentNumber, String phoneNum, String addres) {
		Connection conn = null;
		PreparedStatement  pstmt = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "UPDATE student_info SET phone_num = ?, addres = ? WHERE student_num = '"+studentNumber+"'"; 
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, phoneNum);
			pstmt.setString(2, addres);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	// 한사람 정보 조회
	public DTO simpleOneInfoSelect(String StudentNumber) {
		DTO dto = new DTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet nrs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT * FROM student_info WHERE student_num = '"+StudentNumber+"'";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String univcode = rs.getString("UNIV_CODE");
				query = "SELECT * FROM univ_table WHERE UNIV_CODE='"+univcode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setUnivName(nrs.getString("UNIV_NAME"));
				}
				String majorCode = rs.getString("MAJOR_CODE");
				query = "SELECT * FROM major_table WHERE MAJOR_CODE='"+majorCode+"'";
				pstmt = conn.prepareStatement(query);
				nrs = pstmt.executeQuery();
				while(nrs.next()) {
					dto.setMajorName(nrs.getString("MAJOR_NAME"));
				}
				dto.setGrade(rs.getInt("GRADE"));
				dto.setStudentNum(rs.getString("STUDENT_NUM"));
				dto.setStudentName(rs.getString("STUDENT_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(nrs != null) nrs.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//end finally
		return dto;
	} //end simpleInfoSelect()
	
	// 주소검색1
	public ArrayList<DTO> ad1Serch() { 
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT si_do FROM letter_db GROUP BY si_do HAVING COUNT(*) > 1";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DTO dto = new DTO();
				dto.setAd1(rs.getString("si_do"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 주소검색2
	public ArrayList<DTO> ad2Serch(String ad) { 
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT si_gun_gu FROM letter_db WHERE si_do = '"+ad+"' GROUP BY si_gun_gu HAVING COUNT(*) > 1";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DTO dto = new DTO();
				dto.setAd2(rs.getString("si_gun_gu"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 주소검색3
	public ArrayList<DTO> ad3Serch(String ad) { 
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT oop_mien  FROM letter_db WHERE si_gun_gu = '"+ad+"' GROUP BY oop_mien HAVING COUNT(*) > 1";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DTO dto = new DTO();
				dto.setAd3(rs.getString("oop_mien"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
