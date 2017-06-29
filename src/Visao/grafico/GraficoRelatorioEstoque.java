/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.grafico;

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
import Visao.relatorios.GerarRelatorioCarvao;
import Visao.relatorios.GerarRelatorioCarvaoExpedido;
import Visao.relatorios.GerarRelatorioEstoqueBasico;
import Visao.relatorios.GerarRelatorioForno;
import Visao.relatorios.GerarRelatorioMadeiraPraca;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Cristiano GD
 */
public class GraficoRelatorioEstoque extends javax.swing.JFrame {

    private ArrayList linhas;
    private String[] colunas;
    private ArrayList list_fazendas;
    private ArrayList list_mat_gen;
    private ArrayList list_projeto;
    private ArrayList list_talhadia;
    
    private int id;
    private String fazenda;
    private String projeto;
    private String material_genetico;
    private float areaTotal;  
    private float m3_haMedia;  
    private float mdc_haMedia;
    private float vol_mad_pracaTotal;
    private float vol_carv_pracaTotal;
    private float vol_mad_estTotal;
    private float vol_mad_transpTotal;
    private float vol_mad_procTotal;
    private float mad_ton_estTotal;
    private float mad_ton_transpTotal;
    private float mdc_estTotal;
    private float mdc_prodTotal;
    private float mdc_transpTotal;
    private float carv_ton_estTotal;
    private float carv_ton_prodTotal;
    private float carv_ton_transpTotal;    
    private float madeira_fornoTotal;    
    private float media_idades;
    private int cont;
    private int TAMANHO=20;
    
    private String filtro_talhadia;
    private String filtro_matgen;
    private String filtro_proj;
    private String filtro_faz;
         
