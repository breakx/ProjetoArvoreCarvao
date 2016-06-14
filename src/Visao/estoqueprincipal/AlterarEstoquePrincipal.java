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
    //private float rendimento;
    private int talhadia;
    private float idade_hoje;
    private float idade_corte1;
    private float idade_corte2;
    private float idade_corte3;
    private String situacao;  
    //private float rend_grav_est;
    
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
     * @param coluna
     */
    public AlterarEstoquePrincipal(String coluna[]) {
        super("Alterar Carvao");
        initComponents();     
        this.setExtendedState(MAXIMIZED_BOTH);    
        _carregarCategoria();
        id = coluna[0];//id_estoque      
        //jComboBoxEstado.setSelectedIndex(Integer.parseInt(coluna[1]));//estado  
        //jComboBoxBloco.setSelectedIndex(Integer.parseInt(coluna[2]));//bloco  
        //jTextFieldMunicipio.setText(coluna[3]);//municipio  
        //jTextFieldFazenda.setText(coluna[4]);//fazenda  
        //jTextFieldProjeto.setText(coluna[5]);//projeto  
        jLabelLocal.setText("Fazenda "+coluna[4]+" "+coluna[3]+"-"+coluna[1]);       
        jSpinnerUpc.setValue(Integer.parseInt(coluna[6]));//upc  
        jSpinnerTalhao.setValue(Integer.parseInt(coluna[7]));//talhao                  
        jSpinnerArea.setValue(Float.parseFloat(coluna[8]));//area         
        jTextFieldMaterialGenetico.setText(coluna[9]);//material_genetico            
        jSpinnerM3HA.setValue(Float.parseFloat(coluna[10]));//m3_ha             
        talhadia = Integer.parseInt(coluna[11]);//talhadia
        jLabelTalhadia.setText("Talhadia: "+talhadia);
        jSpinnerAnoRotacao.setValue(Integer.parseInt(coluna[12]));//ano_rotacao
        jTextFieldDataPlantio.setText(coluna[13]);//data_plantio  
        jTextFieldDataRotacao1.setText(coluna[14]);//data_rotacao_1
        jTextFieldDataRotacao2.setText(coluna[15]);//data_rotacao_2
        jTextFieldDataRotacao3.setText(coluna[16]);//data_rotacao_3 
        //jTextFieldCorte1.setText(coluna[17]);//idade_corte1
        //jTextFieldCorte2.setText(coluna[18]);//idade_corte2
        //jTextFieldCorte3.setText(coluna[19]);//idade_corte3
        jLabelIdadeCorte.setText("Idade Corte 1: "+coluna[17]+" anos. Corte 2: "+coluna[18]+" anos. Corte 3: "+coluna[19]+" anos.");       
        jLabelIdadeHoje.setText("Idade Hoje: "+coluna[20]+" anos.");//idade_hoje
        //jTextFieldConducao.setText(coluna[21]);//conducao
        switch (coluna[22]) {//categoria
            case "Colheita":
                jComboBoxCategoria.setSelectedIndex(1);
                break;
            case "Silvicultura":
                jComboBoxCategoria.setSelectedIndex(2);
                break; 
            case "UTM":
                jComboBoxCategoria.setSelectedIndex(3);
                break;
            default:
                jComboBoxCategoria.setSelectedIndex(0);
                break;
        }
        situacao = coluna[23];//situacao
        jLabelSituacao.setText("Situação: "+situacao);
        if(situacao.equals("Exaurida")){
            jCheckBoxExaurida.setSelected(true);
        }else{
            jCheckBoxExaurida.setSelected(false);
        }
        jSpinnerIMA.setValue(Integer.parseInt(coluna[24]));//ima               
        jLabelMDC_HA.setText("MDC/HA: "+coluna[25]);//mdc_ha 
        jSpinnerDensidadeMadeira.setValue(Float.parseFloat(coluna[26]));//densidade_madeira     
        jSpinnerDensidadeCarvao.setValue(Float.parseFloat(coluna[27]));//densidade_carvao
        jLabelMadeiraTonHA.setText("Madeira Tonelada HA: "+coluna[28]);//mad_ton_ha
        jLabelCarvaoTonHA.setText("Carvão Tonelada HA: "+coluna[29]);//carv_ton_ha
//        jTextFieldIdOperario.setText(coluna[30]);//id_operario                  
//        jTextFieldDataEstoque.setText(coluna[31]);//data_estoque           
        jLabelVolumeEstimado.setText("Volume Madeira Estimado: "+coluna[32]);//vol_mad_estimado       
//        jTextFieldVolumeEstimado.setText(coluna[33]);//vol_mad_transp
//        jTextFieldMadeiraTalhao.setText(coluna[34]);//vol_mad_balanco
        jLabelMDCEstimado.setText("MDC Estimado: "+coluna[35]);//mdc_estimado               
//        jTextFieldMadeiraForno.setText(coluna[36]);//mdc_prod                   
//        jTextFieldMadeiraForno.setText(coluna[37]);//mdc_balanco
        jLabelMadeiraTonEst.setText("Madeira Tonelada Estimada: "+coluna[38]);//mad_ton_estimado
//        jTextFieldMadeiraForno.setText(coluna[39]);//mad_ton_transp                   
//        jTextFieldMadeiraForno.setText(coluna[40]);//mad_ton_balanco
        jLabelCarvaoTonEst.setText("Carvão Tonelada Estimada: "+coluna[41]);//carv_ton_estimado
//        jTextFieldMadeiraForno.setText(coluna[42]);//carv_ton_prod                 
//        jTextFieldMadeiraForno.setText(coluna[43]);//carv_ton_balanco                 
//        jTextFieldMadeiraForno.setText(coluna[44]);//madeira_praca                  
//        jTextFieldMadeiraForno.setText(coluna[45]);//carvao_praca              
//        jTextFieldMadeiraForno.setText(coluna[46]);//madeira_forno          
//        jTextFieldMadeiraForno.setText(coluna[47]);//mdc_transp       
//        jTextFieldMadeiraForno.setText(coluna[48]);//carvao_ton_transp 
        jSpinnerRendimentoGravimetricoEstimado.setValue(Float.parseFloat(coluna[49]));//rend_grav_estimado        
        //jTextFieldRendimentoGravimetricoEstimado.setText(coluna[50]);//rend_grav_real 
        jSpinnerFatorEmpilhamento.setValue(Float.parseFloat(coluna[51]));//fator_empilalhemto
        //CalcularMadeiraTotal();
    }   
    
    private void _carregarCategoria(){
        jComboBoxCategoria.addItem("Colheita");
        jComboBoxCategoria.addItem("Silvicultura");
        jComboBoxCategoria.addItem("UTM");  
        CarregarNome();
    }
        
    private void DefinirTalhadia(){
        jLabelIdadeHoje.setForeground(Color.BLACK );
        jLabelTalhadia.setForeground(Color.BLACK );
        jLabelSituacao.setForeground(Color.BLACK );       
        
        if(!jTextFieldDataPlantio.getText().equals("00/00/0000") && !jTextFieldDataRotacao1.getText().equals("00/00/0000") && !jTextFieldDataRotacao2.getText().equals("00/00/0000") && !jTextFieldDataRotacao3.getText().equals("00/00/0000")){
            talhadia = 3;                        
        }else if(!jTextFieldDataPlantio.getText().equals("00/00/0000") && !jTextFieldDataRotacao1.getText().equals("00/00/0000") && !jTextFieldDataRotacao2.getText().equals("00/00/0000") && jTextFieldDataRotacao3.getText().equals("00/00/0000")){
            talhadia = 2;            
        }else if(!jTextFieldDataPlantio.getText().equals("00/00/0000") && !jTextFieldDataRotacao1.getText().equals("00/00/0000") && jTextFieldDataRotacao2.getText().equals("00/00/0000") && jTextFieldDataRotacao3.getText().equals("00/00/0000")){
            talhadia = 1;
        }else if(!jTextFieldDataPlantio.getText().equals("00/00/0000") && jTextFieldDataRotacao1.getText().equals("00/00/0000") && jTextFieldDataRotacao2.getText().equals("00/00/0000") && jTextFieldDataRotacao3.getText().equals("00/00/0000")){
            talhadia = 0;
        }else {
            jLabelIdadeHoje.setForeground(Color.red );
            jLabelIdadeHoje.setText("Idade: Erro Data"); 
            jLabelTalhadia.setForeground(Color.red );
            jLabelTalhadia.setText("Talhadia: Erro Data");
            jLabelSituacao.setForeground(Color.red );
            jLabelSituacao.setText("Situação: Erro Data");
        }        
        CalcularIdade(jTextFieldDataRotacao1.getText(), jTextFieldDataRotacao2.getText(), jTextFieldDataRotacao3.getText(), jTextFieldDataPlantio.getText());
    }
    
    /**
     * 
     * @param data_c1
     * @param data_c2
     * @param data_c3
     * @param data_plt 
     */
    private void CalcularIdade(String data_c1, String data_c2, String data_c3, String data_plt){
        DecimalFormat decformat = new DecimalFormat("0.0");
        SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yyyy");
        Date hoje = new Date(); 
        
        java.sql.Date plantio = null;
        java.sql.Date corte1 = null;
        java.sql.Date corte2 = null;
        java.sql.Date corte3 = null;
        java.sql.Date fim = null;
        try {
            plantio = new java.sql.Date(dataformat.parse(data_plt).getTime());
            fim = new java.sql.Date(dataformat.parse(dataformat.format(hoje)).getTime());
            
            corte1 = new java.sql.Date(dataformat.parse(data_c1).getTime());
            plantio = new java.sql.Date(dataformat.parse(data_plt).getTime());
            
            corte2 = new java.sql.Date(dataformat.parse(data_c2).getTime());
            corte1 = new java.sql.Date(dataformat.parse(data_c1).getTime());
            
            corte3 = new java.sql.Date(dataformat.parse(data_c3).getTime());
            corte2 = new java.sql.Date(dataformat.parse(data_c2).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AlterarEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //idade hj
        Calendar c1 = Calendar.getInstance();
        c1.setTime(plantio);
        int w1 = c1.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DAY_OF_WEEK, -w1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(fim);
        int w2 = c2.get(Calendar.DAY_OF_WEEK);
        c2.add(Calendar.DAY_OF_WEEK, -w2);
        
        //idade c1
        Calendar c3 = Calendar.getInstance();
        c3.setTime(plantio);
        int w3 = c3.get(Calendar.DAY_OF_WEEK);
        c3.add(Calendar.DAY_OF_WEEK, -w3);

        Calendar c4 = Calendar.getInstance();
        c4.setTime(corte1);
        int w4 = c4.get(Calendar.DAY_OF_WEEK);
        c2.add(Calendar.DAY_OF_WEEK, -w4);
        
        //idade c2
        Calendar c5 = Calendar.getInstance();
        c5.setTime(corte1);
        int w5 = c5.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DAY_OF_WEEK, -w5);

        Calendar c6 = Calendar.getInstance();
        c6.setTime(corte2);
        int w6 = c6.get(Calendar.DAY_OF_WEEK);
        c2.add(Calendar.DAY_OF_WEEK, -w6);
        
        //idade c3
        Calendar c7 = Calendar.getInstance();
        c7.setTime(corte2);
        int w7 = c7.get(Calendar.DAY_OF_WEEK);
        c7.add(Calendar.DAY_OF_WEEK, -w7);

        Calendar c8 = Calendar.getInstance();
        c8.setTime(corte3);
        int w8 = c8.get(Calendar.DAY_OF_WEEK);
        c8.add(Calendar.DAY_OF_WEEK, -w8);

        float dias1 = (c2.getTimeInMillis()-c1.getTimeInMillis())/(60*60*24*365);
        float dias2 = (c4.getTimeInMillis()-c3.getTimeInMillis())/(60*60*24*365);
        float dias3 = (c6.getTimeInMillis()-c5.getTimeInMillis())/(60*60*24*365);
        float dias4 = (c8.getTimeInMillis()-c7.getTimeInMillis())/(60*60*24*365);
        idade_hoje = dias1/1000;
        idade_corte1 = dias2/1000;
        idade_corte2 = dias3/1000;
        idade_corte3 = dias4/1000;
        
        if(idade_hoje<0){
            idade_hoje=0;
        }
        
        jLabelIdadeHoje.setText("Idade Hoje: "+decformat.format(idade_hoje)+" anos."); 
        
        //corrige idades de corte para 0 caso sejam menores que zero.
        if(idade_corte1<0){
            idade_corte1=0;
        }
        if(idade_corte2<0){
            idade_corte2=0;
        }
        if(idade_corte3<0){
            idade_corte3=0;
        }        
        jLabelIdadeCorte.setText("Idade Corte 1: "+decformat.format(idade_corte1)+" anos. Corte 2: "+decformat.format(idade_corte2)+" anos. Corte 3: "+decformat.format(idade_corte3)+" anos.");          
        
        jLabelTalhadia.setText("Talhadia: "+talhadia);
        /*if(!jCheckBoxExaurida.isSelected()){
            DefinirSituacao();
        }else{
            situacao = "Exaurida";            
            jSpinnerM3HA.setValue(0f);
            jLabelSituacao.setText("Situação: "+situacao);
            VolumeMadEstimado();
            //RegistrarEstoque();
        }*/
        DefinirSituacao();
    }   
    
    private void DefinirSituacao(){
        if(jComboBoxCategoria.getSelectedIndex()==1){
            if(!jTextFieldDataRotacao1.getText().equals("00/00/0000") || !jTextFieldDataRotacao2.getText().equals("00/00/0000") || !jTextFieldDataRotacao3.getText().equals("00/00/0000")){
                situacao = "Empilhado";
            }else{
                situacao = "Em Pé";
            }
        }else if(jComboBoxCategoria.getSelectedIndex()==2){
            if((float)jSpinnerM3HA.getValue()>0 && !jTextFieldDataPlantio.getText().equals("00/00/0000")){
                situacao = "Inventariado";
            }else if(!jTextFieldDataPlantio.getText().equals("00/00/0000")){
                situacao = "Não Inventariado";
            }else{
                situacao = "Plantio Futuro";
            }
        }else if(jComboBoxCategoria.getSelectedIndex()==3){
            situacao = "---";
        }else{
            situacao = "-";
        }
        if(jCheckBoxExaurida.isSelected()){
            situacao = "Exaurida";            
            jSpinnerM3HA.setValue(0f);
        }
        jLabelSituacao.setText("Situação: "+situacao);
        if((float)jSpinnerRendimentoGravimetricoEstimado.getValue()>0f){
            CalcularMDCHA();
        }else{
            JOptionPane.showMessageDialog(null, "Erro, rendimento gravimetrico estimado deve ser maior que zero(0)! \nRegistro cancelado!");
        }
        /*if((float)jSpinnerM3HA.getValue()>0){
            CalcularMDCHA();
        }else{
            RegistrarEstoque();
        }*/
    }   
    
    private void CalcularMDCHA(){
        mdc_ha = (float)jSpinnerM3HA.getValue() / (float)jSpinnerRendimentoGravimetricoEstimado.getValue();
        jLabelMDC_HA.setText("MDC/HA: "+mdc_ha);
        VolumeMadEstimado();
    }
    
    private void VolumeMadEstimado(){
        volume_madeira_estimado = (float)jSpinnerArea.getValue()*(float)jSpinnerM3HA.getValue();
        jLabelVolumeEstimado.setText("Volume Madeira Estimado: "+volume_madeira_estimado);
        MetroCarvaoEstimado();
    }
    
    private void MetroCarvaoEstimado(){
        mdc_estimado = mdc_ha*(float)jSpinnerArea.getValue();
        jLabelMDCEstimado.setText("MDC Estimado: "+mdc_estimado);
        MadeiraToneladaHA();
    }
    
    private void MadeiraToneladaHA(){
        mad_ton_ha = (float)jSpinnerM3HA.getValue()* (float)jSpinnerDensidadeMadeira.getValue();
        jLabelMadeiraTonHA.setText("Madeira Tonelada HA: "+mad_ton_ha);
        CarvaoToneladaHA();
    }
    
    private void CarvaoToneladaHA(){
        carv_ton_ha = (float)jSpinnerM3HA.getValue()* (float)jSpinnerDensidadeCarvao.getValue();
        jLabelCarvaoTonHA.setText("Carvão Tonelada HA: "+carv_ton_ha);
        MadeiraToneladaestimado();
    }
    
    private void MadeiraToneladaestimado(){
        mad_ton_estimado = mad_ton_ha * (float)jSpinnerArea.getValue();
        jLabelMadeiraTonEst.setText("Madeira Tonelada Estimada: "+mad_ton_estimado);
        CarvaoToneladaestimado();
    }
        
    private void CarvaoToneladaestimado(){
        carv_ton_estimado = carv_ton_ha * (float)jSpinnerArea.getValue();
        jLabelCarvaoTonEst.setText("Carvão Tonelada Estimada: "+carv_ton_estimado);
        //CalcularRendimentoEstimado();
        
        if(jTextFieldDataPlantio.getText().length()<10 || jTextFieldDataRotacao1.getText().length()<10 || jTextFieldDataRotacao2.getText().length()<10 || jTextFieldDataRotacao3.getText().length()<10){
            JOptionPane.showMessageDialog(null, "O formato das datas de ser 00/00/0000! \nRegistro cancelado!");
        }else if(idade_hoje>30 || idade_corte1>10 || idade_corte2>10 || idade_corte3>10){
            JOptionPane.showMessageDialog(null, "Idade invalida! \nIdade hj: "+idade_hoje+" anos / 30 anos! \nConfira as datas! \nRegistro cancelado!");
        }else{
            RegistrarEstoque();
        }
    }    
    
    private void RegistrarEstoque(){
        ControleEstoquePrincipal estoque_principal = new ControleEstoquePrincipal();
        estoque_principal.setId_estoque_p(id);
        estoque_principal.setUpc((int)jSpinnerUpc.getValue());
        estoque_principal.setArea((float)jSpinnerArea.getValue());
        estoque_principal.setM3_ha((float)jSpinnerM3HA.getValue());
        estoque_principal.setMdc_ha(mdc_ha);
        estoque_principal.setMaterial_genetico(jTextFieldMaterialGenetico.getText());
        estoque_principal.setIma((int)jSpinnerIMA.getValue());
        estoque_principal.setTalhadia(talhadia);
        estoque_principal.setAno_rotacao((int)jSpinnerAnoRotacao.getValue());  
        estoque_principal.setData_plantio(jTextFieldDataPlantio.getText());
        estoque_principal.setData_rotacao_1(jTextFieldDataRotacao1.getText());
        estoque_principal.setData_rotacao_2(jTextFieldDataRotacao2.getText());
        estoque_principal.setData_rotacao_3(jTextFieldDataRotacao3.getText());
        estoque_principal.setIdade_hoje(idade_hoje);        
        estoque_principal.setIdade_corte1(idade_corte1);
        estoque_principal.setIdade_corte2(idade_corte2);
        estoque_principal.setIdade_corte3(idade_corte3);
        estoque_principal.setCategoria(jComboBoxCategoria.getSelectedItem().toString());
        estoque_principal.setSituacao(situacao);             
        estoque_principal.setDensidade_madeira((float)jSpinnerDensidadeMadeira.getValue()); 
        estoque_principal.setDensidade_carvao((float)jSpinnerDensidadeCarvao.getValue());
        estoque_principal.setMad_ton_ha(mad_ton_ha);
        estoque_principal.setCarv_ton_ha(carv_ton_ha); 
        estoque_principal.setRend_grav_estimado((float)jSpinnerRendimentoGravimetricoEstimado.getValue());
        estoque_principal.setFator_empilalhemto((float)jSpinnerFatorEmpilhamento.getValue());
        
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanelSD = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxCategoria = new javax.swing.JComboBox();
        jLabelTalhao = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jSpinnerUpc = new javax.swing.JSpinner();
        jSpinnerTalhao = new javax.swing.JSpinner();
        jSpinnerArea = new javax.swing.JSpinner();
        jSpinnerM3HA = new javax.swing.JSpinner();
        jSpinnerIMA = new javax.swing.JSpinner();
        jSpinnerFatorEmpilhamento = new javax.swing.JSpinner();
        jTextFieldMaterialGenetico = new javax.swing.JTextField();
        jPanelSE = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldDataPlantio = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextFieldDataRotacao3 = new javax.swing.JTextField();
        jTextFieldDataRotacao2 = new javax.swing.JTextField();
        jTextFieldDataRotacao1 = new javax.swing.JTextField();
        jSpinnerDensidadeMadeira = new javax.swing.JSpinner();
        jSpinnerDensidadeCarvao = new javax.swing.JSpinner();
        jSpinnerRendimentoGravimetricoEstimado = new javax.swing.JSpinner();
        jSpinnerAnoRotacao = new javax.swing.JSpinner();
        jPanelI = new javax.swing.JPanel();
        jButtonCargaTalhao = new javax.swing.JButton();
        jLabelMDC_HA = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();
        jLabelTalhadia = new javax.swing.JLabel();
        jLabelSituacao = new javax.swing.JLabel();
        jLabelVolumeEstimado = new javax.swing.JLabel();
        jCheckBoxExaurida = new javax.swing.JCheckBox();
        jLabelMDCEstimado = new javax.swing.JLabel();
        jLabelMadeiraTonHA = new javax.swing.JLabel();
        jLabelCarvaoTonHA = new javax.swing.JLabel();
        jLabelMadeiraTonEst = new javax.swing.JLabel();
        jLabelCarvaoTonEst = new javax.swing.JLabel();
        jLabelIdadeHoje = new javax.swing.JLabel();
        jLabelIdadeCorte = new javax.swing.JLabel();
        jLabelLocal = new javax.swing.JLabel();
        jLabelTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelIdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelIdTipo)
                .addGap(10, 10, 10))
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

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getSize()+1f));
        jLabel10.setText("Upc");
        jLabel10.setPreferredSize(new java.awt.Dimension(200, 25));

        jComboBoxCategoria.setFont(jComboBoxCategoria.getFont().deriveFont(jComboBoxCategoria.getFont().getSize()+1f));
        jComboBoxCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxCategoria.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabelTalhao.setFont(jLabelTalhao.getFont().deriveFont(jLabelTalhao.getFont().getSize()+1f));
        jLabelTalhao.setText("categoria");
        jLabelTalhao.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel18.setFont(jLabel18.getFont().deriveFont(jLabel18.getFont().getSize()+1f));
        jLabel18.setText("talhao");
        jLabel18.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel20.setFont(jLabel20.getFont().deriveFont(jLabel20.getFont().getSize()+1f));
        jLabel20.setText("area");
        jLabel20.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel14.setFont(jLabel14.getFont().deriveFont(jLabel14.getFont().getSize()+1f));
        jLabel14.setText("M³ por hectare");
        jLabel14.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel21.setFont(jLabel21.getFont().deriveFont(jLabel21.getFont().getSize()+1f));
        jLabel21.setText("material_genetico");
        jLabel21.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel11.setFont(jLabel11.getFont().deriveFont(jLabel11.getFont().getSize()+1f));
        jLabel11.setText("ima");
        jLabel11.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel23.setFont(jLabel23.getFont().deriveFont(jLabel23.getFont().getSize()+1f));
        jLabel23.setText("fator empilhamento");
        jLabel23.setPreferredSize(new java.awt.Dimension(200, 25));

        jSpinnerUpc.setFont(jSpinnerUpc.getFont().deriveFont(jSpinnerUpc.getFont().getSize()+1f));
        jSpinnerUpc.setModel(new javax.swing.SpinnerNumberModel());
        jSpinnerUpc.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerTalhao.setFont(jSpinnerTalhao.getFont().deriveFont(jSpinnerTalhao.getFont().getSize()+1f));
        jSpinnerTalhao.setModel(new javax.swing.SpinnerNumberModel());
        jSpinnerTalhao.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerArea.setFont(jSpinnerArea.getFont().deriveFont(jSpinnerArea.getFont().getSize()+1f));
        jSpinnerArea.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.01f)));
        jSpinnerArea.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerM3HA.setFont(jSpinnerM3HA.getFont().deriveFont(jSpinnerM3HA.getFont().getSize()+1f));
        jSpinnerM3HA.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.01f)));
        jSpinnerM3HA.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerIMA.setFont(jSpinnerIMA.getFont().deriveFont(jSpinnerIMA.getFont().getSize()+1f));
        jSpinnerIMA.setModel(new javax.swing.SpinnerNumberModel());
        jSpinnerIMA.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerFatorEmpilhamento.setFont(jSpinnerFatorEmpilhamento.getFont().deriveFont(jSpinnerFatorEmpilhamento.getFont().getSize()+1f));
        jSpinnerFatorEmpilhamento.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.01f)));
        jSpinnerFatorEmpilhamento.setPreferredSize(new java.awt.Dimension(150, 25));

        jTextFieldMaterialGenetico.setFont(jTextFieldMaterialGenetico.getFont().deriveFont(jTextFieldMaterialGenetico.getFont().getSize()+1f));
        jTextFieldMaterialGenetico.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldMaterialGenetico.setPreferredSize(new java.awt.Dimension(150, 25));

        javax.swing.GroupLayout jPanelSDLayout = new javax.swing.GroupLayout(jPanelSD);
        jPanelSD.setLayout(jPanelSDLayout);
        jPanelSDLayout.setHorizontalGroup(
            jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSDLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelSDLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerUpc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelSDLayout.createSequentialGroup()
                        .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSDLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSDLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSDLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerM3HA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSDLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jTextFieldMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSDLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerIMA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSDLayout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerFatorEmpilhamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );
        jPanelSDLayout.setVerticalGroup(
            jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSDLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerUpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerM3HA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMaterialGenetico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerIMA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerFatorEmpilhamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getSize()+1f));
        jLabel8.setText("Ano_rotacao");
        jLabel8.setPreferredSize(new java.awt.Dimension(200, 25));

        jTextFieldDataPlantio.setFont(jTextFieldDataPlantio.getFont().deriveFont(jTextFieldDataPlantio.getFont().getSize()+1f));
        jTextFieldDataPlantio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldDataPlantio.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel25.setFont(jLabel25.getFont().deriveFont(jLabel25.getFont().getSize()+1f));
        jLabel25.setText("data_plantio");
        jLabel25.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel28.setFont(jLabel28.getFont().deriveFont(jLabel28.getFont().getSize()+1f));
        jLabel28.setText("data_rotacao_1");
        jLabel28.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel29.setFont(jLabel29.getFont().deriveFont(jLabel29.getFont().getSize()+1f));
        jLabel29.setText("data_rotacao_2");
        jLabel29.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel31.setFont(jLabel31.getFont().deriveFont(jLabel31.getFont().getSize()+1f));
        jLabel31.setText("data_rotacao_3");
        jLabel31.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getSize()+1f));
        jLabel9.setText("densidade_madeira");
        jLabel9.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel22.setFont(jLabel22.getFont().deriveFont(jLabel22.getFont().getSize()+1f));
        jLabel22.setText("densidade_carvao");
        jLabel22.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel24.setFont(jLabel24.getFont().deriveFont(jLabel24.getFont().getSize()+1f));
        jLabel24.setText("rend. grav. est.");
        jLabel24.setPreferredSize(new java.awt.Dimension(200, 25));

        jTextFieldDataRotacao3.setFont(jTextFieldDataRotacao3.getFont().deriveFont(jTextFieldDataRotacao3.getFont().getSize()+1f));
        jTextFieldDataRotacao3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldDataRotacao3.setPreferredSize(new java.awt.Dimension(150, 25));

        jTextFieldDataRotacao2.setFont(jTextFieldDataRotacao2.getFont().deriveFont(jTextFieldDataRotacao2.getFont().getSize()+1f));
        jTextFieldDataRotacao2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldDataRotacao2.setPreferredSize(new java.awt.Dimension(150, 25));

        jTextFieldDataRotacao1.setFont(jTextFieldDataRotacao1.getFont().deriveFont(jTextFieldDataRotacao1.getFont().getSize()+1f));
        jTextFieldDataRotacao1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldDataRotacao1.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerDensidadeMadeira.setFont(jSpinnerDensidadeMadeira.getFont().deriveFont(jSpinnerDensidadeMadeira.getFont().getSize()+1f));
        jSpinnerDensidadeMadeira.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.001f)));
        jSpinnerDensidadeMadeira.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerDensidadeCarvao.setFont(jSpinnerDensidadeCarvao.getFont().deriveFont(jSpinnerDensidadeCarvao.getFont().getSize()+1f));
        jSpinnerDensidadeCarvao.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.001f)));
        jSpinnerDensidadeCarvao.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerRendimentoGravimetricoEstimado.setFont(jSpinnerRendimentoGravimetricoEstimado.getFont().deriveFont(jSpinnerRendimentoGravimetricoEstimado.getFont().getSize()+1f));
        jSpinnerRendimentoGravimetricoEstimado.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.01f)));
        jSpinnerRendimentoGravimetricoEstimado.setPreferredSize(new java.awt.Dimension(150, 25));

        jSpinnerAnoRotacao.setFont(jSpinnerAnoRotacao.getFont().deriveFont(jSpinnerAnoRotacao.getFont().getSize()+1f));
        jSpinnerAnoRotacao.setModel(new javax.swing.SpinnerNumberModel());
        jSpinnerAnoRotacao.setMinimumSize(new java.awt.Dimension(200, 25));
        jSpinnerAnoRotacao.setPreferredSize(new java.awt.Dimension(150, 25));

        javax.swing.GroupLayout jPanelSELayout = new javax.swing.GroupLayout(jPanelSE);
        jPanelSE.setLayout(jPanelSELayout);
        jPanelSELayout.setHorizontalGroup(
            jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSELayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSELayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerAnoRotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSELayout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jTextFieldDataPlantio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSELayout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerRendimentoGravimetricoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSELayout.createSequentialGroup()
                        .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldDataRotacao1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldDataRotacao2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldDataRotacao3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSpinnerDensidadeMadeira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelSELayout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSpinnerDensidadeCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );
        jPanelSELayout.setVerticalGroup(
            jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSELayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerAnoRotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDataPlantio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDataRotacao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDataRotacao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDataRotacao3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerDensidadeMadeira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerDensidadeCarvao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanelSELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerRendimentoGravimetricoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jButtonCargaTalhao.setFont(jButtonCargaTalhao.getFont().deriveFont(jButtonCargaTalhao.getFont().getSize()+1f));
        jButtonCargaTalhao.setText("Registrar");
        jButtonCargaTalhao.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonCargaTalhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaTalhaoActionPerformed(evt);
            }
        });

        jLabelMDC_HA.setFont(jLabelMDC_HA.getFont().deriveFont(jLabelMDC_HA.getFont().getSize()+1f));
        jLabelMDC_HA.setText("MDC/HA:");
        jLabelMDC_HA.setPreferredSize(new java.awt.Dimension(500, 25));

        jButtonVoltar.setFont(jButtonVoltar.getFont().deriveFont(jButtonVoltar.getFont().getSize()+1f));
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabelTalhadia.setFont(jLabelTalhadia.getFont().deriveFont(jLabelTalhadia.getFont().getSize()+1f));
        jLabelTalhadia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTalhadia.setText("Talhadia:");
        jLabelTalhadia.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelSituacao.setFont(jLabelSituacao.getFont().deriveFont(jLabelSituacao.getFont().getSize()+1f));
        jLabelSituacao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelSituacao.setText("Situação:");
        jLabelSituacao.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelVolumeEstimado.setFont(jLabelVolumeEstimado.getFont().deriveFont(jLabelVolumeEstimado.getFont().getSize()+1f));
        jLabelVolumeEstimado.setText("Volume Madeira Estimado:");
        jLabelVolumeEstimado.setPreferredSize(new java.awt.Dimension(300, 25));

        jCheckBoxExaurida.setFont(jCheckBoxExaurida.getFont().deriveFont(jCheckBoxExaurida.getFont().getSize()+1f));
        jCheckBoxExaurida.setText("Situação Exaurida");
        jCheckBoxExaurida.setPreferredSize(new java.awt.Dimension(500, 25));
        jCheckBoxExaurida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxExauridaActionPerformed(evt);
            }
        });

        jLabelMDCEstimado.setFont(jLabelMDCEstimado.getFont().deriveFont(jLabelMDCEstimado.getFont().getSize()+1f));
        jLabelMDCEstimado.setText("MDC Estimado:");
        jLabelMDCEstimado.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelMadeiraTonHA.setFont(jLabelMadeiraTonHA.getFont().deriveFont(jLabelMadeiraTonHA.getFont().getSize()+1f));
        jLabelMadeiraTonHA.setText("Madeira Tonelada HA:");
        jLabelMadeiraTonHA.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelCarvaoTonHA.setFont(jLabelCarvaoTonHA.getFont().deriveFont(jLabelCarvaoTonHA.getFont().getSize()+1f));
        jLabelCarvaoTonHA.setText("Carvão Tonelada HA:");
        jLabelCarvaoTonHA.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelMadeiraTonEst.setFont(jLabelMadeiraTonEst.getFont().deriveFont(jLabelMadeiraTonEst.getFont().getSize()+1f));
        jLabelMadeiraTonEst.setText("Madeira Tonelada Estimada:");
        jLabelMadeiraTonEst.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelCarvaoTonEst.setFont(jLabelCarvaoTonEst.getFont().deriveFont(jLabelCarvaoTonEst.getFont().getSize()+1f));
        jLabelCarvaoTonEst.setText("Carvão Tonelada Estimada:");
        jLabelCarvaoTonEst.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelIdadeHoje.setFont(jLabelIdadeHoje.getFont().deriveFont(jLabelIdadeHoje.getFont().getSize()+1f));
        jLabelIdadeHoje.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelIdadeHoje.setText("Idade Hoje:");
        jLabelIdadeHoje.setPreferredSize(new java.awt.Dimension(300, 25));

        jLabelIdadeCorte.setFont(jLabelIdadeCorte.getFont().deriveFont(jLabelIdadeCorte.getFont().getSize()+1f));
        jLabelIdadeCorte.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelIdadeCorte.setText("Idade Corte:");
        jLabelIdadeCorte.setPreferredSize(new java.awt.Dimension(300, 25));

        javax.swing.GroupLayout jPanelILayout = new javax.swing.GroupLayout(jPanelI);
        jPanelI.setLayout(jPanelILayout);
        jPanelILayout.setHorizontalGroup(
            jPanelILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelILayout.createSequentialGroup()
                .addGroup(jPanelILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelIdadeCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanelILayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(jPanelILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jCheckBoxExaurida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelMDC_HA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelMDCEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelMadeiraTonHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelCarvaoTonHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelMadeiraTonEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelCarvaoTonEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelIdadeHoje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelTalhadia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelVolumeEstimado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanelILayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelILayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCheckBoxExaurida, jLabelCarvaoTonEst, jLabelCarvaoTonHA, jLabelIdadeCorte, jLabelIdadeHoje, jLabelMDCEstimado, jLabelMDC_HA, jLabelMadeiraTonEst, jLabelMadeiraTonHA, jLabelSituacao, jLabelTalhadia, jLabelVolumeEstimado});

        jPanelILayout.setVerticalGroup(
            jPanelILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelILayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jCheckBoxExaurida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelMDC_HA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelVolumeEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelMDCEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelMadeiraTonHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelCarvaoTonHA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelMadeiraTonEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelCarvaoTonEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelIdadeHoje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelIdadeCorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelTalhadia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabelSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCargaTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelILayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jCheckBoxExaurida, jLabelCarvaoTonEst, jLabelCarvaoTonHA, jLabelIdadeCorte, jLabelIdadeHoje, jLabelMDCEstimado, jLabelMDC_HA, jLabelMadeiraTonEst, jLabelMadeiraTonHA, jLabelSituacao, jLabelTalhadia, jLabelVolumeEstimado});

        jLabelLocal.setFont(jLabelLocal.getFont().deriveFont(jLabelLocal.getFont().getSize()+4f));
        jLabelLocal.setText("Local:");
        jLabelLocal.setPreferredSize(new java.awt.Dimension(300, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanelSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanelSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPanelI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Editar Estoque");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 60));

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jScrollPane1.setViewportView(jPanelMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonCargaTalhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaTalhaoActionPerformed
        //CalcularMadeiraTotal();
        //RegistrarEstoque();
        //CalcularIdade(jTextFieldDataPlantio.getText(), jTextFieldDataRotacao1.getText());
        DefinirTalhadia();
    }//GEN-LAST:event_jButtonCargaTalhaoActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

        float ant;
    private void jCheckBoxExauridaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxExauridaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxExauridaActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> {
            new AlterarEstoquePrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCargaTalhao;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JCheckBox jCheckBoxExaurida;
    private javax.swing.JComboBox jComboBoxCategoria;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCarvaoTonEst;
    private javax.swing.JLabel jLabelCarvaoTonHA;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelIdadeCorte;
    private javax.swing.JLabel jLabelIdadeHoje;
    private javax.swing.JLabel jLabelLocal;
    private javax.swing.JLabel jLabelMDCEstimado;
    private javax.swing.JLabel jLabelMDC_HA;
    private javax.swing.JLabel jLabelMadeiraTonEst;
    private javax.swing.JLabel jLabelMadeiraTonHA;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelSituacao;
    private javax.swing.JLabel jLabelTalhadia;
    private javax.swing.JLabel jLabelTalhao;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelVolumeEstimado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelI;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSD;
    private javax.swing.JPanel jPanelSE;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerAnoRotacao;
    private javax.swing.JSpinner jSpinnerArea;
    private javax.swing.JSpinner jSpinnerDensidadeCarvao;
    private javax.swing.JSpinner jSpinnerDensidadeMadeira;
    private javax.swing.JSpinner jSpinnerFatorEmpilhamento;
    private javax.swing.JSpinner jSpinnerIMA;
    private javax.swing.JSpinner jSpinnerM3HA;
    private javax.swing.JSpinner jSpinnerRendimentoGravimetricoEstimado;
    private javax.swing.JSpinner jSpinnerTalhao;
    private javax.swing.JSpinner jSpinnerUpc;
    private javax.swing.JTextField jTextFieldDataPlantio;
    private javax.swing.JTextField jTextFieldDataRotacao1;
    private javax.swing.JTextField jTextFieldDataRotacao2;
    private javax.swing.JTextField jTextFieldDataRotacao3;
    private javax.swing.JTextField jTextFieldMaterialGenetico;
    // End of variables declaration//GEN-END:variables
}
