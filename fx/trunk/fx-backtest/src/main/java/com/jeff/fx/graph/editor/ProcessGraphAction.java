package com.jeff.fx.graph.editor;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.common.*;
import com.jeff.fx.datastore.CandleDataStore;
import com.jeff.fx.graph.node.BaseNode;
import com.jeff.fx.graph.node.EntryNode;
import com.jeff.fx.filter.CandleFilterModel;
import com.jeff.fx.filter.CandleFilterModelEvaluator;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;

@org.springframework.stereotype.Component
public class ProcessGraphAction extends AbstractAction
{
    @Autowired
    private CandleDataStore loader;

    @Autowired
    private CandleFilterModelEvaluator evaluator;

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

    private void apply(EntryNode node)
    {
        long stime = System.nanoTime();

        try
        {
            CandleCollection candles = loadTestData();
            CandleFilterModel model = new CandleFilterModel(candles, new IndicatorCache(), evaluator);

            for(int c=0; c<candles.getCandleCount(); c++)
            {
                model.setIndex(c);
                CandleDataPoint candle = model.getCandles().getCandle(c);
                boolean result = decide(node, model, c);

                if(result)
                    System.out.println(candle + " = " + result);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.printf("Completed in %.3fs", (System.nanoTime() - stime) / 1000000000.0);
    }

    private CandleCollection loadTestData() throws IOException
    {
        FXDataSource dataSource = FXDataSource.Forexite;
        Instrument instrument = Instrument.GBPUSD;
        Period period = Period.FifteenMin;
        LocalDate startDate = new LocalDate(2010, 10, 13);

        FXDataRequest request = new FXDataRequest();
        request.setDataSource(dataSource);
        request.setDate(startDate);
        request.setEndDate(startDate);
        request.setInstrument(instrument);
        request.setPeriod(period);
        return new CandleCollection(loader.loadCandlesForWeek(request));
    }

    public void actionPerformed(ActionEvent e)
    {
        mxGraphComponent graphComponent = getEditor(e).getGraphComponent();
        mxGraph graph = graphComponent.getGraph();

        mxCell root = (mxCell)graph.getModel().getRoot();
        mxCell enterNode = findEnterNode(root);

        // link up the decision tree nodes
        traverseVertex(enterNode, "");

        apply((EntryNode) enterNode.getValue());
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

    private boolean decide(BaseNode node, CandleFilterModel model, int idx)
    {
        boolean result = node.evaluate(model, idx);

        if(!node.isTerminal())
        {
            result = decide(node.getChild(result), model, idx);
        }

        return result;
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
            BaseNode src = (BaseNode)source.getValue();
            BaseNode dest = (BaseNode)edge.getTarget().getValue();
            boolean value = getEdgeValue(edge);
            src.setChildNode(value, dest);

            System.out.printf("%s> %s Target:%s Source:%s%n", indent, value, edge.getTarget().getValue().getClass().getSimpleName(), edge.getSource().getValue().getClass().getSimpleName());
            traverseVertex((mxCell)edge.getTarget(), indent);
        }
    }

    private boolean getEdgeValue(mxCell edge)
    {
        if(edge.getValue() instanceof Boolean)
            return (Boolean)edge.getValue();

        if(edge.getValue() instanceof Integer)
        {
            if(((Integer)edge.getValue()) == 0)
                return false;
            else if(((Integer)edge.getValue()) == 1)
                return true;
            else
                throw new RuntimeException(String.format("Don't know how to handle edge value integer '%s'", edge.getValue()));
        }

        if(edge.getValue() instanceof String)
        {
            if((edge.getValue().equals("false") || edge.getValue().equals("0")))
                return false;
            else if((edge.getValue().equals("true") || edge.getValue().equals("1")))
                return true;
            else
                throw new RuntimeException(String.format("Don't know how to handle edge value string '%s'", edge.getValue()));
        }

        throw new RuntimeException(String.format("Don't know how to handle edge value of class '%s'", edge.getValue().getClass()));
    }
}
