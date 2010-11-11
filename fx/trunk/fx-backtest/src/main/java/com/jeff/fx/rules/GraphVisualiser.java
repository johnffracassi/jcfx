package com.jeff.fx.rules;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.jeff.fx.common.TimeOfWeek;
import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.business.AbstractFXNode;
import com.jeff.fx.rules.business.TimeOfWeekNodeFactory;
import com.jeff.fx.rules.logic.OrNode;
import com.jeff.fx.rules.logic.TrueNode;
import com.jeff.fx.rules.logic.XorNode;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;

public class GraphVisualiser extends JFrame
{
    public static void main(String[] args)
    {
        GraphVisualiser frame = new GraphVisualiser();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        
        Node<CandleFilterModel> left = TimeOfWeekNodeFactory.timeOfWeekIsBetween(new TimeOfWeek(TimeOfWeek.MONDAY, 2, 0), new TimeOfWeek(TimeOfWeek.MONDAY, 6, 0));
        Node<CandleFilterModel> right = TimeOfWeekNodeFactory.timeOfWeekIsBetween(new TimeOfWeek(TimeOfWeek.TUESDAY, 2, 0), new TimeOfWeek(TimeOfWeek.TUESDAY, 6, 0));
        OrNode<CandleFilterModel> or = new OrNode<CandleFilterModel>(left, right);
        XorNode<CandleFilterModel> xor = new XorNode<CandleFilterModel>(new TrueNode(), or);
        frame.updateRootNode(xor);
    }
    
    public GraphVisualiser()
    {
        super("Node Test");
    }
    
    private Object insertNode(Object parent, mxGraph graph, Node<CandleFilterModel> node)
    {
        String fillColour = node.getChildCount() > 0 ? "#ddddff" : "#ffffdd";
        String lineColour = node.getChildCount() > 0 ? "#aaaacc" : "#ccccaa";
        
        Object vertex = graph.insertVertex(parent, null, node.getLabel(), 0, 0, 120, 50, "strokeColor=" + lineColour + ";fillColor=" + fillColour);
        
        for(int i=0; i<node.getChildCount(); i++)
        {
            Object child = insertNode(parent, graph, node.getChild(i));
            graph.insertEdge(parent, null, null, vertex, child);
        }
        
        return vertex;
    }
    
    private void updateRootNode(Node<CandleFilterModel> node)
    {
        final mxGraph graph = new mxGraph();
        graph.setConnectableEdges(false);
        graph.setAllowDanglingEdges(false);
        graph.setAllowLoops(false);
        graph.setAutoOrigin(false);
        graph.setAllowNegativeCoordinates(false);
        graph.setCellsEditable(false);
        
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {
            insertNode(parent, graph, node);
            
            new mxLayoutManager(graph) {
                mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
                public mxIGraphLayout getLayout(Object parent)
                {
                    if (graph.getModel().getChildCount(parent) > 0)
                    {
                        return layout;
                    }
                    return null;
                }
            };
        }
        finally
        {
            graph.getModel().endUpdate();
            graph.setCellsLocked(true);
        }

        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);

        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e)
            {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                if (cell != null)
                {
                    System.out.println();
                    System.out.println("cell=" + graph.getLabel(cell));
                }
            }
        });
        
        validate();
    }
}
