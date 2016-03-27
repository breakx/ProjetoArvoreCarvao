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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristiano GD
 */
public class AlterarEstoquePrincipal extends javax.swing.JFrame {

    private String id;

    AlterarEstoquePrincipal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void _carregaestados(){
	jComboBoxEstado.addItem("AC");
	jComboBoxEstado.addItem("AL");
	jComboBoxEstado.addItem("AP");
	jComboBoxEstado.addItem("AM");
	jComboBoxEstado.addItem("BA");
	jComboBoxEstado.addItem("CE");
	jComboBoxEstado.addItem("DF");
	jComboBoxEstado.addItem("ES");
	jComboBoxEstado.addItem("GO");
	jComboBoxEstado.addItem("MA");
	jComboBoxEstado.addItem("MT");
	jComboBoxEstado.addItem("MS");
	jComboBoxEstado.addItem("MG");
	jComboBoxEstado.addItem("PA");
	jComboBoxEstado.addItem("PB");
	jComboBoxEstado.addItem("PR");
	jComboBoxEstado.addItem("PE");
	jComboBoxEstado.addItem("PI");
	jComboBoxEstado.addItem("RJ");
	jComboBoxEstado.addItem("RN");
	jComboBoxEstado.addItem("RS");
	jComboBoxEstado.addItem("RO");
	jComboBoxEstado.addItem("RR");
	jComboBoxEstado.addItem("SC");
	jComboBoxEstado.addItem("SE");
	jComboBoxEstado.addItem("SP");
	jComboBoxEstado.addItem("TO");
        _carregarTalhao();
    }
    
    private void _carregarTalhao(){
        jComboBoxTalhao.addItem("I");
        jComboBoxTalhao.addItem("II");
        jComboBoxTalhao.addItem("III");
        jComboBoxTalhao.addItem("IV");
        jComboBoxTalhao.addItem("V");
        jComboBoxTalhao.addItem("VI");
        jComboBoxTalhao.addItem("VII");
        _carregarBloco();
    }
    
    private void _carregarBloco(){
        jComboBoxBloco.addItem("Norte");
        jComboBoxBloco.addItem("Sul");
        jComboBoxBloco.addItem("Leste");
        jComboBoxBloco.addItem("Oeste");
    }
    
    /**
     * Creates new form AlterarEstoque
     */
    public AlterarEstoquePrincipal(String id_estoque, String estado, String upc, String bloco, String municipio, String fazenda, String projeto, String ano_rotacao, String talhao, String area, String m3_ha, String data_plantio, String material_genetico, String talhadia, String data_rotacao_1, String data_rotacao_2, String idade, String categoria, String situacao, String ima, String mdc_ha, String mdc, String densidade_carvao, String densidade_madeira, String id_operario, String data_estoque, String volume_estimado, String madeira_talhao, String madeira_praca, String madeira_forno, String mad_ton_tot, String carv_ton_tot) {
        super("Alterar Carvao");
        initComponents();        
        _carregaestados(); 
        CarregarNome();
        this.id = id_estoque;      
        jComboBoxEstado.setSelectedIndex(Integer.parseInt(estado));
        jTextFieldUpc.setText(upc);        
        jComboBoxBloco.setSelectedIndex(Integer.parseInt(bloco));
        jTextFieldMunicipio.setText(municipio);
        jTextFieldFazenda.setText(fazenda);  
        jTextFieldProjeto.setText(projeto);                   
        jTextFieldAnoRotacao.setText(ano_rotacao); 
        jComboBoxTalhao.setSelectedIndex(Integer.parseInt(talhao));        
        jTextFieldArea.setText(area);                    
        jTextFieldM3HA.setText(m3_ha);
        jTextFieldDataPlantio.setText(data_plantio);          
        jTextFieldMaterialGenetico.setText(material_genetico);         
        jTextFieldTalhadia.setText(talhadia);  
        jTextFieldDataRotacao1.setText(data_rotacao_1);    
        jTextFieldDataRotacao2.setText(data_rotacao_2);          
        jTextFieldIdade.setText(idade);                 
        jTextFieldCategoria.setText(categoria);    
        jTextFieldSituacao.setText(situacao);           
        jTextFieldIMA.setText(ima);          
        jTextFieldMDCHA.setText(mdc_ha);       
        jTextFieldMDC.setText(mdc);     
        jTextFieldDensidadeCarvao.setText(densidade_carvao);  
        jTextFieldDensidadeMadeira.setText(densidade_madeira);      
        jTextFieldIdOperario.setText(id_operario);                  
        jTextFieldDataEstoque.setText(data_estoque);               
        jTextFieldVolumeEstimado.setText(volume_estimado);  
        jTextFieldMadeiraTalhao.setText(madeira_talhao);                     
        jTextFieldMadeiraPraca.setText(madeira_praca);                    
        jTextFieldMadeiraForno.setText(madeira_forno);              
        jTextFieldMadeiraTotal.setText(mad_ton_tot);                      
        jTextFieldCarvaoTotal.setText(carv_ton_tot); 
        CalcularMadeiraTotal();
    }
    
