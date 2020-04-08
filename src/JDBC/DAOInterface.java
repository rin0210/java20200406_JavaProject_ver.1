package JDBC;

import java.util.ArrayList;

public interface DAOInterface {
	
	
	// 아이디 중복체크, 예약확인
	boolean idCheck(String id);

	boolean insert(Object o);
	
	public ArrayList<Object> getList();

}
