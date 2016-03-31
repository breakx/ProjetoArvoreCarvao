/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.madeira;

import Controle.ControleEstoquePrincipal;
import Controle.ControleMadeira;
import Controle.ControlePrincipal;
import Controle.madeira.AlterarMadeiraCtrl;
import Modelo.ConexaoBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristiano GD
 */
public class AlterarMadeiraEntradaPraca extends javax.swing.JFrame {

    private AlterarMadeiraEntradaPraca() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private String id;
    private float volumeTalhao = 0;
    private float volumePraca = 0;
    private float fator = 0;
    
    /**
     * Creates new form AlterarMadeiraEntradaPraca
     * 
     */
    public AlterarMadeiraEntradaPraca(String id, String id_estoque, String volume, String altura1, String altura2, String altura3, String compr, String larg, String volume_talhao, String peso) throws SQLException {
        super("Alterar Madeira");
        initComponents();
        CarregarEstoque(id_estoque);
        CarregarNome();
        this.id = id;
        ControlePrincipal.id_estoque_principal = id_estoque;
        jTextFieldPracaH1.setText(altura1);
        jTextFieldPracaH2.setText(altura2);
        jTextFieldPracaH3.setText(altura3);
        jTextFieldPracaComprimento.setText(compr);
        jTextFieldPracaLargura.setText(larg);
        jTextFieldPracaPeso.setText(peso);
        jLabelVolumePraca.setText("Volume: "+volume+" m³");
        volumeTalhao = Float.parseFloat(volume_talhao);
    }
    
    private void CarregarEstoque(String id_estq) throws SQLException{
        String query = "Select madeira_talhao, madeira_praca, madeira_forno, mad_ton_tot, carv_ton_tot from estoque_principal where id_estoque_p = "+id_estq;
        ConexaoBD con = ConexaoBD.getConexao();
        
        ResultSet rs = con.consultaSql(query);
        JOptionPane.showMessageDialog(null, "CarregarEstoque: "+query);
        while(rs.next()){
            ControlePrincipal.volume_madeira_talhao = Float.parseFloat(rs.getString("madeira_talhao")); 
            ControlePrincipal.volume_madeira_praca = Float.parseFloat(rs.getString("madeira_praca")); 
            ControlePrincipal.volume_madeira_forno = Float.parseFloat(rs.getString("madeira_forno")); 
            ControlePrincipal.volume_madeira_total = Float.parseFloat(rs.getString("mad_ton_tot")); 
            ControlePrincipal.volume_carvao_total = Float.parseFloat(rs.getString("carv_ton_tot"));       
        }
        con.fecharConexao();
    }   
    
    public void CalcularVolumePraca(){
        if(!jTextFieldPracaH1.getText().equals("0") && !jTextFieldPracaH2.getText().equals("0") && !jTextFieldPracaH3.getText().equals("0") && !jTextFieldPracaLargura.getText().equals("0")&& !jTextFieldPracaComprimento.getText().equals("0")){
            volumePraca = ((Float.parseFloat(jTextFieldPracaH1.getText()) * Float.parseFloat(jTextFieldPracaH2.getText()) * Float.parseFloat(jTextFieldPracaH3.getText()))/3)
            *(Float.parseFloat(jTextFieldPracaLargura.getText()) * Float.parseFloat(jTextFieldPracaComprimento.getText()));
                //JOptionPane.showMessageDialog(null, "Dados ok: "+volumePraca);      
                jLabelVolumePraca.setText("Volume: "+volumePraca+" m³");
        }
        CalculaFator();
    }
    
    private void CalculaFator(){
        fator = volumeTalhao/volumePraca;
        jLabelFatorMadeira.setText("Fator: "+fator);
        RegistarCargaPraca();
    }
    
