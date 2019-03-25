package GUI;

import main.Translator;

import java.util.*;

import javax.swing.JFileChooser;

public class OpenFile {
	
	public String pick(){
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}
}
