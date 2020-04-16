package Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import JDBC.BDTO;
import JDBC.DAOCenter;
import JDBC.MDTO;
import JDBC.RDTO;

public class ServerCenter {
	private ArrayList<SConnect> sList = new ArrayList<>();
	private DAOCenter dc = null;
	private MDTO mt = null;
	private RDTO rt = null;
	private BDTO bt = null;

	private ArrayList<Object> obList = null;
//	private ArrayList<String> myDaysList = new ArrayList<>();
//	private ArrayList<String[]> roomList = new ArrayList<>();

//	private String chkIn = null;
//	private String chkOut = null;
//	private String adult = null;
//	private String kid = null;

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

		} else if ('^' == (msg.charAt(0))) { // 방목록 가져올수 있도록
			getDBList(withClient, msg);
		}
	}

	// DB로부터 방목록 가져옴
	private void getDBList(Socket withClient, String msg) {
//		ArrayList<RDTO> getRoomList = new ArrayList<>();
//		ArrayList<BDTO> getBookingList = new ArrayList<>();

		if (msg.indexOf("callRoom") > -1) { // 방목록 불러옴
			obList = dc.getList("room");
//			for (Object o : obList) {
//				rt = (RDTO) o;
//				getRoomList.add(rt);
//			}

			for (SConnect s : sList) {
				s.sendObject(withClient, obList);
			}
			System.out.println(obList);

		} else if (msg.indexOf("callBooking") > -1) { // 예약목록 불러옴
			obList = dc.getList("booking");
//			for (Object o : obList) {
//				bt = (BDTO) o;
//				getBookingList.add(bt);
//			}
//
			for (SConnect s : sList) {
				s.sendObject(withClient, obList);
			}
			System.out.println(obList);
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
			String m = "loginYes"; // 로그인 성공
			for (SConnect s : sList) {
				s.send(withClient, m);
				s.streamSet(withClient, id);
			}
		} else {
			String m = "loginNo"; // 로그인 실패
			for (SConnect s : sList) {
				s.send(withClient, m);
			}
		}
	}

	// 로그인 체크
	private boolean loginChk(String id, String pwd) {
		ArrayList<MDTO> memberList = new ArrayList<>();

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
		StringTokenizer st = new StringTokenizer(info); // 기본이 띄어쓰기
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

		String m = "memberOk";

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
			m = "idNo";
		} else {
			m = "idYes";
		}

		for (SConnect s : sList) {
			s.send(withClient, m);
		}
	}
}
