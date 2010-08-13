package com.jeff.fx.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

public class GUIUtil {
	
	/**
	 * Create a nice looking titled JXPanel with shadow border
	 * @param title
	 * @param pnl
	 * @return
	 */
	public static JXPanel frame(String title, JPanel pnl) {
		
        JXTitledPanel titled = new JXTitledPanel(title, pnl);
        Border shadow = new DropShadowBorder(Color.BLACK, 3, 0.66f, 3, false, false, true, true);
        Border line = BorderFactory.createLineBorder(Color.GRAY);
        Border border = BorderFactory.createCompoundBorder(shadow, line);
        titled.setBorder(border);
        return titled;
	}
	
}
