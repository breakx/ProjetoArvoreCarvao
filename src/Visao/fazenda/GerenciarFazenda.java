/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.fazenda;

import Controle.ControleEstoquePrincipal;
import Controle.ControleFazenda;
import Controle.ControlePrincipal;
import Controle.fazenda.InserirFazendaCtrl;
import Modelo.ConexaoBD;
import Visao.estoqueprincipal.InserirEstoquePrincipal;
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
public class GerenciarFazenda extends javax.swing.JFrame {

    /**
     * Creates new form GereciarFazenda
     */
    public GerenciarFazenda() throws SQLException {
        initComponents();
        CarregarNome();
        DefaultTableModel dtm = (DefaultTableModel) jTableFazenda.getModel();
        String query = "Select * from fazenda";
        ConexaoBD con = ConexaoBD.getConexao();


        ResultSet rs = con.consultaSql(query);

        while(rs.next()){
            String [] reg = {
                rs.getString("id_fazenda"),
                rs.getString("estado"),
                rs.getString("municipio"),
                rs.getString("fazenda"),
                rs.getString("projeto"),
                rs.getString("bloco"),
                rs.getString("cod_estado"),
                rs.getString("cod_bloco")
            };
            dtm.addRow(reg);
        }
        //JOptionPane.showMessageDialog(null, "Usuario: "+usuario.getNome_usuario());  
        con.fecharConexao();
    }
    
    private void InserirInfo(){
        new InserirFazenda().setVisible(true);
        dispose();
    }
    
