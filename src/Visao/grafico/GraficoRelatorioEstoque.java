/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.grafico;

import Controle.ControlePrincipal;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.relatorios.GerarRelatorioEstoqueBasico;
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
    private ArrayList fazendas;
    private ArrayList mat_gen;
    
    private int id;
    private String fazenda;
    private String material_genetico;
    private float areaTotal;  
    private float m3_haMedia;  
    private float mdc_haMedia;
    private int tamanho;
         
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
        _carregarFazendas();
    }
    
    private void _carregarFazendas(){ 
        //jComboBoxFazenda.removeAllItems();
        //jComboBoxFazenda.addItem("-");
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql;
        query = "SELECT fazenda FROM estoque_principal";
        fazendas = new ArrayList();
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<fazendas.size(); j++) {
                    if (fazendas.get(j).equals(rs.getString("fazenda"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("fazenda"));
                    fazendas.add(rs.getString("fazenda"));
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
        String filtro_matgen;
        
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
        }
        
        query = "SELECT material_genetico FROM estoque_principal";//+whereSql;
        
        mat_gen = new ArrayList();
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<mat_gen.size(); j++) {
                    if (mat_gen.get(j).equals(rs.getString("material_genetico"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" mg "+rs.getString("material_genetico"));
                    mat_gen.add(rs.getString("material_genetico"));
                    jComboBoxMatGen.addItem(rs.getString("material_genetico"));
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
        ResultSet rs;      
        linhas = new ArrayList();
                
        tamanho = 6;
        /*id, fazenda, material material, area, m3ha, mdcha*/
        id=0;
        
        
        //defini quantidade de colunas
        colunas = new String[tamanho];
              
        //carrega nomes das colunas selecionadas ou todas.
        for(int i = 0; i < tamanho; i++)
        {
            colunas[i] = (String) jTableRelatorioGrafico.getColumnName(i);
        }
        
        //faz busca a partir dos filtros acima
        for(int i = 0; i < fazendas.size(); i++)
        {
            for(int j = 0; j < mat_gen.size(); j++)
            {
                whereSql = "where fazenda = '"+fazendas.get(i)+"' and material_genetico = '"+mat_gen.get(j)+"'"; 
                query = "SELECT * FROM estoque_principal "+whereSql+" ORDER BY fazenda,projeto,upc,talhao ASC";
                fazenda="";
                material_genetico="";
                areaTotal=0;
                m3_haMedia=0;
                mdc_haMedia=0;
                //System.out.println("Query: " + query);
                //carrega dados do banco de dados dependendo da consulta sql
                rs = con.consultaSql(query);
                
                //cria um objeto coluna de acordo com as colunas selecionadas para cada linha encontrada na consulta
                Object[] coluna = new Object[tamanho];
                try {            
                    while(rs.next()){                        

                        //coluna[31] = decformat.format(rs.getString("vol_mad_estimado"));
                        //System.out.println("Add Dados ["+31+"]: "+coluna[31]);
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

                        //System.out.printf("\nCalculo m3ha: "+ m3_haMedia); 
                        
                    }

                    if(areaTotal>0){
                        mdc_haMedia = mdc_haMedia/areaTotal;
                        m3_haMedia = m3_haMedia/areaTotal;
                        
                        fazenda = fazendas.get(i).toString();
                        material_genetico = mat_gen.get(j).toString();

                        coluna[0]=id;
                        coluna[1]=fazenda;
                        coluna[2]=material_genetico;
                        coluna[3]=areaTotal;
                        coluna[4]=m3_haMedia;
                        coluna[5]=mdc_haMedia;

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
            /*if(colunas[i].length()<=8){                
                jTableRelatorioGrafico.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableRelatorioGrafico.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*11);
            }else{
                jTableRelatorioGrafico.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*9);
            }*/
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
        
        int tipo = 0;
        switch (jComboBoxTipo.getSelectedIndex()){
            case 1:
                tipo = 3;
                ControlePrincipal.tipo_grafico = "Area Total";
                break;
            case 2:
                tipo = 4;
                ControlePrincipal.tipo_grafico = "M³_ha";
                break;
            case 3:
                tipo = 5;
                ControlePrincipal.tipo_grafico = "MDC_ha";
                break;
            default:
                tipo = 0;
                break;
        }
        if(tipo>0){            
            for (int i=0; i<id; i++){            
                //System.out.println("Grafico:  "+ ControlePrincipal.valor_grafico.length);
                //System.out.println("Tabela Fazenda:  "+ jTableRelatorioGrafico.getValueAt(i, 1)+" - "+jTableRelatorioGrafico.getValueAt(i,2));
                //System.out.println("Tabela Area:  "+ jTableRelatorioGrafico.getValueAt(i, 3));            
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
//                if(!filtro_matgen.equals("")){
//                    titulo = "Material genetico "+filtro_matgen;
//                }
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
                        table.addCell(new Paragraph(jTableRelatorioGrafico.getValueAt(i, j).toString(),font));
                    }
                }

                document.add(table);
                
                document.add(new Paragraph(" "));
                
                font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                document.add(new Paragraph("Dados Totais",font));                
                font = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
//                document.add(new Paragraph(jLabelInfo1.getText(),font));
//                document.add(new Paragraph(jLabelAreaTotal.getText(),font));
//                document.add(new Paragraph(jLabelM3_ha.getText(),font));
//                document.add(new Paragraph(jLabelMDC_ha.getText(),font));
//                document.add(new Paragraph(jLabelTotalMadeiraPraca.getText(),font));
//                document.add(new Paragraph(jLabelTotalCarvaoPraca.getText(),font));
//
//                document.add(new Paragraph(jLabelInfo2.getText(),font));
//                document.add(new Paragraph(jLabelVolumeMadeiraEstTotal.getText(),font));
//                document.add(new Paragraph(jLabelVolumeMadeiraTranspTotal.getText(),font));
//                document.add(new Paragraph(jLabelToneladaMadeiraEstTotal.getText(),font));
//                document.add(new Paragraph(jLabelToneladaMadeiraTranspTotal.getText(),font));
//
//                document.add(new Paragraph(jLabelInfo3.getText(),font));
//                document.add(new Paragraph(jLabelVolumeCarvaoEstTotal.getText(),font));
//                document.add(new Paragraph(jLabelVolumeCarvaoProdTotal.getText(),font));
//                document.add(new Paragraph(jLabelVolumeCarvaoTranspTotal.getText(),font));
//                document.add(new Paragraph(jLabelToneladaCarvaoEstTotal.getText(),font));
//                document.add(new Paragraph(jLabelToneladaCarvaoProdTotal.getText(),font));
//                document.add(new Paragraph(jLabelToneladaCarvaoTranspTotal.getText(),font));
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
        jComboBoxTipo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jButtonGerarGrafico = new javax.swing.JButton();
        jButtonGerarPDF = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxMatGen = new javax.swing.JComboBox();
        jButtonFiltrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableRelatorioGrafico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Fazenda", "Material genetico", "Area Total(ha)", "Media Geral m³/ha", "Media ponderada mdc/ha"
            }
        ));
        jScrollPane1.setViewportView(jTableRelatorioGrafico);

        javax.swing.GroupLayout jPaneGraficoRELayout = new javax.swing.GroupLayout(jPaneGraficoRE);
        jPaneGraficoRE.setLayout(jPaneGraficoRELayout);
        jPaneGraficoRELayout.setHorizontalGroup(
            jPaneGraficoRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPaneGraficoRELayout.setVerticalGroup(
            jPaneGraficoRELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
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

        jComboBoxTipo.setFont(jComboBoxTipo.getFont().deriveFont(jComboBoxTipo.getFont().getSize()+1f));
        jComboBoxTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Area Total", "M³/ha", "MDC/ha" }));
        jComboBoxTipo.setPreferredSize(new java.awt.Dimension(80, 25));
        jComboBoxTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoActionPerformed(evt);
            }
        });

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+1f));
        jLabel3.setText("Tipo de grafico");
        jLabel3.setPreferredSize(new java.awt.Dimension(80, 25));

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
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonGerarGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonGerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBoxMatGen, 0, 78, Short.MAX_VALUE)
                        .addGap(37, 37, 37)
                        .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonGerarGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonGerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMatGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(351, Short.MAX_VALUE))
        );

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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
                    .addComponent(jPaneGraficoRE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGerarGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarGraficoActionPerformed
        PrepararGrafico();
    }//GEN-LAST:event_jButtonGerarGraficoActionPerformed

    private void jComboBoxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoActionPerformed

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
    private javax.swing.JComboBox jComboBoxTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPaneGraficoRE;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableRelatorioGrafico;
    // End of variables declaration//GEN-END:variables
}
