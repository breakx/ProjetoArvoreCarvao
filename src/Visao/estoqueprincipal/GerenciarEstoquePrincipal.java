/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.estoqueprincipal;

import Controle.ControlePrincipal;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.login.Login;
import Visao.relatorios.GerarRelatorioEstoqueBasico;
import Visao.relatorios.GerarRelatorioEstoquePrincipal;
import Visao.usuario.GerenciarUsuarios;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Cristiano GD
 */
public class GerenciarEstoquePrincipal extends javax.swing.JFrame {

    /**
     * Creates new form Estoque
     * @throws java.sql.SQLException
     */
    public GerenciarEstoquePrincipal() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);    
        jButtonExcluir.setVisible(false);
        CarregarNome();
        _carregarProjetos();
    }    
    
    private void _carregarProjetos(){
        jComboBoxProjeto.addItem("I");
        jComboBoxProjeto.addItem("II");
        jComboBoxProjeto.addItem("III");  
        jComboBoxProjeto.addItem("IV"); 
        jComboBoxProjeto.addItem("V"); 
        jComboBoxProjeto.addItem("VI"); 
        jComboBoxProjeto.addItem("VII"); 
        jComboBoxProjeto.addItem("VIII"); 
        jComboBoxProjeto.addItem("IX"); 
        jComboBoxProjeto.addItem("X");  
        _carregarFazendas();
    }
    
    private void _carregarFazendas(){ 
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql;
        query = "SELECT fazenda FROM estoque_principal";
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        jComboBoxFazenda.addItem("-");
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<jComboBoxFazenda.getItemCount(); j++) {
                    if (jComboBoxFazenda.getItemAt(j).equals(rs.getString("fazenda"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("fazenda"));
                    jComboBoxFazenda.addItem(rs.getString("fazenda"));
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao gerar lista de fazendas! "+ex);
        }        
        PreencherTabela();
    }
    
    /**
     * 
     */
    private void PreencherTabela(){
        ArrayList linhas = new ArrayList();
        String[] colunas = new String[] { 
            "Id",
            "Estado",
            "Bloco",
            "Municipio",
            "Fazenda",
            "Projeto",
            "UPC",
            "Talhao",
            "Area",
            "Mat.Gen.",
            "M3/ha",
            "Talhadia",
            "Ano_Rt",
            "Data_Plant",
            "Data_Rt_1",
            "Data_Rt_2",
            "Data_Rt_3",
            "Idade_C1",
            "Idade_C2",
            "Idade_C3",
            "Idade_Hj",
            "Conducao",
            "Categoria",
            "Situacao",
            "IMA",
            "MdC/ha",
            "Dens_Md",
            "Dens_Cv",
            "Md_Tn_ha",
            "Cv_Tn_ha",
            "Id_Op",
            "Data_Reg",
            "Vol_Md_Est",
            "Vol_Md_Transp",
            "Vol_Md_Bal",
            "MdC_Est",
            "MdC_Prod",
            "MdC_Bal",
            "Md_Tn_Est",
            "Md_Tn_Transp",
            "Md_Tn_Bal",
            "Cv_Tn_Est",
            "Cv_Tn_Prod",
            "Cv_Tn_Bal",
            "Md_Praca",
            "Cv_Praca",
            "Md_Forno",
            "MdC_Transp",
            "Cv_Tn_Transp",
            "RG_Est",
            "RG_Real",
            "Fator_Emp"
        };
        String query;
        String whereSql;
        
        //Controle e definição das variaveis da clausula where like. Filtros
        String filtro_upc;
        String filtro_talhao;
        String filtro_proj;
        String filtro_faz; 
        
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
        
        if(jComboBoxProjeto.getSelectedItem().equals("-")){
            filtro_proj="";
        }else{
            filtro_proj = jComboBoxProjeto.getSelectedItem().toString();
        }
        
        if(jComboBoxFazenda.getSelectedItem().equals("-")){
            filtro_faz="";
        }else{
            filtro_faz=jComboBoxFazenda.getSelectedItem().toString();
        }
                 
        whereSql = "where ";
        //faz busca a partir dos filtros acima               
        if(!filtro_upc.equals("")){
            whereSql += "upc = '"+filtro_upc+"'";
        }
        //System.out.println("whereSql: " + whereSql.length());
        
        if(!filtro_proj.equals("")){
            if(whereSql.length()>=15){
                whereSql += " and projeto = '"+filtro_proj+"'";
            }else{
                whereSql += "projeto = '"+filtro_proj+"'";
            }
        }
        
        if(!filtro_faz.equals("")){
            if(whereSql.length()>=15){
                whereSql += " and fazenda = '"+filtro_faz+"'";
            }else{
                whereSql += "fazenda = '"+filtro_faz+"'";
            }
        }
        
        if(!filtro_talhao.equals("")){
            if(whereSql.length()>=15){
                whereSql += " and talhao = '"+filtro_talhao+"'";
            }else{
                whereSql += "talhao = '"+filtro_talhao+"'";
            }
        }
        
        if(whereSql.length()<7){
            whereSql = "";
        }
        //System.out.println("whereSql: " + whereSql.length());
        
        query = "Select * from estoque_principal "+whereSql;
        //System.out.printf("query: "+ query);
        int tamanho = 0;       
        ConexaoBD con = ConexaoBD.getConexao(0);        
        ResultSet rs = con.consultaSql(query);
        
        try {
            while(rs.next()){
                linhas.add(new Object[]{
                    rs.getString("id_estoque_p"),
                    rs.getString("estado"),
                    rs.getString("bloco"),
                    rs.getString("municipio"),
                    rs.getString("fazenda"),
                    rs.getString("projeto"),
                    rs.getString("upc"),
                    rs.getString("talhao"),
                    rs.getString("area"),
                    rs.getString("material_genetico"),
                    rs.getString("m3_ha"),
                    rs.getString("talhadia"),
                    rs.getString("ano_rotacao"),
                    rs.getString("data_plantio"),
                    rs.getString("data_rotacao_1"),
                    rs.getString("data_rotacao_2"),
                    rs.getString("data_rotacao_3"),
                    rs.getString("idade_corte1"),
                    rs.getString("idade_corte2"),
                    rs.getString("idade_corte3"),
                    rs.getString("idade_hoje"),
                    rs.getString("conducao"),
                    rs.getString("categoria"),
                    rs.getString("situacao"),
                    rs.getString("ima"),
                    rs.getString("mdc_ha"),
                    rs.getString("densidade_madeira"),
                    rs.getString("densidade_carvao"),
                    rs.getString("mad_ton_ha"),
                    rs.getString("carv_ton_ha"),
                    rs.getString("id_operario"),
                    rs.getString("data_estoque"),
                    rs.getString("vol_mad_estimado"),
                    rs.getString("vol_mad_transp"),
                    rs.getString("vol_mad_balanco"),
                    rs.getString("mdc_estimado"),                    
                    rs.getString("mdc_prod"),
                    rs.getString("mdc_balanco"),
                    rs.getString("mad_ton_estimado"),
                    rs.getString("mad_ton_transp"),
                    rs.getString("mad_ton_balanco"),
                    rs.getString("carv_ton_estimado"),
                    rs.getString("carv_ton_prod"),
                    rs.getString("carv_ton_balanco"),
                    rs.getString("madeira_praca"),
                    rs.getString("carvao_praca"),
                    rs.getString("madeira_forno"),
                    rs.getString("mdc_transp"),
                    rs.getString("carv_ton_transp"),
                    rs.getString("rend_grav_estimado"),
                    rs.getString("rend_grav_real"),
                    rs.getString("fator_empilalhemto")
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        GerarTabela modelo = new GerarTabela(linhas, colunas);
        jTableEstoquePrincipal.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }
            jTableEstoquePrincipal.getColumnModel().getColumn(i).setResizable(false);
            //System.out.println("Indice: "+i+" - "+ colunas[i]);
        }
        jTableEstoquePrincipal.getColumnModel().getColumn(0).setMinWidth(0);     
        jTableEstoquePrincipal.getColumnModel().getColumn(0).setPreferredWidth(0);  
        jTableEstoquePrincipal.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableEstoquePrincipal.getColumnModel().getColumn(0).setResizable(false);
        jTableEstoquePrincipal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableEstoquePrincipal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //duplo click
        jTableEstoquePrincipal.addMouseListener(new MouseAdapter(){
            @Override
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
        if(jTableEstoquePrincipal.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableEstoquePrincipal.getSelectedRow();
            //JOptionPane.showMessageDialog(null, "tamnaho: "+jTableEstoquePrincipal.getColumnCount());
            String cols[] = new String[jTableEstoquePrincipal.getColumnCount()];
            for(int i=0; i<jTableEstoquePrincipal.getColumnCount(); i++){
                cols[i]=jTableEstoquePrincipal.getValueAt(linha, i).toString();
            }
            /*String id_estoque = jTableEstoquePrincipal.getValueAt(linha, 0).toString();//<
            String estado = jTableEstoquePrincipal.getValueAt(linha, 1).toString();
            String bloco = jTableEstoquePrincipal.getValueAt(linha, 2).toString();
            String municipio = jTableEstoquePrincipal.getValueAt(linha, 3).toString();
            String fazenda = jTableEstoquePrincipal.getValueAt(linha, 4).toString();
            String projeto = jTableEstoquePrincipal.getValueAt(linha, 5).toString();
            String upc = jTableEstoquePrincipal.getValueAt(linha, 6).toString();//<
            String talhao = jTableEstoquePrincipal.getValueAt(linha, 7).toString();
            String area = jTableEstoquePrincipal.getValueAt(linha, 8).toString();//<
            String material_genetico = jTableEstoquePrincipal.getValueAt(linha, 9).toString();//<
            String m3_ha = jTableEstoquePrincipal.getValueAt(linha, 10).toString();//<
            //String talhadia = jTableEstoquePrincipal.getValueAt(linha, 11).toString();
            String ano_rotacao = jTableEstoquePrincipal.getValueAt(linha, 12).toString();//<
            String data_plantio = jTableEstoquePrincipal.getValueAt(linha, 13).toString();//<
            String data_rotacao_1 = jTableEstoquePrincipal.getValueAt(linha, 14).toString();//<
            String data_rotacao_2 = jTableEstoquePrincipal.getValueAt(linha, 15).toString();//<
            String data_rotacao_3 = jTableEstoquePrincipal.getValueAt(linha, 16).toString();//<
            String idade_c1 = jTableEstoquePrincipal.getValueAt(linha, 17).toString();
            String idade_c2 = jTableEstoquePrincipal.getValueAt(linha, 18).toString();
            String idade_c3 = jTableEstoquePrincipal.getValueAt(linha, 19).toString();
            String idade_hj = jTableEstoquePrincipal.getValueAt(linha, 20).toString();
            String conducao = jTableEstoquePrincipal.getValueAt(linha, 21).toString();
            String categoria = jTableEstoquePrincipal.getValueAt(linha, 22).toString();//<
            //String situacao = jTableEstoquePrincipal.getValueAt(linha, 23).toString();
            String ima = jTableEstoquePrincipal.getValueAt(linha, 24).toString();//<
            //String mdc_ha = jTableEstoquePrincipal.getValueAt(linha, 25).toString();
            String densidade_madeira = jTableEstoquePrincipal.getValueAt(linha, 26).toString();//<
            String densidade_carvao = jTableEstoquePrincipal.getValueAt(linha, 27).toString();//<
            String mad_ton_ha = jTableEstoquePrincipal.getValueAt(linha, 28).toString();
            String carv_ton_ha = jTableEstoquePrincipal.getValueAt(linha, 29).toString();
            String id_operario = jTableEstoquePrincipal.getValueAt(linha, 30).toString();
            String data_estoque	= jTableEstoquePrincipal.getValueAt(linha, 31).toString();
            String vol_mad_estimado = jTableEstoquePrincipal.getValueAt(linha, 32).toString();
            String vol_mad_transp = jTableEstoquePrincipal.getValueAt(linha, 33).toString();
            String vol_mad_balanco = jTableEstoquePrincipal.getValueAt(linha, 34).toString();
            String mdc_estimado = jTableEstoquePrincipal.getValueAt(linha, 35).toString();
            String mdc_prod = jTableEstoquePrincipal.getValueAt(linha, 36).toString();
            String mdc_balanco = jTableEstoquePrincipal.getValueAt(linha, 37).toString();
            String mad_ton_estimado = jTableEstoquePrincipal.getValueAt(linha, 38).toString();
            String mad_ton_transp = jTableEstoquePrincipal.getValueAt(linha, 39).toString();
            String mad_ton_balanco = jTableEstoquePrincipal.getValueAt(linha, 40).toString();
            String carv_ton_estimado = jTableEstoquePrincipal.getValueAt(linha, 41).toString();
            String carv_ton_transp = jTableEstoquePrincipal.getValueAt(linha, 42).toString();
            String carv_ton_balanco = jTableEstoquePrincipal.getValueAt(linha, 43).toString();
            String madeira_praca = jTableEstoquePrincipal.getValueAt(linha, 44).toString();
            String madeira_forno = jTableEstoquePrincipal.getValueAt(linha, 45).toString();
            String rend_grav_estimado = jTableEstoquePrincipal.getValueAt(linha, 46).toString();//<
            //String rend_grav_real = jTableEstoquePrincipal.getValueAt(linha, 47).toString();
            String fator_empilalhemto = jTableEstoquePrincipal.getValueAt(linha, 48).toString();//<*/
            
            //String data_estoque = jTableEstoque.getValueAt(linha, 28).toString();
            //String id_operario = jTableEstoque.getValueAt(linha, 29).toString();
 
            new AlterarEstoquePrincipal(cols).setVisible(true);
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void ExcluirInfo(){
        if(jTableEstoquePrincipal.getSelectedRow()>=0) {
            int linha = jTableEstoquePrincipal.getSelectedRow();
            String id_estoque = jTableEstoquePrincipal.getValueAt(linha, 0).toString();
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void InserirInfo(){
        new InserirEstoquePrincipal().setVisible(true);
        this.setVisible(false);
        dispose();
    } 
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    } 
    
    private void VoltarMenu(){        
        try {
            new GerenciarUsuarios().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanelMain = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonAtualizar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonRelatorio = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxFazenda = new javax.swing.JComboBox();
        jComboBoxProjeto = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerUPC = new javax.swing.JSpinner();
        jSpinnerTalhao = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jButtonFiltrar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEstoquePrincipal = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Gerenciar Estoque Principal");
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
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
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

        jButtonAtualizar.setFont(jButtonAtualizar.getFont().deriveFont(jButtonAtualizar.getFont().getSize()+1f));
        jButtonAtualizar.setText("Atualizar");
        jButtonAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtualizarActionPerformed(evt);
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

        jButtonRelatorio.setFont(jButtonRelatorio.getFont().deriveFont(jButtonRelatorio.getFont().getSize()+1f));
        jButtonRelatorio.setText("Relatorio");
        jButtonRelatorio.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelatorioActionPerformed(evt);
            }
        });

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()+1f));
        jLabel7.setText("Fazenda");
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 25));

        jComboBoxFazenda.setFont(jComboBoxFazenda.getFont().deriveFont(jComboBoxFazenda.getFont().getSize()+1f));

        jComboBoxProjeto.setFont(jComboBoxProjeto.getFont().deriveFont(jComboBoxProjeto.getFont().getSize()+1f));
        jComboBoxProjeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getSize()+1f));
        jLabel6.setText("Projeto");
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 25));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
        jLabel2.setText("UPC");
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 25));

        jSpinnerUPC.setFont(jSpinnerUPC.getFont().deriveFont(jSpinnerUPC.getFont().getSize()+1f));
        jSpinnerUPC.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9, 1));
        jSpinnerUPC.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerTalhao.setFont(jSpinnerTalhao.getFont().deriveFont(jSpinnerTalhao.getFont().getSize()+1f));
        jSpinnerTalhao.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));
        jSpinnerTalhao.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getSize()+1f));
        jLabel9.setText("Talhao");
        jLabel9.setPreferredSize(new java.awt.Dimension(80, 25));

        jButtonFiltrar.setFont(jButtonFiltrar.getFont().deriveFont(jButtonFiltrar.getFont().getSize()+1f));
        jButtonFiltrar.setText("Filtrar");
        jButtonFiltrar.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerUPC, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jComboBoxProjeto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxFazenda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonAtualizar, jButtonExcluir});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
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
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonAtualizar, jButtonExcluir});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jTableEstoquePrincipal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTableEstoquePrincipal);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                .addContainerGap())
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
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarActionPerformed
        AlterarInfo();
    }//GEN-LAST:event_jButtonAtualizarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        ExcluirInfo();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);        
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelatorioActionPerformed
        try {
            new GerarRelatorioEstoqueBasico().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonRelatorioActionPerformed

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        PreencherTabela();
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

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
            java.util.logging.Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new GerenciarEstoquePrincipal().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtualizar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRelatorio;
    private javax.swing.JComboBox jComboBoxFazenda;
    private javax.swing.JComboBox jComboBoxProjeto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerTalhao;
    private javax.swing.JSpinner jSpinnerUPC;
    private javax.swing.JTable jTableEstoquePrincipal;
    // End of variables declaration//GEN-END:variables
}
