/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.estoqueprincipal;

import Controle.ControleEstoquePrincipal;
import Controle.ControlePrincipal;
import Controle.estoqueprincipal.AlterarEstoquePrincipalCtrl;
import java.awt.Color;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristiano GD
 */
public class AlterarEstoquePrincipal extends javax.swing.JFrame {

    private String id;
    private float mdc_ha;
    private float rendimento;
    private int talhadia;
    private float idade;
    private String situacao;  
    private float rend_grav_est;
    private float rend_grav_real;
    
    private float volume_madeira_estimado;
    private float mdc_estimado;
    private float mad_ton_ha;
    private float carv_ton_ha;
    private float mad_ton_estimado;
    private float carv_ton_estimado;

    AlterarEstoquePrincipal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
    /**
     * Creates new form AlterarEstoque
     */
    public AlterarEstoquePrincipal(String id_estoque, String upc, String talhao, String area, String m3_ha, String data_plantio, String material_genetico, String talhadia, String ano_rotacao, String data_rotacao_1, String data_rotacao_2, String data_rotacao_3, String idade, String categoria, String situacao, String ima, String mdc_ha, String mdc_estimado, String densidade_carvao, String densidade_madeira, String mdc_balanco, String rend_grav_estimado, String fator_empilalhemto) {
        super("Alterar Carvao");
        initComponents();     
        _carregarCategoria();
        this.id = id_estoque;      
        //jComboBoxEstado.setSelectedIndex(Integer.parseInt(estado));
        jTextFieldUpc.setText(upc);        
        //jComboBoxBloco.setSelectedIndex(Integer.parseInt(bloco));
        //jTextFieldMunicipio.setText(municipio);
        //jTextFieldFazenda.setText(fazenda);  
        //jTextFieldProjeto.setText(projeto);                   
        jTextFieldAnoRotacao.setText(ano_rotacao); 
        jTextFieldTalhao.setText(talhao);        
        jTextFieldArea.setText(area);                    
        jTextFieldM3HA.setText(m3_ha);
        jTextFieldDataPlantio.setText(data_plantio);          
        jTextFieldMaterialGenetico.setText(material_genetico);         
        this.talhadia = Integer.parseInt(talhadia);  
        jTextFieldDataRotacao1.setText(data_rotacao_1);    
        jTextFieldDataRotacao2.setText(data_rotacao_2);     
        jTextFieldDataRotacao3.setText(data_rotacao_3);         
        //jTextFieldIdade.setText(idade); 
        if(categoria.equals("Colheita")){
            jComboBoxCategoria.setSelectedIndex(1); 
        }else if(categoria.equals("Silvicultura")){
            jComboBoxCategoria.setSelectedIndex(2); 
        }else if(categoria.equals("UTM")){
            jComboBoxCategoria.setSelectedIndex(3); 
        }else{
            jComboBoxCategoria.setSelectedIndex(0); 
        }
        this.situacao = situacao; 
        if(situacao =="Exaurida"){
            jCheckBoxExaurida.setSelected(true);
        }else{
            jCheckBoxExaurida.setSelected(false);
        }
        jTextFieldIMA.setText(ima);          
        //jTextFieldMDCHA.setText(mdc_ha);       
        //jTextFieldMDC.setText(mdc);     
        jTextFieldDensidadeCarvao.setText(densidade_carvao);  
        jTextFieldDensidadeMadeira.setText(densidade_madeira);       
        /*jTextFieldIdOperario.setText(id_operario);                  
        jTextFieldDataEstoque.setText(data_estoque);               
        jTextFieldVolumeEstimado.setText(volume_estimado);  
        jTextFieldMadeiraTalhao.setText(madeira_talhao);                     
        jTextFieldMadeiraPraca.setText(madeira_praca);                    
        jTextFieldMadeiraForno.setText(madeira_forno);              
        jTextFieldMadeiraTotal.setText(mad_ton_tot);                      
        jTextFieldCarvaoTotal.setText(carv_ton_tot); */
        rend_grav_est = Float.parseFloat(rend_grav_estimado);
        jTextFieldRendimentoGravimetricoEstimado.setText(rend_grav_estimado);                      
        jTextFieldFatorEmpilhamento.setText(fator_empilalhemto);
        //CalcularMadeiraTotal();
    }   
    
