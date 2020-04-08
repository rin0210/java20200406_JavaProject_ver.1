package Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import JDBC.DAOCenter;
import JDBC.MDTO;

public class ServerCenter {
	private ArrayList<SConnect> sList = new ArrayList<>();
	private DAOCenter dc = null;
	private MDTO mt = null;
	private ArrayList<MDTO> memberList = new ArrayList<>();
	private ArrayList<Object> obList = null;

	ServerCenter() {
		dc = DAOCenter.getInstance();
	}

	public void addList(SConnect s) {
		this.sList.add(s);
	}

	public void receive(Socket withClient, String msg) {
		if ('*' == (msg.charAt(0))) { // 중복체크로
			idCheck(withClient, msg);

		} else if ('/' == (msg.charAt(0))) { // 회원가입으로
			joinGo(withClient, msg);

		} else if ('>' == (msg.charAt(0))) { // 로그인으로
			loginGo(withClient, msg);
		}
	}

	// 로그인
	private void loginGo(Socket withClient, String msg) {
		int fs = msg.indexOf(" ", 0);
		int ed = msg.lastIndexOf(" ");
//		String a = msg.substring(0, fs); // 조건 부분
		String id = msg.substring(fs + 1, ed); // 아이디 부분
		String pwd = msg.substring(ed + 1); // 비밀번호 부분

		if (loginChk(id, pwd)) {
			String m = "/login yes"; // 로그인 성공
			for (SConnect s : sList) {
				s.send(withClient, m);
				s.streamSet(withClient, id);
			}
		} else {
			String m = "/login no"; // 로그인 실패
			for (SConnect s : sList) {
				s.send(withClient, m);
			}
		}
	}

	// 로그인 체크
	private boolean loginChk(String id, String pwd) {
		this.obList = dc.getList("member"); // 회원목록 가져오기
		for (Object o : obList) {
			mt = (MDTO) o;
			memberList.add(mt);
		}
		for (MDTO m : memberList) {
			if (m.getId().equals(id) && m.getPwd().equals(pwd)) {
				return true;
			}
		}
		return false;
	}

	// 회원가입
	private void joinGo(Socket withClient, String msg) {
		System.out.println(msg);

		int ed = msg.indexOf(" ", 0);
//		String a = msg.substring(0, ed); // 조건 부분
		String info = msg.substring(ed + 1); // 회원정보 부분

		mt = new MDTO();
		StringTokenizer st = new StringTokenizer(info, " ");
//		System.out.println(st.countTokens());
		while (st.hasMoreTokens()) {
			for (int i = 0; i < st.countTokens(); i++) {
				mt.setId(st.nextToken());
				mt.setPwd(st.nextToken());
				mt.setName(st.nextToken());
				mt.setTel(st.nextToken());
				mt.setAddr(st.nextToken());
			}
		}
		dc.insert("member", (Object) mt);

		String m = "/join memberOk";

		for (SConnect s : sList) {
			s.send(withClient, m);
		}
	}

	// 아이디 중복체크
	private void idCheck(Socket withClient, String msg) {
		int ed = msg.indexOf(" ", 0);
		String id = msg.substring(ed + 1); // 아이디 부분

		String m;
		if (dc.idCheck(id) == false) { // 중복 아이디가 있다면
			m = "/join no";
		} else {
			m = "/join yes";
		}

		for (SConnect s : sList) {
			s.send(withClient, m);
		}
	}
}
