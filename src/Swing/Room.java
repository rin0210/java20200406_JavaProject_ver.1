package Swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import Client.CConnect;
import JDBC.RDTO;

public class Room extends JFrame {
	private String header[] = { "객실유형", "정원", "요금", "상세보기" };

	private DefaultTableModel tableModel = new DefaultTableModel(null, header);
	private JTable table = new JTable(tableModel);
	private JTableHeader th = table.getTableHeader();
	private JScrollPane tableScroll = new JScrollPane(table);

	private JPanel wholeTab = new JPanel();
	private JPanel underTab = new JPanel();

	private JButton outBtn;

	private CConnect cc = null;
	private ArrayList<String[]> roomList = null;
	private String[] in = new String[3];

	public Room(CConnect cc, ArrayList<String[]> roomList) {
		super("객실예약");
		this.cc = cc;
		this.roomList = roomList;

		th.setPreferredSize(new Dimension(0, 50)); // 헤더 사이즈 지정
		th.setFont(new Font("돋움", Font.PLAIN, 14));

		this.setBounds(0, 0, 500, 400);
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

	private void init() {
		for (int i = 0; i < roomList.size(); i++) {
			tableModel.addRow(roomList.get(i));
		}
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

	private void setting() {
		table.setRowHeight(50); // 행 높이 지정
		table.getTableHeader().setReorderingAllowed(false); // 테이블 컬럼의 이동을 방지, 마우스드래그로 함부로 이동되지 못하게
		table.setShowVerticalLines(false); // 셀 사이에 세로선을 그릴지 여부
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 테이블 행을 한개만 선택할 수 있음

		DefaultTableCellRenderer ts = new DefaultTableCellRenderer(); // 셀 안에 들어가는 데이터의 정렬을 조절
		ts.setHorizontalAlignment(SwingConstants.CENTER); // 셀에 출력될 데이터 정렬 지정

		TableColumnModel tc = table.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}

//	public static void main(String[] args) {
//		new Room();
//	}

}
