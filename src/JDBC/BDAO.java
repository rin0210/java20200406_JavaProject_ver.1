package JDBC;

import java.sql.Connection;
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

	@Override
	public boolean insert(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	// 전체 목록 가져오기(방이름,정원,가격)
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
				r.setId(rs.getString("id"));
				r.setRoom(rs.getString("room"));
				r.setPeople(rs.getInt("people"));
				r.setChkIn(rs.getString("chkin"));
				r.setChkOut(rs.getString("chkout"));
				o = (Object) r;
				obList.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obList;
	}

}
