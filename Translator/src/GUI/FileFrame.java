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

@SuppressWarnings("Duplicates")
public class FileFrame extends JFrame {

	private JPanel contentPane;
	private JTextPane fileOutput;
	private JTextPane fileText;
	private JButton btnNewButton;
	private JButton btnTranslate;
	private String fileName;
	private Translator translator;
	private int languageIndex;
	
	public FileFrame(Translator translatorReference, int languageIndexReference) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 490, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		translator = translatorReference;
		languageIndex = languageIndexReference;

		JLabel lblAddingANew = new JLabel("Translating a text file");
		lblAddingANew.setBounds(5, 5, 418, 20);
		contentPane.add(lblAddingANew);
		
		fileOutput = new JTextPane();
		fileOutput.setBounds(252, 70, 212, 456);
		contentPane.add(fileOutput);

		fileText = new JTextPane();
		fileText.setBounds(9, 70, 212, 456);
		contentPane.add(fileText);
		
		btnNewButton = new JButton("Choose");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenFile file = new OpenFile();
				fileName = file.pick();
			}
		});
		btnNewButton.setBounds(132, 23, 89, 23);
		contentPane.add(btnNewButton);
		
		btnTranslate = new JButton("Translate");
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileName!=null) {
					translator.translateFile(fileName,languageIndex, fileText, fileOutput);
				}
			}
		});
		btnTranslate.setBounds(286, 23, 89, 23);
		contentPane.add(btnTranslate);
	}
}
