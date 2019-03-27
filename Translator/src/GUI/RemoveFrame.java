package GUI;

import main.Translator;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("Duplicates")
/**
 * Class that handles GUI for removing words from dictionary
 */
public class RemoveFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private Translator translator;
	private int languageIndex;
	private boolean isLeft = true;
	private boolean isEnglishOnLeft;
	private String leftText;
	private String rightText;
	private JLabel lblText;

	/**
	 * Handles GUI and initialisation
	 * @param translatorReference translator
	 * @param languageIndexReference language index
	 * @param englishOnLeft is not flipped
	 */
	public RemoveFrame(Translator translatorReference, int languageIndexReference, boolean englishOnLeft) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 640);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setTitle("Remove a word");

		translator = translatorReference;
		languageIndex = languageIndexReference;
		isEnglishOnLeft = englishOnLeft;

		if (isEnglishOnLeft) {
			leftText = "English";
			switch (languageIndexReference) {
				case 0:
					rightText = "Lithuanian";
					break;
				case 1:
					rightText = "Swedish";
					break;
				case 2:
					rightText = "Albanian";
					break;
				default:
					rightText = "Lithuanian";
					break;
			}
		} else {
			rightText = "English";
			switch (languageIndexReference) {
				case 0:
					leftText = "Lithuanian";
					break;
				case 1:
					leftText = "Swedish";
					break;
				case 2:
					leftText = "Albanian";
					break;
				default:
					leftText = "Lithuanian";
					break;
			}
		}

		JMenu menu = new JMenu("Language");
		JMenuItem item = new JMenuItem();
		item.setText(leftText);
		item.addActionListener(this);
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem();
		item.setText(rightText);
		item.addActionListener(this);
		menu.add(item);
		menuBar.add(menu);
		
		JLabel lblAddingANew = new JLabel("Remove a word from the dictionary");
		lblAddingANew.setFont(new Font("Cambria Math", Font.PLAIN, 30));
		lblAddingANew.setForeground(Color.WHITE);
		lblAddingANew.setBounds(83, 16, 510, 45);
		contentPane.add(lblAddingANew);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Cambria Math", Font.PLAIN, 24));
		textArea.setBounds(146, 147, 335, 275);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("Remove");
		btnNewButton.setFont(new Font("Cambria Math", Font.PLAIN, 24));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isLeft) {
					translator.removeFromDictionary(textArea.getText(), languageIndex, true);
				} else {
					translator.removeFromDictionaryByValue(textArea.getText(), languageIndex, true);
				}
				translator.writeFile(languageIndex);
			}
		});
		btnNewButton.setBounds(164, 454, 304, 67);
		contentPane.add(btnNewButton);
		
		lblText = new JLabel();
		lblText.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblText.setForeground(Color.WHITE);
		lblText.setBounds(136, 77, 345, 67);
		if (isLeft) {
			lblText.setText(leftText);
		} else {
			lblText.setText(rightText);
		}
		contentPane.add(lblText);
	}

	/**
	 * Handles language switching menu
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String text = source.getText();
		if (text.equals(leftText)) {
			isLeft = true;
			lblText.setText(leftText);
		} else {
			isLeft = false;
			lblText.setText(rightText);
		}
	}
}
