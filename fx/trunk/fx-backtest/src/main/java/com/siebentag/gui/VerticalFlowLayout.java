package com.siebentag.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

public class VerticalFlowLayout implements LayoutManager2 {
	public static final int TOP = 1;
	public static final int CENTER = 2;
	public static final int BOTTOM = 3;

	protected List<Component> components = new ArrayList<Component>();

	int verticalGap = 0;
	int alignment = TOP;

	public VerticalFlowLayout() {
	}

	public VerticalFlowLayout(int vGap) {
		verticalGap = vGap;
	}

	public VerticalFlowLayout(int vGap, int align) {
		verticalGap = vGap;
		alignment = align;
	}

	/**
	 * Adds a component to the layout using constraints provided. Constraints
	 * are not used at all in this layout.
	 * 
	 * @param comp The Component to add.
	 * @param constraints The constraints object.
	 */
	public void addLayoutComponent(Component comp, Object constraints) {
		components.add(comp);
	}

	/**
	 * Adds a component to the layout by name. This method is irrelevant to this
	 * layout as names are not used.
	 * 
	 * @param name The name to use.
	 * @param comp The component to add.
	 */
	public void addLayoutComponent(String name, Component comp) {
		components.add(comp);
	}

	/**
	 * Retrieve the minimum size that this layout requires.
	 * 
	 * @param target The container that the layout is working on.
	 * @return The minimum size that the layout requires.
	 */
	public Dimension minimumLayoutSize(Container target) {
		Insets insets = target.getInsets();

		int height = 0;
		int width = 0;

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);
			Dimension compSize = comp.getMinimumSize();
			height += compSize.height;
			if (width < compSize.width)
				width = compSize.width;
		}
		height += (components.size() - 1) * verticalGap;

		return new Dimension((width + insets.left + insets.right), (height
				+ insets.top + insets.bottom));
	}

	/**
	 * Retrieve the preferred size for the layout.
	 * 
	 * @param target The container that the layout is working on.
	 * @return The preferred size that the layout requires.
	 */
	public Dimension preferredLayoutSize(Container target) {
		Insets insets = target.getInsets();

		int height = 0;
		int width = 0;

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);
			Dimension compSize = comp.getPreferredSize();
			height += compSize.height;
			if (width < compSize.width)
				width = compSize.width;
		}
		height += (components.size() - 1) * verticalGap;
		return new Dimension((width + insets.left + insets.right), (height
				+ insets.top + insets.bottom));
	}

	/**
	 * Retrieve the maximum size that this layout would need.
	 * 
	 * @param target The container that the layout is working on.
	 * @return The maximum size that the layout requires.
	 */
	public Dimension maximumLayoutSize(Container target) {
		Insets insets = target.getInsets();

		int height = 0;
		int width = 0;

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);
			Dimension compSize = comp.getMaximumSize();
			height += compSize.height;
			if (width < compSize.width)
				width = compSize.width;
		}
		height += (components.size() - 1) * verticalGap;
		return new Dimension((width + insets.left + insets.right), (height
				+ insets.top + insets.bottom));
	}

	/**
	 * Do the laying out of the components. Sets the size and position of all
	 * components in the layout.
	 * 
	 * @param parent The container that the layout is working on.
	 */
	public void layoutContainer(Container parent) {
		
		Component[] components = parent.getComponents();
		Dimension parentSize = parent.getSize();
		Dimension size = preferredLayoutSize(parent);
		Insets insets = parent.getInsets();
		int contentWidth = parentSize.width - insets.left - insets.right;
		int contentHeight = parentSize.height - insets.top - insets.bottom;
		int heightRequirements = size.height - insets.top - insets.bottom;
		int accumulatedHeight = insets.top;

		if (alignment == BOTTOM)
			accumulatedHeight += contentHeight - heightRequirements;

		if (alignment == CENTER)
			accumulatedHeight += (contentHeight - heightRequirements) / 2;

		for (int i = 0; i < components.length; i++) {
			if (i != 0)
				accumulatedHeight += verticalGap;

			Component comp = components[i];
			Dimension compSize = comp.getPreferredSize();
			comp.setSize(new Dimension(contentWidth, compSize.height));
			comp.setLocation(new Point(insets.left, accumulatedHeight));
			accumulatedHeight += compSize.height;
		}
	}

	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	public void invalidateLayout(Container target) {
	}
}
