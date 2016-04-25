/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristiano GD
 */

package Visao.login;

import Controle.ControlePrincipal;
import Controle.ControleUsuario;
import Modelo.ConexaoBD;
import Visao.usuario.GerenciarUsuarios;
import Visao.carvao.GerenciarCarvaoForno;
import Visao.madeira.GerenciarMadeiraPraca;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristiano
 */
public class Login extends javax.swing.JFrame {

    //variables
    ControleUsuario usuario = new ControleUsuario();
    
    /** Creates new form Login */
    public Login() {
        initComponents();          
    }
    
    public void SystemIn(){
        try
        {
            //DefaultTableModel dtm = (DefaultTableModel) jTableUsuario.getModel();
            String query = "Select * from usuario where login_usuario = '"+jTextFieldUsuario.getText()+"'";
            ConexaoBD con = ConexaoBD.getConexao();

            ResultSet rs = con.consultaSql(query);
            rs.first();

            if(jTextFieldUsuario.getText().equals(rs.getString("login_usuario")) && jTextFieldSenha.getText().equals(rs.getString("senha_usuario"))){ 
                    String id_u = rs.getString("id_usuario");
                    String login = rs.getString("login_usuario");
                    String tipo = ControlePrincipal.tipo_u = rs.getString("tipo_usuario");
                    ControlePrincipal.nome = rs.getString("nome_usuario");
                    ControlePrincipal.upc_u = rs.getString("upc_usuario"); 
                    ControlePrincipal.id_op = tipo+"."+id_u+".upc-"+rs.getString("upc_usuario");     
                    
                    //JOptionPane.showMessageDialog(null, "Logado!"+login+tipo+usuario.getNome_usuario()+usuario.getId_tipo());  
                    CarregaTela(tipo);                    
                }else{
                    JOptionPane.showMessageDialog(null, "Erro senha invalida!");
                }
            con.fecharConexao();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Usuario Invalido! "+ex);                    
        }
    }
    
    public void CarregaTela(String tipo){
        if(tipo.equals("op_c")){
            try
            {
                GerenciarCarvaoForno gcf = new GerenciarCarvaoForno();
                gcf.setVisible(true);
                //gcf.ChangeName(nome, id_op);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(tipo.equals("op_m")){
            try
            {
                GerenciarMadeiraPraca gmtp = new GerenciarMadeiraPraca();
                gmtp.setVisible(true);
                //gmtp.ChangeName(nome, id_op);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(tipo.equals("op_s")){
            try
            {
                GerenciarUsuarios gu = new GerenciarUsuarios();
                gu.setVisible(true);
                //gu.ChangeName(nome, id_op);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setVisible(false);
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jButtonLogar = new javax.swing.JButton();
        jTextFieldSenha = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(250, 250));
        setPreferredSize(new java.awt.Dimension(300, 300));
        setSize(new java.awt.Dimension(300, 300));

        jPanel1.setMinimumSize(new java.awt.Dimension(225, 225));
        jPanel1.setPreferredSize(new java.awt.Dimension(225, 225));

        jLabel2.setText("Usuario");

        jLabel3.setText("Senha");

        jButtonLogar.setText("Logar");
        jButtonLogar.setPreferredSize(new java.awt.Dimension(100, 50));
        jButtonLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogarActionPerformed(evt);
            }
        });

        jLabel1.setFont(jLabel1.getFont().deriveFont((jLabel1.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, jLabel1.getFont().getSize()+9));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 65, Short.MAX_VALUE)
                .addComponent(jButtonLogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldUsuario)
                            .addComponent(jTextFieldSenha))))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jButtonLogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogarActionPerformed
        // TODO add your handling code here:        
        SystemIn();
    }//GEN-LAST:event_jButtonLogarActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {               
                new Login().setVisible(true); 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jTextFieldSenha;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables

}
