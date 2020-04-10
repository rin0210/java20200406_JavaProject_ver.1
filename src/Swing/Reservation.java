package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Reservation extends JFrame {
	private JLabel mainLabel, chkInLable, chkOutLabel, dayLabel, adultLabel, kidLabel;
	private JTextField textField, textField_1, textField_2;
	private JButton button, button_1, button_2, button_3;
	private JComboBox comboBox, comboBox_1;
	private CalendarData cd = null;

	public Reservation() {
		super("객실예약");
		getContentPane().setLayout(null); // 배치관리자 해제
		this.setBounds(0, 0, 620, 370);
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
		mainLabel.setBounds(130, 36, 344, 37);
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

		dayLabel = new JLabel("박");
		dayLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		dayLabel.setBounds(390, 141, 37, 37);
		this.add(dayLabel);

		adultLabel = new JLabel("명");
		adultLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		adultLabel.setBounds(465, 141, 37, 37);
		this.add(adultLabel);

		kidLabel = new JLabel("명");
		kidLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		kidLabel.setBounds(540, 141, 37, 37);
		this.add(kidLabel);
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
		textField.setEnabled(false); // 사용자가 임의로 수정 불가하게 만듦
		this.add(textField_1);

		textField_2 = new JTextField(); // 박
		textField_2.setBounds(348, 141, 37, 37);
		textField_2.setHorizontalAlignment(JTextField.CENTER);
		textField.setEnabled(false); // 사용자가 임의로 수정 불가하게 만듦
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
		button_2.setBounds(232, 218, 146, 37);
		this.add(button_2);

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
//				new Choice(cc);
				dispose();
			}
		});

	}

	// 콤보박스 셋팅
	private void comboSetting() {
		comboBox = new JComboBox();
		comboBox.setBounds(423, 141, 37, 35);
		this.add(comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(498, 141, 37, 35);
		this.add(comboBox_1);

	}

	private void lookCalendar(String s) {
		cd = new CalendarData(this, s);
	}

	public void chkInDay(String b, String s, String a) { // b:전체날짜, s:in/out구분, a:day
		String in, out;
		if (s.equals("In")) {
			textField.setText(b);
			in = a;
		} else if (s.equals("Out")) {
			textField_1.setText(b);
			out = a;
		} // 이렇게 하면 안돼..day만으로 구하면 달이 넘어가면 망함
	}

	public static void main(String[] args) {
		new Reservation();
	}

}