    private void _carregarCategoria(){
        jComboBoxCategoria.addItem("Colheita");
        jComboBoxCategoria.addItem("Silvicultura");
        jComboBoxCategoria.addItem("UTM");  
        CarregarNome();
    }
        
    private void DefinirTalhadia(){
        jLabelIdade.setForeground(Color.BLACK );
        jLabelTalhadia.setForeground(Color.BLACK );
        jLabelSituacao.setForeground(Color.BLACK );       
        
        if(!jTextFieldDataPlantio.getText().equals("00-00-0000") && !jTextFieldDataRotacao1.getText().equals("00-00-0000") && !jTextFieldDataRotacao2.getText().equals("00-00-0000") && !jTextFieldDataRotacao3.getText().equals("00-00-0000")){
            talhadia = 3;
            CalcularIdade(jTextFieldDataRotacao3.getText());                        
        }else if(!jTextFieldDataPlantio.getText().equals("00-00-0000") && !jTextFieldDataRotacao1.getText().equals("00-00-0000") && !jTextFieldDataRotacao2.getText().equals("00-00-0000") && jTextFieldDataRotacao3.getText().equals("00-00-0000")){
            talhadia = 2;
            CalcularIdade(jTextFieldDataRotacao2.getText());            
        }else if(!jTextFieldDataPlantio.getText().equals("00-00-0000") && !jTextFieldDataRotacao1.getText().equals("00-00-0000") && jTextFieldDataRotacao2.getText().equals("00-00-0000") && jTextFieldDataRotacao3.getText().equals("00-00-0000")){
            talhadia = 1;
            CalcularIdade(jTextFieldDataRotacao1.getText());
        }else if(!jTextFieldDataPlantio.getText().equals("00-00-0000") && jTextFieldDataRotacao1.getText().equals("00-00-0000") && jTextFieldDataRotacao2.getText().equals("00-00-0000") && jTextFieldDataRotacao3.getText().equals("00-00-0000")){
            talhadia = 0;
            CalcularIdade(jTextFieldDataPlantio.getText());
        }else {
            jLabelIdade.setForeground(Color.red );
            jLabelIdade.setText("Idade: Erro Data"); 
            jLabelTalhadia.setForeground(Color.red );
            jLabelTalhadia.setText("Talhadia: Erro Data");
            jLabelSituacao.setForeground(Color.red );
            jLabelSituacao.setText("Situação: Erro Data");
        }
    }
     
