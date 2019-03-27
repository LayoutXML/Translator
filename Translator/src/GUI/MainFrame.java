package GUI;

import main.Tester;
import main.Translator;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;


/**
 * Class that handles main GUI screen
 */
public class MainFrame implements ActionListener{

	private JFrame frame;
	private JTextArea textOriginal;
	private JTextArea textTranslation;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnPrintDict;
	private JButton btnTranslateText;
	private JLabel lblLangFrom;
	private JLabel lblLangTo;
	private JMenuBar menuBar;
	private Translator translator;
	private int languageIndex = 0;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private String languageFrom = "English";
	private String languageTo = "Lithuanian";
	private boolean[] englishIsOnLeft = {true,true,true};
	private boolean[] dictionaryLoaded;
	private boolean isAddNewWordsToDictOptionEnabled = false;
	private JCheckBox chckbxNewCheckBox;
	private JLabel lblFlagIndicator;
	private JMenuItem item_1;
	private JMenuItem item_2;

	/**
	 * Launch program
	 * @param args args
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
		frame.setMinimumSize(new Dimension(1280, 800));
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 1280, 797);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Translator");

		Image icon = Toolkit.getDefaultToolkit().getImage("translate.png");
	    frame.setIconImage(icon);

		dictionaryLoaded = new boolean[3];
		dictionaryLoaded[0] = true;
		dictionaryLoaded[1] = false;
		dictionaryLoaded[2] = false;

		translator = new Translator();
		translator.initialise();
		translator.readFile(languageIndex);
		
		JButton btnTranslate = new JButton("Translate");
		btnTranslate.setBounds(550, 344, 168, 57);
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translate) {
				translate();
			}
		});
		frame.getContentPane().setLayout(null);
		btnTranslate.setFont(new Font("Cambria Math", Font.PLAIN, 24));
		frame.getContentPane().add(btnTranslate);

		JButton btnFlip = new JButton("");
		btnFlip.setBounds(582, 193, 103, 92);
		btnFlip.setIcon(new ImageIcon("reverse.png"));
		btnFlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translate) {
				translator.flipDictionary(languageIndex);
				englishIsOnLeft[languageIndex] = !englishIsOnLeft[languageIndex];
				changeLabels();
			}
		});
		btnFlip.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(btnFlip);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(105, 143, 415, 361);
		frame.getContentPane().add(scrollPane);
		
		textOriginal = new JTextArea();
		textOriginal.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(textOriginal);
		textOriginal.setLineWrap(true);
		textOriginal.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 24));
		textOriginal.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(741, 143, 459, 364);
		frame.getContentPane().add(scrollPane_1);
		
		textTranslation = new JTextArea();
		textTranslation.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(textTranslation);
		textTranslation.setLineWrap(true);
		textTranslation.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 24));
		textTranslation.setColumns(10);
		
		btnAdd = new JButton("Add a word");
		btnAdd.setBounds(105, 600, 185, 75);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent addWordToDictionary) {
				AddFrame add = new AddFrame("", translator, languageIndex, englishIsOnLeft[languageIndex]);
				add.setVisible(true);
			}
		});
		btnAdd.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 24));
		frame.getContentPane().add(btnAdd);
		
		btnRemove = new JButton("Remove a word");
		btnRemove.setBounds(381, 600, 200, 75);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent removeWordFromDictionary) {
				RemoveFrame remove = new RemoveFrame(translator, languageIndex, englishIsOnLeft[languageIndex]);
				remove.setVisible(true);
			}
		});
		btnRemove.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 24));
		frame.getContentPane().add(btnRemove);
		
		btnPrintDict = new JButton("Print Dictionary");
		btnPrintDict.setBounds(699, 600, 200, 75);
		btnPrintDict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent printDictionary) {
				PrintFrame print = new PrintFrame(translator,languageIndex);
				print.setVisible(true);
			}
		});
		btnPrintDict.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 24));
		frame.getContentPane().add(btnPrintDict);
		
		btnTranslateText = new JButton("Translate a text file");
		btnTranslateText.setBounds(1000, 600, 200, 75);
		btnTranslateText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translateFile) {
				FileFrame frame = new FileFrame(translator, languageIndex);
				frame.setVisible(true);
			}
		});
		btnTranslateText.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 18));
		frame.getContentPane().add(btnTranslateText);
		
		JLabel lblJavaTranslator = new JLabel("Java Translator");
		lblJavaTranslator.setBounds(472, 31, 325, 59);
		lblJavaTranslator.setForeground(Color.WHITE);
		lblJavaTranslator.setFont(new Font("Cambria Math", Font.BOLD, 40));
		frame.getContentPane().add(lblJavaTranslator);
		
		lblLangFrom = new JLabel(languageFrom);
		lblLangFrom.setFont(new Font("Cambria Math", Font.BOLD, 30));
		lblLangFrom.setBounds(255, 86, 265, 50);
		lblLangFrom.setForeground(Color.WHITE);
		frame.getContentPane().add(lblLangFrom);
		
		lblLangTo = new JLabel(languageTo);
		lblLangTo.setFont(new Font("Cambria Math", Font.BOLD, 30));
		lblLangTo.setBounds(899, 90, 303, 41);
		lblLangTo.setForeground(Color.WHITE);
		frame.getContentPane().add(lblLangTo);
		
		chckbxNewCheckBox = new JCheckBox("Option to add new word");
		chckbxNewCheckBox.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 22));
		chckbxNewCheckBox.setForeground(Color.WHITE);
		chckbxNewCheckBox.setBackground(Color.DARK_GRAY);
		chckbxNewCheckBox.setBounds(496, 532, 459, 35);
		chckbxNewCheckBox.setSelected(isAddNewWordsToDictOptionEnabled);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent translateFile) {
				isAddNewWordsToDictOptionEnabled = chckbxNewCheckBox.isSelected();
			}
		});
		frame.getContentPane().add(chckbxNewCheckBox);
		
		lblFlagIndicator = new JLabel("");
		lblFlagIndicator.setBounds(1214, 0, 60, 57);
		lblFlagIndicator.setIcon(new ImageIcon("lithuania.png"));
		frame.getContentPane().add(lblFlagIndicator);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu menu = new JMenu("Language");
		menu.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 18));
		JMenuItem item = new JMenuItem("Lithuanian");
		item.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 18));
		item.setIcon(new ImageIcon("lithuania.png"));
		item.addActionListener(this);
		menu.add(item);
		menu.addSeparator();
		item_1 = new JMenuItem("Swedish");
		item_1.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 18));
		item_1.setIcon(new ImageIcon("sweden.png"));
		item_1.addActionListener(this);
		menu.add(item_1);
		menu.addSeparator();
		item_2 = new JMenuItem("Albanian");
		item_2.setFont(new Font("Cambria Math Semilight", Font.PLAIN, 18));
		item_2.setIcon(new ImageIcon("albania.png"));
		item_2.addActionListener(this);
		menu.add(item_2);
		menuBar.add(menu);
	}

	/**
	 * Handles translation - splits words, saves characters etc
	 */
	private void translate() {
		textTranslation.setText("");
		String input, translation, characters = "", lastTranslation="", lastOriginalWord="";
		int indexOf;
		boolean error = false, isFirst = true, lastEmpty=false;
		input = textOriginal.getText();
		String[] words = input.split("\\P{L}+");
		long startTime = Calendar.getInstance().getTimeInMillis();
		ArrayList<String> notTranslated = new ArrayList<>();
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
			if (isAddNewWordsToDictOptionEnabled) {
				if (translation.toLowerCase().equals(word.toLowerCase())) {
					notTranslated.add(word.toLowerCase());
				}
			}
		}
		textTranslation.setText(textTranslation.getText()+lastTranslation + input);
		for (String notTranslatedWord : notTranslated) {
			AddFrame add = new AddFrame(notTranslatedWord, translator, languageIndex, englishIsOnLeft[languageIndex]);
			add.setVisible(true);
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double wordsPerSecond = words.length*1d/((endTime-startTime)/1000d);
		System.out.format("\n\nSpeed: %.2f words per second. (It took "
				+ (endTime - startTime) + "ms (" + (endTime - startTime) / 1000d + " seconds) to translate " + words.length + " words)\n",wordsPerSecond);

	}

