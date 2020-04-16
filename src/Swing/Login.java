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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Client.CConnect;

public class Login extends JFrame {
	private CConnect cc = null;
	private HMain hm = null;
	private Choice ch = null;

	private JLabel idChkLabel = new JLabel(); // 아이디 체크 라벨
	private JLabel pwdChkLabel = new JLabel(); // 비밀번호 체크 라벨
	private JLabel labelId, labelPwd;
	private JTextField txtId;
	private JButton loginBtn, outBtn;
	private JPasswordField txtPwd;

	public Login(CConnect cc) {
		super("로그인");
		this.cc = cc;
		this.setLayout(null); // 배치관리자 해제
		this.setBounds(0, 0, 250, 280);
		setLocationRelativeTo(null); // 바탕화면 한가운데 띄우기

		labelTxtsetting();
		buttonSetting();
		checkLabel();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	// 라벨, 텍스트필드 셋팅
	private void labelTxtsetting() {
		labelId = new JLabel("아 이 디");
		labelId.setFont(new Font("돋움", Font.PLAIN, 12));
		labelPwd = new JLabel("비밀번호");
		labelPwd.setFont(new Font("돋움", Font.PLAIN, 12));

		labelId.setBounds(30, 60, 100, 20);
		labelPwd.setBounds(30, 100, 100, 20);

		this.add(labelId);
		this.add(labelPwd);

		txtId = new JTextField();
		txtPwd = new JPasswordField();
		txtPwd.setEchoChar('*');

		txtId.setBounds(100, 60, 100, 20);
		txtPwd.setBounds(100, 100, 100, 20);

		this.add(txtId);
		this.add(txtPwd);
	}

	// 버튼 셋팅
	private void buttonSetting() {
		loginBtn = new JButton("로그인");
		loginBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		loginBtn.setFocusPainted(false);
		loginBtn.setBounds(76, 150, 80, 25);
		this.add(loginBtn);

		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean chk = true;

				String pwd = "";
				char[] secret_pwd = txtPwd.getPassword(); // 패스워드 읽어와서 char[]배열에 저장
				for (char c : secret_pwd) {
					pwd = pwd + c;
				}
				if (txtId.getText().equals("")) {
					idChkLabel.setText(" *아이디를 입력해주세요.");
					chk = false;
				} else if (pwd.equals("")) {
					pwdChkLabel.setText(" *비밀번호를 입력해주세요.");
					chk = false;
				}

				if (chk) {
					idChkLabel.setText("");
					pwdChkLabel.setText("");

					String msg = ">login " + txtId.getText() + " " + pwd;
					cc.send(msg);
					chkMsg();
				}
			}
		});

		outBtn = new JButton("< 이전");
		outBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		outBtn.setBorderPainted(false);
		outBtn.setContentAreaFilled(false);
		outBtn.setFocusPainted(false);
		outBtn.setBounds(5, 200, 70, 30);
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
				hm = HMain.getInstance();
				hm.setVisible(true);
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
		idChkLabel.setFont(new Font("돋움", Font.PLAIN, 12)); // 아이디 체크 라벨
		idChkLabel.setForeground(Color.RED);
		idChkLabel.setBounds(30, 78, 200, 20);
		this.add(idChkLabel);

		pwdChkLabel.setFont(new Font("돋움", Font.PLAIN, 12)); // 비밀번호 체크 라벨
		pwdChkLabel.setForeground(Color.RED);
		pwdChkLabel.setBounds(30, 118, 250, 20);
		this.add(pwdChkLabel);
	}

	// 아이디 비밀번호 체크 메세지
	public void chkMsg() {
		String msg = cc.receive();
		if (msg.indexOf("loginYes") > -1) { // 로그인 성공
			JOptionPane.showMessageDialog(null, "성공적으로 로그인되었습니다.", "Message", JOptionPane.INFORMATION_MESSAGE);
			ch = new Choice(cc);
			dispose();

		} else if (msg.indexOf("loginNo") > -1) { // 로그인 실패
			JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 다시 확인해주세요.", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}

//	public static void main(String[] args) {
//		new Login();
//	}
}
