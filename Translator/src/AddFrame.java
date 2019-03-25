import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddFrame extends JFrame {

	private JPanel contentPane;
	private Translator translator;
	private int languageIndex;
	
	public AddFrame(String word, Translator translatorReference, int languageIndexReference) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		translator = translatorReference;
		languageIndex = languageIndexReference;
		
		JLabel lblAddingANew = new JLabel("Adding a new word to the Dictionary");
		lblAddingANew.setBounds(5, 5, 424, 14);
		contentPane.add(lblAddingANew);
		
		JTextArea originalWordInput = new JTextArea();
		originalWordInput.setBounds(69, 65, 121, 93);
		contentPane.add(originalWordInput);
		originalWordInput.setText(word);
		
		JTextArea translationInput = new JTextArea();
		translationInput.setBounds(251, 65, 128, 93);
		contentPane.add(translationInput);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				translator.addToDictionary(originalWordInput.getText(),translationInput.getText(),languageIndex);
				translator.writeFile(languageIndex);
			}
		});
		btnNewButton.setBounds(159, 200, 89, 23);
		contentPane.add(btnNewButton);
	}
}
