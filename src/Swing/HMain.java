package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.CConnect;
import JDBC.DAOCenter;

public class HMain extends JFrame {
	private CConnect cc = null;
	private Join j;
	private Login l;

	// 싱글톤
	private static HMain single = null;

	public static HMain getInstance() {
		if (single == null) {
			single = new HMain();
		}
		return single;
	}

	public void admin(CConnect cc) {
		this.cc = cc;
	}

	private HMain() {
		this.setLayout(new BorderLayout());
		this.setBounds(0, 0, 700, 600);
		setLocationRelativeTo(null); // 프레임창이 바탕화면 한가운데 띄워짐

		createCP();
		createNP();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private void createNP() {
		JPanel nP = new JPanel();
//		nP.setLayout(new FlowLayout(FlowLayout.RIGHT)); // 오른쪽 정렬
		nP.setLayout(new BorderLayout());
		JPanel hotelP = new JPanel();
		JLabel hotelLable = new JLabel("HOTEL");
		hotelLable.setFont(new Font("Serif", Font.BOLD, 40)); // 글씨체, 굵기, 크기
		hotelP.add(hotelLable);
		nP.add(hotelP, "Center");

		JPanel loginP = new JPanel();
		loginP.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton loginBtn = new JButton("로그인");
		loginBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		loginBtn.setBorderPainted(false); // 버튼 테두리선 설정
		loginBtn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		loginBtn.setFocusPainted(false); // 포커스 표시 설정
		loginP.add(loginBtn);

		loginBtn.addMouseListener(new MouseListener() { // 로그인 버튼

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				loginBtn.setForeground(Color.GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				l = new Login(cc);
				dispose();
			}
		});

//		loginBtn.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				l = new Login(cc);
//				dispose();
//			}
//		});

		JButton joinBtn = new JButton("회원가입");
		joinBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		joinBtn.setBorderPainted(false);
		joinBtn.setContentAreaFilled(false);
		joinBtn.setFocusPainted(false);
		loginP.add(joinBtn);

		joinBtn.addMouseListener(new MouseListener() { // 회원가입 버튼

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				joinBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				joinBtn.setForeground(Color.GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				j = new Join(cc);
				dispose();
			}
		});

//		joinBtn.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				j = new Join(cc);
//				dispose();
//			}
//		});

		JButton nonMBtn = new JButton("비회원예약");
		nonMBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		nonMBtn.setBorderPainted(false);
		nonMBtn.setContentAreaFilled(false);
		nonMBtn.setFocusPainted(false);
		loginP.add(nonMBtn);
		nP.add(loginP, "North");

		nonMBtn.addMouseListener(new MouseListener() { // 비회원예약 버튼

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nonMBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nonMBtn.setForeground(Color.GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

//		nonMBtn.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				new Choice(cc);
//				dispose();
//			}
//		});

		this.add(nP, "North");
	}

	private void createCP() {
		JPanel cP = new JPanel();
		ImageIcon hc = new ImageIcon("HMain.jpg");
		Image hm = hc.getImage(); // 이미지아이콘을 이미지로 변경
		hm = hm.getScaledInstance(700, 460, Image.SCALE_SMOOTH); // 이미지 사이즈 조절
		hc = new ImageIcon(hm); // 다시 이미지아이콘으로 변경

		JLabel labelImage = new JLabel(hc);
		cP.add(labelImage);
		this.add(cP, "Center");
	}

//	public void setMsg(String msg) {
//		if (msg.indexOf("/join") == 0) {
//			j.chkMsg(msg);
//		} else if (msg.indexOf("/login") == 0) {
//			l.chkMsg(msg);
//		}
//	}

	public void visible() {
		this.setVisible(true);
	}

}
