/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.grafico;

import Controle.ControlePrincipal;
import Visao.relatorios.GerarRelatorioEstoqueBasico;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author Cristiano GD
 */
public class Grafico extends javax.swing.JFrame {

    ChartPanel myChartPanel;
    JFreeChart graf;
    String titulo;
    /**
     * Creates new form Grafico
     */
    public Grafico() {
        initComponents();
    }
    
    private CategoryDataset createDataset() { 
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        //dataset.addValue(1000.0,"01/2012","Mês/Ano");
        for(int i=0; i<ControlePrincipal.valor_grafico.length;i++){
            dataset.addValue(ControlePrincipal.valor_grafico[i],ControlePrincipal.info_grafico[i],ControlePrincipal.tipo_grafico);
        }
        return dataset; 
    }
    
    public void criaGrafico() {
        CategoryDataset cds = createDataset();
        titulo = "Gráfico de "+ControlePrincipal.tipo_grafico;
        String eixoy = "Valores";
        String txt_legenda = "Ledenda:";
        boolean legenda = true;
        boolean tooltips = true;
        boolean urls = true;
        graf = ChartFactory.createBarChart3D(ControlePrincipal.descricao, txt_legenda, eixoy, cds, PlotOrientation.VERTICAL, legenda, tooltips, urls);
        
        CategoryPlot plot = (CategoryPlot) graf.getPlot();
        CategoryItemRenderer renderer = plot.getRenderer(); 
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(false);
        //ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.CENTER,
        //TextAnchor.CENTER, TextAnchor.CENTER, 0.0);
        //renderer.setBasePositiveItemLabelPosition(p);
        
        /*ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -Math.PI / 2.0);
        renderer.setBasePositiveItemLabelPosition(p);
        ItemLabelPosition p2 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, -Math.PI / 2.0);
        renderer.setPositiveItemLabelPosition(p2);*/
        
        
       
        CategoryItemRenderer line = plot.getRenderer();  

        //aqui é definido o label  
        line.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
        line.setBaseItemLabelsVisible(true);  
        line.setBaseItemLabelPaint(Color.BLACK);  
        ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BOTTOM_CENTER, TextAnchor.CENTER, 0.0);     
        line.setBasePositiveItemLabelPosition(p);  

			
        myChartPanel = new ChartPanel(graf, true);
        myChartPanel.setSize(jPanel1.getWidth(),jPanel1.getHeight());
        myChartPanel.setVisible(true); 
        jPanel1.removeAll();
        jPanel1.add(myChartPanel); 
        jPanel1.revalidate();
        jPanel1.repaint();         
        
    }
    
    private void GerarPDF() throws DocumentException, FileNotFoundException {
        OutputStream arquivo;
        try {
            arquivo = new FileOutputStream(titulo+".png");
            ChartUtilities.writeChartAsPNG(arquivo, graf, 1204, 768);
            arquivo.close();
            this.dispose();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Grafico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Grafico.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonGerarPDF = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 640));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 192, Short.MAX_VALUE)
        );

        jButtonGerarPDF.setText("Gerar Arquivo");
        jButtonGerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonGerarPDF))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jButtonGerarPDF)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarPDFActionPerformed
        try {
            //AlterarInfo();
            //SelecionarTalhao();
            GerarPDF();
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(Grafico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gerar PDF Error: "+ex);
        }
    }//GEN-LAST:event_jButtonGerarPDFActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Grafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Grafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Grafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Grafico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Grafico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonGerarPDF;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}