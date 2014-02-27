/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.stocktrader.core;

import static INFSYS.queries.INFSistemQuery.Br_RNFA_Mesec_LineChartData;
import java.text.ParseException;
import org.dobrivoje.javafx.generators.BarChartGenerator2;
import org.dobrivoje.javafx.generators.LineChartGenerator2;
import org.dobrivoje.javafx.generators.LineChartGenerator3;
import org.dobrivoje.javafx.generators.StackedBarChartGenerator2;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.stocktrader.core//Core//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "CoreTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.stocktrader.core.CoreTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_CoreAction",
        preferredID = "CoreTopComponent"
)
@Messages({
    "CTL_CoreAction=Core",
    "CTL_CoreTopComponent=Core Window",
    "HINT_CoreTopComponent=This is a Core window"
})
public final class CoreTopComponent extends TopComponent {

    private static final BarChartGenerator2 bCG2 = new BarChartGenerator2();
    private static final StackedBarChartGenerator2 stackedBCG2 = new StackedBarChartGenerator2();
    //
    private static final LineChartGenerator2 lcg2 = LineChartGenerator2.getDefault();
    private static final LineChartGenerator3 lcg3 = LineChartGenerator3.getDefault();
    private static final String[] dattt = new String[]{"2011-3-1", "2013-6-1", "2010-11-1", "2008-5-1", "2009-3-1", "2010-11-1", "2013-7-1"};

    public CoreTopComponent() {
        initComponents();
        setName(Bundle.CTL_CoreTopComponent());
        setToolTipText(Bundle.HINT_CoreTopComponent());

        bCG2.barChartSetUpPanel(leftPanel);
        bCG2.createFXObject();

        stackedBCG2.barChartSetUpPanel(midPanel);
        stackedBCG2.createFXObject();

        lcg2.lineChartSetUpPanel(rightPanel);
        lcg2.createFXObject();

        lcg3.lineChartSetUpPanel(downPanel);
        try {
            lcg3.setSerije(
                    Br_RNFA_Mesec_LineChartData("2010-6-1", 1),
                    Br_RNFA_Mesec_LineChartData("2010-6-1", 2),
                    Br_RNFA_Mesec_LineChartData("2010-6-1", 3)
            );

            lcg3.setSerijeNazivi("RN !", "FA.", "...");
            lcg3.createFXObject();
        } catch (ParseException ex) {
        } catch (NullPointerException n) {
            StatusDisplayer.getDefault().setStatusText("NULL !");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        midPanel = new javax.swing.JPanel();
        rightPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        downPanel = new javax.swing.JPanel();

        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        leftPanel.setLayout(new java.awt.BorderLayout());

        midPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        midPanel.setLayout(new java.awt.BorderLayout());

        rightPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        rightPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(CoreTopComponent.class, "CoreTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(midPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(midPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(6, 6, 6))
        );

        downPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        downPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(downPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        componentOpened();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel downPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel midPanel;
    private javax.swing.JPanel rightPanel;
    // End of variables declaration//GEN-END:variables

    //<editor-fold defaultstate="collapsed" desc="NB Defaults">
    @Override
    public void componentOpened() {
        bCG2.createFXObject();
        stackedBCG2.createFXObject();
        lcg2.createFXObject();

        try {
            lcg3.setSerije(
                    Br_RNFA_Mesec_LineChartData(dattt[(int) (dattt.length * Math.random())], 1),
                    Br_RNFA_Mesec_LineChartData(dattt[(int) (dattt.length * Math.random())], 2),
                    Br_RNFA_Mesec_LineChartData(dattt[(int) (dattt.length * Math.random())], 3)
            );

            lcg3.setSerijeNazivi("RN !", "FA", "ST");
            lcg3.createFXObject();
        } catch (ParseException ex) {
        } catch (NullPointerException n) {
            StatusDisplayer.getDefault().setStatusText("NULL !");
        }
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    //</editor-fold>
}
