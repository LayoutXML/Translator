package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Option2 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnSubmit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Option2 frame = new Option2();
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
	public Option2() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddText = new JLabel("Add word or phrase");
		lblAddText.setBounds(158, 84, 174, 34);
		contentPane.add(lblAddText);
		
		textField = new JTextField();
		textField.setBounds(122, 129, 174, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(168, 180, 89, 23);
		contentPane.add(btnSubmit);
	}

}