    /**
     * Calcular Idade
     * @param datainicio 
     */
    private void CalcularIdade(String datainicio){
        DecimalFormat decformat = new DecimalFormat("0.0");
        SimpleDateFormat dataformat = new SimpleDateFormat("dd-MM-yyyy");
        Date hoje = new Date(); 
        java.sql.Date inicio = null;
        java.sql.Date fim = null;
        try {
            inicio = new java.sql.Date(dataformat.parse(datainicio).getTime());
            fim = new java.sql.Date(dataformat.parse(dataformat.format(hoje)).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AlterarEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Calendar c1 = Calendar.getInstance();
        c1.setTime(inicio);
        int w1 = c1.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DAY_OF_WEEK, -w1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(fim);
        int w2 = c2.get(Calendar.DAY_OF_WEEK);
        c2.add(Calendar.DAY_OF_WEEK, -w2);

        float dias = (c2.getTimeInMillis()-c1.getTimeInMillis())/(60*60*24*365);
        idade = dias/1000;
        jLabelIdade.setText("Idade: "+decformat.format(idade)+" anos.");        
        jLabelTalhadia.setText("Talhadia: "+talhadia);
        if(!jCheckBoxExaurida.isSelected()){
            DefinirSituacao();
        }else{
            situacao = "Exaurida";
            jLabelSituacao.setText("Situação: "+situacao);
            VolumeMadEstimado();
        }
    } 
    
    private void DefinirSituacao(){
        if(jComboBoxCategoria.getSelectedIndex()==1){
            if(!jTextFieldDataRotacao1.getText().equals("00-00-0000") || !jTextFieldDataRotacao2.getText().equals("00-00-0000") || !jTextFieldDataRotacao3.getText().equals("00-00-0000")){
                situacao = "Empilhado";
            }else{
                situacao = "Em Pé";
            }
        }else if(jComboBoxCategoria.getSelectedIndex()==2){
            if(!jTextFieldM3HA.getText().equals("0") && !jTextFieldDataPlantio.getText().equals("00-00-0000")){
                situacao = "Inventariado";
            }else if(!jTextFieldDataPlantio.getText().equals("00-00-0000")){
                situacao = "Não Inventariado";
            }else{
                situacao = "Plantio Futuro";
            }
        }else if(jComboBoxCategoria.getSelectedIndex()==3){
            situacao = "---";
        }else{
            situacao = "-";
        }
        jLabelSituacao.setText("Situação: "+situacao);
        CalcularMDCHA();
    }   
    
    private void CalcularMDCHA(){
        mdc_ha = Float.parseFloat(jTextFieldM3HA.getText()) / Float.parseFloat(jTextFieldRendimentoGravimetricoEstimado.getText());
        jLabelMDC_HA.setText("MDC/HA: "+mdc_ha);
        VolumeMadEstimado();
    }
    
    private void VolumeMadEstimado(){
        volume_madeira_estimado = Float.parseFloat(jTextFieldArea.getText())*Float.parseFloat(jTextFieldM3HA.getText());
        jLabelVolumeEstimado.setText("Volume Madeira Estimado: "+volume_madeira_estimado);
        MetroCarvaoEstimado();
    }
    
    private void MetroCarvaoEstimado(){
        mdc_estimado = mdc_ha*Float.parseFloat(jTextFieldArea.getText());
        jLabelMDCEstimado.setText("MDC Estimado: "+mdc_estimado);
        MadeiraToneladaHA();
    }
    
    private void MadeiraToneladaHA(){
        mad_ton_ha = Float.parseFloat(jTextFieldM3HA.getText())* Float.parseFloat(jTextFieldDensidadeMadeira.getText());
        jLabelMadeiraTonHA.setText("Madeira Tonelada HA: "+mad_ton_ha);
        CarvaoToneladaHA();
    }
    
    private void CarvaoToneladaHA(){
        carv_ton_ha = Float.parseFloat(jTextFieldM3HA.getText())* Float.parseFloat(jTextFieldDensidadeCarvao.getText());
        jLabelCarvaoTonHA.setText("Carvão Tonelada HA: "+carv_ton_ha);
        MadeiraToneladaestimado();
    }
    
    private void MadeiraToneladaestimado(){
        mad_ton_estimado = mad_ton_ha * Float.parseFloat(jTextFieldArea.getText());
        jLabelMadeiraTonEst.setText("Madeira Tonelada Estimada: "+mad_ton_estimado);
        CarvaoToneladaestimado();
    }
        
    private void CarvaoToneladaestimado(){
        carv_ton_estimado = carv_ton_ha * Float.parseFloat(jTextFieldArea.getText());
        jLabelCarvaoTonEst.setText("Carvão Tonelada Estimada: "+carv_ton_estimado);
        //CalcularRendimentoEstimado();
    }
    
    private void CalcularRendimentoEstimado(){
        rendimento = volume_madeira_estimado/mdc_estimado;
        if(rendimento<rend_grav_est){
            jLabelRendimento.setForeground(new Color(0,102,0));
        }else{
            jLabelRendimento.setForeground(Color.red );
        }
        jLabelRendimento.setText("Rendimento: "+rendimento+"%");
        RegistrarEstoque();
    }
    
    private void CalcularRendimentoGravimetricoReal(){
        //rend_grav_real = volume_madeira_real/mdc_real;
        //jLabelCarvaoTonEst.setText("Carvão Tonelada Estimada:: "+carv_ton_estimado);
    }
    
    
    private void RegistrarEstoque(){
        ControleEstoquePrincipal estoque_principal = new ControleEstoquePrincipal();
        estoque_principal.setId_estoque_p(id);
        estoque_principal.setUpc(jTextFieldUpc.getText());
        estoque_principal.setArea(Float.parseFloat(jTextFieldArea.getText()));
        estoque_principal.setM3_ha(Float.parseFloat(jTextFieldM3HA.getText()));
        estoque_principal.setMdc_ha(mdc_ha);
        estoque_principal.setMaterial_genetico(Integer.parseInt(jTextFieldMaterialGenetico.getText()));
        estoque_principal.setIma(Float.parseFloat(jTextFieldIMA.getText()));
        estoque_principal.setTalhadia(talhadia);
        estoque_principal.setAno_rotacao(Integer.parseInt(jTextFieldAnoRotacao.getText()));  
        estoque_principal.setData_plantio(jTextFieldDataPlantio.getText());
        estoque_principal.setData_rotacao_1(jTextFieldDataRotacao1.getText());
        estoque_principal.setData_rotacao_2(jTextFieldDataRotacao2.getText());
        estoque_principal.setData_rotacao_3(jTextFieldDataRotacao3.getText());
        estoque_principal.setIdade(idade);
        estoque_principal.setCategoria(jComboBoxCategoria.getSelectedItem().toString());
        estoque_principal.setSituacao(situacao);             
        estoque_principal.setDensidade_madeira(Float.parseFloat(jTextFieldDensidadeMadeira.getText())); 
        estoque_principal.setDensidade_carvao(Float.parseFloat(jTextFieldDensidadeCarvao.getText()));
        estoque_principal.setMad_ton_ha(mad_ton_ha);
        estoque_principal.setCarv_ton_ha(carv_ton_ha); 
        estoque_principal.setRend_grav_estimado(Float.parseFloat(jTextFieldRendimentoGravimetricoEstimado.getText()));
        estoque_principal.setFator_empilalhemto(Float.parseFloat(jTextFieldFatorEmpilhamento.getText()));
        
        estoque_principal.setVol_mad_estimado(volume_madeira_estimado);
        //estoque_principal.setVol_mad_balanco(-volume_madeira_estimado);
        estoque_principal.setMdc_estimado(mdc_estimado);
        //estoque_principal.setMdc_balanco(-mdc_estimado);
        estoque_principal.setMad_ton_estimado(mad_ton_estimado);
        //estoque_principal.setMad_ton_balanco(-mad_ton_estimado);
        estoque_principal.setCarv_ton_estimado(carv_ton_estimado);
        //estoque_principal.setCarv_ton_balanco(-carv_ton_estimado);

        //SELECT * FROM notas WHERE id_notas >= 0 limit 10
        
        //DefinirTalhadia();
        
        AlterarEstoquePrincipalCtrl alterar = new AlterarEstoquePrincipalCtrl(estoque_principal);

        try {
            new GerenciarEstoquePrincipal().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(AlterarEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }
    
    private void VoltarMenu(){
        try {
            new GerenciarEstoquePrincipal().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(AlterarEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanelMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabelTalhao = new javax.swing.JLabel();
        jButtonCargaTalhao = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelMDC_HA = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldMaterialGenetico = new javax.swing.JTextField();
        jTextFieldDataPlantio = new javax.swing.JTextField();
        jTextFieldArea = new javax.swing.JTextField();
        jTextFieldAnoRotacao = new javax.swing.JTextField();
        jTextFieldIMA = new javax.swing.JTextField();
        jTextFieldDensidadeCarvao = new javax.swing.JTextField();
        jTextFieldDensidadeMadeira = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextFieldDataRotacao1 = new javax.swing.JTextField();
        jButtonVoltar = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jTextFieldDataRotacao2 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextFieldDataRotacao3 = new javax.swing.JTextField();
        jTextFieldM3HA = new javax.swing.JTextField();
        jLabelRendimento = new javax.swing.JLabel();
        jTextFieldTalhao = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldUpc = new javax.swing.JTextField();
        jLabelIdade = new javax.swing.JLabel();
        jLabelTalhadia = new javax.swing.JLabel();
        jLabelSituacao = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldFatorEmpilhamento = new javax.swing.JTextField();
        jTextFieldRendimentoGravimetricoEstimado = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabelVolumeEstimado = new javax.swing.JLabel();
        jComboBoxCategoria = new javax.swing.JComboBox();
        jCheckBoxExaurida = new javax.swing.JCheckBox();
        jLabelMDCEstimado = new javax.swing.JLabel();
        jLabelMadeiraTonHA = new javax.swing.JLabel();
        jLabelCarvaoTonHA = new javax.swing.JLabel();
        jLabelMadeiraTonEst = new javax.swing.JLabel();
        jLabelCarvaoTonEst = new javax.swing.JLabel();
        jLabelTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabelTalhao.setFont(jLabelTalhao.getFont().deriveFont(jLabelTalhao.getFont().getSize()+2f));
        jLabelTalhao.setText("categoria");
        jLabelTalhao.setPreferredSize(new java.awt.Dimension(115, 15));

        jButtonCargaTalhao.setText("Registrar");
        jButtonCargaTalhao.setPreferredSize(new java.awt.Dimension(200, 40));
        jButtonCargaTalhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaTalhaoActionPerformed(evt);
            }
        });

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getSize()+2f));
        jLabel8.setText("Ano_rotacao");
        jLabel8.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getSize()+2f));
        jLabel9.setText("densidade_madeira");
        jLabel9.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel11.setFont(jLabel11.getFont().deriveFont(jLabel11.getFont().getSize()+2f));
        jLabel11.setText("ima");
        jLabel11.setPreferredSize(new java.awt.Dimension(115, 15));

