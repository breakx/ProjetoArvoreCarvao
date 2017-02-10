/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.carvao;

import Controle.ControleCarvao;
import Controle.ControlePrincipal;
import Controle.ControleUsuario;
import Controle.carvao.InserirCarvaoCtrl;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.relatorios.GerarRelatorioEstoquePrincipal;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Cristiano GD
 */
public class InserirMadeiraForno extends javax.swing.JFrame {

    ControleUsuario usuario = new ControleUsuario();
    //private ArrayList vol_max;
    
    private ArrayList<Float> vol_max;
    private ArrayList<Integer> id_forno;
    private ArrayList<String> situacao_forno;
    
    private float vol1;
    private float vol2;
    private float somaVol;
    //private int[] vol_max;
    /**
     * Creates new form InserirMadeiraForno
     */
    public InserirMadeiraForno() {
        initComponents();    
        this.setExtendedState(MAXIMIZED_BOTH);    
        jLabelMunicipio.setText("Municipio: "+ControlePrincipal.municipio);
        jLabelFazenda.setText("Fazenda: "+ControlePrincipal.fazenda);        
        jLabelTalhao.setText("Talhao: "+ControlePrincipal.talhao);        
        jLabelUPC.setText("UPC: "+ControlePrincipal.upc);        
        jLabelVolumeMadeiraPraca.setText("Volume atual de madeira na praça: "+ControlePrincipal.madeira_praca+" m³");
        jLabelMaterialGenetico.setText("Material Genético: "+ControlePrincipal.material_genetico);
                        
        CarregarNome();
        //RegistrarMadeiraForno();
        //_carregarFornos();
        PreencherTabela();
    }  
    
