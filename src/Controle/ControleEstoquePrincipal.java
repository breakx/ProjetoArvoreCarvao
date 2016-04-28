/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 *
 * @author Cristiano GD
 */
public class ControleEstoquePrincipal {
    
    private String id_estoque_p = null;
    private String estado = null; 
    private String bloco = null;
    private String municipio = null; 
    private String fazenda = null;
    private String projeto = null; 
    private String upc = null;
    private int talhao = 0;
    private float area = 0.0f;
    private String material_genetico = "-";
    private float m3_ha = 0.0f;
    private int talhadia = 0;
    private int ano_rotacao = 0; 
    private String data_plantio = "00/00/0000";
    private String data_rotacao_1 = "00/00/0000";
    private String data_rotacao_2 = "00/00/0000";
    private String data_rotacao_3 = "00/00/0000";
    private float idade_corte1 = 0.0f;
    private float idade_corte2 = 0.0f;
    private float idade_corte3 = 0.0f;
    private float idade_hoje = 0.0f;    
    private String conducao = "-";
    private String categoria = "Silvicultura";
    private String situacao = "Plantio Futuro";
    private float ima = 0.0f;
    private float mdc_ha = 0.0f;
    private float densidade_madeira = 0.0f; 
    private float densidade_carvao = 0.0f; 
    private float mad_ton_ha = 0.0f; 
    private float carv_ton_ha = 0.0f; 
    private String id_operario = "-";
    private String data_estoque = "00/00/0000";
    private float vol_mad_estimado = 0.0f; 
    private float vol_mad_transp = 0.0f;  
    private float vol_mad_balanco = 0.0f; 
    private float mdc_estimado = 0.0f;  
    private float mdc_transp = 0.0f; 
    private float mdc_balanco = 0.0f; 
    private float mad_ton_estimado = 0.0f; 
    private float mad_ton_transp = 0.0f; 
    private float mad_ton_balanco = 0.0f; 
    private float carv_ton_estimado = 0.0f; 
    private float carv_ton_transp = 0.0f; 
    private float carv_ton_balanco = 0.0f; 
    private float madeira_praca = 0.0f; 
    private float madeira_forno = 0.0f; 
    private float rend_grav_estimado = 0.0f; 
    private float rend_grav_real = 0.0f; 
    private float fator_empilalhemto = 0.0f; 

    /**
     * @return the id_estoque_p
     */
    public String getId_estoque_p() {
        return id_estoque_p;
    }

