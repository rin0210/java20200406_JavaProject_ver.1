package Swing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		ArrayList<String> dayList = new ArrayList<>();
		ArrayList<String> compare = new ArrayList<>();
		compare.add("20200501");

		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyyMMdd"); // 년월 표시
		// dateFormat = new SimpleDateFormat("yyyyMMdd"); //년월일 표시

		Calendar cal = Calendar.getInstance();

		cal.set(2020, 5 - 1, 5); // 종료 날짜 셋팅
		String endDate = dateFormat.format(cal.getTime());

		cal.set(2020, 5 - 1, 1); // 시작 날짜 셋팅
		String startDate = dateFormat.format(cal.getTime());

//		int i = 0;

		while (true) { // 다르다면 실행, 동일 하다면 빠져나감
			dayList.add(startDate);

//			if (i == 0) { // 최초 실행 출력
//				System.out.println(dateFormat.format(cal.getTime()));
//			}

//			cal.add(Calendar.MONTH, 1); // 1달 더해줌
			cal.add(Calendar.DATE, 1); // 1일 더해줌
			startDate = dateFormat.format(cal.getTime()); // 비교를 위한 값 셋팅

			// +1달 출력
//			System.out.println(dateFormat.format(cal.getTime()));

//			i++;
			if (startDate.equals(endDate)) {
				break;
			}

		}

		System.out.println(dayList);

		if (compare.size() >= dayList.size()) {

			for (int i = 0; i < compare.size(); i++) {
				if (compare.contains(dayList.get(i))) {
					System.out.println("ok");
					break;
				}
			}
		} else {
			for (int i = 0; i < dayList.size(); i++) {
				if(dayList.contains(compare.get(i))) {
					System.out.println("ok?");
					break;
				}
			}
		}
	}
}
