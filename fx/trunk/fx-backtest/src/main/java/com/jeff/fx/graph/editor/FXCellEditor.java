/**
 * $Id: FXCellEditor.java,v 1.10 2009/05/07 07:24:13 gaudenz Exp $
 * Copyright (c) 2008, Gaudenz Alder
 */
package com.jeff.fx.graph.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.view.mxICellEditor;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;

/**
 * To control this editor, use mxGraph.invokesStopCellEditing, mxGraph.
 * enterStopsCellEditing and mxGraph.escapeEnabled.
 */
public class FXCellEditor implements mxICellEditor
{
	public static int DEFAULT_MIN_WIDTH = 100;
	public static int DEFAULT_MIN_HEIGHT = 60;
	public static double DEFAULT_MINIMUM_EDITOR_SCALE = 1;
	protected mxGraphComponent graphComponent;
	protected double minimumEditorScale = DEFAULT_MINIMUM_EDITOR_SCALE;
	protected int minimumWidth = DEFAULT_MIN_WIDTH;
	protected int minimumHeight = DEFAULT_MIN_HEIGHT;

	protected transient Object editingCell;
	protected transient EventObject trigger;
	protected transient JScrollPane scrollPane;
	protected transient JTextArea textArea;
	protected transient JEditorPane editorPane;

	protected transient KeyAdapter keyListener = new KeyAdapter()
	{
		protected transient boolean ignoreEnter = false;

		public void keyPressed(KeyEvent e)
		{
			if (graphComponent.isEnterStopsCellEditing() && e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if (e.isShiftDown() || e.isControlDown() || e.isAltDown())
				{
					if (!ignoreEnter)
					{
						ignoreEnter = true;

						// Redirects the event with no modifier keys
						try
						{
							KeyEvent event = new KeyEvent((Component) e.getSource(), e.getID(), e.getWhen(), 0, e.getKeyCode(), e.getKeyChar());
							((Component) e.getSource()).dispatchEvent(event);
						}
						finally
						{
							ignoreEnter = false;
						}
					}
				}
				else if (!ignoreEnter)
				{
					stopEditing(false);
				}
			}
		}
	};

	public FXCellEditor(mxGraphComponent graphComponent)
	{
		this.graphComponent = graphComponent;

		// Creates the plain text editor
		textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		textArea.setOpaque(false);
		textArea.addKeyListener(keyListener);

		// Creates the HTML editor
		editorPane = new JEditorPane();
		editorPane.setOpaque(false);
		editorPane.setContentType("text/html");
		editorPane.addKeyListener(keyListener);

		// Creates the scollpane that contains the editor
		// FIXME: Cursor not visible when scrolling
		scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setVisible(false);
		scrollPane.setOpaque(false);
	}

	public Component getEditor()
	{
		if (textArea.getParent() != null)
		{
			return textArea;
		}
		else if (editingCell != null)
		{
			return editorPane;
		}

		return null;
	}

	public Rectangle getEditorBounds(mxCellState state)
	{
		mxIGraphModel model = state.getView().getGraph().getModel();
		mxGeometry geometry = model.getGeometry(state.getCell());
		Rectangle bounds = null;

		if ((geometry != null && geometry.getOffset() != null && (geometry.getOffset().getX() != 0 || geometry.getOffset().getY() != 0)) || model.isEdge(state.getCell()))
		{
			bounds = state.getLabelBounds().getRectangle();
			bounds.height += 10;
		}
		else
		{
			bounds = state.getRectangle();
		}

		// Applies the horizontal and vertical label positions
		if (model.isVertex(state.getCell()))
		{
			String horizontal = mxUtils.getString(state.getStyle(),mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);

			if (horizontal.equals(mxConstants.ALIGN_LEFT))
			{
				bounds.x -= state.getWidth();
			}
			else if (horizontal.equals(mxConstants.ALIGN_RIGHT))
			{
				bounds.x += state.getWidth();
			}

			String vertical = mxUtils.getString(state.getStyle(),
					mxConstants.STYLE_VERTICAL_LABEL_POSITION,
					mxConstants.ALIGN_MIDDLE);

			if (vertical.equals(mxConstants.ALIGN_TOP))
			{
				bounds.y -= state.getHeight();
			}
			else if (vertical.equals(mxConstants.ALIGN_BOTTOM))
			{
				bounds.y += state.getHeight();
			}
		}

		return bounds;
	}

