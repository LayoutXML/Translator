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

/**
 * Class that handles GUI for adding words to the dictionary
 */
public class AddFrame extends JFrame {

	private JPanel contentPane;
	private Translator translator;
	private int languageIndex;
	private boolean englishOnLeft;

	/**
	 * GUI creation and initialisation
	 * @param word word to add
	 * @param translatorReference translator
	 * @param languageIndexReference language index
	 * @param englishOnLeftReference is not flipped
	 */
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
		lblAddingANew.setFont(new Font("Malgun Gothic", Font.BOLD, 26));
		lblAddingANew.setForeground(Color.WHITE);
		lblAddingANew.setBounds(54, 35, 554, 49);
		contentPane.add(lblAddingANew);
		
		JTextArea originalWordInput = new JTextArea();
		originalWordInput.setBackground(Color.LIGHT_GRAY);
		originalWordInput.setFont(new Font("Malgun Gothic Semilight", Font.PLAIN, 20));
		originalWordInput.setBounds(54, 198, 244, 176);
		contentPane.add(originalWordInput);
		originalWordInput.setText(word);
		
		JTextArea translationInput = new JTextArea();
		translationInput.setBackground(Color.LIGHT_GRAY);
		translationInput.setFont(new Font("Malgun Gothic Semilight", Font.PLAIN, 20));
		translationInput.setBounds(333, 198, 244, 176);
		contentPane.add(translationInput);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setFont(new Font("Malgun Gothic Semilight", Font.PLAIN, 30));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				translator.addToDictionary(originalWordInput.getText(),translationInput.getText(),languageIndex, true);
				translator.writeFile(languageIndex);
			}
		});
		btnNewButton.setBounds(188, 440, 256, 93);
		contentPane.add(btnNewButton);
		
		JLabel lblOriginal = new JLabel();
		lblOriginal.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		lblOriginal.setForeground(Color.WHITE);
		lblOriginal.setBounds(102, 138, 128, 49);
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
		lblTranslation.setFont(new Font("Malgun Gothic Semilight", Font.PLAIN, 20));
		lblTranslation.setForeground(Color.WHITE);
		lblTranslation.setBackground(new Color(240, 240, 240));
		lblTranslation.setBounds(386, 138, 128, 44);
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
