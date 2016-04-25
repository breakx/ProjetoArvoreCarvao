/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.relatorios;

import Controle.ControleEstoquePrincipal;
import Controle.ControlePrincipal;
import Controle.estoqueprincipal.InserirEstoquePrincipalCtrl;
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
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
//import api iText import com.lowagie.text.*; 

/**
 *
 * @author Cristiano GD
 */
public class GerarRelatorioEstoqueBasico extends javax.swing.JFrame {
    
    private ArrayList linhas;
    private String[] colunas;
    private int tamanho;
    float areaTotal;
    float vol_mad_transpTotal;
    float mdc_transpTotal;
    float mad_ton_transpTotal;
    float carv_ton_transpTotal;
    float madeira_pracaTotal;
    float madeira_fornoTotal;
    float mad_ton_totTotal;
    float carv_ton_totTotal;     
    float m3_haMedia;
    float rend_gravMediaPonderada;
    
    /**
     * Creates new form GerarRelatorioEstoquePrincipal
     * @throws java.sql.SQLException
     */
    public GerarRelatorioEstoqueBasico() throws SQLException {
        initComponents();
        ChangeName();
        //PreencherTabelaCompleta();
        PreencherTabelaFiltrada();
        this.setExtendedState(MAXIMIZED_BOTH);
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
        _carregarProjetos();
    }
    
    private void _carregarProjetos(){
        jComboBoxProjeto.addItem("I");
        jComboBoxProjeto.addItem("II");
        jComboBoxProjeto.addItem("III");  
        _carregarFazendas();
    }
    
