package Swing;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import JDBC.RDTO;
import Server.SConnect;

public class Test {

	private ArrayList<String> myDaysList = new ArrayList<>();
	private ArrayList<String[]> roomList = new ArrayList<>();

	private String chkIn = null;
	private String chkOut = null;
	private String adult = null;
	private String kid = null;

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
		chkDays(withClient, "mine", chkIn, chkOut, null);
	}

	// 두 날짜 사이의 날짜 목록 구하기
	private void chkDays(Socket withClient, String a, String in, String out, RDTO r) {
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
			reserveChk(withClient); // 내 날짜목록 구하고 넘어가

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
	}

	// 날짜 비교
	private void compare(ArrayList<String> alreadyList, RDTO r) {
		if (alreadyList.size() >= myDaysList.size()) {
			for (int i = 0; i < alreadyList.size(); i++) {
				if (alreadyList.contains(myDaysList.get(i))) { // 날짜가 중복되면
					break;
				} else {
					addList(r);
				}
			}
		} else {
			for (int i = 0; i < myDaysList.size(); i++) {
				if (myDaysList.contains(alreadyList.get(i))) { // 날짜가 중복되면
					break;
				} else {
					addList(r);
				}
			}
		}
	}

	// DB로부터 전체목록 가져와서 방 체크하기
	private void reserveChk(Socket withClient) {
		ArrayList<RDTO> getRoomList = new ArrayList<>();

//		obList = dc.getList("room");
//		for (Object o : obList) {
//			rt = (RDTO) o;
//			getRoomList.add(rt);
//		}
		System.out.println("getRoomList : " + getRoomList.size());

//		for (SConnect s : sList) {
//			s.sendObject(withClient, getRoomList);
//		}

		int people = Integer.valueOf(adult) + Integer.valueOf(kid); // 전체인원:성인+아동

//		for (RDTO r : getRoomList) {
//			if (people <= r.getPeople()) { // 인원 수 체크
//				if (r.getId() == null) { // 예약자가 없으면
//					addList(r);
//				} else { // 예약자가 있으면
//					chkDays(withClient, "already", r.getChkIn(), r.getChkOut(), r); // 날짜체크할꺼야
//				}
//			}

//		}
		reserveGo(withClient);
	}

	// 예약화면으로 넘어가기
	private void reserveGo(Socket withClient) {
//		Object o = (Object) roomList;
//		System.out.println("Object: " + o);

//		for (SConnect s : sList) {
//			s.sendObject(withClient, roomList);
//		}
	}

	// 방 목록에 담기
	private void addList(RDTO r) {
		roomList.add(r.getArray());
		System.out.println("roomList: " + roomList);
	}

}
