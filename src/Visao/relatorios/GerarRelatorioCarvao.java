/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.relatorios;

import Controle.ControlePrincipal;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.carvao.InserirMadeiraForno;
import Visao.login.Login;
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
public class GerarRelatorioCarvao extends javax.swing.JFrame {
    
    /**
     * Creates new form GerarRelatorioCarvao
     * @throws java.sql.SQLException
     */
    public GerarRelatorioCarvao() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        jButtonLogout.setVisible(false);
        ChangeName();
        _carregarUsuarios();
    }   
    
    private void _carregarUsuarios(){ 
        jComboBoxUsuario.removeAllItems();
        jComboBoxUsuario.addItem("-");
        ConexaoBD con = ConexaoBD.getConexao();
        String query;
        ResultSet rs;
        query = "SELECT id_operario FROM controle_carvao";
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<jComboBoxUsuario.getItemCount(); j++) {
                    if (jComboBoxUsuario.getItemAt(j).equals(rs.getString("id_operario"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("id_operario"));
                    jComboBoxUsuario.addItem(rs.getString("id_operario"));
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar usuarios! "+ex);
        }             
        _carregarFornos();
    }
    
    private void _carregarFornos(){ 
        jComboBoxForno.removeAllItems();
        jComboBoxForno.addItem("-");
        ConexaoBD con = ConexaoBD.getConexao();
        String query;
        ResultSet rs;
        query = "SELECT forno FROM controle_carvao";
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<jComboBoxForno.getItemCount(); j++) {
                    if (jComboBoxForno.getItemAt(j).equals(rs.getString("forno"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("id_operario"));
                    jComboBoxForno.addItem(rs.getString("forno"));
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar fornos! "+ex);
        }             
        PreencherTabela();
    }   
    
    /**
     * 
     */
    private void PreencherTabela(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[] { 
            //"id_controle_carvao",
            "id_estoque_p",
            "id_operario",
            "upc_c",
            "talhao",
            "forno",
            "volume_madeira",
            "data_entrada_madeira_forno",
            "volume_carvao",
            "data_saida_carvao_forno",
            "rend_grav_forno"
        };
        int tamanho = 0;    
        String query;
        /*if(ControlePrincipal.tipo_u.equals("op_scv")){
            query = "Select * from controle_carvao";
        }else{
            query = "Select * from controle_carvao where id_operario = '" +ControlePrincipal.id_op+"'";
        }*/
        
        String whereSql;
        
        //Controle e definição das variaveis da clausula where like. Filtros
        String filtro_op_u;
        String filtro_forno;
        String filtro_upc;
        String filtro_talhao;
        String filtro_data_i;
        String filtro_data_f;
        if(jComboBoxUsuario.getSelectedItem().equals("-")){
            filtro_op_u="";
        }else{
            filtro_op_u=jComboBoxUsuario.getSelectedItem().toString();
        }
        
        if(jComboBoxForno.getSelectedItem().equals("-")){
            filtro_forno="";
        }else{
            filtro_forno=jComboBoxForno.getSelectedItem().toString();
        }
        
        if(jSpinnerUPC.getValue().equals(0)){
            filtro_upc=""; 
        }else{
            filtro_upc=jSpinnerUPC.getValue().toString();
        }
        
        if(jSpinnerTalhao.getValue().equals(0)){
            filtro_talhao=""; 
        }else{
            filtro_talhao=jSpinnerTalhao.getValue().toString();
        }
        
        if(jTextFieldDataI.getText().equals("")){
            filtro_data_i=""; 
        }else{
            filtro_data_i=jTextFieldDataI.getText();
        }
        
        if(jTextFieldDataF.getText().equals("")){
            filtro_data_f=""; 
        }else{
            filtro_data_f=jTextFieldDataF.getText();
        }
        
        //faz busca a partir dos filtros acima
        if(!filtro_op_u.equals("") ||!filtro_forno.equals("") ||!filtro_upc.equals("") ||!filtro_talhao.equals("") ||!filtro_data_i.equals("") ||!filtro_data_f.equals("")){
            //whereSql = "where id_operario like '%"+filtro_op_u+"%' and forno like '%"+filtro_forno+"%' and upc_c like '%"+filtro_upc+"%' and talhao like '%"+filtro_talhao+"%' and data_entrada_madeira_forno like '%"+filtro_data_i+"%' and data_entrada_madeira_forno like '%"+filtro_data_f+"%'";
            whereSql = "where id_operario like '%"+filtro_op_u+"%' and forno like '%"+filtro_forno+"%' and upc_c like '%"+filtro_upc+"%' and talhao like '%"+filtro_talhao+"%' and data_entrada_madeira_forno BETWEEN '"+filtro_data_i+"' and '"+filtro_data_f+"'";
        }else{
            whereSql = "";
        }
        
        query = "Select * from controle_carvao "+whereSql;
        ConexaoBD con = ConexaoBD.getConexao();         
        ResultSet rs = con.consultaSql(query);
        System.out.println("query: "+query);
        try {
            while(rs.next()){
                dados.add(new Object[]{
                    //rs.getString("id_controle_carvao"),
                    rs.getString("id_estoque_p"),
                    rs.getString("id_operario"),
                    rs.getString("upc_c"),
                    rs.getString("talhao"),
                    rs.getString("forno"),
                    rs.getString("volume_madeira"),
                    rs.getString("data_entrada_madeira_forno"),
                    rs.getString("volume_carvao"),
                    rs.getString("data_saida_carvao_forno"),
                    rs.getString("rend_grav_forno")
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        GerarTabela modelo = new GerarTabela(dados, colunas);
        jTableRelatorioCarvao.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableRelatorioCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableRelatorioCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableRelatorioCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }
            jTableRelatorioCarvao.getColumnModel().getColumn(i).setResizable(false);
            //System.out.println("Indice: "+i+" - "+ colunas[i].length()*200);
        }
        jTableRelatorioCarvao.getTableHeader().setReorderingAllowed(false);
        jTableRelatorioCarvao.setAutoResizeMode(jTableRelatorioCarvao.AUTO_RESIZE_OFF);
        jTableRelatorioCarvao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        con.fecharConexao();
    }      
    
    private void SelecionarPraca(){
        if(jTableRelatorioCarvao.getSelectedRow()>=0) {
            int linha = jTableRelatorioCarvao.getSelectedRow();
            String id_madeira = jTableRelatorioCarvao.getValueAt(linha, 0).toString();
            ControlePrincipal.talhao = Integer.parseInt(jTableRelatorioCarvao.getValueAt(linha, 8).toString());
            new InserirMadeiraForno().setVisible(true);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        this.setVisible(false);
        dispose();
    }
    
    private void ChangeName(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    }
    
    private void VoltarMenu(){        
        try {
            new GerarRelatorioEstoqueBasico().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioCarvao.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jButtonVoltar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxUsuario = new javax.swing.JComboBox();
        jButtonFiltrar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxForno = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerUPC = new javax.swing.JSpinner();
        jSpinnerTalhao = new javax.swing.JSpinner();
        jTextFieldDataI = new javax.swing.JTextField();
        jTextFieldDataF = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRelatorioCarvao = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Relatorio Estoque Carvão");
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabelIdTipo, jLabelNome});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
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

        jButtonVoltar.setFont(jButtonVoltar.getFont().deriveFont(jButtonVoltar.getFont().getSize()+1f));
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()+1f));
        jLabel7.setText("Usuario");
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 25));

        jComboBoxUsuario.setFont(jComboBoxUsuario.getFont().deriveFont(jComboBoxUsuario.getFont().getSize()+1f));
        jComboBoxUsuario.setPreferredSize(new java.awt.Dimension(150, 25));

        jButtonFiltrar.setFont(jButtonFiltrar.getFont().deriveFont(jButtonFiltrar.getFont().getSize()+1f));
        jButtonFiltrar.setText("Filtrar");
        jButtonFiltrar.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFiltrarActionPerformed(evt);
            }
        });

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getSize()+1f));
        jLabel8.setText("Forno");
        jLabel8.setPreferredSize(new java.awt.Dimension(80, 25));

        jComboBoxForno.setFont(jComboBoxForno.getFont().deriveFont(jComboBoxForno.getFont().getSize()+1f));
        jComboBoxForno.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getSize()+1f));
        jLabel9.setText("Talhao");
        jLabel9.setPreferredSize(new java.awt.Dimension(80, 25));

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getSize()+1f));
        jLabel10.setText("Data");
        jLabel10.setPreferredSize(new java.awt.Dimension(80, 25));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
        jLabel2.setText("UPC");
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 25));

        jSpinnerUPC.setFont(jSpinnerUPC.getFont().deriveFont(jSpinnerUPC.getFont().getSize()+1f));
        jSpinnerUPC.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9, 1));
        jSpinnerUPC.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerTalhao.setFont(jSpinnerTalhao.getFont().deriveFont(jSpinnerTalhao.getFont().getSize()+1f));
        jSpinnerTalhao.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9, 1));
        jSpinnerTalhao.setPreferredSize(new java.awt.Dimension(150, 25));

        jTextFieldDataI.setFont(jTextFieldDataI.getFont().deriveFont(jTextFieldDataI.getFont().getSize()+1f));
        jTextFieldDataI.setPreferredSize(new java.awt.Dimension(150, 25));

        jTextFieldDataF.setFont(jTextFieldDataF.getFont().deriveFont(jTextFieldDataF.getFont().getSize()+1f));
        jTextFieldDataF.setPreferredSize(new java.awt.Dimension(150, 25));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDataF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldDataI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerUPC, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxForno, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerUPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDataI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jTextFieldDataF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jScrollPane2.setViewportView(jTableRelatorioCarvao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE))
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        //_carregarUsuarios();
        PreencherTabela();
        //JOptionPane.showMessageDialog(null, jListFiltrar.getSelectedValuesList());
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(GerarRelatorioCarvao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioCarvao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioCarvao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioCarvao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerarRelatorioCarvao().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioCarvao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JComboBox jComboBoxForno;
    private javax.swing.JComboBox jComboBoxUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerTalhao;
    private javax.swing.JSpinner jSpinnerUPC;
    private javax.swing.JTable jTableRelatorioCarvao;
    private javax.swing.JTextField jTextFieldDataF;
    private javax.swing.JTextField jTextFieldDataI;
    // End of variables declaration//GEN-END:variables
}
