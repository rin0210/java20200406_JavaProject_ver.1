package Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
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
	private String reNum = null;

	ServerCenter() {
		dc = DAOCenter.getInstance();
	}

	public void addList(SConnect s) {
		this.sList.add(s);
	}

	public void receive(Socket withClient, String msg, String id) {
		if (msg.indexOf("/idChk") > -1) { // 중복체크로
			idCheck(withClient, msg);

		} else if (msg.indexOf("/join") > -1) { // 회원가입으로
			joinGo(withClient, msg);

		} else if (msg.indexOf("/login") > -1) { // 로그인으로
			loginGo(withClient, msg);

		} else if (msg.indexOf("/call") > -1) { // 방목록 가져오기
			getDBList(withClient, msg);

		} else if (msg.indexOf("/getMyInfo") > -1) { // 내정보 가져오기
			getMyInfo(withClient, msg, id);

		} else if (msg.indexOf("/reservation") > -1) { // 예약저장으로
			setReserve(withClient, msg, id);

		} else if (msg.indexOf("/logout") > -1) { // 로그아웃으로
			logoutGo(withClient, msg);

		} else if (msg.indexOf("/myBooking") > -1) { // 예약확인으로
			getMyBooking(withClient, msg, id);
		}
	}

	// 예약확인하기 위함
	private void getMyBooking(Socket withClient, String msg, String id) {
		ArrayList<BDTO> getBookingList = new ArrayList<>();
		ArrayList<BDTO> myBookingList = new ArrayList<>();
		this.obList = dc.getList("booking"); // 예약자 목록 불러오고

		System.out.println("예약자 목록 가져왔어? " + obList);

		for (SConnect s : sList) {
			s.sendObject(withClient, obList);
		}
	}

	// 로그아웃
	private void logoutGo(Socket withClient, String msg) {
		for (SConnect s : sList) {
			s.logout(withClient, msg);
		}
	}

	// 랜덤한 예약번호 만들기
	private String setRandomNum() {
		Random r = new Random();
		reNum = "";

		for (int i = 0; i < 10; i++) {
			reNum = reNum + String.valueOf(r.nextInt(8) + 1);
		}

		return reNum;
	}

	// 예약 저장
	private void setReserve(Socket withClient, String msg, String id) {
		int ed = msg.indexOf(" ", 0);
//		String a = msg.substring(0, ed); // 조건 부분
		String info = msg.substring(ed + 1); // 회원정보 부분

		bt = new BDTO();
		bt.setReNum(setRandomNum());

		if (id != null) {
			bt.setId(id); // 회원일 경우
		}

		StringTokenizer st = new StringTokenizer(info); // 기본이 띄어쓰기
		while (st.hasMoreTokens()) {
			for (int i = 0; i < st.countTokens(); i++) {
				bt.setName(st.nextToken());
				bt.setRoom(st.nextToken());
				bt.setPeople(Integer.valueOf(st.nextToken()));
				bt.setChkIn(st.nextToken());
				bt.setChkOut(st.nextToken());
				bt.setNight(Integer.valueOf(st.nextToken()));
				bt.setPrice(Integer.valueOf(st.nextToken()));
			}
		}
		dc.insert("booking", (Object) bt);

		String m = "reservationOk " + reNum; // 예약번호 보내기

		for (SConnect s : sList) {
			s.send(withClient, m);
		}
		System.out.println("m: " + m);
	}

	// 내정보 가져오기
	private void getMyInfo(Socket withClient, String msg, String id) {
		ArrayList<MDTO> memberList = new ArrayList<>();
		String[] myInfo = null;
		this.obList = dc.getList("member"); // 회원목록 가져오기
		for (Object o : obList) {
			mt = (MDTO) o;
			memberList.add(mt);
		}
		for (MDTO m : memberList) {
			if (m.getId().equals(id)) { // 아이디 체크 후
				myInfo = m.getArray(); // 정보 저장
				break;
			}
		}
//		String m = myInfo+"";
		for (SConnect s : sList) {
			s.sendObject(withClient, myInfo);
		}
		System.out.println("myInfo : " + myInfo);

	}

	// DB로부터 방목록 가져옴
	private void getDBList(Socket withClient, String msg) {

		if (msg.indexOf("callRoom") > -1) { // 방목록 불러옴
			this.obList = dc.getList("room");

			for (SConnect s : sList) {
				s.sendObject(withClient, obList);
			}
			System.out.println(obList);

		} else if (msg.indexOf("callBooking") > -1) { // 예약목록 불러옴
			this.obList = dc.getList("booking");

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

		String m = null;
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
