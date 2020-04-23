package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Client.CConnect;

public class Reservation extends JFrame {
	private JLabel mainLabel, chkInLable, chkOutLabel, dayLabel, adultLabel, kidLabel, numLabel, numLabel_1,
			peopleChkLabel;
	private JTextField textField, textField_1, textField_2;
	private JButton button, button_1, button_2, button_3;
	private JComboBox<String> comboBox, comboBox_1;

	private Calendar calIn = Calendar.getInstance();
	private Calendar calOut = Calendar.getInstance();

	private CConnect cc = null;
	private CalendarData cd = null;
	private CompareDate cpd = null;
	private Reservation rv = null;

	private String chkInDay = null;
	private String chkOutDay = null;
	private String adultCombo = null;
	private String kidCombo = null;
	private String night = null;
	private String chk = null; // 회원인지 비회원인지 체크
	private String id = null;

	public Reservation(CConnect cc, String id, String chk) {
		super("객실예약");
		this.cc = cc;
		this.rv = this;
		this.id = id;
		this.chk = chk;

		getContentPane().setLayout(null); // 배치관리자 해제
		this.setBounds(0, 0, 635, 370);
		setLocationRelativeTo(null); // 바탕화면 한가운데 띄우기

		labelSetting();
		txtSetting();
		buttonSetting();
		comboSetting();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	// 라벨 셋팅
	private void labelSetting() {
		mainLabel = new JLabel("날짜 · 인원 선택");
		mainLabel.setFont(new Font("돋움", Font.BOLD, 24));
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setBounds(133, 36, 344, 37);
		this.add(mainLabel);

		chkInLable = new JLabel("체크인 날짜");
		chkInLable.setFont(new Font("돋움", Font.PLAIN, 13));
		chkInLable.setHorizontalAlignment(JLabel.CENTER);
		chkInLable.setBounds(44, 109, 90, 27);
		this.add(chkInLable);

		chkOutLabel = new JLabel("체크아웃 날짜");
		chkOutLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		chkOutLabel.setHorizontalAlignment(JLabel.CENTER);
		chkOutLabel.setBounds(199, 109, 90, 27);
		this.add(chkOutLabel);

		adultLabel = new JLabel("성인");
		adultLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		adultLabel.setHorizontalAlignment(JLabel.CENTER);
		adultLabel.setBounds(423, 109, 55, 27);
		this.add(adultLabel);

		kidLabel = new JLabel("아동");
		kidLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		kidLabel.setHorizontalAlignment(JLabel.CENTER);
		kidLabel.setBounds(516, 109, 55, 27);
		this.add(kidLabel);

		dayLabel = new JLabel("박");
		dayLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		dayLabel.setBounds(390, 141, 37, 37);
		this.add(dayLabel);

		numLabel = new JLabel("명");
		numLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		numLabel.setBounds(483, 141, 37, 37);
		this.add(numLabel);

		numLabel_1 = new JLabel("명");
		numLabel_1.setFont(new Font("돋움", Font.PLAIN, 13));
		numLabel_1.setBounds(576, 141, 37, 37);
		this.add(numLabel_1);

		peopleChkLabel = new JLabel(" *예약 인원은 성인과 아동의 수를 모두 합하여 최대 5명까지 예약가능합니다.");
		peopleChkLabel.setFont(new Font("돋움", Font.PLAIN, 12));
		peopleChkLabel.setHorizontalAlignment(JTextField.RIGHT);
		peopleChkLabel.setForeground(Color.BLUE);
		peopleChkLabel.setBounds(156, 290, 438, 30);
		getContentPane().add(peopleChkLabel);

	}

	// 텍스트필드 셋팅
	private void txtSetting() {
		textField = new JTextField(); // 체크인
		textField.setBounds(32, 141, 146, 37);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setEnabled(false); // 사용자가 임의로 수정 불가하게 만듦
		this.add(textField);

		textField_1 = new JTextField(); // 체크아웃
		textField_1.setBounds(190, 141, 146, 37);
		textField_1.setHorizontalAlignment(JTextField.CENTER);
		textField_1.setEnabled(false); // 사용자가 임의로 수정 불가하게 만듦
		this.add(textField_1);

		textField_2 = new JTextField(); // 박
		textField_2.setBounds(348, 141, 37, 37);
		textField_2.setHorizontalAlignment(JTextField.CENTER);
		textField_2.setEnabled(false); // 사용자가 임의로 수정 불가하게 만듦
		this.add(textField_2);

	}

	// 버튼 셋팅
	private void buttonSetting() {
		ImageIcon icon_1 = new ImageIcon("calendar1.jpg"); // 디폴트 이미지 아이콘
		Image img_1 = icon_1.getImage(); // 이미지아이콘을 이미지로 변경
		img_1 = img_1.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // 이미지 사이즈 조절
		icon_1 = new ImageIcon(img_1); // 다시 이미지아이콘으로 변경

		ImageIcon icon_2 = new ImageIcon("calendar2.jpg"); // 버튼에 마우스 올렸을때 출력되는 이미지아이콘
		Image img_2 = icon_2.getImage();
		img_2 = img_2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_2 = new ImageIcon(img_2);

		button = new JButton(icon_1);
		button.setRolloverIcon(icon_2); // 버튼에 마우스 올렸을때 출력되는 이미지아이콘
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBounds(125, 101, 37, 37);
		this.add(button);

		button.addActionListener(new ActionListener() { // 체크인 버튼 달력 클릭시

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lookCalendar("In");
			}
		});

		button_1 = new JButton(icon_1);
		button_1.setRolloverIcon(icon_2); // 버튼에 마우스 올렸을때 출력되는 이미지아이콘
		button_1.setBorderPainted(false);
		button_1.setContentAreaFilled(false);
		button_1.setFocusPainted(false);
		button_1.setBounds(287, 101, 37, 37);
		this.add(button_1);

		button_1.addActionListener(new ActionListener() { // 체크아웃 버튼 달력 클릭시

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lookCalendar("Out");

			}
		});

