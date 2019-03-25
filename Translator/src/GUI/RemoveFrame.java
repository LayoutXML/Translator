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

@SuppressWarnings("Duplicates")
public class RemoveFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private Translator translator;
	private int languageIndex;
	private boolean isLeft = true;
	
	public RemoveFrame(Translator translatorReference, int languageIndexReference) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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
		
		JLabel lblAddingANew = new JLabel("Removing a word from the Dictionary");
		lblAddingANew.setBounds(10, 30, 424, 14);
		contentPane.add(lblAddingANew);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(62, 86, 147, 109);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("Remove");
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
		btnNewButton.setBounds(264, 130, 89, 23);
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
