package com.jeff.fx.graph.editor;

import com.jeff.fx.graph.editor.EditorActions.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Component
public class FXEditorToolBar extends JToolBar
{
    @Autowired
    private ProcessGraphAction processGraphAction;

    @Autowired
    private BasicGraphEditor editor;

	private boolean ignoreZoomChange = false;

	public FXEditorToolBar()
	{
		super(JToolBar.HORIZONTAL);
    }

    @PostConstruct
    private void init()
    {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), getBorder()));
		setFloatable(false);

		add(editor.bind("Process", processGraphAction));
        addSeparator();
        add(editor.bind("New", new NewAction(),"/images/page_add.png"));
        add(editor.bind("Open", new OpenAction(),"/images/folder.png"));
        add(editor.bind("Save", new SaveAction(false),"/images/disk.png"));
        addSeparator();
		add(editor.bind("Print", new PrintAction(),"/images/print.gif"));
		addSeparator();
		add(editor.bind("Cut", TransferHandler.getCutAction(),"/images/cut.png"));
		add(editor.bind("Copy", TransferHandler.getCopyAction(),"/images/page_white_copy.png"));
		add(editor.bind("Paste", TransferHandler.getPasteAction(),"/images/page_paste.png"));
		addSeparator();
		add(editor.bind("Delete", mxGraphActions.getDeleteAction(),"/images/bin.png"));
		addSeparator();
		add(editor.bind("Undo", new HistoryAction(true),"/images/arrow_undo.png"));
		add(editor.bind("Redo", new HistoryAction(false),"/images/arrow_redo.png"));
		addSeparator();

		// Gets the list of available fonts from the local graphics environment
		// and adds some frequently used fonts at the beginning of the list
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		List<String> fonts = new ArrayList<String>();
		fonts.addAll(Arrays.asList(new String[] { "Helvetica", "Verdana", "Times New Roman", "Garamond", "Courier New", "-" }));
		fonts.addAll(Arrays.asList(env.getAvailableFontFamilyNames()));

		final JComboBox fontCombo = new JComboBox(fonts.toArray());
		fontCombo.setEditable(true);
		fontCombo.setMinimumSize(new Dimension(120, 0));
		fontCombo.setPreferredSize(new Dimension(120, 0));
		fontCombo.setMaximumSize(new Dimension(120, 100));
		add(fontCombo);

		fontCombo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String font = fontCombo.getSelectedItem().toString();

				if (font != null && !font.equals("-"))
				{
					mxGraph graph = editor.getGraphComponent().getGraph();
					graph.setCellStyles(mxConstants.STYLE_FONTFAMILY, font);
				}
			}
		});

		final JComboBox sizeCombo = new JComboBox(new Object[] { "6pt", "8pt","9pt", "10pt", "12pt", "14pt", "18pt", "24pt", "30pt", "36pt","48pt", "60pt" });
		sizeCombo.setEditable(true);
		sizeCombo.setMinimumSize(new Dimension(65, 0));
		sizeCombo.setPreferredSize(new Dimension(65, 0));
		sizeCombo.setMaximumSize(new Dimension(65, 100));
		add(sizeCombo);

		sizeCombo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mxGraph graph = editor.getGraphComponent().getGraph();
				graph.setCellStyles(mxConstants.STYLE_FONTSIZE, sizeCombo.getSelectedItem().toString().replace("pt", ""));
			}
		});

		addSeparator();

		add(editor.bind("Bold", new FontStyleAction(true),"/images/text_bold.png"));
		add(editor.bind("Italic", new FontStyleAction(false),"/images/text_italic.png"));

		addSeparator();

		add(editor.bind("Left", new KeyValueAction(mxConstants.STYLE_ALIGN,mxConstants.ALIGN_LEFT),"/images/text_align_left.png"));
		add(editor.bind("Center", new KeyValueAction(mxConstants.STYLE_ALIGN,mxConstants.ALIGN_CENTER),"/images/text_align_center.png"));
		add(editor.bind("Right", new KeyValueAction(mxConstants.STYLE_ALIGN,mxConstants.ALIGN_RIGHT),"/images/text_align_right.png"));

		addSeparator();

		add(editor.bind("Font", new ColorAction("Font",mxConstants.STYLE_FONTCOLOR),"/images/text_uppercase.png"));
		add(editor.bind("Stroke", new ColorAction("Stroke",mxConstants.STYLE_STROKECOLOR),"/images/color_wheel.png"));
		add(editor.bind("Fill", new ColorAction("Fill",mxConstants.STYLE_FILLCOLOR),"/images/color_wheel.png"));

		addSeparator();

		final mxGraphView view = editor.getGraphComponent().getGraph().getView();
		final JComboBox zoomCombo = new JComboBox(new Object[] { "400%","200%", "150%", "100%", "75%", "50%", mxResources.get("page"),mxResources.get("width"), mxResources.get("actualSize") });
		zoomCombo.setEditable(true);
		zoomCombo.setMinimumSize(new Dimension(75, 0));
		zoomCombo.setPreferredSize(new Dimension(75, 0));
		zoomCombo.setMaximumSize(new Dimension(75, 100));
		zoomCombo.setMaximumRowCount(9);
		add(zoomCombo);

		// Sets the zoom in the zoom combo the current value
		mxIEventListener scaleTracker = new mxIEventListener()
		{
			public void invoke(Object sender, mxEventObject evt)
			{
				ignoreZoomChange = true;

				try
				{
					zoomCombo.setSelectedItem((int) Math.round(100 * view.getScale())+ "%");
				}
				finally
				{
					ignoreZoomChange = false;
				}
			}
		};

		// Installs the scale tracker to update the value in the combo box
		// if the zoom is changed from outside the combo box
		view.getGraph().getView().addListener(mxEvent.SCALE, scaleTracker);
		view.getGraph().getView().addListener(mxEvent.SCALE_AND_TRANSLATE,scaleTracker);

		// Invokes once to sync with the actual zoom value
		scaleTracker.invoke(null, null);

		zoomCombo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mxGraphComponent graphComponent = editor.getGraphComponent();

				// Zoomcombo is changed when the scale is changed in the diagram
				// but the change is ignored here
				if (!ignoreZoomChange)
				{
					String zoom = zoomCombo.getSelectedItem().toString();

					if (zoom.equals(mxResources.get("page")))
					{
						graphComponent.setPageVisible(true);
						graphComponent
								.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_PAGE);
					}
					else if (zoom.equals(mxResources.get("width")))
					{
						graphComponent.setPageVisible(true);
						graphComponent
								.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_WIDTH);
					}
					else if (zoom.equals(mxResources.get("actualSize")))
					{
						graphComponent.zoomActual();
					}
					else
					{
						try
						{
							zoom = zoom.replace("%", "");
							double scale = Math.min(16, Math.max(0.01,
									Double.parseDouble(zoom) / 100));
							graphComponent.zoomTo(scale, graphComponent
									.isCenterZoom());
						}
						catch (Exception ex)
						{
							JOptionPane.showMessageDialog(editor, ex
									.getMessage());
						}
					}
				}
			}
		});
	}
}
