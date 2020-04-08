package Swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class TestWB extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	

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
		getContentPane().setLayout(null); // 배치관리자 해제
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("돋움", Font.BOLD, 12));
		lblNewLabel.setBounds(119, 36, 344, 37);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(32, 109, 92, 27);
		getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(32, 141, 146, 37);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("New label");
		label.setBounds(190, 109, 92, 27);
		getContentPane().add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(190, 141, 146, 37);
		getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(348, 141, 37, 37);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(385, 141, 37, 37);
		getContentPane().add(lblNewLabel_3);
		
		JLabel label_2 = new JLabel("New label");
		label_2.setBounds(460, 141, 37, 37);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("New label");
		label_3.setBounds(535, 141, 37, 37);
		getContentPane().add(label_3);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(423, 141, 37, 37);
		getContentPane().add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(498, 141, 37, 37);
		getContentPane().add(comboBox_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(218, 218, 146, 37);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(119, 104, 37, 37);
		getContentPane().add(btnNewButton_1);
		
		JButton button = new JButton("New button");
		button.setBounds(279, 104, 37, 37);
		getContentPane().add(button);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(12, 299, 97, 23);
		getContentPane().add(btnNewButton_2);
		
		this.setBounds(0, 0, 620, 370);
		setLocationRelativeTo(null); // 바탕화면 한가운데 띄우기
	}
}
