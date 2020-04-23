package Swing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import Client.CConnect;
import JDBC.BDTO;
import JDBC.RDTO;

public class CompareDate {
	private CConnect cc = null;
	private Reservation rv = null;
	private Room r = null;
	
	private String info = null;
	private Object o = null;
	private ArrayList<Object> getObjList = null;
	private ArrayList<RDTO> getRoomList = new ArrayList<>();
	private ArrayList<BDTO> getBookingList = new ArrayList<>();
	private RDTO rt = null;
	private BDTO bt = null;
	
	private String chkIn = null;
	private String chkOut = null;
	private String adult = null;
	private String kid = null;
	private String night = null;
	private String chk = null;
	
	private ArrayList<String> myDaysList = new ArrayList<>();
	private ArrayList<String[]> myRoomList = new ArrayList<>();
	private ArrayList<String> overlapList = new ArrayList<>(); // 중복된 방 목록
	private String[] mine = null; /////

	public CompareDate(CConnect cc, Reservation rv, String chk, String info, String night, String[] mine) { ///////
		this.cc = cc;
		this.rv = rv;
		this.chk = chk;
		this.info = info;
		this.night = night;
		this.mine = mine; /////
//		this.getObjList = cc.receiveObject();

		getList();
		myReserve();
	}

	// DB에서 방,예약 목록 가져오기
	private void getList() {
		String msg = "/callRoom";
		cc.send(msg);
		o = cc.receiveObject();
		getObjList = (ArrayList<Object>) o;
//		getObjList = cc.receiveObject();

		for (Object o : getObjList) {
			rt = (RDTO) o;
			getRoomList.add(rt);
		}
		System.out.println("방목록 가져왔니? " + getRoomList.size());

		msg = "/callBooking";
		cc.send(msg);
		o = cc.receiveObject();
		getObjList = (ArrayList<Object>) o;

		for (Object o : getObjList) {
			bt = (BDTO) o;
			getBookingList.add(bt);
		}
		System.out.println("예약목록 가져왔니? " + getBookingList.size());
	}

	// msg분석하는 메서드
	private void myReserve() {
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
		System.out.println("체크인 : " + chkIn);
		System.out.println("체크아웃 : " + chkOut);
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
			compareDate(alreadyList, r);
		}
	}

	// 날짜 비교
	private void compareDate(ArrayList<String> alreadyList, RDTO r) {
		System.out.println("alreadyList 사이즈? "+alreadyList.size());
		System.out.println("myDaysList 사이즈? "+myDaysList.size());
		if (alreadyList.size() >= myDaysList.size()) {
			for (int i = 0; i < myDaysList.size(); i++) {
				if (alreadyList.contains(myDaysList.get(i))) { // 날짜가 중복되면
					overlapList.add(r.getRoom());
					break;
				} else {
				}
			}
		} else {
			for (int i = 0; i < alreadyList.size(); i++) {
				if (myDaysList.contains(alreadyList.get(i))) { // 날짜가 중복되면
					overlapList.add(r.getRoom());
					break;
				} else {
				}
			}
		}
	}

	// 예약목록 있는지 체크하기
	private void reserveChk() {

		if (getBookingList.size() >= 1) { // 호텔 방 중에 예약한 방이 하나라도 있으면
			
			for (RDTO r : getRoomList) {
				for (BDTO b : getBookingList) {
					if (r.getRoom().equals(b.getRoom())) {
						chkDays("already", b.getChkIn(), b.getChkOut(), r); // 날짜체크할꺼야
						break;
					} else {

					}
				}
			}
		}
		reserveGo();
	}

	private void reserveGo() {
		System.out.println("중복날짜 방목록 :" + overlapList);
		
		for (int i = 0; i < getRoomList.size(); i++) {
			for (int j = 0; j < overlapList.size(); j++) {
				if (getRoomList.get(i).getRoom().equals(overlapList.get(j))) {
					getRoomList.remove(i);
				}
			}
		}
		for (RDTO r : getRoomList) {
			myRoomList.add(r.getArray());
		}

		System.out.println("내가 띄울 방목록 몇개? " + myRoomList.size());
		r = new Room(cc, rv, chk, myRoomList, night, mine); 
	}

}
