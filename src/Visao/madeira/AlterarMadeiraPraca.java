/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.madeira;

import Controle.ControleMadeira;
import Controle.ControlePrincipal;
import Controle.madeira.AlterarMadeiraCtrl;
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
public class AlterarMadeiraPraca extends javax.swing.JFrame {
 
    private String id;
    private float volumeMadeiraMStereo_ant;
    private float volumeMadeiraM3_ant;
    private float volumeMadeiraMStereo = 0;
    private float volumeMadeiraM3 = 0;
    //private float fator_empilalhemto = 1.4f;
    private float vol_mad_transp_bd = 0; 
    private float vol_mad_balanco_bd = 0; 
    private float mad_ton_transp_bd = 0; 
    private float mad_ton_balanco_bd = 0; 
    private float madeira_praca_bd = 0; 
    AlterarMadeiraPraca() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Creates new form AlterarMadeiraEntradaPraca
     * 
     */
    //public  AlterarMadeiraPraca(String id_controle_madeira, String id_estoque, String altura1_t, String altura2_t, String altura3_t, String comprimento_t, String largura_t, String peso_t, String altura1_bt, String altura2_bt, String altura3_bt, String comprimento_bt, String largura_bt, String peso_bt, String vol_mst, String vol_m3) throws SQLException {
    public  AlterarMadeiraPraca(String[] dados) throws SQLException {    super("Alterar Madeira");
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);    
        CarregarEstoque(dados[18]);//id_estoque
        CarregarNome();
        this.id = dados[17];//id_controle_madeira
        volumeMadeiraMStereo_ant = Float.parseFloat(dados[2]);//mad_volume_m_stereo
        volumeMadeiraM3_ant = Float.parseFloat(dados[3]);//mad_volume_m3
        jSpinnerTranporteT_H1.setValue(Float.parseFloat(dados[4]));//altura1_t
        jSpinnerTranporteT_H2.setValue(Float.parseFloat(dados[5]));//altura2_t
        jSpinnerTranporteT_H3.setValue(Float.parseFloat(dados[6]));//altura3_t
        jSpinnerTranporteT_Comprimento.setValue(Float.parseFloat(dados[7]));//comprimento_t
        jSpinnerTranporteT_Largura.setValue(Float.parseFloat(dados[8]));//largura_t
        jSpinnerTranporteT_Peso.setValue(Float.parseFloat(dados[9]));//peso_t
        
        if(!dados[10].equals("0")){//altura1_bt
            jCheckBoxBT.setSelected(true);
            jSpinnerTranporteBT_H1.setEnabled(true);
            jSpinnerTranporteBT_H2.setEnabled(true);
            jSpinnerTranporteBT_H3.setEnabled(true);
            jSpinnerTranporteBT_Comprimento.setEnabled(true);
            jSpinnerTranporteBT_Largura.setEnabled(true);
            jSpinnerTranporteBT_Peso.setEnabled(true);
            jSpinnerTranporteBT_H1.setValue(Float.parseFloat(dados[10]));//altura1_bt
            jSpinnerTranporteBT_H2.setValue(Float.parseFloat(dados[11]));//altura2_bt
            jSpinnerTranporteBT_H3.setValue(Float.parseFloat(dados[12]));//altura3_bt
            jSpinnerTranporteBT_Comprimento.setValue(Float.parseFloat(dados[13]));//comprimento_bt
            jSpinnerTranporteBT_Largura.setValue(Float.parseFloat(dados[14]));//largura_bt
            jSpinnerTranporteBT_Peso.setValue(Float.parseFloat(dados[15]));//peso_bt
        }/*else{
            jCheckBoxBT.setSelected(false);
            jSpinnerTranporteBT_H1.setValue(null);
            jSpinnerTranporteBT_H2.setValue(null);
            jSpinnerTranporteBT_H3.setValue(null);
            jSpinnerTranporteBT_Comprimento.setValue(null);
            jSpinnerTranporteBT_Largura.setValue(null);
            jSpinnerTranporteBT_Peso.setValue(null);
        }*/
        
