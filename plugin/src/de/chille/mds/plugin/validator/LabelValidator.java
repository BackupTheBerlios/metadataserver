package de.chille.mds.plugin.validator;

import org.eclipse.jface.dialogs.IInputValidator;

public class LabelValidator implements IInputValidator {
	public String isValid(String newText) {
		if (newText.length() == 0) {
			return "";
		} else if (newText.length() > 50) {
			return "Maximum are 50 Chars.";
		} else if (!newText.matches("[0-9a-zA-Z_]*")) {
			return "Please Use Only Alphanumeric Chars.";
		} else {
			return null;
		}
	}
}