/*
 * Created on 11/08/2010, 5:26:42 PM
 */

package com.jeff.fx.backtest.dataviewer;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;
import org.jfree.chart.ChartPanel;

import com.jeff.fx.gui.GUIUtil;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author jeff
 */
@SuppressWarnings("serial")
public class CandleDataView extends javax.swing.JPanel {

    /** Creates new form NewJPanel */
    public CandleDataView() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlData = new javax.swing.JPanel();
        pnlActions = new javax.swing.JPanel();
        btnPrevWeek = new javax.swing.JButton();
        btnNextWeek = new javax.swing.JButton();
        btnDone = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        table = new JXTable();
        pnlDetails = new javax.swing.JPanel();
        pnlSummary = new javax.swing.JPanel();
        pnlSummary.setBorder(new EmptyBorder(5, 5, 5, 5));
        jLabel7 = new javax.swing.JLabel();
        lblDetails = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblOpenTime = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCloseTime = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCandleCount = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblOpenClose = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblPriceRange = new javax.swing.JLabel();
        pnlChart = new javax.swing.JPanel();
        pnlChart.setLayout(new BorderLayout());

        pnlData.setLayout(new java.awt.BorderLayout());

        btnPrevWeek.setText("< Prev");
        pnlActions.add(btnPrevWeek);

        btnNextWeek.setText("Next >");
        pnlActions.add(btnNextWeek);

        btnDone.setText("Done");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });
        btnDone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnDoneFocusGained(evt);
            }
        });
        pnlActions.add(btnDone);

        pnlData.add(pnlActions, java.awt.BorderLayout.SOUTH);
        scrollPane.setViewportView(table);

        pnlData.add(scrollPane, java.awt.BorderLayout.CENTER);

        pnlDetails.setLayout(new java.awt.BorderLayout());

        pnlSummary.setLayout(new java.awt.GridLayout(6, 2, 0, 4));

        jLabel7.setText("Details");
        pnlSummary.add(jLabel7);

        lblDetails.setText("Forexite / AUDUSD / m1");
        pnlSummary.add(lblDetails);

        jLabel1.setText("Open");
        pnlSummary.add(jLabel1);

        lblOpenTime.setText("05/04/2010 18:00");
        pnlSummary.add(lblOpenTime);

        jLabel3.setText("Close");
        pnlSummary.add(jLabel3);

        lblCloseTime.setText("10/04/2010 17:54");
        pnlSummary.add(lblCloseTime);

        jLabel5.setText("Candle Count");
        pnlSummary.add(jLabel5);

        lblCandleCount.setText("0");
        pnlSummary.add(lblCandleCount);

        jLabel8.setText("Open/Close Price");
        pnlSummary.add(jLabel8);

        lblOpenClose.setText("0.8900 / 0.8984");
        pnlSummary.add(lblOpenClose);

        jLabel11.setText("Price Range");
        pnlSummary.add(jLabel11);

        lblPriceRange.setText("+84");
        pnlSummary.add(lblPriceRange);
        setLayout(new MigLayout("", "[600px:n,grow 20][250:n,grow 80]", "[160][grow,fill]"));

        pnlSummary = GUIUtil.frame("Dataset Summary", pnlSummary);
        pnlChart = GUIUtil.frame("Price History", pnlChart);
        pnlData = GUIUtil.frame("Candle Data", pnlData);

        add(pnlSummary, "cell 1 0,grow");
        add(pnlChart, "cell 1 1,grow");
        add(pnlData, "cell 0 0 1 2,grow");
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnDoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnDoneFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDoneFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnNextWeek;
    private javax.swing.JButton btnPrevWeek;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblCandleCount;
    private javax.swing.JLabel lblCloseTime;
    private javax.swing.JLabel lblDetails;
    private javax.swing.JLabel lblOpenClose;
    private javax.swing.JLabel lblOpenTime;
    private javax.swing.JLabel lblPriceRange;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlData;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlSummary;
    private javax.swing.JScrollPane scrollPane;
    private JXTable table;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnDone() {
        return btnDone;
    }

    public void setBtnDone(JButton btnDone) {
        this.btnDone = btnDone;
    }

    public JButton getBtnNextWeek() {
        return btnNextWeek;
    }

    public void setBtnNextWeek(JButton btnNextWeek) {
        this.btnNextWeek = btnNextWeek;
    }

    public JButton getBtnPrevWeek() {
        return btnPrevWeek;
    }

    public void setBtnPrevWeek(JButton btnPrevWeek) {
        this.btnPrevWeek = btnPrevWeek;
    }

    public JPanel getPnlActions() {
        return pnlActions;
    }

    public void setPnlChart(ChartPanel chart) {
    	pnlChart.removeAll();
    	pnlChart.add(chart, BorderLayout.CENTER);
    }
    
    public void setPnlActions(JPanel pnlActions) {
        this.pnlActions = pnlActions;
    }

    public JLabel getLblCandleCount() {
        return lblCandleCount;
    }

    public JLabel getLblCloseTime() {
        return lblCloseTime;
    }

    public JLabel getLblDetails() {
        return lblDetails;
    }

    public JLabel getLblOpenClose() {
        return lblOpenClose;
    }

    public JLabel getLblOpenTime() {
        return lblOpenTime;
    }

    public JLabel getLblPriceRange() {
        return lblPriceRange;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JXTable getTable() {
        return table;
    }

    public void setTable(JXTable table) {
        this.table = table;
    }

}