        jLabelVolumeMadeiraMSt.setText("Volume madeira: "+volumeMadeiraMStereo_ant+" mst");
        jLabelVolumeMadeiraM3.setText("Volume madeira: "+volumeMadeiraM3_ant+" m³");  
        id = dados[17];//id_controle_madeira
        ControlePrincipal.id_estoque_principal = dados[18];//id_estoque_p
    }     
    
    private void CarregarEstoque(String id_estq) throws SQLException{
        
        //String query = "Select vol_mad_transp, vol_mad_balanco, mad_ton_transp, mad_ton_balanco, madeira_praca, fator_empilalhemto from estoque_principal where id_estoque_p = "+id_estq;
        String query = "Select * from estoque_principal where id_estoque_p = "+id_estq;
        ConexaoBD con = ConexaoBD.getConexao(0);
        
        ResultSet rs = con.consultaSql(query);
        //JOptionPane.showMessageDialog(null, "Carregando Estoque: "+query);
        while(rs.next()){ 
            vol_mad_transp_bd = Float.parseFloat(rs.getString("vol_mad_transp")); 
            vol_mad_balanco_bd = Float.parseFloat(rs.getString("vol_mad_balanco")); 
            mad_ton_transp_bd = Float.parseFloat(rs.getString("mad_ton_transp")); 
            mad_ton_balanco_bd = Float.parseFloat(rs.getString("mad_ton_balanco")); 
            madeira_praca_bd = Float.parseFloat(rs.getString("madeira_praca")); 
            
            ControlePrincipal.vol_mad_estimado = Float.parseFloat(rs.getString("vol_mad_estimado")); 
            ControlePrincipal.mad_ton_estimado = Float.parseFloat(rs.getString("mad_ton_estimado")); 
            ControlePrincipal.fator_empilalhemto = Float.parseFloat(rs.getString("fator_empilalhemto")); 
        }
        //System.out.println("Estoque_id: " +id_estq+", "+ vol_mad_transp_bd+", "+ vol_mad_balanco_bd+", "+ mad_ton_transp_bd+", "+ mad_ton_balanco_bd+", "+ madeira_praca_bd+", "+ControlePrincipal.fator_empilalhemto);
        con.fecharConexao();
    } 
        
    public void CalcularVolumeTalhao(){
        if(jCheckBoxBT.isSelected()){
            if(!jSpinnerTranporteT_H1.getValue().equals("0") && !jSpinnerTranporteT_H2.getValue().equals("0") && !jSpinnerTranporteT_H3.getValue().equals("0") && !jSpinnerTranporteT_Largura.getValue().equals("0")&& !jSpinnerTranporteT_Comprimento.getValue().equals("0") && 
                    !jSpinnerTranporteBT_H1.getValue().equals("0") && !jSpinnerTranporteT_H2.getValue().equals("0") && !jSpinnerTranporteT_H3.getValue().equals("0") && !jSpinnerTranporteT_Largura.getValue().equals("0")&& !jSpinnerTranporteT_Comprimento.getValue().equals("0")){
                
                volumeMadeiraMStereo = (((float)jSpinnerTranporteT_H1.getValue() * (float)jSpinnerTranporteT_H2.getValue() * (float)jSpinnerTranporteT_H3.getValue())/3)
                    *((float)jSpinnerTranporteT_Largura.getValue() * (float)jSpinnerTranporteT_Comprimento.getValue()) +
                        (((float)jSpinnerTranporteBT_H1.getValue() * (float)jSpinnerTranporteBT_H2.getValue() * (float)jSpinnerTranporteBT_H3.getValue())/3)
                    *((float)jSpinnerTranporteBT_Largura.getValue() * (float)jSpinnerTranporteBT_Comprimento.getValue());
                    //JOptionPane.showMessageDialog(null, "Dados ok: "+volumePraca);   
            }
        }else{
            if(!jSpinnerTranporteT_H1.getValue().equals("0") && !jSpinnerTranporteT_H2.getValue().equals("0") && !jSpinnerTranporteT_H3.getValue().equals("0") && !jSpinnerTranporteT_Largura.getValue().equals("0")&& !jSpinnerTranporteT_Comprimento.getValue().equals("0")){
                
                volumeMadeiraMStereo = (((float)jSpinnerTranporteT_H1.getValue() * (float)jSpinnerTranporteT_H2.getValue() * (float)jSpinnerTranporteT_H3.getValue())/3)
                    *((float)jSpinnerTranporteT_Largura.getValue() * (float)jSpinnerTranporteT_Comprimento.getValue());
                    //JOptionPane.showMessageDialog(null, "Dados ok: "+volumePraca);   
            }
        }
        jLabelVolumeMadeiraMSt.setText("Volume madeira: "+volumeMadeiraMStereo+" mst"); 
        if(ControlePrincipal.fator_empilalhemto > 0){
            volumeMadeiraM3 = volumeMadeiraMStereo / ControlePrincipal.fator_empilalhemto;
        }else{
            JOptionPane.showMessageDialog(null, "Fator = 0, volume incorreto! " + ControlePrincipal.fator_empilalhemto);
        }        
        jLabelVolumeMadeiraM3.setText("Volume madeira: "+volumeMadeiraM3+" m³");   
        AtualizarDadosMadeira();
    }    
    
    private void AtualizarDadosMadeira(){                
        ControlePrincipal.vol_mad_transp = vol_mad_transp_bd-(volumeMadeiraM3_ant-volumeMadeiraM3);
        ControlePrincipal.vol_mad_balanco = ControlePrincipal.vol_mad_transp - ControlePrincipal.vol_mad_estimado;
        
        ControlePrincipal.mad_ton_transp = mad_ton_transp_bd-(ControlePrincipal.vol_mad_transp * ControlePrincipal.densidade_madeira);
        ControlePrincipal.mad_ton_balanco = ControlePrincipal.mad_ton_transp - ControlePrincipal.mad_ton_estimado;
        
        ControlePrincipal.madeira_praca = madeira_praca_bd-(volumeMadeiraM3_ant-volumeMadeiraM3);
        ControlePrincipal.atualizarDados = "madeira";
        RegistarCargaPraca();
        //System.out.println("Verficar: " + ControlePrincipal.vol_mad_transp+", "+ ControlePrincipal.vol_mad_balanco+", "+ ControlePrincipal.mad_ton_transp+", "+ ControlePrincipal.mad_ton_balanco+", "+ ControlePrincipal.madeira_praca);
    }
    
    private void RegistarCargaPraca(){
        DateFormat data_entrega = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        Date date = new Date();
        
        ControleMadeira madeira = new ControleMadeira();
        madeira.setMad_volume_m_stereo(volumeMadeiraMStereo);
        madeira.setMad_volume_m3(volumeMadeiraM3);
        madeira.setAltura1_t((float)jSpinnerTranporteT_H1.getValue());
        madeira.setAltura2_t((float)jSpinnerTranporteT_H2.getValue());
        madeira.setAltura3_t((float)jSpinnerTranporteT_H3.getValue());
        madeira.setComprimento_t((float)jSpinnerTranporteT_Comprimento.getValue());
        madeira.setLargura_t((float)jSpinnerTranporteT_Largura.getValue());
        madeira.setPeso_t((float)jSpinnerTranporteT_Peso.getValue());
        
        if(jCheckBoxBT.isSelected()){
            madeira.setAltura1_bt((float)jSpinnerTranporteBT_H1.getValue());
            madeira.setAltura2_bt((float)jSpinnerTranporteBT_H2.getValue());
            madeira.setAltura3_bt((float)jSpinnerTranporteBT_H3.getValue());
            madeira.setComprimento_bt((float)jSpinnerTranporteBT_Comprimento.getValue());
            madeira.setLargura_bt((float)jSpinnerTranporteBT_Largura.getValue());
            madeira.setPeso_bt((float)jSpinnerTranporteBT_Peso.getValue());
        }
        
        //madeira.setId_estoque(ControlePrincipal.id_estoque_principal);
        madeira.setId_controle_madeira(id);
        
        //JOptionPane.showMessageDialog(null, "Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_transp+" carv: "+ControlePrincipal.volume_carvao_transp);
        
        AlterarMadeiraCtrl alterar = new AlterarMadeiraCtrl(madeira);

        try {
            new GerenciarMadeiraPraca().setVisible(true);
            this.setVisible(false);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(InserirMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    }   
    
    private void VoltarMenu(){
        try {   
            new GerenciarMadeiraPraca().setVisible(true);
            this.setVisible(false);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(AlterarMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabelName1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelVolumeMadeiraMSt = new javax.swing.JLabel();
        jButtonCargaTalhao = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();
        jCheckBoxBT = new javax.swing.JCheckBox();
        jLabelVolumeMadeiraM3 = new javax.swing.JLabel();
        jSpinnerTranporteT_H1 = new javax.swing.JSpinner();
        jSpinnerTranporteT_H2 = new javax.swing.JSpinner();
        jSpinnerTranporteT_H3 = new javax.swing.JSpinner();
        jSpinnerTranporteT_Comprimento = new javax.swing.JSpinner();
        jSpinnerTranporteT_Largura = new javax.swing.JSpinner();
        jSpinnerTranporteT_Peso = new javax.swing.JSpinner();
        jSpinnerTranporteBT_H1 = new javax.swing.JSpinner();
        jSpinnerTranporteBT_H2 = new javax.swing.JSpinner();
        jSpinnerTranporteBT_H3 = new javax.swing.JSpinner();
        jSpinnerTranporteBT_Comprimento = new javax.swing.JSpinner();
        jSpinnerTranporteBT_Largura = new javax.swing.JSpinner();
        jSpinnerTranporteBT_Peso = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelName1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelName1.setText("Entrada de Madeira na Praça");
        jLabelName1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelName1.setPreferredSize(new java.awt.Dimension(275, 60));

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
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                        .addGap(53, 53, 53))
                    .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(94, 94, 94))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
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

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
        jLabel2.setText("Altura-1");
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+1f));
        jLabel3.setText("Altura-2");
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+1f));
        jLabel4.setText("Altura-3");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+1f));
        jLabel5.setText("Comprimento");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabel12.setFont(jLabel12.getFont().deriveFont(jLabel12.getFont().getSize()+1f));
        jLabel12.setText("Largura");
        jLabel12.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelVolumeMadeiraMSt.setFont(jLabelVolumeMadeiraMSt.getFont().deriveFont(jLabelVolumeMadeiraMSt.getFont().getSize()+1f));
        jLabelVolumeMadeiraMSt.setText("Volume madeira: 0 mst");
        jLabelVolumeMadeiraMSt.setPreferredSize(new java.awt.Dimension(108, 25));

        jButtonCargaTalhao.setFont(jButtonCargaTalhao.getFont().deriveFont(jButtonCargaTalhao.getFont().getSize()+1f));
        jButtonCargaTalhao.setText("Registrar");
        jButtonCargaTalhao.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonCargaTalhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaTalhaoActionPerformed(evt);
            }
        });

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()+1f));
        jLabel7.setText("Peso");
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 15));

        jButtonVoltar.setFont(jButtonVoltar.getFont().deriveFont(jButtonVoltar.getFont().getSize()+1f));
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jCheckBoxBT.setFont(jCheckBoxBT.getFont().deriveFont(jCheckBoxBT.getFont().getSize()+1f));
        jCheckBoxBT.setText("bitrem");
        jCheckBoxBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxBTActionPerformed(evt);
            }
        });

        jLabelVolumeMadeiraM3.setFont(jLabelVolumeMadeiraM3.getFont().deriveFont(jLabelVolumeMadeiraM3.getFont().getSize()+1f));
        jLabelVolumeMadeiraM3.setText("Volume madeira: 0m³");

        jSpinnerTranporteT_H1.setFont(jSpinnerTranporteT_H1.getFont().deriveFont(jSpinnerTranporteT_H1.getFont().getSize()+1f));
        jSpinnerTranporteT_H1.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteT_H1.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteT_H2.setFont(jSpinnerTranporteT_H2.getFont().deriveFont(jSpinnerTranporteT_H2.getFont().getSize()+1f));
        jSpinnerTranporteT_H2.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteT_H2.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteT_H3.setFont(jSpinnerTranporteT_H3.getFont().deriveFont(jSpinnerTranporteT_H3.getFont().getSize()+1f));
        jSpinnerTranporteT_H3.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteT_H3.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteT_Comprimento.setFont(jSpinnerTranporteT_Comprimento.getFont().deriveFont(jSpinnerTranporteT_Comprimento.getFont().getSize()+1f));
        jSpinnerTranporteT_Comprimento.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteT_Comprimento.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteT_Largura.setFont(jSpinnerTranporteT_Largura.getFont().deriveFont(jSpinnerTranporteT_Largura.getFont().getSize()+1f));
        jSpinnerTranporteT_Largura.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(3.3f), Float.valueOf(0.01f)));
        jSpinnerTranporteT_Largura.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteT_Peso.setFont(jSpinnerTranporteT_Peso.getFont().deriveFont(jSpinnerTranporteT_Peso.getFont().getSize()+1f));
        jSpinnerTranporteT_Peso.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteT_Peso.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteBT_H1.setFont(jSpinnerTranporteBT_H1.getFont().deriveFont(jSpinnerTranporteBT_H1.getFont().getSize()+1f));
        jSpinnerTranporteBT_H1.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteBT_H1.setEnabled(false);
        jSpinnerTranporteBT_H1.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteBT_H2.setFont(jSpinnerTranporteBT_H2.getFont().deriveFont(jSpinnerTranporteBT_H2.getFont().getSize()+1f));
        jSpinnerTranporteBT_H2.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteBT_H2.setEnabled(false);
        jSpinnerTranporteBT_H2.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteBT_H3.setFont(jSpinnerTranporteBT_H3.getFont().deriveFont(jSpinnerTranporteBT_H3.getFont().getSize()+1f));
        jSpinnerTranporteBT_H3.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteBT_H3.setEnabled(false);
        jSpinnerTranporteBT_H3.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteBT_Comprimento.setFont(jSpinnerTranporteBT_Comprimento.getFont().deriveFont(jSpinnerTranporteBT_Comprimento.getFont().getSize()+1f));
        jSpinnerTranporteBT_Comprimento.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteBT_Comprimento.setEnabled(false);
        jSpinnerTranporteBT_Comprimento.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteBT_Largura.setFont(jSpinnerTranporteBT_Largura.getFont().deriveFont(jSpinnerTranporteBT_Largura.getFont().getSize()+1f));
        jSpinnerTranporteBT_Largura.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(3.3f), Float.valueOf(0.01f)));
        jSpinnerTranporteBT_Largura.setEnabled(false);
        jSpinnerTranporteBT_Largura.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerTranporteBT_Peso.setFont(jSpinnerTranporteBT_Peso.getFont().deriveFont(jSpinnerTranporteBT_Peso.getFont().getSize()+1f));
        jSpinnerTranporteBT_Peso.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        jSpinnerTranporteBT_Peso.setEnabled(false);
        jSpinnerTranporteBT_Peso.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSpinnerTranporteT_Largura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSpinnerTranporteT_Comprimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSpinnerTranporteT_Peso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSpinnerTranporteT_H1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSpinnerTranporteT_H3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSpinnerTranporteT_H2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxBT, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSpinnerTranporteBT_Comprimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSpinnerTranporteBT_Peso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSpinnerTranporteBT_H1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSpinnerTranporteBT_H3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSpinnerTranporteBT_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSpinnerTranporteBT_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelVolumeMadeiraMSt, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                            .addComponent(jLabelVolumeMadeiraM3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jCheckBoxBT)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteT_H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteT_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteT_H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteBT_H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteT_Comprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteBT_Comprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteT_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteBT_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteT_Peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerTranporteBT_Peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSpinnerTranporteBT_H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jSpinnerTranporteBT_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jLabelVolumeMadeiraMSt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelVolumeMadeiraM3)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(287, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabelVolumeMadeiraM3, jLabelVolumeMadeiraMSt});

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
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE))
                    .addComponent(jLabelName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelName1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonCargaTalhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaTalhaoActionPerformed
        CalcularVolumeTalhao();
        //RegistrarCargaTalhao();
    }//GEN-LAST:event_jButtonCargaTalhaoActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jCheckBoxBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxBTActionPerformed
        if(jCheckBoxBT.isSelected()){
            jSpinnerTranporteBT_H1.setEnabled(true);
            jSpinnerTranporteBT_H2.setEnabled(true);
            jSpinnerTranporteBT_H3.setEnabled(true);
            jSpinnerTranporteBT_Comprimento.setEnabled(true);
            jSpinnerTranporteBT_Largura.setEnabled(true);
            jSpinnerTranporteBT_Peso.setEnabled(true);
        }else{
            jSpinnerTranporteBT_H1.setEnabled(false);
            jSpinnerTranporteBT_H2.setEnabled(false);
            jSpinnerTranporteBT_H3.setEnabled(false);
            jSpinnerTranporteBT_Comprimento.setEnabled(false);
            jSpinnerTranporteBT_Largura.setEnabled(false);
            jSpinnerTranporteBT_Peso.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxBTActionPerformed

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
            java.util.logging.Logger.getLogger(AlterarMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlterarMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlterarMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlterarMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlterarMadeiraPraca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCargaTalhao;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JCheckBox jCheckBoxBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelName1;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelVolumeMadeiraM3;
    private javax.swing.JLabel jLabelVolumeMadeiraMSt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner jSpinnerTranporteBT_Comprimento;
    private javax.swing.JSpinner jSpinnerTranporteBT_H1;
    private javax.swing.JSpinner jSpinnerTranporteBT_H2;
    private javax.swing.JSpinner jSpinnerTranporteBT_H3;
    private javax.swing.JSpinner jSpinnerTranporteBT_Largura;
    private javax.swing.JSpinner jSpinnerTranporteBT_Peso;
    private javax.swing.JSpinner jSpinnerTranporteT_Comprimento;
    private javax.swing.JSpinner jSpinnerTranporteT_H1;
    private javax.swing.JSpinner jSpinnerTranporteT_H2;
    private javax.swing.JSpinner jSpinnerTranporteT_H3;
    private javax.swing.JSpinner jSpinnerTranporteT_Largura;
    private javax.swing.JSpinner jSpinnerTranporteT_Peso;
    // End of variables declaration//GEN-END:variables
}
