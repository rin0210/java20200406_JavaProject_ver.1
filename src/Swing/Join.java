package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Client.CConnect;

public class Join extends JFrame {
	private CConnect cc = null;
	private boolean idChk = false;
	private String[] membership = new String[5];

	private JLabel idCheckLabel = new JLabel(); // 아이디 체크
	private JLabel pwdCheckLabel = new JLabel(" *비밀번호는 6글자 이상 입력해주세요."); // 비밀번호 체크
	private JLabel allCheckLabel = new JLabel(); // 전체 입력사항 체크
	private JTextField txtId, txtPwd, txtName, txtTel1, txtTel2, txtTel3, txtAddr;
	private JLabel hipen1, hipen2;

	public Join(CConnect cc) {
		super("회원가입");
		this.cc = cc;
		this.setLayout(null); // 배치관리자 해제
		this.setBounds(0, 0, 350, 440);
		setLocationRelativeTo(null); // 프레임창이 바탕화면 한가운데 띄워짐

		labelSetting();
		txtSetting();
		buttonSetting();
		checkLabel();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	// 라벨 셋팅
	private void labelSetting() {
		JLabel label = new JLabel("회원정보 입력");
		label.setFont(new Font("돋움", Font.BOLD, 17));
		JLabel labelLineS = new JLabel("──────────────────────");
		JLabel labelId = new JLabel("아 이 디");
		labelId.setFont(new Font("돋움", Font.PLAIN, 15));
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
		labelPwd.setBounds(30, 140, 100, 20);
		labelName.setBounds(30, 180, 100, 20);
		labelTel.setBounds(30, 220, 100, 20);
		labelAddr.setBounds(30, 260, 100, 20);
		labelLineE.setBounds(25, 300, 320, 20);

		this.add(label);
		this.add(labelLineS);
		this.add(labelId);
		this.add(labelPwd);
		this.add(labelName);
		this.add(labelTel);
		this.add(labelAddr);
		this.add(labelLineE);
	}

	// 텍스트필드 셋팅
	private void txtSetting() {
		txtId = new JTextField();
		txtPwd = new JTextField();
		txtName = new JTextField();
		txtTel1 = new JTextField();
		txtTel2 = new JTextField();
		txtTel3 = new JTextField();
		hipen1 = new JLabel("-");
		hipen2 = new JLabel("-");
		txtAddr = new JTextField();

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
	}

	// 버튼 셋팅
	private void buttonSetting() {
		JButton checkBtn = new JButton("중복확인");
		checkBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		checkBtn.setFocusPainted(false);
		checkBtn.setBounds(225, 100, 85, 20);
		this.add(checkBtn);

		checkBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtId.getText().equals("")) {
					idCheckLabel.setText(" *아이디를 입력해주세요.");
				} else {
					idCheckLabel.setText("");
					idChk = true;
					cc.send("*id " + txtId.getText());
				}
			}
		});

		JButton applyBtn = new JButton("가입 신청");
		applyBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		applyBtn.setFocusPainted(false);
		applyBtn.setBounds(120, 330, 90, 25);
		this.add(applyBtn);

		applyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean chk = true;
				if (txtId.getText().equals("") || txtPwd.getText().equals("") || txtName.getText().equals("")
						|| txtTel1.getText().equals("") || txtTel2.getText().equals("") || txtTel3.getText().equals("")
						|| txtAddr.getText().equals("")) {
					allCheckLabel.setText("등록이 필요한 기본정보입니다. 모두 입력해주세요.");
					chk = false;

				} else if (txtPwd.getText().length() < 6) {
//					pwdCheckLabel.setText(" *비밀번호는 6글자 이상 입력해주세요.");
					pwdCheckLabel.setForeground(Color.RED);
					chk = false;
				}

				if (chk) {
					pwdCheckLabel.setText("");
					if (idChk) {
						JTextField txtTel = new JTextField();
						txtTel.setText(txtTel1.getText() + txtTel2.getText() + txtTel3.getText()); // 전화번호 합침
						String msg = "/join ";

						membership[0] = txtId.getText() + " ";
						membership[1] = txtPwd.getText() + " ";
						membership[2] = txtName.getText() + " ";
						membership[3] = txtTel.getText() + " ";
						membership[4] = txtAddr.getText();

						for (int i = 0; i < membership.length; i++) {
							msg = msg + membership[i];
						}
						cc.send(msg);

					} else {
						JOptionPane.showMessageDialog(null, "아이디 중복을 확인해주세요.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		JButton outBtn = new JButton("< 이전");
		outBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		outBtn.setBorderPainted(false);
		outBtn.setContentAreaFilled(false);
		outBtn.setFocusPainted(false);
		outBtn.setBounds(5, 360, 70, 30);
		this.add(outBtn);
		
		outBtn.addMouseListener(new MouseListener() { // 나가기 버튼

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				outBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				outBtn.setForeground(Color.GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				new HMain(cc);
				dispose();
			}
		});

//		outBtn.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				new HMain(cc);
//				dispose();
//			}
//		});
	}

	// 입력 체크 라벨 설정
	private void checkLabel() {
		idCheckLabel.setFont(new Font("돋움", Font.PLAIN, 12)); // 아이디 체크 라벨
		idCheckLabel.setForeground(Color.RED);
		idCheckLabel.setBounds(30, 118, 200, 20);
		this.add(idCheckLabel);

		pwdCheckLabel.setFont(new Font("돋움", Font.PLAIN, 12)); // 비밀번호 체크 라벨
		pwdCheckLabel.setForeground(Color.BLUE);
		pwdCheckLabel.setBounds(30, 158, 250, 20);
		this.add(pwdCheckLabel);

		allCheckLabel.setFont(new Font("돋움", Font.PLAIN, 12)); // 전체 정보 체크 라벨
		allCheckLabel.setForeground(Color.RED);
		allCheckLabel.setBounds(30, 45, 300, 20);
		this.add(allCheckLabel);
	}

	// 알림 메시지 팝업
	public void chkMsg(String msg) {
		if (msg.indexOf("yes") > -1) {
			JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.", "Message", JOptionPane.INFORMATION_MESSAGE);

		} else if (msg.indexOf("no") > -1) {
			JOptionPane.showMessageDialog(null, "이미 존재하고 있는 아이디입니다.", "Message", JOptionPane.INFORMATION_MESSAGE);

		} else if (msg.indexOf("memberOk") > -1) {
			JOptionPane.showMessageDialog(null, "회원등록이 완료되었습니다.", "Message", JOptionPane.INFORMATION_MESSAGE);
			new HMain(cc);
			dispose();
		}
	}

//	public static void main(String[] args) {
//		new Join();
//	}

}
