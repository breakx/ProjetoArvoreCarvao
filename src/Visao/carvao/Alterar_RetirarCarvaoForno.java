/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.carvao;

import Controle.ControleCarvao;
import Controle.ControlePrincipal;
import Controle.ControleUsuario;
import Controle.carvao.AlterarCarvaoCtrl;
import Modelo.ConexaoBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristiano GD
 */
public class Alterar_RetirarCarvaoForno extends javax.swing.JFrame {

    private String id;
    private String id_forno;
    private float rend_grav_forno = 0;
    private float madeira;
    ControleUsuario usuario = new ControleUsuario();
    
    Alterar_RetirarCarvaoForno() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Creates new form Alterar_RetirarCarvaoForno
     * @param id_controle_carvao
     * @param id_estoque
     * @param madeira_forno
     * @throws java.sql.SQLException
     */
    public Alterar_RetirarCarvaoForno(String id_controle_carvao, String id_estoque, String id_forno, String madeira_forno) throws SQLException {
        super("Alterar Carvao");
        initComponents();
        CarregarNome();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.id = id_controle_carvao;
        this.id_forno = id_forno;
        ControlePrincipal.id_estoque_principal = id_estoque;
        this.madeira = Float.parseFloat(madeira_forno);     
        CarregarEstoque();
    }
    
    private void CarregarEstoque() throws SQLException{
        String query = "Select municipio, fazenda, talhao, upc, vol_mad_transp, densidade_madeira, densidade_carvao, mdc_estimado, mdc_prod, mdc_balanco, carv_ton_estimado, carv_ton_prod, carv_ton_balanco, madeira_praca, carvao_praca, madeira_forno, mad_ton_transp, rend_grav_real, mdc_transp, carv_ton_transp "
                + "from estoque_principal where id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        ConexaoBD con = ConexaoBD.getConexao(0);
        System.out.println("Query : "+ControlePrincipal.rend_grav_real);
        ResultSet rs = con.consultaSql(query);
        //JOptionPane.showMessageDialog(null, "CarregarEstoque: "+query);
        while(rs.next()){ 
            ControlePrincipal.vol_mad_transp = Float.parseFloat(rs.getString("vol_mad_transp"));
            ControlePrincipal.densidade_carvao = Float.parseFloat(rs.getString("densidade_carvao"));
            ControlePrincipal.densidade_madeira = Float.parseFloat(rs.getString("densidade_madeira"));
            ControlePrincipal.mdc_estimado = Float.parseFloat(rs.getString("mdc_estimado"));
            ControlePrincipal.mdc_prod = Float.parseFloat(rs.getString("mdc_prod"));
            ControlePrincipal.mdc_balanco = Float.parseFloat(rs.getString("mdc_balanco"));
            ControlePrincipal.carv_ton_estimado = Float.parseFloat(rs.getString("carv_ton_estimado"));
            ControlePrincipal.carv_ton_prod = Float.parseFloat(rs.getString("carv_ton_prod"));
            ControlePrincipal.carv_ton_balanco = Float.parseFloat(rs.getString("carv_ton_balanco"));
            ControlePrincipal.madeira_praca = Float.parseFloat(rs.getString("madeira_praca"));
            ControlePrincipal.carvao_praca = Float.parseFloat(rs.getString("carvao_praca"));
            ControlePrincipal.madeira_forno = Float.parseFloat(rs.getString("madeira_forno"));
            ControlePrincipal.mdc_transp = Float.parseFloat(rs.getString("mdc_transp"));
            ControlePrincipal.carv_ton_transp = Float.parseFloat(rs.getString("carv_ton_transp"));
            
            ControlePrincipal.mad_ton_transp = Float.parseFloat(rs.getString("mad_ton_transp"));
            ControlePrincipal.rend_grav_real = Float.parseFloat(rs.getString("rend_grav_real"));
            
            ControlePrincipal.municipio = rs.getString("municipio");
            ControlePrincipal.fazenda = rs.getString("fazenda");
            ControlePrincipal.talhao = Integer.parseInt(rs.getString("talhao"));
            ControlePrincipal.upc = Integer.parseInt(rs.getString("upc"));
        }        
        jLabelMunicipio.setText("Municipio: "+ControlePrincipal.municipio);
        jLabelFazenda.setText("Fazenda: "+ControlePrincipal.fazenda);        
        jLabelTalhao.setText("Talhao: "+ControlePrincipal.talhao);        
        jLabelUPC.setText("UPC: "+ControlePrincipal.upc);
        jLabelVolumeMadeiraForno.setText("Volume de madeira forno: "+madeira+" m³");
        con.fecharConexao();
    } 
    
