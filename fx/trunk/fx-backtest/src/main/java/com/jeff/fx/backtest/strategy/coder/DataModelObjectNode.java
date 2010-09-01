package com.jeff.fx.backtest.strategy.coder;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

public class DataModelObjectNode extends DefaultMutableTreeNode {
	
	private static final long serialVersionUID = -3588047664736178622L;

	private String label;
	private boolean leaf;
	private static Set<Class<?>> leafTypes = new HashSet<Class<?>>();
	
	static {
		leafTypes.add(Integer.class);
		leafTypes.add(String.class);
		leafTypes.add(Double.class);
		leafTypes.add(Float.class);
		leafTypes.add(Long.class);
		leafTypes.add(Byte.class);
		leafTypes.add(Short.class);
		leafTypes.add(Character.class);
		leafTypes.add(Boolean.class);
		leafTypes.add(int.class);
		leafTypes.add(double.class);
		leafTypes.add(float.class);
		leafTypes.add(long.class);
		leafTypes.add(byte.class);
		leafTypes.add(char.class);
		leafTypes.add(short.class);
		leafTypes.add(boolean.class);
	}
	
	public DataModelObjectNode(String label, Class<?> value) {
		
		super(value);
		this.label = label;
		leaf = leafTypes.contains(value);
		rebuildChildren(value);
	}
	
	private void rebuildChildren(Class<?> type) {
		
		removeAllChildren();

		if(!leaf) {
			for(Method method : type.getMethods()) {
				if(method.getName().startsWith("get") && !method.getName().equals("getClass")) {
					DataModelFieldNode node = new DataModelFieldNode();
					node.setUserObject(method);
					add(node);
				}
			}
		}
	}
	
	public String getLabel() {
		return label;
	}

	public void setUserObject(StrategyParam param) {
		label = param.getName();
		super.setUserObject(param.getType());
		leaf = leafTypes.contains(param.getType());
		rebuildChildren(param.getType());
	}
	
	public boolean isLeaf() {
		return leaf;
	}
}