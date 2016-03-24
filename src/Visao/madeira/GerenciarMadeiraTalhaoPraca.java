/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.madeira;

import Controle.ControlePrincipal;
import Controle.ControleUsuario;
import Modelo.ConexaoBD;
import Visao.relatorios.BuscarRelatorioMadeiraEstoquePrincipal;
import Visao.login.Login;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cristiano GD
 */
public class GerenciarMadeiraTalhaoPraca extends javax.swing.JFrame {

    ControleUsuario usuario = new ControleUsuario();
    
    /**
     * Creates new form Movimentar_Madeira_Talhao_Praca
     * @throws java.sql.SQLException
     */
    public GerenciarMadeiraTalhaoPraca() throws SQLException {
        initComponents();
        CarregarNome();
        DefaultTableModel dtm = (DefaultTableModel) jTableMadeira.getModel();
        String query;
        if("op_s".equals(ControlePrincipal.tipo_u)){
            query = "Select * from controle_madeira";
        }else{
            query = "Select * from controle_madeira where id_operario = '" +ControlePrincipal.id_op+"'";
        }
        ConexaoBD con = ConexaoBD.getConexao();
        
        ResultSet rs = con.consultaSql(query);

        while(rs.next()){
            String [] reg = {
                rs.getString("id_controle_madeira"),
                rs.getString("id_estoque_p"),
                rs.getString("id_operario"),
                rs.getString("talhao"),
                rs.getString("saida_volume_talhao"),
                rs.getString("data_talhao"),
                rs.getString("altura1_t"),
                rs.getString("altura2_t"),
                rs.getString("altura3_t"),
                rs.getString("comprimento_t"),
                rs.getString("largura_t"),
                rs.getString("peso_t"),
                rs.getString("entrada_volume_praca"),
                rs.getString("data_praca"),
                rs.getString("altura1_p"),
                rs.getString("altura2_p"),
                rs.getString("altura3_p"),
                rs.getString("comprimento_p"),
                rs.getString("largura_p"),
                rs.getString("peso_p"),
                rs.getString("fator")};
            dtm.addRow(reg);
        }
        con.fecharConexao();
    }       
    