    /**
     * @param id_estoque_p the id_estoque_p to set
     */
    public void setId_estoque_p(String id_estoque_p) {
        this.id_estoque_p = id_estoque_p;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the bloco
     */
    public String getBloco() {
        return bloco;
    }

    /**
     * @param bloco the bloco to set
     */
    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    /**
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the fazenda
     */
    public String getFazenda() {
        return fazenda;
    }

    /**
     * @param fazenda the fazenda to set
     */
    public void setFazenda(String fazenda) {
        this.fazenda = fazenda;
    }

    /**
     * @return the projeto
     */
    public String getProjeto() {
        return projeto;
    }

    /**
     * @param projeto the projeto to set
     */
    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    /**
     * @return the upc
     */
    public String getUpc() {
        return upc;
    }

    /**
     * @param upc the upc to set
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }

    /**
     * @return the talhao
     */
    public int getTalhao() {
        return talhao;
    }

    /**
     * @param talhao the talhao to set
     */
    public void setTalhao(int talhao) {
        this.talhao = talhao;
    }

    /**
     * @return the area
     */
    public float getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(float area) {
        this.area = area;
    }

    /**
     * @return the m3_ha
     */
    public float getM3_ha() {
        return m3_ha;
    }

    /**
     * @param m3_ha the m3_ha to set
     */
    public void setM3_ha(float m3_ha) {
        this.m3_ha = m3_ha;
    }

    /**
     * @return the material_genetico
     */
    public String getMaterial_genetico() {
        return material_genetico;
    }

    /**
     * @param material_genetico the material_genetico to set
     */
    public void setMaterial_genetico(String material_genetico) {
        this.material_genetico = material_genetico;
    }

    /**
     * @return the talhadia
     */
    public int getTalhadia() {
        return talhadia;
    }

    /**
     * @param talhadia the talhadia to set
     */
    public void setTalhadia(int talhadia) {
        this.talhadia = talhadia;
    }

    /**
     * @return the ano_rotacao
     */
    public int getAno_rotacao() {
        return ano_rotacao;
    }

    /**
     * @param ano_rotacao the ano_rotacao to set
     */
    public void setAno_rotacao(int ano_rotacao) {
        this.ano_rotacao = ano_rotacao;
    }

    /**
     * @return the data_plantio
     */
    public String getData_plantio() {
        return data_plantio;
    }

    /**
     * @param data_plantio the data_plantio to set
     */
    public void setData_plantio(String data_plantio) {
        this.data_plantio = data_plantio;
    }

    /**
     * @return the data_rotacao_1
     */
    public String getData_rotacao_1() {
        return data_rotacao_1;
    }

    /**
     * @param data_rotacao_1 the data_rotacao_1 to set
     */
    public void setData_rotacao_1(String data_rotacao_1) {
        this.data_rotacao_1 = data_rotacao_1;
    }

    /**
     * @return the data_rotacao_2
     */
    public String getData_rotacao_2() {
        return data_rotacao_2;
    }

    /**
     * @param data_rotacao_2 the data_rotacao_2 to set
     */
    public void setData_rotacao_2(String data_rotacao_2) {
        this.data_rotacao_2 = data_rotacao_2;
    }

    /**
     * @return the data_rotacao_3
     */
    public String getData_rotacao_3() {
        return data_rotacao_3;
    }

    /**
     * @param data_rotacao_3 the data_rotacao_3 to set
     */
    public void setData_rotacao_3(String data_rotacao_3) {
        this.data_rotacao_3 = data_rotacao_3;
    }

    /**
     * @return the idade_hoje
     */
    public float getIdade_hoje() {
        return idade_hoje;
    }

    /**
     * @param idade_hoje the idade_hoje to set
     */
    public void setIdade_hoje(float idade_hoje) {
        this.idade_hoje = idade_hoje;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    /**
     * @return the ima
     */
    public float getIma() {
        return ima;
    }

    /**
     * @param ima the ima to set
     */
    public void setIma(float ima) {
        this.ima = ima;
    }
    
    /**
     * @return the mdc_ha
     */
    public float getMdc_ha() {
        return mdc_ha;
    }

    /**
     * @param mdc_ha the mdc_ha to set
     */
    public void setMdc_ha(float mdc_ha) {
        this.mdc_ha = mdc_ha;
    }

    /**
     * @return the densidade_madeira
     */
    public float getDensidade_madeira() {
        return densidade_madeira;
    }

    /**
     * @param densidade_madeira the densidade_madeira to set
     */
    public void setDensidade_madeira(float densidade_madeira) {
        this.densidade_madeira = densidade_madeira;
    }

    /**
     * @return the densidade_carvao
     */
    public float getDensidade_carvao() {
        return densidade_carvao;
    }

    /**
     * @param densidade_carvao the densidade_carvao to set
     */
    public void setDensidade_carvao(float densidade_carvao) {
        this.densidade_carvao = densidade_carvao;
    }

    /**
     * @return the mad_ton_ha
     */
    public float getMad_ton_ha() {
        return mad_ton_ha;
    }

    /**
     * @param mad_ton_ha the mad_ton_ha to set
     */
    public void setMad_ton_ha(float mad_ton_ha) {
        this.mad_ton_ha = mad_ton_ha;
    }

    /**
     * @return the carv_ton_ha
     */
    public float getCarv_ton_ha() {
        return carv_ton_ha;
    }

    /**
     * @param carv_ton_ha the carv_ton_ha to set
     */
    public void setCarv_ton_ha(float carv_ton_ha) {
        this.carv_ton_ha = carv_ton_ha;
    }

    /**
     * @return the id_operario
     */
    public String getId_operario() {
        return id_operario;
    }

    /**
     * @param id_operario the id_operario to set
     */
    public void setId_operario(String id_operario) {
        this.id_operario = id_operario;
    }

    /**
     * @return the data_estoque
     */
    public String getData_estoque() {
        return data_estoque;
    }

    /**
     * @param data_estoque the data_estoque to set
     */
    public void setData_estoque(String data_estoque) {
        this.data_estoque = data_estoque;
    }

    /**
     * @return the vol_mad_estimado
     */
    public float getVol_mad_estimado() {
        return vol_mad_estimado;
    }

    /**
     * @param vol_mad_estimado the vol_mad_estimado to set
     */
    public void setVol_mad_estimado(float vol_mad_estimado) {
        this.vol_mad_estimado = vol_mad_estimado;
    }

    /**
     * @return the vol_mad_transp
     */
    public float getVol_mad_transp() {
        return vol_mad_transp;
    }

    /**
     * @param vol_mad_transp the vol_mad_transp to set
     */
    public void setVol_mad_transp(float vol_mad_transp) {
        this.vol_mad_transp = vol_mad_transp;
    }

    /**
     * @return the vol_mad_balanco
     */
    public float getVol_mad_balanco() {
        return vol_mad_balanco;
    }

    /**
     * @param vol_mad_balanco the vol_mad_balanco to set
     */
    public void setVol_mad_balanco(float vol_mad_balanco) {
        this.vol_mad_balanco = vol_mad_balanco;
    }

    /**
     * @return the mdc_estimado
     */
    public float getMdc_estimado() {
        return mdc_estimado;
    }

    /**
     * @param mdc_estimado the mdc_estimado to set
     */
    public void setMdc_estimado(float mdc_estimado) {
        this.mdc_estimado = mdc_estimado;
    }

    /**
     * @return the mdc_transp
     */
    public float getMdc_transp() {
        return mdc_transp;
    }

    /**
     * @param mdc_transp the mdc_transp to set
     */
    public void setMdc_transp(float mdc_transp) {
        this.mdc_transp = mdc_transp;
    }

    /**
     * @return the mdc_balanco
     */
    public float getMdc_balanco() {
        return mdc_balanco;
    }

    /**
     * @param mdc_balanco the mdc_balanco to set
     */
    public void setMdc_balanco(float mdc_balanco) {
        this.mdc_balanco = mdc_balanco;
    }

    /**
     * @return the mad_ton_estimado
     */
    public float getMad_ton_estimado() {
        return mad_ton_estimado;
    }

    /**
     * @param mad_ton_estimado the mad_ton_estimado to set
     */
    public void setMad_ton_estimado(float mad_ton_estimado) {
        this.mad_ton_estimado = mad_ton_estimado;
    }

    /**
     * @return the mad_ton_transp
     */
    public float getMad_ton_transp() {
        return mad_ton_transp;
    }

    /**
     * @param mad_ton_transp the mad_ton_transp to set
     */
    public void setMad_ton_transp(float mad_ton_transp) {
        this.mad_ton_transp = mad_ton_transp;
    }

    /**
     * @return the mad_ton_balanco
     */
    public float getMad_ton_balanco() {
        return mad_ton_balanco;
    }

    /**
     * @param mad_ton_balanco the mad_ton_balanco to set
     */
    public void setMad_ton_balanco(float mad_ton_balanco) {
        this.mad_ton_balanco = mad_ton_balanco;
    }

    /**
     * @return the carv_ton_estimado
     */
    public float getCarv_ton_estimado() {
        return carv_ton_estimado;
    }

    /**
     * @param carv_ton_estimado the carv_ton_estimado to set
     */
    public void setCarv_ton_estimado(float carv_ton_estimado) {
        this.carv_ton_estimado = carv_ton_estimado;
    }

    /**
     * @return the carv_ton_transp
     */
    public float getCarv_ton_transp() {
        return carv_ton_transp;
    }

    /**
     * @param carv_ton_transp the carv_ton_transp to set
     */
    public void setCarv_ton_transp(float carv_ton_transp) {
        this.carv_ton_transp = carv_ton_transp;
    }

    /**
     * @return the carv_ton_balanco
     */
    public float getCarv_ton_balanco() {
        return carv_ton_balanco;
    }

    /**
     * @param carv_ton_balanco the carv_ton_balanco to set
     */
    public void setCarv_ton_balanco(float carv_ton_balanco) {
        this.carv_ton_balanco = carv_ton_balanco;
    }

    /**
     * @return the madeira_praca
     */
    public float getMadeira_praca() {
        return madeira_praca;
    }

    /**
     * @param madeira_praca the madeira_praca to set
     */
    public void setMadeira_praca(float madeira_praca) {
        this.madeira_praca = madeira_praca;
    }

    /**
     * @return the madeira_forno
     */
    public float getMadeira_forno() {
        return madeira_forno;
    }

    /**
     * @param madeira_forno the madeira_forno to set
     */
    public void setMadeira_forno(float madeira_forno) {
        this.madeira_forno = madeira_forno;
    }

    /**
     * @return the rend_grav_estimado
     */
    public float getRend_grav_estimado() {
        return rend_grav_estimado;
    }

    /**
     * @param rend_grav_estimado the rend_grav_estimado to set
     */
    public void setRend_grav_estimado(float rend_grav_estimado) {
        this.rend_grav_estimado = rend_grav_estimado;
    }

    /**
     * @return the rend_grav_real
     */
    public float getRend_grav_real() {
        return rend_grav_real;
    }

    /**
     * @param rend_grav_real the rend_grav_real to set
     */
    public void setRend_grav_real(float rend_grav_real) {
        this.rend_grav_real = rend_grav_real;
    }

    /**
     * @return the fator_empilalhemto
     */
    public float getFator_empilalhemto() {
        return fator_empilalhemto;
    }

    /**
     * @param fator_empilalhemto the fator_empilalhemto to set
     */
    public void setFator_empilalhemto(float fator_empilalhemto) {
        this.fator_empilalhemto = fator_empilalhemto;
    }

    /**
     * @return the idade_corte1
     */
    public float getIdade_corte1() {
        return idade_corte1;
    }

    /**
     * @param idade_corte1 the idade_corte1 to set
     */
    public void setIdade_corte1(float idade_corte1) {
        this.idade_corte1 = idade_corte1;
    }

    /**
     * @return the conducao
     */
    public String getConducao() {
        return conducao;
    }

    /**
     * @param conducao the conducao to set
     */
    public void setConducao(String conducao) {
        this.conducao = conducao;
    }

    /**
     * @return the idade_corte2
     */
    public float getIdade_corte2() {
        return idade_corte2;
    }

    /**
     * @param idade_corte2 the idade_corte2 to set
     */
    public void setIdade_corte2(float idade_corte2) {
        this.idade_corte2 = idade_corte2;
    }

    /**
     * @return the idade_corte3
     */
    public float getIdade_corte3() {
        return idade_corte3;
    }

    /**
     * @param idade_corte3 the idade_corte3 to set
     */
    public void setIdade_corte3(float idade_corte3) {
        this.idade_corte3 = idade_corte3;
    }

}