    private void AdicionarProjeto(){
        if(jTableFazenda.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableFazenda.getSelectedRow();
            String id_fazenda = jTableFazenda.getValueAt(linha, 0).toString();
            String estado = jTableFazenda.getValueAt(linha, 1).toString();
            String municipio = jTableFazenda.getValueAt(linha, 2).toString();
            String fazenda = jTableFazenda.getValueAt(linha, 3).toString();
            int projeto = Integer.parseInt(jTableFazenda.getValueAt(linha, 4).toString());
            String bloco = jTableFazenda.getValueAt(linha, 5).toString();
            int  cod_est = Integer.parseInt(jTableFazenda.getValueAt(linha, 6).toString());
            int  cod_blc = Integer.parseInt(jTableFazenda.getValueAt(linha, 7).toString());

            projeto++;
            
            //new AlterarFazenda(id_fazenda, cod_est, municipio, fazenda, cod_blc, projeto).setVisible(true);
            
            ControleFazenda faz = new ControleFazenda();
            faz.setId_fazenda(id_fazenda);
            faz.setCod_estado(cod_est);
            faz.setEstado(estado);
            faz.setCod_bloco(cod_blc);
            faz.setBloco(bloco);
            faz.setMunicipio(municipio);
            faz.setFazenda(fazenda);
            faz.setProjeto(projeto);
        
            //SELECT * FROM notas WHERE id_notas >= 0 limit 10

            InserirFazendaCtrl inserir = new InserirFazendaCtrl(faz); 
            
            try {
                new GerenciarFazenda().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(InserirFazenda.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void AlterarInfo(){
        if(jTableFazenda.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableFazenda.getSelectedRow();
            String id_fazenda = jTableFazenda.getValueAt(linha, 0).toString();
            //String estado = jTableFazenda.getValueAt(linha, 1).toString();
            String municipio = jTableFazenda.getValueAt(linha, 2).toString();
            String fazenda = jTableFazenda.getValueAt(linha, 3).toString();
            String projeto = jTableFazenda.getValueAt(linha, 4).toString();
            //String bloco = jTableFazenda.getValueAt(linha, 5).toString();
            String cod_est = jTableFazenda.getValueAt(linha, 6).toString();
            String cod_blc = jTableFazenda.getValueAt(linha, 7).toString();

            new AlterarFazenda(id_fazenda, cod_est, municipio, fazenda, cod_blc, projeto).setVisible(true);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void ExcluirInfo(){
        if(jTableFazenda.getSelectedRow()>=0) {
            int linha = jTableFazenda.getSelectedRow();
            String id_fazenda = jTableFazenda.getValueAt(linha, 0).toString();
            //new ExcluirFazenda(id_usuario).setVisible(true);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    } 
    
    private void InserirEstoque(){
        if(jTableFazenda.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableFazenda.getSelectedRow();
            //String id_fazenda = jTableFazenda.getValueAt(linha, 0).toString();
            ControlePrincipal.estado = jTableFazenda.getValueAt(linha, 1).toString();
            ControlePrincipal.municipio = jTableFazenda.getValueAt(linha, 2).toString();
            ControlePrincipal.fazenda = jTableFazenda.getValueAt(linha, 3).toString();
            ControlePrincipal.projeto = jTableFazenda.getValueAt(linha, 4).toString();
            ControlePrincipal.bloco = jTableFazenda.getValueAt(linha, 5).toString();
            //String cod_est = jTableFazenda.getValueAt(linha, 6).toString();
            //String cod_blc = jTableFazenda.getValueAt(linha, 7).toString();      
            
            /*ControleEstoquePrincipal estoque = new ControleEstoquePrincipal();
            estoque.setEstado(jTableFazenda.getValueAt(linha, 1).toString());
            estoque.setMunicipio(jTableFazenda.getValueAt(linha, 2).toString());
            estoque.setFazenda(jTableFazenda.getValueAt(linha, 3).toString());
            estoque.setProjeto(jTableFazenda.getValueAt(linha, 4).toString());
            estoque.setBloco(jTableFazenda.getValueAt(linha, 5).toString());*/
            
            new InserirEstoquePrincipal().setVisible(true);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }

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
        jButtonInserir = new javax.swing.JButton();
        jButtonAdicionarProjeto = new javax.swing.JButton();
        jButtonCriarEstoque = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFazenda = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Gerenciar Fazenda");
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

        jButtonInserir.setFont(jButtonInserir.getFont().deriveFont(jButtonInserir.getFont().getSize()+1f));
        jButtonInserir.setText("Inserir");
        jButtonInserir.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirActionPerformed(evt);
            }
        });

        jButtonAdicionarProjeto.setFont(jButtonAdicionarProjeto.getFont().deriveFont(jButtonAdicionarProjeto.getFont().getSize()+1f));
        jButtonAdicionarProjeto.setText("<html>Adicionar<br>Projeto</html>");
        jButtonAdicionarProjeto.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonAdicionarProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarProjetoActionPerformed(evt);
            }
        });

        jButtonCriarEstoque.setFont(jButtonCriarEstoque.getFont().deriveFont(jButtonCriarEstoque.getFont().getSize()+1f));
        jButtonCriarEstoque.setText("<html>Criar<br>Estoque</html>");
        jButtonCriarEstoque.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonCriarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCriarEstoqueActionPerformed(evt);
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonAdicionarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonCriarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonAdicionarProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonCriarEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
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

        jTableFazenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_faz", "estado", "municipio", "fazenda", "projeto", "bloco", "cod_est", "cod_blc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableFazenda.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableFazenda.setColumnSelectionAllowed(true);
        jTableFazenda.setFillsViewportHeight(true);
        jTableFazenda.setMaximumSize(new java.awt.Dimension(450, 450));
        jTableFazenda.setMinimumSize(new java.awt.Dimension(450, 450));
        jTableFazenda.setPreferredSize(new java.awt.Dimension(500, 0));
        jTableFazenda.setRequestFocusEnabled(false);
        jTableFazenda.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableFazenda);
        jTableFazenda.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTableFazenda.getColumnModel().getColumnCount() > 0) {
            jTableFazenda.getColumnModel().getColumn(0).setResizable(false);
            jTableFazenda.getColumnModel().getColumn(1).setResizable(false);
            jTableFazenda.getColumnModel().getColumn(2).setResizable(false);
            jTableFazenda.getColumnModel().getColumn(3).setResizable(false);
            jTableFazenda.getColumnModel().getColumn(4).setResizable(false);
            jTableFazenda.getColumnModel().getColumn(5).setResizable(false);
            jTableFazenda.getColumnModel().getColumn(6).setResizable(false);
            jTableFazenda.getColumnModel().getColumn(7).setResizable(false);
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

    private void jButtonInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirActionPerformed
        InserirInfo();
    }//GEN-LAST:event_jButtonInserirActionPerformed

    private void jButtonAdicionarProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarProjetoActionPerformed
        AdicionarProjeto();
    }//GEN-LAST:event_jButtonAdicionarProjetoActionPerformed

    private void jButtonCriarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCriarEstoqueActionPerformed
        InserirEstoque();
    }//GEN-LAST:event_jButtonCriarEstoqueActionPerformed

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
            java.util.logging.Logger.getLogger(GerenciarFazenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarFazenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarFazenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarFazenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerenciarFazenda().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarFazenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdicionarProjeto;
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonCriarEstoque;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonInserir;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableFazenda;
    // End of variables declaration//GEN-END:variables
}