    private void RegistrarEstoque(){
        DateFormat data_estoque_principal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();

        ControleEstoquePrincipal estoque_principal = new ControleEstoquePrincipal();
        estoque_principal.setId_estoque_p(id);
        //estoque_principal.setEstado(jComboBoxEstado.getSelectedIndex());
        estoque_principal.setUpc(jTextFieldUpc.getText());
        //estoque_principal.setBloco(jComboBoxBloco.getSelectedIndex());
        estoque_principal.setMunicipio(jTextFieldMunicipio.getText());
        estoque_principal.setFazenda(jTextFieldFazenda.getText());
        estoque_principal.setProjeto(jTextFieldProjeto.getText()); 
        estoque_principal.setAno_rotacao(Integer.parseInt(jTextFieldAnoRotacao.getText()));
        //estoque_principal.setTalhao(jComboBoxTalhao.getSelectedIndex());   
        estoque_principal.setArea(Float.parseFloat(jTextFieldArea.getText()));
        estoque_principal.setMaterial_genetico(Integer.parseInt(jTextFieldMaterialGenetico.getText()));
        estoque_principal.setData_plantio(jTextFieldDataPlantio.getText());
        estoque_principal.setTalhadia(jTextFieldTalhadia.getText());
        estoque_principal.setData_rotacao_1(jTextFieldDataRotacao1.getText());
        estoque_principal.setData_rotacao_2(jTextFieldDataRotacao2.getText());      
        estoque_principal.setIdade(Float.parseFloat(jTextFieldIdade.getText()));
        estoque_principal.setCategoria(jTextFieldCategoria.getText());
        estoque_principal.setSituacao(jTextFieldSituacao.getText());         
        estoque_principal.setIma(Float.parseFloat(jTextFieldIMA.getText()));
        estoque_principal.setMdc_ha(Float.parseFloat(jTextFieldMDCHA.getText()));    
        estoque_principal.setMdc(Float.parseFloat(jTextFieldMDC.getText()));
        estoque_principal.setDensidade_carvao(Float.parseFloat(jTextFieldDensidadeCarvao.getText()));
        estoque_principal.setDensidade_madeira(Float.parseFloat(jTextFieldDensidadeMadeira.getText())); 
        estoque_principal.setVolume_estimado(Float.parseFloat(jTextFieldVolumeEstimado.getText()));   
        estoque_principal.setMadeira_talhao(Float.parseFloat(jTextFieldMadeiraTalhao.getText()));
        estoque_principal.setMadeira_praca(Float.parseFloat(jTextFieldMadeiraPraca.getText()));
        estoque_principal.setMadeira_forno(Float.parseFloat(jTextFieldMadeiraForno.getText())); 
        estoque_principal.setMad_ton_tot(Float.parseFloat(jTextFieldMadeiraTotal.getText()));
        estoque_principal.setCarv_ton_tot(Float.parseFloat(jTextFieldCarvaoTotal.getText()));
        
        estoque_principal.setId_operario(ControlePrincipal.id_op);
        estoque_principal.setData_estoque(data_estoque_principal.format(date));
        //estoque.setVolume_carvao(Float.parseFloat(jTextFieldVolumeCarvao.getText()));

        //SELECT * FROM notas WHERE id_notas >= 0 limit 10
        
        CalcularMadeiraTotal();
        
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
    
    private float madeiraTotal;
    private float rendimento;
    /*private float madeiraTalhao;
    private float madeiraPraca;
    private float madeiraForno;*/
    private void CalcularMadeiraTotal(){
        madeiraTotal = Float.parseFloat(jTextFieldMadeiraTalhao.getText())+Float.parseFloat(jTextFieldMadeiraPraca.getText())+Float.parseFloat(jTextFieldMadeiraForno.getText());
        jTextFieldMadeiraTotal.setText(String.valueOf(madeiraTotal));
        rendimento = (madeiraTotal*100)/Float.parseFloat(jTextFieldVolumeEstimado.getText());
        if(rendimento>100){
            //JLabel.setForeground(Color.green);
            jLabelRendimento.setForeground(new Color(0,102,0));
        }else{
            //JLabel.setForeground(Color.red);
            jLabelRendimento.setForeground(Color.red );
        }
        jLabelRendimento.setText("Rendimento: "+rendimento+"%");
        //RegistrarEstoque();
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
        jLabel6 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelTalhao = new javax.swing.JLabel();
        jButtonCargaTalhao = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldMaterialGenetico = new javax.swing.JTextField();
        jTextFieldDataPlantio = new javax.swing.JTextField();
        jTextFieldArea = new javax.swing.JTextField();
        jTextFieldCategoria = new javax.swing.JTextField();
        jTextFieldTalhadia = new javax.swing.JTextField();
        jTextFieldMadeiraTotal = new javax.swing.JTextField();
        jTextFieldMadeiraForno = new javax.swing.JTextField();
        jTextFieldMadeiraPraca = new javax.swing.JTextField();
        jTextFieldMadeiraTalhao = new javax.swing.JTextField();
        jTextFieldAnoRotacao = new javax.swing.JTextField();
        jTextFieldProjeto = new javax.swing.JTextField();
        jTextFieldFazenda = new javax.swing.JTextField();
        jTextFieldMunicipio = new javax.swing.JTextField();
        jTextFieldUpc = new javax.swing.JTextField();
        jTextFieldSituacao = new javax.swing.JTextField();
        jTextFieldIMA = new javax.swing.JTextField();
        jTextFieldMDCHA = new javax.swing.JTextField();
        jTextFieldMDC = new javax.swing.JTextField();
        jTextFieldDensidadeCarvao = new javax.swing.JTextField();
        jTextFieldDensidadeMadeira = new javax.swing.JTextField();
        jTextFieldIdOperario = new javax.swing.JTextField();
        jTextFieldDataEstoque = new javax.swing.JTextField();
        jComboBoxEstado = new javax.swing.JComboBox();
        jComboBoxBloco = new javax.swing.JComboBox();
        jComboBoxTalhao = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jTextFieldVolumeEstimado = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextFieldDataRotacao1 = new javax.swing.JTextField();
        jButtonVoltar = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jTextFieldDataRotacao2 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextFieldCarvaoTotal = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextFieldIdade = new javax.swing.JTextField();
        jTextFieldM3HA = new javax.swing.JTextField();
        jLabelRendimento = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Editar Estoque");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 70));

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

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+2f));
        jLabel1.setText("Estado");
        jLabel1.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+2f));
        jLabel2.setText("Upc");
        jLabel2.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+2f));
        jLabel3.setText("Bloco");
        jLabel3.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+2f));
        jLabel4.setText("Municipio");
        jLabel4.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+2f));
        jLabel5.setText("Fazenda");
        jLabel5.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabelTalhao.setFont(jLabelTalhao.getFont().deriveFont(jLabelTalhao.getFont().getSize()+2f));
        jLabelTalhao.setText("categoria");
        jLabelTalhao.setPreferredSize(new java.awt.Dimension(115, 16));

        jButtonCargaTalhao.setText("Registrar");
        jButtonCargaTalhao.setPreferredSize(new java.awt.Dimension(200, 40));
        jButtonCargaTalhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaTalhaoActionPerformed(evt);
            }
        });

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()+2f));
        jLabel7.setText("Projeto");
        jLabel7.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getSize()+2f));
        jLabel8.setText("Ano_rotacao");
        jLabel8.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getSize()+2f));
        jLabel9.setText("densidade_madeira");
        jLabel9.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getSize()+2f));
        jLabel10.setText("madeira_forno");
        jLabel10.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel11.setFont(jLabel11.getFont().deriveFont(jLabel11.getFont().getSize()+2f));
        jLabel11.setText("ima");
        jLabel11.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel12.setFont(jLabel12.getFont().deriveFont(jLabel12.getFont().getSize()+2f));
        jLabel12.setText("data_estoque");
        jLabel12.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel13.setFont(jLabel13.getFont().deriveFont(jLabel13.getFont().getSize()+2f));
        jLabel13.setText("volume_estimado");
        jLabel13.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel14.setFont(jLabel14.getFont().deriveFont(jLabel14.getFont().getSize()+2f));
        jLabel14.setText("m3_ha");
        jLabel14.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel15.setFont(jLabel15.getFont().deriveFont(jLabel15.getFont().getSize()+2f));
        jLabel15.setText("situacao");
        jLabel15.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel16.setFont(jLabel16.getFont().deriveFont(jLabel16.getFont().getSize()+2f));
        jLabel16.setText("mdc_ha");
        jLabel16.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel17.setFont(jLabel17.getFont().deriveFont(jLabel17.getFont().getSize()+2f));
        jLabel17.setText("madeira_praca");
        jLabel17.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel18.setFont(jLabel18.getFont().deriveFont(jLabel18.getFont().getSize()+2f));
        jLabel18.setText("talhao");
        jLabel18.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel19.setFont(jLabel19.getFont().deriveFont(jLabel19.getFont().getSize()+2f));
        jLabel19.setText("talhadia");
        jLabel19.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel20.setFont(jLabel20.getFont().deriveFont(jLabel20.getFont().getSize()+2f));
        jLabel20.setText("area");
        jLabel20.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel21.setFont(jLabel21.getFont().deriveFont(jLabel21.getFont().getSize()+2f));
        jLabel21.setText("material_genetico");
        jLabel21.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel22.setFont(jLabel22.getFont().deriveFont(jLabel22.getFont().getSize()+2f));
        jLabel22.setText("densidade_carvao");
        jLabel22.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel23.setFont(jLabel23.getFont().deriveFont(jLabel23.getFont().getSize()+2f));
        jLabel23.setText("mad_ton_tot");
        jLabel23.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel24.setFont(jLabel24.getFont().deriveFont(jLabel24.getFont().getSize()+2f));
        jLabel24.setText("id_operario");
        jLabel24.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel25.setFont(jLabel25.getFont().deriveFont(jLabel25.getFont().getSize()+2f));
        jLabel25.setText("data_plantio");
        jLabel25.setPreferredSize(new java.awt.Dimension(115, 16));

        jLabel26.setFont(jLabel26.getFont().deriveFont(jLabel26.getFont().getSize()+2f));
        jLabel26.setText("mdc");
        jLabel26.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldMaterialGenetico.setFont(jTextFieldMaterialGenetico.getFont().deriveFont(jTextFieldMaterialGenetico.getFont().getSize()+2f));
        jTextFieldMaterialGenetico.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldDataPlantio.setFont(jTextFieldDataPlantio.getFont().deriveFont(jTextFieldDataPlantio.getFont().getSize()+2f));
        jTextFieldDataPlantio.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldArea.setFont(jTextFieldArea.getFont().deriveFont(jTextFieldArea.getFont().getSize()+2f));
        jTextFieldArea.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldCategoria.setFont(jTextFieldCategoria.getFont().deriveFont(jTextFieldCategoria.getFont().getSize()+2f));
        jTextFieldCategoria.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldTalhadia.setFont(jTextFieldTalhadia.getFont().deriveFont(jTextFieldTalhadia.getFont().getSize()+2f));
        jTextFieldTalhadia.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldMadeiraTotal.setFont(jTextFieldMadeiraTotal.getFont().deriveFont(jTextFieldMadeiraTotal.getFont().getSize()+2f));
        jTextFieldMadeiraTotal.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldMadeiraForno.setFont(jTextFieldMadeiraForno.getFont().deriveFont(jTextFieldMadeiraForno.getFont().getSize()+2f));
        jTextFieldMadeiraForno.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldMadeiraPraca.setFont(jTextFieldMadeiraPraca.getFont().deriveFont(jTextFieldMadeiraPraca.getFont().getSize()+2f));
        jTextFieldMadeiraPraca.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldMadeiraTalhao.setFont(jTextFieldMadeiraTalhao.getFont().deriveFont(jTextFieldMadeiraTalhao.getFont().getSize()+2f));
        jTextFieldMadeiraTalhao.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldAnoRotacao.setFont(jTextFieldAnoRotacao.getFont().deriveFont(jTextFieldAnoRotacao.getFont().getSize()+2f));
        jTextFieldAnoRotacao.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldProjeto.setFont(jTextFieldProjeto.getFont().deriveFont(jTextFieldProjeto.getFont().getSize()+2f));
        jTextFieldProjeto.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldFazenda.setFont(jTextFieldFazenda.getFont().deriveFont(jTextFieldFazenda.getFont().getSize()+2f));
        jTextFieldFazenda.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldMunicipio.setFont(jTextFieldMunicipio.getFont().deriveFont(jTextFieldMunicipio.getFont().getSize()+2f));
        jTextFieldMunicipio.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldUpc.setFont(jTextFieldUpc.getFont().deriveFont(jTextFieldUpc.getFont().getSize()+2f));
        jTextFieldUpc.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldSituacao.setFont(jTextFieldSituacao.getFont().deriveFont(jTextFieldSituacao.getFont().getSize()+2f));
        jTextFieldSituacao.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldIMA.setFont(jTextFieldIMA.getFont().deriveFont(jTextFieldIMA.getFont().getSize()+2f));
        jTextFieldIMA.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldMDCHA.setFont(jTextFieldMDCHA.getFont().deriveFont(jTextFieldMDCHA.getFont().getSize()+2f));
        jTextFieldMDCHA.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldMDC.setFont(jTextFieldMDC.getFont().deriveFont(jTextFieldMDC.getFont().getSize()+2f));
        jTextFieldMDC.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldDensidadeCarvao.setFont(jTextFieldDensidadeCarvao.getFont().deriveFont(jTextFieldDensidadeCarvao.getFont().getSize()+2f));
        jTextFieldDensidadeCarvao.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldDensidadeMadeira.setFont(jTextFieldDensidadeMadeira.getFont().deriveFont(jTextFieldDensidadeMadeira.getFont().getSize()+2f));
        jTextFieldDensidadeMadeira.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldIdOperario.setFont(jTextFieldIdOperario.getFont().deriveFont(jTextFieldIdOperario.getFont().getSize()+2f));
        jTextFieldIdOperario.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldDataEstoque.setFont(jTextFieldDataEstoque.getFont().deriveFont(jTextFieldDataEstoque.getFont().getSize()+2f));
        jTextFieldDataEstoque.setPreferredSize(new java.awt.Dimension(100, 25));

        jComboBoxEstado.setFont(jComboBoxEstado.getFont().deriveFont(jComboBoxEstado.getFont().getSize()+2f));
        jComboBoxEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxEstado.setPreferredSize(new java.awt.Dimension(100, 25));
        jComboBoxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEstadoActionPerformed(evt);
            }
        });

        jComboBoxBloco.setFont(jComboBoxBloco.getFont().deriveFont(jComboBoxBloco.getFont().getSize()+2f));
        jComboBoxBloco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxBloco.setPreferredSize(new java.awt.Dimension(100, 25));

        jComboBoxTalhao.setFont(jComboBoxTalhao.getFont().deriveFont(jComboBoxTalhao.getFont().getSize()+2f));
        jComboBoxTalhao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxTalhao.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel27.setFont(jLabel27.getFont().deriveFont(jLabel27.getFont().getSize()+2f));
        jLabel27.setText("madeira_talhao");
        jLabel27.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldVolumeEstimado.setFont(jTextFieldVolumeEstimado.getFont().deriveFont(jTextFieldVolumeEstimado.getFont().getSize()+2f));
        jTextFieldVolumeEstimado.setPreferredSize(new java.awt.Dimension(100, 25));
        jTextFieldVolumeEstimado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldVolumeEstimadoActionPerformed(evt);
            }
        });

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

        jLabel30.setFont(jLabel30.getFont().deriveFont(jLabel30.getFont().getSize()+2f));
        jLabel30.setText("carv_ton_tot");
        jLabel30.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldCarvaoTotal.setFont(jTextFieldCarvaoTotal.getFont().deriveFont(jTextFieldCarvaoTotal.getFont().getSize()+2f));
        jTextFieldCarvaoTotal.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel31.setFont(jLabel31.getFont().deriveFont(jLabel31.getFont().getSize()+2f));
        jLabel31.setText("idade");
        jLabel31.setPreferredSize(new java.awt.Dimension(115, 16));

        jTextFieldIdade.setFont(jTextFieldIdade.getFont().deriveFont(jTextFieldIdade.getFont().getSize()+2f));
        jTextFieldIdade.setPreferredSize(new java.awt.Dimension(100, 25));

        jTextFieldM3HA.setFont(jTextFieldM3HA.getFont().deriveFont(jTextFieldM3HA.getFont().getSize()+2f));
        jTextFieldM3HA.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabelRendimento.setText("Rendimento:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldTalhadia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldMaterialGenetico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDataPlantio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldFazenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldMunicipio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldAnoRotacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldUpc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxBloco, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDataRotacao1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxTalhao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldM3HA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextFieldSituacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldDensidadeCarvao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldMDC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldIMA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldMDCHA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldDensidadeMadeira, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldIdOperario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(235, 235, 235))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldDataEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldVolumeEstimado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextFieldMadeiraPraca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextFieldMadeiraForno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextFieldMadeiraTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextFieldMadeiraTalhao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDataRotacao2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCarvaoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldIdade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelRendimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(235, 235, 235))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTalhao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldUpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxBloco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldIMA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldMDCHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldMDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldDensidadeCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAnoRotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldDensidadeMadeira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldIdOperario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldDataEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldVolumeEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldM3HA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldDataPlantio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMadeiraTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldMadeiraPraca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldTalhadia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMadeiraForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDataRotacao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldMadeiraTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldDataRotacao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldCarvaoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRendimento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jComboBoxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEstadoActionPerformed

    private void jButtonCargaTalhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaTalhaoActionPerformed
        CalcularMadeiraTotal();
        RegistrarEstoque();
    }//GEN-LAST:event_jButtonCargaTalhaoActionPerformed

    private void jTextFieldVolumeEstimadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldVolumeEstimadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldVolumeEstimadoActionPerformed

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
    private javax.swing.JComboBox jComboBoxBloco;
    private javax.swing.JComboBox jComboBoxEstado;
    private javax.swing.JComboBox jComboBoxTalhao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelRendimento;
    private javax.swing.JLabel jLabelTalhao;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldAnoRotacao;
    private javax.swing.JTextField jTextFieldArea;
    private javax.swing.JTextField jTextFieldCarvaoTotal;
    private javax.swing.JTextField jTextFieldCategoria;
    private javax.swing.JTextField jTextFieldDataEstoque;
    private javax.swing.JTextField jTextFieldDataPlantio;
    private javax.swing.JTextField jTextFieldDataRotacao1;
    private javax.swing.JTextField jTextFieldDataRotacao2;
    private javax.swing.JTextField jTextFieldDensidadeCarvao;
    private javax.swing.JTextField jTextFieldDensidadeMadeira;
    private javax.swing.JTextField jTextFieldFazenda;
    private javax.swing.JTextField jTextFieldIMA;
    private javax.swing.JTextField jTextFieldIdOperario;
    private javax.swing.JTextField jTextFieldIdade;
    private javax.swing.JTextField jTextFieldM3HA;
    private javax.swing.JTextField jTextFieldMDC;
    private javax.swing.JTextField jTextFieldMDCHA;
    private javax.swing.JTextField jTextFieldMadeiraForno;
    private javax.swing.JTextField jTextFieldMadeiraPraca;
    private javax.swing.JTextField jTextFieldMadeiraTalhao;
    private javax.swing.JTextField jTextFieldMadeiraTotal;
    private javax.swing.JTextField jTextFieldMaterialGenetico;
    private javax.swing.JTextField jTextFieldMunicipio;
    private javax.swing.JTextField jTextFieldProjeto;
    private javax.swing.JTextField jTextFieldSituacao;
    private javax.swing.JTextField jTextFieldTalhadia;
    private javax.swing.JTextField jTextFieldUpc;
    private javax.swing.JTextField jTextFieldVolumeEstimado;
    // End of variables declaration//GEN-END:variables
}
