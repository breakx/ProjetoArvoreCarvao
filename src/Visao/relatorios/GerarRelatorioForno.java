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
import Visao.grafico.Grafico;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

/**
 *
 * @author Cristiano GD
 */
public class GerarRelatorioForno extends javax.swing.JFrame {

    private ArrayList linhas;
    private String[] colunas;
    /**
     * Creates new form GerarRelatorioForno
     */
    public GerarRelatorioForno() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        jMenuItemRelatorioFornos.setVisible(false);
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
        CarregarNome();
        _carregarFornos();
        PreencherTabela();
    }
    
    private void _carregarFornos(){ 
        jComboBoxForno.removeAllItems();
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql;
        query = "SELECT nome_forno FROM forno";
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        jComboBoxForno.addItem("-");
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<jComboBoxForno.getItemCount(); j++) {
                    if (jComboBoxForno.getItemAt(j).equals(rs.getString("nome_forno"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("fazenda"));
                    jComboBoxForno.addItem(rs.getString("nome_forno"));
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Preencher Tabela Completa ! "+ex);
        }        
    }
    
    /**
     * 
     */
    private void PreencherTabela(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[] { 
            "nome_forno", 
            "volume_maximo_forno", 
            "situacao_forno", 
            "upc_forno",
            "data_alteracao",
            "usuario_alt",
            "id_forno"
        };
        int tamanho = 0;            
        String whereSql;
        
        //Controle e definição das variaveis da clausula where like. Filtros
        String filtro_forno;
        if(jComboBoxForno.getSelectedItem().equals("-")){
            filtro_forno="";
        }else{
            filtro_forno=jComboBoxForno.getSelectedItem().toString();
        }
        
        //faz busca a partir dos filtros acima
        if(!filtro_forno.equals("")){
            whereSql = " where fazenda like '%"+filtro_forno+"%'";
        }else{
            whereSql = "";
        }
        String query = "Select * from forno"+whereSql;
        ConexaoBD con = ConexaoBD.getConexao(0);         
        ResultSet rs = con.consultaSql(query);
        int vazio=0;
        int cheio=0;
        int carb=0;
        int resf=0;
        int refor=0;
        int manut=0;
        try {
            while(rs.next()){
                dados.add(new Object[]{
                    rs.getString("nome_forno"),
                    rs.getString("volume_maximo_forno"),
                    rs.getString("situacao_forno"),
                    rs.getString("upc_forno"),
                    rs.getString("data_alteracao"),
                    rs.getString("usuario_alt"),
                    rs.getString("id_forno")
                });
                if(rs.getString("situacao_forno").equals("Vazio")||rs.getString("situacao_forno").equals("Novo")){
                    vazio++;
                }
                if(rs.getString("situacao_forno").equals("Cheio")){
                    cheio++;
                }
                if(rs.getString("situacao_forno").equals("Carbonizando")){
                    carb++;
                    cheio++;
                }
                if(rs.getString("situacao_forno").equals("Resfriando")){
                    resf++;
                    cheio++;
                }
                if(rs.getString("situacao_forno").equals("Reforma")){
                    refor++;
                    vazio++;
                }
                if(rs.getString("situacao_forno").equals("Manutencao")){
                    manut++;
                    vazio++;
                }
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
                
        jLabelFornosVazios.setText("Fornos Vazios: "+vazio);
        jLabelFornosCheios.setText("Fornos Cheios: "+cheio);
        jLabelFornosCarbonizando.setText("Fornos Carbonizando: "+carb);
        jLabelFornosResfriando.setText("Fornos Resfriando: "+resf);
        jLabelFornosReforma.setText("Fornos em Reforma: "+refor);
        jLabelFornosManutencao.setText("Fornos em Manutenção: "+manut);
        
        //Grafico
        ControlePrincipal.valor_grafico = new float[6];
        ControlePrincipal.info_grafico = new String[6];
        ControlePrincipal.tipo_grafico = "Fornos";
        
        ControlePrincipal.valor_grafico[0]=vazio;
        ControlePrincipal.info_grafico[0]="Vazios";
        ControlePrincipal.valor_grafico[1]=cheio;
        ControlePrincipal.info_grafico[1]="Cheios";
        ControlePrincipal.valor_grafico[2]=carb;
        ControlePrincipal.info_grafico[2]="Carbonizando";
        ControlePrincipal.valor_grafico[3]=resf;
        ControlePrincipal.info_grafico[3]="Resfriando";
        ControlePrincipal.valor_grafico[4]=refor;
        ControlePrincipal.info_grafico[4]="Reforma";
        ControlePrincipal.valor_grafico[5]=manut;
        ControlePrincipal.info_grafico[5]="Manutenção";
        
        GerarTabela modelo = new GerarTabela(dados, colunas);
        jTableForno.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableForno.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableForno.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableForno.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }
            /*if(i>4){
                jTableFazenda.getColumnModel().getColumn(i).setMinWidth(0);     
                jTableFazenda.getColumnModel().getColumn(i).setPreferredWidth(0);  
                jTableFazenda.getColumnModel().getColumn(i).setMaxWidth(0);
                jTableFazenda.getColumnModel().getColumn(i).setResizable(false);
            }*/
            //System.out.println("Indice: "+i+" - "+ colunas[i].length()*200);
        }
        jTableForno.getTableHeader().setReorderingAllowed(false);
        jTableForno.setAutoResizeMode(jTableForno.AUTO_RESIZE_OFF);
        jTableForno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //duplo click
        /*jTableForno.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                    if(e.getClickCount() == 2){
                        //System.out.println("duplo-clique detectado");
                        AlterarInfo();
                    }
                }
            });*/ 
        con.fecharConexao();
    } 
    
    private void GerarPDF() throws DocumentException, FileNotFoundException {
        try {
            int linha = jTableForno.getSelectedRow();
                Document document = new Document(PageSize.A4, 10, 10, 10, 10);
                //System.out.println(new File(".").getAbsolutePath());
                String arquivo = new File("Relatorio Carvão Expedido.").getAbsolutePath()+"pdf";            
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
                document.open();
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                //String titulo = jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString()+ " "+jTableRelatorioEstoquePrincipal.getValueAt(linha, 2).toString()+"-"+jTableRelatorioEstoquePrincipal.getValueAt(linha, 0).toString();
                String titulo = "Relatorio Carvão Expedido";                
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
                        table.addCell(new Paragraph(jTableForno.getValueAt(i, j).toString(),font));
                    }
                }

                document.add(table);
                document.add(new Paragraph(" "));
                
                font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                document.add(new Paragraph("Dados Totais",font));
                font = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
                document.add(new Paragraph(jLabelInfo1.getText(),font));        
                document.add(new Paragraph(jLabelFornosCheios.getText(),font));        
                document.add(new Paragraph(jLabelFornosVazios.getText(),font));      
                document.add(new Paragraph(jLabelFornosCarbonizando.getText(),font));                      
                document.add(new Paragraph(jLabelFornosResfriando.getText(),font));      
                document.add(new Paragraph(jLabelFornosReforma.getText(),font));                      
                document.add(new Paragraph(jLabelFornosManutencao.getText(),font));
                document.close();
                JOptionPane.showMessageDialog(null, "PDF: "+arquivo+" gerado!");
        } catch (FileNotFoundException | DocumentException | HeadlessException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Erro ao gerar pdf: "+ex);
            JOptionPane.showMessageDialog(null, "Erro ao gerar pdf: "+ex);
        }
        
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

        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonGerenciarEstoque = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxForno = new javax.swing.JComboBox();
        jButtonFiltrar = new javax.swing.JButton();
        jButtonRelatorio = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableForno = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabelFornosVazios = new javax.swing.JLabel();
        jLabelFornosCheios = new javax.swing.JLabel();
        jLabelInfo1 = new javax.swing.JLabel();
        jLabelFornosCarbonizando = new javax.swing.JLabel();
        jLabelFornosResfriando = new javax.swing.JLabel();
        jLabelFornosReforma = new javax.swing.JLabel();
        jLabelFornosManutencao = new javax.swing.JLabel();
        jButtonGrafico = new javax.swing.JButton();
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
        jLabelTitulo.setText("Relatorio de Fornos");
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelIdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

        jButtonExcluir.setFont(jButtonExcluir.getFont().deriveFont(jButtonExcluir.getFont().getSize()+1f));
        jButtonExcluir.setText("Excluir");
        jButtonExcluir.setPreferredSize(new java.awt.Dimension(100, 60));
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

        jButtonGerenciarEstoque.setFont(jButtonGerenciarEstoque.getFont().deriveFont(jButtonGerenciarEstoque.getFont().getSize()+1f));
        jButtonGerenciarEstoque.setText("<html>Gerenciar<br>Estoque</html>");
        jButtonGerenciarEstoque.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonGerenciarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerenciarEstoqueActionPerformed(evt);
            }
        });

        jLabel7.setText("FORNO:");

        jButtonFiltrar.setText("Filtrar");
        jButtonFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFiltrarActionPerformed(evt);
            }
        });

        jButtonRelatorio.setFont(jButtonRelatorio.getFont().deriveFont(jButtonRelatorio.getFont().getSize()+1f));
        jButtonRelatorio.setText("<html>Voltar<br>Relatorio</html>");
        jButtonRelatorio.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBoxForno, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonGerenciarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonRelatorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonGerenciarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxForno)
                    .addComponent(jButtonFiltrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jTableForno.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTableForno);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 724, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );

        jLabelFornosVazios.setFont(jLabelFornosVazios.getFont());
        jLabelFornosVazios.setText("Fornos Vazios: 0");
        jLabelFornosVazios.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelFornosCheios.setFont(jLabelFornosCheios.getFont());
        jLabelFornosCheios.setText("Fornos Cheios: 0");
        jLabelFornosCheios.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelInfo1.setFont(jLabelInfo1.getFont().deriveFont(jLabelInfo1.getFont().getStyle() | java.awt.Font.BOLD, 12));
        jLabelInfo1.setText("Informações Gerais");
        jLabelInfo1.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelFornosCarbonizando.setFont(jLabelFornosCarbonizando.getFont());
        jLabelFornosCarbonizando.setText("Fornos Carbonizando: 0");
        jLabelFornosCarbonizando.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelFornosResfriando.setFont(jLabelFornosResfriando.getFont());
        jLabelFornosResfriando.setText("Fornos Resfriando: 0");
        jLabelFornosResfriando.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelFornosReforma.setFont(jLabelFornosReforma.getFont());
        jLabelFornosReforma.setText("Fornos em Reforma: 0");
        jLabelFornosReforma.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelFornosManutencao.setFont(jLabelFornosManutencao.getFont());
        jLabelFornosManutencao.setText("Fornos em Manutenção: 0");
        jLabelFornosManutencao.setPreferredSize(new java.awt.Dimension(200, 15));

        jButtonGrafico.setText("Grafico");
        jButtonGrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGraficoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(532, 532, 532))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelFornosCheios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(jLabelFornosVazios, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(jLabelFornosCarbonizando, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(jLabelFornosResfriando, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelFornosReforma, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                    .addComponent(jLabelFornosManutencao, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addGap(325, 325, 325))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonGrafico)
                                .addContainerGap())))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabelInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFornosVazios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFornosReforma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFornosCheios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFornosManutencao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jLabelFornosCarbonizando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelFornosResfriando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonGrafico))
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
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        //ExcluirInfo();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonGerenciarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerenciarEstoqueActionPerformed
        //GerenciarEstoque();
    }//GEN-LAST:event_jButtonGerenciarEstoqueActionPerformed

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        //PreencherTabela();
        //JOptionPane.showMessageDialog(null, jListFiltrar.getSelectedValuesList());
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

    private void jButtonRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelatorioActionPerformed
        try {
            new GerarRelatorioEstoqueBasico().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonRelatorioActionPerformed

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

    private void jButtonGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGraficoActionPerformed
        Grafico gr = new Grafico();
        gr.criaGrafico();
        gr.setVisible(true);
    }//GEN-LAST:event_jButtonGraficoActionPerformed

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
            java.util.logging.Logger.getLogger(GerarRelatorioForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GerarRelatorioForno().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JButton jButtonGerenciarEstoque;
    private javax.swing.JButton jButtonGrafico;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRelatorio;
    private javax.swing.JComboBox jComboBoxForno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelFornosCarbonizando;
    private javax.swing.JLabel jLabelFornosCheios;
    private javax.swing.JLabel jLabelFornosManutencao;
    private javax.swing.JLabel jLabelFornosReforma;
    private javax.swing.JLabel jLabelFornosResfriando;
    private javax.swing.JLabel jLabelFornosVazios;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelInfo1;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableForno;
    // End of variables declaration//GEN-END:variables
}
