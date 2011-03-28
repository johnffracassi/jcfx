package com.jeff.fx.graph.editor;

import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.graph.node.BaseNode;
import com.jeff.fx.graph.node.EntryNode;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProcessGraphAction extends AbstractAction
{
	public static final BasicGraphEditor getEditor(ActionEvent e)
	{
		if (e.getSource() instanceof Component)
		{
			Component component = (Component) e.getSource();

			while (component != null && !(component instanceof BasicGraphEditor))
			{
				component = component.getParent();
			}

			return (BasicGraphEditor) component;
		}

		return null;
	}

    public void actionPerformed(ActionEvent e)
    {
        mxGraphComponent graphComponent = getEditor(e).getGraphComponent();
        mxGraph graph = graphComponent.getGraph();

        mxCell root = (mxCell)graph.getModel().getRoot();
        mxCell enterNode = findEnterNode(root);

        traverseVertex(enterNode, "");
        doGraph(enterNode, null, null);
    }

    private mxCell findEnterNode(mxICell root)
    {
        for (int c=0; c<root.getChildCount(); c++)
        {
            mxCell child = (mxCell)root.getChildAt(c);

            if(child.getValue() != null && child.getValue() instanceof EntryNode)
            {
                return child;
            }
            else
            {
                return findEnterNode(child);
            }
        }

        return null;
    }

    private void doGraph(mxCell cell, CandleDataPoint candle, CandleFilterModel model)
    {
        BaseNode node = (BaseNode)cell.getValue();
        boolean result = node.evaluate(candle, model);

        if(countExits(cell) > 0)
        {
            mxCell target = getTarget(cell, result ? 0 : 1);
            doGraph(target, candle, model);
        }
        else
        {
            System.out.println("Terminating at " + cell.getValue());
        }
    }

    private int countExits(mxCell vertex)
    {
        int exits = 0;
        for(int i=0; i<vertex.getEdgeCount(); i++)
        {
            mxCell edge = (mxCell)vertex.getEdgeAt(i);
            if(edge.getTarget() != vertex)
            {
                exits ++;
            }
        }
        return exits;
    }

    private mxCell getTarget(mxCell vertex, int idx)
    {
        int exits = 0;

        for(int i=0; i<vertex.getEdgeCount(); i++)
        {
            mxCell edge = (mxCell)vertex.getEdgeAt(i);
            if(edge.getTarget() != vertex)
            {
                if(exits == idx)
                {
                    return (mxCell)edge.getTarget();
                }
                exits ++;
            }
        }

        return null;
    }

    private void traverseVertex(mxCell node, String indent)
    {
        System.out.printf("%s> V:%s T:%s [%d children, %d edges]%n", indent, node.getValue(), node.getClass().getSimpleName(), node.getChildCount(), node.getEdgeCount());

        for (int c=0; c<node.getEdgeCount(); c++)
        {
            mxCell edge = (mxCell)node.getEdgeAt(c);
            traverseEdge(node, edge, indent + "  ");
        }
    }

    private void traverseEdge(mxCell source, mxCell edge, String indent)
    {
        // make sure the target is not the source vertex
        if(edge.getTarget() != source)
        {
            System.out.printf("%s> Target:%s Source:%s%n", indent, edge.getTarget().getValue(), edge.getSource().getValue());
            traverseVertex((mxCell)edge.getTarget(), indent);
        }
    }
}
