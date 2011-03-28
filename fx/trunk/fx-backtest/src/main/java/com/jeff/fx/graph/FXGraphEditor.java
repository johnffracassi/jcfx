package com.jeff.fx.graph;

import com.jeff.fx.graph.editor.*;
import com.jeff.fx.graph.node.*;
import com.jeff.fx.rules.Node;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.swing.view.mxCellEditor;
import com.mxgraph.swing.view.mxICellEditor;
import com.mxgraph.util.*;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

public class FXGraphEditor extends BasicGraphEditor
{
	public static final NumberFormat numberFormat = NumberFormat.getInstance();
	public static URL url = null;

	public FXGraphEditor()
	{
		this("Graph Editor", new CustomGraphComponent(new CustomGraph()));
	}

	public FXGraphEditor(String appTitle, mxGraphComponent component)
	{
		super(appTitle, component);
		final mxGraph graph = graphComponent.getGraph();

		// Creates the shapes palette
		EditorPalette shapesPalette = insertPalette(mxResources.get("shapes"));

		// Sets the edge template to be used for creating new edges if an edge
		// is clicked in the shape palette
		shapesPalette.addListener(mxEvent.SELECT, new mxIEventListener()
		{
			public void invoke(Object sender, mxEventObject evt)
			{
				Object tmp = evt.getProperty("transferable");

				if (tmp instanceof mxGraphTransferable)
				{
					mxGraphTransferable t = (mxGraphTransferable) tmp;
					Object cell = t.getCells()[0];

					if (graph.getModel().isEdge(cell))
					{
						((CustomGraph) graph).setEdgeTemplate(cell);
					}
				}
			}
		});

		// Adds some template cells for dropping into the graph
		shapesPalette.addTemplate("Entry",new ImageIcon(FXGraphEditor.class.getResource("/images/doubleellipse.png")),"ellipse;shape=doubleEllipse;", 60, 60, new EntryNode());
        shapesPalette.addTemplate("Proceed",new ImageIcon(FXGraphEditor.class.getResource("/images/doubleellipse.png")),"ellipse;shape=doubleEllipse;", 60, 60, new ProceedNode());
        shapesPalette.addTemplate("Terminate",new ImageIcon(FXGraphEditor.class.getResource("/images/doubleellipse.png")),"ellipse;shape=doubleEllipse;", 60, 60, new TerminateNode());
        shapesPalette.addTemplate("Time Range",new ImageIcon(FXGraphEditor.class.getResource("/images/rhombus.png")),"rhombus", 140, 60, new TimeRangeNode());
        shapesPalette.addTemplate("Expression",new ImageIcon(FXGraphEditor.class.getResource("/images/rhombus.png")),"rhombus", 140, 60, new ExpressionNode());
		shapesPalette.addTemplate("Action", new ImageIcon(FXGraphEditor.class.getResource("/images/rounded.png")), "rounded=1", 140, 60, new ActionNode());


	}

    @Override
    protected void installToolBar()
    {
        add(new MyEditorToolBar(this, JToolBar.HORIZONTAL),BorderLayout.NORTH);
    }

    public static class CustomGraphComponent extends mxGraphComponent
	{
		public CustomGraphComponent(mxGraph graph)
		{
			super(graph);

			// Sets switches typically used in an editor
			setPageVisible(true);
			setGridVisible(true);
			setToolTips(true);
			getConnectionHandler().setCreateTarget(true);

			// Loads the defalt stylesheet from an external file
			mxCodec codec = new mxCodec();
			Document doc = mxUtils.loadDocument(FXGraphEditor.class.getResource("/resources/default-style.xml").toString());
			codec.decode(doc.getDocumentElement(), graph.getStylesheet());

			// Sets the background to white
			getViewport().setOpaque(true);
			getViewport().setBackground(Color.WHITE);
		}

        @Override
        protected mxICellEditor createCellEditor()
        {
            mxCellEditor editor = (mxCellEditor)super.createCellEditor();

            

            return editor;
        }

