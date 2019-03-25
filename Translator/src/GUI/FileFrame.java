package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FileFrame extends JFrame {

	private JPanel contentPane;
	public JTextPane fileOutput;
	private JButton btnNewButton;
	private JButton btnTranslate;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileFrame frame = new FileFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public FileFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 490, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddingANew = new JLabel("Translating a text file");
		lblAddingANew.setBounds(5, 5, 418, 20);
		contentPane.add(lblAddingANew);
		
		fileOutput = new JTextPane();
		fileOutput.setText("Text");
		fileOutput.setBounds(252, 70, 212, 456);
		contentPane.add(fileOutput);
		
		btnNewButton = new JButton("choose file");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(132, 23, 89, 23);
		contentPane.add(btnNewButton);
		
		btnTranslate = new JButton("translate");
		btnTranslate.setBounds(286, 23, 89, 23);
		contentPane.add(btnTranslate);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("Text");
		textPane.setBounds(9, 70, 212, 456);
		contentPane.add(textPane);
	}
}