    private void AlterarInfo(){
        if(jTableMadeira.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableMadeira.getSelectedRow();
            
            if("0".equals(jTableMadeira.getValueAt(linha, 12).toString())){
                //JOptionPane.showMessageDialog(null, "Em transporte!"+jTableMadeira.getValueAt(linha, 12).toString());
                String id_controle_madeira = jTableMadeira.getValueAt(linha, 0).toString();
                String id_estoque_p = jTableMadeira.getValueAt(linha, 1).toString();
                //String id_operador = jTableMadeira.getValueAt(linha, 2).toString();
                //String talhao = jTableMadeira.getValueAt(linha, 3).toString();
                String saida_volume_talhao = jTableMadeira.getValueAt(linha, 4).toString();
                /*String data_talhao = jTableMadeira.getValueAt(linha, 5).toString();
                String altura1_t = jTableMadeira.getValueAt(linha, 6).toString();
                String altura2_t = jTableMadeira.getValueAt(linha, 7).toString();
                String altura3_t = jTableMadeira.getValueAt(linha, 8).toString();
                String comprimento_t = jTableMadeira.getValueAt(linha, 9).toString();
                String largura_t = jTableMadeira.getValueAt(linha, 10).toString();            
                String peso_t = jTableMadeira.getValueAt(linha, 11).toString();*/
                String entrada_volume_praca = jTableMadeira.getValueAt(linha, 12).toString();
                String data_praca = jTableMadeira.getValueAt(linha, 13).toString();
                String altura1_p = jTableMadeira.getValueAt(linha, 14).toString();
                String altura2_p = jTableMadeira.getValueAt(linha, 15).toString();
                String altura3_p = jTableMadeira.getValueAt(linha, 16).toString();
                String comprimento_p = jTableMadeira.getValueAt(linha, 17).toString();
                String largura_p = jTableMadeira.getValueAt(linha, 18).toString();            
                String peso_p = jTableMadeira.getValueAt(linha, 19).toString();
                //String fator = jTableMadeira.getValueAt(linha, 20).toString();
                try {
                    new AlterarMadeiraEntradaPraca(id_controle_madeira, id_estoque_p, entrada_volume_praca, altura1_p, altura2_p, altura3_p,
                            comprimento_p, largura_p, saida_volume_talhao, peso_p).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarMadeiraTalhaoPraca.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Transporte OK!");
            }
            
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void ExcluirInfo(){
        if(jTableMadeira.getSelectedRow()>=0) {
            int linha = jTableMadeira.getSelectedRow();
            String id_madeira = jTableMadeira.getValueAt(linha, 0).toString();
            new ExcluirMadeiraInfo(id_madeira).setVisible(true);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void InserirInfo(){
        if(ControlePrincipal.talhao != null)//verifica se a linha a ser alterada esta marcada
        {
            new InserirMadeiraSaidaTalhao().setVisible(true);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione um talhão!");
    } 
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
        ControlePrincipal.tela_anterior = "madeira";
        //JOptionPane.showMessageDialog(null, "Tela: "+ControlePrincipal.tela_anterior);
    } 
    
    /*public void CalcularFator(){
        if(volumePraca != 0 && volumeTalhao != 0){
            //JOptionPane.showMessageDialog(null, "Dados ok: "+(volumePraca-volumeTalhao));
            jLabelFator.setText("Fator: "+(volumePraca-volumeTalhao));
        }
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonBuscarEstoque = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMadeira = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Gerenciar Madeira");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 70));

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
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
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

        jButtonAlterar.setFont(jButtonAlterar.getFont().deriveFont(jButtonAlterar.getFont().getSize()+1f));
        jButtonAlterar.setText("Alterar");
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonExcluir.setFont(jButtonExcluir.getFont().deriveFont(jButtonExcluir.getFont().getSize()+1f));
        jButtonExcluir.setText("Excluir");
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jButtonLogout.setFont(jButtonLogout.getFont().deriveFont(jButtonLogout.getFont().getSize()+13f));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        jButtonBuscarEstoque.setFont(jButtonBuscarEstoque.getFont().deriveFont(jButtonBuscarEstoque.getFont().getSize()+1f));
        jButtonBuscarEstoque.setText("<html>Buscar <br>Estoque</html>");
        jButtonBuscarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarEstoqueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonBuscarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jButtonBuscarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setDebugGraphicsOptions(javax.swing.DebugGraphics.LOG_OPTION);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(450, 450));
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(450, 450));
        jScrollPane1.setRequestFocusEnabled(false);

        jTableMadeira.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_mad", "id_estoque", "id_op", "talhao", "s_vol_talhao", "data_t", "h1_t", "h2_t", "h3_t", "compr_t", "larg_t", "peso_t", "e_vol_praca", "data_p", "h1_p", "h2_p", "h3_p", "compr_p", "larg_p", "peso_p", "fator"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMadeira.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableMadeira.setColumnSelectionAllowed(true);
        jTableMadeira.setFillsViewportHeight(true);
        jTableMadeira.setMaximumSize(new java.awt.Dimension(450, 450));
        jTableMadeira.setMinimumSize(new java.awt.Dimension(450, 450));
        jTableMadeira.setPreferredSize(new java.awt.Dimension(1300, 100));
        jTableMadeira.setRequestFocusEnabled(false);
        jTableMadeira.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableMadeira.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableMadeira);
        jTableMadeira.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTableMadeira.getColumnModel().getColumnCount() > 0) {
            jTableMadeira.getColumnModel().getColumn(0).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(1).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(2).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(3).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(4).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(5).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(6).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(7).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(8).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(9).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(10).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(11).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(12).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(13).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(14).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(15).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(16).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(17).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(18).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(19).setResizable(false);
            jTableMadeira.getColumnModel().getColumn(20).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        AlterarInfo();
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        ExcluirInfo();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonBuscarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarEstoqueActionPerformed
        new BuscarRelatorioMadeiraEstoquePrincipal().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonBuscarEstoqueActionPerformed
    
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
            java.util.logging.Logger.getLogger(GerenciarMadeiraTalhaoPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarMadeiraTalhaoPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarMadeiraTalhaoPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarMadeiraTalhaoPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerenciarMadeiraTalhaoPraca().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarMadeiraTalhaoPraca.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonBuscarEstoque;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMadeira;
    // End of variables declaration//GEN-END:variables
}