        jLabel14.setFont(jLabel14.getFont().deriveFont(jLabel14.getFont().getSize()+2f));
        jLabel14.setText("M³ por hectare");
        jLabel14.setPreferredSize(new java.awt.Dimension(115, 15));

        jLabelMDC_HA.setFont(jLabelMDC_HA.getFont().deriveFont(jLabelMDC_HA.getFont().getSize()+2f));
        jLabelMDC_HA.setText("MDC/HA:");
        jLabelMDC_HA.setPreferredSize(new java.awt.Dimension(250, 16));

        jLabel18.setFont(jLabel18.getFont().deriveFont(jLabel18.getFont().getSize()+2f));
        jLabel18.setText("talhao");
        jLabel18.setPreferredSize(new java.awt.Dimension(115, 15));

        jLabel20.setFont(jLabel20.getFont().deriveFont(jLabel20.getFont().getSize()+2f));
        jLabel20.setText("area");
        jLabel20.setPreferredSize(new java.awt.Dimension(115, 15));

        jLabel21.setFont(jLabel21.getFont().deriveFont(jLabel21.getFont().getSize()+2f));
        jLabel21.setText("material_genetico");
        jLabel21.setPreferredSize(new java.awt.Dimension(115, 15));

        jLabel22.setFont(jLabel22.getFont().deriveFont(jLabel22.getFont().getSize()+2f));
        jLabel22.setText("densidade_carvao");
        jLabel22.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel25.setFont(jLabel25.getFont().deriveFont(jLabel25.getFont().getSize()+2f));
        jLabel25.setText("data_plantio");
        jLabel25.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldMaterialGenetico.setFont(jTextFieldMaterialGenetico.getFont().deriveFont(jTextFieldMaterialGenetico.getFont().getSize()+2f));
        jTextFieldMaterialGenetico.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldDataPlantio.setFont(jTextFieldDataPlantio.getFont().deriveFont(jTextFieldDataPlantio.getFont().getSize()+2f));
        jTextFieldDataPlantio.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldArea.setFont(jTextFieldArea.getFont().deriveFont(jTextFieldArea.getFont().getSize()+2f));
        jTextFieldArea.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldAnoRotacao.setFont(jTextFieldAnoRotacao.getFont().deriveFont(jTextFieldAnoRotacao.getFont().getSize()+2f));
        jTextFieldAnoRotacao.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldIMA.setFont(jTextFieldIMA.getFont().deriveFont(jTextFieldIMA.getFont().getSize()+2f));
        jTextFieldIMA.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldDensidadeCarvao.setFont(jTextFieldDensidadeCarvao.getFont().deriveFont(jTextFieldDensidadeCarvao.getFont().getSize()+2f));
        jTextFieldDensidadeCarvao.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldDensidadeMadeira.setFont(jTextFieldDensidadeMadeira.getFont().deriveFont(jTextFieldDensidadeMadeira.getFont().getSize()+2f));
        jTextFieldDensidadeMadeira.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel28.setFont(jLabel28.getFont().deriveFont(jLabel28.getFont().getSize()+2f));
        jLabel28.setText("data_rotacao_1");
        jLabel28.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldDataRotacao1.setFont(jTextFieldDataRotacao1.getFont().deriveFont(jTextFieldDataRotacao1.getFont().getSize()+2f));
        jTextFieldDataRotacao1.setPreferredSize(new java.awt.Dimension(100, 25));

