package com.jeff.fx.rules;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;

public class GraphVisualiser extends JFrame
{
    public GraphVisualiser()
    {
        super("Node Test");
    }
    
    public void updateModel(Node<?> root)
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
            Object v1 = graph.insertVertex(parent, null, "And", 340, 20, 120, 50, "strokeColor=#aaaa88;fillColor=#ffffdd");
            Object v2 = graph.insertVertex(parent, null, "Time > Mo0200", 0, 0, 120, 50);
            Object v3 = graph.insertVertex(parent, null, "Time < Mo0600", 0, 0, 120, 50);
            graph.insertEdge(parent, null, null, v1, v2);
            graph.insertEdge(parent, null, null, v1, v3);

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
                    System.out.println("cell=" + graph.getLabel(cell));
                }
            }
        });
        
        validate();
    }

    public static void main(String[] args)
    {
        GraphVisualiser frame = new GraphVisualiser();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.updateModel(null);
    }
}
