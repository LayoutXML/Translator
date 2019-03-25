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
	private JTextField txtEnglish;
	private JTextField txtOutput;
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
				System.out.println("TRANSLATING...");	//default message to console (can remove)
				//TODO Make this button read input from left text box and print the translation to the right text box
			}
		});
		btnTranslate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTranslate.setBounds(374, 210, 168, 57);
		frame.getContentPane().add(btnTranslate);
		
		txtEnglish = new JTextField();
		txtEnglish.setText("English");
		txtEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent inputText) {
			}
		});
		txtEnglish.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEnglish.setBounds(164, 107, 228, 83);
		frame.getContentPane().add(txtEnglish);
		txtEnglish.setColumns(10);
		
		txtOutput = new JTextField();
		txtOutput.setText("Output");
		txtOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent outputText) {
			}
		});
		txtOutput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtOutput.setColumns(10);
		txtOutput.setBounds(519, 107, 228, 83);
		frame.getContentPane().add(txtOutput);
		
		JButton btnAdd = new JButton("Add a word");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent addWordToDictionary) {
				AddFrame add = new AddFrame();
				add.setVisible(true);
				//TODO Make this fully functional
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAdd.setBounds(130, 318, 185, 75);
		frame.getContentPane().add(btnAdd);
		
		btnRemove = new JButton("Remove a word");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent removeWordFromDictionary) {
				RemoveFrame remove = new RemoveFrame();
				remove.setVisible(true);
				//TODO Make this fully functional
			}
		});
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRemove.setBounds(130, 429, 185, 75);
		frame.getContentPane().add(btnRemove);
		
		btnPrintDict = new JButton("Print Dictionary");
		btnPrintDict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent printDictionary) {
				PrintFrame print = new PrintFrame();
				print.setVisible(true);
				//TODO Print entire dictionary to new frame
			}
		});
		btnPrintDict.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrintDict.setBounds(564, 318, 200, 75);
		frame.getContentPane().add(btnPrintDict);
		
		btnTranslateText = new JButton("Translate a text file");
		btnTranslateText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translateFile) {
				FileFrame fframe = new FileFrame();
				FileFrame.setVisible(true);
				OpenFile file = new OpenFile();
				try {
					file.pick();
				} catch (Exception e) {
					e.printStackTrace();
				}
				txtOutput.setText(file.sb.toString());
				//TODO Change text output to an output field on fframe & ensure each word is printed
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
				//TODO Change dictionary used to Albanian
			}
		});
		mntmAlbanian.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mntmAlbanian.setIcon(new ImageIcon("C:\\Users\\Calum\\Documents\\GitHub\\Translator\\Translator\\icons\\Albania.png"));
		mnLanguage.add(mntmAlbanian);
		
		mntmLithuanian = new JMenuItem("Lithuanian");
		mntmLithuanian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent changeLanguageToLithuanian) {
				//TODO Change dictionary used to Lithuanian
			}
		});
		mntmLithuanian.setIcon(new ImageIcon("C:\\Users\\Calum\\Documents\\GitHub\\Translator\\Translator\\icons\\Lithuania.png"));
		mntmLithuanian.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnLanguage.add(mntmLithuanian);
		
		mntmSwedish = new JMenuItem("Swedish");
		mntmSwedish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent changeLanguageToSwedish) {
				//TODO Change dictionary used to Swedish
			}
		});
		mntmSwedish.setIcon(new ImageIcon("C:\\Users\\Calum\\Documents\\GitHub\\Translator\\Translator\\icons\\Sweden.png"));
		mntmSwedish.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnLanguage.add(mntmSwedish);
	}
}
