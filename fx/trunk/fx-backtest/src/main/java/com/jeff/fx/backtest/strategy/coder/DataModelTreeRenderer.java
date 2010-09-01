package com.jeff.fx.backtest.strategy.coder;

import java.awt.Component;
import java.lang.reflect.Method;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

class DataModelTreeRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -2926391722146347589L;

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

		if(value instanceof DataModelFieldNode) {
			
			// node representing a field on a class
			Method method = ((Method)((DataModelFieldNode)value).getUserObject());
			String label =  Character.toLowerCase(method.getName().charAt(3)) + method.getName().substring(4) + ": " + method.getReturnType().getSimpleName();
			return super.getTreeCellRendererComponent(tree, label, sel, expanded, leaf, row, hasFocus);
			
		} else if(value instanceof DataModelObjectNode) {
			
			// object node with class methods as leafs
			DataModelObjectNode node = (DataModelObjectNode)value;
			String label = node.getLabel() + ": " + ((Class<?>)node.getUserObject()).getSimpleName();
			return super.getTreeCellRendererComponent(tree, label, sel, expanded, leaf, row, hasFocus);
			
		} else {
			
			return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		}
	}
	
	
}
