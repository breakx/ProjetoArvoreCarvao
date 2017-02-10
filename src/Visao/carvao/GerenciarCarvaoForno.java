/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.carvao;

import Controle.ControleCarvao;
import Controle.ControlePrincipal;
import Controle.carvao.AlterarCarvaoCtrl;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.expedircarvao.GerenciarEnvioCarvao;
import Visao.login.Login;
import Visao.relatorios.GerarRelatorioEstoqueBasico;
import Visao.relatorios.GerarRelatorioEstoquePrincipal;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Cristiano GD
 */
public class GerenciarCarvaoForno extends javax.swing.JFrame {
    
    private float dias_resfriando;
    private float dias_carbonizando;
    private float PERIODO_RESFRIAMENTO = 7;
    /**
     * Creates new form Carvao
     * @throws java.sql.SQLException
     */
    public GerenciarCarvaoForno() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        jButtonExcluir.setVisible(false);
        if(!ControlePrincipal.tipo_u.equals("op_ger")){
            jButtonRelatorio.setVisible(false);
        }
        CarregarNome();
        PreencherTabela();
    }   
    
    /**
     * 
     */
    private void PreencherTabela(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[] { 
            "upc_c", //0
            "talhao", //1
            "forno", //2
            "volume_madeira",//3 
            "volume_carvao", //4
            "umidade",//5
            "data_ignicao",//6
            "dias_carbonizando",//7
            "data_fim_carbonizacao",//8
            "dias_resfriando",//9
            "data_entrada_madeira_forno",//10  
            "data_saida_carvao_forno", //11
            "id_estoque_p", //12
            "id_operario", //13
            "rend_grav_forno", //14
            "id_controle_carvao",//15
            "id_forno"//16
        };
        String query;
        int tamanho = 0;
        if(ControlePrincipal.tipo_u.equals("op_ger")){
            query = "Select * from controle_carvao order by id_controle_carvao desc";
        }else{
            query = "Select * from controle_carvao where id_operario = '" +ControlePrincipal.id_op+"' order by id_controle_carvao desc";
        }
        
        ConexaoBD con = ConexaoBD.getConexao(0);
        ResultSet rs = con.consultaSql(query);
        try {
            if(rs != null) {
                while(rs.next()){
                    dados.add(new Object[]{
                        rs.getString("upc_c"),//0
                        rs.getString("talhao"),//1
                        rs.getString("forno"),//2
                        rs.getString("volume_madeira"),//3              
                        rs.getString("volume_carvao"),//4
                        rs.getString("umidade"),//5
                        rs.getString("data_ignicao"),//6
                        dias_carbonizando = CalcularDias(rs.getTimestamp("data_ignicao"), rs.getTimestamp("data_fim_carbonizacao")),//7
                        rs.getString("data_fim_carbonizacao"),//8
                        dias_resfriando = CalcularDias(rs.getTimestamp("data_fim_carbonizacao"), rs.getTimestamp("data_saida_carvao_forno")),//9
                        rs.getString("data_entrada_madeira_forno"),//10
                        rs.getString("data_saida_carvao_forno"),//11
                        rs.getString("id_estoque_p"),//12
                        rs.getString("id_operario"),//13
                        rs.getString("rend_grav_forno"),//14
                        rs.getString("id_controle_carvao"),//15
                        rs.getString("id_forno")//16                            
                    });
                    //System.out.println("Data carvão: "+rs.getTimestamp("data_entrada_madeira_forno"));
                    tamanho++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        GerarTabela modelo = new GerarTabela(dados, colunas);
        jTableCarvao.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableCarvao.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }  
            if(i>9 && !ControlePrincipal.tipo_u.equals("op_ger")){
                jTableCarvao.getColumnModel().getColumn(i).setMinWidth(0);     
                jTableCarvao.getColumnModel().getColumn(i).setPreferredWidth(0);  
                jTableCarvao.getColumnModel().getColumn(i).setMaxWidth(0);
                jTableCarvao.getColumnModel().getColumn(i).setResizable(false);
            }
            //System.out.println("Indice: "+i+" - "+ colunas[i].length()*200);
        }
        jTableCarvao.getTableHeader().setReorderingAllowed(false);
        jTableCarvao.setAutoResizeMode(jTableCarvao.AUTO_RESIZE_OFF);
        jTableCarvao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableCarvao.setSelectionBackground(Color.green);
        
        //duplo click
        jTableCarvao.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                    if(e.getClickCount() == 2){
                        //System.out.println("duplo-clique detectado");
                        AlterarInfo();
                    }
                }
            }); 
        con.fecharConexao();
        RenomearColunas();
    }
    
    private void RenomearColunas(){
        /*for(int i=0;i<colunas.length;i++){
            System.out.println("Indice: "+i+" - "+ colunas[i]);
        }*/
        jTableCarvao.getColumnModel().getColumn(0).setHeaderValue("UPC");
        jTableCarvao.getColumnModel().getColumn(1).setHeaderValue("Talhao");
        jTableCarvao.getColumnModel().getColumn(2).setHeaderValue("Forno");
        jTableCarvao.getColumnModel().getColumn(3).setHeaderValue("Vol. Madeira m³");
        jTableCarvao.getColumnModel().getColumn(4).setHeaderValue("Vol. Carvão m³");
        jTableCarvao.getColumnModel().getColumn(5).setHeaderValue("Umidade");
        jTableCarvao.getColumnModel().getColumn(6).setHeaderValue("Data Ignicao");
        jTableCarvao.getColumnModel().getColumn(7).setHeaderValue("Dias Carbonizando");
        jTableCarvao.getColumnModel().getColumn(8).setHeaderValue("Data Fim Carbonização");
        jTableCarvao.getColumnModel().getColumn(9).setHeaderValue("Dias Resfriando");
        jTableCarvao.getColumnModel().getColumn(10).setHeaderValue("Data Entrada Madeira Forno");
        jTableCarvao.getColumnModel().getColumn(11).setHeaderValue("Data Saida Carvão Forno");
        jTableCarvao.getColumnModel().getColumn(12).setHeaderValue("Nº Estoque");
        jTableCarvao.getColumnModel().getColumn(13).setHeaderValue("Operario");
        jTableCarvao.getColumnModel().getColumn(14).setHeaderValue("Rend. Grav. Forno");
        jTableCarvao.getColumnModel().getColumn(15).setHeaderValue("Numero");
        jTableCarvao.getColumnModel().getColumn(16).setHeaderValue("Nº Forno");
    }
        
    
    private void AlterarInfo(){
        System.out.println("Erro!");
        if(jTableCarvao.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableCarvao.getSelectedRow();
            
            /*for(int i=0; i<10;i++){                
                System.out.println("coluna["+i+"]: "+jTableCarvao.getValueAt(linha, i).toString());
            }*/
            
            String id_controle_carvao = jTableCarvao.getValueAt(linha, 15).toString();
            String id_estoque = jTableCarvao.getValueAt(linha, 12).toString();
            //String talhao = jTableCarvao.getValueAt(linha, 0).toString();
            //String forno = jTableCarvao.getValueAt(linha, 1).toString();
            String volume_madeira = jTableCarvao.getValueAt(linha, 3).toString();
            //String data_entrada_madeira_forno = jTableCarvao.getValueAt(linha, 6).toString();
            String id_operario = jTableCarvao.getValueAt(linha, 13).toString();
            String volume_carvao = jTableCarvao.getValueAt(linha, 4).toString();
            String data_saida_carvao_forno = jTableCarvao.getValueAt(linha, 11).toString();
            //String rend_grav_forno = jTableCarvao.getValueAt(linha, 10).toString();
            String id_forno = jTableCarvao.getValueAt(linha, 16).toString();
            float dias_resf = 0;
            System.out.println("dias_resf: "+dias_resf);
            dias_resf =  Float.parseFloat(jTableCarvao.getValueAt(linha, 9).toString());
            if(dias_resf>PERIODO_RESFRIAMENTO){
                if(volume_carvao.equals("0")){
                    try {
                        new Alterar_RetirarCarvaoForno(id_controle_carvao, id_estoque, id_forno, volume_madeira).setVisible(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
                    }          
                    this.setVisible(false);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Processo de carbonização finalizado!");
                }
            }else{
                int res = JOptionPane.showConfirmDialog(  
                        null,
                        "Abrir o forno antes do periodo de "+PERIODO_RESFRIAMENTO+" dias pode comprometer a produção!"
                                + "\nDeseja abrir assim mesmo?" ,
                        "",
                        JOptionPane.YES_NO_OPTION);

                if(res == JOptionPane.YES_OPTION)
                {
                    JOptionPane.showMessageDialog(null, "Forno aberto  "+(PERIODO_RESFRIAMENTO-dias_resfriando) +" antes do recomendado!");
                    if(volume_carvao.equals("0")){
                    try {
                        new Alterar_RetirarCarvaoForno(id_controle_carvao, id_estoque, id_forno, volume_madeira).setVisible(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
                    }          
                    this.setVisible(false);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Processo de carbonização finalizado!");
                }
                }else{
                    JOptionPane.showMessageDialog(null, "Atividade cancelada!");
                }            
            }            
        }else{
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        }
    }
    
    private void IgnicaoForno_FinalizarCarbonizacao(){
        DateFormat data_forno = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        if(jTableCarvao.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableCarvao.getSelectedRow();
            ControleCarvao carvao = new ControleCarvao();
            //System.out.println("Situacao data: "+jTableCarvao.getValueAt(linha, 16));
            if(jTableCarvao.getValueAt(linha, 6)==null){//ignicao                
                carvao.setId_controle_carvao(jTableCarvao.getValueAt(linha, 15).toString());
                carvao.setId_operario(ControlePrincipal.id_op);
                carvao.setData_ignicao(data_forno.format(date));
                ControlePrincipal.condicao_forno="Carbonizando";
                ControlePrincipal.id_forno_usado = jTableCarvao.getValueAt(linha, 16).toString();
                //JOptionPane.showMessageDialog(null, "id: "+ControlePrincipal.id_estoque_principal+"Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_transp+" carv: "+ControlePrincipal.volume_carvao_transp);
                //System.out.println("Ignição id forno: "+ControlePrincipal.id_forno_usado);
                AlterarCarvaoCtrl alterar = new AlterarCarvaoCtrl(carvao);
                try {
                    new GerenciarCarvaoForno().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.setVisible(false);
                dispose();
            
            }else if(jTableCarvao.getValueAt(linha, 8)==null){//fim carbonização
                carvao.setId_controle_carvao(jTableCarvao.getValueAt(linha, 15).toString());
                carvao.setId_operario(ControlePrincipal.id_op);
                carvao.setData_fim_carbonizacao(data_forno.format(date));
                ControlePrincipal.condicao_forno="Resfriando";
                ControlePrincipal.id_forno_usado = jTableCarvao.getValueAt(linha, 16).toString();
                //JOptionPane.showMessageDialog(null, "id: "+ControlePrincipal.id_estoque_principal+"Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_transp+" carv: "+ControlePrincipal.volume_carvao_transp);
                System.out.println("Ignição id forno: "+ControlePrincipal.id_forno_usado);
                AlterarCarvaoCtrl alterar = new AlterarCarvaoCtrl(carvao);
                try {
                    new GerenciarCarvaoForno().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.setVisible(false);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Periodo de carbonização finalizado, confira o forno!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        }
    }     
        
    private float CalcularDias(Timestamp data_princ, Timestamp data_ctrl){
        //1970-01-01 00:00:00
        float diasDiferenca=0;
        if(data_princ!=null && data_ctrl==null){//dias carbonizando = hj-prin
            DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
            df.setLenient(false);
            long dt = 0;
            try 
            { 
                Date d1 = df.parse (data_princ.toString());
                //System.out.println ("Data I: "+d1);
                Date hoje = new Date(); 
                //System.out.println ("Data hj: "+hoje);
                dt = (hoje.getTime() - d1.getTime()) + (60*60*1000); //3600000 tempo(milisegundos) 1 hora para compensar horário de verão
            } 
            catch (java.text.ParseException evt ) {}        

            diasDiferenca = dt / (60*60*24*1000);//86400000 tempo(milisegundos) em dias
            //System.out.println ("Diferenca: "+diasDiferenca);
            //return diasDiferenca;
        }else if(data_princ!=null && data_ctrl!=null){//fim carb = ctrl - prin
            DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
            df.setLenient(false);
            long dt = 0;
            try 
            { 
                Date d1 = df.parse (data_princ.toString());
                //System.out.println ("Data II: "+d1);
                //Date hoje = new Date(); 
                //System.out.println ("Data hj: "+hoje);
                dt = (data_ctrl.getTime() - d1.getTime()) + (60*60*1000); //3600000 tempo(milisegundos) 1 hora para compensar horário de verão
            } 
            catch (java.text.ParseException evt ) {}        

            diasDiferenca = dt / (60*60*24*1000);//86400000 tempo(milisegundos) em dias
            //System.out.println ("Diferenca: "+diasDiferenca);
            //return diasDiferenca;
        }       
        return diasDiferenca;
    }   

    private void InserirInfo(){
        new InserirMadeiraForno().setVisible(true);
        this.setVisible(false);
        dispose();
    }
    
    private void ExcluirInfo(){
        if(jTableCarvao.getSelectedRow()>=0) {
            int linha = jTableCarvao.getSelectedRow();
            String id_carvao = jTableCarvao.getValueAt(linha, 0).toString();
            new ExcluirInfoMadeiraCarvaoForno(id_carvao).setVisible(true);            
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
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
        jButtonRetirarCarvaoForno = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonBuscarEstoque = new javax.swing.JButton();
        jButtonRelatorio = new javax.swing.JButton();
        jButtonGerenciarEnvioCarvao = new javax.swing.JButton();
        jButtonIgnicao_FimCarbonizacao = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCarvao = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 600));

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Gerenciar Carvão");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(94, 94, 94))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                        .addGap(147, 147, 147))))
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

        jButtonRetirarCarvaoForno.setFont(jButtonRetirarCarvaoForno.getFont().deriveFont(jButtonRetirarCarvaoForno.getFont().getSize()+1f));
        jButtonRetirarCarvaoForno.setText("<html>Retirar<br>Carvao<br>Forno</html>");
        jButtonRetirarCarvaoForno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRetirarCarvaoFornoActionPerformed(evt);
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

        jButtonBuscarEstoque.setFont(jButtonBuscarEstoque.getFont().deriveFont(jButtonBuscarEstoque.getFont().getSize()+1f));
        jButtonBuscarEstoque.setText("<html>Buscar <br>Estoque</html>");
        jButtonBuscarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarEstoqueActionPerformed(evt);
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

        jButtonGerenciarEnvioCarvao.setFont(jButtonGerenciarEnvioCarvao.getFont().deriveFont(jButtonGerenciarEnvioCarvao.getFont().getSize()+1f));
        jButtonGerenciarEnvioCarvao.setText("<html>Gerenciar<br>Envio<br>Carvão</html>");
        jButtonGerenciarEnvioCarvao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerenciarEnvioCarvaoActionPerformed(evt);
            }
        });

        jButtonIgnicao_FimCarbonizacao.setFont(jButtonIgnicao_FimCarbonizacao.getFont().deriveFont(jButtonIgnicao_FimCarbonizacao.getFont().getSize()+1f));
        jButtonIgnicao_FimCarbonizacao.setText("<html>Inicio/Fim<br>Carbonizacao</html>");
        jButtonIgnicao_FimCarbonizacao.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonIgnicao_FimCarbonizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIgnicao_FimCarbonizacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonBuscarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonGerenciarEnvioCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(jButtonIgnicao_FimCarbonizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonRetirarCarvaoForno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonBuscarEstoque, jButtonExcluir, jButtonRetirarCarvaoForno});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonBuscarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGerenciarEnvioCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRetirarCarvaoForno, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIgnicao_FimCarbonizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonBuscarEstoque, jButtonExcluir, jButtonRetirarCarvaoForno});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jScrollPane2.setViewportView(jTableCarvao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRetirarCarvaoFornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRetirarCarvaoFornoActionPerformed
       AlterarInfo();
    }//GEN-LAST:event_jButtonRetirarCarvaoFornoActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        ExcluirInfo();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonBuscarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarEstoqueActionPerformed
        try {
            //new BuscarRelatorioMadeiraEstoquePrincipal().setVisible(true);            
            ControlePrincipal.condicao_carvao="forno";
            new GerarRelatorioEstoquePrincipal().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonBuscarEstoqueActionPerformed

    private void jButtonRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelatorioActionPerformed
        try {
            new GerarRelatorioEstoqueBasico().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonRelatorioActionPerformed

    private void jButtonGerenciarEnvioCarvaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerenciarEnvioCarvaoActionPerformed
        try {
            new GerenciarEnvioCarvao().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonGerenciarEnvioCarvaoActionPerformed

    private void jButtonIgnicao_FimCarbonizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIgnicao_FimCarbonizacaoActionPerformed
        int linha = jTableCarvao.getSelectedRow();
        if(jTableCarvao.getValueAt(linha, 4).toString().equals("0")){
            IgnicaoForno_FinalizarCarbonizacao(); 
        }else{
            JOptionPane.showMessageDialog(null, "Processo de carbonização finalizado!");
        }                           
    }//GEN-LAST:event_jButtonIgnicao_FimCarbonizacaoActionPerformed

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
            java.util.logging.Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerenciarCarvaoForno().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarEstoque;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGerenciarEnvioCarvao;
    private javax.swing.JButton jButtonIgnicao_FimCarbonizacao;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRelatorio;
    private javax.swing.JButton jButtonRetirarCarvaoForno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCarvao;
    // End of variables declaration//GEN-END:variables
}