	/**
	 * Handles language switching menu
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String text = source.getText();
		switch (text) {
			case "Lithuanian":
				languageIndex = 0;
				lblFlagIndicator.setIcon(new ImageIcon("lithuania.png"));
				break;
			case "Swedish":
				languageIndex = 1;
				lblFlagIndicator.setIcon(new ImageIcon("sweden.png"));
				break;
			case "Albanian":
				languageIndex = 2;
				lblFlagIndicator.setIcon(new ImageIcon("albania.png"));
				break;
			default:
				languageIndex = 0;
				break;
		}
		if (!dictionaryLoaded[languageIndex]) {
			translator.readFile(languageIndex);
			dictionaryLoaded[languageIndex]=true;
		}
		changeLabels();
	}

	/**
	 * Changes language labels appropriately
	 */
	public void changeLabels()
	{
		if(!englishIsOnLeft[languageIndex])
		{
			languageTo = "English";
			if(languageIndex == 0)
			{
				languageFrom = "Lithuanian";
			}
			else if(languageIndex == 1)
			{
				languageFrom = "Swedish";
			}
			else if(languageIndex == 2)
			{
				languageFrom = "Albanian";
			}
		}
		else
		{
			languageFrom = "English";
			if(languageIndex == 0)
			{
				languageTo = "Lithuanian";
			}
			else if(languageIndex == 1)
			{
				languageTo = "Swedish";
			}
			else if(languageIndex == 2)
			{
				languageTo = "Albanian";
			}
		}
		lblLangFrom.setText(languageFrom);
		lblLangTo.setText(languageTo);
	}
}
