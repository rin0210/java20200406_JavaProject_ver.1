package Swing;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Calendar extends JFrame {
	private JLabel mainLabel, chkInLable, chkOutLabel, dayLabel, adultLabel, kidLabel;
	private JTextField textField, textField_1, textField_2;
	private JButton button, button_1, button_2, button_3;
	private JComboBox comboBox, comboBox_1;

	public Calendar() {
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
		mainLabel.setBounds(124, 36, 344, 37);
		this.add(mainLabel);

		chkInLable = new JLabel("체크인 날짜");
		chkInLable.setFont(new Font("돋움", Font.PLAIN, 13));
		chkInLable.setHorizontalAlignment(JLabel.CENTER); 
		chkInLable.setBounds(32, 109, 92, 27);
		this.add(chkInLable);

		chkOutLabel = new JLabel("체크아웃 날짜");
		chkOutLabel.setFont(new Font("돋움", Font.PLAIN, 13));
		chkOutLabel.setHorizontalAlignment(JLabel.CENTER); 
		chkOutLabel.setBounds(190, 109, 92, 27);
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
		textField = new JTextField();
		textField.setBounds(32, 141, 146, 37);
		getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(190, 141, 146, 37);
		getContentPane().add(textField_1);

		textField_2 = new JTextField();
		textField_2.setBounds(348, 141, 37, 37);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);

	}

	// 버튼 셋팅
	private void buttonSetting() {
		button = new JButton();
		button.setBounds(119, 104, 37, 37);
		this.add(button);

		button_1 = new JButton();
		button_1.setBounds(279, 104, 37, 37);
		this.add(button_1);

		button_2 = new JButton("검색");
		button_2.setFont(new Font("돋움", Font.PLAIN, 14));
		button_2.setHorizontalAlignment(JButton.CENTER);
		button_2.setBounds(218, 218, 146, 37);
		this.add(button_2);

		button_3 = new JButton("New button");
		button_3.setBounds(12, 299, 97, 23);
		this.add(button_3);

	}

	// 콤보박스 셋팅
	private void comboSetting() {
		comboBox = new JComboBox();
		comboBox.setBounds(423, 141, 37, 37);
		getContentPane().add(comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(498, 141, 37, 37);
		getContentPane().add(comboBox_1);

	}

	public static void main(String[] args) {
		new Calendar();
	}

}
