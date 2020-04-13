package JDBC;

import java.util.ArrayList;

public interface DAOInterface {
	
	
	// 아이디 중복체크, 예약확인
	boolean idCheck(String id);

	boolean insert(Object o);
	
	// 전체 목록 가져오기
	public ArrayList<Object> getList();

}