		button_2 = new JButton("검색");
		button_2.setFont(new Font("돋움", Font.PLAIN, 14));
		button_2.setHorizontalAlignment(JButton.CENTER);
		button_2.setFocusPainted(false);
		button_2.setBounds(232, 218, 146, 37);
		this.add(button_2);

		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("") || textField_1.getText().equals("")
						|| textField_2.getText().equals("") || comboBox.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "입력사항을 모두 선택해주세요.", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else {
					adultCombo = comboBox.getSelectedItem().toString(); // 콤보박스에서 데이터값 가져오기
					kidCombo = comboBox_1.getSelectedItem().toString();

					int people = Integer.valueOf(adultCombo) + Integer.valueOf(kidCombo); // 전체인원:성인+아동
					if (people > 5) {
						peopleChkLabel.setForeground(Color.RED);

					} else {
						String info = chkInDay + " " + chkOutDay + " " + adultCombo + " " + kidCombo;
						System.out.println(info);

						String[] mine = new String[7];

						mine[2] = String.valueOf(people) + " ";
						mine[3] = chkInDay + " ";
						mine[4] = chkOutDay + " ";
						mine[5] = night + " ";

						cpd = new CompareDate(cc, rv, chk, info, night, mine);
						dispose();
					}

				}
			}
		});

		button_3 = new JButton("< 이전");
		button_3.setFont(new Font("돋움", Font.PLAIN, 12));
		button_3.setBorderPainted(false);
		button_3.setContentAreaFilled(false);
		button_3.setFocusPainted(false);
		button_3.setBounds(5, 290, 70, 30);
		this.add(button_3);

		button_3.addMouseListener(new MouseListener() { // 나가기 버튼

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button_3.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				button_3.setForeground(Color.GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				new Choice(cc, id);
				dispose();
			}
		});
	}

	// 콤보박스 셋팅
	private void comboSetting() {
		String[] combo = new String[6];
		for (int i = 0; i <= 5; i++) {
			combo[i] = String.valueOf(i);
		}

		comboBox = new JComboBox<>(combo);
		comboBox.setBounds(423, 141, 55, 35);
		this.add(comboBox);

		comboBox_1 = new JComboBox<>(combo);
		comboBox_1.setBounds(516, 141, 55, 35);
		this.add(comboBox_1);

	}

	// 달력 열기
	private void lookCalendar(String s) {
		cd = new CalendarData(this, s);
	}

	// 선택한 날짜 셋팅
	public void chkInOutDay(String b, String s, int y, int m, int d) { // b:전체날짜, s:in/out구분
		System.out.println(y + "/" + m + "/" + d);
		
		if (s.equals("In")) {
			textField.setText(b);
			calIn.set(y, m, d);

			String strFormat = "yyyy-MM-dd"; // cConnect로 넘겨주기위해서 String으로 형변환시킴
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			chkInDay = sdf.format(calIn.getTime());
			System.out.println("chkInDay: " + chkInDay);
			
		} else if (s.equals("Out")) {
			textField_1.setText(b);
			calOut.set(y, m, d);

			String strFormat = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			chkOutDay = sdf.format(calOut.getTime());
			System.out.println("chkOutDay: " + chkOutDay);
			
		}

		if (!textField.getText().equals("") && !textField_1.getText().equals("")) { // 체크인아웃 날짜를 모두 선택하면
			int compare = chkInDay.compareTo(chkOutDay); // 체크인아웃 날짜 비교하는 변수
			System.out.println(compare);
			if (compare < 0) {
				long diffSec = (calOut.getTimeInMillis() - calIn.getTimeInMillis()) / 1000; // 두 날짜간의 차이를 1000으로 나누어
																							// 초단위로 변환
				long diffDay = diffSec / (24 * 60 * 60); // 1일은 24(시간)*60(분)*60(초)이므로 이를 나누면 일로 변환됨
				night = String.valueOf(diffDay);
				textField_2.setText(night);
			} else {
				JOptionPane.showMessageDialog(null, "날짜를 다시 확인해주세요.", "Message", JOptionPane.INFORMATION_MESSAGE);
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
			}
		}
	}

//	public static void main(String[] args) {
//		new Reservation(cc);
//	}

}
