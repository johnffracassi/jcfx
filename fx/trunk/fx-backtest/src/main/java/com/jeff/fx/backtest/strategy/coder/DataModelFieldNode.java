package com.jeff.fx.backtest.strategy.coder;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.swing.tree.DefaultMutableTreeNode;

public class DataModelFieldNode extends DefaultMutableTreeNode implements Transferable {
	
	private static final long serialVersionUID = 4825802451644794966L;
	
	public boolean isLeaf() {
		return true;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { new DataFlavor(Method.class, "Method Call") };
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return (flavor.getRepresentationClass() == Method.class);
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if(isDataFlavorSupported(flavor)) {
			return ((Method)getUserObject()).getName();
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}
}
