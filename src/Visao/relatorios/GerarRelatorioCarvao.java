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
import Visao.estoqueprincipal.GerenciarEstoquePrincipal;
import Visao.expedircarvao.GerenciarEnvioCarvao;
import Visao.fazenda.GerenciarFazenda;
import Visao.forno.GerenciarForno;
import Visao.login.Login;
import Visao.madeira.GerenciarMadeiraPraca;
import Visao.usuario.GerenciarUsuarios;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Cristiano GD
 */
public class GerarRelatorioCarvao extends javax.swing.JFrame {
    
    private ArrayList linhas;
    private String[] colunas;
    private float vol_mad_Total;
    private float vol_mad_Processada;
    private float vol_carv_Total;
    
    /**
     * Creates new form GerarRelatorioCarvao
     * @throws java.sql.SQLException
     */
    public GerarRelatorioCarvao() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        jMenuItemRelatorioCarvao.setVisible(false);
        if(ControlePrincipal.tipo_u!=null){
            if(ControlePrincipal.tipo_u.equals("op_dir")){
                jMenuGerenciar.setVisible(false);
                jMenuItemGerenciarCarvaoForno.setVisible(false);
                jMenuItemGerenciarEstoque.setVisible(false);
                jMenuItemGerenciarFazendas.setVisible(false);
                jMenuItemGerenciarMadeiraPraça.setVisible(false);
                jMenuItemGerenciarExpedirCarvao.setVisible(false);
                jMenuItemGerenciarUsuarios.setVisible(false);
                jMenuItemGerenciarForno.setVisible(false);
            }
        }
        ChangeName();
        _carregarUsuarios();
    }   
    
    private void _carregarUsuarios(){ 
        jComboBoxUsuario.removeAllItems();
        jComboBoxUsuario.addItem("-");
        ConexaoBD con = ConexaoBD.getConexao(0);
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
        ConexaoBD con = ConexaoBD.getConexao(0);
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
        _carregarMateriaisGeneticos();
    }   
    
    private void _carregarMateriaisGeneticos(){ 
        jComboBoxMaterialGenetico.removeAllItems();
        jComboBoxMaterialGenetico.addItem("-");
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        query = "SELECT material_gen FROM controle_carvao";
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<jComboBoxMaterialGenetico.getItemCount(); j++) {
                    if (jComboBoxMaterialGenetico.getItemAt(j).equals(rs.getString("material_gen"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("material_gen"));
                    jComboBoxMaterialGenetico.addItem(rs.getString("material_gen"));
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar materiais genéticos! "+ex);
        }             
        PreencherTabela();
    }   
    
    /**
     * 
     */
    private void PreencherTabela(){                      
        linhas = new ArrayList();
        colunas = new String[] { 
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
            "rend_grav_forno",
            "material_gen"
        };
        int tamanho = 0;    
        String query;        
        String whereSql;
        
        //Controle e definição das variaveis da clausula where like. Filtros
        String filtro_op_u;
        String filtro_forno;
        String filtro_mat_gen;
        String filtro_upc;
        String filtro_talhao;
        String filtro_data_i = null;
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
        
        if(jComboBoxMaterialGenetico.getSelectedItem().equals("-")){
            filtro_mat_gen="";
        }else{
            filtro_mat_gen=jComboBoxMaterialGenetico.getSelectedItem().toString();
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
            filtro_data_i = jTextFieldDataI.getText().replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3/$2/$1");
        }
        
        if(jTextFieldDataF.getText().equals("")){
            filtro_data_f=""; 
        }else{
            filtro_data_f = jTextFieldDataF.getText().replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3/$2/$1");
        }
        
        //faz busca a partir dos filtros acima
        whereSql = "where ";
        if(!filtro_op_u.equals("")){
            whereSql += "id_operario = '"+filtro_op_u+"'";
        }
        
        //System.out.println("whereSql: " + whereSql.length());
        if(!filtro_forno.equals("")){
            if(whereSql.length()>=17){
                whereSql += " and forno = '"+filtro_forno+"'";
            }else{            
                whereSql += "forno = '"+filtro_forno+"'";
            }        
        }
        
        if(!filtro_mat_gen.equals("")){
            if(whereSql.length()>=17){
                whereSql += " and material_gen = '"+filtro_mat_gen+"'";
            }else{            
                whereSql += "material_gen = '"+filtro_mat_gen+"'";
            }        
        }
        
        if(!filtro_upc.equals("")){
            if(whereSql.length()>=17){
                whereSql += " and upc_c = '"+filtro_upc+"'";
            }else{
                whereSql += "upc_c = '"+filtro_upc+"'";
            }
        }
        
        if(!filtro_talhao.equals("")){
            if(whereSql.length()>=17){
                whereSql += " and talhao = '"+filtro_talhao+"'";
            }else{
                whereSql += "talhao = '"+filtro_talhao+"'";
            }            
        }
        
        if(!filtro_data_i.equals("")){        
            if(!filtro_data_f.equals("")){
                if(whereSql.length()>=17){
                    whereSql += " and data_entrada_madeira_forno between '"+filtro_data_i+"' and '"+filtro_data_f+"'";
                }else{
                    whereSql += "data_entrada_madeira_forno between '"+filtro_data_i+"' and '"+filtro_data_f+"'";
                }
            }else{
                if(whereSql.length()>=17){
                    whereSql += " and data_entrada_madeira_forno >= '"+filtro_data_i+"'";
                }else{
                    whereSql += " data_entrada_madeira_forno >= '"+filtro_data_i+"'";
                }
            }
        }
        
        if(whereSql.length()<7){
            whereSql = "";
        }
        
        query = "Select * from controle_carvao "+whereSql;
        //System.out.println("query: "+query);
        ConexaoBD con = ConexaoBD.getConexao(0);         
        ResultSet rs = con.consultaSql(query);
        vol_mad_Total=0;
        vol_mad_Processada=0;
        vol_carv_Total=0;
        Locale brasil = new Locale ("pt", "BR");
        DecimalFormat decformat = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols (brasil));
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            while(rs.next()){
                linhas.add(new Object[]{
                    //rs.getString("id_controle_carvao"),
                    rs.getString("id_estoque_p"),
                    rs.getString("id_operario"),
                    rs.getString("upc_c"),
                    rs.getString("talhao"),
                    rs.getString("forno"),
                    rs.getString("volume_madeira"),
                    newDateFormat.format(rs.getTimestamp("data_entrada_madeira_forno")),
                    rs.getString("volume_carvao"),
                    rs.getString("data_saida_carvao_forno"),
                    rs.getString("rend_grav_forno"),
                    rs.getString("material_gen")
                });
                
                //volume madeira total
                if(rs.getString("volume_madeira")!=null){
                    vol_mad_Total += Float.valueOf(rs.getString("volume_madeira"));
                }
                
                //volume carvão total
                if(rs.getString("volume_carvao")!=null){
                    if(!rs.getString("volume_carvao").equals("0")){
                        vol_mad_Processada += Float.valueOf(rs.getString("volume_madeira"));
                    }
                    vol_carv_Total += Float.valueOf(rs.getString("volume_carvao"));
                }
                tamanho++;
            }
            jLabelVolumeMadeiraTotal.setText("Volume madeira total: "+decformat.format(vol_mad_Total)+" m³");   
            jLabelVolumeMadeiraProcessada.setText("Volume madeira processada: "+decformat.format(vol_mad_Processada)+" m³"); 
            jLabelVolumeCarvaoTotal.setText("Volume carvão total: "+decformat.format(vol_carv_Total)+" m³");
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        GerarTabela modelo = new GerarTabela(linhas, colunas);
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
        jTableRelatorioCarvao.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableRelatorioCarvao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        con.fecharConexao();
        RenomearColunas();
    }
    
    private void RenomearColunas(){
        /*for(int i=0;i<colunas.length;i++){
            System.out.println("Indice: "+i+" - "+ colunas[i]);
        }*/
        jTableRelatorioCarvao.getColumnModel().getColumn(0).setHeaderValue("Nº Estoque");
        jTableRelatorioCarvao.getColumnModel().getColumn(1).setHeaderValue("Operario");
        jTableRelatorioCarvao.getColumnModel().getColumn(2).setHeaderValue("UPC");
        jTableRelatorioCarvao.getColumnModel().getColumn(3).setHeaderValue("Talhão");
        jTableRelatorioCarvao.getColumnModel().getColumn(4).setHeaderValue("Nome Forno");
        jTableRelatorioCarvao.getColumnModel().getColumn(5).setHeaderValue("Capacidade Forno(m.e)");
        jTableRelatorioCarvao.getColumnModel().getColumn(6).setHeaderValue("Data Fechamento Forno");
        jTableRelatorioCarvao.getColumnModel().getColumn(7).setHeaderValue("Vol. Carvão");
        jTableRelatorioCarvao.getColumnModel().getColumn(8).setHeaderValue("Data Abertura Forno");
        jTableRelatorioCarvao.getColumnModel().getColumn(9).setHeaderValue("Rendimento");
        jTableRelatorioCarvao.getColumnModel().getColumn(10).setHeaderValue("Material Genetico");
        /*jTableRelatorioCarvao.getColumnModel().getColumn(11).setHeaderValue("Data Saida Carvão Forno");
        jTableRelatorioCarvao.getColumnModel().getColumn(12).setHeaderValue("Nº Estoque");
        jTableRelatorioCarvao.getColumnModel().getColumn(13).setHeaderValue("Operario");
        jTableRelatorioCarvao.getColumnModel().getColumn(14).setHeaderValue("Rend. Grav. Forno");
        jTableRelatorioCarvao.getColumnModel().getColumn(15).setHeaderValue("Numero");
        jTableRelatorioCarvao.getColumnModel().getColumn(16).setHeaderValue("Nº Forno");*/
    }
    
    private void GerarPDF() throws DocumentException, FileNotFoundException {
        try {
            int linha = jTableRelatorioCarvao.getSelectedRow();
                Document document = new Document(PageSize.A4, 10, 10, 10, 10);
                //System.out.println(new File(".").getAbsolutePath());
                String arquivo = new File("Relatorio Carvão Praça.").getAbsolutePath()+"pdf";            
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
                document.open();
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                //String titulo = jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString()+ " "+jTableRelatorioEstoquePrincipal.getValueAt(linha, 2).toString()+"-"+jTableRelatorioEstoquePrincipal.getValueAt(linha, 0).toString();
                String titulo = "Relatorio Carvão Praça";                
                Paragraph pgt = new Paragraph(titulo, font);
                pgt.setAlignment(Element.ALIGN_CENTER);
                document.add(pgt);
                document.add(new Paragraph(" "));
                /*if(!filtro_matgen.equals("")){
                    titulo = "Material genetico "+filtro_matgen;
                }*/
                PdfPTable table = new PdfPTable(colunas.length);
                // Definindo uma fonte, com tamanho 20 e negrito
                PdfPCell header = new PdfPCell(new Paragraph(titulo,font));
                header.setColspan(colunas.length);
                table.addCell(header);
                table.setWidthPercentage(100.0f);
                table.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
                //System.out.println("Tamanho: "+linhas.size());
                font = new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.NORMAL);
                for (String coluna : colunas) {
                    table.addCell(new Paragraph(coluna,font));
                }

                //varias linhas
                for(int i=0;i<linhas.size();i++){//linha
                    for(int j=0;j<colunas.length;j++){//coluna
                        //table.addCell(jTableRelatorioEstoquePrincipal.getValueAt(i, j).toString());
                        table.addCell(new Paragraph(jTableRelatorioCarvao.getValueAt(i, j).toString(),font));
                    }
                }

                document.add(table);
                document.add(new Paragraph(" "));
                
                font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                document.add(new Paragraph("Dados Totais",font));
                font = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
                document.add(new Paragraph(jLabelInfo1.getText(),font));                
                document.add(new Paragraph(jLabelVolumeMadeiraTotal.getText(),font));
                document.add(new Paragraph(jLabelVolumeCarvaoTotal.getText(),font));
                document.close();
                JOptionPane.showMessageDialog(null, "PDF: "+arquivo+" gerado!");
        } catch (FileNotFoundException | DocumentException | HeadlessException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Erro ao gerar pdf: "+ex);
            JOptionPane.showMessageDialog(null, "Erro ao gerar pdf: "+ex);
        }
        
    }
    
    private void ChangeName(){
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
        jLabel7 = new javax.swing.JLabel();
        jComboBoxUsuario = new javax.swing.JComboBox();
        jButtonFiltrar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxForno = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabelData = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerUPC = new javax.swing.JSpinner();
        jSpinnerTalhao = new javax.swing.JSpinner();
        jTextFieldDataI = new javax.swing.JTextField();
        jTextFieldDataF = new javax.swing.JTextField();
        jLabelData1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBoxMaterialGenetico = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRelatorioCarvao = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabelVolumeMadeiraTotal = new javax.swing.JLabel();
        jLabelVolumeCarvaoTotal = new javax.swing.JLabel();
        jLabelInfo1 = new javax.swing.JLabel();
        jLabelVolumeMadeiraProcessada = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuPrincipal = new javax.swing.JMenu();
        jMenuItemGerarPDF = new javax.swing.JMenuItem();
        jMenuItemValidade = new javax.swing.JMenuItem();
        jMenuItemLogout = new javax.swing.JMenuItem();
        jMenuRelatorio = new javax.swing.JMenu();
        jMenuItemRelatorioEstoque = new javax.swing.JMenuItem();
        jMenuItemRelatorioMadeiraPraca = new javax.swing.JMenuItem();
        jMenuItemRelatorioCarvao = new javax.swing.JMenuItem();
        jMenuItemRelatorioCarvaoExpedido = new javax.swing.JMenuItem();
        jMenuItemRelatorioFornos = new javax.swing.JMenuItem();
        jMenuGerenciar = new javax.swing.JMenu();
        jMenuItemGerenciarUsuarios = new javax.swing.JMenuItem();
        jMenuItemGerenciarFazendas = new javax.swing.JMenuItem();
        jMenuItemGerenciarMadeiraPraça = new javax.swing.JMenuItem();
        jMenuItemGerenciarCarvaoForno = new javax.swing.JMenuItem();
        jMenuItemGerenciarExpedirCarvao = new javax.swing.JMenuItem();
        jMenuItemGerenciarEstoque = new javax.swing.JMenuItem();
        jMenuItemGerenciarForno = new javax.swing.JMenuItem();

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

        jLabelData.setFont(jLabelData.getFont().deriveFont(jLabelData.getFont().getSize()+1f));
        jLabelData.setText("Data inicio");
        jLabelData.setPreferredSize(new java.awt.Dimension(80, 25));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
        jLabel2.setText("UPC");
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 25));

        jSpinnerUPC.setFont(jSpinnerUPC.getFont().deriveFont(jSpinnerUPC.getFont().getSize()+1f));
        jSpinnerUPC.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9, 1));
        jSpinnerUPC.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerTalhao.setFont(jSpinnerTalhao.getFont().deriveFont(jSpinnerTalhao.getFont().getSize()+1f));
        jSpinnerTalhao.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));
        jSpinnerTalhao.setPreferredSize(new java.awt.Dimension(150, 25));

        jTextFieldDataI.setFont(jTextFieldDataI.getFont().deriveFont(jTextFieldDataI.getFont().getSize()+1f));
        jTextFieldDataI.setPreferredSize(new java.awt.Dimension(150, 25));

        jTextFieldDataF.setFont(jTextFieldDataF.getFont().deriveFont(jTextFieldDataF.getFont().getSize()+1f));
        jTextFieldDataF.setPreferredSize(new java.awt.Dimension(150, 25));
        jTextFieldDataF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDataFActionPerformed(evt);
            }
        });

        jLabelData1.setFont(jLabelData1.getFont().deriveFont(jLabelData1.getFont().getSize()+1f));
        jLabelData1.setText("Data fim");
        jLabelData1.setPreferredSize(new java.awt.Dimension(80, 25));

        jLabel11.setFont(jLabel11.getFont().deriveFont(jLabel11.getFont().getSize()+1f));
        jLabel11.setText("Mat. Gen.");
        jLabel11.setPreferredSize(new java.awt.Dimension(80, 25));

        jComboBoxMaterialGenetico.setFont(jComboBoxMaterialGenetico.getFont().deriveFont(jComboBoxMaterialGenetico.getFont().getSize()+1f));
        jComboBoxMaterialGenetico.setPreferredSize(new java.awt.Dimension(150, 25));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelData1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDataF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldDataI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                                .addComponent(jComboBoxUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBoxMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jLabelData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDataI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDataF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelData1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jScrollPane2.setViewportView(jTableRelatorioCarvao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        jLabelVolumeMadeiraTotal.setFont(jLabelVolumeMadeiraTotal.getFont());
        jLabelVolumeMadeiraTotal.setText("Volume madeira total: 0m³");
        jLabelVolumeMadeiraTotal.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelVolumeCarvaoTotal.setFont(jLabelVolumeCarvaoTotal.getFont());
        jLabelVolumeCarvaoTotal.setText("Volume carvão total: 0 m³");
        jLabelVolumeCarvaoTotal.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelInfo1.setFont(jLabelInfo1.getFont().deriveFont(jLabelInfo1.getFont().getStyle() | java.awt.Font.BOLD, 12));
        jLabelInfo1.setText("Informações Gerais");
        jLabelInfo1.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelVolumeMadeiraProcessada.setFont(jLabelVolumeMadeiraProcessada.getFont());
        jLabelVolumeMadeiraProcessada.setText("Volume madeira processada: 0m³");
        jLabelVolumeMadeiraProcessada.setPreferredSize(new java.awt.Dimension(200, 15));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelInfo1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                        .addGap(532, 532, 532))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelVolumeCarvaoTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabelVolumeMadeiraTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addGap(132, 132, 132))
                            .addComponent(jLabelVolumeMadeiraProcessada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                        .addGap(531, 531, 531))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabelInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabelVolumeMadeiraTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabelVolumeMadeiraProcessada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabelVolumeCarvaoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jMenuPrincipal.setText("Menu");

        jMenuItemGerarPDF.setText("Gerar PDF");
        jMenuItemGerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerarPDFActionPerformed(evt);
            }
        });
        jMenuPrincipal.add(jMenuItemGerarPDF);

        jMenuItemValidade.setText("Validade");
        jMenuItemValidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemValidadeActionPerformed(evt);
            }
        });
        jMenuPrincipal.add(jMenuItemValidade);

        jMenuItemLogout.setText("Logout");
        jMenuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogoutActionPerformed(evt);
            }
        });
        jMenuPrincipal.add(jMenuItemLogout);

        jMenuBar1.add(jMenuPrincipal);

        jMenuRelatorio.setText("Relatorios");

        jMenuItemRelatorioEstoque.setText("Estoque");
        jMenuItemRelatorioEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRelatorioEstoqueActionPerformed(evt);
            }
        });
        jMenuRelatorio.add(jMenuItemRelatorioEstoque);

        jMenuItemRelatorioMadeiraPraca.setText("Madeira/Praça");
        jMenuItemRelatorioMadeiraPraca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRelatorioMadeiraPracaActionPerformed(evt);
            }
        });
        jMenuRelatorio.add(jMenuItemRelatorioMadeiraPraca);

        jMenuItemRelatorioCarvao.setText("Carvão");
        jMenuItemRelatorioCarvao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRelatorioCarvaoActionPerformed(evt);
            }
        });
        jMenuRelatorio.add(jMenuItemRelatorioCarvao);

        jMenuItemRelatorioCarvaoExpedido.setText("Carvão Expedido");
        jMenuItemRelatorioCarvaoExpedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRelatorioCarvaoExpedidoActionPerformed(evt);
            }
        });
        jMenuRelatorio.add(jMenuItemRelatorioCarvaoExpedido);

        jMenuItemRelatorioFornos.setText("Fornos");
        jMenuItemRelatorioFornos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRelatorioFornosActionPerformed(evt);
            }
        });
        jMenuRelatorio.add(jMenuItemRelatorioFornos);

        jMenuBar1.add(jMenuRelatorio);

        jMenuGerenciar.setText("Gerenciar");

        jMenuItemGerenciarUsuarios.setText("Usuarios");
        jMenuItemGerenciarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerenciarUsuariosActionPerformed(evt);
            }
        });
        jMenuGerenciar.add(jMenuItemGerenciarUsuarios);

        jMenuItemGerenciarFazendas.setText("Fazendas");
        jMenuItemGerenciarFazendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerenciarFazendasActionPerformed(evt);
            }
        });
        jMenuGerenciar.add(jMenuItemGerenciarFazendas);

        jMenuItemGerenciarMadeiraPraça.setText("Madeira/Praça");
        jMenuItemGerenciarMadeiraPraça.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerenciarMadeiraPraçaActionPerformed(evt);
            }
        });
        jMenuGerenciar.add(jMenuItemGerenciarMadeiraPraça);

        jMenuItemGerenciarCarvaoForno.setText("Carvao/Forno");
        jMenuItemGerenciarCarvaoForno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerenciarCarvaoFornoActionPerformed(evt);
            }
        });
        jMenuGerenciar.add(jMenuItemGerenciarCarvaoForno);

        jMenuItemGerenciarExpedirCarvao.setText("ExpedirCarvão");
        jMenuItemGerenciarExpedirCarvao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerenciarExpedirCarvaoActionPerformed(evt);
            }
        });
        jMenuGerenciar.add(jMenuItemGerenciarExpedirCarvao);

        jMenuItemGerenciarEstoque.setText("Estoque");
        jMenuItemGerenciarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerenciarEstoqueActionPerformed(evt);
            }
        });
        jMenuGerenciar.add(jMenuItemGerenciarEstoque);

        jMenuItemGerenciarForno.setText("Fornos");
        jMenuItemGerenciarForno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerenciarFornoActionPerformed(evt);
            }
        });
        jMenuGerenciar.add(jMenuItemGerenciarForno);

        jMenuBar1.add(jMenuGerenciar);

        setJMenuBar(jMenuBar1);

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        //_carregarUsuarios();
        PreencherTabela();
        //JOptionPane.showMessageDialog(null, jListFiltrar.getSelectedValuesList());
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

    private void jTextFieldDataFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDataFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDataFActionPerformed

    private void jMenuItemGerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerarPDFActionPerformed
        try {
            //AlterarInfo();
            //SelecionarTalhao();
            GerarPDF();
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gerar PDF Error: "+ex);
        }
    }//GEN-LAST:event_jMenuItemGerarPDFActionPerformed

    private void jMenuItemValidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemValidadeActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Validado até: "+ControlePrincipal.validade);
    }//GEN-LAST:event_jMenuItemValidadeActionPerformed

    private void jMenuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemLogoutActionPerformed

    private void jMenuItemGerenciarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerenciarUsuariosActionPerformed
        try {
            new GerenciarUsuarios().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemGerenciarUsuariosActionPerformed

    private void jMenuItemGerenciarFazendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerenciarFazendasActionPerformed
        try {
            new GerenciarFazenda().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemGerenciarFazendasActionPerformed

    private void jMenuItemGerenciarMadeiraPraçaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerenciarMadeiraPraçaActionPerformed
        try {
            new GerenciarMadeiraPraca().setVisible(true);
            ControlePrincipal.tipo_estoque="madeira";
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemGerenciarMadeiraPraçaActionPerformed

    private void jMenuItemGerenciarCarvaoFornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerenciarCarvaoFornoActionPerformed
        try {
            new GerenciarCarvaoForno().setVisible(true);
            ControlePrincipal.tipo_estoque="carvao";
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemGerenciarCarvaoFornoActionPerformed

    private void jMenuItemGerenciarExpedirCarvaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerenciarExpedirCarvaoActionPerformed
        try {
            new GerenciarEnvioCarvao().setVisible(true);
            ControlePrincipal.tipo_estoque="carvao";
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemGerenciarExpedirCarvaoActionPerformed

    private void jMenuItemGerenciarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerenciarEstoqueActionPerformed
        try {
            new GerenciarEstoquePrincipal().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemGerenciarEstoqueActionPerformed

    private void jMenuItemRelatorioCarvaoExpedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioCarvaoExpedidoActionPerformed
        try {
            new GerarRelatorioCarvaoExpedido().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemRelatorioCarvaoExpedidoActionPerformed

    private void jMenuItemRelatorioCarvaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioCarvaoActionPerformed
        try {
            new GerarRelatorioCarvao().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemRelatorioCarvaoActionPerformed

    private void jMenuItemRelatorioMadeiraPracaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioMadeiraPracaActionPerformed
        try {
            new GerarRelatorioMadeiraPraca().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemRelatorioMadeiraPracaActionPerformed

    private void jMenuItemRelatorioEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioEstoqueActionPerformed
        try {
            new GerarRelatorioEstoqueBasico().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemRelatorioEstoqueActionPerformed

    private void jMenuItemRelatorioFornosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioFornosActionPerformed
        new GerarRelatorioForno().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItemRelatorioFornosActionPerformed

    private void jMenuItemGerenciarFornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerenciarFornoActionPerformed
        new GerenciarForno().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemGerenciarFornoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new GerarRelatorioCarvao().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(GerarRelatorioCarvao.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JComboBox jComboBoxForno;
    private javax.swing.JComboBox jComboBoxMaterialGenetico;
    private javax.swing.JComboBox jComboBoxUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JLabel jLabelData1;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelInfo1;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelVolumeCarvaoTotal;
    private javax.swing.JLabel jLabelVolumeMadeiraProcessada;
    private javax.swing.JLabel jLabelVolumeMadeiraTotal;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuGerenciar;
    private javax.swing.JMenuItem jMenuItemGerarPDF;
    private javax.swing.JMenuItem jMenuItemGerenciarCarvaoForno;
    private javax.swing.JMenuItem jMenuItemGerenciarEstoque;
    private javax.swing.JMenuItem jMenuItemGerenciarExpedirCarvao;
    private javax.swing.JMenuItem jMenuItemGerenciarFazendas;
    private javax.swing.JMenuItem jMenuItemGerenciarForno;
    private javax.swing.JMenuItem jMenuItemGerenciarMadeiraPraça;
    private javax.swing.JMenuItem jMenuItemGerenciarUsuarios;
    private javax.swing.JMenuItem jMenuItemLogout;
    private javax.swing.JMenuItem jMenuItemRelatorioCarvao;
    private javax.swing.JMenuItem jMenuItemRelatorioCarvaoExpedido;
    private javax.swing.JMenuItem jMenuItemRelatorioEstoque;
    private javax.swing.JMenuItem jMenuItemRelatorioFornos;
    private javax.swing.JMenuItem jMenuItemRelatorioMadeiraPraca;
    private javax.swing.JMenuItem jMenuItemValidade;
    private javax.swing.JMenu jMenuPrincipal;
    private javax.swing.JMenu jMenuRelatorio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerTalhao;
    private javax.swing.JSpinner jSpinnerUPC;
    private javax.swing.JTable jTableRelatorioCarvao;
    private javax.swing.JTextField jTextFieldDataF;
    private javax.swing.JTextField jTextFieldDataI;
    // End of variables declaration//GEN-END:variables
}
