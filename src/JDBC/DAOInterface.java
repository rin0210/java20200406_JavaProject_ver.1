package JDBC;

public interface DAOInterface {
	
	public void insert(String[] list);
	
	// 아이디 중복체크
	public boolean idCheck(String id);

}