    /**
     * Creates new form GraficoRelatorioEstoque
     */
    public GraficoRelatorioEstoque() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        jButtonGerarPDF.setVisible(false);
        ChangeName();
    }
    
    private CategoryDataset createDataset() { 
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        //dataset.addValue(1000.0,"01/2012","Mês/Ano");
        for(int i=0; i<ControlePrincipal.valor_grafico.length;i++){
            dataset.addValue(ControlePrincipal.valor_grafico[i],ControlePrincipal.info_grafico[i],ControlePrincipal.tipo_grafico);
        }
        return dataset; 
    }
    
    public void criaGrafico() {
        CategoryDataset cds = createDataset();
        String titulo = "Gráfico de "+ControlePrincipal.tipo_grafico;
        String eixoy = "Valores";
        String txt_legenda = "Ledenda:";
        boolean legenda = true;
        boolean tooltips = true;
        boolean urls = true;
        JFreeChart graf = ChartFactory.createBarChart3D(titulo, txt_legenda, eixoy, cds, PlotOrientation.VERTICAL, legenda, tooltips, urls);
        ChartPanel myChartPanel = new ChartPanel(graf, true);
        myChartPanel.setSize(jPaneGraficoRE.getWidth(),jPaneGraficoRE.getHeight());
        myChartPanel.setVisible(true); 
        jPaneGraficoRE.removeAll();
        jPaneGraficoRE.add(myChartPanel); 
        jPaneGraficoRE.revalidate();
        jPaneGraficoRE.repaint(); 
    }
    
    private void ChangeName(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
        _carregarTiposGraficos();
    }
    
    private void _carregarTiposGraficos(){
        jComboBoxTipoGrafico.removeAllItems();
        jComboBoxTipoGrafico.addItem("-");
        jComboBoxTipoGrafico.addItem("Area Total");
        jComboBoxTipoGrafico.addItem("M³/ha");
        jComboBoxTipoGrafico.addItem("MDC/ha");
        jComboBoxTipoGrafico.addItem("Madeira praça(m³)");
        jComboBoxTipoGrafico.addItem("MDC praca(m³)");  
        
        jComboBoxTipoGrafico.addItem("Madeira estimada(m³)");
        jComboBoxTipoGrafico.addItem("Madeira transportada(m³)");
        jComboBoxTipoGrafico.addItem("Madeira processada(m³)");
        jComboBoxTipoGrafico.addItem("Madeira estimada(t)");
        jComboBoxTipoGrafico.addItem("Madeira transportada(t)"); 
        
        jComboBoxTipoGrafico.addItem("MDC estimado");
        jComboBoxTipoGrafico.addItem("MDC produzido");
        jComboBoxTipoGrafico.addItem("MDC transportado");
        jComboBoxTipoGrafico.addItem("Carvão estimado(t)");
        jComboBoxTipoGrafico.addItem("Carvão transportado(t)"); 
        _carregarFazendas();
    }
    
    private void _carregarFazendas(){ 
        //jComboBoxFazenda.removeAllItems();
        //jComboBoxFazenda.addItem("-");
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql;
        query = "SELECT fazenda FROM estoque_principal ORDER BY fazenda ASC";
        list_fazendas = new ArrayList();
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<list_fazendas.size(); j++) {
                    if (list_fazendas.get(j).equals(rs.getString("fazenda"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("fazenda"));
                    list_fazendas.add(rs.getString("fazenda"));
                    //jComboBoxFazenda.addItem(rs.getString("fazenda"));
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar fazendas! "+ex);
        }   
        /*for (int j=0; j<fazendas.size(); j++) {
            System.out.println("Fazenda["+j+"]: "+fazendas.get(j)); 
        }*/
        _carregarMaterialGenetico();
    }
    
    private void _carregarMaterialGenetico(){ 
        jComboBoxMatGen.removeAllItems();
        jComboBoxMatGen.addItem("-");
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql = null;
        /*String filtro_matgen;
        
         //Controle e definição das variaveis da clausula where like. Filtros
        if(jComboBoxMatGen.getSelectedItem().equals("-")){
            filtro_matgen="";
        }else{
            filtro_matgen=jComboBoxMatGen.getSelectedItem().toString();
        }
        
        //faz busca a partir dos filtros acima
        if(!filtro_matgen.equals("")){
            if(whereSql.length()>=15){
                whereSql += " and material_genetico = '"+filtro_matgen+"'";
            }else{
                whereSql += "material_genetico = '"+filtro_matgen+"'";
            }
        }*/
        
        query = "SELECT material_genetico FROM estoque_principal ORDER BY material_genetico ASC";//+whereSql;
        
        list_mat_gen = new ArrayList();
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<list_mat_gen.size(); j++) {
                    if (list_mat_gen.get(j).equals(rs.getString("material_genetico"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" mg "+rs.getString("material_genetico"));
                    if(!rs.getString("material_genetico").equals("-")){
                        list_mat_gen.add(rs.getString("material_genetico"));
                        jComboBoxMatGen.addItem(rs.getString("material_genetico"));
                    }
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao carregar fazendas! "+ex);
        }   
        /*for (int j=0; j<mat_gen.size(); j++) {
            System.out.println("Material Genetico["+j+"]: "+mat_gen.get(j)); 
        }*/
        PreencherTabelaFiltrada();
    }
    
    
    
    
    private void PreencherTabelaFiltrada(){  
        //JOptionPane.showMessageDialog(null, "Size! " + jListFiltrar.getSelectedIndices().length + jListFiltrar.getModel().getSize());
        Locale brasil = new Locale ("pt", "BR");
        DecimalFormat decformat = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols (brasil));
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query="";
        String whereSql=""; 
        String andSql=""; 
        ResultSet rs;  
        
        int mat_gem=0;
        
        linhas = new ArrayList();
        colunas = new String[] { 
            "Id",
            "Fazenda",
            "Projeto",
            "Material Genetico",
            
            "Area total",
            "Media geral m³/ha",
            "Media ponderada mdc/ha",
            "Volume madeira praça total",
            "Volume carvao praça total",
                        
            "Volume madeira estimada total",
            "Volume madeira transportada total",
            "Volume madeira processada total",
            "Toneladas de madeira estimada totais",
            "Toneladas de madeira transportada totais",
            
            "Volume carvão estimado total",
            "Volume carvão produzido total",
            "Volume carvão transportado total",
            "Toneladas de carvão estimado total",     
            "Toneladas de carvão produzido total",
            "Toneladas de carvão transportado total"
        };
                
        /*id, fazenda, material material, area, m3ha, mdcha*/
        id=0;        
        
        //defini quantidade de colunas
        /*colunas = new String[tamanho];
              
        //carrega nomes das colunas selecionadas ou todas.
        for(int i = 0; i < tamanho; i++)
        {
            colunas[i] = (String) jTableRelatorioGrafico.getColumnName(i);
        }*/
        
        //Controle e definição das variaveis da clausula where like. Filtros
        if(jComboBoxMatGen.getSelectedItem().equals("-")){
            filtro_matgen="";
            mat_gem=list_mat_gen.size();
        }else{
            filtro_matgen=jComboBoxMatGen.getSelectedItem().toString();
            mat_gem=1;
            //list_mat_gen.clear();
            //list_mat_gen.add(filtro_matgen);
        }
        
        //faz busca a partir dos filtros acima
        //System.out.println("list_fazendas: "+list_fazendas.size());
        //System.out.println("list_mat_gen: "+list_mat_gen.size());
        //System.out.println("mat_gem: "+mat_gem);
        for(int i = 0; i < list_fazendas.size(); i++)
        {
            for(int j = 0; j < mat_gem; j++){
                //for(int k = 0; k < list_projeto.size(); k++){
            
                whereSql = "where fazenda = '"+list_fazendas.get(i)+"'";
                //System.out.println("list_projeto: "+list_mat_gen.size());
                //if(list_mat_gen.size()>1){
                if(filtro_matgen.equals("")){
                    whereSql += "and material_genetico = '"+list_mat_gen.get(j)+"'"; 
                    //System.out.println("list_mat_gen: "+list_mat_gen.get(j));
                }else {
                    whereSql += " and material_genetico = '"+filtro_matgen+"'"; 
                    //System.out.println("filtro_matgen: "+filtro_matgen);
                }
                
                query = "SELECT * FROM estoque_principal "+whereSql+" ORDER BY fazenda,projeto,upc,talhao ASC";

                //System.out.println("Query: "+query);

                fazenda="";
                projeto="";
                material_genetico="";
                areaTotal=0;
                m3_haMedia=0;
                mdc_haMedia=0;
                vol_mad_pracaTotal=0;
                vol_carv_pracaTotal=0;
                vol_mad_estTotal=0;
                vol_mad_transpTotal=0;
                vol_mad_procTotal=0;
                madeira_fornoTotal=0;
                mad_ton_estTotal=0;
                mad_ton_transpTotal=0;
                mdc_estTotal=0;
                mdc_prodTotal=0;
                mdc_transpTotal=0;
                carv_ton_estTotal=0;
                carv_ton_prodTotal=0;
                carv_ton_transpTotal=0;
                media_idades=0;
                cont=0;
                //System.out.println("Query: " + query);
                //carrega dados do banco de dados dependendo da consulta sql
                rs = con.consultaSql(query);
                //cria um objeto coluna de acordo com as colunas selecionadas para cada linha encontrada na consulta
                Object[] coluna = new Object[TAMANHO];
                try {            
                    while(rs.next()){                        

                        //coluna[31] = decformat.format(rs.getString("vol_mad_estimado"));
                        //System.out.println("Add Dados ["+31+"]: "+coluna[31]);
                        projeto = rs.getString("projeto");
                        //area total
                        if(rs.getString("area")!=null){
                            areaTotal += Float.valueOf(rs.getString("area"));
                        }

                        //media aritmetica  m3/ha
                        if(rs.getString("m3_ha")!=null && Float.valueOf(rs.getString("m3_ha"))>0){
                            //m3_haMedia += Float.valueOf(rs.getString("m3_ha"));
                            m3_haMedia += Float.valueOf(rs.getString("m3_ha")) * Float.valueOf(rs.getString("area"));
                        }

                        //media ponderada mdc/ha
                        if(rs.getString("mdc_ha")!=null && Float.valueOf(rs.getString("mdc_ha"))>0 ){
                            mdc_haMedia += Float.valueOf(rs.getString("mdc_ha")) * Float.valueOf(rs.getString("area"));
                        }

                        //madeira praça total
                        if(rs.getString("madeira_praca")!=null && Float.valueOf(rs.getString("madeira_praca"))>0 ){
                            vol_mad_pracaTotal += Float.valueOf(rs.getString("madeira_praca"));
                        }

                        //carvão praça total
                        if(rs.getString("carvao_praca")!=null && Float.valueOf(rs.getString("carvao_praca"))>0 ){
                            vol_carv_pracaTotal += Float.valueOf(rs.getString("carvao_praca"));
                        }

                        //Volumes totais de madeira
                        if(rs.getString("vol_mad_estimado")!=null){     
                            vol_mad_estTotal += Float.valueOf(rs.getString("vol_mad_estimado"));
                        }
                        if(rs.getString("vol_mad_transp")!=null){
                            vol_mad_transpTotal += Float.valueOf(rs.getString("vol_mad_transp"));
                        }
                        if(rs.getString("madeira_forno")!=null){
                            madeira_fornoTotal += Float.valueOf(rs.getString("madeira_forno"));
                        }

                        //Toneladas totais de madeira
                        if(rs.getString("mad_ton_estimado")!=null){
                            mad_ton_estTotal += Float.valueOf(rs.getString("mad_ton_estimado"));
                        }
                        if(rs.getString("mad_ton_transp")!=null){
                            mad_ton_transpTotal += Float.valueOf(rs.getString("mad_ton_transp"));
                        }

                        //Volumes totais de carvao
                        if(rs.getString("mdc_estimado")!=null){
                            mdc_estTotal += Float.valueOf(rs.getString("mdc_estimado"));
                        }
                        if(rs.getString("mdc_prod")!=null){
                            mdc_prodTotal += Float.valueOf(rs.getString("mdc_prod"));
                        }
                        if(rs.getString("mdc_transp")!=null){
                            mdc_transpTotal += Float.valueOf(rs.getString("mdc_transp"));
                        }

                        //Toneladas totais de carvao
                        if(rs.getString("carv_ton_estimado")!=null){
                            carv_ton_estTotal += Float.valueOf(rs.getString("carv_ton_estimado"));
                        } 
                        if(rs.getString("carv_ton_prod")!=null){
                            carv_ton_prodTotal += Float.valueOf(rs.getString("carv_ton_prod"));
                        } 
                        if(rs.getString("carv_ton_transp")!=null){
                            carv_ton_transpTotal += Float.valueOf(rs.getString("carv_ton_transp"));
                        }           

                        if(rs.getString("idade_hoje")!=null){
                            media_idades += Float.valueOf(rs.getString("idade_hoje"));
                            cont++;
                        } 
                        //System.out.printf("\nCalculo m3ha: "+ m3_haMedia); 
                    }

                    if(areaTotal>0){
                        mdc_haMedia = mdc_haMedia/areaTotal;
                        m3_haMedia = m3_haMedia/areaTotal;

                        fazenda = list_fazendas.get(i).toString(); 
                        System.out.println("list_mat_gen: "+list_mat_gen.size()); 
                        if(mat_gem>1){
                            material_genetico = list_mat_gen.get(j).toString();
                        }else{
                            material_genetico=jComboBoxMatGen.getSelectedItem().toString();
                        }
                        
                        System.out.println("material_genetico: "+material_genetico); 

                        coluna[0]=id;
                        coluna[1]=fazenda;
                        coluna[2]=projeto;
                        coluna[3]=material_genetico;

                        coluna[4]=areaTotal;
                        coluna[5]=m3_haMedia;
                        coluna[6]=mdc_haMedia;

                        coluna[7]=vol_mad_pracaTotal;
                        coluna[8]=vol_carv_pracaTotal;

                        coluna[9]=vol_mad_estTotal;
                        coluna[10]=vol_mad_transpTotal;
                        coluna[11]=madeira_fornoTotal;
                        coluna[12]=mad_ton_estTotal;
                        coluna[13]=mad_ton_transpTotal;

                        coluna[14]=mdc_estTotal;
                        coluna[15]=mdc_prodTotal;
                        coluna[16]=mdc_transpTotal;
                        coluna[17]=carv_ton_estTotal;
                        coluna[18]=carv_ton_prodTotal;
                        coluna[19]=carv_ton_transpTotal;
                        //coluna[20]=media_idades;

                        //adiciona a cada linha os valores de cada objeto coluna
                        linhas.add(coluna);
                        id++;
                    }else{
                        mdc_haMedia = 0;
                        m3_haMedia = 0;
                    }   

                    //System.out.printf("\nlinha m3ha: "+ linhas.get(id));                     
                    //System.out.printf("\nCalculo m3ha: "+ m3_haMedia); 

                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Erro ao Preencher Tabela Filtrada ! "+ex);
                    JOptionPane.showMessageDialog(null, "Erro ao Preencher Tabela Filtrada ! "+ex);
                }               
            }
        //}
        }
        //System.out.printf("\nLinha m3ha: "+ linhas.get(0));   
        
        MontarTabela();        
        con.fecharConexao();
    }
    
    private void MontarTabela(){
        //monta a tabela com as respectivas linhas e colunas
        GerarTabela modelo = new GerarTabela(linhas, colunas);
        jTableRelatorioGrafico.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableRelatorioGrafico.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableRelatorioGrafico.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*11);
            }else{
                jTableRelatorioGrafico.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*9);
            }
            jTableRelatorioGrafico.getColumnModel().getColumn(i).setPreferredWidth(6);
            jTableRelatorioGrafico.getColumnModel().getColumn(i).setResizable(false);
            //jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setHeaderValue("");
            //System.out.println("Indice: "+i+" - "+ colunas[i]);
        }
        jTableRelatorioGrafico.getTableHeader().setReorderingAllowed(false);
        jTableRelatorioGrafico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
    }
    
    private void PrepararGrafico(){
        //Grafico
        ControlePrincipal.valor_grafico = new float[id];
        ControlePrincipal.info_grafico = new String[id];
        //System.out.println("Indice: "+jComboBoxTipoGrafico.getSelectedIndex());
        
        int tipo = 0;
        switch (jComboBoxTipoGrafico.getSelectedIndex()){
            case 1:
                tipo = 4;
                ControlePrincipal.tipo_grafico = "Area Total";
                break;
            case 2:
                tipo = 5;
                ControlePrincipal.tipo_grafico = "M³/ha";
                break;
            case 3:
                tipo = 6;
                ControlePrincipal.tipo_grafico = "MDC/ha";
                break;
            case 4:
                tipo = 7;
                ControlePrincipal.tipo_grafico = "Madeira praça(m³)";
                break;
            case 5:
                tipo = 8;
                ControlePrincipal.tipo_grafico = "MDC praca(m³)";
                break;
            case 6:
                tipo = 9;
                ControlePrincipal.tipo_grafico = "Madeira estimada(m³)";
                break;
            case 7:
                tipo = 10;
                ControlePrincipal.tipo_grafico = "Madeira transportada(m³)";
                break;
            case 8:
                tipo = 11;
                ControlePrincipal.tipo_grafico = "Madeira processada(m³)";
                break;
            case 9:
                tipo = 12;
                ControlePrincipal.tipo_grafico = "Madeira estimada(t)";
                break;
            case 10:
                tipo = 13;
                ControlePrincipal.tipo_grafico = "Madeira transportada(t)";
                break;
            case 11:
                tipo = 14;
                ControlePrincipal.tipo_grafico = "MDC estimado";
                break;
            case 12:
                tipo = 15;
                ControlePrincipal.tipo_grafico = "MDC produzido";
                break;
            case 13:
                tipo = 16;
                ControlePrincipal.tipo_grafico = "MDC transportado";
                break;
            case 14:
                tipo = 17;
                ControlePrincipal.tipo_grafico = "Carvão estimado(t)";
                break;
            case 15:
                tipo = 18;
                ControlePrincipal.tipo_grafico = "Carvão transportado(t)";
                break;
            default:
                tipo = 0;
                break;
        }
        if(tipo>0){            
            for (int i=0; i<id; i++){            
                //System.out.println("Grafico:  "+ ControlePrincipal.valor_grafico.length);
                //System.out.println("Tabela Fazenda:  "+ jTableRelatorioGrafico.getValueAt(i, 2)+" - "+jTableRelatorioGrafico.getValueAt(i,3));
                //System.out.println("Tabela Area:  "+ jTableRelatorioGrafico.getValueAt(i, 4));            
                ControlePrincipal.valor_grafico[i]=(float) jTableRelatorioGrafico.getValueAt(i, tipo);
                ControlePrincipal.info_grafico[i]=(String) jTableRelatorioGrafico.getValueAt(i, 1)+" - "+jTableRelatorioGrafico.getValueAt(i,2);
            }         
        
            Grafico gr = new Grafico();
            gr.criaGrafico();
            gr.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Selecione um tipo de gráfico!");
        }
    }
    
    private void GerarPDF() throws DocumentException, FileNotFoundException {
        try {            
            int linha = jTableRelatorioGrafico.getSelectedRow();
                Document document = new Document(PageSize.A4, 10, 10, 10, 10);
                //System.out.println(new File(".").getAbsolutePath());
                String arquivo = new File("RelatorioFaz.").getAbsolutePath()+"pdf";            
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
                document.open();
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                //String titulo = jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString()+ " "+jTableRelatorioEstoquePrincipal.getValueAt(linha, 2).toString()+"-"+jTableRelatorioEstoquePrincipal.getValueAt(linha, 0).toString();
                String titulo = "Relatorio";                
                Paragraph pgt = new Paragraph(titulo, font);
                pgt.setAlignment(Element.ALIGN_CENTER);
                document.add(pgt);
                document.add(new Paragraph(" "));
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
                        table.addCell(new Paragraph(jTableRelatorioGrafico.getValueAt(i, j).toString(),font));
                    }
                }

                document.add(table);
                
                document.add(new Paragraph(" "));
                
                font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                document.add(new Paragraph("Dados Totais",font));                
                font = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
                document.close();
                JOptionPane.showMessageDialog(null, "PDF: "+arquivo+" gerado!");
        } catch (FileNotFoundException | DocumentException | HeadlessException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Erro ao gerar pdf: "+ex);
            JOptionPane.showMessageDialog(null, "Erro ao gerar pdf: "+ex);
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPaneGraficoRE = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRelatorioGrafico = new javax.swing.JTable();
        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBoxTipoGrafico = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jButtonGerarGrafico = new javax.swing.JButton();
        jButtonGerarPDF = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxMatGen = new javax.swing.JComboBox();
        jButtonFiltrar = new javax.swing.JButton();
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

        jScrollPane1.setViewportView(jTableRelatorioGrafico);

        javax.swing.GroupLayout jPaneGraficoRELayout = new javax.swing.GroupLayout(jPaneGraficoRE);
        jPaneGraficoRE.setLayout(jPaneGraficoRELayout);
        jPaneGraficoRELayout.setHorizontalGroup(
            jPaneGraficoRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPaneGraficoRELayout.setVerticalGroup(
            jPaneGraficoRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
        );

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Grafico Estoque");
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
                    .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelIdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 350));

        jComboBoxTipoGrafico.setFont(jComboBoxTipoGrafico.getFont().deriveFont(jComboBoxTipoGrafico.getFont().getSize()+1f));
        jComboBoxTipoGrafico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxTipoGrafico.setPreferredSize(new java.awt.Dimension(80, 25));
        jComboBoxTipoGrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoGraficoActionPerformed(evt);
            }
        });

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+1f));
        jLabel3.setText("Tipo de grafico");
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 25));

        jButtonGerarGrafico.setFont(jButtonGerarGrafico.getFont().deriveFont(jButtonGerarGrafico.getFont().getSize()+1f));
        jButtonGerarGrafico.setText("Gerar Grafico");
        jButtonGerarGrafico.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonGerarGrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarGraficoActionPerformed(evt);
            }
        });

        jButtonGerarPDF.setText("GerarPDF");
        jButtonGerarPDF.setPreferredSize(new java.awt.Dimension(80, 25));
        jButtonGerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarPDFActionPerformed(evt);
            }
        });

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+1f));
        jLabel4.setText("Mat. Genetico");

        jComboBoxMatGen.setFont(jComboBoxMatGen.getFont().deriveFont(jComboBoxMatGen.getFont().getSize()+1f));
        jComboBoxMatGen.setPreferredSize(new java.awt.Dimension(50, 25));

        jButtonFiltrar.setFont(jButtonFiltrar.getFont().deriveFont(jButtonFiltrar.getFont().getSize()+1f));
        jButtonFiltrar.setText("Filtrar");
        jButtonFiltrar.setPreferredSize(new java.awt.Dimension(80, 25));
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
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonGerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBoxMatGen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBoxTipoGrafico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButtonGerarGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 115, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTipoGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jButtonGerarGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButtonGerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMatGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(10, 10, 10)
                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(312, Short.MAX_VALUE))
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
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPaneGraficoRE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
                    .addComponent(jPaneGraficoRE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGerarGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarGraficoActionPerformed
        PrepararGrafico();
    }//GEN-LAST:event_jButtonGerarGraficoActionPerformed

    private void jComboBoxTipoGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoGraficoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoGraficoActionPerformed

    private void jButtonGerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarPDFActionPerformed
        try {
            //AlterarInfo();
            //SelecionarTalhao();
            GerarPDF();
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(GraficoRelatorioEstoque.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gerar PDF Error: "+ex);
        }
    }//GEN-LAST:event_jButtonGerarPDFActionPerformed

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        PreencherTabelaFiltrada();
        //_carregarCategoria();
        //JOptionPane.showMessageDialog(null, jListFiltrar.getSelectedValuesList());
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

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

    private void jMenuItemRelatorioEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioEstoqueActionPerformed
        try {
            new GerarRelatorioEstoqueBasico().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemRelatorioEstoqueActionPerformed

    private void jMenuItemRelatorioMadeiraPracaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioMadeiraPracaActionPerformed
        try {
            new GerarRelatorioMadeiraPraca().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemRelatorioMadeiraPracaActionPerformed

    private void jMenuItemRelatorioCarvaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioCarvaoActionPerformed
        try {
            new GerarRelatorioCarvao().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemRelatorioCarvaoActionPerformed

    private void jMenuItemRelatorioCarvaoExpedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioCarvaoExpedidoActionPerformed
        try {
            new GerarRelatorioCarvaoExpedido().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jMenuItemRelatorioCarvaoExpedidoActionPerformed

    private void jMenuItemRelatorioFornosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRelatorioFornosActionPerformed
        new GerarRelatorioForno().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItemRelatorioFornosActionPerformed

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
            java.util.logging.Logger.getLogger(GraficoRelatorioEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GraficoRelatorioEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GraficoRelatorioEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraficoRelatorioEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraficoRelatorioEstoque().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JButton jButtonGerarGrafico;
    private javax.swing.JButton jButtonGerarPDF;
    private javax.swing.JComboBox jComboBoxMatGen;
    private javax.swing.JComboBox jComboBoxTipoGrafico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
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
    private javax.swing.JPanel jPaneGraficoRE;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableRelatorioGrafico;
    // End of variables declaration//GEN-END:variables
}