    private void _carregarFazendas(){ 
        ConexaoBD con = ConexaoBD.getConexao();
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
            JOptionPane.showMessageDialog(null, "Erro ao Preencher Tabela Completa ! "+ex);
        }        
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
        //_carregarFiltros();
    }
    
    private void _carregarFiltros(){
            /*jComboBoxFiltrar.addItem("Estado");
            jComboBoxFiltrar.addItem("Bloco");
            jComboBoxFiltrar.addItem("Municipio");
            jComboBoxFiltrar.addItem("Fazenda");
            jComboBoxFiltrar.addItem("Projeto");
            jComboBoxFiltrar.addItem("UPC");
            jComboBoxFiltrar.addItem("Talhao");
            jComboBoxFiltrar.addItem("Area");
            jComboBoxFiltrar.addItem("M3/ha");
            jComboBoxFiltrar.addItem("Mat.Gen.");
            jComboBoxFiltrar.addItem("Talhadia");
            jComboBoxFiltrar.addItem("Ano_Rt");
            jComboBoxFiltrar.addItem("Data_Plant");
            jComboBoxFiltrar.addItem("Data_Rt_1");
            jComboBoxFiltrar.addItem("Data_Rt_2");
            jComboBoxFiltrar.addItem("Data_Rt_3");
            jComboBoxFiltrar.addItem("Idade");
            jComboBoxFiltrar.addItem("Categoria");
            jComboBoxFiltrar.addItem("Situacao");
            jComboBoxFiltrar.addItem("IMA");
            jComboBoxFiltrar.addItem("MdC/ha");
            jComboBoxFiltrar.addItem("Dens_Md");
            jComboBoxFiltrar.addItem("Dens_Cv");
            jComboBoxFiltrar.addItem("Md_Tn_ha");
            jComboBoxFiltrar.addItem("Cv_Tn_ha");
            jComboBoxFiltrar.addItem("Id_Op");
            jComboBoxFiltrar.addItem("Data_Reg");
            jComboBoxFiltrar.addItem("Vol_Md_Est");
            jComboBoxFiltrar.addItem("Vol_Md_Transp");
            jComboBoxFiltrar.addItem("Vol_Md_Bal");
            jComboBoxFiltrar.addItem("MdC_Est");
            jComboBoxFiltrar.addItem("MdC_Transp");
            jComboBoxFiltrar.addItem("MdC_Bal");
            jComboBoxFiltrar.addItem("Md_Tn_Est");
            jComboBoxFiltrar.addItem("Md_Tn_Transp");
            jComboBoxFiltrar.addItem("Md_Tn_Bal");
            jComboBoxFiltrar.addItem("Cv_Tn_Est");
            jComboBoxFiltrar.addItem("Cv_Tn_Transp");
            jComboBoxFiltrar.addItem("Cv_Tn_Bal");
            jComboBoxFiltrar.addItem("Md_Praca");
            jComboBoxFiltrar.addItem("Md_Forno");
            jComboBoxFiltrar.addItem("Md_Tn_Tot");
            jComboBoxFiltrar.addItem("Cv_Tn_Tot");
            jComboBoxFiltrar.addItem("RG_Est");
            jComboBoxFiltrar.addItem("RG_Real");
            jComboBoxFiltrar.addItem("Fator_Emp");*/
            
        /*ArrayList model = new ArrayList();
        model.add(new Object[]{
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
            "Vol_Md_Transp",
            "Vol_Md_Bal",
            "MdC_Est",
            "MdC_Transp",
            "MdC_Bal",
            "Md_Tn_Est",
            "Md_Tn_Transp",
            "Md_Tn_Bal",
            "Cv_Tn_Est",
            "Cv_Tn_Transp",
            "Cv_Tn_Bal",
            "Md_Praca",
            "Md_Forno",
            "Md_Tn_Tot",
            "Cv_Tn_Tot",
            "RG_Est",
            "RG_Real",
            "Fator_Emp"
            });*/
        //jListFiltrar.add("Estado", this);
        //jListFiltrar.setModel((ListModel) model);
    }
    
    /**
     * 
     */
    private void PreencherTabelaCompleta(){
        /*linhas = new ArrayList();
        tamanho = 0;
        
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
            "Vol_Md_Transp",
            "Vol_Md_Bal",
            "MdC_Est",
            "MdC_Transp",
            "MdC_Bal",
            "Md_Tn_Est",
            "Md_Tn_Transp",
            "Md_Tn_Bal",
            "Cv_Tn_Est",
            "Cv_Tn_Transp",
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
            whereSql = "where municipio like '%"+ControlePrincipal.municipio+"%' and fazenda like '%"+ControlePrincipal.fazenda+"%'";
        }else if(ControlePrincipal.municipio != null){
            whereSql = "where municipio like '%"+ControlePrincipal.municipio+"%' ORDER BY `id_estoque_p` DESC";
        }else if(ControlePrincipal.fazenda != null){
            whereSql = "where fazenda like '%"+ControlePrincipal.fazenda+"%' ORDER BY `id_estoque_p` DESC";
        }else {
            whereSql = "";
        }
        

        query = "SELECT * FROM estoque_principal "+ whereSql;
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);

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
                    rs.getString("vol_mad_transp"),
                    rs.getString("vol_mad_balanco"),
                    rs.getString("mdc_estimado"),
                    rs.getString("mdc_transp"),
                    rs.getString("mdc_balanco"),
                    rs.getString("mad_ton_estimado"),
                    rs.getString("mad_ton_transp"),
                    rs.getString("mad_ton_balanco"),
                    rs.getString("carv_ton_estimado"),
                    rs.getString("carv_ton_transp"),
                    rs.getString("carv_ton_balanco"),
                    rs.getString("madeira_praca"),
                    rs.getString("madeira_forno"),
                    rs.getString("mad_ton_tot"),
                    rs.getString("carv_ton_tot"),
                    rs.getString("rend_grav_estimado"),
                    rs.getString("rend_grav_real"),
                    rs.getString("fator_empilalhemto"),
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Preencher Tabela Completa ! "+ex);
        }
        
        MontarTabela();        
        con.fecharConexao();*/
    }    
    
    private void PreencherTabelaFiltrada(){  
        //JOptionPane.showMessageDialog(null, "Size! " + jListFiltrar.getSelectedIndices().length + jListFiltrar.getModel());
        DecimalFormat decformat = new DecimalFormat("0.0");
        ConexaoBD con = ConexaoBD.getConexao();
        String query;
        ResultSet rs;
        String whereSql;
        String dado = jListFiltrar.getSelectedValuesList().toString();
        dado = dado.replaceAll("[\\[\\]]", "");               
        linhas = new ArrayList();
        // busca todos os campos
        if(dado.equals("-")){
            jListFiltrar.clearSelection();
        }
        if(!dado.contains("area")){
            dado+= ", area";
        }
        if(!dado.contains("vol_mad_transp")){
            dado+= ", vol_mad_transp";
        }
        if(!dado.contains("mdc_transp")){
            dado+= ", mdc_transp";
        }
        if(!dado.contains("mad_ton_transp")){
            dado+= ", mad_ton_transp";
        }
        if(!dado.contains("carv_ton_transp")){
            dado+= ", carv_ton_transp";
        }  
        
        if(!dado.contains("m3_ha")){
            dado+= ", m3_ha";
        }
        
        //JOptionPane.showMessageDialog(null, "Dado sql: "+dado);
        //verifica se algum campo foi selecionado e defini quantidade, senao defini total
        if(jListFiltrar.getSelectedIndices().length>0){
            tamanho = jListFiltrar.getSelectedIndices().length;
        }else{
            tamanho = jListFiltrar.getModel().getSize()-1;
        }
        
        //defini quantidade de colunas
        colunas = new String[tamanho];
        
        //carrega nomes das colunas selecionadas ou todas.
        for(int i = 0; i < tamanho; i++)
        {
            if(tamanho<46){
               //System.out.println(jListFiltrar.getSelectedValuesList().get(i));
               colunas[i] = (String) jListFiltrar.getSelectedValuesList().get(i);
            }else{
               //System.out.println(jListFiltrar.getModel().getElementAt(i+1));
               colunas[i] = (String) jListFiltrar.getModel().getElementAt(i+1);
            }
        }
        
        //Controle e definição das variaveis da clausula where like. Filtros
        String filtro_upc;
        String filtro_cat;
        String filtro_matgen;
        String filtro_proj;
        String filtro_faz;
        if(jSpinnerUPC.getValue().equals(0)){
            if(jCheckBoxUTM.isSelected()){
                filtro_upc="UTM"; 
            }else{
                filtro_upc="%%";    
            }
        }else{
            filtro_upc=jSpinnerUPC.getValue().toString();
        }
        
        if(jComboBoxCategoria.getSelectedItem().equals("-")){
            filtro_cat="";
        }else{
            filtro_cat=jComboBoxCategoria.getSelectedItem().toString();
        }
        
        if(jComboBoxMatGen.getSelectedItem().equals("-")){
            filtro_matgen="";
        }else{
            filtro_matgen=jComboBoxMatGen.getSelectedItem().toString();
        }
        
        if(jComboBoxProjeto.getSelectedItem().equals("-")){
            filtro_proj="";
        }else{
            filtro_proj = String.valueOf(jComboBoxProjeto.getSelectedIndex());
        }
        
        
        if(jComboBoxFazenda.getSelectedItem().equals("-")){
            filtro_faz="";
        }else{
            filtro_faz=jComboBoxFazenda.getSelectedItem().toString();
        }
        
        //faz busca a partir dos filtros acima
        if(!filtro_upc.equals("%%") || !filtro_cat.equals("") || !filtro_matgen.equals("") || !filtro_proj.equals("") || !filtro_faz.equals("")){
            whereSql = "where upc like '"+filtro_upc+"' and material_genetico like '%"+filtro_matgen+"%' and categoria like '%"+filtro_cat+"%' and projeto like '%"+filtro_proj+"%' and fazenda like '%"+filtro_faz+"%'";
        }else{
            whereSql = "";
        }

        if(tamanho<46){
            query = "SELECT "+dado+" FROM estoque_principal "+whereSql;
        }else{
            query = "SELECT * FROM estoque_principal "+whereSql;
        }        
        //JOptionPane.showMessageDialog(null, "Query: " + query);
        //carrega dados do banco de dados dependendo da consulta sql
        rs = con.consultaSql(query);
        areaTotal=0;
        vol_mad_transpTotal=0;
        mdc_transpTotal=0;
        mad_ton_transpTotal=0;
        carv_ton_transpTotal=0;
        m3_haMedia=0;
        try {            
            while(rs.next()){
                //cria um objeto coluna de acordo com as colunas selecionadas para cada linha encontrada na consulta
                Object[] coluna = new Object[tamanho];
                
                //carrega em cada coluna seu respectivo valor do banco de dados referente a sua coluna.
                for(int i = 0; i < tamanho; i++)
                {
                    coluna[i] = rs.getString(colunas[i]);
                    //System.out.println("Add Dados ["+i+"]: "+ob[i]);
                }
                if(rs.getString("area")!=null){
                    areaTotal += Float.valueOf(rs.getString("area"));
                }
                if(rs.getString("vol_mad_transp")!=null){
                    vol_mad_transpTotal += Float.valueOf(rs.getString("vol_mad_transp"));
                }
                if(rs.getString("mdc_transp")!=null){
                    mdc_transpTotal += Float.valueOf(rs.getString("mdc_transp"));
                }
                if(rs.getString("mad_ton_transp")!=null){
                    mad_ton_transpTotal += Float.valueOf(rs.getString("mad_ton_transp"));
                }
                if(rs.getString("carv_ton_transp")!=null){
                    carv_ton_transpTotal += Float.valueOf(rs.getString("carv_ton_transp"));
                }   
                
                if(rs.getString("m3_ha")!=null){
                    m3_haMedia += Float.valueOf(rs.getString("m3_ha"));
                }
                //System.out.printf("\nCalculo m3ha: "+ m3_haMedia); 
                //adiciona a cada linha os valores de cada objeto coluna
                linhas.add(coluna);
            }
            //System.out.printf("\nCalculo 2 m3ha: "+ m3_haMedia+ "linhas "+linhas.size());
            m3_haMedia = m3_haMedia/linhas.size();
            jLabelAreaTotal.setText("Area total: "+decformat.format(areaTotal)+" m³");
            jLabelVolumeMadeiraTotal.setText("Volume madeira total: "+decformat.format(vol_mad_transpTotal)+" m³");
            jLabelVolumeCarvaoTotal.setText("Volume carvão Total: "+decformat.format(mdc_transpTotal)+" m³");
            jLabelToneladaMadeiraTotal.setText("Toneladas de madeira totais: "+decformat.format(mad_ton_transpTotal)+" m³");
            jLabelToneladaCarvaoTotal.setText("Toneladas de carvão totais: "+decformat.format(carv_ton_transpTotal)+" m³");
            jLabelM3_ha.setText("Media geral m³/ha: "+decformat.format(m3_haMedia)+" m³");
            /*int n = dados.size(); 
            for (int i=0; i<n; i++) { 
                System.out.printf("Posição %d- %s\n", i, dados.get(i)); 
            } */
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Erro ao Preencher Tabela Filtrada ! "+ex);
            JOptionPane.showMessageDialog(null, "Erro ao Preencher Tabela Filtrada ! "+ex);
        }
        
        MontarTabela();        
        con.fecharConexao();
    }
    
    private void MontarTabela(){
        //monta a tabela com as respectivas linhas e colunas
        GerarTabela modelo = new GerarTabela(linhas, colunas);
        jTableRelatorioEstoquePrincipal.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }
            jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setResizable(false);
            //System.out.println("Indice: "+i+" - "+ colunas[i].length());
        }
        jTableRelatorioEstoquePrincipal.getTableHeader().setReorderingAllowed(false);
        jTableRelatorioEstoquePrincipal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
    }
      
    private void CarregarExcel() throws BiffException, IOException{
        DateFormat data_estoque_principal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
        Date date = new Date();    
        ControleEstoquePrincipal estoque_principal = new ControleEstoquePrincipal();
        /* pega o arquiivo do Excel */  
        Workbook workbook = Workbook.getWorkbook(new File("C:/Users/crist/Desktop/Nova pasta/projeto.xls"));  

        /* pega a primeira planilha dentro do arquivo XLS */  
        Sheet sheet = workbook.getSheet(0);  

        //Pega a quantidade de linhas da planilha   
        int linhas = sheet.getRows();  
        JOptionPane.showMessageDialog(null, "Lendo excel, linhas... "+linhas);
        Cell celula = null;
        for (int i = 1; i < linhas; i++) {  
            String stringa4="";
            /* pega os valores das células como se numa matriz */  
            /*for (int j = 0; j < 36; j++) {  
                //celula = sheet.getCell(j, i);//col, lin
                System.out.println(sheet.getCell(j, i).getContents());  
                //stringa4 += celula.getContents()+", ";               
            }
            //System.out.println(sheet.getCell(7, i).getContents()));              
            System.out.println("importando "+i);  */
            //System.out.println(sheet.getCell(29, i).getContents()); 
            estoque_principal.setEstado(sheet.getCell(0, i).getContents());            
            estoque_principal.setUpc(sheet.getCell(1, i).getContents());
            estoque_principal.setBloco(sheet.getCell(2, i).getContents());
            estoque_principal.setMunicipio(sheet.getCell(3, i).getContents());
            estoque_principal.setFazenda(sheet.getCell(4, i).getContents());
            estoque_principal.setProjeto(sheet.getCell(5, i).getContents());
            estoque_principal.setTalhao(Integer.parseInt(sheet.getCell(6, i).getContents()));
            estoque_principal.setArea(Float.parseFloat(sheet.getCell(7, i).getContents().replace(",",".")));
            estoque_principal.setM3_ha(Float.parseFloat(sheet.getCell(8, i).getContents().replace(",",".")));
            estoque_principal.setTalhadia(Integer.parseInt(sheet.getCell(9, i).getContents()));
            estoque_principal.setMaterial_genetico(sheet.getCell(10, i).getContents());
            estoque_principal.setAno_rotacao(Integer.parseInt(sheet.getCell(11, i).getContents()));
            estoque_principal.setData_plantio(sheet.getCell(12, i).getContents());
            estoque_principal.setData_rotacao_1(sheet.getCell(13, i).getContents());
            estoque_principal.setData_rotacao_2(sheet.getCell(14, i).getContents());
            estoque_principal.setData_rotacao_3(sheet.getCell(15, i).getContents());
            estoque_principal.setIdade_hoje(Float.parseFloat(sheet.getCell(16, i).getContents().replace(",",".")));
            estoque_principal.setCategoria(sheet.getCell(17, i).getContents());
            estoque_principal.setIma(Float.parseFloat(sheet.getCell(18, i).getContents().replace(",",".")));
            estoque_principal.setSituacao(sheet.getCell(19, i).getContents());
            estoque_principal.setMad_ton_ha(Float.parseFloat(sheet.getCell(20, i).getContents().replace(",",".")));
            estoque_principal.setCarv_ton_ha(Float.parseFloat(sheet.getCell(21, i).getContents().replace(",",".")));
            estoque_principal.setMdc_ha(Float.parseFloat(sheet.getCell(22, i).getContents().replace(",",".")));
            estoque_principal.setDensidade_madeira(Float.parseFloat(sheet.getCell(23, i).getContents().replace(",",".")));
            estoque_principal.setDensidade_carvao(Float.parseFloat(sheet.getCell(24, i).getContents().replace(",",".")));
            estoque_principal.setVol_mad_estimado(Float.parseFloat(sheet.getCell(25, i).getContents().replace(",",".")));
            estoque_principal.setVol_mad_transp(Float.parseFloat(sheet.getCell(26, i).getContents().replace(",",".")));
            estoque_principal.setVol_mad_balanco(Float.parseFloat(sheet.getCell(27, i).getContents().replace(",",".")));
            estoque_principal.setMdc_estimado(Float.parseFloat(sheet.getCell(28, i).getContents().replace(",",".")));
            estoque_principal.setMdc_transp(Float.parseFloat(sheet.getCell(29, i).getContents().replace(",",".")));
            estoque_principal.setMdc_balanco(Float.parseFloat(sheet.getCell(30, i).getContents().replace(",",".")));
            estoque_principal.setMad_ton_estimado(Float.parseFloat(sheet.getCell(31, i).getContents().replace(",",".")));
            estoque_principal.setMad_ton_transp(Float.parseFloat(sheet.getCell(32, i).getContents().replace(",",".")));
            estoque_principal.setMad_ton_balanco(Float.parseFloat(sheet.getCell(33, i).getContents().replace(",",".")));
            estoque_principal.setCarv_ton_estimado(Float.parseFloat(sheet.getCell(34, i).getContents().replace(",",".")));
            estoque_principal.setCarv_ton_transp(Float.parseFloat(sheet.getCell(35, i).getContents().replace(",",".")));
            estoque_principal.setCarv_ton_balanco(Float.parseFloat(sheet.getCell(36, i).getContents().replace(",",".")));

            estoque_principal.setData_estoque(data_estoque_principal.format(date));
            estoque_principal.setId_operario(ControlePrincipal.id_op);
            InserirEstoquePrincipalCtrl inserir = new InserirEstoquePrincipalCtrl(estoque_principal);
        }  
        System.out.println("Importado");  
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
            
            for (String coluna : colunas) {
                table.addCell(coluna);
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
                ControlePrincipal.vol_mad_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 29).toString());
                ControlePrincipal.vol_mad_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 30).toString());
                ControlePrincipal.mad_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 34).toString());
                ControlePrincipal.mad_ton_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 35).toString());
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
                ControlePrincipal.mdc_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 32).toString());
                ControlePrincipal.mdc_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 33).toString());
                ControlePrincipal.carv_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 37).toString());
                ControlePrincipal.carv_ton_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 38).toString());
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
        jButtonVoltar = new javax.swing.JButton();
        jSpinnerUPC = new javax.swing.JSpinner();
        jComboBoxCategoria = new javax.swing.JComboBox();
        jComboBoxMatGen = new javax.swing.JComboBox();
        jCheckBoxUTM = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButtonFiltrar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListFiltrar = new javax.swing.JList();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxProjeto = new javax.swing.JComboBox();
        jComboBoxFazenda = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRelatorioEstoquePrincipal = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabelAreaTotal = new javax.swing.JLabel();
        jLabelM3_ha = new javax.swing.JLabel();
        jLabelVolumeCarvaoTotal = new javax.swing.JLabel();
        jLabelVolumeMadeiraTotal = new javax.swing.JLabel();
        jLabelToneladaMadeiraTotal = new javax.swing.JLabel();
        jLabelToneladaCarvaoTotal = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemGerarPDF = new javax.swing.JMenuItem();
        jMenuItemCarregarExcel = new javax.swing.JMenuItem();
        jMenuItemLogout = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 780));

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                .addGap(53, 53, 53))
                            .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(94, 94, 94))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelIdTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addGap(37, 37, 37)
                .addComponent(jLabelIdTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 350));

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
        jCheckBoxUTM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUTMActionPerformed(evt);
            }
        });

        jLabel2.setText("UPC");

        jLabel3.setText("Categoria");

        jLabel4.setText("Mat. Genetico");

        jButtonFiltrar.setText("Filtrar");
        jButtonFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFiltrarActionPerformed(evt);
            }
        });

        jLabel5.setText("Filtrar por:");

        jListFiltrar.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "-", "estado", "bloco", "municipio", "fazenda", "projeto", "upc", "talhao", "area", "m3_ha", "material_genetico", "talhadia", "ano_rotacao", "data_plantio", "data_rotacao_1", "data_rotacao_2", "data_rotacao_3", "idade_hoje", "idade_corte1", "idade_corte2", "idade_corte3", "categoria", "situacao", "ima", "mdc_ha", "densidade_madeira", "densidade_carvao", "mad_ton_ha", "carv_ton_ha", "id_operario", "data_estoque", "vol_mad_estimado", "vol_mad_transp", "vol_mad_balanco", "mdc_estimado", "mdc_transp", "mdc_balanco", "mad_ton_estimado", "mad_ton_transp", "mad_ton_balanco", "carv_ton_estimado", "carv_ton_transp", "carv_ton_balanco", "madeira_praca", "madeira_forno", "rend_grav_estimado", "rend_grav_real", "fator_empilalhemto" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jListFiltrar);

        jLabel6.setText("PROJETOS");

        jLabel7.setText("FAZENDAS");

        jComboBoxProjeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxMatGen, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(53, 53, 53)
                        .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jCheckBoxUTM, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSpinnerUPC, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerUPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jCheckBoxUTM))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMatGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addComponent(jButtonFiltrar)
                .addGap(18, 18, 18)
                .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jTableRelatorioEstoquePrincipal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTableRelatorioEstoquePrincipal);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
        );

        jLabelAreaTotal.setText("Area Total: 0 m³");

        jLabelM3_ha.setText("Media Geral m³/ha: 0 m³");

        jLabelVolumeCarvaoTotal.setText("Volume carvão Total: 0 m³");

        jLabelVolumeMadeiraTotal.setText("Volume madeira total: 0 m³");

        jLabelToneladaMadeiraTotal.setText("Toneladas de madeira totais: 0 m³");

        jLabelToneladaCarvaoTotal.setText("Toneladas de carvão totais: 0 m³");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAreaTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelM3_ha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelVolumeCarvaoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelToneladaCarvaoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelVolumeMadeiraTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelToneladaMadeiraTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAreaTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelVolumeMadeiraTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLabelToneladaMadeiraTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelM3_ha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelVolumeCarvaoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLabelToneladaCarvaoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jMenu1.setText("Arquivo");

        jMenuItemGerarPDF.setText("Gerar PDF");
        jMenuItemGerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerarPDFActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemGerarPDF);

        jMenuItemCarregarExcel.setText("Carregar Excel");
        jMenuItemCarregarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCarregarExcelActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemCarregarExcel);

        jMenuItemLogout.setText("Logout");
        jMenuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemLogout);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        PreencherTabelaFiltrada();
        //JOptionPane.showMessageDialog(null, jListFiltrar.getSelectedValuesList());
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

    private void jMenuItemCarregarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCarregarExcelActionPerformed
        try {
            CarregarExcel();
        } catch (BiffException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemCarregarExcelActionPerformed

    private void jMenuItemGerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerarPDFActionPerformed
        try {
            //AlterarInfo();
            //SelecionarTalhao();
            GerarPDF();
        } catch (DocumentException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemGerarPDFActionPerformed

    private void jMenuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemLogoutActionPerformed

    private void jCheckBoxUTMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUTMActionPerformed
        // TODO add your handling code here:
        if(jCheckBoxUTM.isSelected()){
            jSpinnerUPC.setEnabled(false);
            jSpinnerUPC.setValue(0);
        }else{
            jSpinnerUPC.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBoxUTMActionPerformed

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
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JCheckBox jCheckBoxUTM;
    private javax.swing.JComboBox jComboBoxCategoria;
    private javax.swing.JComboBox jComboBoxFazenda;
    private javax.swing.JComboBox jComboBoxMatGen;
    private javax.swing.JComboBox jComboBoxProjeto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelAreaTotal;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelM3_ha;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelToneladaCarvaoTotal;
    private javax.swing.JLabel jLabelToneladaMadeiraTotal;
    private javax.swing.JLabel jLabelVolumeCarvaoTotal;
    private javax.swing.JLabel jLabelVolumeMadeiraTotal;
    private javax.swing.JList jListFiltrar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemCarregarExcel;
    private javax.swing.JMenuItem jMenuItemGerarPDF;
    private javax.swing.JMenuItem jMenuItemLogout;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinnerUPC;
    private javax.swing.JTable jTableRelatorioEstoquePrincipal;
    // End of variables declaration//GEN-END:variables
    
}