    private void AtualizarDadosCarvao(){                
        ControlePrincipal.mdc_prod += (float) jSpinnerVolumeCarvao.getValue();
        ControlePrincipal.carvao_praca += (float) jSpinnerVolumeCarvao.getValue();
        ControlePrincipal.mdc_balanco = ControlePrincipal.mdc_prod - ControlePrincipal.mdc_estimado;
        
        ControlePrincipal.carv_ton_prod += ControlePrincipal.mdc_prod * ControlePrincipal.densidade_carvao;
        ControlePrincipal.carv_ton_balanco = ControlePrincipal.carv_ton_transp - ControlePrincipal.carv_ton_estimado;
        
        ControlePrincipal.madeira_forno -= madeira;
        
        ControlePrincipal.condicao_forno = "Vazio";
        ControlePrincipal.id_forno_usado = id_forno;
        
        //ControlePrincipal.rend_grav_real = ControlePrincipal.mad_ton_transp/ControlePrincipal.carv_ton_prod;
        //ControlePrincipal.rend_grav_real = (ControlePrincipal.vol_mad_transp-(ControlePrincipal.madeira_praca+ControlePrincipal.madeira_forno))/ControlePrincipal.mdc_prod;
        //ControlePrincipal.rend_grav_real = ((ControlePrincipal.vol_mad_transp-(ControlePrincipal.madeira_praca+ControlePrincipal.madeira_forno))*ControlePrincipal.densidade_madeira)/(ControlePrincipal.mdc_prod*ControlePrincipal.densidade_carvao);
        //System.out.println("Info mad proc : "+(ControlePrincipal.vol_mad_transp-(ControlePrincipal.madeira_praca+ControlePrincipal.madeira_forno))+ " m³");
        //System.out.println("Info carv prod : "+(ControlePrincipal.mdc_prod)+ " m³");
        //System.out.println("Info rend_grav_real: "+ControlePrincipal.rend_grav_real);
        //System.out.println("Densidade m: "+ControlePrincipal.densidade_madeira+" c: "+ControlePrincipal.densidade_carvao);
        //System.out.println("Peso m: "+((ControlePrincipal.vol_mad_transp-(ControlePrincipal.madeira_praca+ControlePrincipal.madeira_forno))*ControlePrincipal.densidade_madeira)+" c: "+(ControlePrincipal.mdc_prod*ControlePrincipal.densidade_carvao));
        //System.out.println("Info coeficiente de Conversão: "+ControlePrincipal.rend_grav_real);
        //rend_grav_forno = (madeira*ControlePrincipal.densidade_madeira)/(float) jSpinnerVolumeCarvao.getValue()*ControlePrincipal.densidade_carvao ;
        //rend_grav_forno = (madeira)/(float) jSpinnerVolumeCarvao.getValue();
        //ControlePrincipal.vol_carv_fornos+=(float) jSpinnerVolumeCarvao.getValue();
        
        rend_grav_forno = (float) jSpinnerVolumeCarvao.getValue()*ControlePrincipal.densidade_carvao /(madeira*ControlePrincipal.densidade_madeira);
        ControlePrincipal.rend_grav_fornos+=rend_grav_forno;
        //ControlePrincipal.rend_grav_real = ControlePrincipal.rend_grav_fornos/ControlePrincipal.vol_carv_fornos;
        ControlePrincipal.rend_grav_real = CalcularRendGravReal();
        jLabelRendimentoForno.setText("Rendimento gravimentrico forno: "+rend_grav_forno);  
        ControlePrincipal.atualizarDados = "carvao";
        //JOptionPane.showMessageDialog(null, "Coeficiente de Conversão: "+ControlePrincipal.rend_grav_real);
        JOptionPane.showMessageDialog(null, "Rendimento gravimentrico: "+ControlePrincipal.rend_grav_real);
        RegistrarCarvaoForno();
    }    
    
