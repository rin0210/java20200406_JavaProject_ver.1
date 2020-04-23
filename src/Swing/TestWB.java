package Swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Client.CConnect;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.UIManager;

public class TestWB extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestWB frame = new TestWB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestWB() {
//		super("예약확인");

		getContentPane().setLayout(null); // 배치관리자 해제
		
		JList list = new JList();
		list.setBounds(0, 0, 324, 74);
		getContentPane().add(list);
		
		JLabel lblNewLabel = new JLabel("\uC608\uC57D\uBC88\uD638");
		lblNewLabel.setBounds(25, 100, 85, 25);
		getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("\uC131       \uBA85");
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.setBounds(25, 135, 85, 25);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\uCCB4 \uD06C \uC778");
		label_1.setBounds(25, 170, 85, 25);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\uCCB4\uD06C\uC544\uC6C3");
		label_2.setBounds(25, 205, 85, 25);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\uC219\uBC15\uAE30\uAC04");
		label_3.setBounds(25, 240, 85, 25);
		getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("\uD569     \uACC4");
		label_4.setBounds(25, 275, 85, 25);
		getContentPane().add(label_4);
		
		
		this.setBounds(0, 0, 340, 480);
		setLocationRelativeTo(null); // 바탕화면 한가운데 띄우기

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
