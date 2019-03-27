package GUI;

import main.Translator;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

@SuppressWarnings("Duplicates")
/**
 * Class that handles GUI for translating a file
 */
public class FileFrame extends JFrame {

	private JPanel contentPane;
	private JTextPane fileOutput;
	private JTextPane fileText;
	private JButton btnNewButton;
	private JButton btnTranslate;
	private String fileName;
	private Translator translator;
	private int languageIndex;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	/**
	 * GUI and initialisation
	 * @param translatorReference translator
	 * @param languageIndexReference language index
	 */
	public FileFrame(Translator translatorReference, int languageIndexReference) {
		setMinimumSize(new Dimension(640, 640));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 640);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setTitle("Translate file");

		translator = translatorReference;
		languageIndex = languageIndexReference;

		JLabel lblAddingANew = new JLabel("Translate a .txt File");
		lblAddingANew.setFont(new Font("Cambria Math", Font.PLAIN, 22));
		lblAddingANew.setForeground(Color.WHITE);
		lblAddingANew.setBounds(218, 11, 191, 20);
		contentPane.add(lblAddingANew);
		
		btnNewButton = new JButton("Choose");
		btnNewButton.setFont(new Font("Cambria Math", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenFile file = new OpenFile();
				fileName = file.pick();
			}
		});
		btnNewButton.setBounds(95, 53, 118, 23);
		contentPane.add(btnNewButton);
		
		btnTranslate = new JButton("Translate");
		btnTranslate.setFont(new Font("Cambria Math", Font.PLAIN, 20));
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileName!=null) {
					translator.translateFile(fileName,languageIndex, fileText, fileOutput);
				}
			}
		});
		btnTranslate.setBounds(396, 53, 140, 23);
		contentPane.add(btnTranslate);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(325, 87, 271, 504);
		contentPane.add(scrollPane_1);
		
		fileOutput = new JTextPane();
		fileOutput.setFont(new Font("Cambria Math", Font.PLAIN, 20));
		scrollPane_1.setViewportView(fileOutput);
				
				scrollPane = new JScrollPane();
				scrollPane.setBounds(26, 87, 268, 504);
				contentPane.add(scrollPane);
		
				fileText = new JTextPane();
				fileText.setFont(new Font("Cambria Math", Font.PLAIN, 20));
				scrollPane.setViewportView(fileText);
	}
}