    private float CalcularRendGravReal(){
        String query = "SELECT c.*, f.volume_maximo_forno FROM controle_carvao c INNER JOIN forno f on (c.id_forno = f.id_forno) WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        ConexaoBD con = ConexaoBD.getConexao(0);
        ResultSet rs = con.consultaSql(query);
        int volume_max=0;
        float rend_grav_real_atual=0;
        try {
            while(rs.next()){
                rend_grav_real_atual += (rs.getInt("volume_maximo_forno")* rs.getFloat("rend_grav_forno"));
                volume_max += rs.getInt("volume_maximo_forno");
            }
            System.out.println("rend_grav_real_atual : "+rend_grav_real_atual);
            System.out.println("volume_max : "+volume_max);
            
            rend_grav_real_atual=rend_grav_real_atual/volume_max;
            System.out.println("rend_grav_real: "+rend_grav_real_atual);
        } catch (SQLException ex) {
            Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rend_grav_real_atual;
    }
    
    private void RegistrarCarvaoForno(){
        DateFormat data_forno = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        ControleCarvao carvao = new ControleCarvao();
        carvao.setId_controle_carvao(id);
        carvao.setId_estoque(ControlePrincipal.id_estoque_principal);
        carvao.setId_operario(ControlePrincipal.id_op);
        carvao.setVolume_carvao((float) jSpinnerVolumeCarvao.getValue());
        carvao.setData_saida_carvao(data_forno.format(date));
        carvao.setRend_grav_forno(rend_grav_forno);
        ControlePrincipal.condicao_forno = "Vazio";
        
        //JOptionPane.showMessageDialog(null, "id: "+ControlePrincipal.id_estoque_principal+"Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_transp+" carv: "+ControlePrincipal.volume_carvao_transp);
        
        AlterarCarvaoCtrl alterar = new AlterarCarvaoCtrl(carvao);

        try {
            new GerenciarCarvaoForno().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }
    
    private void VoltarMenu(){
        try {
            new GerenciarCarvaoForno().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonRegistrarCarvaoForno = new javax.swing.JButton();
        jButtonVoltar = new javax.swing.JButton();
        jLabelRendimentoForno = new javax.swing.JLabel();
        jLabelMunicipio = new javax.swing.JLabel();
        jLabelFazenda = new javax.swing.JLabel();
        jLabelTalhao = new javax.swing.JLabel();
        jLabelUPC = new javax.swing.JLabel();
        jLabelVolumeMadeiraForno = new javax.swing.JLabel();
        jSpinnerVolumeCarvao = new javax.swing.JSpinner();
        jLabelTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                    .addComponent(jLabelIdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabelIdTipo, jLabelNome});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabelIdTipo, jLabelNome});

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
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

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+1f));
        jLabel1.setText("Volume de carvão forno: ");
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 25));

        jButtonRegistrarCarvaoForno.setFont(jButtonRegistrarCarvaoForno.getFont().deriveFont(jButtonRegistrarCarvaoForno.getFont().getSize()+1f));
        jButtonRegistrarCarvaoForno.setText("Registrar");
        jButtonRegistrarCarvaoForno.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonRegistrarCarvaoForno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarCarvaoFornoActionPerformed(evt);
            }
        });

        jButtonVoltar.setFont(jButtonVoltar.getFont().deriveFont(jButtonVoltar.getFont().getSize()+1f));
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabelRendimentoForno.setFont(jLabelRendimentoForno.getFont().deriveFont(jLabelRendimentoForno.getFont().getSize()+1f));
        jLabelRendimentoForno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRendimentoForno.setText("Rendimento gravimentrico forno:");
        jLabelRendimentoForno.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabelMunicipio.setFont(jLabelMunicipio.getFont().deriveFont(jLabelMunicipio.getFont().getSize()+4f));
        jLabelMunicipio.setText("Municipio: ");
        jLabelMunicipio.setPreferredSize(new java.awt.Dimension(400, 25));

        jLabelFazenda.setFont(jLabelFazenda.getFont().deriveFont(jLabelFazenda.getFont().getSize()+4f));
        jLabelFazenda.setText("Fazenda: ");
        jLabelFazenda.setPreferredSize(new java.awt.Dimension(350, 25));

        jLabelTalhao.setFont(jLabelTalhao.getFont().deriveFont(jLabelTalhao.getFont().getSize()+4f));
        jLabelTalhao.setText("Talhao:");
        jLabelTalhao.setPreferredSize(new java.awt.Dimension(350, 25));

        jLabelUPC.setFont(jLabelUPC.getFont().deriveFont(jLabelUPC.getFont().getSize()+4f));
        jLabelUPC.setText("UPC:");
        jLabelUPC.setPreferredSize(new java.awt.Dimension(350, 25));

        jLabelVolumeMadeiraForno.setFont(jLabelVolumeMadeiraForno.getFont().deriveFont(jLabelVolumeMadeiraForno.getFont().getSize()+1f));
        jLabelVolumeMadeiraForno.setText("Volume de madeira forno: 0.00 m³");
        jLabelVolumeMadeiraForno.setPreferredSize(new java.awt.Dimension(400, 20));

        jSpinnerVolumeCarvao.setFont(jSpinnerVolumeCarvao.getFont().deriveFont(jSpinnerVolumeCarvao.getFont().getSize()+1f));
        jSpinnerVolumeCarvao.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.100000024f)));
        jSpinnerVolumeCarvao.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelRendimentoForno, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelFazenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTalhao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelMunicipio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelUPC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelVolumeMadeiraForno, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jButtonRegistrarCarvaoForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jSpinnerVolumeCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabelFazenda, jLabelMunicipio, jLabelTalhao, jLabelUPC});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelUPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelVolumeMadeiraForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerVolumeCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabelRendimentoForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRegistrarCarvaoForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(429, 429, 429))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabelFazenda, jLabelMunicipio, jLabelTalhao, jLabelUPC});

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Volume de carvão gerado");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 70));

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
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)))
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegistrarCarvaoFornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarCarvaoFornoActionPerformed
        //RegistrarCarvaoForno();
        if((float) jSpinnerVolumeCarvao.getValue()<madeira){
            AtualizarDadosCarvao();
        }else{
            JOptionPane.showMessageDialog(null, "Erro, volume de madeira insuficiente no forno!");
        }
    }//GEN-LAST:event_jButtonRegistrarCarvaoFornoActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        ///new Login().setVisible(true);
        ///dispose();
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
            java.util.logging.Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Alterar_RetirarCarvaoForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Alterar_RetirarCarvaoForno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRegistrarCarvaoForno;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelFazenda;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelMunicipio;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelRendimentoForno;
    private javax.swing.JLabel jLabelTalhao;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelUPC;
    private javax.swing.JLabel jLabelVolumeMadeiraForno;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner jSpinnerVolumeCarvao;
    // End of variables declaration//GEN-END:variables
}
