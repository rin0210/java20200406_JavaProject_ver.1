package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MDAO implements DAOInterface {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	MDAO(Connection conn) {
		this.conn = conn;
	}

	// 전체 목록 가져오기
	public ArrayList<Object> getList() {
		MDTO m = null;
		ArrayList<Object> obList = new ArrayList<>();
		Object o = null;

		try {
			stmt = conn.createStatement();
			String sql = "select * from member";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				m = new MDTO();
				m.setId(rs.getString("id"));
				m.setPwd(rs.getString("pwd"));
				m.setName(rs.getString("name"));
				m.setTel(rs.getString("tel"));
				m.setAddr(rs.getString("addr"));
				m.setPoint(rs.getInt("point"));
				m.setLev(rs.getInt("lev"));

				o = (Object) m;
				obList.add(o);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obList;
	}

	// 아이디 중복체크
	public boolean idCheck(String id) {
		boolean result = true;

		try {
			stmt = conn.createStatement();
			String sql = "select * from member where id='" + id + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) { // 가져온 객체가 있다면
				result = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 회원 등록
	@Override
	public boolean insert(Object o) {
		boolean result = false;
//		System.out.println("MDAO insert 왔어?");

		MDTO m = (MDTO) o;

//		System.out.println("DB넣을꺼야?");
		try {
			String sql = "insert into member values(?,?,?,?,?,0,2)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, m.getId());
			psmt.setString(2, m.getPwd());
			psmt.setString(3, m.getName());
			psmt.setString(4, m.getTel());
			psmt.setString(5, m.getAddr());

			int r = psmt.executeUpdate();

			if (r > 0) {
				result = true;
			}
			psmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
