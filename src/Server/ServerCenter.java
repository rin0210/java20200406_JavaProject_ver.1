package Server;

import java.net.Socket;
import java.util.ArrayList;

import JDBC.DAOCenter;
import JDBC.MDTO;

public class ServerCenter {
	private ArrayList<SConnect> sList = new ArrayList<>();
	private DAOCenter dc = null;
	private MDTO mt = null;
	private boolean check = false; // 아이디 중복체크 했는지

	public ServerCenter() {
		dc = DAOCenter.getInstance();
	}

	public void addList(SConnect s) {
		this.sList.add(s);
	}

	public void receive(Socket withClient, String msg) {
		if ('*' == (msg.charAt(0))) { // 중복체크로
			check = true;
			idCheck(withClient, msg);
		} else if ('/' == (msg.charAt(0))) { // 회원가입으로
			joinGo(msg);
		} else if ('>' == (msg.charAt(0))) { // 로그인으로
			loginGo(msg);
		}
	}

	// 로그인
	private void loginGo(String msg) {

	}

	// 회원가입
	private void joinGo(String msg) {
		int ed = msg.indexOf(" ", 0);
		String a = msg.substring(0, ed); // 조건 부분
		String b = msg.substring(ed + 1); // 회원정보 부분
		mt = new MDTO();

		if (a.indexOf("id") == 0) {
			mt.setId(b);
		} else if (a.indexOf("pwd") == 0) {
		} else if (a.indexOf("name") == 0) {
		} else if (a.indexOf("tel") == 0) {
		} else if (a.indexOf("addr") == 0) {
		}

//		dc.insert("join", );
	}

	// 아이디 중복체크
	private void idCheck(Socket withClient, String msg) {
		String m;
		if (dc.idCheck(msg)) { // 중복 아이디가 있다면
			m = "no";
		} else {
			m = "yes";
		}

		for (SConnect s : sList) {
			s.send(withClient,m);
		}
		
		check = false;

	}
}
