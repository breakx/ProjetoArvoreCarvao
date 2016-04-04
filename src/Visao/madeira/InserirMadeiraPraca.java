/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.madeira;

import Controle.ControleMadeira;
import Controle.ControlePrincipal;
import Controle.madeira.InserirMadeiraCtrl;
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
public class InserirMadeiraPraca extends javax.swing.JFrame {
   
    float volumeMadeiraMStereo = 0;
    float volumeMadeiraM3 = 0;
    float fator_emp = 0;
    
    /**
     * Creates new form CadastrarSaidaMadeiraTalhao
     */
    public InserirMadeiraPraca() {
        initComponents();
        CarregarNome();
    }
    
    public void CalcularVolumeTalhao(){
        if(jCheckBoxBT.isSelected()){
            if(!jTextFieldTranporteA_H1.getText().equals("0") && !jTextFieldTranporteA_H2.getText().equals("0") && !jTextFieldTranporteA_H3.getText().equals("0") && !jTextFieldTranporteA_Largura.getText().equals("0")&& !jTextFieldTranporteA_Comprimento.getText().equals("0") && 
                    !jTextFieldTranporteA_H1.getText().equals("0") && !jTextFieldTranporteA_H2.getText().equals("0") && !jTextFieldTranporteA_H3.getText().equals("0") && !jTextFieldTranporteA_Largura.getText().equals("0")&& !jTextFieldTranporteA_Comprimento.getText().equals("0")){
                volumeMadeiraMStereo = (((Float.parseFloat(jTextFieldTranporteA_H1.getText()) * Float.parseFloat(jTextFieldTranporteA_H2.getText()) * Float.parseFloat(jTextFieldTranporteA_H3.getText()))/3)
                    *(Float.parseFloat(jTextFieldTranporteA_Largura.getText()) * Float.parseFloat(jTextFieldTranporteA_Comprimento.getText())) +
                        ((Float.parseFloat(jTextFieldTranporteBT_H1.getText()) * Float.parseFloat(jTextFieldTranporteBT_H2.getText()) * Float.parseFloat(jTextFieldTranporteBT_H3.getText()))/3)
                    *(Float.parseFloat(jTextFieldTranporteBT_Largura.getText()) * Float.parseFloat(jTextFieldTranporteBT_Comprimento.getText())));
                    //JOptionPane.showMessageDialog(null, "Dados ok: "+volumePraca);   
            }
        }else{
            if(!jTextFieldTranporteA_H1.getText().equals("0") && !jTextFieldTranporteA_H2.getText().equals("0") && !jTextFieldTranporteA_H3.getText().equals("0") && !jTextFieldTranporteA_Largura.getText().equals("0")&& !jTextFieldTranporteA_Comprimento.getText().equals("0")){
                volumeMadeiraMStereo = ((Float.parseFloat(jTextFieldTranporteA_H1.getText()) * Float.parseFloat(jTextFieldTranporteA_H2.getText()) * Float.parseFloat(jTextFieldTranporteA_H3.getText()))/3)
                    *(Float.parseFloat(jTextFieldTranporteA_Largura.getText()) * Float.parseFloat(jTextFieldTranporteA_Comprimento.getText()));
                    //JOptionPane.showMessageDialog(null, "Dados ok: "+volumePraca);   
            }else{
                JOptionPane.showMessageDialog(null, "Campo em branco ou 0!");
                jCheckBoxBT.setSelected(false);
                //CalcularVolumeTalhao();
            }
        }
        jLabelVolumeMadeiraMSt.setText("Volume madeira: "+volumeMadeiraMStereo+" mst"); 
        /*if(ControlePrincipal.fator_emp > 0){
            volumeMadeiraM3 = volumeMadeiraMStereo / ControlePrincipal.fator_emp;
        }else{
            JOptionPane.showMessageDialog(null, "Fator = 0, volume incorreto! " + ControlePrincipal.fator_emp);
        }*/    
        volumeMadeiraM3 = volumeMadeiraMStereo / ControlePrincipal.fator_emp;
        jLabelVolumeMadeiraM3.setText("Volume madeira: "+volumeMadeiraM3+" m³");   
        AtualizarDadosMadeira();
    }   
    