    private void PreencherTabela(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[] { 
            "nome_forno", 
            "volume_maximo_forno", 
            "situacao_forno", 
            "id_forno"
        };
        int tamanho = 0;            
        String whereSql;
        
        String query = "Select * from forno";
        ConexaoBD con = ConexaoBD.getConexao(0);         
        ResultSet rs = con.consultaSql(query);
        
        try {
            while(rs.next()){
                dados.add(new Object[]{
                    rs.getString("nome_forno"),//0
                    rs.getString("volume_maximo_forno"),//1
                    rs.getString("situacao_forno"),//2
                    rs.getString("id_forno")//3
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
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
        jTableForno.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                    if(e.getClickCount() == 2){
                        //System.out.println("duplo-clique detectado");
                        //AlterarInfo();
                    }
                }
            }); 
        con.fecharConexao();
    } 
        
    /*private void _carregarFornos(){ 
        jComboBoxForno.removeAllItems();
        //vol_max = new ArrayList();
        vol_max = new ArrayList<Float>();
        id_forno = new ArrayList<Integer>();
        situacao_forno = new ArrayList<String>();
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql;
        query = "SELECT id_forno,nome_forno,volume_maximo_forno, situacao_forno FROM forno";        
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        
        jComboBoxForno.addItem("-");
        vol_max.add(0.0f);
        id_forno.add(0);
        situacao_forno.add(null);
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
                    jComboBoxForno.addItem(rs.getString("nome_forno")+" nº "+rs.getString("id_forno")+" - "+rs.getString("situacao_forno"));  
                    vol_max.add(Float.parseFloat(rs.getString("volume_maximo_forno"))); 
                    id_forno.add(Integer.parseInt(rs.getString("id_forno"))); 
                    situacao_forno.add(rs.getString("situacao_forno"));
                }          
                
                //System.out.println("i: "+vol_max.get(i)); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(InserirMadeiraForno.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Preencher Tabela Completa ! "+ex);
        }        
    }*/
    
    private void RegistrarMadeiraForno(){
        DateFormat data_forno = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
        Date date = new Date();
        ControleCarvao carvao = new ControleCarvao();
        int linha = 0;
        float vol_mad_forno = 0;
        
        if(jTableForno.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            linha = jTableForno.getSelectedRow();
            vol_mad_forno = Float.parseFloat(jTableForno.getValueAt(linha, 1).toString())/ControlePrincipal.fator_empilalhemto;
            carvao.setId_forno(jTableForno.getValueAt(linha, 3).toString());
            carvao.setForno(jTableForno.getValueAt(linha, 0).toString());
        }
        //float vol_mad_forno;
        //System.out.println("i: "+i); 
        //System.out.println("vol_max: "+vol_max); 
        
        carvao.setId_estoque(ControlePrincipal.id_estoque_principal);
        carvao.setId_operario(ControlePrincipal.id_op);        
        //carvao.setId_forno(id_forno.get(i).toString());
        carvao.setUpc_c(ControlePrincipal.upc);
        carvao.setTalhao(ControlePrincipal.talhao);
        //carvao.setForno(jComboBoxForno.getSelectedItem().toString());
        //carvao.setVolume_madeira((float) vol_max.get(i));//metro estereo
        carvao.setData_entrada_madeira_forno(data_forno.format(date));
        carvao.setMaterial_gen(ControlePrincipal.material_genetico);
        carvao.setUmidade((float)jSpinnerUmidade.getValue());
        //vol_mad_forno = (float) vol_max.get(i)/ControlePrincipal.fator_empilalhemto;
        carvao.setVolume_madeira(vol_mad_forno);
        //System.out.println("vol. madeira(m³): "+carvao.getVolume_madeira()+ControlePrincipal.madeira_praca); 
        jLabelVolumeMadeiraForno.setText("Volume total de madeira enfornada: "+vol_mad_forno+" m³");
        if(carvao.getVolume_madeira() < ControlePrincipal.madeira_praca){
            //if((float)jSpinnerVolumeMadeira.getValue() == vol_max.get(i)){
                //if(situacao_forno.get(i).equals("Vazio")||situacao_forno.get(i).equals("Novo")){
                if(jTableForno.getValueAt(linha, 2).toString().equals("Vazio")||jTableForno.getValueAt(linha, 2).toString().equals("Novo")){//situação
                    ControlePrincipal.madeira_forno += vol_mad_forno;//m³
                    ControlePrincipal.madeira_praca -= vol_mad_forno;//m³
                    ControlePrincipal.atualizarDados = "carvao";
                    ControlePrincipal.condicao_forno = "Cheio";
                    ControlePrincipal.id_forno_usado = jTableForno.getValueAt(linha, 1).toString();
                    //jLabelVolumeMadeiraForno.setText("Volume total de madeira enfornada: "+vol_mad_forno+" m³");
                    InserirCarvaoCtrl inserir = new InserirCarvaoCtrl(carvao);

                    try {
                        new GerenciarCarvaoForno().setVisible(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(InserirMadeiraForno.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.setVisible(false);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Erro, Forno em Uso! ");
                }                
            /*}else if((float)jSpinnerVolumeMadeira.getValue() < vol_max.get(i)){
                JOptionPane.showMessageDialog(null, "Erro, volume de madeira insuficiente!");
            }else{
                JOptionPane.showMessageDialog(null, "Erro, volume de madeira ultrapassa o limite de "+vol_max.get(i)+"!");
            }*/
        }else{
            JOptionPane.showMessageDialog(null, "Erro, volume de madeira insuficiente na praça!");
            
            /*int n = JOptionPane.showConfirmDialog(  
                    null,
                    "Erro, volume de madeira insuficiente na praça!\nDeseja buscar em outro talhão?" ,
                    "",
                    JOptionPane.YES_NO_OPTION);

            if(n == JOptionPane.YES_OPTION){
                //System.out.println("SQL= "+comando);
                //JOptionPane.showMessageDialog(null, "Executando...");
                //volume1 deve vir da primeira busca no banco de dados
                vol1=carvao.getVolume_madeira();
                
                //volume2 da segunda
                vol2 = 1;
                somaVol = vol1+vol2;
                
                //volume maximo do forno
                try {        
                    ControlePrincipal.condicao_carvao="forno";
                    new GerarRelatorioEstoquePrincipal().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.setVisible(false);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Cancelado");
            } */
        }       
    }
    
    private void VoltarMenu(){
        try {
            new GerenciarCarvaoForno().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(InserirMadeiraForno.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabelVolumeMadeiraForno = new javax.swing.JLabel();
        jButtonRegistrarMadeiraForno = new javax.swing.JButton();
        jLabelTalhao = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();
        jLabelFazenda = new javax.swing.JLabel();
        jLabelVolumeMadeiraPraca = new javax.swing.JLabel();
        jLabelMunicipio = new javax.swing.JLabel();
        jLabelUPC = new javax.swing.JLabel();
        jLabelMaterialGenetico = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSpinnerUmidade = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableForno = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Volume de madeira no forno");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 70));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(270, 145));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+4f));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Bem Vindo");

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
                    .addComponent(jLabelNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelIdTipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabelIdTipo, jLabelNome});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabelIdTipo, jLabelNome});

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 350));

        jButtonLogout.setFont(jButtonLogout.getFont().deriveFont(jButtonLogout.getFont().getSize()+13f));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabelVolumeMadeiraForno.setFont(jLabelVolumeMadeiraForno.getFont().deriveFont(jLabelVolumeMadeiraForno.getFont().getSize()+1f));
        jLabelVolumeMadeiraForno.setText("Volume total de madeira enfornada: 0.0 m³");
        jLabelVolumeMadeiraForno.setPreferredSize(new java.awt.Dimension(100, 25));

        jButtonRegistrarMadeiraForno.setFont(jButtonRegistrarMadeiraForno.getFont().deriveFont(jButtonRegistrarMadeiraForno.getFont().getSize()+1f));
        jButtonRegistrarMadeiraForno.setText("Registrar");
        jButtonRegistrarMadeiraForno.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonRegistrarMadeiraForno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarMadeiraFornoActionPerformed(evt);
            }
        });

        jLabelTalhao.setFont(jLabelTalhao.getFont().deriveFont(jLabelTalhao.getFont().getSize()+4f));
        jLabelTalhao.setText("Talhao:");
        jLabelTalhao.setPreferredSize(new java.awt.Dimension(300, 25));

        jButtonVoltar.setFont(jButtonVoltar.getFont().deriveFont(jButtonVoltar.getFont().getSize()+1f));
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabelFazenda.setFont(jLabelFazenda.getFont().deriveFont(jLabelFazenda.getFont().getSize()+4f));
        jLabelFazenda.setText("Fazenda: ");
        jLabelFazenda.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelVolumeMadeiraPraca.setFont(jLabelVolumeMadeiraPraca.getFont().deriveFont(jLabelVolumeMadeiraPraca.getFont().getSize()+1f));
        jLabelVolumeMadeiraPraca.setText("Volume atual de madeira: 0.00 m³");
        jLabelVolumeMadeiraPraca.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelMunicipio.setFont(jLabelMunicipio.getFont().deriveFont(jLabelMunicipio.getFont().getSize()+4f));
        jLabelMunicipio.setText("Municipio: ");
        jLabelMunicipio.setPreferredSize(new java.awt.Dimension(350, 25));

        jLabelUPC.setFont(jLabelUPC.getFont().deriveFont(jLabelUPC.getFont().getSize()+4f));
        jLabelUPC.setText("UPC:");
        jLabelUPC.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelMaterialGenetico.setFont(jLabelMaterialGenetico.getFont().deriveFont(jLabelMaterialGenetico.getFont().getSize()+4f));
        jLabelMaterialGenetico.setText("Material Genético:");
        jLabelMaterialGenetico.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+1f));
        jLabel5.setText("Umidade");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 25));

        jSpinnerUmidade.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(0.1f)));
        jSpinnerUmidade.setPreferredSize(new java.awt.Dimension(200, 25));

        jTableForno.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTableForno);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButtonRegistrarMadeiraForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelUPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelVolumeMadeiraPraca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jSpinnerUmidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelVolumeMadeiraForno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabelFazenda, jLabelMunicipio, jLabelTalhao, jLabelUPC, jLabelVolumeMadeiraPraca});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelMunicipio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelUPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelVolumeMadeiraPraca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerUmidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabelVolumeMadeiraForno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonRegistrarMadeiraForno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(164, 164, 164))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabelFazenda, jLabelMunicipio, jLabelTalhao, jLabelUPC, jLabelVolumeMadeiraPraca});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegistrarMadeiraFornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarMadeiraFornoActionPerformed
        RegistrarMadeiraForno();
    }//GEN-LAST:event_jButtonRegistrarMadeiraFornoActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

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
            java.util.logging.Logger.getLogger(InserirMadeiraForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new InserirMadeiraForno().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRegistrarMadeiraForno;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelFazenda;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelMaterialGenetico;
    private javax.swing.JLabel jLabelMunicipio;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTalhao;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelUPC;
    private javax.swing.JLabel jLabelVolumeMadeiraForno;
    private javax.swing.JLabel jLabelVolumeMadeiraPraca;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerUmidade;
    private javax.swing.JTable jTableForno;
    // End of variables declaration//GEN-END:variables
}
