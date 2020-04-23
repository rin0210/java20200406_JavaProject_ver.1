package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BDAO implements DAOInterface {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	BDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean idCheck(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	// 예약 등록
	public boolean insert(Object o) {
		boolean result = false;

		BDTO b = (BDTO) o;

		try {
			String sql = "insert into booking values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, b.getReNum());
			
			if (b.getId() != null) {
				psmt.setString(2, b.getId()); // 회원
			} else {
				psmt.setString(2, null); // 비회원
			}
			
			psmt.setString(3, b.getName());
			psmt.setString(4, b.getRoom());
			psmt.setInt(5, b.getPeople());
			psmt.setString(6, b.getChkIn());
			psmt.setString(7, b.getChkOut());
			psmt.setInt(8, b.getNight());
			psmt.setInt(9, b.getPrice());

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

	// 전체 목록 가져오기
	public ArrayList<Object> getList() {
		BDTO r = null;
		ArrayList<Object> obList = new ArrayList<>();
		Object o = null;

		try {
			stmt = conn.createStatement();
			String sql = "select * from booking";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				r = new BDTO();
				r.setReNum(rs.getString("renum"));
				r.setId(rs.getString("id"));
				r.setName(rs.getString("name"));
				r.setRoom(rs.getString("room"));
				r.setPeople(rs.getInt("people"));
				r.setChkIn(rs.getString("chkin"));
				r.setChkOut(rs.getString("chkout"));
				r.setNight(rs.getInt("night"));
				r.setPrice(rs.getInt("price"));
				o = (Object) r;
				obList.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obList;
	}

}
