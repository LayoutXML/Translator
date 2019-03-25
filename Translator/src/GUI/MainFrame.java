package GUI;

import main.Tester;
import main.Translator;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;

@SuppressWarnings("Duplicates")
public class MainFrame implements ActionListener{

	private JFrame frame;
	private JTextArea textOriginal;
	private JTextArea textTranslation;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnPrintDict;
	private JButton btnTranslateText;
	private JMenuBar menuBar;
	private Translator translator;
	private int languageIndex = 0;

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
		frame.setResizable(false);
		frame.setTitle("Translator");

		translator = new Translator();
		translator.initialise();
		translator.readFile(languageIndex);
		
		JButton btnTranslate = new JButton("Translate");
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translate) {
				translate();
			}
		});
		btnTranslate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTranslate.setBounds(374, 210, 168, 57);
		frame.getContentPane().add(btnTranslate);

		JButton btnFlip = new JButton("<>");
		btnFlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translate) {
				translator.flipDictionary(languageIndex);
			}
		});
		btnFlip.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFlip.setBounds(426, 118, 60, 60);
		frame.getContentPane().add(btnFlip);
		
		textOriginal = new JTextArea();
		textOriginal.setLineWrap(true);
		textOriginal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textOriginal.setBounds(164, 107, 228, 83);
		textOriginal.setColumns(10);
		frame.getContentPane().add(textOriginal);
		
		textTranslation = new JTextArea();
		textTranslation.setLineWrap(true);
		textTranslation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textTranslation.setColumns(10);
		textTranslation.setBounds(519, 107, 228, 83);
		frame.getContentPane().add(textTranslation);
		
		btnAdd = new JButton("Add a word");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent addWordToDictionary) {
				AddFrame add = new AddFrame("", translator, languageIndex);
				add.setVisible(true);
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAdd.setBounds(130, 318, 185, 75);
		frame.getContentPane().add(btnAdd);
		
		btnRemove = new JButton("Remove a word");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent removeWordFromDictionary) {
				RemoveFrame remove = new RemoveFrame(translator, languageIndex);
				remove.setVisible(true);
			}
		});
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRemove.setBounds(130, 429, 185, 75);
		frame.getContentPane().add(btnRemove);
		
		btnPrintDict = new JButton("Print Dictionary");
		btnPrintDict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent printDictionary) {
				PrintFrame print = new PrintFrame(translator,languageIndex);
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
				FileFrame frame = new FileFrame(translator, languageIndex);
				frame.setVisible(true);
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
		JMenu menu = new JMenu("Language");
		JMenuItem item = new JMenuItem("Lithuanian");
		item.setIcon(new ImageIcon("lithuania.png"));
		item.addActionListener(this);
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem("Swedish");
		item.setIcon(new ImageIcon("sweden.png"));
		item.addActionListener(this);
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem("Albanian");
		item.setIcon(new ImageIcon("albania.png"));
		item.addActionListener(this);
		menu.add(item);
		menuBar.add(menu);
	}

	private void translate() {
		textTranslation.setText("");
		String input, translation, characters = "", lastTranslation="", lastOriginalWord="";
		int indexOf;
		boolean error = false, isFirst = true, lastEmpty=false;
		input = textOriginal.getText();
		String[] words = input.split("\\W+");
		long startTime = Calendar.getInstance().getTimeInMillis();
		for (String word : words) {
			if (!error) {
				indexOf = input.indexOf(word);
				if (indexOf == -1) {
					error = true;
				} else {
					characters = input.substring(0, indexOf);
					input = input.substring(indexOf + word.length());
				}
			}
			boolean phrasalVerb = false;
			for (String phrase : translator.getPhrasalVerbs()) {
				if (word.toLowerCase().equals(phrase)) {
					phrasalVerb = true;
				}
			}
			boolean capitalize = lastEmpty && (lastTranslation.contains(".") || lastTranslation.contains("?") || lastTranslation.contains("!"));
			if (phrasalVerb) {
				translation = translator.translate(lastOriginalWord + " " + word, languageIndex);
			} else {
				textTranslation.setText(textTranslation.getText()+lastTranslation);
				translation = translator.translate(word, languageIndex);
			}
			lastTranslation="";
			if (isFirst || characters.contains(".") || characters.contains("?") || characters.contains("!") || capitalize) {
				if (translation.length() > 0) {
					translation = translation.substring(0, 1).toUpperCase() + translation.substring(1);
					if (isFirst) {
						isFirst = false;
					}
				}
			}
			if ((!error && !lastEmpty) || (characters.contains("\n"))) {
				lastTranslation+=characters;
			}
			if (!translation.equals("")) {
				lastTranslation+=translation;
				lastEmpty = false;
			} else {
				lastEmpty = true;
			}
			lastOriginalWord = word;
		}
		textTranslation.setText(textTranslation.getText()+lastTranslation + input);
		long endTime = Calendar.getInstance().getTimeInMillis();
		double wordsPerSecond = words.length*1d/((endTime-startTime)/1000d);
		System.out.format("\n\nSpeed: %.2f words per second. (It took "
				+ (endTime - startTime) + "ms (" + (endTime - startTime) / 1000d + " seconds) to translate " + words.length + " words)\n",wordsPerSecond);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String text = source.getText();
		switch (text) {
			case "Lithuanian":
				languageIndex = 0;
				break;
			case "Swedish":
				languageIndex = 1;
				break;
			case "Albanian":
				languageIndex = 2;
				break;
			default:
				languageIndex = 0;
				break;
		}
		translator.readFile(languageIndex);
	}

}
