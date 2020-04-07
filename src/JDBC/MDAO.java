package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MDAO implements DAOInterface {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	MDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("클래스 로드 성공~!");

		} catch (ClassNotFoundException e) {
			System.out.println("클래스 로드 실패..");
		}
	}

	private boolean connect() {
		boolean cFalg = false;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			cFalg = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cFalg;
	}

	// 아이디 중복체크
	public boolean idCheck(String id) {
		boolean result = false;

		if (connect()) {
			try {
				stmt = conn.createStatement();
				String sql = "select * from member where id='" + id + "'";
				rs = stmt.executeQuery(sql);
				if (rs.next()) { // 가져온 객체가 있다면
					result = true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("DB 접속 오류..!");
			System.exit(0);
		}
		System.out.println("MDAO왔어?");
		return result;
	}

	@Override
	public void insert(String[] list) {
		

	}

}
