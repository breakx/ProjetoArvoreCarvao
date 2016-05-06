/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.estoqueprincipal;

import Controle.ControleEstoquePrincipal;
import Controle.ControlePrincipal;
import Controle.estoqueprincipal.InserirEstoquePrincipalCtrl;
import Modelo.ConexaoBD;
import Visao.fazenda.GerenciarFazenda;
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
public class InserirEstoquePrincipal extends javax.swing.JFrame {
   
    ControleEstoquePrincipal estoque_principal = new ControleEstoquePrincipal();
    private int talhao = 1;
    /**
     * Creates new form InserirEstoque
     */
    public InserirEstoquePrincipal() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);    
         CarregarNome();
         BuscarTalhao();
    }    
    
    private void BuscarTalhao(){
    //
        String query = "SELECT `talhao` FROM `estoque_principal` WHERE `fazenda` = '"+ControlePrincipal.fazenda+"' and `projeto` = '"+ControlePrincipal.projeto+"'";
        ConexaoBD con = ConexaoBD.getConexao(); 
        //System.out.println("importando "+query);  
        ResultSet rs = con.consultaSql(query);

        if(rs != null){
            try {
                while(rs.next()){
                    talhao ++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(InserirEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        jLabelTalhao.setText("Talhão: "+talhao);
        jLabelFazenda.setText("Fazenda "+ControlePrincipal.fazenda);
        jLabelProjeto.setText("Projeto "+ControlePrincipal.projeto);
        if(ControlePrincipal.tipo_u.equals("op_scv")){
            jSpinnerUpc.setValue(ControlePrincipal.upc_u);
            jSpinnerUpc.setEnabled(false);
        }else{
            jSpinnerUpc.setEnabled(true);
        }
        
        jTextFieldMaterialGenetico.setText(ControlePrincipal.material_genetico);
        //JOptionPane.showMessageDialog(null, "Talhao: "+talhao);  
        con.fecharConexao();
    }
    
    private void GerarDensidadeMadeira(String codigo){
        JOptionPane.showMessageDialog(null, "Material Genetico: "+codigo); 
        if(codigo.equals("1270")){
            estoque_principal.setDensidade_madeira(0.486f);
        }else if(codigo.equals("2486")){
            estoque_principal.setDensidade_madeira(0.483f);
        }else if(codigo.equals("3281")){
            estoque_principal.setDensidade_madeira(0.446f);
        }else if(codigo.equals("3334")){
            estoque_principal.setDensidade_madeira(0.504f);
        }else if(codigo.equals("3335")){
            estoque_principal.setDensidade_madeira(0.477f);
        }else if(codigo.equals("3336")){
            estoque_principal.setDensidade_madeira(0.459f);
        }else if(codigo.equals("3486")){
            estoque_principal.setDensidade_madeira(0.463f);
        /*}else if(codigo.equals("3487")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("-")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("6")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("8")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("1288")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("1528")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("16")){
            estoque_principal.setDensidade_madeira(0.476f);*/
        }else if(codigo.equals("3016")){
            estoque_principal.setDensidade_madeira(0.455f);
        }else if(codigo.equals("3025")){
            estoque_principal.setDensidade_madeira(0.499f);
        }else if(codigo.equals("3203")){
            estoque_principal.setDensidade_madeira(0.464f);
        //}else if(codigo.equals("3676")){
            //estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("6382")){
            estoque_principal.setDensidade_madeira(0.52f);
        /*}else if(codigo.equals("Citriodora")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("Cloeziana")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("nd")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("semente")){
            estoque_principal.setDensidade_madeira(0.476f);
        }else if(codigo.equals("Urophylla")){
            estoque_principal.setDensidade_madeira(0.476f);*/
        }else if(codigo.equals("VM-01")){
            estoque_principal.setDensidade_madeira(0.53f); 
        }else{
            estoque_principal.setDensidade_madeira(0.476f);
        }
        GerarDensidadeCarvao(jTextFieldMaterialGenetico.getText());
    }
    
    private void GerarDensidadeCarvao(String codigo){
        if(codigo.equals("3334")){
            estoque_principal.setDensidade_carvao(0.24f);
        }else if(codigo.equals("3281") || codigo.equals("3335")){
            estoque_principal.setDensidade_carvao(0.232f);
        }else if(codigo.equals("3336")){
            estoque_principal.setDensidade_carvao(0.224f);
        }else if(codigo.equals("3487")){
            estoque_principal.setDensidade_carvao(0.237f);
        }else{
            estoque_principal.setDensidade_carvao(0.233f);
        }
        RegistrarEstoque(); 
    }
    
    private void RegistrarEstoque(){
        DateFormat data_estoque_principal = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        Date date = new Date();        
        
        estoque_principal.setUpc((int)jSpinnerUpc.getValue());
        estoque_principal.setTalhao(talhao);
        estoque_principal.setMaterial_genetico(jTextFieldMaterialGenetico.getText()); 
        estoque_principal.setArea(Float.parseFloat(jSpinnerArea.getValue().toString())); 
        
        estoque_principal.setData_estoque(data_estoque_principal.format(date));
        estoque_principal.setId_operario(ControlePrincipal.id_op);
        
        ControlePrincipal.upc = (Integer)jSpinnerUpc.getValue() ;
        ControlePrincipal.material_genetico = jTextFieldMaterialGenetico.getText();
        
        InserirEstoquePrincipalCtrl inserir = new InserirEstoquePrincipalCtrl(estoque_principal);      
        
        new InserirEstoquePrincipal().setVisible(true);
        this.setVisible(false);
        dispose();
    }
    
    private void GerenciarEstoque(){
        try {
            new GerenciarEstoquePrincipal().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarFazenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();   
    }
    
    private void VoltarMenu(){
        ControlePrincipal.upc = 0;
        ControlePrincipal.material_genetico = "";
        try {
            new GerenciarFazenda().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(InserirEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }        
        this.setVisible(false);
        dispose();
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

        jPanelMain = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jButtonGerenciarEstoque = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelTalhao = new javax.swing.JLabel();
        jButtonCargaTalhao = new javax.swing.JButton();
        jButtonVoltar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabelFazenda = new javax.swing.JLabel();
        jLabelProjeto = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSpinnerUpc = new javax.swing.JSpinner();
        jSpinnerArea = new javax.swing.JSpinner();
        jTextFieldMaterialGenetico = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Cadastrar Estoque");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 60));

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
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelIdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabelIdTipo, jLabelNome});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelIdTipo)
                .addGap(10, 10, 10))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabelIdTipo, jLabelNome});

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 350));

        jButtonLogout.setFont(jButtonLogout.getFont().deriveFont(jButtonLogout.getFont().getSize()+13f));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        jButtonGerenciarEstoque.setFont(jButtonGerenciarEstoque.getFont().deriveFont(jButtonGerenciarEstoque.getFont().getSize()+1f));
        jButtonGerenciarEstoque.setText("<html>Gerenciar<br>Estoque</html>");
        jButtonGerenciarEstoque.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonGerenciarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerenciarEstoqueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonGerenciarEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonGerenciarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
        jLabel2.setText("Upc");
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 16));

        jLabelTalhao.setFont(jLabelTalhao.getFont().deriveFont(jLabelTalhao.getFont().getSize()+1f));
        jLabelTalhao.setText("Talhao : 1");
        jLabelTalhao.setPreferredSize(new java.awt.Dimension(100, 16));

        jButtonCargaTalhao.setFont(jButtonCargaTalhao.getFont().deriveFont(jButtonCargaTalhao.getFont().getSize()+1f));
        jButtonCargaTalhao.setText("<html>Inserir<br>Talhão</html>");
        jButtonCargaTalhao.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonCargaTalhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaTalhaoActionPerformed(evt);
            }
        });

        jButtonVoltar.setFont(jButtonVoltar.getFont().deriveFont(jButtonVoltar.getFont().getSize()+1f));
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+1f));
        jLabel3.setText("Material Genetico");
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 16));

        jLabelFazenda.setFont(jLabelFazenda.getFont().deriveFont(jLabelFazenda.getFont().getSize()+4f));
        jLabelFazenda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelFazenda.setText("Fazenda --- ");

        jLabelProjeto.setFont(jLabelProjeto.getFont().deriveFont(jLabelProjeto.getFont().getSize()+4f));
        jLabelProjeto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelProjeto.setText("Projeto ---");

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+1f));
        jLabel4.setText("Area");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 16));

        jSpinnerUpc.setFont(jSpinnerUpc.getFont().deriveFont(jSpinnerUpc.getFont().getSize()+1f));
        jSpinnerUpc.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));
        jSpinnerUpc.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerArea.setFont(jSpinnerArea.getFont().deriveFont(jSpinnerArea.getFont().getSize()+1f));
        jSpinnerArea.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), null, Float.valueOf(0.1f)));
        jSpinnerArea.setPreferredSize(new java.awt.Dimension(200, 25));

        jTextFieldMaterialGenetico.setFont(jTextFieldMaterialGenetico.getFont().deriveFont(jTextFieldMaterialGenetico.getFont().getSize()+1f));
        jTextFieldMaterialGenetico.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabelTalhao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(jSpinnerArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSpinnerUpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 390, Short.MAX_VALUE))
                    .addComponent(jLabelProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelFazenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerUpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(403, 403, 403))
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonCargaTalhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaTalhaoActionPerformed
        GerarDensidadeMadeira(jTextFieldMaterialGenetico.getText());
    }//GEN-LAST:event_jButtonCargaTalhaoActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonGerenciarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerenciarEstoqueActionPerformed
        GerenciarEstoque();
    }//GEN-LAST:event_jButtonGerenciarEstoqueActionPerformed

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
            java.util.logging.Logger.getLogger(InserirEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InserirEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InserirEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InserirEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InserirEstoquePrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCargaTalhao;
    private javax.swing.JButton jButtonGerenciarEstoque;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelFazenda;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelProjeto;
    private javax.swing.JLabel jLabelTalhao;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JSpinner jSpinnerArea;
    private javax.swing.JSpinner jSpinnerUpc;
    private javax.swing.JTextField jTextFieldMaterialGenetico;
    // End of variables declaration//GEN-END:variables
}
