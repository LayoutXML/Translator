package GUI;

import javax.swing.JFileChooser;

/**
 * Class that handles file selection
 */
public class OpenFile {

	/**
	 * Allows choosing a file to translate
	 * @return file path
	 */
	public String pick(){
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}
}
