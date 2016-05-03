/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.relatorios;

import Controle.ControleEstoquePrincipal;
import Controle.ControleFazenda;
import Controle.ControlePrincipal;
import Controle.estoqueprincipal.InserirEstoquePrincipalCtrl;
import Controle.fazenda.InserirFazendaCtrl;
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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Container;
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
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private float areaTotal;  
    private float m3_haMedia;  
    private float mdc_haMedia;
    private float vol_mad_transpTotal;
    private float vol_mad_estTotal;
    private float mdc_transpTotal;
    private float mdc_estTotal;
    private float mad_ton_transpTotal;
    private float mad_ton_estTotal;
    private float carv_ton_transpTotal;
    private float carv_ton_estTotal;
    //private float madeira_pracaTotal;
    //private float madeira_fornoTotal;
    //private float mad_ton_totTotal;
    //private float carv_ton_totTotal;   
    //private float rend_gravMediaPonderada;
    private String caminho="";    
    private JProgressBar progressBar;
    private JFrame f;
    private Container content;
    private Border border;      
    private int barra=0;
    private String dado;
    
    private String filtro_upc;
    private String filtro_cat;
    private String filtro_talhadia;
    private String filtro_matgen;
    private String filtro_proj;
    private String filtro_faz;
    
    /**
     * Creates new form GerarRelatorioEstoquePrincipal
     * @throws java.sql.SQLException
     */
    public GerarRelatorioEstoqueBasico() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        ChangeName();
        PreencherTabelaFiltrada();
        jButtonVoltar.setVisible(false);
    }  
    
    private void ChangeName(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
        _carregarCategoria();
    }
    
    private void _carregarCategoria(){
        jComboBoxCategoria.removeAllItems();
        jComboBoxCategoria.addItem("-");
        jComboBoxCategoria.addItem("Colheita");
        jComboBoxCategoria.addItem("Silvicultura");
        jComboBoxCategoria.addItem("UTM");  
        _carregarTalhadia();
    }
    
    private void _carregarTalhadia(){
        jComboBoxTalhadia.removeAllItems();
        jComboBoxTalhadia.addItem("-");
        jComboBoxTalhadia.addItem("1");
        jComboBoxTalhadia.addItem("2");
        jComboBoxTalhadia.addItem("3");  
        _carregarProjetos();
    }
    
    private void _carregarProjetos(){
        jComboBoxProjeto.removeAllItems();
        jComboBoxProjeto.addItem("-");
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
        jComboBoxFazenda.removeAllItems();
        jComboBoxFazenda.addItem("-");
        ConexaoBD con = ConexaoBD.getConexao();
        String query;
        ResultSet rs;
        String whereSql;
        query = "SELECT fazenda FROM estoque_principal";
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
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
            JOptionPane.showMessageDialog(null, "Erro ao carregar fazendas! "+ex);
        }        
        _carregarMaterialGenetico();
    }
    
    private void _carregarMaterialGenetico(){
        jComboBoxMatGen.removeAllItems();
        jComboBoxMatGen.addItem("-");
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
        //PreencherTabelaFiltrada();
    }
    
    private void PreencherTabelaFiltrada(){  
        //JOptionPane.showMessageDialog(null, "Size! " + jListFiltrar.getSelectedIndices().length + jListFiltrar.getModel().getSize());
        DecimalFormat decformat = new DecimalFormat("0.0");
        ConexaoBD con = ConexaoBD.getConexao();
        String query;
        ResultSet rs;
        String whereSql;
        dado = jListFiltrar.getSelectedValuesList().toString();
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
        if(!dado.contains("mdc_ha")){
            dado+= ", mdc_ha";
        }
        if(!dado.contains("vol_mad_estimado")){
            dado+= ", vol_mad_estimado";
        }
        if(!dado.contains("mdc_estimado")){
            dado+= ", mdc_estimado";
        }
        if(!dado.contains("mad_ton_estimado")){
            dado+= ", mad_ton_estimado";
        }
        if(!dado.contains("carv_ton_estimado")){
            dado+= ", carv_ton_estimado";
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
            if(tamanho<jListFiltrar.getModel().getSize()-1){
               //System.out.println(jListFiltrar.getSelectedValuesList().get(i));
               colunas[i] = (String) jListFiltrar.getSelectedValuesList().get(i);
            }else{
               //System.out.println(jListFiltrar.getModel().getElementAt(i+1));
               colunas[i] = (String) jListFiltrar.getModel().getElementAt(i+1);
            }
        }
        
        //Controle e definição das variaveis da clausula where like. Filtros
        if(jSpinnerUPC.getValue().equals(0)){
            if(jCheckBoxUTM.isSelected()){
                filtro_upc="UTM"; 
            }else{
                filtro_upc="";    
            }
        }else{
            filtro_upc=jSpinnerUPC.getValue().toString();
        }
        
        if(jComboBoxCategoria.getSelectedItem().equals("-")){
            filtro_cat="";
        }else{
            filtro_cat=jComboBoxCategoria.getSelectedItem().toString();
        }
        
        if(jComboBoxTalhadia.getSelectedItem().equals("-")){
            filtro_talhadia="";
        }else{
            filtro_talhadia=jComboBoxTalhadia.getSelectedItem().toString();
        }
        
        if(jComboBoxMatGen.getSelectedItem().equals("-")){
            filtro_matgen="";
        }else{
            filtro_matgen=jComboBoxMatGen.getSelectedItem().toString();
        }
        
        if(jComboBoxProjeto.getSelectedItem().equals("-")){
            filtro_proj="";
        }else{
            filtro_proj = String.valueOf(jComboBoxProjeto.getSelectedItem());
        }        
        
        if(jComboBoxFazenda.getSelectedItem().equals("-")){
            filtro_faz="";
        }else{
            filtro_faz=jComboBoxFazenda.getSelectedItem().toString();
        }
        
        //faz busca a partir dos filtros acima
        if(!filtro_upc.equals("") || !filtro_cat.equals("") || !filtro_talhadia.equals("") || !filtro_matgen.equals("") || !filtro_proj.equals("") || !filtro_faz.equals("")){
            whereSql = "where upc like '%"+filtro_upc+"%' and material_genetico like '%"+filtro_matgen+"%' and categoria like '%"+filtro_cat+"%' and talhadia like '%"+filtro_talhadia+"%' and projeto like '%"+filtro_proj+"%' and fazenda like '%"+filtro_faz+"%'";
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
        m3_haMedia=0;
        mdc_haMedia=0;
        vol_mad_transpTotal=0;
        vol_mad_estTotal=0;
        mdc_transpTotal=0;
        mdc_estTotal=0;
        mad_ton_transpTotal=0;
        mad_ton_estTotal=0;
        carv_ton_transpTotal=0;
        carv_ton_estTotal=0;
        ArrayList val = new ArrayList();
        ArrayList prod = new ArrayList();
        int cntr = 0;
        int cont=0;
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
                //area total
                if(rs.getString("area")!=null){
                    areaTotal += Float.valueOf(rs.getString("area"));
                }
                //media aritmetica  m3/ha
                if(rs.getString("m3_ha")!=null && Float.valueOf(rs.getString("m3_ha"))>0){
                    m3_haMedia += Float.valueOf(rs.getString("m3_ha"));
                    cont++;
                } 
                
                //media aritmetica mdc/ha
                if(rs.getString("mdc_ha")!=null && Float.valueOf(rs.getString("mdc_ha"))>0 ){
                    mdc_haMedia += Float.valueOf(rs.getString("mdc_ha")) * Float.valueOf(rs.getString("area"));
                }
                
                //Volumes totais de madeira
                if(rs.getString("vol_mad_transp")!=null){
                    vol_mad_transpTotal += Float.valueOf(rs.getString("vol_mad_transp"));
                }
                if(rs.getString("vol_mad_estimado")!=null){
                    vol_mad_estTotal += Float.valueOf(rs.getString("vol_mad_estimado"));
                }
                
                //Volumes totais de carvao
                if(rs.getString("mdc_transp")!=null){
                    mdc_transpTotal += Float.valueOf(rs.getString("mdc_transp"));
                }
                if(rs.getString("mdc_estimado")!=null){
                    mdc_estTotal += Float.valueOf(rs.getString("mdc_estimado"));
                }
                
                //Toneladas totais de madeira
                if(rs.getString("mad_ton_transp")!=null){
                    mad_ton_transpTotal += Float.valueOf(rs.getString("mad_ton_transp"));
                }
                if(rs.getString("mad_ton_estimado")!=null){
                    mad_ton_estTotal += Float.valueOf(rs.getString("mad_ton_estimado"));
                }
                
                //Toneladas totais de carvao
                if(rs.getString("carv_ton_transp")!=null){
                    carv_ton_transpTotal += Float.valueOf(rs.getString("carv_ton_transp"));
                }
                if(rs.getString("carv_ton_estimado")!=null){
                    carv_ton_estTotal += Float.valueOf(rs.getString("carv_ton_estimado"));
                }               
                
                //System.out.printf("\nCalculo m3ha: "+ m3_haMedia); 
                //adiciona a cada linha os valores de cada objeto coluna
                linhas.add(coluna);
            }
            
            //para media ponderada
            /*for(int i=0; i<prod.size();i++){
                System.out.printf("\nCalculo prod["+i+"]: "+ Integer.valueOf(prod.get(i).toString())); 
                m3_haMedia += Integer.valueOf(prod.get(i).toString());
                System.out.printf("\nMedia Ponderada: "+ m3_haMedia);
            }*/
            
            //System.out.printf("\nCalculo 2 m3ha: "+ m3_haMedia+ "linhas "+linhas.size());
            //m3_haMedia = m3_haMedia/linhas.size();
            mdc_haMedia = mdc_haMedia/areaTotal;
            m3_haMedia = m3_haMedia/cont;
            
            jLabelAreaTotal.setText("Area total: "+decformat.format(areaTotal)+" m³");
            jLabelMDC_ha.setText("Media ponderada mdc/ha: "+decformat.format(mdc_haMedia)+" m³");
            jLabelM3_ha.setText("Media geral m³/ha: "+decformat.format(m3_haMedia)+" m³");
            
            jLabelVolumeMadeiraTranspTotal.setText("Volume madeira transportada total: "+decformat.format(vol_mad_transpTotal)+" m³");
            jLabelVolumeMadeiraEstTotal.setText("Volume madeira estimada total: "+decformat.format(vol_mad_estTotal)+" m³");
            
            jLabelVolumeCarvaoTranspTotal.setText("Volume carvão transportado total: "+decformat.format(mdc_transpTotal)+" m³");
            jLabelVolumeCarvaoEstTotal.setText("Volume carvão estimado total: "+decformat.format(mdc_estTotal)+" m³");
            
            jLabelToneladaMadeiraTranspTotal.setText("Toneladas de madeira transportada totais: "+decformat.format(mad_ton_transpTotal)+" m³");
            jLabelToneladaMadeiraEstTotal.setText("Toneladas de madeira estimada totais: "+decformat.format(mad_ton_estTotal)+" m³");
            
            jLabelToneladaCarvaoTranspTotal.setText("Toneladas de carvão transportado totais: "+decformat.format(carv_ton_transpTotal)+" m³");
            jLabelToneladaCarvaoEstTotal.setText("Toneladas de carvão estimado totais: "+decformat.format(carv_ton_estTotal)+" m³");
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
    
    private void CarregarEstoqueExcel() throws BiffException, IOException{      
        ControlePrincipal.excel_cmd = true;
        DateFormat data_estoque_principal = new SimpleDateFormat("dd/MM/yyyy"); 
        Date date = new Date();    
        ControleEstoquePrincipal estoque_principal = new ControleEstoquePrincipal();
        //caminho="C:/Users/crist/Desktop/Nova pasta/Estoque de Madeira-Cadastro.xls";
        // pega o arquiivo do Excel  
        Workbook workbook = Workbook.getWorkbook(new File(caminho));  

        // pega a primeira planilha dentro do arquivo XLS 
        Sheet sheet = workbook.getSheet(0);  

        //Pega a quantidade de linhas da planilha   
        int total_linhas = sheet.getRows(); 
        //int total_linhas = 100;
        JOptionPane.showMessageDialog(null, "Lendo excel, total linhas... "+total_linhas);
        
        CarregandoDados();
        this.setVisible(false);
        //JOptionPane.showMessageDialog(null, "CarregandoDados...");  
        for (int i = 1; i < total_linhas; i++) {  
            barra = (int)((i*100)/total_linhas);
            /*for (int j = 0; j < 41; j++) {  
                //celula = sheet.getCell(j, i);//col, lin
                //System.out.print(sheet.getCell(j, i).getContents()+" - ");  
                System.out.println("["+j+"]: "+sheet.getCell(j, i).getContents());  
                //System.out.print(sheet.getCell(j, 1).getContents()+" - ");  
                //stringa4 += celula.getContents()+", ";               
            }*/
            //System.out.println(sheet.getCell(7, i).getContents()));              
            //System.out.println("importando "+barra);  
            //System.out.println(sheet.getCell(0, i).getContents());  
            
            // pega os valores das células como se numa matriz  
            /*estoque_principal.setId_estoque_p(sheet.getCell(0, i).getContents()); 
            estoque_principal.setEstado(sheet.getCell(0, i).getContents()); 
            estoque_principal.setBloco(sheet.getCell(2, i).getContents());
            estoque_principal.setMunicipio(sheet.getCell(3, i).getContents());
            estoque_principal.setFazenda(sheet.getCell(4, i).getContents());
            estoque_principal.setProjeto(sheet.getCell(5, i).getContents());*/
            
            ControlePrincipal.estado = sheet.getCell(0, i).getContents();
            ControlePrincipal.bloco = sheet.getCell(2, i).getContents();
            ControlePrincipal.municipio = sheet.getCell(3, i).getContents();
            ControlePrincipal.fazenda = sheet.getCell(4, i).getContents();
            ControlePrincipal.projeto = sheet.getCell(5, i).getContents();
            estoque_principal.setUpc(sheet.getCell(1, i).getContents());
            //ajusta dados do talhao            
            dado = sheet.getCell(8, i).getContents();
            dado = dado.replaceAll(" ", "");  
            dado = dado.replaceAll("[TABC-]", ""); 
            
            //System.out.println("["+i+"]:"+sheet.getCell(8, i).getContents()+"-"+dado);  
            if(!dado.equals("")){
                estoque_principal.setTalhao(Integer.parseInt(dado));
            }else{
                estoque_principal.setTalhao(0);
            }
            
            if(!sheet.getCell(9, i).getContents().equals("")){
                estoque_principal.setArea(Float.parseFloat(sheet.getCell(9, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setArea(0f);
            }
            
            estoque_principal.setMaterial_genetico(sheet.getCell(11, i).getContents());
            
            if(!sheet.getCell(22, i).getContents().equals("")){
                estoque_principal.setM3_ha(Float.parseFloat(sheet.getCell(22, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setM3_ha(0f);
            }
            
            //JOptionPane.showMessageDialog(null, "Verificando... "+sheet.getCell(10, i).getContents());
            if(sheet.getCell(10, i).getContents().equals("0") || sheet.getCell(10, i).getContents().equals("1")){
                estoque_principal.setTalhadia(Integer.parseInt(sheet.getCell(10, i).getContents()));
            }else{
                estoque_principal.setIma(0);
            }
            
            if(!sheet.getCell(7, i).getContents().equals("")){
                estoque_principal.setAno_rotacao(Integer.parseInt(sheet.getCell(7, i).getContents()));
            }else{
                estoque_principal.setAno_rotacao(0);
            }
            
            estoque_principal.setData_plantio(sheet.getCell(12, i).getContents());
            estoque_principal.setData_rotacao_1(sheet.getCell(13, i).getContents());
            estoque_principal.setData_rotacao_2("00/00/0000");
            estoque_principal.setData_rotacao_3("00/00/0000");
            
            if(!sheet.getCell(14, i).getContents().equals("")){
                estoque_principal.setIdade_corte1(Float.parseFloat(sheet.getCell(14, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setIdade_corte1(0f);
            }
            estoque_principal.setIdade_corte2(Float.parseFloat("0"));
            estoque_principal.setIdade_corte3(Float.parseFloat("0"));
            
            if(!sheet.getCell(15, i).getContents().equals("")){
                estoque_principal.setIdade_hoje(Float.parseFloat(sheet.getCell(15, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setIdade_hoje(0f);
            }
            
            estoque_principal.setConducao(sheet.getCell(16, i).getContents());
            estoque_principal.setCategoria(sheet.getCell(17, i).getContents());
            estoque_principal.setSituacao(sheet.getCell(18, i).getContents());
            if(!sheet.getCell(21, i).getContents().equals("")){
                estoque_principal.setIma(Float.parseFloat(sheet.getCell(21, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setIma(0f);
            }
                        
            if(!sheet.getCell(23, i).getContents().equals("")){
                estoque_principal.setMdc_ha(Float.parseFloat(sheet.getCell(23, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setMdc_ha(0f);
            }
            
            if(!sheet.getCell(19, i).getContents().equals("")){
                estoque_principal.setDensidade_madeira(Float.parseFloat(sheet.getCell(19, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setDensidade_madeira(0f);
            }
            
            if(!sheet.getCell(20, i).getContents().equals("")){
                estoque_principal.setDensidade_carvao(Float.parseFloat(sheet.getCell(20, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setDensidade_carvao(0f);
            }
            
            if(!sheet.getCell(24, i).getContents().equals("")){
                estoque_principal.setMad_ton_ha(Float.parseFloat(sheet.getCell(24, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setMad_ton_ha(0f);
            }
            
            if(!sheet.getCell(25, i).getContents().equals("")){
                estoque_principal.setCarv_ton_ha(Float.parseFloat(sheet.getCell(25, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setCarv_ton_ha(0f);
            }
            
            estoque_principal.setId_operario(ControlePrincipal.id_op);
            estoque_principal.setData_estoque(data_estoque_principal.format(date));
            
            if(!sheet.getCell(26, i).getContents().equals("")){
                estoque_principal.setVol_mad_estimado(Float.parseFloat(sheet.getCell(26, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setVol_mad_estimado(0f);
            }
                        
            if(!sheet.getCell(27, i).getContents().equals("")){
                estoque_principal.setVol_mad_transp(Float.parseFloat(sheet.getCell(27, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setVol_mad_transp(0f);
            }
            
            if(!sheet.getCell(28, i).getContents().equals("")){
                estoque_principal.setVol_mad_balanco(Float.parseFloat(sheet.getCell(28, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setVol_mad_balanco(0f);
            }
            
            if(!sheet.getCell(29, i).getContents().equals("")){
                estoque_principal.setMdc_estimado(Float.parseFloat(sheet.getCell(29, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setMdc_estimado(0f);
            }
            
            if(!sheet.getCell(30, i).getContents().equals("")){
                estoque_principal.setMdc_transp(Float.parseFloat(sheet.getCell(30, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setMdc_transp(0f);
            }
            
            if(!sheet.getCell(31, i).getContents().equals("")){
                estoque_principal.setMdc_balanco(Float.parseFloat(sheet.getCell(31, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setMdc_balanco(0f);
            }
            
            if(!sheet.getCell(32, i).getContents().equals("")){
                estoque_principal.setMad_ton_estimado(Float.parseFloat(sheet.getCell(32, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setMad_ton_estimado(0f);
            }
                        
            if(!sheet.getCell(33, i).getContents().equals("")){
                estoque_principal.setMad_ton_transp(Float.parseFloat(sheet.getCell(33, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setMad_ton_transp(0f);
            }
            
            if(!sheet.getCell(34, i).getContents().equals("")){
                estoque_principal.setMad_ton_balanco(Float.parseFloat(sheet.getCell(35, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setMad_ton_balanco(0f);
            }
            
            if(!sheet.getCell(35, i).getContents().equals("")){
                estoque_principal.setCarv_ton_estimado(Float.parseFloat(sheet.getCell(35, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setCarv_ton_estimado(0f);
            }
            
            if(!sheet.getCell(36, i).getContents().equals("")){
                estoque_principal.setCarv_ton_transp(Float.parseFloat(sheet.getCell(36, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setCarv_ton_transp(0f);
            }
            
            if(!sheet.getCell(37, i).getContents().equals("")){
                estoque_principal.setCarv_ton_balanco(Float.parseFloat(sheet.getCell(37, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setCarv_ton_balanco(0f);
            }
            
            estoque_principal.setMadeira_praca(Float.parseFloat("0"));
            estoque_principal.setMadeira_forno(Float.parseFloat("0"));
            
            if(!sheet.getCell(38, i).getContents().equals("")){
                estoque_principal.setRend_grav_estimado(Float.parseFloat(sheet.getCell(38, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setRend_grav_estimado(0f);
            }
            
            if(!sheet.getCell(27, i).getContents().equals("")){
                estoque_principal.setRend_grav_real(Float.parseFloat(sheet.getCell(39, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setRend_grav_real(0f);
            }
            
            if(!sheet.getCell(40, i).getContents().equals("")){
                estoque_principal.setFator_empilalhemto(Float.parseFloat(sheet.getCell(40, i).getContents().replace(",",".")));
            }else{
                estoque_principal.setFator_empilalhemto(0f);
            }
            //System.out.println("importando "+barra);
            progressBar.setValue(barra);
            //JOptionPane.showMessageDialog(null, "importando "+barra); 
            //try { Thread.sleep (1000); } catch (InterruptedException ex) {}
            InserirEstoquePrincipalCtrl inserir = new InserirEstoquePrincipalCtrl(estoque_principal);
        }  
        //System.out.println("Importado");
        progressBar.setValue(100);
        JOptionPane.showMessageDialog(null, "Importação Completa!"); 
        ControlePrincipal.excel_cmd = false;
        f.dispose(); 
        PreencherTabelaFiltrada();
        this.setVisible(true);            
    }    
            
    private void CarregandoDados(){
        f = new JFrame("Importando dados");
        f.setSize(300, 100);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        content = f.getContentPane();
        border = BorderFactory.createTitledBorder("Inserindo dados excel...");
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setBorder(border);        
        progressBar.setStringPainted(true);
        progressBar.setValue(barra);
        content.add(progressBar, BorderLayout.NORTH);     
        /*progressBar.setValue(50);
        //try { Thread.sleep (5000); } catch (InterruptedException ex) {}  
        //progressBar.setValue(75);        
        while (barra <= 100) {
            barra++;
            //barra = (int)((barra*100)/500);
            System.out.println("importando "+barra); 
            progressBar.setValue(barra);
            try { Thread.sleep (1000); } catch (InterruptedException ex) {}
        }*/     
    }
    
    /**
     * Carrega dados do excel com dados referente a fazendas
     * @throws BiffException
     * @throws IOException 
     */
    private void CarregarFazendasExcel() throws BiffException, IOException{
        ControlePrincipal.excel_cmd=true;
        DateFormat data_registro = new SimpleDateFormat("dd/MM/yyyy"); 
        Date date = new Date();    
        ControleFazenda fazenda = new ControleFazenda();     
        //caminho="C:/Users/crist/Desktop/Nova pasta/Estoque de Madeira-Cadastro.xls";
        
        /* pega o arquiivo do Excel */  
        Workbook workbook = Workbook.getWorkbook(new File(caminho));  

        /* pega a primeira planilha dentro do arquivo XLS */  
        Sheet sheet = workbook.getSheet(0);  

        //Pega a quantidade de linhas da planilha   
        int total_linhas = sheet.getRows();  
        //JOptionPane.showMessageDialog(null, "Lendo excel, linhas... "+total_linhas);
        System.out.println("Total linhas: "+total_linhas);
        Cell celula = null;  
        
        ArrayList faz = new ArrayList();
        ArrayList proj = new ArrayList();
        int cntr=0;
        for (int i = 1; i < total_linhas; i++) {  
            if(sheet.getCell(4, i).getContents()!=null){
                System.out.println("\nval vazio: "+ faz.isEmpty()+" tam: "+faz.size()); 
                if(faz.isEmpty() && proj.isEmpty()){
                    faz.add(sheet.getCell(4, i).getContents());
                    proj.add(sheet.getCell(5, i).getContents());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>Add faz["+0+"]: "+ faz.get(0)+" e proj["+0+"]: "+ proj.get(0)); 
                    
                    fazenda.setEstado(sheet.getCell(0, i).getContents());                        
                    fazenda.setBloco(sheet.getCell(2, i).getContents());                      
                    fazenda.setMunicipio(sheet.getCell(3, i).getContents());            
                    fazenda.setFazenda(sheet.getCell(4, i).getContents());            
                    fazenda.setProjeto(sheet.getCell(5, i).getContents());
                    InserirFazendaCtrl inserir = new InserirFazendaCtrl(fazenda);
                }else {
                    for(int j=0; j<faz.size(); j++){
                        System.out.println("Lendo faz[excel]: "+ sheet.getCell(4, i).getContents()+" = faz["+j+"]: "+faz.get(j)+" e proj[excel]: "+ sheet.getCell(5, i).getContents()+" = proj["+j+"]: "+proj.get(j)+"?"); 
                        if((sheet.getCell(4, i).getContents().equals(faz.get(j)))&&(sheet.getCell(5, i).getContents().equals(proj.get(j)))){
                            cntr++;
                        }
                    }
                    if(cntr==0){                            
                        faz.add(sheet.getCell(4, i).getContents());
                        proj.add(sheet.getCell(5, i).getContents());
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>Add nova faz: "+sheet.getCell(4, i).getContents()+ ", novo proj: "+sheet.getCell(5, i).getContents());
                        
                        //insert
                        //fazenda.setId_fazenda(sheet.getCell(0, i).getContents());  
                        fazenda.setEstado(sheet.getCell(0, i).getContents());                        
                        fazenda.setBloco(sheet.getCell(2, i).getContents());                      
                        fazenda.setMunicipio(sheet.getCell(3, i).getContents());            
                        fazenda.setFazenda(sheet.getCell(4, i).getContents());            
                        fazenda.setProjeto(sheet.getCell(5, i).getContents());
                        InserirFazendaCtrl inserir = new InserirFazendaCtrl(fazenda);
                    }
                    cntr=0;
                }
            }
            /* pega os valores das células como se numa matriz */  
            /*for (int j = 0; j < 41; j++) {  
                //celula = sheet.getCell(j, i);//col, lin
                //System.out.print(sheet.getCell(j, i).getContents()+" - ");  
                System.out.println("["+j+"]: "+sheet.getCell(j, i).getContents());  
                //System.out.print(sheet.getCell(j, 1).getContents()+" - ");  
                //stringa4 += celula.getContents()+", ";               
            }*/
            //System.out.println(sheet.getCell(7, i).getContents()));              
            //System.out.println("importando "+i);  
            //System.out.println(sheet.getCell(29, i).getContents());                
        }  
        System.out.println("Importado");  
        ControlePrincipal.excel_cmd=false;
    }
    
    private int SelecionaCodigoEstado(String UF){
        int cod=0;
        switch(UF){
                case "AC":
                    cod = 1;
                    break;
                case "AL":
                    cod = 2;
                    break;
                case "AP":
                    cod = 3;
                    break;
                case "AM":
                    cod = 4;
                    break;
                case "BA":
                    cod = 5;
                    break;
                case "CE":
                    cod = 6;
                    break;
                case "DF":
                    cod = 7;
                    break;
                case "ES":
                    cod = 8;
                    break;
                case "GO":
                    cod = 9;
                    break;
                case "MA":
                    cod = 10;
                    break;
                case "MT":
                    cod = 11;
                    break;
                case "MS":
                    cod = 12;
                    break;
                case "MG":
                    cod = 13;
                    break;
                case "PA":
                    cod = 14;
                    break;
                case "PB":
                    cod = 15;
                    break;
                case "PR":
                    cod = 16;
                    break;
                case "PE":
                    cod = 17;
                    break;
                case "PI":
                    cod = 18;
                    break;
                case "RJ":
                    cod = 19;
                    break;
                case "RN":
                    cod = 20;
                    break;
                case "RS":
                    cod = 21;
                    break;
                case "RO":
                    cod = 22;
                    break;
                case "RR":
                    cod = 23;
                    break;
                case "SC":
                    cod = 24;
                    break;
                case "SE":
                    cod = 25;
                    break;
                case "SP":
                    cod = 26;
                    break;
                case "TO":
                    cod = 27;
                    break;
            }
        return cod;
    }
    
    private int SelecionaCodigoBloco(String BL){
        int cod=0;
        switch(BL){
                case "Norte":
                    cod = 1;
                    break;
                case "Sul":
                    cod = 2;
                    break;
                case "Leste":
                    cod = 3;
                    break;
                case "Oeste":
                    cod = 4;
                    break;                
            }
        return cod;
    }
    
    private void GerarPDF() throws DocumentException, FileNotFoundException {
        try {
            /*if(jTableRelatorioEstoquePrincipal.getSelectedRow()>=0) { 
                int linha = jTableRelatorioEstoquePrincipal.getSelectedRow();
                Document document = new Document(PageSize.A4, 10, 10, 10, 10);
                //System.out.println(new File(".").getAbsolutePath());
                String arquivo = new File("RelatorioFaz.").getAbsolutePath()+"pdf";            
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
                document.open();
                Font font = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                //String titulo = jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString()+ " "+jTableRelatorioEstoquePrincipal.getValueAt(linha, 2).toString()+"-"+jTableRelatorioEstoquePrincipal.getValueAt(linha, 0).toString();
                String titulo = "";
                if(!filtro_matgen.equals("")){
                    titulo = "do material genetico "+filtro_matgen;
                }
                Paragraph pgt = new Paragraph("Relatorio "+titulo, font);
                pgt.setAlignment(Element.ALIGN_CENTER);
                document.add(pgt);
                document.add(new Paragraph(" "));
                //System.out.println("Colunas "+colunas.length);
                //for(int i=4; i<colunas.length; i++){
                    //document.add(new Paragraph(colunas[i]+": "+jTableRelatorioEstoquePrincipal.getValueAt(linha, i).toString()));
                //}           
                //document.add(new Paragraph("Municipio: "+jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString()));

                PdfPTable table = new PdfPTable(colunas.length);
                // Definindo uma fonte, com tamanho 20 e negrito
                PdfPCell header = new PdfPCell(new Paragraph("Relatorio "+titulo,font));
                header.setColspan(colunas.length);
                table.addCell(header);
                table.setWidthPercentage(100.0f);
                table.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
                //System.out.println("Tamanho: "+linhas.size());
                font = new Font(FontFamily.TIMES_ROMAN, 4, Font.NORMAL);
                for (String coluna : colunas) {
                    table.addCell(new Paragraph(coluna,font));
                }

                //Linha selecionada
                //for(int j=0;j<colunas.length;j++){//coluna
                    //table.addCell(jTableRelatorioEstoquePrincipal.getValueAt(linha, j).toString());
                    //table.addCell(new Paragraph(jTableRelatorioEstoquePrincipal.getValueAt(linha, j).toString(),font));
                //}

                //varias linhas
                for(int i=0;i<linhas.size();i++){//linha
                    for(int j=0;j<colunas.length;j++){//coluna
                        //table.addCell(jTableRelatorioEstoquePrincipal.getValueAt(i, j).toString());
                        table.addCell(new Paragraph(jTableRelatorioEstoquePrincipal.getValueAt(i, j).toString(),font));
                    }
                }

                document.add(table);
                
                document.add(new Paragraph("Relatorio Geral"));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(jLabelAreaTotal.getText()));
                document.add(new Paragraph(jLabelM3_ha.getText()));
                document.add(new Paragraph(jLabelMDC_ha.getText()));

                document.add(new Paragraph(jLabelVolumeMadeiraEstTotal.getText()));
                document.add(new Paragraph(jLabelVolumeMadeiraTranspTotal.getText()));
                document.add(new Paragraph(jLabelVolumeCarvaoEstTotal.getText()));
                document.add(new Paragraph(jLabelVolumeCarvaoTranspTotal.getText()));

                document.add(new Paragraph(jLabelToneladaMadeiraEstTotal.getText()));
                document.add(new Paragraph(jLabelToneladaMadeiraTranspTotal.getText()));
                document.add(new Paragraph(jLabelToneladaCarvaoEstTotal.getText()));
                document.add(new Paragraph(jLabelToneladaCarvaoTranspTotal.getText()));
                document.close();
                JOptionPane.showMessageDialog(null, "PDF: "+arquivo+" gerado!");
            }else {
                //Document document = new Document(PageSize.A4, 72, 72, 72, 72);
                Rectangle rect = new Rectangle(1200, 595);
                Document document = new Document(rect);
                String arquivo = new File("RelatorioGeral.").getAbsolutePath()+"pdf";
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
                document.open();
                document.add(new Paragraph("Relatorio Geral"));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(jLabelAreaTotal.getText()));
                document.add(new Paragraph(jLabelM3_ha.getText()));
                document.add(new Paragraph(jLabelMDC_ha.getText()));

                document.add(new Paragraph(jLabelVolumeMadeiraEstTotal.getText()));
                document.add(new Paragraph(jLabelVolumeMadeiraTranspTotal.getText()));
                document.add(new Paragraph(jLabelVolumeCarvaoEstTotal.getText()));
                document.add(new Paragraph(jLabelVolumeCarvaoTranspTotal.getText()));

                document.add(new Paragraph(jLabelToneladaMadeiraEstTotal.getText()));
                document.add(new Paragraph(jLabelToneladaMadeiraTranspTotal.getText()));
                document.add(new Paragraph(jLabelToneladaCarvaoEstTotal.getText()));
                document.add(new Paragraph(jLabelToneladaCarvaoTranspTotal.getText()));

                document.close();
                JOptionPane.showMessageDialog(null, "PDF: "+arquivo+" gerado!");
            }*/
            int linha = jTableRelatorioEstoquePrincipal.getSelectedRow();
                Document document = new Document(PageSize.A4, 10, 10, 10, 10);
                //System.out.println(new File(".").getAbsolutePath());
                String arquivo = new File("RelatorioFaz.").getAbsolutePath()+"pdf";            
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
                document.open();
                Font font = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                //String titulo = jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString()+ " "+jTableRelatorioEstoquePrincipal.getValueAt(linha, 2).toString()+"-"+jTableRelatorioEstoquePrincipal.getValueAt(linha, 0).toString();
                String titulo = "Relatorio";                
                Paragraph pgt = new Paragraph(titulo, font);
                pgt.setAlignment(Element.ALIGN_CENTER);
                document.add(pgt);
                document.add(new Paragraph(" "));
                if(!filtro_matgen.equals("")){
                    titulo = "Material genetico "+filtro_matgen;
                }
                PdfPTable table = new PdfPTable(colunas.length);
                // Definindo uma fonte, com tamanho 20 e negrito
                PdfPCell header = new PdfPCell(new Paragraph(titulo,font));
                header.setColspan(colunas.length);
                table.addCell(header);
                table.setWidthPercentage(100.0f);
                table.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
                //System.out.println("Tamanho: "+linhas.size());
                font = new Font(FontFamily.TIMES_ROMAN, 4, Font.NORMAL);
                for (String coluna : colunas) {
                    table.addCell(new Paragraph(coluna,font));
                }

                //varias linhas
                for(int i=0;i<linhas.size();i++){//linha
                    for(int j=0;j<colunas.length;j++){//coluna
                        //table.addCell(jTableRelatorioEstoquePrincipal.getValueAt(i, j).toString());
                        table.addCell(new Paragraph(jTableRelatorioEstoquePrincipal.getValueAt(i, j).toString(),font));
                    }
                }

                document.add(table);
                
                document.add(new Paragraph(" "));
                
                font = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                document.add(new Paragraph("Dados Totais",font));
                font = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
                document.add(new Paragraph(jLabelAreaTotal.getText(),font));
                document.add(new Paragraph(jLabelM3_ha.getText(),font));
                document.add(new Paragraph(jLabelMDC_ha.getText(),font));

                document.add(new Paragraph(jLabelVolumeMadeiraEstTotal.getText(),font));
                document.add(new Paragraph(jLabelVolumeMadeiraTranspTotal.getText(),font));
                document.add(new Paragraph(jLabelVolumeCarvaoEstTotal.getText(),font));
                document.add(new Paragraph(jLabelVolumeCarvaoTranspTotal.getText(),font));

                document.add(new Paragraph(jLabelToneladaMadeiraEstTotal.getText(),font));
                document.add(new Paragraph(jLabelToneladaMadeiraTranspTotal.getText(),font));
                document.add(new Paragraph(jLabelToneladaCarvaoEstTotal.getText(),font));
                document.add(new Paragraph(jLabelToneladaCarvaoTranspTotal.getText(),font));
                document.close();
                JOptionPane.showMessageDialog(null, "PDF: "+arquivo+" gerado!");
        } catch (Exception ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Erro ao gerar pdf: "+ex);
            JOptionPane.showMessageDialog(null, "Erro ao gerar pdf: "+ex);
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
                        
            switch (ControlePrincipal.tipo_u) {
                case "op_m":
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
                    }   break;
                case "op_c":
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
                }   break;
                case "op_s":
                    break;
            }            
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        //this.setVisible(false);
    }
    
    private void VoltarMenu(){        
        switch (ControlePrincipal.tipo_u) {
            case "op_m":
                try {
                    new GerenciarMadeiraPraca().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "op_c":
                try {
                    new GerenciarCarvaoForno().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            }   break;
            case "op_s":
                try {
                    new GerenciarUsuarios().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            }   break;
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
        jLabel8 = new javax.swing.JLabel();
        jComboBoxTalhadia = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRelatorioEstoquePrincipal = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabelAreaTotal = new javax.swing.JLabel();
        jLabelM3_ha = new javax.swing.JLabel();
        jLabelVolumeCarvaoTranspTotal = new javax.swing.JLabel();
        jLabelVolumeMadeiraTranspTotal = new javax.swing.JLabel();
        jLabelToneladaMadeiraTranspTotal = new javax.swing.JLabel();
        jLabelToneladaCarvaoTranspTotal = new javax.swing.JLabel();
        jLabelMDC_ha = new javax.swing.JLabel();
        jLabelVolumeMadeiraEstTotal = new javax.swing.JLabel();
        jLabelVolumeCarvaoEstTotal = new javax.swing.JLabel();
        jLabelToneladaMadeiraEstTotal = new javax.swing.JLabel();
        jLabelToneladaCarvaoEstTotal = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemGerarPDF = new javax.swing.JMenuItem();
        jMenuItemCarregarEstoqueExcel = new javax.swing.JMenuItem();
        jMenuItemCarregarFazendaExcel = new javax.swing.JMenuItem();
        jMenuItemLogout = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemMadeiraPraca = new javax.swing.JMenuItem();
        jMenuItemCarvao = new javax.swing.JMenuItem();

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
            String[] strings = { "-", "estado", "bloco", "municipio", "fazenda", "projeto", "upc", "talhao", "area", "m3_ha", "material_genetico", "talhadia", "ano_rotacao", "data_plantio", "data_rotacao_1", "data_rotacao_2", "data_rotacao_3", "idade_hoje", "idade_corte1", "idade_corte2", "idade_corte3", "conducao", "categoria", "situacao", "ima", "mdc_ha", "densidade_madeira", "densidade_carvao", "mad_ton_ha", "carv_ton_ha", "id_operario", "data_estoque", "vol_mad_estimado", "vol_mad_transp", "vol_mad_balanco", "mdc_estimado", "mdc_transp", "mdc_balanco", "mad_ton_estimado", "mad_ton_transp", "mad_ton_balanco", "carv_ton_estimado", "carv_ton_transp", "carv_ton_balanco", "madeira_praca", "madeira_forno", "rend_grav_estimado", "rend_grav_real", "fator_empilalhemto" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jListFiltrar);

        jLabel6.setText("PROJETOS");

        jLabel7.setText("FAZENDAS");

        jLabel8.setText("Talhadia");

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
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(53, 53, 53)
                        .addComponent(jComboBoxTalhadia, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxTalhadia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(13, 13, 13)))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabelAreaTotal.setFont(jLabelAreaTotal.getFont());
        jLabelAreaTotal.setText("Area Total: 0 m³");
        jLabelAreaTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelM3_ha.setFont(jLabelM3_ha.getFont());
        jLabelM3_ha.setText("Media Geral m³/ha: 0 m³");
        jLabelM3_ha.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelVolumeCarvaoTranspTotal.setFont(jLabelVolumeCarvaoTranspTotal.getFont());
        jLabelVolumeCarvaoTranspTotal.setText("Volume carvão transportado total: 0 m³");
        jLabelVolumeCarvaoTranspTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelVolumeMadeiraTranspTotal.setFont(jLabelVolumeMadeiraTranspTotal.getFont());
        jLabelVolumeMadeiraTranspTotal.setText("Volume madeira transportada total: 0 m³");
        jLabelVolumeMadeiraTranspTotal.setMaximumSize(new java.awt.Dimension(100, 15));
        jLabelVolumeMadeiraTranspTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelToneladaMadeiraTranspTotal.setFont(jLabelToneladaMadeiraTranspTotal.getFont());
        jLabelToneladaMadeiraTranspTotal.setText("Toneladas de madeira transportada totais: 0 m³");
        jLabelToneladaMadeiraTranspTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelToneladaCarvaoTranspTotal.setFont(jLabelToneladaCarvaoTranspTotal.getFont());
        jLabelToneladaCarvaoTranspTotal.setText("Toneladas de carvão transportado totais: 0 m³");
        jLabelToneladaCarvaoTranspTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelMDC_ha.setFont(jLabelMDC_ha.getFont());
        jLabelMDC_ha.setText("Media geral mdc/ha: 0 m³");
        jLabelMDC_ha.setMaximumSize(new java.awt.Dimension(100, 15));
        jLabelMDC_ha.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelVolumeMadeiraEstTotal.setFont(jLabelVolumeMadeiraEstTotal.getFont());
        jLabelVolumeMadeiraEstTotal.setText("Volume madeira estimada total: 0 m³");
        jLabelVolumeMadeiraEstTotal.setMaximumSize(new java.awt.Dimension(100, 15));
        jLabelVolumeMadeiraEstTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelVolumeCarvaoEstTotal.setFont(jLabelVolumeCarvaoEstTotal.getFont());
        jLabelVolumeCarvaoEstTotal.setText("Volume carvão estimado total: 0 m³");
        jLabelVolumeCarvaoEstTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelToneladaMadeiraEstTotal.setFont(jLabelToneladaMadeiraEstTotal.getFont());
        jLabelToneladaMadeiraEstTotal.setText("Toneladas de madeira estimada totais: 0 m³");
        jLabelToneladaMadeiraEstTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelToneladaCarvaoEstTotal.setFont(jLabelToneladaCarvaoEstTotal.getFont());
        jLabelToneladaCarvaoEstTotal.setText("Toneladas de carvão estimado totais: 0 m³");
        jLabelToneladaCarvaoEstTotal.setPreferredSize(new java.awt.Dimension(100, 15));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelM3_ha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelAreaTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelMDC_ha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVolumeMadeiraEstTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelVolumeMadeiraTranspTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addComponent(jLabelVolumeCarvaoEstTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelVolumeCarvaoTranspTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelToneladaCarvaoEstTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelToneladaMadeiraEstTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelToneladaMadeiraTranspTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelToneladaCarvaoTranspTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVolumeMadeiraTranspTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelToneladaMadeiraTranspTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAreaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVolumeMadeiraEstTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelToneladaMadeiraEstTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMDC_ha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelToneladaCarvaoTranspTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelVolumeCarvaoTranspTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelM3_ha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVolumeCarvaoEstTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelToneladaCarvaoEstTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabelToneladaCarvaoEstTotal, jLabelToneladaCarvaoTranspTotal, jLabelToneladaMadeiraEstTotal, jLabelToneladaMadeiraTranspTotal, jLabelVolumeCarvaoEstTotal, jLabelVolumeCarvaoTranspTotal, jLabelVolumeMadeiraEstTotal, jLabelVolumeMadeiraTranspTotal});

        jMenu1.setText("Arquivo");

        jMenuItemGerarPDF.setText("Gerar PDF");
        jMenuItemGerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerarPDFActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemGerarPDF);

        jMenuItemCarregarEstoqueExcel.setText("Carregar Estoque Excel");
        jMenuItemCarregarEstoqueExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCarregarEstoqueExcelActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemCarregarEstoqueExcel);

        jMenuItemCarregarFazendaExcel.setText("Carregar Fazenda Excel");
        jMenuItemCarregarFazendaExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCarregarFazendaExcelActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemCarregarFazendaExcel);

        jMenuItemLogout.setText("Logout");
        jMenuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemLogout);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Relatorios");

        jMenuItemMadeiraPraca.setText("Madeira/Praça");
        jMenuItemMadeiraPraca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMadeiraPracaActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemMadeiraPraca);

        jMenuItemCarvao.setText("Carvão");
        jMenuItemCarvao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCarvaoActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemCarvao);

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
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        PreencherTabelaFiltrada();        
        _carregarCategoria();
        //JOptionPane.showMessageDialog(null, jListFiltrar.getSelectedValuesList());
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

    private void jMenuItemCarregarEstoqueExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCarregarEstoqueExcelActionPerformed
        try {
            JFileChooser abrir = new JFileChooser();   
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Excel .xls","xls");   
            abrir.addChoosableFileFilter(filter);  
            abrir.setAcceptAllFileFilterUsed(false);              
            int retorno = abrir.showOpenDialog(null);              
            if (retorno==JFileChooser.APPROVE_OPTION) { 
                
                caminho = abrir.getSelectedFile().getAbsolutePath();  
                CarregarEstoqueExcel();
            }
            //try { Thread.sleep (1000); } catch (InterruptedException ex) {}
            //CarregarEstoqueExcel();
        } catch (BiffException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Carregar Estoque Excel Error: "+ex);
            JOptionPane.showMessageDialog(null, "Carregar Estoque Excel Error: "+ex);
        } catch (IOException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Carregar Estoque Excel Error: "+ex);
            JOptionPane.showMessageDialog(null, "Carregar Estoque Excel Error: "+ex);
        }        
    }//GEN-LAST:event_jMenuItemCarregarEstoqueExcelActionPerformed

    private void jMenuItemGerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerarPDFActionPerformed
        try {
            //AlterarInfo();
            //SelecionarTalhao();
            GerarPDF();
        } catch (DocumentException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gerar PDF Error: "+ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gerar PDF Error: "+ex);
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

    private void jMenuItemCarregarFazendaExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCarregarFazendaExcelActionPerformed
        
            try {
                JFileChooser abrir = new JFileChooser();   
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Excel .xls","xls");   
                abrir.addChoosableFileFilter(filter);  
                abrir.setAcceptAllFileFilterUsed(false);              
                int retorno = abrir.showOpenDialog(null);              
                if (retorno==JFileChooser.APPROVE_OPTION) { 
                    caminho = abrir.getSelectedFile().getAbsolutePath();  
                    CarregarFazendasExcel();
                }
            } catch (BiffException ex) {
                Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Carregar Fazenda Excel Error: "+ex);
            } catch (IOException ex) {
                Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Carregar Fazenda Excel Error: "+ex);
            }
    }//GEN-LAST:event_jMenuItemCarregarFazendaExcelActionPerformed

    private void jMenuItemMadeiraPracaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMadeiraPracaActionPerformed
        try {
            new GerarRelatorioMadeiraPraca().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemMadeiraPracaActionPerformed

    private void jMenuItemCarvaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCarvaoActionPerformed
        try {
            new GerarRelatorioCarvao().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemCarvaoActionPerformed

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
    private javax.swing.JComboBox jComboBoxTalhadia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelAreaTotal;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelM3_ha;
    private javax.swing.JLabel jLabelMDC_ha;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelToneladaCarvaoEstTotal;
    private javax.swing.JLabel jLabelToneladaCarvaoTranspTotal;
    private javax.swing.JLabel jLabelToneladaMadeiraEstTotal;
    private javax.swing.JLabel jLabelToneladaMadeiraTranspTotal;
    private javax.swing.JLabel jLabelVolumeCarvaoEstTotal;
    private javax.swing.JLabel jLabelVolumeCarvaoTranspTotal;
    private javax.swing.JLabel jLabelVolumeMadeiraEstTotal;
    private javax.swing.JLabel jLabelVolumeMadeiraTranspTotal;
    private javax.swing.JList jListFiltrar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemCarregarEstoqueExcel;
    private javax.swing.JMenuItem jMenuItemCarregarFazendaExcel;
    private javax.swing.JMenuItem jMenuItemCarvao;
    private javax.swing.JMenuItem jMenuItemGerarPDF;
    private javax.swing.JMenuItem jMenuItemLogout;
    private javax.swing.JMenuItem jMenuItemMadeiraPraca;
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
