package Swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Client.CConnect;

public class Room extends JFrame {
	String header[] = { "객실유형", "정원", "요금", "상세보기" };

	DefaultTableModel tableModel = new DefaultTableModel(null, header);
	JTable table = new JTable(tableModel);
	JTableHeader th = table.getTableHeader();
	JScrollPane tableScroll = new JScrollPane(table);

	JPanel wholeTab = new JPanel();
	JPanel underTab = new JPanel();

	JButton outBtn;

	private CConnect cc = null;

	public Room() {
		super("객실예약");
		th.setPreferredSize(new Dimension(0, 50)); // 헤더 사이즈 지정
		th.setFont(new Font("돋움", Font.PLAIN, 14));

		this.setBounds(0, 0, 600, 500);
		setLocationRelativeTo(null); // 바탕화면 한가운데 띄우기

		init();
		setting();
		createWholeTab();
		createUnderTab();

		this.add(th);
		this.add(tableScroll);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private void createUnderTab() {
		underTab.setLayout(new FlowLayout(FlowLayout.LEFT));
		outBtn = new JButton("< 이전");
		outBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		outBtn.setBorderPainted(false);
		outBtn.setContentAreaFilled(false);
		outBtn.setFocusPainted(false);
		underTab.add(outBtn);
		this.add(underTab, "South");
		
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
				dispose();
			}
		});
	}

	private void createWholeTab() {
		wholeTab.add(tableScroll);
		this.add(wholeTab, "Center");
	}

	private void init() {

	}

	private void setting() {

	}

	public static void main(String[] args) {
		new Room();
	}

}