        jButtonVoltar.setText("Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabel29.setFont(jLabel29.getFont().deriveFont(jLabel29.getFont().getSize()+2f));
        jLabel29.setText("data_rotacao_2");
        jLabel29.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldDataRotacao2.setFont(jTextFieldDataRotacao2.getFont().deriveFont(jTextFieldDataRotacao2.getFont().getSize()+2f));
        jTextFieldDataRotacao2.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel31.setFont(jLabel31.getFont().deriveFont(jLabel31.getFont().getSize()+2f));
        jLabel31.setText("data_rotacao_3");
        jLabel31.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldDataRotacao3.setFont(jTextFieldDataRotacao3.getFont().deriveFont(jTextFieldDataRotacao3.getFont().getSize()+2f));
        jTextFieldDataRotacao3.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldM3HA.setFont(jTextFieldM3HA.getFont().deriveFont(jTextFieldM3HA.getFont().getSize()+2f));
        jTextFieldM3HA.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabelRendimento.setFont(jLabelRendimento.getFont().deriveFont(jLabelRendimento.getFont().getSize()+1f));
        jLabelRendimento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelRendimento.setText("Rendimento Estimado:");
        jLabelRendimento.setPreferredSize(new java.awt.Dimension(250, 16));

        jTextFieldTalhao.setFont(jTextFieldTalhao.getFont().deriveFont(jTextFieldTalhao.getFont().getSize()+2f));
        jTextFieldTalhao.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getSize()+2f));
        jLabel10.setText("Upc");
        jLabel10.setPreferredSize(new java.awt.Dimension(115, 15));

        jTextFieldUpc.setFont(jTextFieldUpc.getFont().deriveFont(jTextFieldUpc.getFont().getSize()+2f));
        jTextFieldUpc.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabelIdade.setFont(jLabelIdade.getFont().deriveFont(jLabelIdade.getFont().getSize()+1f));
        jLabelIdade.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelIdade.setText("Idade:");
        jLabelIdade.setPreferredSize(new java.awt.Dimension(250, 16));

        jLabelTalhadia.setFont(jLabelTalhadia.getFont().deriveFont(jLabelTalhadia.getFont().getSize()+1f));
        jLabelTalhadia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTalhadia.setText("Talhadia:");
        jLabelTalhadia.setPreferredSize(new java.awt.Dimension(250, 16));

        jLabelSituacao.setFont(jLabelSituacao.getFont().deriveFont(jLabelSituacao.getFont().getSize()+1f));
        jLabelSituacao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelSituacao.setText("Situação:");
        jLabelSituacao.setPreferredSize(new java.awt.Dimension(250, 16));

        jLabel23.setFont(jLabel23.getFont().deriveFont(jLabel23.getFont().getSize()+2f));
        jLabel23.setText("fator empilhamento");
        jLabel23.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldFatorEmpilhamento.setFont(jTextFieldFatorEmpilhamento.getFont().deriveFont(jTextFieldFatorEmpilhamento.getFont().getSize()+2f));
        jTextFieldFatorEmpilhamento.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldRendimentoGravimetricoEstimado.setFont(jTextFieldRendimentoGravimetricoEstimado.getFont().deriveFont(jTextFieldRendimentoGravimetricoEstimado.getFont().getSize()+2f));
        jTextFieldRendimentoGravimetricoEstimado.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel24.setFont(jLabel24.getFont().deriveFont(jLabel24.getFont().getSize()+2f));
        jLabel24.setText("rend. grav. est.");
        jLabel24.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabelVolumeEstimado.setFont(jLabelVolumeEstimado.getFont().deriveFont(jLabelVolumeEstimado.getFont().getSize()+1f));
        jLabelVolumeEstimado.setText("Volume Madeira Estimado:");
        jLabelVolumeEstimado.setPreferredSize(new java.awt.Dimension(250, 16));

        jComboBoxCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxCategoria.setPreferredSize(new java.awt.Dimension(100, 20));

        jCheckBoxExaurida.setText("Situação Exaurida");

        jLabelMDCEstimado.setFont(jLabelMDCEstimado.getFont().deriveFont(jLabelMDCEstimado.getFont().getSize()+1f));
        jLabelMDCEstimado.setText("MDC Estimado:");
        jLabelMDCEstimado.setPreferredSize(new java.awt.Dimension(250, 16));

        jLabelMadeiraTonHA.setFont(jLabelMadeiraTonHA.getFont().deriveFont(jLabelMadeiraTonHA.getFont().getSize()+1f));
        jLabelMadeiraTonHA.setText("Madeira Tonelada HA:");
        jLabelMadeiraTonHA.setPreferredSize(new java.awt.Dimension(250, 16));

        jLabelCarvaoTonHA.setFont(jLabelCarvaoTonHA.getFont().deriveFont(jLabelCarvaoTonHA.getFont().getSize()+1f));
        jLabelCarvaoTonHA.setText("Carvão Tonelada HA:");
        jLabelCarvaoTonHA.setPreferredSize(new java.awt.Dimension(250, 16));

        jLabelMadeiraTonEst.setFont(jLabelMadeiraTonEst.getFont().deriveFont(jLabelMadeiraTonEst.getFont().getSize()+1f));
        jLabelMadeiraTonEst.setText("Madeira Tonelada Estimada:");
        jLabelMadeiraTonEst.setPreferredSize(new java.awt.Dimension(250, 16));

        jLabelCarvaoTonEst.setFont(jLabelCarvaoTonEst.getFont().deriveFont(jLabelCarvaoTonEst.getFont().getSize()+1f));
        jLabelCarvaoTonEst.setText("Carvão Tonelada Estimada:");
        jLabelCarvaoTonEst.setPreferredSize(new java.awt.Dimension(250, 16));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBoxCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 28, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextFieldTalhao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldMaterialGenetico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldM3HA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldIMA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldUpc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jCheckBoxExaurida)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldDataPlantio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldAnoRotacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldDataRotacao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldDataRotacao3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldDataRotacao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldDensidadeMadeira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldDensidadeCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldFatorEmpilhamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldRendimentoGravimetricoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelRendimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelTalhadia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelVolumeEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelMDCEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelMDC_HA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabelMadeiraTonEst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelCarvaoTonEst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabelCarvaoTonHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMadeiraTonHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldAnoRotacao, jTextFieldArea, jTextFieldDataPlantio, jTextFieldDataRotacao1, jTextFieldDataRotacao2, jTextFieldDataRotacao3, jTextFieldDensidadeCarvao, jTextFieldDensidadeMadeira, jTextFieldIMA, jTextFieldM3HA, jTextFieldMaterialGenetico, jTextFieldTalhao, jTextFieldUpc});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel14, jLabel18, jLabel20, jLabel21, jLabelTalhao});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel22, jLabel25, jLabel28, jLabel29, jLabel31, jLabel8, jLabel9});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabelIdade, jLabelMDCEstimado, jLabelMDC_HA, jLabelRendimento, jLabelSituacao, jLabelTalhadia, jLabelVolumeEstimado});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAnoRotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDataPlantio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDataRotacao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldM3HA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDataRotacao3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDensidadeMadeira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldDataRotacao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDensidadeCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldIMA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldFatorEmpilhamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldRendimentoGravimetricoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jCheckBoxExaurida)
                .addGap(19, 19, 19)
                .addComponent(jLabelMDC_HA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelVolumeEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabelMDCEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelMadeiraTonHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCarvaoTonHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabelMadeiraTonEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelCarvaoTonEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTalhadia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRendimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel14, jLabel18, jLabel20, jLabel21, jLabelCarvaoTonHA, jLabelIdade, jLabelMDCEstimado, jLabelMDC_HA, jLabelMadeiraTonHA, jLabelRendimento, jLabelSituacao, jLabelTalhadia, jLabelTalhao, jLabelVolumeEstimado});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextFieldArea, jTextFieldIMA, jTextFieldM3HA, jTextFieldMaterialGenetico, jTextFieldTalhao, jTextFieldUpc});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel22, jLabel25, jLabel28, jLabel29, jLabel31, jLabel8, jLabel9});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextFieldAnoRotacao, jTextFieldDataPlantio, jTextFieldDataRotacao1, jTextFieldDataRotacao2, jTextFieldDataRotacao3, jTextFieldDensidadeCarvao, jTextFieldDensidadeMadeira});

        jComboBoxCategoria.getAccessibleContext().setAccessibleName("");

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Editar Estoque");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 70));

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 804, Short.MAX_VALUE)
            .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainLayout.createSequentialGroup()
                    .addContainerGap(10, Short.MAX_VALUE)
                    .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanelMainLayout.createSequentialGroup()
                            .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(10, Short.MAX_VALUE)))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 763, Short.MAX_VALUE)
            .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMainLayout.createSequentialGroup()
                    .addContainerGap(10, Short.MAX_VALUE)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                    .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelMainLayout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                    .addContainerGap(10, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonCargaTalhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaTalhaoActionPerformed
        //CalcularMadeiraTotal();
        //RegistrarEstoque();
        //CalcularIdade(jTextFieldDataPlantio.getText(), jTextFieldDataRotacao1.getText());
        DefinirTalhadia();
    }//GEN-LAST:event_jButtonCargaTalhaoActionPerformed

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
            java.util.logging.Logger.getLogger(AlterarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlterarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlterarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlterarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlterarEstoquePrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCargaTalhao;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JCheckBox jCheckBoxExaurida;
    private javax.swing.JComboBox jComboBoxCategoria;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCarvaoTonEst;
    private javax.swing.JLabel jLabelCarvaoTonHA;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelIdade;
    private javax.swing.JLabel jLabelMDCEstimado;
    private javax.swing.JLabel jLabelMDC_HA;
    private javax.swing.JLabel jLabelMadeiraTonEst;
    private javax.swing.JLabel jLabelMadeiraTonHA;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelRendimento;
    private javax.swing.JLabel jLabelSituacao;
    private javax.swing.JLabel jLabelTalhadia;
    private javax.swing.JLabel jLabelTalhao;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelVolumeEstimado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JTextField jTextFieldAnoRotacao;
    private javax.swing.JTextField jTextFieldArea;
    private javax.swing.JTextField jTextFieldDataPlantio;
    private javax.swing.JTextField jTextFieldDataRotacao1;
    private javax.swing.JTextField jTextFieldDataRotacao2;
    private javax.swing.JTextField jTextFieldDataRotacao3;
    private javax.swing.JTextField jTextFieldDensidadeCarvao;
    private javax.swing.JTextField jTextFieldDensidadeMadeira;
    private javax.swing.JTextField jTextFieldFatorEmpilhamento;
    private javax.swing.JTextField jTextFieldIMA;
    private javax.swing.JTextField jTextFieldM3HA;
    private javax.swing.JTextField jTextFieldMaterialGenetico;
    private javax.swing.JTextField jTextFieldRendimentoGravimetricoEstimado;
    private javax.swing.JTextField jTextFieldTalhao;
    private javax.swing.JTextField jTextFieldUpc;
    // End of variables declaration//GEN-END:variables
}
