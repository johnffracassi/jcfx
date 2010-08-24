package com.jeff.fx.gui.field;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class IntTextField extends JTextField {

	private static final long serialVersionUID = 1462148797142222672L;

	public IntTextField() {
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				updateValue();
			}
		});
		setColumns(8);
	}
	
	public void setValue(int value) {
		setText(String.valueOf(value));
	}
	
	public int getValue() {
		if(isValidValue()) {
			return new Integer(getText());
		} else {
			return 0;
		}
	}
	
	protected void updateValue() {
		validateValue();
	}
	
	protected void validateValue() {
		setBackground(isValidValue() ? Color.WHITE : Color.RED);
	}

	public boolean isValidValue() {
		try {
			new Integer(getText());
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
}
