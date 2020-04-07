package JDBC;

public class DAOCenter {
	// 싱글톤
	private static DAOCenter single = null;
	private DAOInterface DAOIF = null;

	private DAOCenter() {

	}

	public static DAOCenter getInstance() {
		if (single == null) {
			single = new DAOCenter();
		}
		return single;
	}

	public void insert(String s, String[] list) {
		if (s.equals("join")) {
			DAOIF = new MDAO();
			DAOIF.insert(list);
		}
	}

	// 아이디 중복체크
	public boolean idCheck(String id) {
		DAOIF = new MDAO();
		if (DAOIF.idCheck(id) == true) { // 중복 아이디가 있다면
			return true;
		}
		return false;
	}

}
