import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;

public class FileFrame extends JFrame {

	private JPanel contentPane;
	public JTextPane fileOutput;
	private JScrollBar scrollBar;


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
		fileOutput.setBounds(15, 29, 408, 499);
		contentPane.add(fileOutput);
		
		scrollBar = new JScrollBar();
		scrollBar.setBounds(427, 28, 26, 502);
		contentPane.add(scrollBar);
	}
}
