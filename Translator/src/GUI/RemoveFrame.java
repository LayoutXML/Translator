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
public class RemoveFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private Translator translator;
	private int languageIndex;
	private boolean isLeft = true;
	
	public RemoveFrame(Translator translatorReference, int languageIndexReference) {
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

		JMenu menu = new JMenu("Language");
		JMenuItem item = new JMenuItem("Left");
		item.addActionListener(this);
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem("Right");
		item.addActionListener(this);
		menu.add(item);
		menuBar.add(menu);
		
		JLabel lblAddingANew = new JLabel("Remove a word from the dictionary");
		lblAddingANew.setFont(new Font("Cambria Math", Font.PLAIN, 22));
		lblAddingANew.setForeground(Color.WHITE);
		lblAddingANew.setBounds(130, 11, 452, 45);
		contentPane.add(lblAddingANew);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(222, 160, 147, 109);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("Remove");
		btnNewButton.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isLeft) {
					translator.removeFromDictionary(textArea.getText(), languageIndex);
				} else {
					translator.removeFromDictionaryByValue(textArea.getText(), languageIndex);
				}
				translator.writeFile(languageIndex);
			}
		});
		btnNewButton.setBounds(222, 309, 148, 45);
		contentPane.add(btnNewButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String text = source.getText();
		switch (text) {
			case "Left":
				isLeft = true;
				break;
			case "Right":
				isLeft = false;
				break;
			default:
				isLeft = true;
				break;
		}
	}
}
