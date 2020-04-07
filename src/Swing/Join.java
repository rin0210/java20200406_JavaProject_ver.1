package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Client.CConnect;

public class Join extends JFrame {
	private CConnect cc = null;
	
//	JLabel checkLabel; // 아이디 중복체크
//	String checkMsg;
//	JTextField checkTxt; 

	public Join(CConnect cc) {
		super("회원가입");
		this.cc = cc;
		this.setLayout(null); // 배치관리자 해제
		this.setBounds(0, 0, 350, 440);
		setLocationRelativeTo(null); // 프레임창이 바탕화면 한가운데 띄워짐
		

		setting();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private void setting() {
		JLabel label = new JLabel("회원정보 입력");
		label.setFont(new Font("돋움", Font.BOLD, 17));
		JLabel labelLineS = new JLabel("──────────────────────");
		JLabel labelId = new JLabel("아 이 디");
		labelId.setFont(new Font("돋움", Font.PLAIN, 15));

//		checkLabel = new JLabel(checkMsg); // 아이디 중복체크
////		checkTxt = new JTextField();
//		checkLabel.setFont(new Font("돋움", Font.PLAIN, 12));
//		checkLabel.setForeground(Color.RED);

		JLabel labelPwd = new JLabel("비밀번호");
		labelPwd.setFont(new Font("돋움", Font.PLAIN, 15));
		JLabel labelName = new JLabel("성     명");
		labelName.setFont(new Font("돋움", Font.PLAIN, 15));
		JLabel labelTel = new JLabel("전화번호");
		labelTel.setFont(new Font("돋움", Font.PLAIN, 15));
		JLabel labelAddr = new JLabel("주     소");
		labelAddr.setFont(new Font("돋움", Font.PLAIN, 15));
		JLabel labelLineE = new JLabel("──────────────────────");

		label.setBounds(30, 25, 120, 20);
		labelLineS.setBounds(25, 60, 320, 20);
		labelId.setBounds(30, 100, 100, 20);
//		checkLabel.setBounds(30, 118, 200, 20);
		labelPwd.setBounds(30, 140, 100, 20);
		labelName.setBounds(30, 180, 100, 20);
		labelTel.setBounds(30, 220, 100, 20);
		labelAddr.setBounds(30, 260, 100, 20);
		labelLineE.setBounds(25, 300, 320, 20);

		this.add(label);
		this.add(labelLineS);
		this.add(labelId);
//		this.add(checkLabel);
		this.add(labelPwd);
		this.add(labelName);
		this.add(labelTel);
		this.add(labelAddr);
		this.add(labelLineE);

		JTextField txtId = new JTextField();
		JTextField txtPwd = new JTextField();
		JTextField txtName = new JTextField();
		JTextField txtTel1 = new JTextField();
		JTextField txtTel2 = new JTextField();
		JTextField txtTel3 = new JTextField();
		JLabel hipen1 = new JLabel("-");
		JLabel hipen2 = new JLabel("-");
		JTextField txtAddr = new JTextField();

		txtId.setBounds(100, 100, 120, 20);
		txtPwd.setBounds(100, 140, 120, 20);
		txtName.setBounds(100, 180, 120, 20);
		txtTel1.setBounds(100, 220, 45, 20);
		txtTel2.setBounds(160, 220, 45, 20);
		txtTel3.setBounds(220, 220, 45, 20);
		hipen1.setBounds(150, 220, 20, 20);
		hipen2.setBounds(210, 220, 20, 20);
		txtAddr.setBounds(100, 260, 210, 20);

		this.add(txtId);
		this.add(txtPwd);
		this.add(txtName);
		this.add(txtTel1);
		this.add(txtTel2);
		this.add(txtTel3);
		this.add(hipen1);
		this.add(hipen2);
		this.add(txtAddr);

		JButton checkBtn = new JButton("중복확인");
		checkBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		checkBtn.setBounds(225, 100, 85, 20);
		this.add(checkBtn);

		checkBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cc.send("*id " + txtId.getText());
			}
		});

		JButton applyBtn = new JButton("가입 신청");
		applyBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		applyBtn.setBounds(120, 340, 90, 25);
		this.add(applyBtn);

		applyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField txtTel = new JTextField();
				txtTel.setText(txtTel1.getText() + txtTel2.getText() + txtTel3.getText()); // 전화번호 합침

				cc.send("/id " + txtId.getText());
				cc.send("/pwd " + txtPwd.getText());
				cc.send("/name " + txtName.getText());
				cc.send("/tel " + txtTel.getText());
				cc.send("/addr " + txtAddr.getText());

				JOptionPane.showMessageDialog(null, "회원등록이 완료되었습니다.", "Message", JOptionPane.INFORMATION_MESSAGE);
				new HMain(cc);
				dispose();
			}
		});
	}

	public void idChk(String msg) {
		if(msg.equals("yes")) {
			JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else if(msg.equals("no")){
			JOptionPane.showMessageDialog(null, "중복된 아이디가 있습니다.", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
//		new Join();
	}


}
