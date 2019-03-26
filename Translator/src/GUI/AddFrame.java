package GUI;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import main.Translator;
import java.awt.Color;
import java.awt.Font;

public class AddFrame extends JFrame {

	private JPanel contentPane;
	private Translator translator;
	private int languageIndex;
	private boolean englishOnLeft;
	
	public AddFrame(String word, Translator translatorReference, int languageIndexReference, boolean englishOnLeftReference) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 640);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		setTitle("Add new words");
		contentPane.setLayout(null);

		translator = translatorReference;
		languageIndex = languageIndexReference;
		englishOnLeft = englishOnLeftReference;
		
		JLabel lblAddingANew = new JLabel("Add a new word to the current dictionary");
		lblAddingANew.setFont(new Font("Cambria Math", Font.PLAIN, 22));
		lblAddingANew.setForeground(Color.WHITE);
		lblAddingANew.setBounds(109, 11, 424, 49);
		contentPane.add(lblAddingANew);
		
		JTextArea originalWordInput = new JTextArea();
		originalWordInput.setBounds(109, 198, 121, 93);
		contentPane.add(originalWordInput);
		originalWordInput.setText(word);
		
		JTextArea translationInput = new JTextArea();
		translationInput.setBounds(375, 198, 128, 93);
		contentPane.add(translationInput);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				translator.addToDictionary(originalWordInput.getText(),translationInput.getText(),languageIndex, true);
				translator.writeFile(languageIndex);
			}
		});
		btnNewButton.setBounds(221, 324, 161, 64);
		contentPane.add(btnNewButton);
		
		JLabel lblOriginal = new JLabel();
		lblOriginal.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		lblOriginal.setForeground(Color.WHITE);
		lblOriginal.setBounds(143, 159, 72, 28);
		if (englishOnLeft) {
			lblOriginal.setText("English");
		} else {
			switch (languageIndex) {
				case 0:
					lblOriginal.setText("Lithuanian");
					break;
				case 1:
					lblOriginal.setText("Swedish");
					break;
				case 2:
					lblOriginal.setText("Albanian");
					break;
				default:
					lblOriginal.setText("Lithuanian");
					break;
			}
		}
		contentPane.add(lblOriginal);
		
		JLabel lblTranslation = new JLabel();
		lblTranslation.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		lblTranslation.setForeground(Color.WHITE);
		lblTranslation.setBackground(new Color(240, 240, 240));
		lblTranslation.setBounds(395, 162, 69, 20);
		if (!englishOnLeft) {
			lblTranslation.setText("English");
		} else {
			switch (languageIndex) {
				case 0:
					lblTranslation.setText("Lithuanian");
					break;
				case 1:
					lblTranslation.setText("Swedish");
					break;
				case 2:
					lblTranslation.setText("Albanian");
					break;
				default:
					lblTranslation.setText("Lithuanian");
					break;
			}
		}
		contentPane.add(lblTranslation);
	}
}
