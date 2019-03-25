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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		translator = translatorReference;
		languageIndex = languageIndexReference;
		
		JLabel lblAddingANew = new JLabel("Printing entire contents to dictionary");
		lblAddingANew.setBounds(5, 5, 424, 14);
		contentPane.add(lblAddingANew);


		DefaultListModel<String> listModel = new DefaultListModel<>();
		JList<String> list = new JList<>(listModel);
		list.setBounds(33, 30, 357, 221);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(357, 221));
		contentPane.add(list);

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

