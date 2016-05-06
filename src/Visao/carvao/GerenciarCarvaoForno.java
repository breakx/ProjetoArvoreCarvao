/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.carvao;

import Controle.ControlePrincipal;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.login.Login;
import Visao.buscas.BuscarRelatorioCarvaoPraca;
import Visao.relatorios.GerarRelatorioEstoqueBasico;
import Visao.relatorios.GerarRelatorioEstoquePrincipal;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Cristiano GD
 */
public class GerenciarCarvaoForno extends javax.swing.JFrame {
    
    /**
     * Creates new form Carvao
     * @throws java.sql.SQLException
     */
    public GerenciarCarvaoForno() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        jButtonExcluir.setVisible(false);
        if(!ControlePrincipal.tipo_u.equals("op_dir")){
            jButtonRelatorio.setVisible(false);
        }
        CarregarNome();
        PreencherTabela();
    }   
    
    /**
     * 
     */
    private void PreencherTabela(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[] { 
            "upc_c", 
            "talhao", 
            "forno", 
            "volume_madeira", 
            "volume_carvao", 
            "data_entrada_madeira_forno",  
            "data_saida_carvao_forno", 
            "id_controle_carvao", 
            "id_estoque_p", 
            "id_operario", 
            "rend_grav_forno"
        };
        String query;
        int tamanho = 0;
        if(ControlePrincipal.tipo_u.equals("op_dir")){
            query = "Select * from controle_carvao";
        }else{
            query = "Select * from controle_carvao where id_operario = '" +ControlePrincipal.id_op+"'";
        }
        
        ConexaoBD con = ConexaoBD.getConexao();
        ResultSet rs = con.consultaSql(query);
        
        try {
            while(rs.next()){
                dados.add(new Object[]{
                    rs.getString("upc_c"),//0
                    rs.getString("talhao"),//1
                    rs.getString("forno"),//2
                    rs.getString("volume_madeira"),//3              
                    rs.getString("volume_carvao"),//4
                    rs.getString("data_entrada_madeira_forno"),//5
                    rs.getString("data_saida_carvao_forno"),//6
                    rs.getString("id_estoque_p"),//7
                    rs.getString("id_operario"),//8
                    rs.getString("rend_grav_forno"),//9
                    rs.getString("id_controle_carvao")//10
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        GerarTabela modelo = new GerarTabela(dados, colunas);
        jTableCarvao.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }
            if(i>4 && !ControlePrincipal.tipo_u.equals("op_dir")){
                jTableCarvao.getColumnModel().getColumn(i).setMinWidth(0);     
                jTableCarvao.getColumnModel().getColumn(i).setPreferredWidth(0);  
                jTableCarvao.getColumnModel().getColumn(i).setMaxWidth(0);
                jTableCarvao.getColumnModel().getColumn(i).setResizable(false);
            }
            //System.out.println("Indice: "+i+" - "+ colunas[i].length()*200);
        }
        jTableCarvao.getTableHeader().setReorderingAllowed(false);
        jTableCarvao.setAutoResizeMode(jTableCarvao.AUTO_RESIZE_OFF);
        jTableCarvao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //duplo click
        jTableCarvao.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                    if(e.getClickCount() == 2){
                        //System.out.println("duplo-clique detectado");
                        AlterarInfo();
                    }
                }
            }); 
        con.fecharConexao();
    }    
    
    private void AlterarInfo(){
        if(jTableCarvao.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableCarvao.getSelectedRow();
            String id_controle_carvao = jTableCarvao.getValueAt(linha, 10).toString();
            String id_estoque = jTableCarvao.getValueAt(linha, 7).toString();
            //String talhao = jTableCarvao.getValueAt(linha, 0).toString();
            //String forno = jTableCarvao.getValueAt(linha, 1).toString();
            String volume_madeira = jTableCarvao.getValueAt(linha, 3).toString();
            //String data_entrada_madeira_forno = jTableCarvao.getValueAt(linha, 6).toString();
            String id_operario = jTableCarvao.getValueAt(linha, 8).toString();
            String volume_carvao = jTableCarvao.getValueAt(linha, 4).toString();
            String data_saida_carvao_forno = jTableCarvao.getValueAt(linha, 6).toString();
            //String rend_grav_forno = jTableCarvao.getValueAt(linha, 10).toString();
                        
            /*for(int i=0; i<10;i++){                
                System.out.println("coluna["+i+"]: "+jTableCarvao.getValueAt(linha, i).toString());
            }*/
            if(volume_carvao.equals("0")){
                try {
                    new Alterar_RetirarCarvaoForno(id_controle_carvao, id_estoque, volume_madeira).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
                }          
                this.setVisible(false);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Processo de carbonização finalizado!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        }
    }

    private void InserirInfo(){
        new InserirMadeiraForno().setVisible(true);
        this.setVisible(false);
        dispose();
    }
    
    private void ExcluirInfo(){
        if(jTableCarvao.getSelectedRow()>=0) {
            int linha = jTableCarvao.getSelectedRow();
            String id_carvao = jTableCarvao.getValueAt(linha, 0).toString();
            new ExcluirInfoMadeiraCarvaoForno(id_carvao).setVisible(true);            
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
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
        jButtonRetirarCarvaoForno = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonBuscarEstoque = new javax.swing.JButton();
        jButtonRelatorio = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCarvao = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 600));

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Gerenciar Carvão");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(94, 94, 94))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                        .addGap(147, 147, 147))))
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

        jButtonRetirarCarvaoForno.setFont(jButtonRetirarCarvaoForno.getFont().deriveFont(jButtonRetirarCarvaoForno.getFont().getSize()+1f));
        jButtonRetirarCarvaoForno.setText("<html>Retirar<br>Carvao<br>Forno</html>");
        jButtonRetirarCarvaoForno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRetirarCarvaoFornoActionPerformed(evt);
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

        jButtonRelatorio.setFont(jButtonRelatorio.getFont().deriveFont(jButtonRelatorio.getFont().getSize()+1f));
        jButtonRelatorio.setText("Relatorio");
        jButtonRelatorio.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonRetirarCarvaoForno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonBuscarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jButtonRelatorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonBuscarEstoque, jButtonExcluir, jButtonRetirarCarvaoForno});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonBuscarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonRetirarCarvaoForno, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonBuscarEstoque, jButtonExcluir, jButtonRetirarCarvaoForno});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jScrollPane2.setViewportView(jTableCarvao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRetirarCarvaoFornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRetirarCarvaoFornoActionPerformed
       AlterarInfo();
    }//GEN-LAST:event_jButtonRetirarCarvaoFornoActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        ExcluirInfo();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonBuscarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarEstoqueActionPerformed
        try {
            //new BuscarRelatorioMadeiraEstoquePrincipal().setVisible(true);
            new GerarRelatorioEstoquePrincipal().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonBuscarEstoqueActionPerformed

    private void jButtonRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelatorioActionPerformed
        try {
            new GerarRelatorioEstoqueBasico().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonRelatorioActionPerformed

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
            java.util.logging.Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerenciarCarvaoForno().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarEstoque;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRelatorio;
    private javax.swing.JButton jButtonRetirarCarvaoForno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCarvao;
    // End of variables declaration//GEN-END:variables
}
