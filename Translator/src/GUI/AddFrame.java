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
	
	public AddFrame(String word, Translator translatorReference, int languageIndexReference) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 640);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		translator = translatorReference;
		languageIndex = languageIndexReference;
		
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
				translator.addToDictionary(originalWordInput.getText(),translationInput.getText(),languageIndex);
				translator.writeFile(languageIndex);
			}
		});
		btnNewButton.setBounds(221, 324, 161, 64);
		contentPane.add(btnNewButton);
		
		JLabel lblEnglish = new JLabel("English");
		lblEnglish.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		lblEnglish.setForeground(Color.WHITE);
		lblEnglish.setBounds(143, 159, 72, 28);
		contentPane.add(lblEnglish);
	}
}