    private void RegistarCargaPraca(){
        DateFormat data_talhao = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
        Date date = new Date();
        
        ControleMadeira madeira = new ControleMadeira();
        madeira.setId_controle_madeira(id);
        //madeira.setId_operario("12");
        //madeira.setTalhao("t001");
        madeira.setEntrada_volume_praca(volumePraca);
        madeira.setData_praca(data_talhao.format(date));
        madeira.setAltura1_p(Float.parseFloat(jTextFieldPracaH1.getText()));
        madeira.setAltura2_p(Float.parseFloat(jTextFieldPracaH2.getText()));
        madeira.setAltura3_p(Float.parseFloat(jTextFieldPracaH3.getText()));
        madeira.setComprimento_p(Float.parseFloat(jTextFieldPracaComprimento.getText()));
        madeira.setLargura_p(Float.parseFloat(jTextFieldPracaLargura.getText()));        
        madeira.setPeso_p(Float.parseFloat(jTextFieldPracaPeso.getText()));
        madeira.setFator(fator);
        //madeira.setComprimento_p(Float.parseFloat(jComboBoxPracaComprimento.getSelectedItem().toString()));
        //madeira.setLargura_p(Float.parseFloat(jComboBoxPracaLargura.getSelectedItem().toString()));

        //JOptionPane.showMessageDialog(null, "Dados ok: "+jComboBoxPracaComprimento.getSelectedIndex()); 
        
        ControlePrincipal.volume_madeira_praca += volumePraca;
        
        //JOptionPane.showMessageDialog(null, "Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_total+" carv: "+ControlePrincipal.volume_carvao_total);
        
        AlterarMadeiraCtrl alterar = new AlterarMadeiraCtrl(madeira);

        try {
            new GerenciarMadeiraTalhaoPraca().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(InserirMadeiraSaidaTalhao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setVisible(false);
        dispose();
    }
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    }   
    
    private void VoltarMenu(){
        try {   
            new GerenciarMadeiraTalhaoPraca().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(AlterarMadeiraEntradaPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setVisible(false);
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelName1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldPracaH1 = new javax.swing.JTextField();
        jTextFieldPracaH2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldPracaH3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabelVolumePraca = new javax.swing.JLabel();
        jButtonCargaPraca = new javax.swing.JButton();
        jButtonVoltar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldPracaPeso = new javax.swing.JTextField();
        jLabelFatorMadeira = new javax.swing.JLabel();
        jTextFieldPracaLargura = new javax.swing.JTextField();
        jTextFieldPracaComprimento = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelName1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelName1.setText("Entrada de Madeira na Praça");
        jLabelName1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelName1.setPreferredSize(new java.awt.Dimension(275, 70));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(270, 145));

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+4f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Bem Vindo");

        jLabelNome.setFont(jLabelNome.getFont().deriveFont((jLabelNome.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, jLabelNome.getFont().getSize()+4));
        jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelNome.setText("Usuario");

        jLabelIdTipo.setFont(jLabelIdTipo.getFont().deriveFont(jLabelIdTipo.getFont().getSize()+1f));
        jLabelIdTipo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdTipo.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(53, 53, 53))
                    .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(94, 94, 94))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 350));

        jButtonLogout.setFont(jButtonLogout.getFont().deriveFont(jButtonLogout.getFont().getSize()+13f));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(252, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel6.setText("Altura-1");

        jTextFieldPracaH1.setText("0");

        jTextFieldPracaH2.setText("0");

        jLabel7.setText("Altura-2");

        jLabel8.setText("Altura-3");

        jTextFieldPracaH3.setText("0");

        jLabel9.setText("Comprimento");

        jLabel10.setText("Largura");

        jLabelVolumePraca.setText("Volume: 0m³");

        jButtonCargaPraca.setText("Praça");
        jButtonCargaPraca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaPracaActionPerformed(evt);
            }
        });

        jButtonVoltar.setText("Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabel11.setText("Peso");

        jTextFieldPracaPeso.setText("0");
        jTextFieldPracaPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPracaPesoActionPerformed(evt);
            }
        });

        jLabelFatorMadeira.setText("Fator: ");

        jTextFieldPracaLargura.setText("0");

        jTextFieldPracaComprimento.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(58, 58, 58)
                            .addComponent(jTextFieldPracaPeso))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButtonCargaPraca, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelVolumePraca))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButtonVoltar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelFatorMadeira, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldPracaComprimento, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPracaH3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(jTextFieldPracaH2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPracaH1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPracaLargura))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldPracaH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldPracaH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldPracaH3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldPracaComprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldPracaLargura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPracaPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVolumePraca)
                    .addComponent(jLabelFatorMadeira))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCargaPraca)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(85, 85, 85))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(290, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(510, 510, 510))
                        .addComponent(jLabelName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(7, 7, 7)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCargaPracaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaPracaActionPerformed
        CalcularVolumePraca();
        //RegistarCargaPraca();
    }//GEN-LAST:event_jButtonCargaPracaActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jTextFieldPracaPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPracaPesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPracaPesoActionPerformed

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
            java.util.logging.Logger.getLogger(AlterarMadeiraEntradaPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlterarMadeiraEntradaPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlterarMadeiraEntradaPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlterarMadeiraEntradaPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlterarMadeiraEntradaPraca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCargaPraca;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFatorMadeira;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelName1;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelVolumePraca;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldPracaComprimento;
    private javax.swing.JTextField jTextFieldPracaH1;
    private javax.swing.JTextField jTextFieldPracaH2;
    private javax.swing.JTextField jTextFieldPracaH3;
    private javax.swing.JTextField jTextFieldPracaLargura;
    private javax.swing.JTextField jTextFieldPracaPeso;
    // End of variables declaration//GEN-END:variables
}