	public void startEditing(Object cell, EventObject trigger)
	{
		if (editingCell != null)
		{
			stopEditing(true);
		}

		mxCellState state = graphComponent.getGraph().getView().getState(cell);

		if (state != null)
		{
			double scale = Math.max(minimumEditorScale, graphComponent.getGraph().getView().getScale());
			JTextComponent currentEditor = null;
			this.trigger = trigger;
			editingCell = cell;

			scrollPane.setBounds(getEditorBounds(state));
			scrollPane.setSize(Math.max(scrollPane.getWidth(), (int) Math.round(minimumWidth * scale)), Math.max(scrollPane.getHeight(), (int) Math.round(minimumHeight * scale)));
			scrollPane.setVisible(true);
			
			String value = getInitialValue(state, trigger);

			if (graphComponent.getGraph().isHtmlLabel(cell))
			{
				editorPane.setDocument(mxUtils.createHtmlDocumentObject(state.getStyle(), scale));
				editorPane.setText(mxUtils.getBodyMarkup(value, true));

				// Workaround for wordwrapping in editor pane
				// FIXME: Cursor not visible at end of line
				JPanel wrapper = new JPanel(new BorderLayout());
				wrapper.setOpaque(false);
				wrapper.add(editorPane, BorderLayout.CENTER);
				scrollPane.setViewportView(wrapper);

				currentEditor = editorPane;
			}
			else
			{
				textArea.setFont(mxUtils.getFont(state.getStyle(), scale));
				Color fontColor = mxUtils.getColor(state.getStyle(),mxConstants.STYLE_FONTCOLOR, Color.black);
				textArea.setForeground(fontColor);
				textArea.setText(value);

				scrollPane.setViewportView(textArea);
				currentEditor = textArea;
			}

			graphComponent.getGraphControl().add(scrollPane, 0);
			
			if (isHideLabel(state))
			{
				graphComponent.redraw(state);
			}

			currentEditor.revalidate();
			currentEditor.requestFocusInWindow();
			currentEditor.selectAll();
		}
	}
	
	protected boolean isHideLabel(mxCellState state)
	{
		return true;
	}

	public void stopEditing(boolean cancel)
	{
		if (editingCell != null)
		{
			scrollPane.transferFocusUpCycle();
			Object cell = editingCell;
			editingCell = null;

			if (!cancel)
			{
				EventObject trig = trigger;
				trigger = null;
				graphComponent.labelChanged(cell, getCurrentValue(), trig);
			}
			else
			{
				mxCellState state = graphComponent.getGraph().getView().getState(cell);
				graphComponent.redraw(state);
			}

			if (scrollPane.getParent() != null)
			{
				scrollPane.setVisible(false);
				scrollPane.getParent().remove(scrollPane);
			}

			graphComponent.requestFocusInWindow();
		}
	}

	protected String getInitialValue(mxCellState state, EventObject trigger)
	{
		return graphComponent.getEditingValue(state.getCell(), trigger);
	}

	public String getCurrentValue()
	{
		if (textArea.getParent() != null)
		{
			return textArea.getText();
		}

		return editorPane.getText();
	}

	public Object getEditingCell()
	{
		return editingCell;
	}

	public double getMinimumEditorScale()
	{
		return minimumEditorScale;
	}

	public void setMinimumEditorScale(double minimumEditorScale)
	{
		this.minimumEditorScale = minimumEditorScale;
	}

	public int getMinimumWidth()
	{
		return minimumWidth;
	}

	public void setMinimumWidth(int minimumWidth)
	{
		this.minimumWidth = minimumWidth;
	}

	public int getMinimumHeight()
	{
		return minimumHeight;
	}

	public void setMinimumHeight(int minimumHeight)
	{
		this.minimumHeight = minimumHeight;
	}
}
