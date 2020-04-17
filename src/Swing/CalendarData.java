package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarData extends JFrame implements ActionListener {

	private JPanel NPanel, CPanel, SPanel, Panel_1, Panel_2, Panel_3;
	private JLabel yearLabel, monthLabel;
	private JLabel[] weeksLabel = new JLabel[7]; // 요일 라벨
	private String[] weeksName = { "일", "월", "화", "수", "목", "금", "토" };
	private JButton[] calBtn = new JButton[42]; // 달력 넣을 버튼
	private JButton beforeBtn, nextBtn, okBtn;
	private int year, month, day, thisYear, thisMonth = 0;
	private int beforeDay_1;

	private Reservation r = null;
	private String s;

	Calendar today, calendar;

	public CalendarData(Reservation r, String s) {
		super("날짜선택");
		this.r = r;
		this.s = s; // 체크인인지 체크아웃인지 구분하는 변수
		this.setBounds(0, 0, 360, 360);
		setLocationRelativeTo(null); // 프레임창이 바탕화면 한가운데 띄워짐

		CPanel = new JPanel(); // 달력 전체 판
		CPanel.setLayout(new GridLayout(7, 7));
		this.add(CPanel, BorderLayout.CENTER);

		addGrid();
		topSetting();
		calendarSetting();
		laoutSetting();

		beforeBtn.addActionListener(this);
		nextBtn.addActionListener(this);
		okBtn.addActionListener(this);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	// 그리드레이아웃을 한 CPanel에 요일라벨과 날짜버튼을 넣어줌
	private void addGrid() {
		for (int i = 0; i < weeksName.length; i++) { // 7번
			CPanel.add(weeksLabel[i] = new JLabel(weeksName[i])); // 요일라벨을 넣어준다.
			weeksLabel[i].setHorizontalAlignment(JLabel.CENTER);
		}

		for (int i = 0; i < calBtn.length; i++) { // 42번
			CPanel.add(calBtn[i] = new JButton("")); // 날짜버튼을 넣어준다.
			calBtn[i].setFont(new Font("돋움", Font.PLAIN, 13));
			calBtn[i].setBorderPainted(false);
			calBtn[i].setContentAreaFilled(false);
		}
	}

	private void calendarSetting() {
		// 캘린더 날짜 설정
		// calendar: 그레고리안 캘린더
		calendar.set(Calendar.YEAR, year); // 올해
		calendar.set(Calendar.MONTH, month - 1); // 이번달
		// todSetting에서 month = calendar.get(Calendar.MONTH) + 1;을 해줬는데 이는 출력할때 사람기준으로
		// 설정하기 위함임 이미 month를 +1로 설정해줬기 때문에 다시 -1을 해준것
		calendar.set(Calendar.DATE, 1); // 날짜를 1일로 설정
		System.out.println("Calendar.DATE: " + calendar.get(Calendar.DATE));

		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 1일이 몇번째 요일인지(일요일부터 1로 시작)
		System.out.println("dayOfWeek: " + dayOfWeek);
		System.out.println("calendar.getFirstDayOfWeek(): " + calendar.getFirstDayOfWeek());

		int beforeDay = 0;
		// 1일이 포함된 첫시작 요일부터 1일인 요일전까지, 즉 일요일부터 해당 달의 첫시작(1일) 요일전까지 빈칸으로 셋팅하기 위해서 사용
		for (int i = calendar.getFirstDayOfWeek(); i < dayOfWeek; i++) {
			System.out.println("i: " + i);
			beforeDay++;
		}
		beforeDay_1 = beforeDay;

		System.out.println("beforeDay: " + beforeDay);
		for (int i = 0; i < beforeDay; i++) {
			calBtn[i].setText("");
		}

		// 1일부터 현재달의 마지막 날짜까지 돌리는 for문
		// 날짜 삽입
		for (int i = calendar.getMinimum(Calendar.DAY_OF_MONTH); i <= calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			calBtn[i + beforeDay - 1].setText((i) + ""); // i가 int이기때문에 ""를 더해서 String으로 만들어줌

			calBtn[i + beforeDay - 1].addActionListener(this); // 버튼 클릭시 액션 정의
		}

		System.out.println("1 :" + calendar.get(Calendar.MONTH));
		System.out.println("2 :" + (month - 1));
		System.out.println("month: " + month);

		// 오늘 이전 날짜 버튼 비활성화
		if (month == thisMonth && year <= thisYear) {
			for (int i = calendar.getMinimum(Calendar.DAY_OF_MONTH); i < day; i++) {
				calBtn[i + beforeDay - 1].setEnabled(false);
			}
		}
		for (int i = calendar.getMinimum(Calendar.DAY_OF_MONTH); i <= calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			if ((month < thisMonth && year == thisYear)) {
				calBtn[i + beforeDay - 1].setEnabled(false);
			} else if (year < thisYear) {
				calBtn[i + beforeDay - 1].setEnabled(false);
			}
		} // 여기까지

		// 일자가 찍히지 않은 버튼 비활성화
		for (int i = 0; i < calBtn.length; i++) {
			if (calBtn[i].getText().equals("")) {
				calBtn[i].setEnabled(false);
			}
		}
	}

	// 상단의 0000년 0월 설정(2020년 4월)
	private void topSetting() {
		today = Calendar.getInstance();
		calendar = new GregorianCalendar();

		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH) + 1; // 달은 1월이 0부터 시작하므로 +1을 해줘서 현재달로 출력시킴

		thisYear = today.get(Calendar.YEAR);
		thisMonth = today.get(Calendar.MONTH) + 1;
		day = today.get(Calendar.DATE); // day에 오늘 날짜 지정
	}

	private void laoutSetting() {
		NPanel = new JPanel();
		this.add(NPanel, BorderLayout.NORTH);
		NPanel.setLayout(new BorderLayout());

		Panel_1 = new JPanel();
		NPanel.add(Panel_1, BorderLayout.CENTER);

		yearLabel = new JLabel(year + "년");
		yearLabel.setFont(new Font("돋움", Font.BOLD, 20));
		Panel_1.add(yearLabel);

		monthLabel = new JLabel(month + "월");
		monthLabel.setFont(new Font("돋움", Font.BOLD, 20));
		Panel_1.add(monthLabel);

		Panel_2 = new JPanel();
		NPanel.add(Panel_2, BorderLayout.WEST);

		beforeBtn = new JButton("<"); // 이전달 달력 보는 버튼
		beforeBtn.setFont(new Font("돋움", Font.BOLD, 12));
		beforeBtn.setBorderPainted(false);
		beforeBtn.setContentAreaFilled(false);
		beforeBtn.setFocusPainted(false);
		Panel_2.add(beforeBtn);
		beforeBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				beforeBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				beforeBtn.setForeground(Color.GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		Panel_3 = new JPanel();
		NPanel.add(Panel_3, BorderLayout.EAST);

		nextBtn = new JButton(">"); // 다음달 달력 보는 버튼
		nextBtn.setFont(new Font("돋움", Font.BOLD, 12));
		nextBtn.setBorderPainted(false);
		nextBtn.setContentAreaFilled(false);
		nextBtn.setFocusPainted(false);
		Panel_3.add(nextBtn);
		nextBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nextBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nextBtn.setForeground(Color.GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		SPanel = new JPanel();
		getContentPane().add(SPanel, BorderLayout.SOUTH);

		okBtn = new JButton("선택"); // 선택 버튼
		okBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		okBtn.setFocusPainted(false);
		SPanel.add(okBtn);
	}

	// 각 버튼의 행동 정의
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == beforeBtn) { // < 이전 버튼 누르면
			CPanel.removeAll();
			calendarInput(-1);
			addGrid();
			calendarSetting();
			yearLabel.setText(year + "년");
			monthLabel.setText(month + "월");
		} else if (e.getSource() == nextBtn) { // > 다음 버튼 누르면
			CPanel.removeAll();
			calendarInput(1);
			addGrid();
			calendarSetting();
			yearLabel.setText(year + "년");
			monthLabel.setText(month + "월");
		}

		// 달력의 날짜 버튼 누르면
		for (int i = calendar.getMinimum(Calendar.DAY_OF_MONTH); i <= calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			if (e.getSource().equals(calBtn[i + beforeDay_1 - 1])) {
				String choiceDay = calBtn[i + beforeDay_1 - 1].getText();
				chkDay(choiceDay);
				break;
			}
		}

		if (e.getSource().equals(okBtn)) { // 선택 버튼 누르면
			dispose();
		}
	}

	// 날짜를 예약화면으로 넘겨주는 메서드
	private void chkDay(String choiceDay) {
		System.out.println(month);
		String strFormat = "yyyy-MM-dd"; // 날짜 형식 지정
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);

		int cd = Integer.valueOf(choiceDay);
		Calendar c = new GregorianCalendar(year, month - 1, cd);
		String date = sdf.format(c.getTime());
		System.out.println(date);
		r.chkInOutDay(date, s, year, month - 1, cd); // date:선택한 년월일, s:in/out구분, 년,월,일
		// -1 너무너무 헷갈린다..

	}

	// 이전과 다음으로 넘어가는 month와 year설정
	private void calendarInput(int i) {
		month = month + i;
		if (month <= 0) {
			month = 12; // 지난 해로 넘어감
			year = year - 1;
		} else if (month >= 13) {
			month = 1; // 다음 해로 넘어감
			year = year + 1;
		}
	}

//	public static void main(String[] args) {
//		new CalendarData();
//	}

}
