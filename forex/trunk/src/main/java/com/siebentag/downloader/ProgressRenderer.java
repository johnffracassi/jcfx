package com.siebentag.downloader;

import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

// This class renders a JProgressBar in a table cell.
public class ProgressRenderer extends JProgressBar implements TableCellRenderer
{
    private static final long serialVersionUID = 8014882191170108722L;

	// Constructor for ProgressRenderer.
	public ProgressRenderer(int min, int max)
	{
		super(min, max);
	}

	/*
	 * Returns this JProgressBar as the renderer for the given table cell.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		// Set JProgressBar's percent complete value.
		setValue((int) ((Float) value).floatValue());
		return this;
	}
}