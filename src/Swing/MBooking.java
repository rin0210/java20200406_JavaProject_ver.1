package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Client.CConnect;
import JDBC.BDTO;

public class MBooking extends JFrame {
	private JTextField textField, textField_1, textField_2, textField_3, textField_4, textField_5, textField_6,
			textField_7;
	private JButton button_1, button_2;
	private JLabel label, label_1, label_2, label_3, label_4, label_5, label_6, label_7, label_8, label_9, label_10,
			label_11, allCheckLabel;

	private CConnect cc = null;
	private Object o = null;
	private String[] myInfo = null;
	private int totalPrice = 0;
	private String room = null;
	private String[] mine = null;
	private String name = null;

	private HMain hm = null;
	private Room rm = null;

	public MBooking(CConnect cc, Room rm, String room, int totalPrice, String[] mine) {
		super("회원예약");
		this.cc = cc;
		this.room = room;
		this.totalPrice = totalPrice;
		this.mine = mine;
		this.rm = rm; 

		this.setLayout(null); // 배치관리자 해제
		this.setBounds(0, 0, 635, 385);
		setLocationRelativeTo(null); // 바탕화면 한가운데 띄우기

		init();
		labelSetting();
		textFieldSetting();
		buttonSetting();
		setInfo();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	// 회원정보 불러오기
	private void init() {
		o = cc.receiveObject();
		myInfo = (String[]) o;
	}

	// 정보 셋팅
	private void setInfo() {
		name = myInfo[1];
		textField.setText(myInfo[1]);
		textField_1.setText(myInfo[2]);
		textField_2.setText(myInfo[3]);
		textField_7.setText(String.valueOf(totalPrice));
	}

	private void buttonSetting() {
		button_1 = new JButton("< 이전");
		button_1.setFont(new Font("돋움", Font.PLAIN, 12));
		button_1.setBorderPainted(false);
		button_1.setContentAreaFilled(false);
		button_1.setFocusPainted(false);
		button_1.setBounds(18, 304, 80, 23);
		this.add(button_1);

		button_1.addMouseListener(new MouseListener() { // 나가기 버튼

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button_1.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				button_1.setForeground(Color.GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				rm.setVisible(true);
				dispose();
			}
		});

		button_2 = new JButton("예약하기");
		button_2.setFont(new Font("돋움", Font.PLAIN, 12));
		button_2.setFocusPainted(false);
		button_2.setBounds(514, 272, 90, 30);
		this.add(button_2);

		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("") || textField_1.getText().equals("")
						|| textField_2.getText().equals("") || textField_3.getText().equals("")
						|| textField_4.getText().equals("") || textField_5.getText().equals("")
						|| textField_6.getText().equals("")) {
					allCheckLabel.setText("*필수기재사항입니다. 해당내용을 모두 입력해주세요.");
				} else {
					mine[0] = name + " ";

					String msg = "/reservation ";
					for (int i = 0; i < mine.length; i++) {
						msg = msg + mine[i];
					}

					cc.send(msg);
					chkMsg();
				}
			}
		});
	}

	public void chkMsg() {
		String msg = cc.receive();
		if (msg.indexOf("reservationOk") > -1) {
			int ed = msg.indexOf(" ", 0);
			String reNum = msg.substring(ed + 1); // 예약번호 부분
			
			JOptionPane.showMessageDialog(null, "예약이 완료되었습니다.\n고객님의 예약 번호는 "+reNum+" 입니다."+"\n저희 호텔을 이용해주셔서 감사합니다.", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			hm = HMain.getInstance();
			hm.visible(1);
			dispose();
		}
	}

	private void textFieldSetting() {
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(104, 92, 130, 30);
		this.add(textField);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(104, 145, 130, 30);
		this.add(textField_1);

		textField_1.addKeyListener(new KeyAdapter() { // 연락처 글자수 제한
			public void keyTyped(KeyEvent k) {
				JTextField src = (JTextField) k.getSource();
				if (src.getText().length() >= 11) {
					k.consume();
				}
			}
		});

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(104, 198, 130, 30);
		this.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(376, 92, 130, 30);
		this.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(376, 145, 130, 30);
		this.add(textField_4);

		textField_4.addKeyListener(new KeyAdapter() { // 카드번호 글자수 제한
			public void keyTyped(KeyEvent k) {
				JTextField src = (JTextField) k.getSource();
				if (src.getText().length() >= 16) {
					k.consume();
				}
			}
		});

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(376, 198, 30, 30);
		this.add(textField_5);

		textField_5.addKeyListener(new KeyAdapter() { // 유효기간 글자수 제한
			public void keyTyped(KeyEvent k) {
				JTextField src = (JTextField) k.getSource();
				if (src.getText().length() >= 2) {
					k.consume();
				}
			}
		});

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(447, 198, 30, 30);
		this.add(textField_6);

		textField_6.addKeyListener(new KeyAdapter() { // 유효기간 글자수 제한
			public void keyTyped(KeyEvent k) {
				JTextField src = (JTextField) k.getSource();
				if (src.getText().length() >= 2) {
					k.consume();
				}
			}
		});

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(372, 273, 130, 30);
		this.add(textField_7);

	}

	private void labelSetting() {
		allCheckLabel = new JLabel(); // 필수기재사항 체크
		allCheckLabel.setFont(new Font("돋움", Font.PLAIN, 12));
		allCheckLabel.setForeground(Color.RED);
		allCheckLabel.setBounds(40, 10, 300, 20);
		this.add(allCheckLabel);

		label = new JLabel("고객정보");
		label.setFont(new Font("돋움", Font.PLAIN, 14));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBounds(37, 28, 68, 33);
		this.add(label);

		label_1 = new JLabel("성   명");
		label_1.setFont(new Font("돋움", Font.PLAIN, 13));
		label_1.setHorizontalAlignment(JLabel.CENTER);
		label_1.setBounds(34, 92, 57, 33);
		this.add(label_1);

		label_2 = new JLabel("연락처");
		label_2.setFont(new Font("돋움", Font.PLAIN, 13));
		label_2.setHorizontalAlignment(JLabel.CENTER);
		label_2.setBounds(34, 145, 57, 33);
		this.add(label_2);

		label_3 = new JLabel("주   소");
		label_3.setFont(new Font("돋움", Font.PLAIN, 13));
		label_3.setHorizontalAlignment(JLabel.CENTER);
		label_3.setBounds(34, 198, 57, 33);
		this.add(label_3);

		label_4 = new JLabel("신용카드정보");
		label_4.setFont(new Font("돋움", Font.PLAIN, 14));
		label_4.setHorizontalAlignment(JLabel.CENTER);
		label_4.setBounds(301, 28, 105, 33);
		this.add(label_4);

		label_5 = new JLabel("카드종류");
		label_5.setFont(new Font("돋움", Font.PLAIN, 13));
		label_5.setHorizontalAlignment(JLabel.CENTER);
		label_5.setBounds(309, 92, 57, 33);
		this.add(label_5);

		label_6 = new JLabel("카드번호");
		label_6.setFont(new Font("돋움", Font.PLAIN, 13));
		label_6.setHorizontalAlignment(JLabel.CENTER);
		label_6.setBounds(309, 145, 57, 33);
		this.add(label_6);

		label_7 = new JLabel("유효기간");
		label_7.setFont(new Font("돋움", Font.PLAIN, 13));
		label_7.setHorizontalAlignment(JLabel.CENTER);
		label_7.setBounds(309, 198, 57, 33);
		this.add(label_7);

		label_8 = new JLabel("월");
		label_8.setFont(new Font("돋움", Font.PLAIN, 13));
		label_8.setHorizontalAlignment(JLabel.CENTER);
		label_8.setBounds(410, 198, 30, 33);
		this.add(label_8);

		label_9 = new JLabel("년");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("돋움", Font.PLAIN, 13));
		label_9.setBounds(480, 198, 30, 33);
		this.add(label_9);

		label_10 = new JLabel("요금합계");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setFont(new Font("돋움", Font.PLAIN, 13));
		label_10.setBounds(308, 271, 57, 33);
		this.add(label_10);

		label_11 = new JLabel("*부가가치세 및 봉사금 포함");
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setFont(new Font("돋움", Font.PLAIN, 11));
		label_11.setBounds(316, 299, 240, 33);
		this.add(label_11);

	}

//	public static void main(String[] args) {
//		new MBooking();
//	}

}
