/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.relatorios;

import Controle.ControlePrincipal;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.carvao.GerenciarCarvaoForno;
import Visao.carvao.InserirMadeiraForno;
import Visao.login.Login;
import Visao.madeira.GerenciarMadeiraPraca;
import Visao.madeira.InserirMadeiraPraca;
import Visao.usuario.GerenciarUsuarios;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
//import api iText import com.lowagie.text.*; 
/**
 *
 * @author Cristiano GD
 */
public class GerarRelatorioEstoqueBasico extends javax.swing.JFrame {
    
    private ArrayList dados;
    private String[] colunas;
    int tamanho;
    private String upc="";
    private String categoria="";
    private String mat_gen="";
    
    /**
     * Creates new form GerarRelatorioEstoquePrincipal
     * @throws java.sql.SQLException
     */
    public GerarRelatorioEstoqueBasico() throws SQLException {
        initComponents();
        ChangeName();
        PreencherTabelaCompleta();
    }   
    
    private void ChangeName(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
        _carregarCategoria();
    }
    
    private void _carregarCategoria(){
        jComboBoxCategoria.addItem("Colheita");
        jComboBoxCategoria.addItem("Silvicultura");
        jComboBoxCategoria.addItem("UTM");  
        _carregarMaterialGenetico();
    }
    
    private void _carregarMaterialGenetico(){
        jComboBoxMatGen.addItem("1270");
        jComboBoxMatGen.addItem("2486");
        jComboBoxMatGen.addItem("3281");
        jComboBoxMatGen.addItem("3334");
        jComboBoxMatGen.addItem("3335");
        jComboBoxMatGen.addItem("3336");
        jComboBoxMatGen.addItem("3486");
        jComboBoxMatGen.addItem("3487");
        //jComboBoxMatGen.addItem("-");
        jComboBoxMatGen.addItem("6");
        jComboBoxMatGen.addItem("8");
        jComboBoxMatGen.addItem("1288");
        jComboBoxMatGen.addItem("1528");
        jComboBoxMatGen.addItem("16");
        jComboBoxMatGen.addItem("3016");
        jComboBoxMatGen.addItem("3025");
        jComboBoxMatGen.addItem("3203");
        jComboBoxMatGen.addItem("3676");
        jComboBoxMatGen.addItem("6382");
        jComboBoxMatGen.addItem("Citriodora");
        jComboBoxMatGen.addItem("Cloeziana");
        jComboBoxMatGen.addItem("nd");
        jComboBoxMatGen.addItem("semente");
        jComboBoxMatGen.addItem("Urophylla");
        jComboBoxMatGen.addItem("VM-01");
    }
    
    /**
     * 
     */
    private void PreencherTabelaCompleta(){
        dados = new ArrayList();
        tamanho = 0;
        ConexaoBD con = ConexaoBD.getConexao();
        String query;
        ResultSet rs;
        String WhereSql;
        colunas = new String[] {
            "Id",
            "Estado",
            "Bloco",
            "Municipio",
            "Fazenda",
            "Projeto",
            "UPC",
            "Talhao",
            "Area",
            "M3/ha",
            "Mat.Gen.",
            "Talhadia",
            "Ano_Rt",
            "Data_Plant",
            "Data_Rt_1",
            "Data_Rt_2",
            "Data_Rt_3",
            "Idade",
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
            "Vol_Md_Real",
            "Vol_Md_Bal",
            "MdC_Est",
            "MdC_Real",
            "MdC_Bal",
            "Md_Tn_Est",
            "Md_Tn_Real",
            "Md_Tn_Bal",
            "Cv_Tn_Est",
            "Cv_Tn_Real",
            "Cv_Tn_Bal",
            "Md_Praca",
            "Md_Forno",
            "Md_Tn_Tot",
            "Cv_Tn_Tot",
            "RG_Est",
            "RG_Real",
            "Fator_Emp"
        };

        if(ControlePrincipal.municipio != null && ControlePrincipal.fazenda != null){
            WhereSql = "where municipio like '%"+ControlePrincipal.municipio+"%' and fazenda like '%"+ControlePrincipal.fazenda+"%'";
        }else if(ControlePrincipal.municipio != null){
            WhereSql = "where municipio like '%"+ControlePrincipal.municipio+"%' ORDER BY `id_estoque_p` DESC";
        }else if(ControlePrincipal.fazenda != null){
            WhereSql = "where fazenda like '%"+ControlePrincipal.fazenda+"%' ORDER BY `id_estoque_p` DESC";
        }else {
            WhereSql = "";
        }

        query = "SELECT * FROM estoque_principal "+ WhereSql;
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);

