import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnRemove;
	private JButton btnPrintDict;
	private JButton btnTranslateText;
	private JMenuItem mntmLanguage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnTranslate = new JButton("Translate");
		btnTranslate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTranslate.setBounds(374, 210, 168, 57);
		frame.getContentPane().add(btnTranslate);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(164, 107, 228, 83);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_1.setColumns(10);
		textField_1.setBounds(519, 107, 228, 83);
		frame.getContentPane().add(textField_1);
		
		JButton btnAdd = new JButton("Add a word");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAdd.setBounds(130, 318, 185, 75);
		frame.getContentPane().add(btnAdd);
		
		btnRemove = new JButton("Remove a word");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRemove.setBounds(130, 429, 185, 75);
		frame.getContentPane().add(btnRemove);
		
		btnPrintDict = new JButton("Print Dictionary");
		btnPrintDict.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrintDict.setBounds(564, 318, 200, 75);
		frame.getContentPane().add(btnPrintDict);
		
		btnTranslateText = new JButton("Translate a text file");
		btnTranslateText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTranslateText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTranslateText.setBounds(564, 429, 200, 75);
		frame.getContentPane().add(btnTranslateText);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mntmLanguage = new JMenuItem("Language");
		menuBar.add(mntmLanguage);
	}
}