        /**
		 * Overrides drop behaviour to set the cell style if the target
		 * is not a valid drop target and the cells are of the same
		 * type (eg. both vertices or both edges). 
		 */
		public Object[] importCells(Object[] cells, double dx, double dy, Object target, Point location)
		{
			if (target == null && cells.length == 1 && location != null)
			{
				target = getCellAt(location.x, location.y);

				if (target instanceof mxICell && cells[0] instanceof mxICell)
				{
					mxICell targetCell = (mxICell) target;
					mxICell dropCell = (mxICell) cells[0];

					if (targetCell.isVertex() == dropCell.isVertex() || targetCell.isEdge() == dropCell.isEdge())
					{
						mxIGraphModel model = graph.getModel();
						model.setStyle(target, model.getStyle(cells[0]));
						graph.setSelectionCell(target);

						return null;
					}
				}
			}

			return super.importCells(cells, dx, dy, target, location);
		}

	}

	public static class CustomGraph extends mxGraph
	{
		protected Object edgeTemplate;

		public CustomGraph()
		{
			setAlternateEdgeStyle("edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical");
		}

		public void setEdgeTemplate(Object template)
		{
			edgeTemplate = template;
		}

		/**
		 * Prints out some useful information about the cell in the tooltip.
		 */
		public String getToolTipForCell(Object cell)
		{
			String tip = "<html>";
			mxGeometry geo = getModel().getGeometry(cell);
			mxCellState state = getView().getState(cell);

			if (getModel().isEdge(cell))
			{
				tip += "points={";

				if (geo != null)
				{
					List<mxPoint> points = geo.getPoints();

					if (points != null)
					{
						Iterator<mxPoint> it = points.iterator();

						while (it.hasNext())
						{
							mxPoint point = it.next();
							tip += "[x=" + numberFormat.format(point.getX())
									+ ",y=" + numberFormat.format(point.getY())
									+ "],";
						}

						tip = tip.substring(0, tip.length() - 1);
					}
				}

				tip += "}<br>";
				tip += "absPoints={";

				if (state != null)
				{

					for (int i = 0; i < state.getAbsolutePointCount(); i++)
					{
						mxPoint point = state.getAbsolutePoint(i);
						tip += "[x=" + numberFormat.format(point.getX())
								+ ",y=" + numberFormat.format(point.getY())
								+ "],";
					}

					tip = tip.substring(0, tip.length() - 1);
				}

				tip += "}";
			}
			else
			{
				tip += "geo=[";

				if (geo != null)
				{
					tip += "x=" + numberFormat.format(geo.getX()) + ",y="
							+ numberFormat.format(geo.getY()) + ",width="
							+ numberFormat.format(geo.getWidth()) + ",height="
							+ numberFormat.format(geo.getHeight());
				}

				tip += "]<br>";
				tip += "state=[";

				if (state != null)
				{
					tip += "x=" + numberFormat.format(state.getX()) + ",y="
							+ numberFormat.format(state.getY()) + ",width="
							+ numberFormat.format(state.getWidth())
							+ ",height="
							+ numberFormat.format(state.getHeight());
				}

				tip += "]";
			}

			mxPoint trans = getView().getTranslate();

			tip += "<br>scale=" + numberFormat.format(getView().getScale()) + ", translate=[x=" + numberFormat.format(trans.getX()) + ",y=" + numberFormat.format(trans.getY()) + "]";
			tip += "</html>";

			return tip;
		}

		public Object createEdge(Object parent, String id, Object value, Object source, Object target, String style)
		{
			if (edgeTemplate != null)
			{
				mxCell edge = (mxCell) cloneCells(new Object[] { edgeTemplate })[0];
				edge.setId(id);

				return edge;
			}

            mxCell sourceVertex = (mxCell)source;
            mxCell firstTarget = getFirstTarget(sourceVertex);

            // doesn't have any existing targets
            if(firstTarget == null)
            {
                value = true;
            }
            else
            {
                value = false;
            }

			return super.createEdge(parent, id, value, source, target, style);
		}



        private mxCell getFirstTarget(mxCell source)
        {
            if(source.getEdgeCount() > 0)
            {
                for(int e=0; e<source.getEdgeCount(); e++)
                {
                    mxCell target = (mxCell)((mxCell)source.getEdgeAt(e)).getTarget();
                    if(target != source)
                    {
                        return source;
                    }
                }
            }

            return null;
        }
    }

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

		mxConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
		mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";
		
		FXGraphEditor editor = new FXGraphEditor();
		editor.createFrame(new EditorMenuBar(editor)).setVisible(true);
	}
}
