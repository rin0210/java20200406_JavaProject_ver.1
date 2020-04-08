package Swing;

import javax.swing.JFrame;

public class Room extends JFrame {

	public Room() {

		this.setLayout(null); // 배치관리자 해제
		this.setBounds(0, 0, 250, 280);
		setLocationRelativeTo(null); // 바탕화면 한가운데 띄우기

//		setting();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
