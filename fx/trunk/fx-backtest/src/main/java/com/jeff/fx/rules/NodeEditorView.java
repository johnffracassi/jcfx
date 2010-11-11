package com.jeff.fx.rules;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;

public class NodeEditorView extends JDialog
{

    private final JPanel contentPanel = new JPanel();
    private JComboBox cboType;
    private JComboBox cboSubType;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        try
        {
            NodeEditorView dialog = new NodeEditorView();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public NodeEditorView()
    {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new MigLayout("", "[grow][grow]", "[][][grow]"));
        {
            JLabel lblNodeType = new JLabel("Node Type");
            contentPanel.add(lblNodeType, "cell 0 0,alignx left");
        }
        {
            cboType = new JComboBox();
            cboType.setModel(new DefaultComboBoxModel(new String[] {"Fixed Value Node", "Logic Node", "Business Node"}));
            contentPanel.add(cboType, "cell 1 0,growx");
        }
        {
            JLabel lblSubtype = new JLabel("Sub-type");
            contentPanel.add(lblSubtype, "cell 0 1,alignx left");
        }
        {
            cboSubType = new JComboBox();
            contentPanel.add(cboSubType, "cell 1 1,growx");
        }
        {
            JPanel panel = new JPanel();
            contentPanel.add(panel, "cell 0 2 2 1,grow");
            panel.setLayout(new BorderLayout(0, 0));
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

    public JComboBox getCboType() {
        return cboType;
    }
    public JComboBox getCboSubType() {
        return cboSubType;
    }
}
