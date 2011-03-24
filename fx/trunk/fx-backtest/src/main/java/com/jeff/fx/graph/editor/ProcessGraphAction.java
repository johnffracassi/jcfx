package com.jeff.fx.graph.editor;

import com.jeff.fx.graph.node.EntryNode;
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
