package Swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.CConnect;

public class HMain extends JFrame {
	Join j;

	CConnect cc = null;

	public HMain(CConnect cc) {
		this.cc = cc;
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
		loginBtn.setBorderPainted(false); // 테두리선
		loginBtn.setContentAreaFilled(false);
		loginBtn.setFocusPainted(false);
		loginP.add(loginBtn);

		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
				dispose();

			}
		});

		JButton joinBtn = new JButton("회원가입");
		joinBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		joinBtn.setBorderPainted(false);
		joinBtn.setContentAreaFilled(false);
		joinBtn.setFocusPainted(false);
		loginP.add(joinBtn);

		joinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				j = new Join(cc);
				dispose();
			}
		});

		JButton nonMBtn = new JButton("비회원예약");
		nonMBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		nonMBtn.setBorderPainted(false);
		nonMBtn.setContentAreaFilled(false);
		nonMBtn.setFocusPainted(false);
		loginP.add(nonMBtn);
		nP.add(loginP, "North");

		nonMBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Choice();
				dispose();
			}
		});

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

	public void setMsg(String msg) {
		System.out.println("HMain왔니?");
		j.idChk(msg);
	}
	
	public static void main(String[] args) {
//		new HMain();
	}


}
