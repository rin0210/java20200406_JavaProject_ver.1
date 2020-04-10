package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Sample_1 extends JFrame implements ActionListener

/*
 * Swing 과 JDBC를 이용하여 달력을 만들고 달력에 메모장 기능을 추가하라. 메모는 추가 삭제가 가능하고 메모가 있는날은 달력에 표시가
 * 되어야 한다.
 */

{
	String[] days = { "일", "월", "화", "수", "목", "금", "토" };
	int year, month, day, todays, memoday = 0;

	Font f;

	Calendar today;
	Calendar cal;

	JButton btnBefore2, btnAfter2; // befor2 작년 // after2 내년
	JButton btnBefore, btnAfter;
	JButton btnAdd, btnDel, btnUpdate;
	JButton[] calBtn = new JButton[49];

	JLabel time;

	JPanel panSouth;
	JPanel panNorth;
	JPanel panCenter;

	JTextField txtMonth, txtYear;
	// 글자를 넣을수있는 텍스트 필드 년 월 메모부분
	JTextArea txtWrite;
	BorderLayout bLayout = new BorderLayout();

	Connection con = null;
	Statement stmt = null;

	// DB 설정부
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "root";
	String pwd = "root";
	ResultSet rs = null;

	/*
	 * 디 비 만 들 기 create database java; use java; create table java( memo text, year
	 * varchar(10), month varchar(10), day varchar(10) );
	 */
	String sql;

	public Sample_1() {

		today = Calendar.getInstance(); // 디폴트의 타임 존 및 로케일을 사용해 달력을 가져옵니다.
		cal = new GregorianCalendar();
		/*
		 * GregorianCalendar 는,Calendar 의 구상 서브 클래스이며, 세계의 대부분의 지역에서 사용되는 표준적인 달력 시스템을
		 * 제공합니다.
		 */
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH) + 1;// 1월의 값이 0

		panNorth = new JPanel();
		panNorth.add(btnBefore2 = new JButton(" ↓ "));
		panNorth.add(btnBefore = new JButton(" ← "));

		panNorth.add(txtYear = new JTextField(year + "년"));
		panNorth.add(txtMonth = new JTextField(month + "월"));

		f = new Font("Sherif", Font.BOLD, 18); // 년가 월을 표시하는 텍스트 필드의 글자 속성
		txtYear.setFont(f);
		txtMonth.setFont(f);

		txtYear.setEnabled(false); // 년과 월을 선택 비활성화하여 숫자 수정을 불가피한다.
		txtMonth.setEnabled(false);

		panNorth.add(btnAfter = new JButton(" → "));
		panNorth.add(btnAfter2 = new JButton(" ↑ "));

		add(panNorth, "North");

		/*
		 * jdbcpro라는 큰놈 위에 레이아웃을 동,서,남,북으로 나눠서 패널을 하나 하나 올려 놓는 형식이다. 메인보드 위에 부품이 하나 하나
		 * 조립되듯.....
		 */

		// 이놈은 달력에 날에 해당하는 부분

		panCenter = new JPanel(new GridLayout(7, 7));// 격자나,눈금형태의 배치관리자
		f = new Font("Sherif", Font.BOLD, 12);

		gridInit();
		calSet();
		hideInit();
		add(panCenter, "Center");

		//////////////////////////////////
		panSouth = new JPanel();
		// panS_West = new JPanel();
		// panSouth.add(panS_West);
		// panSouth.add(panS_East);

		panSouth.add(btnAdd = new JButton("메모추가"));
		panSouth.add(btnDel = new JButton("메모삭제"));
		panSouth.add(btnUpdate = new JButton("메모수정"));
		panSouth.add(txtWrite = new JTextArea());

		txtWrite.setPreferredSize(new Dimension(150, 28));
		// 메모를 입력받을 텍스트 박스를 가로 150 세로 28 생성
		add(panSouth, "South");

		// 버튼에 대한 행동들을 정의한다.
		btnBefore.addActionListener(this);
		btnAfter.addActionListener(this);
		btnBefore2.addActionListener(this);
		btnAfter2.addActionListener(this);
		btnAdd.addActionListener(this); // 메모추가
		btnDel.addActionListener(this); // 메모삭제
		btnUpdate.addActionListener(this); // 메모삭제

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫는기능
		setTitle("7조 프로젝트 스케쥴러");
		setBounds(200, 200, 450, 400); // (x,y,가로,세로) 프레임창의 위치
		setVisible(true);

	}// end constuctor

	public void calSet() {

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, (month - 1));
		cal.set(Calendar.DATE, 1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		/*
		 * get 및 set 를 위한 필드치로, 요일을 나타냅니다. 이 필드의 값은,SUNDAY,MONDAY,TUESDAY,WEDNESDAY
		 * ,THURSDAY,FRIDAY, 및 SATURDAY 가 됩니다. get()메소드의 의해 요일이 숫자로 반환
		 */

		int j = 0;
		int hopping = 0;
		calBtn[0].setForeground(new Color(255, 0, 0));// 일요일 "일" RGB의 색 넣는다.
		calBtn[6].setForeground(new Color(0, 0, 255));// 토요일 "토"

		for (int i = cal.getFirstDayOfWeek(); i < dayOfWeek; i++) {
			j++;
		}
		/*
		 * 일요일부터 그달의 첫시작 요일까지 빈칸으로 셋팅하기 위해
		 */
		hopping = j;

		for (int kk = 0; kk < hopping; kk++) {
			calBtn[kk + 7].setText("");
		}
		for (int i = cal.getMinimum(Calendar.DAY_OF_MONTH); i <= cal.getMaximum(Calendar.DAY_OF_MONTH); i++) {
			cal.set(Calendar.DATE, i);
			if (cal.get(Calendar.MONTH) != month - 1) {
				break;
			}
			dbConnect();
			todays = i;
			checkday();
			
			
			
			if (memoday == 1) { // memo가 저장된날은 짙은핑크색으로
				calBtn[i + 6 + hopping].setForeground(new Color(255, 0, 255));
			} else {
				calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 0));
				if ((i + hopping - 1) % 7 == 0) {// 일요일
					calBtn[i + 6 + hopping].setForeground(new Color(255, 0, 0));
				}
				if ((i + hopping) % 7 == 0) {// 토요일
					calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 255));
				}
			}

			/*
			 * 요일을 찍은 다음부터 계산해야 하니 요일을 찍은 버튼의 갯수를 더하고 인덱스가 0부터 시작이니 -1을 해준 값으로 연산을 해주고 버튼의
			 * 색깔을 변경해준다.
			 */
			calBtn[i + 6 + hopping].setText((i) + "");
		} // for

	}// end Calset()

	public void actionPerformed(ActionEvent cook) { // 액션 누르는걸cook 눌렀을때

		if (cook.getSource() == btnBefore) { // 이전달로 가기위한 소스부
			this.panCenter.removeAll();
			calInput(-1); // 달을 하나 빼준다
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year + "년");
			this.txtMonth.setText(month + "월");
		} else if (cook.getSource() == btnAfter) { // 다음 달로 가기위한 소스부
			this.panCenter.removeAll();
			calInput(1); // 달을 하나 더해준다.
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year + "년");
			this.txtMonth.setText(month + "월");
		} else if (cook.getSource() == btnBefore2) { // 전년 으로 가기위한 소스부
			this.panCenter.removeAll();
			calInput(-12); // 12개월을 빼준다.
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year + "년");
			this.txtMonth.setText(month + "월");
		} else if (cook.getSource() == btnAfter2) { // 내년으 로 가기위한 소스부
			this.panCenter.removeAll();
			calInput(12); // 12개월을 더해준다.
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year + "년");
			this.txtMonth.setText(month + "월");
		} else if (cook.getSource() == btnAdd) {
			dbConnect();
			add();
			calSet();
			txtWrite.setText("");

		} else if (cook.getSource() == btnDel) {
			dbConnect();
			del();
			calSet();
			txtWrite.setText("");
		} else if (cook.getSource() == btnUpdate) {
			dbConnect();
			update();
			calSet();
			txtWrite.setText("");
		}

		else if (Integer.parseInt(cook.getActionCommand()) >= 1 && Integer.parseInt(cook.getActionCommand()) <= 31) {
			day = Integer.parseInt(cook.getActionCommand());
			// 버튼의 밸류 즉 1,2,3.... 문자를 정수형으로 변환하여 클릭한 날짜를 바꿔준다.
			System.out.println(day);
			dbConnect();
			searchmemo();
			calSet();
		}
	}// end actionperformed()

	public void hideInit() {
		for (int i = 0; i < calBtn.length; i++) {
			if ((calBtn[i].getText()).equals(""))
				calBtn[i].setEnabled(false);
			// 일이 찍히지 않은 나머지 버튼을 비활성화 시킨다.
		} // end for
	}// end hideInit()
