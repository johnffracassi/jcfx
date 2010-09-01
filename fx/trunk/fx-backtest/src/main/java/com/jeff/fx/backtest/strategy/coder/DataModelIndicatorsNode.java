package com.jeff.fx.backtest.strategy.coder;

import javax.swing.tree.DefaultMutableTreeNode;

import com.jeff.fx.backtest.strategy.IndicatorCache;
import com.jeff.fx.indicator.Indicator;

public class DataModelIndicatorsNode extends DefaultMutableTreeNode {
	
	private static final long serialVersionUID = -3588047664736L;

	public DataModelIndicatorsNode() {
		super("<Indicators>");
		rebuildChildren();
	}
	
	private void rebuildChildren() {
		
		removeAllChildren();

		for(Class<? extends Indicator> indicator : IndicatorCache.getAllIndicators()) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(indicator.getSimpleName());
			add(node);
		}
	}
}