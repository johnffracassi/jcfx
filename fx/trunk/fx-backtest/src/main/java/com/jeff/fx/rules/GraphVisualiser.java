package com.jeff.fx.rules;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.jeff.fx.lookforward.CandleFilterModel;
import com.jeff.fx.rules.logic.AndNode;
import com.jeff.fx.rules.logic.NandNode;
import com.jeff.fx.rules.logic.OrNode;
import com.jeff.fx.rules.logic.TrueNode;
import com.jeff.fx.rules.logic.XorNode;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;

public class GraphVisualiser extends JFrame
{
    private mxGraphComponent graphComponent;
    private mxGraph graph;
    private Node<?> selected = null;
    private Node<?> root = new TrueNode(null);
    
    public static void main(String[] args)
    {
        GraphVisualiser frame = new GraphVisualiser();
        frame.setVisible(true);
        frame.init();
    }
    
    public GraphVisualiser()
    {
        super("Node Test");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    private void init()
    {
        initGraph();
        updateRootNode(root);
    }
    
    private Object insertNode(Object parent, mxGraph graph, Node<?> node)
    {
        String fillColour = node.getChildCount() > 0 ? "#ddddff" : "#ffffdd";
        String lineColour = node.getChildCount() > 0 ? "#aaaacc" : "#ccccaa";
        
        Object vertex = graph.insertVertex(parent, null, node, 0, 0, 120, 50, "strokeColor=" + lineColour + ";fillColor=" + fillColour);
        
        for(int i=0; i<node.getChildCount(); i++)
        {
            Object child = insertNode(parent, graph, node.getChild(i));
            graph.insertEdge(parent, null, null, vertex, child);
        }
        
        return vertex;
    }
    
    private void initGraph()
    {
        graph = new mxGraph();
        graph.setConnectableEdges(false);
        graph.setAllowDanglingEdges(false);
        graph.setAllowLoops(false);
        graph.setAutoOrigin(false);
        graph.setAllowNegativeCoordinates(false);
        graph.setCellsEditable(false);

        graphComponent = new mxGraphComponent(graph);
        add(graphComponent, BorderLayout.CENTER);

        // attach mouse listeners
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e)
            {
                mxCell cell = (mxCell)graphComponent.getCellAt(e.getX(), e.getY());
                if(cell != null && cell.getValue() != null && cell.getValue() instanceof Node)
                {
                    setSelectedNode((Node<?>)cell.getValue());
                }
                
                if (cell != null && e.getButton() == MouseEvent.BUTTON3)
                {
                    JPopupMenu popUp = new JPopupMenu();
                    
                    JMenu mnuLogicType = new JMenu("Logic");
                    mnuLogicType.add(buildLogicMenuItem("And", AndNode.class));
                    mnuLogicType.add(buildLogicMenuItem("Or", OrNode.class));
                    mnuLogicType.add(buildLogicMenuItem("Xor", XorNode.class));
                    mnuLogicType.add(buildLogicMenuItem("Nand", NandNode.class));
                    
                    JMenu mnuFixedType = new JMenu("Fixed Value");
                    mnuFixedType.add(new JMenuItem("True"));
                    mnuFixedType.add(new JMenuItem("False"));
                    
                    JMenu mnuType = new JMenu("Type");
                    mnuType.add(mnuLogicType);
                    mnuType.add(mnuFixedType);
                    mnuType.add(new JMenuItem("Time Range"));
                    mnuType.add(new JMenuItem("Indicator"));

                    popUp.add(mnuType);
                    popUp.add(new JMenuItem("Delete"));
                    popUp.add(new JMenuItem("Properties..."));
                    popUp.show(GraphVisualiser.this, e.getX(), e.getY() + 23);
                }
            }
        });
    }
    
    private void updateRootNode(Node<?> node)
    {
        System.out.println("updating: root node is " + node.getClass().getSimpleName());
        
        mxCell parent = (mxCell)graph.getDefaultParent();
        System.out.println("parent value: " + parent.getValue());
        
        graph.getModel().beginUpdate();
        graph.setCellsLocked(false);
        try
        {
            for(int c=parent.getChildCount(); c>0; c--)
            {
                parent.remove(c-1);
            }
            
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
        
        graphComponent.refresh();
        validate();
    }
    
    private void replaceSelectedNode(Class<? extends Node> nodeClass)
    {
        Node parent = selected.getParent();
        
        if(parent != null)
        {
            int childIdx = parent.getChildIndex(selected);
            Node newNode = makeNode(nodeClass);
            newNode.setParent(parent);
            parent.setChild(childIdx, newNode);
        }
        else
        {
            root = makeNode(nodeClass);
        }
        
        updateRootNode(root);
    }
    
    private <T extends Node> T makeNode(Class<T> nodeClass)
    {
        try
        {
            System.out.println("making a " + nodeClass.getSimpleName());
            return nodeClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    private void setSelectedNode(Node<?> node)
    {
        this.selected = node;
    }
    
    private JMenuItem buildLogicMenuItem(final String label, final Class<? extends Node> type)
    {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                replaceSelectedNode(type);
            }
        });
        return item;
    }
}
