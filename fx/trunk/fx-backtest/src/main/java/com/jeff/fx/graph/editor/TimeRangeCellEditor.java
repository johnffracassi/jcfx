package com.jeff.fx.graph.editor;

import com.jeff.fx.backtest.GenericDialog;
import com.jeff.fx.graph.node.TimeRangeNode;
import com.jeff.fx.gui.beanform.BeanForm;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.view.mxICellEditor;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EventObject;

public class TimeRangeCellEditor implements mxICellEditor
{
	protected mxGraphComponent graphComponent;
	protected transient Object editingCell;
	protected transient EventObject trigger;
	protected transient JScrollPane scrollPane;

	public TimeRangeCellEditor(mxGraphComponent graphComponent)
	{
		this.graphComponent = graphComponent;

		scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setVisible(false);
		scrollPane.setOpaque(false);
	}

	public void startEditing(Object objCell, EventObject trigger)
	{
		if (editingCell != null)
		{
			stopEditing(true);
		}

		mxCellState state = graphComponent.getGraph().getView().getState(objCell);

		if (state != null)
		{
            if(objCell instanceof mxCell)
            {
                mxCell cell = (mxCell) objCell;

                if(cell.getValue() instanceof TimeRangeNode)
                {
                    TimeRangeNode node = (TimeRangeNode)cell.getValue();
                    BeanForm bt = new BeanForm();
                    bt.buildForm(node);

                    GenericDialog gd = new GenericDialog(bt, "Edit Node");
                    gd.setVisible(true);
                }
            }
		}
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
				graphComponent.labelChanged(cell, ((mxCell)cell).getValue(), trig);
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

	public Object getEditingCell()
	{
		return editingCell;
	}
}
