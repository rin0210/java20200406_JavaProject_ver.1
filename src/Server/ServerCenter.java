package Server;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import JDBC.DAOCenter;
import JDBC.MDTO;
import JDBC.RDTO;

public class ServerCenter {
	private ArrayList<SConnect> sList = new ArrayList<>();
	private DAOCenter dc = null;
	private MDTO mt = null;
	private RDTO rt = null;
	private ArrayList<MDTO> memberList = new ArrayList<>();
	private ArrayList<RDTO> getRoomList = new ArrayList<>();
	private ArrayList<Object> obList = null;
	private ArrayList<RDTO> myRoomList = new ArrayList<>();
	private ArrayList<String> myDaysList = new ArrayList<>();

	private String chkIn = null;
	private String chkOut = null;
	private String adult = null;
	private String kid = null;

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

		} else if ('^' == (msg.charAt(0))) { // 예약화면으로
			myReserve(withClient, msg);
		}
	}

	// msg분석하는 메서드
	private void myReserve(Socket withClient, String msg) {
		int ed = msg.indexOf(" ", 0);
//		String a = msg.substring(0, ed); // 조건 부분
		String info = msg.substring(ed + 1); // 예약정보 부분
		StringTokenizer st = new StringTokenizer(info);
		while (st.hasMoreTokens()) {
			for (int i = 0; i < st.countTokens(); i++) {
				chkIn = st.nextToken();
				chkOut = st.nextToken();
				adult = st.nextToken();
				kid = st.nextToken();
			}
		}
		chkDays("mine", chkIn, chkOut, null);
	}

	// 두 날짜 사이의 날짜 목록 구하기
	private void chkDays(String a, String in, String out, RDTO r) {
		int year = 0, month = 0, date = 0;
		int year_1 = 0, month_1 = 0, date_1 = 0;

		StringTokenizer st = new StringTokenizer(in, "-"); // 체크인 날짜 나눔
		while (st.hasMoreTokens()) {
			year = Integer.valueOf(st.nextToken());
			month = Integer.valueOf(st.nextToken());
			date = Integer.valueOf(st.nextToken());
		}
		st = new StringTokenizer(out, "-"); // 체크아웃 날짜 나눔
		while (st.hasMoreTokens()) {
			year_1 = Integer.valueOf(st.nextToken());
			month_1 = Integer.valueOf(st.nextToken());
			date_1 = Integer.valueOf(st.nextToken());
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		cal.set(year_1, month_1 - 1, date_1); // 체크아웃 날짜 셋팅
		String end = sdf.format(cal.getTime());
		cal.set(year, month - 1, date); // 체크인 날짜 셋팅
		String start = sdf.format(cal.getTime());

		if (a.equals("mine")) { // 내가 선택한 날짜
			while (true) {
				myDaysList.add(start);
				cal.add(Calendar.DATE, 1); // 1일씩 더해줌
				start = sdf.format(cal.getTime()); // 비교를 위해서 값 셋팅

				if (start.equals(end)) {
					break;
				}
			}
			reserveChk(); // 내 날짜목록 구하고 넘어가

		} else if (a.equals("already")) { // 다른 사람이 선택한 날짜
			ArrayList<String> alreadyList = new ArrayList<>(); // 계속 새로 만들어줘야해
			while (true) {
				alreadyList.add(start);
				cal.add(Calendar.DATE, 1);
				start = sdf.format(cal.getTime());

				if (start.equals(end)) {
					break;
				}
			}
			compare(alreadyList, r);
		}

		System.out.println("days :" + myDaysList);
	}

	// 날짜 비교
	private void compare(ArrayList<String> alreadyList, RDTO r) {
		System.out.println("서버센터 compare 왔니?");
		if (alreadyList.size() >= myDaysList.size()) {
			for (int i = 0; i < alreadyList.size(); i++) {
				if (alreadyList.contains(myDaysList.get(i))) { // 날짜가 중복되면
					break;
				} else {
					reserveGo(r);
				}
			}
		} else {
			for (int i = 0; i < myDaysList.size(); i++) {
				if (myDaysList.contains(alreadyList.get(i))) { // 날짜가 중복되면
					break;
				} else {
					reserveGo(r);
				}
			}
		}
	}

	// DB로부터 전체목록 가져와서 방 체크하기
	private void reserveChk() {
		obList = dc.getList("room");
		for (Object o : obList) {
			rt = (RDTO) o;
			getRoomList.add(rt);
		}
		System.out.println("getRoomList : " + getRoomList.size());

		int people = Integer.valueOf(adult) + Integer.valueOf(kid); // 전체인원:성인+아동

		for (RDTO r : getRoomList) {
			if (people <= r.getPeople()) { // 인원 수 체크
				if (r.getId() == null) { // 예약자가 없으면
					reserveGo(r);
				} else { // 예약자가 있으면
					chkDays("already", r.getChkIn(), r.getChkOut(), r); // 날짜체크할꺼야
				}
			}
		}
	}

	// 예약으로 넘어가기
	private void reserveGo(RDTO r) {
		myRoomList.add(r);
		
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
