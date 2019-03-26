package GUI;

import main.Translator;

import java.awt.*;

import javax.swing.*;

import java.awt.EventQueue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class PrintFrame extends JFrame {

	private JPanel contentPane;
	private Translator translator;
	private int languageIndex;
	
	public PrintFrame(Translator translatorReference, int languageIndexReference) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 640);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		translator = translatorReference;
		languageIndex = languageIndexReference;
		
		JLabel lblAddingANew = new JLabel("Dictionary");
		lblAddingANew.setFont(new Font("Cambria Math", Font.PLAIN, 22));
		lblAddingANew.setForeground(Color.WHITE);
		lblAddingANew.setBounds(250, 11, 107, 27);
		contentPane.add(lblAddingANew);

		DefaultListModel<String> listModel = new DefaultListModel<>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 42, 545, 516);
		contentPane.add(scrollPane);
		JList<String> list = new JList<>(listModel);
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		Thread thread = new Thread() {
			@Override
			public void run() {
				HashMap<String, String> dictionary = translator.getDictionary(languageIndex);
				String[] sortedDictionary = new String[dictionary.size()];

				int i = 0;
				for (Map.Entry<String, String> entry : dictionary.entrySet()) {
					sortedDictionary[i++] = entry.getKey() + " - " + entry.getValue();
				}

				Arrays.sort(sortedDictionary);
				listModel.removeAllElements();

				for (String pair : sortedDictionary) {
					listModel.addElement(pair);
				}
			}
		};
		thread.run();
	}

}

