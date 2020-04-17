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

public class TestWB extends JFrame {
	private String header[] = { "객실유형", "정원", "요금" };

	private DefaultTableModel tableModel = new DefaultTableModel(null, header);
	private JTable table = new JTable(tableModel);
	private JTableHeader th = table.getTableHeader();
	private JScrollPane tableScroll = new JScrollPane(table);

	private JPanel wholeTab = new JPanel();
	private JPanel underTab = new JPanel();
	private JPanel rigthTab = new JPanel();

	private JButton outBtn;

	private CConnect cc = null;
	private ArrayList<String[]> roomList = null;
	private String[] in = new String[3];

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
		
	}
}