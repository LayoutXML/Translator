import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.ImageIcon;

public class MainFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnRemove;
	private JButton btnPrintDict;
	private JButton btnTranslateText;
	private JMenuBar menuBar;
	private JMenu mnLanguage;
	private JMenuItem mntmLithuanian;
	private JMenuItem mntmSwedish;
	private JMenuItem mntmSpanish;
	private JMenuItem mntmAlbanian;

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
		
		Tester tester = new Tester();
        tester.initialise();
        tester.process();
        
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
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translate) {
			}
		});
		btnTranslate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTranslate.setBounds(374, 210, 168, 57);
		frame.getContentPane().add(btnTranslate);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent inputText) {
			}
		});
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(164, 107, 228, 83);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent outputText) {
			}
		});
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_1.setColumns(10);
		textField_1.setBounds(519, 107, 228, 83);
		frame.getContentPane().add(textField_1);
		
		JButton btnAdd = new JButton("Add a word");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent addWordToDictionary) {
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAdd.setBounds(130, 318, 185, 75);
		frame.getContentPane().add(btnAdd);
		
		btnRemove = new JButton("Remove a word");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent removeWordFromDictionary) {
			}
		});
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRemove.setBounds(130, 429, 185, 75);
		frame.getContentPane().add(btnRemove);
		
		btnPrintDict = new JButton("Print Dictionary");
		btnPrintDict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent printDictionary) {
			}
		});
		btnPrintDict.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrintDict.setBounds(564, 318, 200, 75);
		frame.getContentPane().add(btnPrintDict);
		
		btnTranslateText = new JButton("Translate a text file");
		btnTranslateText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translateFile) {
			}
		});
		btnTranslateText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTranslateText.setBounds(564, 429, 200, 75);
		frame.getContentPane().add(btnTranslateText);
		
		JLabel lblJavaTranslator = new JLabel("Java Translator");
		lblJavaTranslator.setFont(new Font("Cambria Math", Font.PLAIN, 22));
		lblJavaTranslator.setBounds(375, 23, 152, 59);
		frame.getContentPane().add(lblJavaTranslator);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnLanguage = new JMenu("Language");
		menuBar.add(mnLanguage);
		
		mntmAlbanian = new JMenuItem("Albanian");
		mntmAlbanian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent changeLanguageToAlbanian) {
			}
		});
		mntmAlbanian.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mntmAlbanian.setIcon(new ImageIcon("C:\\Users\\Calum\\Documents\\GitHub\\Translator\\Translator\\icons\\Albania.png"));
		mnLanguage.add(mntmAlbanian);
		
		mntmLithuanian = new JMenuItem("Lithuanian");
		mntmLithuanian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent changeLanguageToLithuanian) {
			}
		});
		mntmLithuanian.setIcon(new ImageIcon("C:\\Users\\Calum\\Documents\\GitHub\\Translator\\Translator\\icons\\Lithuania.png"));
		mntmLithuanian.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnLanguage.add(mntmLithuanian);
		
		mntmSpanish = new JMenuItem("Spanish");
		mntmSpanish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent changeLanguageToSpanish) {
			}
		});
		mntmSpanish.setIcon(new ImageIcon("C:\\Users\\Calum\\Documents\\GitHub\\Translator\\Translator\\icons\\Spain.png"));
		mntmSpanish.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnLanguage.add(mntmSpanish);
		
		mntmSwedish = new JMenuItem("Swedish");
		mntmSwedish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent changeLanguageToSwedish) {
			}
		});
		mntmSwedish.setIcon(new ImageIcon("C:\\Users\\Calum\\Documents\\GitHub\\Translator\\Translator\\icons\\Sweden.png"));
		mntmSwedish.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnLanguage.add(mntmSwedish);
	}
}