//     public void separate(){

	public void gridInit() {
		// jPanel3에 버튼 붙이기
		for (int i = 0; i < days.length; i++)
			panCenter.add(calBtn[i] = new JButton(days[i]));

		for (int i = days.length; i < 49; i++) {
			panCenter.add(calBtn[i] = new JButton(""));
			calBtn[i].addActionListener(this);
		}
	}// end gridInit()

	public void panelInit() {
		GridLayout gridLayout1 = new GridLayout(7, 7);
		panCenter.setLayout(gridLayout1);
	}// end panelInit()

	public void calInput(int gap) {

		if (gap == -1 || gap == 1) {
			month += (gap);
			if (month <= 0) {
				month = 12;
				year = year - 1;
			} else if (month >= 13) {
				month = 1;
				year = year + 1;
			}
		} else if (gap == 12) {
			year++;
		} else if (gap == -12) {
			year--;
		}

	}// end calInput()

	public void dbConnect() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pwd);
			stmt = con.createStatement();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// end dbConnect()

	public void add() {
		try {
			String temp = txtWrite.getText();
			if (temp.equals("")) {
				JOptionPane.showMessageDialog(null, "내용이 없습니다.");
				return;
			}
			sql = "insert into memo (memo,year,month,day) values (";
			sql += "'" + temp + "',";
			sql += "" + year + ",";
			sql += "" + month + ",";
			sql += "" + day + ");";
			System.out.println(sql);
			stmt.executeUpdate(sql);

			stmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end add()

	public void update() {
		try {
			String temp = txtWrite.getText();
			if (temp.equals("")) {
				JOptionPane.showMessageDialog(null, "내용이 없습니다.");
				return;
			}
			sql = "update memo set memo =";
			sql += "'" + temp + "'";
			sql += " where year=";
			sql += year + " and month=";
			sql += month + " and day=";
			sql += +day + ";";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end update();

	public void del() {
		try {
			sql = "delete from memo where year=";
			sql += year + " and month=";
			sql += month + " and day=";
			sql += +day + ";";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end del();

	public void searchmemo() {
		try {

			sql = "select memo from memo where year=";
			sql += year + " and month=";
			sql += month + " and day=";
			sql += "" + day + ";";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			String gettemp = "";

			while (rs.next()) {
				gettemp += rs.getString("memo") + "  ";
			}
			txtWrite.setText(gettemp);

			stmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end searchmemo()

	public void checkday() {
		sql = "select * from memo where year=";
		sql += year + " and month=";
		sql += month + " and day=";
		sql += "" + todays + ";";
		// System.out.println(sql);
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				memoday = 1;
			} else
				memoday = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end checkday()

	public static void main(String[] args) {
		new Sample_1();

	}
}
