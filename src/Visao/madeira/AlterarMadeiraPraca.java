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
    private float fator_emp = 1.4f;
    AlterarMadeiraPraca() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Creates new form AlterarMadeiraEntradaPraca
     * 
     */
    public  AlterarMadeiraPraca(String id_controle_madeira, String id_estoque, String altura1_t, String altura2_t, String altura3_t, String comprimento_t, String largura_t, String peso_t, String altura1_bt, String altura2_bt, String altura3_bt, String comprimento_bt, String largura_bt, String peso_bt, String vol_mst, String vol_m3) throws SQLException {
        super("Alterar Madeira");
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);    
        CarregarEstoque(id_estoque);
        CarregarNome();
        this.id = id_controle_madeira;
        volumeMadeiraMStereo_ant = Float.parseFloat(vol_mst);
        volumeMadeiraM3_ant = Float.parseFloat(vol_m3);
        jTextFieldTranporteT_H1.setText(altura1_t);
        jTextFieldTranporteT_H2.setText(altura2_t);
        jTextFieldTranporteT_H3.setText(altura2_t);
        jTextFieldTranporteT_Comprimento.setText(comprimento_t);
        jTextFieldTranporteT_Largura.setText(largura_t);
        jTextFieldTranporteT_Peso.setText(peso_t);
        
        if(!altura1_bt.equals("0")){
            jCheckBoxBT.setSelected(true);
            jTextFieldTranporteBT_H1.setText(altura1_t);
            jTextFieldTranporteBT_H2.setText(altura2_t);
            jTextFieldTranporteBT_H3.setText(altura2_t);
            jTextFieldTranporteBT_Comprimento.setText(comprimento_t);
            jTextFieldTranporteBT_Largura.setText(largura_t);
            jTextFieldTranporteBT_Peso.setText(peso_t);
        }else{
            jCheckBoxBT.setSelected(false);
            jTextFieldTranporteBT_H1.setText(null);
            jTextFieldTranporteBT_H2.setText(null);
            jTextFieldTranporteBT_H3.setText(null);
            jTextFieldTranporteBT_Comprimento.setText(null);
            jTextFieldTranporteBT_Largura.setText(null);
            jTextFieldTranporteBT_Peso.setText(null);
        }
    }     
    
    private void CarregarEstoque(String id_estq) throws SQLException{
        
        String query = "Select vol_mad_transp, mad_ton_transp, madeira_praca from estoque_principal where id_estoque_p = "+id_estq;
        ConexaoBD con = ConexaoBD.getConexao();
        
        ResultSet rs = con.consultaSql(query);
        JOptionPane.showMessageDialog(null, "Carregando Estoque: "+query);
        while(rs.next()){
            /*ControlePrincipal.volume_madeira_talhao = Float.parseFloat(rs.getString("madeira_talhao")); 
            ControlePrincipal.volume_madeira_praca = Float.parseFloat(rs.getString("madeira_praca")); 
            ControlePrincipal.volume_madeira_forno = Float.parseFloat(rs.getString("madeira_forno")); 
            ControlePrincipal.volume_madeira_transpl = Float.parseFloat(rs.getString("mad_ton_tot")); 
            ControlePrincipal.volume_carvao_transp = Float.parseFloat(rs.getString("carv_ton_tot"));   */  
        }
        con.fecharConexao();
    } 
        
    public void CalcularVolumeTalhao(){
        if(jCheckBoxBT.isSelected()){
            if(!jTextFieldTranporteT_H1.getText().equals("0") && !jTextFieldTranporteT_H2.getText().equals("0") && !jTextFieldTranporteT_H3.getText().equals("0") && !jTextFieldTranporteT_Largura.getText().equals("0")&& !jTextFieldTranporteT_Comprimento.getText().equals("0") && 
                    !jTextFieldTranporteT_H1.getText().equals("0") && !jTextFieldTranporteT_H2.getText().equals("0") && !jTextFieldTranporteT_H3.getText().equals("0") && !jTextFieldTranporteT_Largura.getText().equals("0")&& !jTextFieldTranporteT_Comprimento.getText().equals("0")){
                volumeMadeiraMStereo = (((Float.parseFloat(jTextFieldTranporteT_H1.getText()) * Float.parseFloat(jTextFieldTranporteT_H2.getText()) * Float.parseFloat(jTextFieldTranporteT_H3.getText()))/3)
                    *(Float.parseFloat(jTextFieldTranporteT_Largura.getText()) * Float.parseFloat(jTextFieldTranporteT_Comprimento.getText())) +
                        ((Float.parseFloat(jTextFieldTranporteBT_H1.getText()) * Float.parseFloat(jTextFieldTranporteBT_H2.getText()) * Float.parseFloat(jTextFieldTranporteBT_H3.getText()))/3)
                    *(Float.parseFloat(jTextFieldTranporteBT_Largura.getText()) * Float.parseFloat(jTextFieldTranporteBT_Comprimento.getText())));
                    //JOptionPane.showMessageDialog(null, "Dados ok: "+volumePraca);   
            }
        }else{
            if(!jTextFieldTranporteT_H1.getText().equals("0") && !jTextFieldTranporteT_H2.getText().equals("0") && !jTextFieldTranporteT_H3.getText().equals("0") && !jTextFieldTranporteT_Largura.getText().equals("0")&& !jTextFieldTranporteT_Comprimento.getText().equals("0")){
                volumeMadeiraMStereo = ((Float.parseFloat(jTextFieldTranporteT_H1.getText()) * Float.parseFloat(jTextFieldTranporteT_H2.getText()) * Float.parseFloat(jTextFieldTranporteT_H3.getText()))/3)
                    *(Float.parseFloat(jTextFieldTranporteT_Largura.getText()) * Float.parseFloat(jTextFieldTranporteT_Comprimento.getText()));
                    //JOptionPane.showMessageDialog(null, "Dados ok: "+volumePraca);   
            }
        }
        jLabelVolumeMadeiraMSt.setText("Volume madeira: "+volumeMadeiraMStereo+" mst"); 
        if(ControlePrincipal.fator_emp > 0){
            volumeMadeiraM3 = volumeMadeiraMStereo / ControlePrincipal.fator_emp;
        }else{
            JOptionPane.showMessageDialog(null, "Fator = 0, volume incorreto! " + ControlePrincipal.fator_emp);
        }        
        jLabelVolumeMadeiraM3.setText("Volume madeira: "+volumeMadeiraM3+" m³");   
        AtualizarDadosMadeira();
    }    
    
    private void AtualizarDadosMadeira(){                
        ControlePrincipal.vol_mad_transp += volumeMadeiraM3;
        ControlePrincipal.vol_mad_balanco = ControlePrincipal.vol_mad_transp - ControlePrincipal.vol_mad_estimado;
        
        ControlePrincipal.mad_ton_transp = ControlePrincipal.vol_mad_transp * ControlePrincipal.densidade_madeira;
        ControlePrincipal.mad_ton_balanco = ControlePrincipal.mad_ton_transp - ControlePrincipal.mad_ton_estimado;
        
        ControlePrincipal.madeira_praca += volumeMadeiraM3;
        
        ControlePrincipal.mad_ton_tot += ControlePrincipal.mad_ton_transp;
        ControlePrincipal.atualizarDados = "madeira";
        RegistarCargaPraca();
    }
    
    private void RegistarCargaPraca(){
        DateFormat data_entrega = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        Date date = new Date();
        
        ControleMadeira madeira = new ControleMadeira();
        madeira.setId_operario(ControlePrincipal.id_op);
        madeira.setTalhao(ControlePrincipal.talhao);//selecionar talhao
        madeira.setData_entrega(data_entrega.format(date));
        madeira.setMad_volume_m_stereo(volumeMadeiraMStereo);
        madeira.setMad_volume_m3(volumeMadeiraM3);
        madeira.setAltura1_t(Float.parseFloat(jTextFieldTranporteT_H1.getText()));
        madeira.setAltura2_t(Float.parseFloat(jTextFieldTranporteT_H2.getText()));
        madeira.setAltura3_t(Float.parseFloat(jTextFieldTranporteT_H3.getText()));
        madeira.setComprimento_t(Float.parseFloat(jTextFieldTranporteT_Comprimento.getText()));
        madeira.setLargura_t(Float.parseFloat(jTextFieldTranporteT_Largura.getText()));
        madeira.setPeso_t(Float.parseFloat(jTextFieldTranporteT_Peso.getText()));
        
        if(jCheckBoxBT.isSelected()){
            madeira.setAltura1_bt(Float.parseFloat(jTextFieldTranporteBT_H1.getText()));
            madeira.setAltura2_bt(Float.parseFloat(jTextFieldTranporteBT_H2.getText()));
            madeira.setAltura3_bt(Float.parseFloat(jTextFieldTranporteBT_H3.getText()));
            madeira.setComprimento_bt(Float.parseFloat(jTextFieldTranporteBT_Comprimento.getText()));
            madeira.setLargura_bt(Float.parseFloat(jTextFieldTranporteBT_Largura.getText()));
            madeira.setPeso_bt(Float.parseFloat(jTextFieldTranporteBT_Peso.getText()));
        }
        
        madeira.setId_estoque(ControlePrincipal.id_estoque_principal);
        
        //JOptionPane.showMessageDialog(null, "Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_transp+" carv: "+ControlePrincipal.volume_carvao_transp);
        
        AlterarMadeiraCtrl alterar = new AlterarMadeiraCtrl(madeira);

        try {
            new GerenciarMadeiraPraca().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(InserirMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    }   
    
    private void VoltarMenu(){
        try {   
            new GerenciarMadeiraPraca().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(AlterarMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabelName1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldTranporteT_H1 = new javax.swing.JTextField();
        jTextFieldTranporteT_H2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldTranporteT_H3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelVolumeMadeiraMSt = new javax.swing.JLabel();
        jButtonCargaTalhao = new javax.swing.JButton();
        jTextFieldTranporteT_Peso = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldTranporteT_Comprimento = new javax.swing.JTextField();
        jTextFieldTranporteT_Largura = new javax.swing.JTextField();
        jButtonVoltar = new javax.swing.JButton();
        jCheckBoxBT = new javax.swing.JCheckBox();
        jTextFieldTranporteBT_H3 = new javax.swing.JTextField();
        jTextFieldTranporteBT_Peso = new javax.swing.JTextField();
        jTextFieldTranporteBT_H1 = new javax.swing.JTextField();
        jTextFieldTranporteBT_Comprimento = new javax.swing.JTextField();
        jTextFieldTranporteBT_H2 = new javax.swing.JTextField();
        jTextFieldTranporteBT_Largura = new javax.swing.JTextField();
        jLabelVolumeMadeiraM3 = new javax.swing.JLabel();

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

        jLabel2.setText("Altura-1");
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldTranporteT_H1.setText("0");
        jTextFieldTranporteT_H1.setPreferredSize(new java.awt.Dimension(200, 25));
        jTextFieldTranporteT_H1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTranporteT_H1ActionPerformed(evt);
            }
        });

        jTextFieldTranporteT_H2.setText("0");
        jTextFieldTranporteT_H2.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabel3.setText("Altura-2");
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabel4.setText("Altura-3");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 15));

        jTextFieldTranporteT_H3.setText("0");
        jTextFieldTranporteT_H3.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabel5.setText("Comprimento");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabel12.setText("Largura");
        jLabel12.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelVolumeMadeiraMSt.setText("Volume madeira: 0 mst");
        jLabelVolumeMadeiraMSt.setPreferredSize(new java.awt.Dimension(108, 25));

        jButtonCargaTalhao.setText("Registrar");
        jButtonCargaTalhao.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonCargaTalhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaTalhaoActionPerformed(evt);
            }
        });

        jTextFieldTranporteT_Peso.setText("0");
        jTextFieldTranporteT_Peso.setPreferredSize(new java.awt.Dimension(100, 20));
        jTextFieldTranporteT_Peso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTranporteT_PesoActionPerformed(evt);
            }
        });

        jLabel7.setText("Peso");
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 15));

        jTextFieldTranporteT_Comprimento.setText("0");
        jTextFieldTranporteT_Comprimento.setPreferredSize(new java.awt.Dimension(100, 20));

        jTextFieldTranporteT_Largura.setText("0");
        jTextFieldTranporteT_Largura.setPreferredSize(new java.awt.Dimension(100, 20));

        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jCheckBoxBT.setText("bitrem");
        jCheckBoxBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxBTActionPerformed(evt);
            }
        });

        jTextFieldTranporteBT_H3.setText("0");
        jTextFieldTranporteBT_H3.setEnabled(false);
        jTextFieldTranporteBT_H3.setPreferredSize(new java.awt.Dimension(100, 20));

        jTextFieldTranporteBT_Peso.setText("0");
        jTextFieldTranporteBT_Peso.setEnabled(false);
        jTextFieldTranporteBT_Peso.setPreferredSize(new java.awt.Dimension(100, 20));
        jTextFieldTranporteBT_Peso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTranporteBT_PesoActionPerformed(evt);
            }
        });

        jTextFieldTranporteBT_H1.setText("0");
        jTextFieldTranporteBT_H1.setEnabled(false);
        jTextFieldTranporteBT_H1.setPreferredSize(new java.awt.Dimension(200, 25));
        jTextFieldTranporteBT_H1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTranporteBT_H1ActionPerformed(evt);
            }
        });

        jTextFieldTranporteBT_Comprimento.setText("0");
        jTextFieldTranporteBT_Comprimento.setEnabled(false);
        jTextFieldTranporteBT_Comprimento.setPreferredSize(new java.awt.Dimension(100, 20));

        jTextFieldTranporteBT_H2.setText("0");
        jTextFieldTranporteBT_H2.setEnabled(false);
        jTextFieldTranporteBT_H2.setPreferredSize(new java.awt.Dimension(100, 20));

        jTextFieldTranporteBT_Largura.setText("0");
        jTextFieldTranporteBT_Largura.setEnabled(false);
        jTextFieldTranporteBT_Largura.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabelVolumeMadeiraM3.setText("Volume madeira: 0m³");

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
                                .addGap(5, 5, 5)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldTranporteT_Comprimento, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldTranporteT_Peso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jTextFieldTranporteT_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldTranporteT_H3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTranporteT_H1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTranporteT_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jCheckBoxBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldTranporteBT_H3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldTranporteBT_Peso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldTranporteBT_Comprimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldTranporteBT_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldTranporteBT_H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteBT_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelVolumeMadeiraMSt, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                            .addComponent(jLabelVolumeMadeiraM3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldTranporteT_Comprimento, jTextFieldTranporteT_H1, jTextFieldTranporteT_H2, jTextFieldTranporteT_H3, jTextFieldTranporteT_Largura, jTextFieldTranporteT_Peso});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldTranporteBT_Comprimento, jTextFieldTranporteBT_H1, jTextFieldTranporteBT_H2, jTextFieldTranporteBT_H3, jTextFieldTranporteBT_Largura, jTextFieldTranporteBT_Peso});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxBT)
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteT_H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteT_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteBT_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteT_H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteBT_H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteT_Comprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteBT_Comprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteBT_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteT_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTranporteT_Peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTranporteBT_Peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextFieldTranporteBT_H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabelVolumeMadeiraMSt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelVolumeMadeiraM3)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(311, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextFieldTranporteT_Comprimento, jTextFieldTranporteT_H1, jTextFieldTranporteT_H2, jTextFieldTranporteT_H3, jTextFieldTranporteT_Largura, jTextFieldTranporteT_Peso});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextFieldTranporteBT_Comprimento, jTextFieldTranporteBT_H1, jTextFieldTranporteBT_H2, jTextFieldTranporteBT_H3, jTextFieldTranporteBT_Largura, jTextFieldTranporteBT_Peso});

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

    private void jTextFieldTranporteT_H1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTranporteT_H1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTranporteT_H1ActionPerformed

    private void jButtonCargaTalhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaTalhaoActionPerformed
        //CalcularVolumeTalhao();
        //RegistrarCargaTalhao();
    }//GEN-LAST:event_jButtonCargaTalhaoActionPerformed

    private void jTextFieldTranporteT_PesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTranporteT_PesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTranporteT_PesoActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jCheckBoxBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxBTActionPerformed
        if(jCheckBoxBT.isSelected()){
            jTextFieldTranporteBT_H1.setEnabled(true);
            jTextFieldTranporteBT_H2.setEnabled(true);
            jTextFieldTranporteBT_H3.setEnabled(true);
            jTextFieldTranporteBT_Comprimento.setEnabled(true);
            jTextFieldTranporteBT_Largura.setEnabled(true);
            jTextFieldTranporteBT_Peso.setEnabled(true);
        }else{
            jTextFieldTranporteBT_H1.setEnabled(false);
            jTextFieldTranporteBT_H2.setEnabled(false);
            jTextFieldTranporteBT_H3.setEnabled(false);
            jTextFieldTranporteBT_Comprimento.setEnabled(false);
            jTextFieldTranporteBT_Largura.setEnabled(false);
            jTextFieldTranporteBT_Peso.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxBTActionPerformed

    private void jTextFieldTranporteBT_PesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTranporteBT_PesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTranporteBT_PesoActionPerformed

    private void jTextFieldTranporteBT_H1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTranporteBT_H1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTranporteBT_H1ActionPerformed

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
    private javax.swing.JTextField jTextFieldTranporteBT_Comprimento;
    private javax.swing.JTextField jTextFieldTranporteBT_H1;
    private javax.swing.JTextField jTextFieldTranporteBT_H2;
    private javax.swing.JTextField jTextFieldTranporteBT_H3;
    private javax.swing.JTextField jTextFieldTranporteBT_Largura;
    private javax.swing.JTextField jTextFieldTranporteBT_Peso;
    private javax.swing.JTextField jTextFieldTranporteT_Comprimento;
    private javax.swing.JTextField jTextFieldTranporteT_H1;
    private javax.swing.JTextField jTextFieldTranporteT_H2;
    private javax.swing.JTextField jTextFieldTranporteT_H3;
    private javax.swing.JTextField jTextFieldTranporteT_Largura;
    private javax.swing.JTextField jTextFieldTranporteT_Peso;
    // End of variables declaration//GEN-END:variables
}
