package com.jeff.fx.rules;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.jeff.fx.backtest.GenericDialog;
import com.jeff.fx.common.CandleCollection;
import com.jeff.fx.common.CandleDataPoint;
import com.jeff.fx.common.FXDataRequest;
import com.jeff.fx.common.FXDataSource;
import com.jeff.fx.common.Instrument;
import com.jeff.fx.common.Period;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.gui.beanform.BeanForm;
import com.jeff.fx.lookforward.CandleFilterProcessor;
import com.jeff.fx.rules.business.ELNode;
import com.jeff.fx.rules.business.TimeOfWeekNode;
import com.jeff.fx.rules.business.TimeRangeNode;
import com.jeff.fx.rules.logic.AndNode;
import com.jeff.fx.rules.logic.FalseNode;
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

@Component("graphVisualiser")
public class GraphVisualiser extends JFrame
{
    @Autowired
    private CandleDataStore loader;

    @Autowired
    private CandleFilterProcessor processor;
    
    private FXDataSource dataSource = FXDataSource.Forexite;
    private Instrument instrument = Instrument.GBPUSD;
    private Period period = Period.OneMin;
    private LocalDate startDate = new LocalDate(2010, 10, 20);
    
    private mxGraphComponent graphComponent;
    private mxGraph graph;
    private Node selected = null;
    private Node root = new TrueNode(null);
    private JButton btnTest;
    private JLabel lblResult;
    
    public static void main(String[] args)
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context-*.xml");
        GraphVisualiser app = (GraphVisualiser) ctx.getBean("graphVisualiser");
        app.setVisible(true);
        app.init();
    }
    
    public GraphVisualiser()
    {
        super("Node Test");
    }
    
    private void init()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setLayout(new BorderLayout());
        
        JToolBar toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);
        
        btnTest = new JButton("Apply");
        btnTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apply();
            }
        });
        toolBar.add(btnTest);
        
        JPanel pnlStatus = new JPanel();
        pnlStatus.setLayout(new BorderLayout());
        lblResult = new JLabel("Result");
        pnlStatus.add(lblResult);
        getContentPane().add(pnlStatus, BorderLayout.SOUTH);

        initGraph();
        
        updateRootNode(root);
    }

    private void apply()
    {
        try
        {
            lblResult.setText("Loading candles...");
            CandleCollection candles = loadTestData();

            lblResult.setText("Applying filter");
            List<CandleDataPoint> filtered = processor.apply(root, candles);

            lblResult.setText("Found " + filtered.size() + " candle(s)");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public CandleCollection loadTestData() throws IOException
    {
        FXDataRequest request = new FXDataRequest();
        request.setDataSource(dataSource);
        request.setDate(startDate);
        request.setEndDate(startDate);
        request.setInstrument(instrument);
        request.setPeriod(period);
        return new CandleCollection(loader.loadCandlesForWeek(request));
    }
    
    public Node getRootNode()
    {
        return root;
    }
    
    private Object insertNode(Object parent, mxGraph graph, Node node)
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
        getContentPane().add(graphComponent, BorderLayout.CENTER);

        // attach mouse listeners
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e)
            {
                mxCell cell = (mxCell)graphComponent.getCellAt(e.getX(), e.getY());
                if(cell != null && cell.getValue() != null && cell.getValue() instanceof Node)
                {
                    setSelectedNode((Node)cell.getValue());
                }
                
                if (cell != null && e.getButton() == MouseEvent.BUTTON3)
                {
                    JPopupMenu popUp = new JPopupMenu();
                    
                    JMenu mnuType = new JMenu("Type");
                    mnuType.add(buildLogicNodeMenu());
                    mnuType.add(buildFixedNodeMenu());
                    mnuType.add(buildTimeMenu());
                    mnuType.add(buildExpressionNodeMenu());

                    popUp.add(mnuType);
                    popUp.add(buildEditMenuItem());
                    popUp.show(GraphVisualiser.this, e.getX(), e.getY() + 23);
                }
            }
        });
    }
    
    private JMenuItem buildEditMenuItem()
    {
        JMenuItem item = new JMenuItem("Edit...");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BeanForm form = new BeanForm();
                form.buildForm(selected);
                
                GenericDialog editor = new GenericDialog(form, "Edit Node");
                editor.setVisible(true);
                
                updateRootNode(root);
            }
        });
        
        return item;
    }
    
    private JMenu buildLogicNodeMenu()
    {
        JMenu menu = new JMenu("Logic");
        menu.add(buildMenuItem("And", AndNode.class));
        menu.add(buildMenuItem("Or", OrNode.class));
        menu.add(buildMenuItem("Xor", XorNode.class));
        menu.add(buildMenuItem("Nand", NandNode.class));
        return menu;
    }
    
    private JMenuItem buildExpressionNodeMenu()
    {
        JMenuItem item = new JMenuItem("Expression Node");
        
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                replaceSelectedNode(new ELNode());
            }
        });

        return item;
    }
    
    private JMenu buildFixedNodeMenu()
    {
        JMenu menu = new JMenu("Fixed Value");
        
        menu.add(buildMenuItem("True", TrueNode.class));
        menu.add(buildMenuItem("False", FalseNode.class));

        return menu;
    }
    
    private JMenuItem buildTimeMenu()
    {
        JMenuItem menu = new JMenu("Time");
        
        JMenuItem rangeItem = new JMenuItem("Time Range");
        rangeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                replaceSelectedNode(new TimeRangeNode());
            }
        });
        menu.add(rangeItem);
        
        JMenuItem timeItem = new JMenuItem("Time");
        timeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                replaceSelectedNode(new TimeOfWeekNode());
            }
        });
        menu.add(timeItem);
        
        return menu;
    }
    
    private void updateRootNode(Node node)
    {
        mxCell parent = (mxCell)graph.getDefaultParent();
        
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
    
    private void replaceSelectedNode(Node newNode)
    {
        Node parent = selected.getParent();
        
        if(parent != null)
        {
            System.out.println("transmorgifying existing node");
            int childIdx = parent.getChildIndex(selected);
            newNode.setParent(parent);
            parent.setChild(childIdx, newNode);
        }
        else
        {
            System.out.println("replacing root node");
            root = newNode;
        }
        
        updateRootNode(root);
    }
    
    private void replaceSelectedNode(Class<? extends Node> nodeClass)
    {
        replaceSelectedNode(makeNode(nodeClass));
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
    
    private void setSelectedNode(Node node)
    {
        this.selected = node;
    }
    
    private JMenuItem buildMenuItem(final String label, final Class<? extends Node> type)
    {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                replaceSelectedNode(type);
            }
        });
        return item;
    }
    
    public JButton getBtnTest() {
        return btnTest;
    }
    
    public JLabel getLblResult() {
        return lblResult;
    }
}