    private void AtualizarDadosMadeira(){                
        ControlePrincipal.vol_mad_real += volumeMadeiraM3;
        ControlePrincipal.vol_mad_balanco = ControlePrincipal.vol_mad_real - ControlePrincipal.vol_mad_estimado;
        
        ControlePrincipal.mad_ton_real = ControlePrincipal.vol_mad_real * ControlePrincipal.densidade_madeira;
        ControlePrincipal.mad_ton_balanco = ControlePrincipal.mad_ton_real - ControlePrincipal.mad_ton_estimado;
        
        ControlePrincipal.madeira_praca += volumeMadeiraM3;
        
        ControlePrincipal.mad_ton_tot += ControlePrincipal.mad_ton_real;
        
        ControlePrincipal.atualizarDados = "madeira";
        RegistarCargaPraca();
    }
    
    private void RegistarCargaPraca(){
        DateFormat data_entrega = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
        Date date = new Date();
        
        ControleMadeira madeira = new ControleMadeira();
        madeira.setId_operario(ControlePrincipal.id_op);
        madeira.setTalhao(ControlePrincipal.talhao);//selecionar talhao
        madeira.setData_entrega(data_entrega.format(date));
        madeira.setMad_volume_m_stereo(volumeMadeiraMStereo);
        madeira.setMad_volume_m3(volumeMadeiraM3);
        madeira.setAltura1_t(Float.parseFloat(jTextFieldTranporteA_H1.getText()));
        madeira.setAltura2_t(Float.parseFloat(jTextFieldTranporteA_H2.getText()));
        madeira.setAltura3_t(Float.parseFloat(jTextFieldTranporteA_H3.getText()));
        madeira.setComprimento_t(Float.parseFloat(jTextFieldTranporteA_Comprimento.getText()));
        madeira.setLargura_t(Float.parseFloat(jTextFieldTranporteA_Largura.getText()));
        madeira.setPeso_t(Float.parseFloat(jTextFieldTranporteA_Peso.getText()));
        
        if(jCheckBoxBT.isSelected()){
            madeira.setAltura1_bt(Float.parseFloat(jTextFieldTranporteBT_H1.getText()));
            madeira.setAltura2_bt(Float.parseFloat(jTextFieldTranporteBT_H2.getText()));
            madeira.setAltura3_bt(Float.parseFloat(jTextFieldTranporteBT_H3.getText()));
            madeira.setComprimento_bt(Float.parseFloat(jTextFieldTranporteBT_Comprimento.getText()));
            madeira.setLargura_bt(Float.parseFloat(jTextFieldTranporteBT_Largura.getText()));
            madeira.setPeso_bt(Float.parseFloat(jTextFieldTranporteBT_Peso.getText()));
        }    
        
        madeira.setId_estoque(ControlePrincipal.id_estoque_principal);
        
        //JOptionPane.showMessageDialog(null, "Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_real+" carv: "+ControlePrincipal.volume_carvao_real);
                        
        InserirMadeiraCtrl inserir = new InserirMadeiraCtrl(madeira);

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
            Logger.getLogger(InserirMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel6 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldTranporteA_H1 = new javax.swing.JTextField();
        jTextFieldTranporteA_H2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldTranporteA_H3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelVolumeMadeiraMSt = new javax.swing.JLabel();
        jButtonCargaTalhao = new javax.swing.JButton();
        jTextFieldTranporteA_Peso = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldTranporteA_Comprimento = new javax.swing.JTextField();
        jTextFieldTranporteA_Largura = new javax.swing.JTextField();
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
        jLabelName1.setText("Tranportar Madeira Praça");
        jLabelName1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelName1.setPreferredSize(new java.awt.Dimension(275, 70));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(270, 145));

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getSize()+4f));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Bem Vindo");

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
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jLabel6)
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
                .addContainerGap(252, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel1.setText("Altura-1");
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 15));

        jTextFieldTranporteA_H1.setText("0");
        jTextFieldTranporteA_H1.setPreferredSize(new java.awt.Dimension(100, 20));
        jTextFieldTranporteA_H1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTranporteA_H1ActionPerformed(evt);
            }
        });

        jTextFieldTranporteA_H2.setText("0");
        jTextFieldTranporteA_H2.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabel2.setText("Altura-2");
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabel3.setText("Altura-3");
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 15));

        jTextFieldTranporteA_H3.setText("0");
        jTextFieldTranporteA_H3.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabel4.setText("Comprimento");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabel5.setText("Largura");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 15));

        jLabelVolumeMadeiraMSt.setText("Volume madeira: 0 mst");

        jButtonCargaTalhao.setText("Registrar");
        jButtonCargaTalhao.setPreferredSize(new java.awt.Dimension(100, 50));
        jButtonCargaTalhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaTalhaoActionPerformed(evt);
            }
        });

        jTextFieldTranporteA_Peso.setText("0");
        jTextFieldTranporteA_Peso.setPreferredSize(new java.awt.Dimension(100, 20));
        jTextFieldTranporteA_Peso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTranporteA_PesoActionPerformed(evt);
            }
        });

        jLabel7.setText("Peso");
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 15));

        jTextFieldTranporteA_Comprimento.setText("0");
        jTextFieldTranporteA_Comprimento.setPreferredSize(new java.awt.Dimension(100, 20));

        jTextFieldTranporteA_Largura.setText("0");
        jTextFieldTranporteA_Largura.setPreferredSize(new java.awt.Dimension(100, 20));

        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(100, 50));
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
        jTextFieldTranporteBT_H1.setPreferredSize(new java.awt.Dimension(100, 20));
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
                .addGap(66, 66, 66)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldTranporteA_Peso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTranporteA_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldTranporteA_Comprimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldTranporteA_H3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTranporteA_H1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTranporteA_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
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
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabelVolumeMadeiraM3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelVolumeMadeiraMSt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldTranporteA_Comprimento, jTextFieldTranporteA_H1, jTextFieldTranporteA_H2, jTextFieldTranporteA_H3, jTextFieldTranporteA_Largura, jTextFieldTranporteA_Peso});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonCargaTalhao, jButtonVoltar});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldTranporteBT_Comprimento, jTextFieldTranporteBT_H1, jTextFieldTranporteBT_H2, jTextFieldTranporteBT_H3, jTextFieldTranporteBT_Largura, jTextFieldTranporteBT_Peso});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jCheckBoxBT)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldTranporteA_H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldTranporteA_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldTranporteA_H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldTranporteA_Comprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldTranporteA_Largura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldTranporteA_Peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextFieldTranporteBT_H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldTranporteBT_H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldTranporteBT_H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldTranporteBT_Comprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldTranporteBT_Largura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldTranporteBT_Peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabelVolumeMadeiraMSt)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelVolumeMadeiraM3)
                        .addContainerGap(155, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextFieldTranporteA_Comprimento, jTextFieldTranporteA_H1, jTextFieldTranporteA_H2, jTextFieldTranporteA_H3, jTextFieldTranporteA_Largura, jTextFieldTranporteA_Peso});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonCargaTalhao, jButtonVoltar});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextFieldTranporteBT_Comprimento, jTextFieldTranporteBT_H1, jTextFieldTranporteBT_H2, jTextFieldTranporteBT_H3, jTextFieldTranporteBT_Largura, jTextFieldTranporteBT_Peso});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(290, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(510, 510, 510))
                        .addComponent(jLabelName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(7, 7, 7)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldTranporteA_H1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTranporteA_H1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTranporteA_H1ActionPerformed

    private void jButtonCargaTalhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaTalhaoActionPerformed
        CalcularVolumeTalhao();
        //RegistrarCargaTalhao();
    }//GEN-LAST:event_jButtonCargaTalhaoActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jTextFieldTranporteA_PesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTranporteA_PesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTranporteA_PesoActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jTextFieldTranporteBT_PesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTranporteBT_PesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTranporteBT_PesoActionPerformed

    private void jTextFieldTranporteBT_H1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTranporteBT_H1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTranporteBT_H1ActionPerformed

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
            java.util.logging.Logger.getLogger(InserirMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InserirMadeiraPraca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCargaTalhao;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JCheckBox jCheckBoxBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelName1;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelVolumeMadeiraM3;
    private javax.swing.JLabel jLabelVolumeMadeiraMSt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldTranporteA_Comprimento;
    private javax.swing.JTextField jTextFieldTranporteA_H1;
    private javax.swing.JTextField jTextFieldTranporteA_H2;
    private javax.swing.JTextField jTextFieldTranporteA_H3;
    private javax.swing.JTextField jTextFieldTranporteA_Largura;
    private javax.swing.JTextField jTextFieldTranporteA_Peso;
    private javax.swing.JTextField jTextFieldTranporteBT_Comprimento;
    private javax.swing.JTextField jTextFieldTranporteBT_H1;
    private javax.swing.JTextField jTextFieldTranporteBT_H2;
    private javax.swing.JTextField jTextFieldTranporteBT_H3;
    private javax.swing.JTextField jTextFieldTranporteBT_Largura;
    private javax.swing.JTextField jTextFieldTranporteBT_Peso;
    // End of variables declaration//GEN-END:variables
}
