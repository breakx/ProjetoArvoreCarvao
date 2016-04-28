/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.fazenda;

import Controle.ControleFazenda;
import Controle.ControlePrincipal;
import Controle.fazenda.InserirFazendaCtrl;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.estoqueprincipal.GerenciarEstoquePrincipal;
import Visao.estoqueprincipal.InserirEstoquePrincipal;
import Visao.login.Login;
import Visao.relatorios.GerarRelatorioEstoquePrincipal;
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
public class GerenciarFazenda extends javax.swing.JFrame {

    private int projeto = 0;
    /**
     * Creates new form GereciarFazenda
     * @throws java.sql.SQLException
     */
    public GerenciarFazenda() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH); 
        jButtonExcluir.setVisible(false);
        CarregarNome();
        PreencherTabela();
    }   
    
    /**
     * 
     */
    private void PreencherTabela(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[] { 
            "id_fazenda",  
            "estado", 
            "municipio", 
            "bloco", 
            "fazenda", 
            "projeto"
        };
        int tamanho = 0;    
        String query = "Select * from fazenda";
        ConexaoBD con = ConexaoBD.getConexao();         
        ResultSet rs = con.consultaSql(query);
        
        try {
            while(rs.next()){
                dados.add(new Object[]{
                    rs.getString("id_fazenda"),
                    rs.getString("estado"),
                    rs.getString("municipio"),
                    rs.getString("bloco"),
                    rs.getString("fazenda"),
                    rs.getString("projeto")
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        GerarTabela modelo = new GerarTabela(dados, colunas);
        jTableFazenda.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableFazenda.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableFazenda.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableFazenda.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }
            jTableFazenda.getColumnModel().getColumn(i).setResizable(false);
            //System.out.println("Indice: "+i+" - "+ colunas[i].length()*200);
        }
        jTableFazenda.getTableHeader().setReorderingAllowed(false);
        jTableFazenda.setAutoResizeMode(jTableFazenda.AUTO_RESIZE_OFF);
        jTableFazenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        con.fecharConexao();
    }
    
    private void InserirInfo(){
        new InserirFazenda().setVisible(true);
        this.setVisible(false);
        dispose();
    }
    
    private void AdicionarProjeto(){
        if(jTableFazenda.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {                       
            int linha = jTableFazenda.getSelectedRow();
            String id_fazenda = jTableFazenda.getValueAt(linha, 0).toString();
            String estado = jTableFazenda.getValueAt(linha, 1).toString();
            String municipio = jTableFazenda.getValueAt(linha, 2).toString();
            String bloco = jTableFazenda.getValueAt(linha, 3).toString();
            String fazenda = jTableFazenda.getValueAt(linha, 4).toString();
            //projeto = ValidarProjeto(jTableFazenda.getValueAt(linha, 5).toString());
            String query = "Select projeto from fazenda where municipio = '"+municipio+"' and fazenda = '"+fazenda+"'";
            ConexaoBD con = ConexaoBD.getConexao();
            ResultSet rs = con.consultaSql(query);
                try {
                    while(rs.next()){
                        projeto++;
                    }   
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarFazenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            JOptionPane.showMessageDialog(null, "Projeto: "+projeto);  
            con.fecharConexao();
            
            if(projeto<10){
                projeto++;
            
                ControleFazenda faz = new ControleFazenda();
                faz.setId_fazenda(id_fazenda);
                faz.setEstado(estado);
                faz.setBloco(bloco);
                faz.setMunicipio(municipio);
                faz.setFazenda(fazenda);
                faz.setProjeto(DefinirProjeto(projeto));

                //SELECT * FROM notas WHERE id_notas >= 0 limit 10

                InserirFazendaCtrl inserir = new InserirFazendaCtrl(faz); 
            }else{
                JOptionPane.showMessageDialog(null, "Fazenda atingiu limite max de "+projeto+" projetos.");
            }
            
            try {
                new GerenciarFazenda().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(InserirFazenda.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private String DefinirProjeto(int valor){
        String proj="I";
        switch(valor){
            case 1:
                proj="I";
                break;
            case 2:
                proj="II";
                break;
            case 3:
                proj="III";
                break;
            case 4:
                proj="IV";
                break;
            case 5:
                proj="V";
                break;
            case 6:
                proj="VI";
                break;
            case 7:
                proj="VII";
                break;
            case 8:
                proj="VIII";
                break;
            case 9:
                proj="IX";
                break;
            case 10:
                proj="X";
                break;
        }        
        return proj;
    }
    
    private int ValidarProjeto(String texto){
        int proj=0;
        switch(texto){
            case "I":
                proj=1;
                break;
            case "II":
                proj=2;
                break;
            case "III":
                proj=3;
                break;
            case "IV":
                proj=4;
                break;
            case "V":
                proj=5;
                break;
            case "VI":
                proj=6;
                break;
            case "VII":
                proj=7;
                break;
            case "VIII":
                proj=8;
                break;
            case "IX":
                proj=9;
                break;
            case "X":
                proj=10;
                break;
        }        
        return proj;
    }
    
    private void AlterarInfo(){
        if(jTableFazenda.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableFazenda.getSelectedRow();
            String id_fazenda = jTableFazenda.getValueAt(linha, 0).toString();
            String estado = jTableFazenda.getValueAt(linha, 1).toString();
            String municipio = jTableFazenda.getValueAt(linha, 2).toString();
            String bloco = jTableFazenda.getValueAt(linha, 3).toString();
            String fazenda = jTableFazenda.getValueAt(linha, 4).toString();
            String projeto = jTableFazenda.getValueAt(linha, 5).toString();

            new AlterarFazenda(id_fazenda, estado, municipio, fazenda, bloco, projeto).setVisible(true);
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void ExcluirInfo(){
        if(jTableFazenda.getSelectedRow()>=0) {
            int linha = jTableFazenda.getSelectedRow();
            String id_fazenda = jTableFazenda.getValueAt(linha, 0).toString();
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    } 
    
    private void InserirEstoque(){
        if(jTableFazenda.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableFazenda.getSelectedRow();
            //String id_fazenda = jTableFazenda.getValueAt(linha, 0).toString();
            ControlePrincipal.estado = jTableFazenda.getValueAt(linha, 1).toString();
            ControlePrincipal.municipio = jTableFazenda.getValueAt(linha, 2).toString();
            ControlePrincipal.bloco = jTableFazenda.getValueAt(linha, 3).toString(); 
            ControlePrincipal.fazenda = jTableFazenda.getValueAt(linha, 4).toString();
            ControlePrincipal.projeto = jTableFazenda.getValueAt(linha, 5).toString();     
            
            /*ControleEstoquePrincipal estoque = new ControleEstoquePrincipal();
            estoque.setEstado(jTableFazenda.getValueAt(linha, 1).toString());
            estoque.setMunicipio(jTableFazenda.getValueAt(linha, 2).toString());
            estoque.setFazenda(jTableFazenda.getValueAt(linha, 3).toString());
            estoque.setProjeto(jTableFazenda.getValueAt(linha, 4).toString());
            estoque.setBloco(jTableFazenda.getValueAt(linha, 5).toString());*/
            
            new InserirEstoquePrincipal().setVisible(true);
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void GerenciarEstoque(){
        try {
            new GerenciarEstoquePrincipal().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarFazenda.class.getName()).log(Level.SEVERE, null, ex);
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
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonInserir = new javax.swing.JButton();
        jButtonAdicionarProjeto = new javax.swing.JButton();
        jButtonCriarEstoque = new javax.swing.JButton();
        jButtonGerenciarEstoque = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableFazenda = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Gerenciar Fazenda");
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

        jButtonAlterar.setFont(jButtonAlterar.getFont().deriveFont(jButtonAlterar.getFont().getSize()+1f));
        jButtonAlterar.setText("<html>Editar<br>Fazenda</html>");
        jButtonAlterar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

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

        jButtonInserir.setFont(jButtonInserir.getFont().deriveFont(jButtonInserir.getFont().getSize()+1f));
        jButtonInserir.setText("<html>Nova<br>Fazenda</html>");
        jButtonInserir.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirActionPerformed(evt);
            }
        });

        jButtonAdicionarProjeto.setFont(jButtonAdicionarProjeto.getFont().deriveFont(jButtonAdicionarProjeto.getFont().getSize()+1f));
        jButtonAdicionarProjeto.setText("<html>Adicionar<br>Projeto</html>");
        jButtonAdicionarProjeto.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonAdicionarProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarProjetoActionPerformed(evt);
            }
        });

        jButtonCriarEstoque.setFont(jButtonCriarEstoque.getFont().deriveFont(jButtonCriarEstoque.getFont().getSize()+1f));
        jButtonCriarEstoque.setText("<html>Criar<br>Talhao</html>");
        jButtonCriarEstoque.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonCriarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCriarEstoqueActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonAdicionarProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonCriarEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonGerenciarEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInserir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAdicionarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCriarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGerenciarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonAdicionarProjeto, jButtonAlterar, jButtonCriarEstoque, jButtonExcluir, jButtonGerenciarEstoque, jButtonInserir});

        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jTableFazenda.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTableFazenda);

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
            .addGap(0, 673, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jScrollPane2)
                    .addGap(5, 5, 5)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE))
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        AlterarInfo();
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        ExcluirInfo();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirActionPerformed
        InserirInfo();
    }//GEN-LAST:event_jButtonInserirActionPerformed

    private void jButtonAdicionarProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarProjetoActionPerformed
        AdicionarProjeto();
    }//GEN-LAST:event_jButtonAdicionarProjetoActionPerformed

    private void jButtonCriarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCriarEstoqueActionPerformed
        InserirEstoque();
    }//GEN-LAST:event_jButtonCriarEstoqueActionPerformed

    private void jButtonGerenciarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerenciarEstoqueActionPerformed
        GerenciarEstoque();
    }//GEN-LAST:event_jButtonGerenciarEstoqueActionPerformed

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
            java.util.logging.Logger.getLogger(GerenciarFazenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarFazenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarFazenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarFazenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerenciarFazenda().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarFazenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdicionarProjeto;
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonCriarEstoque;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGerenciarEstoque;
    private javax.swing.JButton jButtonInserir;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableFazenda;
    // End of variables declaration//GEN-END:variables
}
