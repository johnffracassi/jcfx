package com.jeff.fx.backtest.strategy.coder;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import com.jeff.fx.common.CandleDataPoint;

public class DataModelTreeModel extends DefaultTreeModel implements StrategyParametersListener {

	private static final long serialVersionUID = -7258640164173378016L;

	private DataModelRootNode root;
	private Map<StrategyParam,MutableTreeNode> params = new HashMap<StrategyParam,MutableTreeNode>();
	
	public DataModelTreeModel() {
		super(new DataModelRootNode());
		root = (DataModelRootNode)getRoot();
		root.add(new DataModelIndicatorsNode());
		addNode(new DataModelObjectNode("candle", CandleDataPoint.class));
	}

	public void addNode(DataModelObjectNode node) {
		root.add(node);
		reload(root);
	}
	
	public void parameterAdded(StrategyParam param) {
		DataModelObjectNode node = new DataModelObjectNode(param.getName(), param.getType());
		params.put(param, node);
		addNode(node);
	}

	public void parameterRemoved(StrategyParam param) {
		root.remove(params.remove(param));
		reload(root);
	}

	public void parameterUpdated(StrategyParam param) {
		DataModelObjectNode node = ((DataModelObjectNode)params.get(param));
		if(node != null) {
			node.setUserObject(param);
			reload(node);
		} else {
			parameterAdded(param);
		}
	}

	public void reset() {
		for(MutableTreeNode node : params.values()) {
			root.remove(node);
		}
		params.clear();
		reload(root);
	}
}