        try {
            while(rs.next()){
                dados.add(new Object[]{
                    rs.getString("id_estoque_p"),
                    rs.getString("estado"),
                    rs.getString("bloco"),
                    rs.getString("municipio"),
                    rs.getString("fazenda"),
                    rs.getString("projeto"),
                    rs.getString("upc"),
                    rs.getString("talhao"),
                    rs.getString("area"),
                    rs.getString("m3_ha"),
                    rs.getString("material_genetico"),
                    rs.getString("talhadia"),
                    rs.getString("ano_rotacao"),
                    rs.getString("data_plantio"),
                    rs.getString("data_rotacao_1"),
                    rs.getString("data_rotacao_2"),
                    rs.getString("data_rotacao_3"),
                    rs.getString("idade"),
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
                    rs.getString("vol_mad_real"),
                    rs.getString("vol_mad_balanco"),
                    rs.getString("mdc_estimado"),
                    rs.getString("mdc_real"),
                    rs.getString("mdc_balanco"),
                    rs.getString("mad_ton_estimado"),
                    rs.getString("mad_ton_real"),
                    rs.getString("mad_ton_balanco"),
                    rs.getString("carv_ton_estimado"),
                    rs.getString("carv_ton_real"),
                    rs.getString("carv_ton_balanco"),
                    rs.getString("madeira_praca"),
                    rs.getString("madeira_forno"),
                    rs.getString("mad_ton_tot"),
                    rs.getString("carv_ton_tot"),
                    rs.getString("rend_grav_estimado"),
                    rs.getString("rend_grav_real"),
                    rs.getString("fator_empilalhemto")
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        MontarTabela();        
        con.fecharConexao();
    }
    
    private void PreencherTabelaFiltrada(){
        if(jCheckBoxUTM.isSelected()){
            upc = "UTM";
        }else if(!jSpinnerUPC.getValue().toString().equals("0")){
            upc = jSpinnerUPC.getValue().toString();
        }else{
            upc = "";
        }
        
        if(jComboBoxCategoria.getSelectedItem().equals("-")){
            categoria="";
        }else{
            categoria = jComboBoxCategoria.getSelectedItem().toString();
        }
        
        if(jComboBoxMatGen.getSelectedItem().toString().equals("-")){
            mat_gen="";
        }else{
            mat_gen = jComboBoxMatGen.getSelectedItem().toString();        
        }
        
        JOptionPane.showMessageDialog(null, "upc: "+upc+" categoria: "+categoria+" mat_gen: "+mat_gen);
        
        dados = new ArrayList();
        tamanho = 0;
        ConexaoBD con = ConexaoBD.getConexao();
        String query;
        ResultSet rs;
        String WhereSql;
        colunas = new String[] { 
            "Estado",
            "Bloco",
            "Municipio",
            "Fazenda",
            "Projeto",
            "UPC",
            "Talhao",
            "Area",
            "M3/ha",
            "Mat.Gen.",
            "Talhadia",                    
            "Vol_Md_Real",                    
            "MdC_Real",                    
            "Md_Tn_Real",                    
            "Cv_Tn_Real",                    
            "Md_Praca",
            "Md_Forno"                    
        };
        //WhereSql = "where upc like '"+upc+"%' and categoria like '"+categoria+"%' and material_genetico like '"+mat_gen+"%' ORDER BY `id_estoque_p` ASC";
        WhereSql = "where upc like '"+upc+"' and categoria like '%"+categoria+"%' and material_genetico like '%"+mat_gen+"%' ORDER BY `m3_ha` ASC";
        //WhereSql = "where upc = '"+upc+"' and categoria = '"+categoria+"' and material_genetico = '"+mat_gen+"' ORDER BY `m3_ha` ASC";
        query = "SELECT * FROM estoque_principal "+ WhereSql;
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);

        try {
            while(rs.next()){
                dados.add(new Object[]{
                    rs.getString("estado"),
                    rs.getString("bloco"),
                    rs.getString("municipio"),
                    rs.getString("fazenda"),
                    rs.getString("projeto"),
                    rs.getString("upc"),
                    rs.getString("talhao"),
                    rs.getString("area"),
                    rs.getString("m3_ha"),
                    rs.getString("material_genetico"),
                    rs.getString("talhadia"),
                    rs.getString("vol_mad_real"),
                    rs.getString("mdc_real"),
                    rs.getString("mad_ton_real"),
                    rs.getString("carv_ton_real"),
                    rs.getString("madeira_praca"),
                    rs.getString("madeira_forno")
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        MontarTabela();        
        con.fecharConexao();
    }
    
    private void MontarTabela(){
        //JOptionPane.showMessageDialog(null, "Montando tabela... "+colunas[1]+linha[1]);
        //System.out.println("Montando tabela... "+dados.get(1).getClass().toString());
        GerarTabela modelo = new GerarTabela(dados, colunas);
        jTableRelatorioEstoquePrincipal.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*100);
            jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setResizable(false);
            //System.out.println("Indice: "+i+" - "+ colunas[i].length()*200);
        }
        jTableRelatorioEstoquePrincipal.getTableHeader().setReorderingAllowed(false);
        jTableRelatorioEstoquePrincipal.setAutoResizeMode(jTableRelatorioEstoquePrincipal.AUTO_RESIZE_OFF);
        jTableRelatorioEstoquePrincipal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void GerarPDF() throws DocumentException, FileNotFoundException {
        if(jTableRelatorioEstoquePrincipal.getSelectedRow()>=0) { 
            int linha = jTableRelatorioEstoquePrincipal.getSelectedRow();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            //System.out.println(new File(".").getAbsolutePath());
            String arquivo = new File("Relatorio.").getAbsolutePath()+"pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
            document.open();
            document.add(new Paragraph("Documento teste!!!"));
            document.add(new Paragraph("Municipio: "+jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString()));
            document.close();
            //JOptionPane.showMessageDialog(null, "PDF: "+writer);
        }else {
            //Document document = new Document(PageSize.A4, 72, 72, 72, 72);
            Rectangle rect = new Rectangle(1200, 595);
            Document document = new Document(rect);
            String arquivo = new File("Relatorio.").getAbsolutePath()+"pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
            document.open();
            PdfPTable table = new PdfPTable(colunas.length);
            PdfPCell header = new PdfPCell(new Paragraph("Relatorio"));
            header.setColspan(841);
            table.addCell(header);	
            
            for(int i=0;i<colunas.length;i++){
                table.addCell(colunas[i].toString());
            }
            
            for(int i=0;i<tamanho;i++){//linha
                for(int j=0;j<colunas.length;j++){//coluna
                    table.addCell(jTableRelatorioEstoquePrincipal.getValueAt(i, j).toString());
                }
            }
            
            document.add(table);
            document.close();
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        }
    }
    
    private void SelecionarTalhao(){
        if(jTableRelatorioEstoquePrincipal.getSelectedRow()>=0) {    
            int linha = jTableRelatorioEstoquePrincipal.getSelectedRow();
            ControlePrincipal.id_estoque_principal = jTableRelatorioEstoquePrincipal.getValueAt(linha, 0).toString();
            ControlePrincipal.municipio = jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString();
            ControlePrincipal.fazenda = jTableRelatorioEstoquePrincipal.getValueAt(linha, 4).toString();
            ControlePrincipal.projeto = jTableRelatorioEstoquePrincipal.getValueAt(linha, 5).toString();
            ControlePrincipal.upc = Integer.parseInt(jTableRelatorioEstoquePrincipal.getValueAt(linha, 6).toString());
            ControlePrincipal.talhao = jTableRelatorioEstoquePrincipal.getValueAt(linha, 7).toString();            
                        
            if(ControlePrincipal.tipo_u.equals("op_m")){
                ControlePrincipal.densidade_madeira = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 22).toString());
                ControlePrincipal.vol_mad_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 28).toString());
                ControlePrincipal.vol_mad_real = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 29).toString());
                ControlePrincipal.vol_mad_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 30).toString());
                ControlePrincipal.mad_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 34).toString());
                ControlePrincipal.mad_ton_real = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 35).toString());
                ControlePrincipal.mad_ton_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 36).toString());
                ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 40).toString());
                ControlePrincipal.mad_ton_tot = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 42).toString());
                ControlePrincipal.fator_emp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 46).toString());
                if(ControlePrincipal.fator_emp>0){
                    //JOptionPane.showMessageDialog(null, "InserirMadeiraPraca");
                    new InserirMadeiraPraca().setVisible(true);
                    this.setVisible(false);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "Talhão "+ControlePrincipal.talhao+" sem fator empilhamento definido!");
                }
            }else if(ControlePrincipal.tipo_u.equals("op_c")){                
                ControlePrincipal.densidade_carvao = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 23).toString());
                ControlePrincipal.mdc_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 31).toString());
                ControlePrincipal.mdc_real = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 32).toString());
                ControlePrincipal.mdc_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 33).toString());
                ControlePrincipal.carv_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 37).toString());
                ControlePrincipal.carv_ton_real = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 38).toString());
                ControlePrincipal.carv_ton_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 39).toString());
                ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 40).toString());
                ControlePrincipal.madeira_forno = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 41).toString());
                ControlePrincipal.carv_ton_tot = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 43).toString());
                ControlePrincipal.rend_grav_real = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 45).toString());
                if(ControlePrincipal.madeira_praca>0){
                    //JOptionPane.showMessageDialog(null, "InserirMadeiraForno");
                    new InserirMadeiraForno().setVisible(true);
                    this.setVisible(false);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "Talhão "+ControlePrincipal.talhao+" sem estoque na praça!");
                }
            }else if(ControlePrincipal.tipo_u.equals("op_s")){ 
                /*try {
                    new GerenciarEstoquePrincipal().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }            
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        //this.setVisible(false);
    }
    
    private void VoltarMenu(){        
        if(ControlePrincipal.tipo_u.equals("op_m")){
            try {
                new GerenciarMadeiraPraca().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(ControlePrincipal.tipo_u.equals("op_c")){
            try {
                new GerenciarCarvaoForno().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(ControlePrincipal.tipo_u.equals("op_s")){
            try {
                new GerenciarUsuarios().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        jButtonGerarPDF = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonVoltar = new javax.swing.JButton();
        jSpinnerUPC = new javax.swing.JSpinner();
        jComboBoxCategoria = new javax.swing.JComboBox();
        jComboBoxMatGen = new javax.swing.JComboBox();
        jCheckBoxUTM = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButtonFiltrar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRelatorioEstoquePrincipal = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Relatorio Estoque Principal Basico");
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

        jButtonGerarPDF.setFont(jButtonGerarPDF.getFont().deriveFont(jButtonGerarPDF.getFont().getSize()+1f));
        jButtonGerarPDF.setText("Gerar PDF");
        jButtonGerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarPDFActionPerformed(evt);
            }
        });

        jButtonLogout.setFont(jButtonLogout.getFont().deriveFont(jButtonLogout.getFont().getSize()+13f));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        jButtonVoltar.setText("Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jSpinnerUPC.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9, 1));

        jComboBoxCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));

        jComboBoxMatGen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));

        jCheckBoxUTM.setText("UTM");

        jLabel2.setText("UPC");

        jLabel3.setText("Categoria");

        jLabel4.setText("Mat. Genetico");

        jButtonFiltrar.setText("Filtrar");
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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(48, 48, 48))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButtonGerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSpinnerUPC, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBoxUTM, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonFiltrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxMatGen, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBoxUTM)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinnerUPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMatGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonFiltrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonGerarPDF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setDebugGraphicsOptions(javax.swing.DebugGraphics.LOG_OPTION);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1000, 1000));
        jScrollPane1.setRequestFocusEnabled(false);

        jTableRelatorioEstoquePrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableRelatorioEstoquePrincipal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableRelatorioEstoquePrincipal.setColumnSelectionAllowed(true);
        jTableRelatorioEstoquePrincipal.setFillsViewportHeight(true);
        jTableRelatorioEstoquePrincipal.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jTableRelatorioEstoquePrincipal.setMinimumSize(new java.awt.Dimension(450, 450));
        jTableRelatorioEstoquePrincipal.setPreferredSize(new java.awt.Dimension(3200, 0));
        jTableRelatorioEstoquePrincipal.setRequestFocusEnabled(false);
        jTableRelatorioEstoquePrincipal.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableRelatorioEstoquePrincipal);
        jTableRelatorioEstoquePrincipal.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jButtonGerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarPDFActionPerformed
        try {
            //AlterarInfo();
            //SelecionarTalhao();
            GerarPDF();
        } catch (DocumentException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonGerarPDFActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        PreencherTabelaFiltrada();
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
            java.util.logging.Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerarRelatorioEstoqueBasico().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JButton jButtonGerarPDF;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JCheckBox jCheckBoxUTM;
    private javax.swing.JComboBox jComboBoxCategoria;
    private javax.swing.JComboBox jComboBoxMatGen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerUPC;
    private javax.swing.JTable jTableRelatorioEstoquePrincipal;
    // End of variables declaration//GEN-END:variables
    
}
